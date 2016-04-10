package com.chejixing.web;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chejixing.biz.bean.XSensor;
import com.chejixing.dao.MonDataDao;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 图标生成控制器
 * created by:zjh
 */

@Controller
public class ChartController {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MonDataDao monDataDao;
    //指定传感器最近时间范围内温湿度曲线图
    @RequestMapping(value = "/refreshRealtimeChart")
    @Transactional(readOnly = true)
    @ResponseBody
    public Object refreshRealTimeSpline(ModelMap model, Long sensorId) throws Exception {
    	XSensor sensor = (XSensor) this.sessionFactory.getCurrentSession().get(XSensor.class, sensorId);
    	
		List<Object[]> list = monDataDao.getRealTimeSpline(sensor.getDevice().getCode(), sensor.getDevice().getSensors().indexOf(sensor));
       return list;
    }
    @RequestMapping(value = "/real_time_spline")
    public Object loadRealTimeSpline(ModelMap model, Long sensorId) throws Exception {
        model.put("sensorId",sensorId);
        return "real_time_spline";
    }}
