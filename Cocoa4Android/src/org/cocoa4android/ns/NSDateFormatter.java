package org.cocoa4android.ns;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NSDateFormatter extends NSObject {
	public SimpleDateFormat _dateFormat;
	
	public NSDateFormatter() {
		_dateFormat = new SimpleDateFormat();
	}
	
	public String dateFormat() {
		return _dateFormat.toPattern();
	}
	
	public void setDateFormat(NSString string) {
		_dateFormat.applyPattern(string.getString());
	}
	
	public String stringFromDate(NSDate date) {
		return _dateFormat.format(date.getDate());
	}
	
	public NSDate dateFromString(String string) {
		NSDate date;
		try {
			date = NSDate.dateWithDate(_dateFormat.parse(string));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			date = new NSDate();
		}
		return date;
	}
	
	public SimpleDateFormat getDateFormat() {
		return _dateFormat;
	}
}
