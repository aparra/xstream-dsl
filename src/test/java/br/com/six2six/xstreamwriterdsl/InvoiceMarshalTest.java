package br.com.six2six.xstreamwriterdsl;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.Fixture;
import br.com.fixturefactory.Rule;
import br.com.six2six.xstreamwriterdsl.converter.InvoiceConverter;
import br.com.six2six.xstreamwriterdsl.model.Invoice;

import com.thoughtworks.xstream.XStream;

public class InvoiceMarshalTest {

	@Before
	public void setup() {
		Fixture.of(Invoice.class).addTemplate("without_amount", new Rule(){{
			add("id", "1");
		}});
	}
	
	@Test
	public void toXML() {
		final String content = "<br.com.six2six.xstreamwriterdsl.model.Invoice>\n"
							 + "  <id>1</id>\n"
							 + "</br.com.six2six.xstreamwriterdsl.model.Invoice>";
		
		Invoice invoice = Fixture.from(Invoice.class).gimme("without_amount");
		
		XStream xstream = new XStream();
		xstream.registerConverter(new InvoiceConverter());
		xstream.setMode(XStream.NO_REFERENCES);
		
		assertEquals(content, xstream.toXML(invoice));
	}
}
