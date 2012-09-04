package br.com.six2six.xstreamdsl.marshal;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import br.com.six2six.xstreamdsl.marshal.Receiver;

public class ReceiverTest {

	@Test
	public void ifNotNull() {
		Receiver receiver = Receiver.options(":if_not_null");
		assertTrue(receiver.writeIfNotNull());
	}
	
	@Test
	public void dateFormat() {
		Receiver receiver = Receiver.options(":date_format => dd/MM/yyyy");

		Date date = createDate();
		assertEquals("15/04/1987", receiver.format(date));
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		assertEquals("15/04/1987", receiver.format(calendar));
	}
	
	private Date createDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return dateFormat.parse("15/04/1987");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
