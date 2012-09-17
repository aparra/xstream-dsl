package br.com.six2six.xstreamdsl.unmarshal.transform;

public interface Transformer {

	<T> T transform(String value, Class<T> type);
}
