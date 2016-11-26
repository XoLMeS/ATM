package khrystosov.tools;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleLogger implements Logger, Serializable{

	@Override
	public void print(String str) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println();
		System.out.println(dateFormat.format(date) + " " + str);
		
	}

	
	
}
