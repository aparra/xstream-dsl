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
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

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
							 + "  <br.com.six2six.xstreamdsl.model.Product>\n"
							 + "    <id>1</id>\n"
							 + "    <name>nicething</name>\n" 
							 + "  </br.com.six2six.xstreamdsl.model.Product>\n" 
							 + "  <br.com.six2six.xstreamdsl.model.Product>\n"
							 + "    <id>2</id>\n"
							 + "    <name>nicething</name>\n" 
							 + "  </br.com.six2six.xstreamdsl.model.Product>\n"
							 + "  <br.com.six2six.xstreamdsl.model.Product>\n"
							 + "    <id>3</id>\n"
							 + "    <name>nicething</name>\n"
							 + "  </br.com.six2six.xstreamdsl.model.Product>\n"
							 + "</br.com.six2six.xstreamdsl.model.Order>";
		
		Order order = Fixture.from(Order.class).gimme("buyed");
		assertEquals(content, driverXML().toXML(order));
	}
	
	@Test
	public void toJson() {
		final String content = "{\"br.com.six2six.xstreamdsl.model.Order\": {\n"
							 + "  \"id\": 1,\n"
							 + "  \"products\": [\n"
							 + "    {\n"
							 + "      \"id\": 1,\n"
							 + "      \"name\": \"nicething\"\n"
							 + "    },\n"
							 + "    {\n"
							 + "      \"id\": 2,\n"
							 + "      \"name\": \"nicething\"\n"
							 + "    },\n"
							 + "    {\n"
							 + "      \"id\": 3,\n"
							 + "      \"name\": \"nicething\"\n"
							 + "    }\n"
							 + "  ]\n"
							 + "}}";
                             
		Order order = Fixture.from(Order.class).gimme("buyed");
		assertEquals(content, driverJSON().toXML(order));
	}
	
	private XStream driverXML() {
		XStream xstream = new XStream() {
			{setMode(NO_REFERENCES);}
		};
		xstream.registerConverter(new OrderConverter());

		return xstream;
	}
	
	private XStream driverJSON() {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver()) {
			{setMode(NO_REFERENCES);}
		};
		xstream.registerConverter(new OrderConverter());
		return xstream;
	}
}
