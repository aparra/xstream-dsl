package br.com.six2six.xstreamwriterdsl;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.fixturefactory.Fixture;
import br.com.fixturefactory.Rule;
import br.com.six2six.xstreamwriterdsl.converter.RoleConverter;
import br.com.six2six.xstreamwriterdsl.converter.UserConverter;
import br.com.six2six.xstreamwriterdsl.model.User;

import com.thoughtworks.xstream.XStream;

public class UserMarshalTest {

	@Before
	public void setup() {
		Fixture.of(User.class).addTemplate("tomcat", new Rule() {{
			add("login", "manager");
			add("roles", asList(User.Role.values()));
		}});
	}
	
	@Test
	public void toXML() {
		final String content = "<br.com.six2six.xstreamwriterdsl.model.User>\n"
							 + "  <login>manager</login>\n"
							 + "  <tomcat-roles>\n"
							 + "    <role>MANAGER_GUI</role>\n"
							 + "    <role>MANAGER_SCRIPT</role>\n"
							 + "    <role>MANAGER_JMX</role>\n"
							 + "  </tomcat-roles>\n"
							 + "</br.com.six2six.xstreamwriterdsl.model.User>";
		
		User user = Fixture.from(User.class).gimme("tomcat");
		
		XStream xstream = new XStream();
		xstream.registerConverter(new UserConverter());
		xstream.registerConverter(new RoleConverter());
		xstream.setMode(XStream.NO_REFERENCES);
		
		assertEquals(content, xstream.toXML(user));
	}
}
