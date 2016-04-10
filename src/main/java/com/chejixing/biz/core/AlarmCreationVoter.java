package com.chejixing.biz.core;

import org.springframework.stereotype.Component;

@Component
public class AlarmCreationVoter {
	public boolean shouldCreateAlarm(boolean over, int count) {
		return over && count == 0;
	}

	public boolean shouldCreatePowerOffAlarm(boolean supportPowerOff, boolean powerOff, int count) {
		return supportPowerOff && powerOff && count == 0;
	}
}
