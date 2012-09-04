package br.com.six2six.xstreamdsl.converter;

import static br.com.six2six.xstreamdsl.marshal.BetterMarshal.build;
import static br.com.six2six.xstreamdsl.unmarshal.BetterUnmarshal.build;
import br.com.six2six.xstreamdsl.model.Address;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class AddressConverter implements Converter {

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
		return Address.class.equals(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		build(writer, context).to(source)
			.node("#street")
			.node("#city")
			.node("#state")
			.node("#country")
			.node("zip-code", "#zipCode");
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return build(reader, context).to(Address.class)
				.node("street")
				.node("city")
				.node("state")
				.node("country")
				.node("zipCode")
				.get();
	}
}
