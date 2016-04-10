package com.chejixing.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.chejixing.biz.bean.AlarmType;
import com.chejixing.biz.bean.Alarm;
import com.chejixing.biz.bean.XSensor;

public class TestAlarmMessageFormatter {

	@Test
	public void test(){
		XSensor sensor = new XSensor();
		sensor.setCollectTime(new Date());
		sensor.setHumidity(50.0);
		sensor.setName("测试探头");
		sensor.setTemperature(16.0);
		
		Alarm exist = new Alarm();
		exist.setCreateTime(new Date());
		exist.setSensor(sensor);
		exist.setReading(52.0);
		exist.setMax(50.0);
		exist.setMin(40.0);
		exist.setType(AlarmType.OVER_HEAT);
		
		String message = exist.getFormattedMessage();
		
		Assert.assertEquals("传感器：[测试探头]温度出现异常！当前温度：52.0，正常温度区间为：[40.0-50.0]", message);
	}
	public static void main(String[] args) throws ParseException{
		String dateStr = "140804120101";
		Date date = new SimpleDateFormat("yyMMddhhmmss").parse(dateStr);
		System.out.println(date);
	}
}
