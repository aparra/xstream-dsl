package br.com.six2six.xstreamdsl.unmarshal.transform;

import static org.apache.commons.lang.ClassUtils.isAssignable;
import static org.apache.commons.lang.StringUtils.isEmpty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeTransformer implements Transformer {

	private final DateFormat format;

	private DateTimeTransformer(String pattern) {
		if (pattern == null) throw new IllegalArgumentException("pattern is required");
		format = new SimpleDateFormat(pattern);	
	}

	public static DateTimeTransformer from(String pattern) {
		return new DateTimeTransformer(pattern);
	}
	
	@Override
	public <T> T transform(String value, Class<T> type)  {
		if (isEmpty(value)) return null;

		if (isAssignable(type, java.util.Date.class)) {
			return type.cast(transformStringToDate(value.toString()));
		
		} else if (isAssignable(type, Calendar.class)) {
			return type.cast(transformDateToCalendar(transformStringToDate(value.toString())));
		}
				
		return null;
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
