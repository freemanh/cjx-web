package com.chejixing.util;

import com.chejixing.biz.bean.DeviceType;

public class DeviceCodeUtils {
	public static String getCode(String deviceId, DeviceType type) {
		switch (type) {
		case JINGSU:
			return "Jingsu" + deviceId;
		case JIUHE:
			return deviceId;
		case LANGNUO:
			return "Langnuo" + deviceId;
		default:
			throw new IllegalArgumentException("Unknown device type:" + type);
		}
	}
}
