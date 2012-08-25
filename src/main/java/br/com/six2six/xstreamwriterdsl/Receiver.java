package br.com.six2six.xstreamwriterdsl;

import static br.com.six2six.xstreamwriterdsl.Option.Token.IF_NOT_NULL;

import java.util.HashSet;
import java.util.Set;

import br.com.six2six.xstreamwriterdsl.format.Formatter;

public class Receiver {

	private Set<Option> options = new HashSet<Option>();
	
	public static Receiver options(String ...options) {
		Receiver receiver = new Receiver();
		
		for (String representation : options) {
			representation = representation.replaceAll("\\s+", "");
			String[] split = representation.split("=>");
			
			receiver.options.add(new Option.Builder().token(split[0]).format(split.length == 1 ? "" : split[1]).build());
		}
		
		return receiver;
	}
	
	public boolean writeIfNotNull() {
		for (Option option : options) { if (option.getToken() == IF_NOT_NULL) return true; }
		return false;
	}
	
	public <T> String format(T value) {
		if (value == null) return "";
		
		Formatter formatter = this.getFormatter(value.getClass());
		return formatter == null ? value.toString() : formatter.format(value); 
	}
	
	private <T> Formatter getFormatter(Class<?> type) {
		for (Option option : options) { 
			if (option.hasFormmatter() && option.getFormatter().canFormat(type)) return option.getFormatter();
		}
		return null;
	}
}
