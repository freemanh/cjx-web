package com.chejixing.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chejixing.biz.service.ReplacingMonDataService;

@Controller
public class DataController {
	private ReplacingMonDataService service;
	
	@RequestMapping("/replace")
	public String replace(
			@RequestParam Date wrongDate,
			@RequestParam(required=false) Integer wrongHour,
			@RequestParam String wrongDeviceCode,
			@RequestParam short wrongSensorIndex,
			@RequestParam Date rightDate,
			@RequestParam(required=false) Integer rightHour,
			@RequestParam String rightDeviceCode,
			@RequestParam short rightSensorIndex
			){
		if(null == wrongHour || null == rightHour){
			service.replace(wrongDate, wrongDeviceCode, wrongSensorIndex, rightDate, rightDeviceCode, rightSensorIndex);
		}else{
			service.replace(wrongDate, wrongHour, wrongDeviceCode, wrongSensorIndex, rightDate, rightHour, rightDeviceCode, rightSensorIndex);
		}
		return "close_win";
	}

	@Autowired
	public void setService(ReplacingMonDataService service) {
		this.service = service;
	}
}
