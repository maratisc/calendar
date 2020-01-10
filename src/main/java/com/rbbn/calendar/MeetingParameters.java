package com.rbbn.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * 
 * Holds parameters specifying a meeting or a portion of a meeting.
 * 
 */
public class MeetingParameters {
	
	private LocalDate startDate;
	private LocalDate endDate;
	private DayOfWeek dayOfWeek;
	
	/**
	 * 
	 * @param startDate start date
	 * @param endDate end date
	 * @param dayOfWeek day of the week
	 * 
	 */
	public MeetingParameters(LocalDate startDate, LocalDate endDate, DayOfWeek dayOfWeek) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.dayOfWeek = dayOfWeek;
	}
	
	/**
	 * 
	 * @return the first meeting date 
	 */
	public LocalDate getFirstMeetingDate() {
		// get the number of days between the first meeting date and the provided start date
		long daysFromStartDateToFirstMeetingDay = dayOfWeek.getValue() - startDate.getDayOfWeek().getValue();
		// make sure the value is in the range 1-7
		daysFromStartDateToFirstMeetingDay = (daysFromStartDateToFirstMeetingDay + 7) % 7;
		return startDate.plusDays(daysFromStartDateToFirstMeetingDay);
	}
	
	/**
	 * 
	 * @return the last meeting date 
	 */
	public LocalDate getLastMeetingDate() {
		// get the number of days between the last meeting date and the provided end date
		long daysFromLastMeetingDayToEndDate = endDate.getDayOfWeek().getValue() - dayOfWeek.getValue();
		// make sure the value is in the range 1-7
		daysFromLastMeetingDayToEndDate = (daysFromLastMeetingDayToEndDate + 7) % 7;
		return endDate.minusDays(daysFromLastMeetingDayToEndDate);
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
	
	@Override
	public String toString() {
		return startDate + " - " + endDate + " - " + dayOfWeek;
	}
		
}
