package br.com.six2six.xstreamwriterdsl.unmarshal;

import static br.com.six2six.xstreamwriterdsl.util.ReflectionUtils.invokeRecursiveSetter;
import static br.com.six2six.xstreamwriterdsl.util.ReflectionUtils.invokeRecursiveType;
import static br.com.six2six.xstreamwriterdsl.util.ReflectionUtils.newInstance;
import static org.apache.commons.lang.ClassUtils.isAssignable;
import static org.apache.commons.lang.ClassUtils.primitiveToWrapper;
import br.com.six2six.xstreamwriterdsl.unmarshal.transform.EnumTransformer;
import br.com.six2six.xstreamwriterdsl.unmarshal.transform.NumberTransformer;
import br.com.six2six.xstreamwriterdsl.unmarshal.transform.Transformer;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;

public class BetterUnmarshal<T> {

	private T bean;
	
	private final HierarchicalStreamReader reader;

	private BetterUnmarshal(HierarchicalStreamReader reader) {
		this.reader = reader;
	}
	
	public static <T> BetterUnmarshal<T> build(HierarchicalStreamReader reader) {
		return new BetterUnmarshal<T>(reader);
	}
	
	@SuppressWarnings("unchecked")
	public BetterUnmarshal<T> to(Class<?> clazz) {
		bean = (T) newInstance(clazz);
		return this;
	}

	public BetterUnmarshal<T> node(String property) {
		invokeRecursiveSetter(bean, property, transform(property, getValue()));
		return this;
	}

	public BetterUnmarshal<T> node(String property, Transformer transformer) {
		invokeRecursiveSetter(bean, property, transform(property, getValue(), transformer));
		return this;
	}

	public BetterUnmarshal<T> node(String name, String property) {
		invokeRecursiveSetter(bean, property, transform(property, getValue()));
		return this;
	}
	
	public BetterUnmarshal<T> node(String name, String property, Transformer transformer) {
		invokeRecursiveSetter(bean, property, transform(property, getValue(), transformer));
		return this;
	}

	public T get() {
		return bean;
	}

	private String getValue() {
		reader.moveDown();
		String value = reader.getValue();
		reader.moveUp();
		return value;
	}
	
	private Object transform(String property, String value) {
		Class<?> type = primitiveToWrapper(invokeRecursiveType(bean, property));
		
		if (isAssignable(type, Number.class)) {
			return new NumberTransformer().transform(value, type);
		
		} else if (isAssignable(type, Enum.class)) { 
			return new EnumTransformer().transform(value, type);
		}

		return value;
	}
	
	private Object transform(String property, String value, Transformer tranformer) {
		Class<?> type = primitiveToWrapper(invokeRecursiveType(bean, property));
		return tranformer.transform(value, type);
	}
}
