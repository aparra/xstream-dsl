package br.com.six2six.xstreamdsl.marshal.format;

public interface Formatter {

	<T> String format(T value);
	boolean canFormat(Class<?> type);
}
