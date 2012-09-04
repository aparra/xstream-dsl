package br.com.six2six.xstreamwriterdsl.unmarshal.transform;

import static org.apache.commons.lang.ClassUtils.isAssignable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeTransformer implements Transformer {

	private DateFormat format;

	private DateTimeTransformer(String pattern) {
		if (pattern == null) throw new IllegalArgumentException("pattern is required");
		format = new SimpleDateFormat(pattern);	
	}

	public static DateTimeTransformer from(String pattern) {
		return new DateTimeTransformer(pattern);
	}
	
	@Override
	public <T> T transform(Object value, Class<T> type)  {
		if (value == null || value.equals("")) return null;

		Object date = null;
		if (isAssignable(type, java.util.Date.class)) {
			date = transformStringToDate(value.toString());
		
		} else if (isAssignable(type, Calendar.class)) {
			date = transformDateToCalendar(transformStringToDate(value.toString()));
		}
				
		return type.cast(date);
	}

	private java.util.Date transformStringToDate(String value) {
		try {
			return format.parse(value);
		} catch (ParseException e) {
			throw new IllegalArgumentException("error parser String value to java.util.Date");
		}
	}

	private Calendar transformDateToCalendar(java.util.Date value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);
		return calendar;
	}
}
