package br.com.six2six.xstreamdsl.unmarshal;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.junit.Test;

import br.com.six2six.xstreamdsl.converter.AddressConverter;
import br.com.six2six.xstreamdsl.converter.ClientConverter;
import br.com.six2six.xstreamdsl.model.Address;
import br.com.six2six.xstreamdsl.model.Client;

import com.thoughtworks.xstream.XStream;

public class ClientUnmarshalTest {

	@Test
	public void toObject() {
		final String content = "<br.com.six2six.xstreamdsl.model.Client>\n"
							 + "  <code>1</code>\n"
							 + "  <fullName>Ander Parra</fullName>\n"
							 + "  <email>casinha@gmail.com</email>\n"
							 + "  <home-address>\n"
							 + "    <street>Ibirapuera Avenue</street>\n"
							 + "    <city>Sao Paulo</city>\n"
							 + "    <state>Sao Paulo</state>\n"
							 + "    <country>Brazil</country>\n"
							 + "    <zip-code>17720000</zip-code>\n"
							 + "  </home-address>\n"
							 + "</br.com.six2six.xstreamdsl.model.Client>";
		
		Client client = (Client) xstream().fromXML(content);
		
		assertNotNull(client);
		assertEquals(Long.valueOf(1), client.getId());
		assertEquals("Ander Parra", client.getName());
		assertEquals("casinha@gmail.com", client.getEmail());
		
		Address address = client.getAddress();
		assertNotNull(address);
		assertEquals("Ibirapuera Avenue", address.getStreet());
		assertEquals("Sao Paulo", address.getCity());
		assertEquals("Brazil", address.getCountry());
		assertEquals("17720000", address.getZipCode());
	}
	
	private XStream xstream() {
		XStream xstream = new XStream() {
			{setMode(NO_REFERENCES);}
		};
		xstream.registerConverter(new ClientConverter());
		xstream.registerConverter(new AddressConverter());

		return xstream;
	}
}
