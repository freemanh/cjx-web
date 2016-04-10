package com.chejixing.socket;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.mina.core.session.IoSession;
import org.junit.Before;
import org.junit.Test;

import com.chejixing.biz.bean.DeviceType;
import com.chejixing.biz.bean.SensorConfig;
import com.chejixing.biz.service.MonDataService;
import com.chejixing.biz.service.SensorManager;
import com.chejixing.socket.message.JingsuDeviceMessage;
import com.chejixing.socket.message.JingsuSettingCommand;
import com.chejixing.socket.message.JingsuSettingResponse;
import com.chejixing.socket.message.JingsuSettingType;

public class JingsuMessageHandlerTest {
	private JingsuMessageHandler handler;
	private IoSession session;
	private Queue<JingsuSettingCommand> commands;
	private SensorManager sensorManager;
	private JingsuSettingResponse msg;

	@Before
	public void before() {
		handler = new JingsuMessageHandler();

		session = mock(IoSession.class);
		commands = new ArrayBlockingQueue<JingsuSettingCommand>(2);
		when(session.getAttribute("commands")).thenReturn(commands);
		when(session.getAttribute(JingsuMessageHandler.KEY_DEVICE_ID)).thenReturn("D001");

		sensorManager = mock(SensorManager.class);
		handler.setSensorManager(sensorManager);

		msg = new JingsuSettingResponse();
	}

	@Test
	public void testProcSyncRespWhenAllCommandsSent() throws Exception {
		// when
		handler.messageReceived(session, msg);

		// then
		verify(session).write("RECEIVE");
		verify(sensorManager).updateSynced(anyString(), anyInt(), any(DeviceType.class));
	}

	@Test
	public void testProcSyncRespWhenNotAllCommandsSent() throws Exception {
		// given
		JingsuSettingCommand cmd = new JingsuSettingCommand(JingsuSettingType.FREQUECE, (short) 1);
		commands.add(cmd);

		// when
		handler.messageReceived(session, msg);

		// then
		verify(session).write(cmd);
	}

	@Test
	public void testFirstSync() throws Exception {
		// given
		JingsuDeviceMessage msg = new JingsuDeviceMessage("D001", 10.0, 20.0, false, 0);

		when(sensorManager.isSynced(anyString(), anyInt(), any(DeviceType.class))).thenReturn(false);
		when(sensorManager.getConfig(anyString(), anyInt(), any(DeviceType.class))).thenReturn(new SensorConfig(10.0, 20.0, 1));
		
		MonDataService monDataService = mock(MonDataService.class);
		handler.setMonDataService(monDataService);

		// when
		handler.messageReceived(session, msg);

		// verify
		verify(session).write(any(JingsuSettingCommand.class));
		verify(session).setAttribute(eq("commands"), any(Queue.class));

	}
}
