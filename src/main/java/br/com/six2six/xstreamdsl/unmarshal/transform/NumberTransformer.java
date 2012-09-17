package br.com.six2six.xstreamdsl.unmarshal.transform;

import static br.com.six2six.xstreamdsl.util.NumberUtils.parseNumber;
import static org.apache.commons.lang.ClassUtils.primitiveToWrapper;
import static org.apache.commons.lang.StringUtils.isEmpty;

public class NumberTransformer implements Transformer {

	@Override
	@SuppressWarnings("unchecked")
	public <T>  T transform(String value, Class<T> type) {
		if (isEmpty(value)) return null;
		
		type = primitiveToWrapper(type);
		if (type == null) throw new IllegalArgumentException("incorrect type for transformer class");
		
		return type.cast(parseNumber(value, type.asSubclass(Number.class)));
	}
}
