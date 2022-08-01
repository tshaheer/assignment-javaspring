package com.assignment.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date conversions.
 */
public final class DateUtil {
	
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	
	private DateUtil() {
    }
	
	public static LocalDate convertToDate(String dateInString) {
		return LocalDate.parse(dateInString, DATE_FORMATTER);
	}

}
