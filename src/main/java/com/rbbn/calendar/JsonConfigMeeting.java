package com.rbbn.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Accepts JSON input specifying meeting parameters. Use in situations where a
 * meeting information is entered in a form/page and it can be captured as JSON.
 * See {@link #setupMeetingParametersList(String)} and
 * {@link #setupVacationAndHolidays(String)} for the required JSON format
 * details.
 * 
 * TODO: Allow specifying number of meetings required in lieu of the end date?
 *
 */
public class JsonConfigMeeting implements IMeetingOccurrences {

	private MeetingOccurrences meetingOccurrences;

	public JsonConfigMeeting() {
		meetingOccurrences = new MeetingOccurrences();
	}

	/**
	 * 
	 * @param json JSON string with meeting parameters, e.g., [{"startDate":
	 *             "2020-05-04", "endDate": "2020-05-04", "meetingDays":
	 *             ["Wednesday"]}, {"startDate": "2021-01-01", "endDate":
	 *             "2021-12-31", "meetingDays": ["Thursday"]}]
	 * 
	 */
	public void setupMeetingParametersList(String json) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> jsonList = mapper.readValue(json, List.class);
		List<MeetingParameters> meetingParametersList = new ArrayList<>();
		jsonList.forEach(inputLine -> {
			List<String> meetingDaysList = (List<String>) inputLine.get("meetingDays");
			// create an instance of MeetingParameters for each meetingDay in the meetingDays list
			meetingDaysList.forEach(meetingDay -> meetingParametersList
					.add(new MeetingParameters(LocalDate.parse((CharSequence) inputLine.get("startDate")),
							LocalDate.parse((CharSequence) inputLine.get("endDate")),
							DayOfWeek.valueOf(meetingDay.toUpperCase()))));
		});
		meetingOccurrences.setMeetingParametersList(meetingParametersList);
	}

	/**
	 * 
	 * @param json The JSON string with a list of comma-separated dates, e.g.,:
	 *             ["2020-05-20","2020-05-21","2020-05-22","2020-05-25","2020-09-07","2020-11-26",
	 *             "2020-11-27","2020-12-25","2021-01-18","2021-05-31","2021-09-06","2021-11-25",
	 *             "2021-11-26","2021-12-25"]"
	 * 
	 */
	public void setupVacationAndHolidays(String json) throws Exception {
		List<LocalDate> vacationAndHolidaysList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		List<String> jsonList = mapper.readValue(json, List.class);
		jsonList.forEach(value -> vacationAndHolidaysList.add(LocalDate.parse(value)));
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