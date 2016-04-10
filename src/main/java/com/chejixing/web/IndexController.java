package com.chejixing.web;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.chejixing.biz.bean.DeviceStatus;
import com.chejixing.biz.bean.XCompany;
import com.chejixing.biz.bean.XSensor;
import com.chejixing.biz.service.MonDataService;
import com.chejixing.biz.service.UserService;

@Controller
public class IndexController {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private MonDataService monDataService;

	@Autowired
	private UserService userService;

	@RequestMapping("/index")
	@Transactional(readOnly = true)
	public String execute(ModelMap model) {
		return "index";
	}

	@RequestMapping("/getSensors")
	@Transactional(readOnly = true)
	public String getSensors(ModelMap model, Principal principal) {
		XCompany company = userService.getCompanyByUsername(principal.getName());

		Session session = this.sessionFactory.getCurrentSession();
		// 按sensor的名称排序
		Query queryCompanySensors = session
				.createQuery("select new map(sensor.temperature as temperature, sensor.humidity as humidity, sensor.id as sensorId, sensor.status as status,device.name as deviceName,device.phoneNo as phoneNo, sensor.name as sensorName, index(sensor) as sensorOrder, sensor.collectTime as collectTime, sensor as sensor) "
						+ " from XDevice as device join device.sensors as sensor join device.company as company "
						+ " where company.id=:companyId and sensor.temperature is not null and sensor.humidity is not null" + " order by sensor.name");
		queryCompanySensors.setLong("companyId", company.getId());
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> sensors = queryCompanySensors.list();

		for (Map<String, Object> sensor : sensors) {
			XSensor entity = (XSensor) sensor.get("sensor");
			if (entity.isOverheat() || entity.isOverhum()) {
				sensor.put("blink", "blink");
			} else {
				sensor.put("blink", null);
			}
			if (!entity.getDevice().getStatus().equals(DeviceStatus.NORMAL)) {
				sensor.put("deviceBlink", "blink");
			}
		}
		model.put("results", sensors);
		return "sensors";
	}

	@RequestMapping("/getStatistics")
	@Transactional(readOnly = true)
	public String getStatistics(ModelMap model, Principal principal) {
		Session session = this.sessionFactory.getCurrentSession();
		XCompany company = userService.getCompanyByUsername(principal.getName());

		Query queryAverage = session.createQuery("select new map(avg(sensor.temperature) as temp, avg(sensor.humidity) as humidity) "
				+ " from XDevice as device join device.sensors as sensor join device.company as company "
				+ " where company.id=:companyId and sensor.temperature is not null and sensor.humidity is not null");
		queryAverage.setLong("companyId", company.getId());
		model.put("avg", queryAverage.uniqueResult());

		Query sensorCountQuery = session.createQuery(
				"select count(sensor.id) from XSensor as sensor join sensor.device as device join device.company as company"
						+ " where sensor.temperature is not null and sensor.humidity is not null and company.id=?").setLong(0, company.getId());
		long count = (long) sensorCountQuery.uniqueResult();
		model.put("sensorCount", count);
		// 查询告警短信数量
		// 只包括发送成功的
		String s = "select count(*) from xtextmessage_audit a,xcompany_alarm_phone_number b" + " where b.company_id=:companyId and b.alarm_phone_number=a.phone" + " and a.success=1";
		Query q = session.createSQLQuery(s).setLong("companyId", company.getId());
		int alarmMessageCount = ((Number) q.uniqueResult()).intValue();
		model.put("alarmMessageCount", alarmMessageCount);
		return "statistics";
	}

	@RequestMapping("/getAlarmCount")
	@Transactional(readOnly = true)
	@ResponseBody
	public Map<String, Long> getAlarmCount(Principal principal) {
		Session session = this.sessionFactory.getCurrentSession();
		XCompany company = userService.getCompanyByUsername(principal.getName());

		Long deviceAlarmCount = (Long) session.createQuery("select count(id) from Alarm where clearTime is null and device.company=:company").setParameter("company", company).uniqueResult();

		Map<String, Long> result = new HashMap<String, Long>();
		result.put("count", deviceAlarmCount);
		return result;
	}
}
