package org.zafritech.zscode.todos.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Component;
import org.zafritech.zscode.todos.enums.RepeatType;

@Component
public class TimeUtils {

	private final static TimeZone timeZone = TimeZone.getTimeZone("UTC");
	
	public TimeUtils() {
		
		TimeZone.setDefault(timeZone);
	}
	
	public LocalDateTime parseDateTime(String dateTime) {
		
		return LocalDateTime.parse(dateTime);
	}
	
	public ZonedDateTime parseZonedDateTime(String dateTime) {
		
		return ZonedDateTime.parse(dateTime);
	}
	
	public String stringDateTime(LocalDateTime dateTime) {
		
		return dateTime.toString();
	}
	
	public String stringZonedDateTime(ZonedDateTime dateTime) {
		
		return dateTime.toString();
	}
	
	public ZonedDateTime zonedDateTimeFromUtilDate(Date date) {
		
		return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}
	
	public LocalDate nextDate(LocalDate dateTime, RepeatType type, Integer count) {
		
		if (type == RepeatType.HOURS) {
			
			return dateTime.plus(count, ChronoUnit.HOURS);
			
		} else if (type == RepeatType.DAYS) {
				
			return dateTime.plus(Period.ofDays(count)); 
				
		} else if (type == RepeatType.WEEKS) {

			return dateTime.plus(Period.ofWeeks(count));
			
		} else if (type == RepeatType.MONTHS) {
			
			return dateTime.plus(Period.ofMonths(count));
				
		} else if (type == RepeatType.YEARS) {
			
			return dateTime.plus(Period.ofYears(count));
		}
	
		return null;
	}
	
	public Long duration(LocalDateTime initialDateTime, LocalDateTime finalDateTime, RepeatType type) {

		if (type == RepeatType.HOURS) {
			
			return  ChronoUnit.HOURS.between(initialDateTime, finalDateTime);
			
		} else if (type == RepeatType.DAYS) {
				
			return  ChronoUnit.DAYS.between(initialDateTime, finalDateTime);
				
		} else if (type == RepeatType.WEEKS) {

			return  ChronoUnit.WEEKS.between(initialDateTime, finalDateTime);
			
		} else if (type == RepeatType.MONTHS) {
			
			return  ChronoUnit.MONTHS.between(initialDateTime, finalDateTime);
				
		} else if (type == RepeatType.YEARS) {
			
			return  ChronoUnit.YEARS.between(initialDateTime, finalDateTime);
		}
	
		return null;
	}
	
	
}
