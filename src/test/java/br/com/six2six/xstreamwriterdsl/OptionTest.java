package br.com.six2six.xstreamwriterdsl;

import static junit.framework.Assert.assertTrue;

import org.junit.Test;

public class OptionTest {

	@Test
	public void ifNotNull() {
		Option option = Option.options(":if_not_null");
		assertTrue(option.writeIfNotNull());
	}
}
