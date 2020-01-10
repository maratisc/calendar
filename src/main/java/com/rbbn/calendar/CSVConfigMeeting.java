package com.rbbn.calendar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Represents a portion of the requirements for a Calendar/Scheduling application. Creates a meeting
 * based on a meeting parameters provided in a CSV file. See {@link #setupMeetingParametersList(String)}
 * Optionally, a CSV file listing dates for vacation and holidays can also be provided. See {@link #setupVacationAndHolidays(String)}
 * When provided meetings will not be set on these dates.
 * 
 */
public class CSVConfigMeeting implements IMeetingOccurrences {

	private static final String COMMA_DELIMITER = ",";
	private MeetingOccurrences meetingOccurrences;
	
	public CSVConfigMeeting() {
		meetingOccurrences = new MeetingOccurrences();
	}
	
	/**
	 * 
	 * @param meetingDatesFilePath        The location of the input csv file that
	 *                                    defines the meeting. The file is expected
	 *                                    in this format: 
	 *                                    # meetingDates.csv 
	 *                                    # start, end, day of week 
	 *                                    2020-05-04, 2020-12-31, Wednesday
	 *                                    2021-01-01, 2021-12-31, Thursday
	 * 
	 */
	public void setupMeetingParametersList(String meetingDatesFilePath) throws Exception {
		List<List<String>> inputLineList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(meetingDatesFilePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				// ignore lines that have comment prefix, '#'
				if (line.contains("#")) continue;
				String[] values = line.split(COMMA_DELIMITER);
				inputLineList.add(Arrays.asList(values));
			}
		}
		
		List<MeetingParameters> meetingParametersList = new ArrayList<>();
		inputLineList.forEach(inputLine -> meetingParametersList.add(new MeetingParameters(LocalDate.parse(inputLine.get(0).trim()),
				LocalDate.parse(inputLine.get(1).trim()), DayOfWeek.valueOf(inputLine.get(2).trim().toUpperCase()))));
		meetingOccurrences.setMeetingParametersList(meetingParametersList);
	}

	/**
	 * 
	 * @param vacationAndHolidayDatesFilePath The location of file that has
	 *                                    comma-separated dates that are either
	 *                                    vacation days or holidays. The file is expected
	 *                                    in this format:
	 *                                    2020-05-20,2020-05-21,2020-05-22,2020-05-25,2020-09-07,2020-11-26,2020-11-27,2020-12-25
	 *                                    
	 */
	public void setupVacationAndHolidays(String vacationAndHolidayDatesFilePath) throws Exception {
		List<LocalDate> vacationAndHolidaysList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(vacationAndHolidayDatesFilePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				// ignore lines that have comment prefix, '#'
				if (line.contains("#")) continue;
				String[] values = line.split(COMMA_DELIMITER);
				Arrays.asList(values).forEach(value -> vacationAndHolidaysList.add(LocalDate.parse(value)));
			}
		}
		meetingOccurrences.setVacationAndHolidaysList(vacationAndHolidaysList);
	}

	@Override
	public int getOccurrencesCount(LocalDate uptoDate) {
		return meetingOccurrences.getOccurrencesCount(uptoDate);
	}

	@Override
	public int getTotalOccurrences() {
		return meetingOccurrences.getTotalOccurrences();
	}
	
	public MeetingOccurrences getMeetingOccurrences() {
		return meetingOccurrences;
	}

}