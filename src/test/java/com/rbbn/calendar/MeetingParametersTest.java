package com.rbbn.calendar;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class MeetingParametersTest {

	@Test
	void getFirstMeetingDate() {
		MeetingParameters meetingParameters = new MeetingParameters(LocalDate.parse("2020-05-04"),
				LocalDate.parse("2020-05-07"), DayOfWeek.valueOf("Wednesday".toUpperCase()));
		boolean x = LocalDate.parse("2020-05-06").equals(meetingParameters.getFirstMeetingDate());
		assertTrue(x, "first meeting date is 2020-05-06");
	}

	@Test
	void getLastMeetingDate() {
		MeetingParameters meetingParameters = new MeetingParameters(LocalDate.parse("2020-05-04"),
				LocalDate.parse("2020-05-07"), DayOfWeek.valueOf("Wednesday".toUpperCase()));
		boolean x = LocalDate.parse("2020-05-06").equals(meetingParameters.getLastMeetingDate());
		assertTrue(x, "last meeting date is 2020-05-06");
	}

}
