package br.com.six2six.xstreamwriterdsl;

import static br.com.six2six.xstreamwriterdsl.util.ReflectionUtils.invokeRecursiveGetter;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class BetterWriter<T> {

	private T bean;

	private HierarchicalStreamWriter writer;
	private MarshallingContext context;
	
	private BetterWriter(HierarchicalStreamWriter writer, MarshallingContext context) {
		this.writer = writer;
		this.context = context;
	}

	public static <T> BetterWriter<T> build(HierarchicalStreamWriter writer, MarshallingContext context) {
		return new BetterWriter<T>(writer, context);
	}
	
	public BetterWriter<T> to(T bean) {
		this.bean = bean;
		return this;
	}
	
	public BetterWriter<T> node(String property) {
		write(normalize(property), invokeRecursiveGetter(bean, normalize(property)));
		return this;
	}
	
	public BetterWriter<T> node(String name, Object value) {
		write(name, get(value));
		return this;
	}

	public BetterWriter<T> delegate(Object value) {
		context.convertAnother(get(value));
		return this;
	}

	public BetterWriter<T> delegate(String name, Object value) {
		writer.startNode(name);
		context.convertAnother(get(value));
		writer.endNode();
		return this;
	}
	
	private BetterWriter<T> write(String name, Object value) {
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
	
	private Object get(Object value) {
		if (value instanceof String && defaultIfEmpty(value).startsWith("#")) return invokeRecursiveGetter(bean, normalize(value.toString())); 
		return value;
	}
}
