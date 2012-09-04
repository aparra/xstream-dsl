xstream-writer-dsl - a simple dsl for writer nodes with xstream 
================================================================== 

### installing

clone xstream-wrtier-dsl project and install in your local repository
 
	$ mvn clean install

use it like a maven dependency on your project

	<dependency>
		<groupId>br.com.six2six</groupId>
		<artifactId>xstream-writer-dsl</artifactId>
		<version>0.0.1</version>
	</dependency>

### usage

writing nodes with new dsl 

converter to Client:

	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
	  build(writer, context).to(source)
	    .node("code", "#id")
	    .node("fullName", "#name")
	    .node("#email")
	    .delegate("home-address", "#address");
	}

converter to Address:

	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
	  build(writer, context).to(source)
	    .node("#street")
	    .node("#city")
	    .node("#state")
	    .node("#country")
	    .node("zip-code", "#zipCode");
	}

converter to Order:

	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
 	  build(writer, context).to(source)
  	    .node("#id")
	    .collection("#products");
	}

converter to User.Role:

	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
	  build(writer, context).node("role", source);
	}

converter to Invoice:

	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
	  build(writer, context).to(source)
	    .node("#id")
	    .node("total", "#amount", options(":if_not_null"))
	    .node("#dueDate", options(":date_format => dd/MM/yyyy"));
	}

You can see more utilization on [tests](xstream-writer-dsl/tree/master/src/test/java/br/com/six2six/xstreamwriterdsl)!

### birth 

[xstream framework](http://xstream.codehaus.org) is cool, but it is boring to work with their converters. Thinking about it, we ([anderparra](https://github.com/aparra), [douglasrodrigo](https://github.com/douglasrodrigo) and [bjornnborg](https://github.com/bjornnborg)) created a new dsl to facilitate our work.
