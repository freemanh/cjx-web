package com.chejixing.web;

import com.chejixing.dao.MonDataDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.invoke.MethodType;
import java.util.List;

/**
 * GPS 位置信息
 * created by:zjh
 */

@Controller
public class LocationController {

    @Autowired
    private MonDataDao monDataDao;

    // 初始化时显示位置信息
    @RequestMapping(value = "/location")
    @Transactional(readOnly = true)
    public String getRealTimeSpline(ModelMap model, String deviceId, Integer sensorId) throws Exception {
        model.put("lat",39.9411);
        model.put("lon",115.949);
        //公司位置
        model.put("lat",30.616100);
        model.put("lon",104.004448);

        //黄荆路东延线西
        model.put("lat",30.566338);
        model.put("lon",104.022555);
        List devices = monDataDao.getDevices();
        model.put("devices",devices);

        //查找各设备的位置
         List locations = monDataDao.getDeviceLocations();
        model.put("locations",locations);

        return "location";
    }

    //动态查询位置信息
    @RequestMapping(value = "/refreshLocations")
    @Transactional(readOnly = true)
    @ResponseBody
    public  Object refreshLocations() throws Exception {
        //查找各设备的位置
        List locations = monDataDao.getDeviceLocations();

        return locations;
    }

     @RequestMapping(value="/trail_playback")
    @Transactional(readOnly = true)
    @ResponseBody
    public Object trailPlayBack(ModelMap model,String deviceCode,
                                @RequestParam(value="startTime",required = false)  @DateTimeFormat(pattern="yyyy-MM-dd")Date startTime,
                                @RequestParam(value="endTime",required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endTime) throws Exception {
        List result = monDataDao.getDeviceHistoryLocations(deviceCode, startTime, endTime);

        return result;
    }
}
