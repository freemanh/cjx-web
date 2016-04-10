package com.chejixing.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chejixing.biz.bean.XCompany;
import com.chejixing.biz.bean.XMonData;
import com.chejixing.biz.service.UserService;
import com.chejixing.dao.MonDataDao;

@Controller
public class HistoryDataModificationController {
	private SessionFactory sessionFactory;
	private MonDataDao monDataDao;
	private UserService userService;

	@RequestMapping("/xiugai")
	@Transactional(readOnly=true)
	public String index(ModelMap model, Principal principal) {
		Session session = sessionFactory.getCurrentSession();
		
		XCompany company = userService.getCompanyByUsername(principal.getName());
		
		Query query = session
				.createQuery("select new map(sensor.name as name, sensor.id as id) from XSensor as sensor join sensor.device as device join device.company as company where company.id=? order by sensor.name");
		query.setLong(0, company.getId());
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> sensors = query.list();
		model.put("sensors", sensors);
		
		return "history_data_modification";
	}
	
	@RequestMapping("/xiugai/load")
	@Transactional(readOnly=true)
	public String list(ModelMap model,
			@RequestParam Date wrongDate,
			@RequestParam Date rightDate,
			@RequestParam Long wrongSensorId,
			@RequestParam Long rightSensorId,
			@RequestParam int wrongHour,
			@RequestParam int rightHour) {
		List<XMonData> wrongData = monDataDao.query(wrongSensorId, wrongDate, wrongHour);
		List<XMonData> rightData = monDataDao.query(rightSensorId, rightDate, rightHour);
		
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		for(XMonData wrongDataItem : wrongData){
			Map<String, Object> resultItem = new HashMap<String, Object>();
			
			resultItem.put("wrongTemp", wrongDataItem.getTemperature());
			resultItem.put("wrongHum", wrongDataItem.getHumidity());
			
			int index = wrongData.indexOf(wrongDataItem);
			if(rightData.size() > index){
				XMonData rightDataItem = rightData.get(index);
				resultItem.put("rightTemp", rightDataItem.getTemperature());
				resultItem.put("rightHum", rightDataItem.getHumidity());
			}
			results.add(resultItem);
		}
		
		model.put("results", results);
		return "_history_data_modification_table";
		
	}
	
	@RequestMapping("/xiugai/save")
	@Transactional
	public String save(@RequestParam Date wrongDate,
			@RequestParam Date rightDate,
			@RequestParam Long wrongSensorId,
			@RequestParam Long rightSensorId,
			@RequestParam int wrongHour,
			@RequestParam int rightHour){
		List<XMonData> wrongData = monDataDao.query(wrongSensorId, wrongDate, wrongHour);
		List<XMonData> rightData = monDataDao.query(rightSensorId, rightDate, rightHour);

		String wrongDeviceCode = null;
		Integer wrongSensorIndex = null;
		for(XMonData wrong : wrongData){
			wrongDeviceCode = wrong.getDeviceCode();
			wrongSensorIndex = wrong.getSensorIndex();
			monDataDao.delete(wrong);
		}
		
		for(XMonData right : rightData){
			Calendar rightCal = Calendar.getInstance();
			rightCal.setTime(right.getCollectTime());
			
			Calendar wrongCal = Calendar.getInstance();
			wrongCal.setTime(wrongDate);
			
			//利用正确数据的时间部分，错误数据的日期部分；因为正确数据和错误数据的日期可能不一致
			rightCal.set(wrongCal.get(Calendar.YEAR), wrongCal.get(Calendar.MONTH), wrongCal.get(Calendar.DATE));
			rightCal.set(Calendar.HOUR_OF_DAY, wrongHour);
			
			XMonData newData = new XMonData();
			newData.setCollectTime(rightCal.getTime());
			newData.setDeviceCode(wrongDeviceCode);
			newData.setHumidity(right.getHumidity());
			newData.setSensorIndex(wrongSensorIndex);
			newData.setTemperature(right.getTemperature());
			
			monDataDao.save(newData);
		}
		
		
		return "close_win";
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	public void setMonDataDao(MonDataDao monDataDao) {
		this.monDataDao = monDataDao;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	
}
