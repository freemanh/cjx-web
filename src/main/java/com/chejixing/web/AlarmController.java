package com.chejixing.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chejixing.biz.bean.Alarm;
import com.chejixing.biz.bean.XCompany;
import com.chejixing.biz.service.UserService;

@Controller
public class AlarmController {
	@Autowired
	private SessionFactory sessionFactory;

	private UserService userService;

	@RequestMapping(value = "/alarms", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String list(ModelMap model, Principal p) {
		Session session = this.sessionFactory.getCurrentSession();

		XCompany company = userService.getCompanyByUsername(p.getName());

		@SuppressWarnings("unchecked")
		List<Alarm> alarms = session.createQuery("from Alarm where clearTime is null and device.company=:company order by createTime desc").setParameter("company", company).list();

		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		for (Alarm alarm : alarms) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", alarm.getId());
			map.put("createTime", alarm.getCreateTime());
			map.put("desc", alarm.getFormattedMessage());

			results.add(map);
		}

		model.put("results", results);

		return "alarm_list";
	}

	@RequestMapping(value = "/history_alarms", method = RequestMethod.GET)
	@Transactional(readOnly = true)
	public String listHistory(ModelMap model, Principal p) {
		Session session = this.sessionFactory.getCurrentSession();
		XCompany company = userService.getCompanyByUsername(p.getName());

		@SuppressWarnings("unchecked")
		List<Alarm> alarms = session.createQuery("from Alarm where clearTime is not null and device.company=:company order by createTime desc").setParameter("company", company).list();

		;
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		for (Alarm alarm : alarms) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", alarm.getId());
			map.put("createTime", alarm.getCreateTime());
			map.put("desc", alarm.getFormattedMessage());

			results.add(map);
		}

		model.put("results", results);

		return "history_alarm_list";
	}

	// 告警短信数量查询
	@RequestMapping("/alarm_sms_stat")
	@Transactional(readOnly = true)
	public String execute(ModelMap model, Principal principal) {
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		model.put("from", c.getTime());
		model.put("to", now);
		return "alarm_sms_stat";
	}

	// 告警短信数量查询结果
	@RequestMapping("/alarm_sms_stat_query")
	@Transactional(readOnly = true)
	public String alarm_sms_stat_query(ModelMap model, Principal p, @RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
			@RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
		Session session = this.sessionFactory.getCurrentSession();
		XCompany company = userService.getCompanyByUsername(p.getName());
		String sql = "select count(*) from xtextmessage_audit a,xcompany_alarm_phone_number p "
				+ "where p.alarm_phone_number = a.phone and p.company_id=:companyId and a.send_time between :from and :to";
		Object result = session.createSQLQuery(sql).setLong("companyId", company.getId()).setDate("from", from).setDate("to", to).uniqueResult();

		model.put("count", result);
		return "alarm_sms_stat_result";
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
