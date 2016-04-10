package com.chejixing.web;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chejixing.biz.bean.XCompany;
import com.chejixing.biz.service.UserService;

@Controller
public class AlarmPhoneController {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserService userService;

	@RequestMapping("/alarm_phone")
	@Transactional(readOnly=true)
	public String execute(ModelMap model, Principal principal){
		String username = principal.getName();
		System.out.println("username:" + username);
		XCompany company = userService.getCompanyByUsername(username);
		Hibernate.initialize(company.getAlarmPhoneNumbers());
		
		model.put("phones", company.getAlarmPhoneNumbers());
		
		return "alarm_phone";
				
	}

    //保存短信号码设置
    @RequestMapping(value  ="/save_alarm_phone",method = RequestMethod.POST)
    @Transactional(readOnly = false)
    @ResponseBody
    public  String saveAlarmPhone(@RequestParam(value="phones[]",required = false) String[] phones
    ){
        Map result = new HashMap();
        if(phones==null){
           return null;
        }
        //todo:临时做法，需改为与显示的company一致
        Session s = sessionFactory.getCurrentSession();
        XCompany company = (XCompany) s.get(XCompany.class, 1l);
        Set<String> ncc = company.getAlarmPhoneNumbers();
        ncc = new HashSet<>(Arrays.asList(phones));
        company.setAlarmPhoneNumbers(ncc);
        s.saveOrUpdate(company);
        s.flush();


        result.put("result","ok");
        return "save_alarm_phone";
    }

    @ExceptionHandler(Exception.class)
    public Exception onException(Exception e){
         e.printStackTrace();
        return e;
    }
}
