package com.rbbn.calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MeetingOccurrencesTest {

	private MeetingOccurrences meeting;

	@BeforeEach
	void setup() throws FileNotFoundException, IOException {
		meeting = new MeetingOccurrences();
		setMeetingParametersList(meeting);
		setVacationAndHolidaysList(meeting);
	}
	
	void setMeetingParametersList(MeetingOccurrences mtng) {
		List<MeetingParameters> meetingParametersList = new ArrayList<>();
		MeetingParameters mp1 = new MeetingParameters(LocalDate.parse("2020-05-04"), LocalDate.parse("2020-12-31"),
				DayOfWeek.WEDNESDAY);
		meetingParametersList.add(mp1);
		MeetingParameters mp2 = new MeetingParameters(LocalDate.parse("2021-01-01"), LocalDate.parse("2021-12-31"),
				DayOfWeek.THURSDAY);
		meetingParametersList.add(mp2);
		mtng.setMeetingParametersList(meetingParametersList);
	}
	
	void setVacationAndHolidaysList(MeetingOccurrences mtng) {
		List<LocalDate> vacationAndHolidaysList = Arrays.asList(new LocalDate[]{LocalDate.parse("2020-05-20"), LocalDate.parse("2020-05-21"), 
												LocalDate.parse("2020-05-22"), LocalDate.parse("2020-05-25"), 
												LocalDate.parse("2020-09-07"), LocalDate.parse("2020-11-26"), 
												LocalDate.parse("2020-11-27"), LocalDate.parse("2020-12-25"),
												LocalDate.parse("2021-01-18"), LocalDate.parse("2021-05-31"), 
												LocalDate.parse("2021-09-06"), LocalDate.parse("2021-11-25"), 
												LocalDate.parse("2021-11-26"), LocalDate.parse("2021-12-25")});
		mtng.setVacationAndHolidaysList(vacationAndHolidaysList);
	}
	
	

	@Test
	void getMeetingParametersList() throws FileNotFoundException, IOException {
		int count = meeting.getMeetingParametersList().size();
		assertEquals(2, count);
	}

	@Test
	void getVacationAndHolidaysList() throws FileNotFoundException, IOException {
		int count = meeting.getVacationAndHolidaysList().size();
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
			} else if (testDate.isBefore(endDate) || testDate.equals(endDate)) {
				// vacation days at 2 and 80
				// at 34 we switch to Thursday from Wednesday, this is a week without a meeting
				if (prevCount != 2 && prevCount != 34 && prevCount != 80)
					assertEquals(prevCount + 1, count);
			} else {
				assertEquals(meeting.getTotalOccurrences(), count);
			}
			prevCount = count;
			testDate = testDate.plusWeeks(1);
		}
	}

	@Test
	void testByIterationWithoutVacation() throws FileNotFoundException, IOException {
		// use a meeting without vacation or holidays
		meeting = new MeetingOccurrences();
		setMeetingParametersList(meeting);

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
			} else if (testDate.isBefore(endDate) || testDate.equals(endDate)) {
				// at 35 we switch to Thursday from Wednesday, this is a week without a meeting
				if (prevCount != 35)
					assertEquals(prevCount + 1, count);
			} else {
				assertEquals(meeting.getTotalOccurrences(), count);
			}
			prevCount = count;
			testDate = testDate.plusWeeks(1);
		}
	}

}