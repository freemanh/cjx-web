package com.chejixing.dao;

import com.chejixing.biz.bean.XDevice;
import com.chejixing.biz.bean.XMonStat;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

//与数据统计相关的操作
@Service
@Repository
public class MonStatDao {
    private static Logger logger = LoggerFactory.getLogger(MonStatDao.class);
    @Autowired
    private SessionFactory sessionFactory;

    //统计每天的数据 ,单设备
    public void countEveryDay(long companyId,String deviceCode,Date from,Date to,Session session){
         String tablename = "mondata_"+deviceCode;
        createMonDataTable(session,tablename);
         String sql ="create table if not exists mon_stat as " +
                 "select :companyId as company_id,:collect_time as collect_time,device_Code,sensor_index," +
                 "max(temperature) as maxTemp,min(temperature) as minTemp,avg(temperature) as avgTemp," +
                 "max(humidity) as maxHum,min(humidity) as minHum,avg(humidity) as avgHum" +
                 " from "+tablename+
                 " where collect_time between :from and :to" +
                 " group by sensor_index,date_format(collect_time,'%Y-%m-%d')";

//        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = (SQLQuery) session.createSQLQuery(sql);
        //note:此处不能用setDate,对同一天会有问题
        query.setTimestamp("from",from);
        query.setTimestamp("to",to);
        query.setTimestamp("collect_time",from);
        query.setLong("companyId",companyId);
        query.executeUpdate();
    }

    //如果不存在对应的mondata表则创建
    public  void createMonDataTable(Session session,String tablename){
        String sql = "create table if not exists " +tablename+
                " as select * from mondata";
        SQLQuery query = session.createSQLQuery(sql);
        query.executeUpdate();
    }

    //统计所有设备每天的的数据
    public void countDevicesEveryDay(){
        Calendar c = (Calendar) GregorianCalendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date begin = c.getTime();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        Date end = c.getTime();

        Session session = sessionFactory.openSession();

        List<XDevice> devices = getxDevices(session);

        for(XDevice d : devices){
            countEveryDay(d.getCompany().getId(),d.getCode(),begin,end,session);
        }
        session.flush();
        session.close();
    }


    private List<XDevice> getxDevices(Session session) {
        String sql ="from XDevice";
        Query query = session.createQuery(sql);
        return query.list();
    }
}
