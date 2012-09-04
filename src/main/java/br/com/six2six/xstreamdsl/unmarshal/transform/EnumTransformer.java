package br.com.six2six.xstreamdsl.unmarshal.transform;

import java.lang.reflect.Method;

public class EnumTransformer implements Transformer {
	
	public <T>  T transform(Object value, Class<T> type) {
		if (value == null || value.equals("")) return null;

		Object returnValue = null;
		
		if (Character.isDigit(value.toString().charAt(0))) {
			int ordinal = Integer.parseInt(value.toString());
			if (ordinal >= type.getEnumConstants().length) {
				throw new IllegalArgumentException("impossible convert Enum, ordinal value invalid");
			}
			returnValue = type.getEnumConstants()[ordinal];
		} else {
			try {
				Method method = type.getMethod("valueOf", Class.class, String.class);
				returnValue = method.invoke(type, type, value.toString());
			} catch (Exception e) {
				throw new IllegalArgumentException("impossible convert Enum, string value invalid");
			}
		}
			
		return type.cast(returnValue);
	}
}
