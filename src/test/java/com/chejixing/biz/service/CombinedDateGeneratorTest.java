package com.chejixing.biz.service;

import static org.junit.Assert.*;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

public class CombinedDateGeneratorTest {
	@Test
	public void test() {
		//given
		DateTime newDate = new DateTime(2012, 1, 1, 0, 0);
		Date baseDatetime = new Date();
		int newHour = 13;
		
		CombinedDateGenerator g = new CombinedDateGenerator();
		
		// when
		Date combinedDate = g.generate(baseDatetime, newDate.toDate(), newHour);

		// then
		DateTime baseWrapper = new DateTime(baseDatetime);
		DateTime combinedWrapper = new DateTime(combinedDate);
		
		assertEquals(combinedWrapper.getYear(), newDate.getYear());
		assertEquals(combinedWrapper.getMonthOfYear(), newDate.getMonthOfYear());
		assertEquals(combinedWrapper.getDayOfMonth(), newDate.getDayOfMonth());
		assertEquals(combinedWrapper.getHourOfDay(), newHour);
		assertEquals(combinedWrapper.getMinuteOfHour(), baseWrapper.getMinuteOfHour());
		assertEquals(combinedWrapper.getSecondOfMinute(), baseWrapper.getSecondOfMinute());
		
	}
}
