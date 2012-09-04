package br.com.six2six.xstreamdsl.converter;

import static br.com.six2six.xstreamdsl.marshal.BetterMarshal.build;
import static br.com.six2six.xstreamdsl.unmarshal.BetterUnmarshal.build;
import br.com.six2six.xstreamdsl.model.Client;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ClientConverter implements Converter {

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
		return Client.class.equals(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		build(writer, context).to(source)
			.node("code", "#id")
			.node("fullName", "#name")
			.node("#email")
			.delegate("home-address", "#address");
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return build(reader, context).to(Client.class)
				.node("id")
				.node("name")
				.node("email")
				.delegate("address")
				.get();
	}
}
