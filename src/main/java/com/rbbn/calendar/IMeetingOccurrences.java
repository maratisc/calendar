package com.rbbn.calendar;

import java.time.LocalDate;

/**
 * 
 * Represents a portion of the requirements for a Calendar/Scheduling application
 * 
 */
public interface IMeetingOccurrences {

	/**
	 * 
	 * @param the date up to which the number of occurrences of this meeting should
	 *            be counted
	 * @return the number of occurrences of this meeting up to the provided date,
	 *         excludes vacation / holidays if provided
	 * 
	 */
	public int getOccurrencesCount(LocalDate uptoDate);

	
	/**
	 * 
	 * @return The total number of occurrences of this meeting
	 * 
	 */
	public int getTotalOccurrences();

}
