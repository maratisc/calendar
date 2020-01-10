package com.rbbn.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * Represents a portion of the requirements for a Calendar/Scheduling application
 * 
 */
public class MeetingOccurrences implements IMeetingOccurrences {

	private List<MeetingParameters> meetingParametersList;
	private List<LocalDate> vacationAndHolidaysList;
	
	public int getOccurrencesCount(LocalDate uptoDate) {
		AtomicInteger count = new AtomicInteger(0);
		getMeetingParametersList().forEach(mp -> {
			// it's easier for the calculations if we work with the actual first and last meeting dates instead of the provided
			// start and end dates
			LocalDate firstMeetingDate = mp.getFirstMeetingDate();
			LocalDate lastMeetingDate = mp.getLastMeetingDate();

			// No occurrences if uptoDate is before the firstMeetingDate
			if (!uptoDate.isBefore(firstMeetingDate)) {
				// uptoDate is the same as the firstMeetingDate or after it
				LocalDate revisedUptoDate = uptoDate;
				if (!uptoDate.isBefore(mp.getEndDate())) {
					// uptoDate is the same as the lastMeetingDate or before it
					revisedUptoDate = lastMeetingDate;
				}
				LocalDate meetingDay = firstMeetingDate;
				while (!revisedUptoDate.isBefore(meetingDay)) {
					if (!getVacationAndHolidaysList().contains(meetingDay)) {
						count.getAndIncrement();
					}
					meetingDay = meetingDay.plusWeeks(1);
				}
			}
		});
		
		return count.get();
	}

	@Override
	public int getTotalOccurrences() {
		LocalDate lastMeetingDate = getMeetingParametersList().stream()
				.max((mp1, mp2) -> mp1.getLastMeetingDate().compareTo(mp2.getLastMeetingDate())).get().getLastMeetingDate();
		return getOccurrencesCount(lastMeetingDate);
	}
	
	public void setMeetingParametersList(List<MeetingParameters> meetingParametersList) {
		this.meetingParametersList = meetingParametersList;
	}
	
	public void setVacationAndHolidaysList(List<LocalDate> vacationAndHolidaysList) {
		this.vacationAndHolidaysList = vacationAndHolidaysList;
	}

	public List<MeetingParameters> getMeetingParametersList() {
		if (meetingParametersList == null) {
			meetingParametersList = new ArrayList<MeetingParameters>();
		}
		return meetingParametersList;
	}
	
	public List<LocalDate> getVacationAndHolidaysList() {
		if (vacationAndHolidaysList == null) {
			vacationAndHolidaysList = new ArrayList<LocalDate>();
		}
		return vacationAndHolidaysList;
	}

}