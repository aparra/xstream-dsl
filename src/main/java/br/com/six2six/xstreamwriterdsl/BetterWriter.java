package br.com.six2six.xstreamwriterdsl;

import static br.com.six2six.xstreamwriterdsl.util.ReflectionUtils.invokeRecursiveGetter;

import java.util.Collection;

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
		write(normalize(property), get(property));
		return this;
	}
	
	public BetterWriter<T> node(String name, Object value) {
		if (value instanceof Receiver) return node(normalize(name), name, (Receiver) value); 
		write(name, get(value));
		return this;
	}

	public BetterWriter<T> node(String name, Object value, Receiver receiver) {
		if (get(value) == null && receiver.writeIfNotNull()) return this; 
		write(name, receiver.format(get(value)));
		return this;
	}
	
	public BetterWriter<T> delegate(Object bean) {
		context.convertAnother(get(bean));
		return this;
	}

	public BetterWriter<T> collection(String property) {
		write(normalize(property), (Collection<?>) get(property));
		return this;
	}

	public BetterWriter<T> collection(String name, Object value) {
		write(name, (Collection<?>) get(value));
		return this;
	}
	
	public BetterWriter<T> delegate(String name, Object value) {
		writer.startNode(name);
		context.convertAnother(get(value));
		writer.endNode();
		return this;
	}
	
	private void write(String name, Object value) {
		writer.startNode(name);
		writer.setValue(defaultIfEmpty(value));
		writer.endNode();
	}
	
	private void write(String name, Collection<?> values) {
		writer.startNode(name);
		for (Object bean : values) {
			delegate(bean);
		}
		writer.endNode();
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
