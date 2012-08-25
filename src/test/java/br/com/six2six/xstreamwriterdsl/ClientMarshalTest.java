package br.com.six2six.xstreamwriterdsl;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.Fixture;
import br.com.fixturefactory.Rule;
import br.com.six2six.xstreamwriterdsl.converter.ClientConverter;
import br.com.six2six.xstreamwriterdsl.model.Address;
import br.com.six2six.xstreamwriterdsl.model.Client;

import com.thoughtworks.xstream.XStream;

public class ClientMarshalTest {

	@Before
	public void setUp() {
		Fixture.of(Client.class).addTemplate("valid", new Rule() {{
			add("id", 1L);
			add("name", "Ander Parra");
			add("nickname", random("casinha"));
			add("email", "${nickname}@gmail.com");
			add("birthday", instant("18 years ago"));
			add("address", fixture(Address.class, "valid"));
		}});
	}
	
	@Test
	public void toXML() {
		final String content = "<br.com.six2six.xstreamwriterdsl.model.Client>\n"
							 + "  <code>1</code>\n"
							 + "  <fullName>Ander Parra</fullName>\n"
							 + "  <email>casinha@gmail.com</email>\n"
							 + "</br.com.six2six.xstreamwriterdsl.model.Client>";
		
		Client client = Fixture.from(Client.class).gimme("valid");
		
		XStream xstream = new XStream();
		xstream.registerConverter(new ClientConverter());
		xstream.setMode(XStream.NO_REFERENCES);
		
		assertEquals(content, xstream.toXML(client));
	}
}
