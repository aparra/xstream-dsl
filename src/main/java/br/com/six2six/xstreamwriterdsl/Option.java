package br.com.six2six.xstreamwriterdsl;

import java.util.HashSet;
import java.util.Set;

public class Option {

	private Set<Token> tokens = new HashSet<Option.Token>();
	
	public static Option options(String ...options) {
		Option option = new Option();
		
		for (String representation : options) {
			option.tokens.add(Token.from(representation));
		}
		
		return option;
	}
	
	public boolean writeIfNotNull() {
		return tokens.contains(Token.IF_NOT_NULL);
	}
	
	public enum Token {
		IF_NOT_NULL(":if_not_null"),
		DATE_FORMAT(":date_format");
		
		private String representation;

		private Token(String representation) {
			this.representation = representation;
		}

		public String getRepresentation() {
			return representation;
		}
		
		public static Token from(String value) {
			for (Token token : Token.values()) {
				if (token.getRepresentation().equalsIgnoreCase(value)) {
					return token;
				}
			}
			throw new IllegalArgumentException("invalid representation for token");
		}
	}
}
