package br.com.six2six.xstreamwriterdsl.converter;

import static br.com.six2six.xstreamwriterdsl.BetterWriter.build;
import br.com.six2six.xstreamwriterdsl.model.Order;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class OrderConverter implements Converter {

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
		return Order.class.equals(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		build(writer, context).to(source)
			.node("#id")
			.collection("#products");
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return null;
	}
}
