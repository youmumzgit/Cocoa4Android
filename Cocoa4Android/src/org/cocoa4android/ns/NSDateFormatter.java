package org.cocoa4android.ns;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NSDateFormatter extends NSObject {
	public SimpleDateFormat _dateFormat;
	
	public NSDateFormatter() {
		_dateFormat = new SimpleDateFormat();
	}
	
	public NSString dateFormat() {
		//return dateFormat.toPattern();
		return null;
	}
	
	public void setDateFormat(NSString string) {
		_dateFormat.applyPattern(string.getString());
	}
	
	public NSString stringFromDate(NSDate date) {
		return new NSString(_dateFormat.format(date.getDate()));
	}
	
	public NSDate dateFromString(NSString string) {
		NSDate date;
		try {
			date = NSDate.dateWithDate(_dateFormat.parse(string.getString()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			date = new NSDate();
		}
		return date;
	}
}
