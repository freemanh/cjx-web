package com.chejixing.integrationtest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.chejixing.biz.service.MonDataService;

public class MonDataServiceIT extends BaseSpringIT {
	private MonDataService monDataService;

	@Test
	@Transactional
	public void testOverTemp() {
		// when
		monDataService.add("015098884572", 50.1, 20.0, new Date(), false);

		// then
		int alarmCount = countRowsInTableWhere("alarm", "alarm_type=1");
		assertEquals(1, alarmCount);

		int dataCount = countRowsInTable("mondata_015098884572");
		assertEquals(1, dataCount);

		int textMsgCount = countRowsInTable("xtextmessage_audit");
		assertEquals(1, textMsgCount);
	}

	@Test
	@Transactional
	public void testOverHum() {
		// when
		monDataService.add("015098884572", 9.0, 8.0, new Date(), false);

		// then
		int alarmCount = countRowsInTableWhere("alarm", "alarm_type=2");
		assertEquals(1, alarmCount);

		int dataCount = countRowsInTable("mondata_015098884572");
		assertEquals(1, dataCount);

		int textMsgCount = countRowsInTable("xtextmessage_audit");
		assertEquals(1, textMsgCount);
	}

	@Test
	@Transactional
	public void testBothOver() {
		// when
		monDataService.add("015098884572", 50.1, 20.0, new Date(), false);
		monDataService.add("015098884572", 9.0, 8.0, new Date(), false);

		// then
		int alarmCount = countRowsInTable("alarm");
		assertEquals(2, alarmCount);

		int dataCount = countRowsInTable("mondata_015098884572");
		assertEquals(2, dataCount);

		int textMsgCount = countRowsInTable("xtextmessage_audit");
		assertEquals(2, textMsgCount);
	}

	@Test
	@Transactional
	public void testBackToNormal() {
		// when
		monDataService.add("015098884572", 50.1, 20.0, new Date(), false);
		monDataService.add("015098884572", 49.0, 20.0, new Date(), false);

		// then
		int alarmCount = countRowsInTable("alarm");
		assertEquals(1, alarmCount);

		int clearedAlarmCount = countRowsInTableWhere("alarm", "clearTime is not null");
		assertEquals(1, clearedAlarmCount);

		int dataCount = countRowsInTable("mondata_015098884572");
		assertEquals(2, dataCount);

		int textMsgCount = countRowsInTable("xtextmessage_audit");
		assertEquals(1, textMsgCount);
	}

	@Test
	@Transactional
	public void testPowerOff() {
		// when
		monDataService.add("015098884572", 40.0, 20.0, new Date(), true);

		// then
		int powerOffAlarmCount = countRowsInTableWhere("alarm", "alarm_type=0");
		assertEquals(1, powerOffAlarmCount);

		int dataCount = countRowsInTable("mondata_015098884572");
		assertEquals(1, dataCount);

		int textMsgCount = countRowsInTable("xtextmessage_audit");
		assertEquals(1, textMsgCount);
	}

	@Test
	@Transactional
	public void testPowerOffAndOverTemp() {
		// when
		monDataService.add("015098884572", 51.0, 20.0, new Date(), true);

		// then
		int powerOffAlarmCount = countRowsInTableWhere("alarm", "alarm_type=0");
		assertEquals(1, powerOffAlarmCount);

		int overHeatAlarmCount = countRowsInTableWhere("alarm", "alarm_type=1");
		assertEquals(1, overHeatAlarmCount);

		int textMsgCount = countRowsInTable("xtextmessage_audit");
		assertEquals(2, textMsgCount);
	}

	@Test
	@Transactional
	public void testDoubleSensor() {
		// when
		monDataService.add("015098884582", 51.0, 52.0, new Date(), false);

		// then
		int overHeatAlarmCount = countRowsInTable("alarm");
		assertEquals(2, overHeatAlarmCount);

		int dataCount = countRowsInTable("mondata_015098884582");
		assertEquals(2, dataCount);

		int textMsgCount = countRowsInTable("xtextmessage_audit");
		assertEquals(2, textMsgCount);
	}

	@Test
	@Transactional
	public void testAddMultiSensorData() {
		// given
		List<Double> temps = new ArrayList<Double>();
		temps.add(20.0);
		temps.add(22.0);
		temps.add(23.0);
		temps.add(24.0);

		List<Double> hums = new ArrayList<Double>();
		hums.add(10.0);
		hums.add(10.0);
		hums.add(10.0);
		hums.add(10.0);

		// when
		monDataService.add("015098884592", temps, hums, new Date(), false);

		// then
		int dataCount = countRowsInTable("mondata_015098884592");
		assertEquals(4, dataCount);
	}

	@Test
	@Transactional
	public void testAlwaysOverHeat() {
		// when
		monDataService.add("015098884572", 51.0, 10.0, new Date(), false);
		monDataService.add("015098884572", 52.0, 10.0, new Date(), false);

		// then
		int overHeatAlarmCount = countRowsInTable("alarm");
		assertEquals(1, overHeatAlarmCount);

		int dataCount = countRowsInTable("mondata_015098884572");
		assertEquals(2, dataCount);

		int textMsgCount = countRowsInTable("xtextmessage_audit");
		assertEquals(1, textMsgCount);
	}

	@Autowired
	public void setMonDataService(MonDataService monDataService) {
		this.monDataService = monDataService;
	}

}
