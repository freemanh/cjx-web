package com.chejixing.integrationtest;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.chejixing.biz.service.MonDataService;
import com.chejixing.dao.MonDataDao;

public class MonDataDaoIT extends BaseSpringIT {
	@Autowired
	MonDataDao dao;
	@Autowired
	MonDataService service;

	@Test
	@Transactional
	public void testQuery() {
		// when
		service.add("015098884572", 40.0, 20.0, new Date(), true);

		@SuppressWarnings("rawtypes")
		List result = dao.query(1l, new Date(), Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		assertEquals(1, result.size());
	}
}
