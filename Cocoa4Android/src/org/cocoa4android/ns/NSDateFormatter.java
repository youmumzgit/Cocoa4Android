/*
 * Copyright (C) 2012 Wu Tong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
			e.printStackTrace();
			date = new NSDate();
		}
		return date;
	}
	
	public SimpleDateFormat getDateFormat() {
		return _dateFormat;
	}
}
