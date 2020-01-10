package com.rbbn.calendar;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class JsonConfigMeetingTest {
	
	private static final String JSON_MEETING_DATES = "[{\"startDate\": \"2020-05-04\"," + 
														"\"endDate\": \"2020-12-31\"," + 
															"\"meetingDays\": [\"Wednesday\"]}," + 
															"{\"startDate\": \"2021-01-01\"," + 
															"\"endDate\": \"2021-12-31\"," + 
															"\"meetingDays\": [\"Thursday\"]}]";
	private static final String JSON_VACATION_HOLIDAY_DATES = "[\"2020-05-20\",\"2020-05-21\",\"2020-05-22\",\"2020-05-25\",\"2020-09-07\",\"2020-11-26\",\"2020-11-27\",\"2020-12-25\",\"2021-01-18\",\"2021-05-31\",\"2021-09-06\",\"2021-11-25\",\"2021-11-26\",\"2021-12-25\"]";

	private JsonConfigMeeting meeting;
	
	@BeforeEach
	void setup() throws Exception {
		meeting = new JsonConfigMeeting();
		meeting.setupMeetingParametersList(JSON_MEETING_DATES);
		meeting.setupVacationAndHolidays(JSON_VACATION_HOLIDAY_DATES);
	}

	@Test
	void getMeetingParametersList() {
		int count = meeting.getMeetingOccurrences().getMeetingParametersList().size();
		assertEquals(2, count);
	}
	
	@Test
	void getVacationAndHolidaysList() {
		int count = meeting.getMeetingOccurrences().getVacationAndHolidaysList().size();
		assertEquals(14, count);
	}
	
	@Test
	void getOccurrencesCount0() {
		int count = meeting.getOccurrencesCount(LocalDate.parse("2020-05-05"));
		assertEquals(0, count);
	}
	
	@Test
	void getOccurrencesCount1() {
		int count = meeting.getOccurrencesCount(LocalDate.parse("2020-05-06"));
		assertEquals(1, count);
	}
	
	@Test
	void getOccurrencesCount() {
		int count = meeting.getOccurrencesCount(LocalDate.parse("2020-12-31"));
		assertEquals(34, count);
	}
	
	@Test
	void getTotalOccurrences() {
		int count = meeting.getTotalOccurrences();
		assertEquals(85, count);
	}
	
	@Test
	void testByIteration() {
		LocalDate startDate = LocalDate.parse("2020-05-06");
		LocalDate endDate = LocalDate.parse("2021-12-30");
		LocalDate testStartDate = LocalDate.parse("2020-01-01");
		LocalDate testEndDate = LocalDate.parse("2022-02-02");
		LocalDate testDate = testStartDate;
		int prevCount = 0;
		while (testDate.isBefore(testEndDate)) {
//			System.out.println("testDate: " + testDate);
//			System.out.println("prevCount: " + prevCount);
			int count = meeting.getOccurrencesCount(testDate);
//			System.out.println("count: " + count);
			if (testDate.isBefore(startDate)) {
				assertEquals(0, count);
			}
			else if (testDate.isBefore(endDate) || testDate.equals(endDate)) {
				// vacation days at 2 and 80
				// at 34 we switch to Thursday from Wednesday, this is a week without a meeting
				if (prevCount != 2 && prevCount != 34 && prevCount != 80) assertEquals(prevCount + 1, count);
			}
			else {
				assertEquals(meeting.getTotalOccurrences(), count);
			}
			prevCount = count;
			testDate = testDate.plusWeeks(1);
		}
	}

	@Test
	void testByIterationWithoutVacation() throws Exception {
		// use a meeting without vacation or holidays
		meeting = new JsonConfigMeeting();
		meeting.setupMeetingParametersList(JSON_MEETING_DATES);

		LocalDate startDate = LocalDate.parse("2020-05-06");
		LocalDate endDate = LocalDate.parse("2021-12-30");
		LocalDate testStartDate = LocalDate.parse("2020-01-01");
		LocalDate testEndDate = LocalDate.parse("2022-02-02");
		LocalDate testDate = testStartDate;
		int prevCount = 0;
		while (testDate.isBefore(testEndDate)) {
//			System.out.println("testDate: " + testDate);
//			System.out.println("prevCount: " + prevCount);
			int count = meeting.getOccurrencesCount(testDate);
//			System.out.println("count: " + count);
			if (testDate.isBefore(startDate)) {
				assertEquals(0, count);
			}
			else if (testDate.isBefore(endDate) || testDate.equals(endDate)) {
				// at 35 we switch to Thursday from Wednesday, this is a week without a meeting
				if (prevCount != 35) assertEquals(prevCount + 1, count);
			}
			else {
				assertEquals(meeting.getTotalOccurrences(), count);
			}
			prevCount = count;
			testDate = testDate.plusWeeks(1);
		}
	}

}