package br.com.six2six.xstreamdsl.unmarshal;

import static br.com.six2six.xstreamdsl.unmarshal.transform.DateTimeTransformer.from;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import br.com.six2six.xstreamdsl.converter.InvoiceConverter;
import br.com.six2six.xstreamdsl.model.Invoice;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class InvoiceUnmarshalTest {

	@Test
	public void partialInvoinceToXML() {
		final String content = "<br.com.six2six.xstreamdsl.model.Invoice>\n"
							 + "  <id>1</id>\n"
							 + "  <dueDate></dueDate>\n"
							 + "</br.com.six2six.xstreamdsl.model.Invoice>";

		Invoice invoice = (Invoice) driverXML().fromXML(content);
		assertNotNull(invoice);
		assertEquals("1", invoice.getId());
		assertNull(invoice.getDueDate());
		assertNull(invoice.getAmount());
	}

	@Test
	public void completeInvoiceToXML() {
		final String content = "<br.com.six2six.xstreamdsl.model.Invoice>\n"
							 + "  <id>1</id>\n"
							 + "  <total>10</total>\n"
							 + "  <dueDate>15/04/1987</dueDate>\n"
							 + "</br.com.six2six.xstreamdsl.model.Invoice>";
		
		Invoice invoice = (Invoice) driverXML().fromXML(content);
		assertNotNull(invoice);
		assertEquals("1", invoice.getId());
		assertEquals(BigDecimal.TEN, invoice.getAmount());
		assertEquals(from("dd/MM/yyyy").transform("15/04/1987", Date.class), invoice.getDueDate());
	}
	
	@Test
	public void partilaInvoiceToJSON() {
		final String content = "{\"br.com.six2six.xstreamdsl.model.Invoice\": {\n"
							 + "  \"id\": 1,\n"
							 + "  \"dueDate\": \"\" \n"
							 + "}}";

		Invoice invoice = (Invoice) driverJSON().fromXML(content);
		assertNotNull(invoice);
		assertEquals("1", invoice.getId());
		assertNull(invoice.getDueDate());
		assertNull(invoice.getAmount());
	}

	@Test
	public void completeInvoiceToJSON() {
		final String content = "{\"br.com.six2six.xstreamdsl.model.Invoice\": {\n"
							 + "  \"id\": \"1\",\n"
							 + "  \"total\": \"10\",\n"
							 + "  \"dueDate\": \"15/04/1987\"\n"
							 + "}}";
		
		Invoice invoice = (Invoice) driverJSON().fromXML(content);
		assertNotNull(invoice);
		assertEquals("1", invoice.getId());
		assertEquals(BigDecimal.TEN, invoice.getAmount());
		assertEquals(from("dd/MM/yyyy").transform("15/04/1987", Date.class), invoice.getDueDate());
	}
	
	private XStream driverXML() {
		XStream xstream = new XStream() {
			{setMode(NO_REFERENCES);}
		};
		xstream.registerConverter(new InvoiceConverter());
		return xstream;
	}
	
	private XStream driverJSON() {
		XStream xstream = new XStream(new JettisonMappedXmlDriver()) {
			{setMode(NO_REFERENCES);}
		};
		xstream.registerConverter(new InvoiceConverter());
		return xstream;
	}
}
