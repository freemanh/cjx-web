package com.chejixing.web;

import java.security.Principal;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chejixing.biz.bean.XSensor;
import com.chejixing.biz.bean.XUser;
import com.chejixing.biz.service.UserService;

@Controller
@RequestMapping("/sensor")
public class SensorController {
	private SessionFactory sessionFactory;
	private UserService userService;

	@Transactional(readOnly = true)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getSensor(@PathVariable Long id, ModelMap model, Principal principal) {
		XSensor sensor = (XSensor) sessionFactory.getCurrentSession().get(XSensor.class, id);
		model.put("sensor", sensor);

		XUser user = userService.getByUsername(principal.getName());

		model.put("isShowRevision", user.isShowRevision());

		return "/sensor/edit";
	}

	/**
	 * @param id
	 * @param sensorName
	 * @param maxTemp
	 * @param minTemp
	 * @param maxHumidity
	 * @param minHumidity
	 * @param tempRevision
	 * @param humRevision
	 * @param uploadFrequency
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String update(@PathVariable Long id, @RequestParam("sensorName") String sensorName, @RequestParam("maxTemp") Double maxTemp, @RequestParam("minTemp") Double minTemp,
			@RequestParam("maxHumidity") Double maxHumidity, @RequestParam("minHumidity") Double minHumidity,
			@RequestParam(value = "tempRevision", required = false) Double tempRevision, @RequestParam(value = "humRevision", required = false) Double humRevision,
			@RequestParam(value = "uploadFrequency", required = false) Integer uploadFrequency,
			@RequestParam(value = "alarmMode", required = false) Integer alarmMode) {
		XSensor sensor = (XSensor) sessionFactory.getCurrentSession().get(XSensor.class, id);
		sensor.setName(sensorName);
		sensor.setMaxTemp(maxTemp);
		sensor.setMinTemp(minTemp);
		sensor.setMaxHumidity(maxHumidity);
		sensor.setMinHumidity(minHumidity);
		if (null != tempRevision) {
			sensor.getConfig().setTempRevision(tempRevision);
			sensor.setSynced(false);
		}
		if (null != humRevision) {
			sensor.getConfig().setHumRevision(humRevision);
			sensor.setSynced(false);
		}
		if (null != uploadFrequency) {
			sensor.getConfig().setUploadFrequency(uploadFrequency);
			sensor.setSynced(false);
		}
		if(null != alarmMode){
			sensor.getConfig().setAlarmMode(alarmMode);
			sensor.setSynced(false);
		}

		return "close_win";
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
