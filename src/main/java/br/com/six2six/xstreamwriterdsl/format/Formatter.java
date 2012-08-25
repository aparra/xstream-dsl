package br.com.six2six.xstreamwriterdsl.format;

public interface Formatter {

	<T> String format(T value);
	boolean canFormat(Class<?> type);
}
