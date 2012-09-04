package br.com.six2six.xstreamwriterdsl.unmarshal.transform;

import static br.com.six2six.xstreamwriterdsl.util.NumberUtils.parseNumber;
import static org.apache.commons.lang.ClassUtils.primitiveToWrapper;

public class NumberTransformer implements Transformer {

	@Override
	@SuppressWarnings("unchecked")
	public <T>  T transform(Object value, Class<T> type) {
		if (value == null || value.equals("")) return null;
		
		type = primitiveToWrapper(type);
		if (type == null) throw new IllegalArgumentException("incorrect type for transformer class");
		
		return type.cast(parseNumber((String) value, type.asSubclass(Number.class)));
	}
}
