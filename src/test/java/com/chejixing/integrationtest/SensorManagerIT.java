package com.chejixing.integrationtest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.chejixing.biz.bean.DeviceType;
import com.chejixing.biz.bean.SensorConfig;
import com.chejixing.biz.service.SensorManager;

public class SensorManagerIT extends BaseSpringIT {
	private static final String DEVICE_ID = "015098884572";
	private SensorManager man;

	@Test
	@Transactional
	public void testIsSynced() {
		boolean synced = man.isSynced(DEVICE_ID, 0, DeviceType.JIUHE);
		assertTrue(synced);

		jdbcTemplate.update("update xsensor set is_synced=false");
		synced = man.isSynced(DEVICE_ID, 0, DeviceType.JIUHE);
		assertFalse(synced);
	}

	@Test
	@Transactional
	public void testUpdateSync() {
		jdbcTemplate.update("update xsensor set is_synced=false");
		man.updateSynced(DEVICE_ID, 0, DeviceType.JIUHE);
		int count = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "xsensor", "is_synced=true");
		assertEquals(1, count);
	}
	
	@Test
	@Transactional
	public void testGetConfig(){
		SensorConfig config = man.getConfig(DEVICE_ID, 0, DeviceType.JIUHE);
		assertNotNull(config);
	}

	@Autowired
	public void setMan(SensorManager man) {
		this.man = man;
	}

}
