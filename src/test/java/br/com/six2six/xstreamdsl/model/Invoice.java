package br.com.six2six.xstreamdsl.model;

import java.math.BigDecimal;
import java.util.Date;

public class Invoice {

	private String id;
	private BigDecimal amount;
	private Date dueDate;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
