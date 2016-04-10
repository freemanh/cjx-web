package com.chejixing.biz.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AlarmCreationVoterTest {
	private AlarmCreationVoter voter;

	@Before
	public void before() {
		voter = new AlarmCreationVoter();
	}

	@Test
	public void testAlarmCreation() {

		assertTrue(voter.shouldCreateAlarm(true, 0));
		assertFalse(voter.shouldCreateAlarm(true, 1));
		assertFalse(voter.shouldCreateAlarm(true, 1));
		assertFalse(voter.shouldCreateAlarm(false, 0));
		assertFalse(voter.shouldCreateAlarm(false, 1));
	}

	@Test
	public void testPowerOfferAlarmCreation() {
		assertTrue(voter.shouldCreatePowerOffAlarm(true, true, 0));
		assertFalse(voter.shouldCreatePowerOffAlarm(false, true, 0));
		assertFalse(voter.shouldCreatePowerOffAlarm(true, false, 0));
		assertFalse(voter.shouldCreatePowerOffAlarm(false, false, 0));
		assertFalse(voter.shouldCreatePowerOffAlarm(true, true, 1));
		assertFalse(voter.shouldCreatePowerOffAlarm(true, true, 2));
		assertFalse(voter.shouldCreatePowerOffAlarm(false, true, 1));
	}
}
