package br.com.six2six.xstreamdsl.unmarshal.transform;

public interface Transformer {

	<T> T transform(Object value, Class<T> type);
}
