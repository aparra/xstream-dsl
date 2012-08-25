package br.com.six2six.xstreamwriterdsl.model;

import java.math.BigDecimal;
import java.util.Date;

public class Invoice {

	private String id;
	private BigDecimal ammount;
	private Date dueDate;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public BigDecimal getAmmount() {
		return ammount;
	}
	public void setAmmount(BigDecimal ammount) {
		this.ammount = ammount;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
