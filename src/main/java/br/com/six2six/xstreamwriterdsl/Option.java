package br.com.six2six.xstreamwriterdsl;

import br.com.six2six.xstreamwriterdsl.format.DateTimeFormat;
import br.com.six2six.xstreamwriterdsl.format.Formatter;

public class Option {

	private final Token token;
	private final Formatter formatter;

	private Option(Builder builder) {
		this.token = builder.token;
		this.formatter = builder.formatter;
	}

	public Token getToken() {
		return token;
	}
	public Formatter getFormatter() {
		return formatter;
	}

	public static class Builder {
	
		private Token token;
		private Formatter formatter;

		public Builder token(String value) {
			token = Token.from(value);
			return this;
		}

		public Builder format(String format) {
			if (token == Token.DATE_FORMAT) {
				this.formatter = new DateTimeFormat(format);
			}
			return this;
		}
		
		public Option build() {
			return new Option(this);
		}
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
