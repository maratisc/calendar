package com.rbbn.calendar;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class CSVConfigMeetingTest {
	
	private static final String INPUT_MEETING_DATES_FILE_PATH = "/Projects/RaytheonBBNCalendar/RaytheonBBNCalendar/input/meetingDates.csv";
	private static final String INPUT_VACATION_HOLIDAY_DATES_FILE_PATH = "/Projects/RaytheonBBNCalendar/RaytheonBBNCalendar/input/vacationAndHolidayDates.csv";

	private CSVConfigMeeting meeting;
	
	@BeforeEach
	void setup() throws Exception {
		meeting = new CSVConfigMeeting();
		meeting.setupMeetingParametersList(INPUT_MEETING_DATES_FILE_PATH);
		meeting.setupVacationAndHolidays(INPUT_VACATION_HOLIDAY_DATES_FILE_PATH);
	}

	@Test
	void getMeetingParametersList() throws FileNotFoundException, IOException {
		int count = meeting.getMeetingOccurrences().getMeetingParametersList().size();
		assertEquals(2, count);
	}
	
	@Test
	void getVacationAndHolidaysList() throws FileNotFoundException, IOException {
		int count = meeting.getMeetingOccurrences().getVacationAndHolidaysList().size();
		assertEquals(14, count);
	}
	
	@Test
	void getOccurrencesCount0() throws FileNotFoundException, IOException {
		int count = meeting.getOccurrencesCount(LocalDate.parse("2020-05-05"));
		assertEquals(0, count);
	}
	
	@Test
	void getOccurrencesCount1() throws FileNotFoundException, IOException {
		int count = meeting.getOccurrencesCount(LocalDate.parse("2020-05-06"));
		assertEquals(1, count);
	}
	
	@Test
	void getOccurrencesCount() throws FileNotFoundException, IOException {
		int count = meeting.getOccurrencesCount(LocalDate.parse("2020-12-31"));
		assertEquals(34, count);
	}
	
	@Test
	void getTotalOccurrences() throws FileNotFoundException, IOException {
		int count = meeting.getTotalOccurrences();
		assertEquals(85, count);
	}
	
	@Test
	void testByIteration() throws FileNotFoundException, IOException {
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
		meeting = new CSVConfigMeeting();
		meeting.setupMeetingParametersList(INPUT_MEETING_DATES_FILE_PATH);

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