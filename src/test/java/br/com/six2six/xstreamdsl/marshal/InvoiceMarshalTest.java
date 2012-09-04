package br.com.six2six.xstreamdsl.marshal;

import static junit.framework.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.Fixture;
import br.com.fixturefactory.Rule;
import br.com.six2six.xstreamdsl.converter.InvoiceConverter;
import br.com.six2six.xstreamdsl.model.Invoice;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

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
		final String content = "<br.com.six2six.xstreamdsl.model.Invoice>\n"
							 + "  <id>1</id>\n"
							 + "  <dueDate></dueDate>\n"
							 + "</br.com.six2six.xstreamdsl.model.Invoice>";
		
		Invoice invoice = Fixture.from(Invoice.class).gimme("without_amount");
		assertEquals(content, driverXML().toXML(invoice));
	}

	@Test
	public void completeInvoiceToXML() {
		final String content = "<br.com.six2six.xstreamdsl.model.Invoice>\n"
							 + "  <id>1</id>\n"
							 + "  <total>10</total>\n"
							 + "  <dueDate>15/04/1987</dueDate>\n"
							 + "</br.com.six2six.xstreamdsl.model.Invoice>";
		
		Invoice invoice = Fixture.from(Invoice.class).gimme("complete");
		assertEquals(content, driverXML().toXML(invoice));
	}
	
	@Test
	public void partilaInvoiceToJSON() {
		final String content = "{\"br.com.six2six.xstreamdsl.model.Invoice\": {\n"
							 + "  \"id\": 1,\n"
							 + "  \"dueDate\": \n"
							 + "}}";

		Invoice invoice = Fixture.from(Invoice.class).gimme("without_amount");
		assertEquals(content, driverJSON().toXML(invoice));
	}

	@Test
	public void completeInvoiceToJSON() {
		final String content = "{\"br.com.six2six.xstreamdsl.model.Invoice\": {\n"
							 + "  \"id\": 1,\n"
							 + "  \"total\": 10,\n"
							 + "  \"dueDate\": 15/04/1987\n"
							 + "}}";
		
		Invoice invoice = Fixture.from(Invoice.class).gimme("complete");
		assertEquals(content, driverJSON().toXML(invoice));
	}
	
	private Date createDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return dateFormat.parse("15/04/1987");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	private XStream driverXML() {
		XStream xstream = new XStream() {
			{setMode(NO_REFERENCES);}
		};
		xstream.registerConverter(new InvoiceConverter());
		return xstream;
	}
	
	private XStream driverJSON() {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver()) {
			{setMode(NO_REFERENCES);}
		};
		xstream.registerConverter(new InvoiceConverter());
		return xstream;
	}
}
