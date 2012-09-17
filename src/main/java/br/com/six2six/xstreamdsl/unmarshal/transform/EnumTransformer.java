package br.com.six2six.xstreamdsl.unmarshal.transform;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.lang.reflect.Method;

public class EnumTransformer implements Transformer {
	
	public <T>  T transform(String value, Class<T> type) {
		if (isEmpty(value)) return null;
		
		if (Character.isDigit(value.charAt(0))) {
			int ordinal = Integer.parseInt(value);
			if (ordinal >= type.getEnumConstants().length) throw new IllegalArgumentException("impossible convert Enum, invalid position");

			return type.getEnumConstants()[ordinal];
		} else {
			try {
				Method method = type.getMethod("valueOf", Class.class, String.class);
				return type.cast(method.invoke(type, type, value.toString()));
			} catch (Exception e) {
				throw new IllegalArgumentException("impossible convert Enum, invalid name");
			}
		}
	}
}
