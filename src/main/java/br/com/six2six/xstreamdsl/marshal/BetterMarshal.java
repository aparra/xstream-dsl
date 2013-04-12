package br.com.six2six.xstreamdsl.marshal;

import static br.com.six2six.xstreamdsl.util.ReflectionUtils.invokeRecursiveGetter;

import java.util.Collection;


import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class BetterMarshal<T> {

	private T bean;

	private HierarchicalStreamWriter writer;
	private MarshallingContext context;
	
	private BetterMarshal(HierarchicalStreamWriter writer, MarshallingContext context) {
		this.writer = writer;
		this.context = context;
	}

	public static <T> BetterMarshal<T> build(HierarchicalStreamWriter writer, MarshallingContext context) {
		return new BetterMarshal<T>(writer, context);
	}
	
	public BetterMarshal<T> to(T bean) {
		this.bean = bean;
		return this;
	}
	
	public BetterMarshal<T> node(String property) {
		write(normalize(property), get(property));
		return this;
	}
	
	public BetterMarshal<T> node(String name, Object value) {
		if (value instanceof Receiver) return node(normalize(name), name, (Receiver) value); 
		write(name, get(value));
		return this;
	}

	public BetterMarshal<T> node(String name, Object value, Receiver receiver) {
		if (get(value) == null && receiver.writeIfNotNull()) return this; 
		write(name, receiver.format(get(value)));
		return this;
	}
	
	public BetterMarshal<T> delegate(Object bean) {
		context.convertAnother(get(bean));
		return this;
	}

	public BetterMarshal<T> collection(String property) {
		write(normalize(property), (Collection<?>) get(property));
		return this;
	}

	public BetterMarshal<T> collection(String name, Object value) {
		write(name, (Collection<?>) get(value));
		return this;
	}
	
	public BetterMarshal<T> delegate(String name, Object value) {
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
		if (writer instanceof JsonWriter) {
			((JsonWriter) writer).startNode(name, values.getClass());
	        context.convertAnother(values);
			writer.endNode();
		} else {
			context.convertAnother(values);			
		}
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
