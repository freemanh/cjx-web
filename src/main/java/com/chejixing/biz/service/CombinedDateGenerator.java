package com.chejixing.biz.service;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.springframework.stereotype.Component;

@Component
public class CombinedDateGenerator {

	public CombinedDateGenerator() {
		super();
	}
	
	public Date generate(Date baseDatetime, Date newDate, int newHour){
		DateTime wdt = new DateTime(newDate);
		
		DateTime originalDateTime = new DateTime(baseDatetime);
		MutableDateTime mutableDateTime = originalDateTime.toMutableDateTime();
		mutableDateTime.setDate(wdt.getMillis());
		mutableDateTime.setHourOfDay(newHour);
		
		return mutableDateTime.toDate();
	}
	
}
