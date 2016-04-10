package com.chejixing.socket;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chejixing.biz.bean.DeviceType;
import com.chejixing.biz.bean.SensorConfig;
import com.chejixing.biz.service.MonDataService;
import com.chejixing.biz.service.SensorManager;
import com.chejixing.socket.message.JingsuDeviceMessage;
import com.chejixing.socket.message.JingsuSettingCommand;
import com.chejixing.socket.message.JingsuSettingResponse;
import com.chejixing.socket.message.JingsuSettingType;

@Component
public class JingsuMessageHandler extends IoHandlerAdapter {
	protected static final String KEY_DEVICE_ID = "deviceId";
	private static Logger logger = LoggerFactory.getLogger(JingsuMessageHandler.class);
	private MonDataService monDataService;
	private SensorManager sensorManager;

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		session.write("ALLSU");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);

	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		super.sessionIdle(session, status);
		session.close(false);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if (message instanceof JingsuDeviceMessage) {
			logger.debug("The received JingSu message:{}", message);

			JingsuDeviceMessage msg = (JingsuDeviceMessage) message;
			monDataService.add(msg.getDeviceId(), msg.getTemp(), msg.getHum(), msg.getDate(), msg.isPoweroff());
			if (msg.getFailCount() == 0) {
				session.setAttribute(KEY_DEVICE_ID, msg.getDeviceId());
				boolean synced = sensorManager.isSynced(msg.getDeviceId(), 0, DeviceType.JINGSU);
				if (synced) {
					close(session);
				} else {
					sync(session, msg);
				}
			} else {
				session.write("CSV" + (msg.getFailCount() - 1));
			}

		} else if (message instanceof JingsuSettingResponse) {
			processSyncResponse(session);
		} else {
			throw new IllegalArgumentException("Unknow how to handle this kind of message:" + message.getClass());
		}
	}

	private void close(IoSession session) {
		logger.debug("Prepareing to send RECEIVE back to device to close the socket session.");
		session.write("RECEIVE");
		session.close(false);
	}

	private void sync(IoSession session, JingsuDeviceMessage msg) {
		logger.debug("The latest sensor config need to be synced, sending relative messages...");
		SensorConfig config = sensorManager.getConfig(msg.getDeviceId(), 0, DeviceType.JINGSU);
		session.write(new JingsuSettingCommand(JingsuSettingType.TEMP, (short) (config.getTempRevision() * 10)));
		logger.debug("The temp sync command has been sent.");

		Queue<JingsuSettingCommand> commands = new ArrayBlockingQueue<JingsuSettingCommand>(3);
		SensorConfig secondSensorConfig = sensorManager.getConfig(msg.getDeviceId(), 1, DeviceType.JINGSU);
		if (null == secondSensorConfig) {
			commands.add(new JingsuSettingCommand(JingsuSettingType.HUMIDITY, (short) (config.getHumRevision() * 10)));
		} else {
			commands.add(new JingsuSettingCommand(JingsuSettingType.HUMIDITY, (short) (secondSensorConfig.getTempRevision() * 10)));
		}

		commands.add(new JingsuSettingCommand(JingsuSettingType.FREQUECE, (short) config.getUploadFrequency()));
		commands.add(new JingsuSettingCommand(JingsuSettingType.ALARM_MODE, (short) config.getAlarmMode()));

		logger.debug("The command queue is:{}", commands);
		session.setAttribute("commands", commands);
		logger.debug("The next two sync command is saved into queue, waiting for first command response.");
	}

	private void processSyncResponse(IoSession session) {
		logger.info("Starting to process the sync response messages...");
		@SuppressWarnings("unchecked")
		Queue<JingsuSettingCommand> commands = (Queue<JingsuSettingCommand>) session.getAttribute("commands");
		logger.debug("The commands queue is:{}", commands);
		JingsuSettingCommand cmd = commands.poll();
		logger.debug("The next command to sync is:{}", cmd);
		if (null != cmd) {
			session.write(cmd);
		} else {
			close(session);
			sensorManager.updateSynced(session.getAttribute(KEY_DEVICE_ID).toString(), 0, DeviceType.JINGSU);
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("Socket exception caught:", cause);
		session.close(true);
	}

	@Autowired
	public void setMonDataService(MonDataService monDataService) {
		this.monDataService = monDataService;
	}

	@Autowired
	public void setSensorManager(SensorManager sensorManager) {
		this.sensorManager = sensorManager;
	}

}
