package br.com.six2six.xstreamdsl.marshal;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.Fixture;
import br.com.fixturefactory.Rule;
import br.com.six2six.xstreamdsl.converter.OrderConverter;
import br.com.six2six.xstreamdsl.model.Order;
import br.com.six2six.xstreamdsl.model.Product;

import com.thoughtworks.xstream.XStream;

public class OrderMarshalTest {

	@Before
	public void setup() {
		Fixture.of(Order.class).addTemplate("buyed", new Rule() {{
			add("id", 1L);
			add("products", has(3).of(Product.class, "niceThings"));
		}});
		
		Fixture.of(Product.class).addTemplate("niceThings", new Rule() {{
			add("id", sequence(1, 1));
			add("name", "nicething");
		}});
	}
	
	@Test
	public void toXml() {
		final String content = "<br.com.six2six.xstreamdsl.model.Order>\n"
							 + "  <id>1</id>\n"
							 + "  <products>\n"
							 + "    <id>1</id>\n"
							 + "    <name>nicething</name>\n"
							 + "    <id>2</id>\n"
							 + "    <name>nicething</name>\n"
							 + "    <id>3</id>\n"
							 + "    <name>nicething</name>\n"
							 + "  </products>\n"
							 + "</br.com.six2six.xstreamdsl.model.Order>";
		
		Order order = Fixture.from(Order.class).gimme("buyed");
		assertEquals(content, xstream().toXML(order));
	}
	
	private XStream xstream() {
		XStream xstream = new XStream() {
			{setMode(NO_REFERENCES);}
		};
		xstream.registerConverter(new OrderConverter());

		return xstream;
	}
}
