package com.chejixing.biz.service;

import java.util.*;
//
//服务端启动一个守护线程，定期运行，检查xsensor表的最后更新时间字段（last_update_time, 现在好像还没有，需要增加）。
//如果最后更新时间与当前系统时间相差10分钟（可配置），修改该xsensor记录的status字段；
//  前端根据xsensor表的status字段确定是否特殊显示设备的离线状态；

import com.chejixing.dao.MonStatDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chejixing.biz.bean.SensorStatus;
import com.chejixing.biz.bean.XSensor;

@Service
@Repository
public class OfflineMonService extends TimerTask {
    @Autowired
	private SessionFactory sessionFactory;

    @Autowired
    private MonStatDao monStatDao;

    //30秒检查一次
    int interval = 30000;

    //设备离线时间,上一次在线距当前时间的时间
    int offlineSpan = 10*60*1000;

    //上一次进行统计的时间
    long lastCountTime;
    //统计间隔
    long countSpan = 24*60*60*1000;

	public OfflineMonService(){
        super();
       new Timer().schedule(this,10000,interval);
    }

    @Override

    public void run() {
        //检查设备离线状态
        checkOffline();

        //每日统计
        countEveryDay();
    }
    @Transactional
    private boolean checkOffline() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from XSensor");
        List<XSensor> sensors = query.list();
        if(sensors==null || sensors.isEmpty())
            return true;

        Date now = new Date();
        for(XSensor s : sensors){
            Date lastUpdate = s.getLastUpdateTime();
            if(lastUpdate==null){
                s.setLastUpdateTime(now);
            }else{
            	SensorStatus status;
                if(now.getTime()-lastUpdate.getTime()>offlineSpan){
                    status = SensorStatus.ABNORMAL;
                }else{
                    status = SensorStatus.NORMAL;
                }
                s.setStatus(status);
            }
            session.persist(s);
        }
        session.flush();
        session.close();
        return false;
    }

    //每天定时统计到中间表,便于后续统计查询
    public void countEveryDay(){
         //如果距离上次统计时间超过24小时，则执行新的统计
        //统计时间放在凌晨
        long time = System.currentTimeMillis();

        Calendar c = Calendar.getInstance();
        int hour = c.get(c.HOUR_OF_DAY);

        //debug only
        boolean morning = true;

//        boolean morning = (hour>2 && hour<5);

//        if((time-lastCountTime>countSpan) && morning){
        if(true){
            monStatDao.countDevicesEveryDay();
            lastCountTime = time;
        }

    }

    @Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
