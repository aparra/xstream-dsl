package br.com.six2six.xstreamdsl.marshal.format;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeFormat implements Formatter {

	private DateFormat format;

	public DateTimeFormat(String format) {
		this.format = new SimpleDateFormat(format);	
	}

	@Override
	public <T> String format(T value) {
		if (value == null) return null;
		
		if (value instanceof java.util.Date) {
			return transformDateToString((java.util.Date) value);
		
		} else if (value instanceof Calendar) {
			return transformDateToString(transformCalendarToDate((Calendar) value));
		
		}
		
		return null;
	}

	@Override
	public boolean canFormat(Class<?> type) {
		return Date.class.equals(type) || Calendar.class.isAssignableFrom(type);
	}
	
	private String transformDateToString(java.util.Date value) {
		return format.format(value);
	}

	private java.util.Date transformCalendarToDate(Calendar value) {
		return value.getTime();
	}
}
