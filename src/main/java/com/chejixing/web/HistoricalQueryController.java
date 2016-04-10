package com.chejixing.web;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.chejixing.util.Define;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chejixing.biz.bean.XCompany;
import com.chejixing.biz.bean.XMonData;
import com.chejixing.biz.service.UserService;
import com.chejixing.dao.MonDataDao;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HistoricalQueryController {
	private static Logger logger = LoggerFactory.getLogger(HistoricalQueryController.class);

	@Autowired
	private MonDataDao monDataDao;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserService userService;

	@RequestMapping("/historical_query")
	@Transactional(readOnly = true)
	public String index(ModelMap model, Principal principal) {
		fillSensors(model, principal);

		return "historical_query";
	}

	private void fillSensors(ModelMap model, Principal principal) {
		XCompany company = userService.getCompanyByUsername(principal.getName());

		Session session = sessionFactory.getCurrentSession();
		// edit by zjh 2014-9-14:查询条件仅有传感器名称还不够
		Query query = session
				.createQuery("select new map(device.name as deviceName,sensor.name as name, sensor.id as id) from XSensor as sensor join sensor.device as device join device.company as company where company.id=? order by sensor.name");
		query.setLong(0, company.getId());
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> sensors = query.list();
		model.put("sensors", sensors);
	}

	@RequestMapping("/historical_query_result")
	@Transactional(readOnly = true)
	@ResponseBody
	public Object historical_query_result(HttpServletRequest request, ModelMap model

	, @RequestParam("sEcho") String sEcho, @RequestParam("iDisplayStart") int iDisplayStart, @RequestParam("iDisplayLength") int iDisplayLength,
			@RequestParam("sensorSelect") long sensorId, @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startTime,
			@RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endTime, Principal principal) {

		Map parameterMap = request.getParameterMap();
		logger.error(parameterMap.toString());
		Map result = new HashMap(parameterMap);
		List<XMonData> dataList = monDataDao.historyQuery(result, sensorId, startTime, endTime, iDisplayStart, iDisplayLength);

		result.put("sEcho", sEcho);

		return result;
	}
}
