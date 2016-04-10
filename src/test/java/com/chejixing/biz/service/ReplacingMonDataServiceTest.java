package com.chejixing.biz.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.chejixing.biz.bean.XMonData;
import com.chejixing.dao.DeviceDao;
import com.chejixing.dao.MonDataDao;

public class ReplacingMonDataServiceTest extends BaseDataSetup{
	@Test
	public void test() {
		// given
		this.setupData();
		
		Date wrongDate = new Date();
		int wrongHour = 1;
		int wrongSensorIndex = this.device1.getSensors().size() - 1;
		Date rightDate = new Date();
		int rightHour = 2;
		int rightSensorIndex = this.device2.getSensors().size() - 1;

		MonDataDao monDataDao = mock(MonDataDao.class);
		
		List<XMonData> wrongDatas = setupWrongDatas(wrongDate, wrongSensorIndex);
		when(monDataDao.query(this.sensorOfDevice1.getId(), wrongDate, wrongHour)).thenReturn(wrongDatas);
		
		List<XMonData> rightDatas = setupRightDatas(rightSensorIndex);
		when(monDataDao.query(this.sensorOfDevice2.getId(), rightDate, rightHour)).thenReturn(rightDatas);
		
		DeviceDao deviceDao = mock(DeviceDao.class);
		when(deviceDao.getByCode(eq(device1.getCode()))).thenReturn(this.device1);
		when(deviceDao.getByCode(eq(device2.getCode()))).thenReturn(this.device2);
		
		CombinedDateGenerator dateGenerator = mock(CombinedDateGenerator.class);

		ReplacingMonDataService service = new ReplacingMonDataService();
		service.setMonDataDao(monDataDao);
		service.setDeviceDao(deviceDao);
		service.setDateGenerator(dateGenerator);
		
		// when
		service.replace(wrongDate, wrongHour, device1.getCode(),
				wrongSensorIndex, rightDate, rightHour, device2.getCode(),
				rightSensorIndex);

		// then
		verify(monDataDao).delete(eq(wrongDatas.get(0)));
		verify(dateGenerator, times(2)).generate(any(Date.class), eq(wrongDate), eq(wrongHour));

		ArgumentCaptor<XMonData> captor = ArgumentCaptor.forClass(XMonData.class);
		verify(monDataDao, times(2)).save(captor.capture());
		
		assertEquals(2, captor.getAllValues().size());
		int i = 0;
		for(XMonData data: captor.getAllValues()){
			XMonData rightData = rightDatas.get(i);
			
			assertEquals(device1.getCode(), data.getDeviceCode());
			assertEquals(rightData.getTemperature(), data.getTemperature(), 0.1);
			assertEquals(rightData.getHumidity(), data.getHumidity(), 0.1);
			assertEquals(wrongSensorIndex, data.getSensorIndex());
			
			i++;
		}
	}

	public List<XMonData> setupRightDatas(int rightSensorIndex) {
		List<XMonData> rightDatas = new ArrayList<XMonData>();
		XMonData rightData1 = new XMonData(2l, this.device2.getCode(), rightSensorIndex, 11.0, 22.0, new Date());
		XMonData rightData2 = new XMonData(3l, this.device2.getCode(), rightSensorIndex, 12.0, 24.0, new Date());
		
		rightDatas.add(rightData1);
		rightDatas.add(rightData2);
		return rightDatas;
	}

	public List<XMonData> setupWrongDatas(Date wrongDate, int wrongSensorIndex) {
		List<XMonData> wrongDatas = new ArrayList<XMonData>();
		XMonData wrongData = new XMonData(1l, this.device1.getCode(), wrongSensorIndex, 10.0, 20.0, wrongDate);
		wrongDatas.add(wrongData);
		return wrongDatas;
	}
}
