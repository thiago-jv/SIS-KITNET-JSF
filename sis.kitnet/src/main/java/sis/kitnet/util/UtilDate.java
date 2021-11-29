package sis.kitnet.util;

import java.util.Calendar;
import java.util.Date;

public class UtilDate {

	public static Date calculaDataMaisDoisMeses(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.add(Calendar.MONTH, +2);
		data = calendar.getTime();
		return data;
	}
}
