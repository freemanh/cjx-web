package com.chejixing.web;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.*;
import org.hibernate.type.IntegerType;
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
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@Controller
public class alarm_query_for_deleteController {
    @Autowired
    private SessionFactory sessionFactory;

    private UserService userService;

    @RequestMapping(value = "/alarm_query_for_deleteTrigger")
    public String alarm_query_for_deleteTrigger(ModelMap model, Principal p) {
            return "alarm_query_for_delete";
    }

    @RequestMapping(value = "/alarm_query_for_deleteResult",method=RequestMethod.POST)
    @Transactional(readOnly = true)
    @ResponseBody
    public Map alarm_query_for_deleteResult(
        @RequestParam("sEcho") String echo,
        @RequestParam("iDisplayStart") int from
        , @RequestParam("iDisplayLength") int maxCount
        , HttpServletRequest request, Principal p) {
        Session session = this.sessionFactory.getCurrentSession();
        XCompany company = userService.getCompanyByUsername(p.getName());

        //某些传递的参数需要传递回去
        Map parameterMap = request.getParameterMap();
        Map result = new HashMap();
        result.put("sEcho",echo);

        String fromClause = "from Alarm where device.company=:company order by createTime desc";

        // 先查总条数
        Query query = session.createQuery("select count(*) as count " + fromClause);
        query.setParameter("company", company);
        Number count = (Number) query.uniqueResult();

        query =  session.createQuery( fromClause);
        query.setParameter("company", company);

        query.setFirstResult(from);
        query.setMaxResults(maxCount);

        // key的名称仅仅适用于jquery dataTables
        result.put("iTotalDisplayRecords", count.intValue());
        List<Alarm> alarms = query.list();
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        for (Alarm alarm : alarms) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", alarm.getId());
             map.put("desc", alarm.getDescp());
            map.put("grade",alarm.getGrade());
            map.put("createTime", alarm.getCreateTime());
            map.put("clearTime", alarm.getClearTime());

            results.add(map);
        }
        result.put("data", results);
        result.put("iTotalRecords", alarms.size());

        return result;
    }

    @RequestMapping(value = "/AlarmDelete",method=RequestMethod.POST)
    @Transactional
    @ResponseBody
    public Map AlarmDelete(
            @RequestParam("ids[]") String[] ids,
           HttpServletRequest request, Principal p) {
        Session session = this.sessionFactory.getCurrentSession();

        List<Long> idList = new ArrayList();
        for(String sid:ids){
            idList.add(Long.parseLong(sid));
        }
        String sql = "delete Alarm where id in (:ids)";
        Query query = session.createQuery(sql);
        query.setParameterList("ids",idList);
        int dels = query.executeUpdate();
        Map results = new HashMap();
        results.put("result",dels+"条记录被删除!");
        return results ;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
