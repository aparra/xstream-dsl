package br.com.six2six.xstreamwriterdsl;

import static junit.framework.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		Fixture.of(Invoice.class).addTemplate("without_amount", new Rule() {{
			add("id", "1");
		}});
		
		Fixture.of(Invoice.class).addTemplate("complete", new Rule() {{
			add("id", "1");
			add("amount", BigDecimal.TEN);
			add("dueDate", createDate());
		}});
	}
	
	@Test
	public void partialInvoinceToXML() {
		final String content = "<br.com.six2six.xstreamwriterdsl.model.Invoice>\n"
							 + "  <id>1</id>\n"
							 + "  <dueDate></dueDate>\n"
							 + "</br.com.six2six.xstreamwriterdsl.model.Invoice>";
		
		Invoice invoice = Fixture.from(Invoice.class).gimme("without_amount");
		
		XStream xstream = new XStream();
		xstream.registerConverter(new InvoiceConverter());
		xstream.setMode(XStream.NO_REFERENCES);
		
		assertEquals(content, xstream.toXML(invoice));
	}

	@Test
	public void completeInvoiceToXML() {
		final String content = "<br.com.six2six.xstreamwriterdsl.model.Invoice>\n"
							 + "  <id>1</id>\n"
							 + "  <total>10</total>\n"
							 + "  <dueDate>15/04/1987</dueDate>\n"
							 + "</br.com.six2six.xstreamwriterdsl.model.Invoice>";
		
		Invoice invoice = Fixture.from(Invoice.class).gimme("complete");
		
		XStream xstream = new XStream();
		xstream.registerConverter(new InvoiceConverter());
		xstream.setMode(XStream.NO_REFERENCES);
		
		assertEquals(content, xstream.toXML(invoice));
	}
	
	private Date createDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return dateFormat.parse("15/04/1987");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
