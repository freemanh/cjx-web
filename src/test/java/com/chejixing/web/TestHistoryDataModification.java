package com.chejixing.web;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.chejixing.biz.bean.XMonData;
import com.chejixing.dao.MonDataDao;

public class TestHistoryDataModification {
	@Test
	public void test(){
		// given
		MonDataDao monDataDaoMock = mock(MonDataDao.class);
		
		int wrongHour = 4;
		int rightHour = 3;
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, wrongHour);
		Date wrongDate1 = cal.getTime();
		cal.add(Calendar.MINUTE, 1);
		cal.set(Calendar.HOUR_OF_DAY, wrongHour);
		Date wrongDate2 = cal.getTime();
		
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, rightHour);
		Date rightDate1 = cal.getTime();
		cal.add(Calendar.MINUTE, 1);
		cal.set(Calendar.HOUR_OF_DAY, rightHour);
		Date rightDate2 = cal.getTime();
		
		List<XMonData> wrongDatas = new ArrayList<XMonData>();
		wrongDatas.add(new XMonData(1L, "d1", 1, 10.0, 20.0, wrongDate1));
		wrongDatas.add(new XMonData(2L, "d1", 1, 11.0, 22.0, wrongDate2));
		when(monDataDaoMock.query(1l, wrongDate1, 4)).thenReturn(wrongDatas);
		
		List<XMonData> rightDatas = new ArrayList<XMonData>();
		rightDatas.add(new XMonData(3L, "d2", 0, 9.0, 19.0, rightDate1));
		rightDatas.add(new XMonData(4L, "d2", 0, 8.0, 18.0, rightDate2));
		when(monDataDaoMock.query(0l, rightDate1, 3)).thenReturn(rightDatas);
		
		HistoryDataModificationController controller = new HistoryDataModificationController();
		controller.setMonDataDao(monDataDaoMock);
		
		// when
		controller.save(wrongDate1, rightDate1, 1l, 0l, wrongHour, rightHour);
		
		// then
		for(XMonData wrongData : wrongDatas){
			verify(monDataDaoMock).delete(eq(wrongData));
		}
		
		ArgumentCaptor<XMonData> captor = ArgumentCaptor.forClass(XMonData.class);
		verify(monDataDaoMock, atLeastOnce()).save(captor.capture());
		List<XMonData> newDatas = captor.getAllValues();
		
		
		for(int i = 0;i < rightDatas.size(); i++){
			XMonData rightData = rightDatas.get(i);
			XMonData newData = newDatas.get(i);
			
			assertNull(newData.getId());
			assertEquals(rightData.getTemperature(), newData.getTemperature(), 0.01);
			assertEquals(rightData.getHumidity(), newData.getHumidity(), 0.01);
			assertEquals("d1", newData.getDeviceCode());
			assertEquals(1, newData.getSensorIndex());
			
			DateFormat df = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
			String newDateString = df.format(newData.getCollectTime());
			String wrongDateString = df.format(wrongDate1);
			String rightDateString = df.format(rightData.getCollectTime());
			
			assertEquals(wrongDateString.substring(0, 11), newDateString.substring(0, 11));
			assertEquals(rightDateString.substring(11), newDateString.substring(11));
		}
		
	}
}
