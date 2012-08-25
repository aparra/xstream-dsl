package br.com.six2six.xstreamwriterdsl;

import static br.com.six2six.xstreamwriterdsl.util.ReflectionUtils.invokeRecursiveGetter;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class BetterWrtier<T> {

	private HierarchicalStreamWriter writer;
	private T bean;
	
	private BetterWrtier(HierarchicalStreamWriter writer) {
		this.writer = writer;
	}

	public static <T> BetterWrtier<T> build(HierarchicalStreamWriter writer) {
		return new BetterWrtier<T>(writer);
	}
	
	public BetterWrtier<T> to(T bean) {
		this.bean = bean;
		return this;
	}
	
	public BetterWrtier<T> node(String property) {
		write(normalize(property), invokeRecursiveGetter(bean, normalize(property)));
		return this;
	}
	
	public BetterWrtier<T> node(String name, Object value) {
		write(name, defaultIfEmpty(value).startsWith("#") ? invokeRecursiveGetter(bean, normalize(value.toString())) : value);
		return this;
	}

	public BetterWrtier<T> write(String name, Object value) {
		writer.startNode(name);
		writer.setValue(defaultIfEmpty(value));
		writer.endNode();
		return this;
	}
	
	private String defaultIfEmpty(Object value) {
		return value == null ? "" : value.toString();
	}
	
	private String normalize(String value) {
		return value.replace("#", "");
	}
}
