package com.chejixing.biz.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.chejixing.biz.bean.DeviceType;
import com.chejixing.biz.bean.SensorConfig;

@Component
public class SensorManager {
	private JdbcTemplate jdbcTemp;

	public double getRevisedValue(double original, double revision) {
		return original + revision;
	}

	public boolean isOver(double value, double min, double max) {
		return (value < min || value > max) ? true : false;
	}

	public boolean isOver(double value, double revision, double min, double max) {
		return isOver(getRevisedValue(value, revision), min, max);
	}

	@Transactional
	public boolean isSynced(String deviceCode, int sensorIndex, DeviceType type) {
		return jdbcTemp.queryForObject("select xsensor.is_synced from xsensor join xdevice on xsensor.device_id=xdevice.id where xdevice.code=? and xsensor.sensor_index=?",
				Boolean.class, deviceCode, sensorIndex);
	}

	@Transactional
	public void updateSynced(String deviceCode, int sensorIndex, DeviceType type) {
		Long sensorId = jdbcTemp.queryForObject(
				"select sensor.id from xsensor as sensor join xdevice as dev on sensor.device_id=dev.id where dev.code=? and sensor.sensor_index=?", Long.class, deviceCode,
				sensorIndex);
		jdbcTemp.update("update xsensor set is_synced=true where id=?", sensorId);

	}

	@Transactional
	public SensorConfig getConfig(String deviceCode, int sensorIndex, DeviceType type) {
		SensorConfig config = jdbcTemp.query("select sensor.temp_revision as tempRevision, sensor.hum_revision as humRevision, sensor.upload_frequency as uploadFrequency"
				+ " from xsensor as sensor join xdevice as dev on sensor.device_id=dev.id" + " where dev.code=? and sensor.sensor_index=?", new ResultSetExtractor<SensorConfig>() {
			@Override
			public SensorConfig extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					SensorConfig config = new SensorConfig();
					config.setHumRevision(rs.getDouble("humRevision"));
					config.setTempRevision(rs.getDouble("tempRevision"));
					config.setUploadFrequency(rs.getInt("uploadFrequency"));
					return config;
				} else {
					return null;
				}

			}
		}, deviceCode, sensorIndex);

		return config;
	}

	@Autowired
	public void setJdbcTemp(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}

}
