package br.com.six2six.xstreamdsl.converter;

import static br.com.six2six.xstreamdsl.marshal.BetterMarshal.build;
import static br.com.six2six.xstreamdsl.marshal.Receiver.options;
import static br.com.six2six.xstreamdsl.unmarshal.BetterUnmarshal.build;
import static br.com.six2six.xstreamdsl.unmarshal.transform.DateTimeTransformer.from;
import br.com.six2six.xstreamdsl.model.Invoice;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class InvoiceConverter implements Converter {

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
		return Invoice.class.equals(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		build(writer, context).to(source)
			.node("#id")
			.node("total", "#amount", options(":if_not_null"))
			.node("#dueDate", options(":date_format => dd/MM/yyyy"));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		return build(reader, context).to(Invoice.class)
				.node("id")
				.node("amount")
				.node("dueDate", from("dd/MM/yyyy"))
				.get();
	}
}
