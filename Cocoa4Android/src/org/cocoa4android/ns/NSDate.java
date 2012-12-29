/*
 * Copyright (C) 2012 Ryan li
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

import java.util.Date;

public class NSDate extends NSObject {
	protected Date _date;
	
	public NSDate() {
		_date = new Date();
	}
	
	public NSDate(long milliseconds) {
		_date = new Date(milliseconds);
	}
	
	public NSDate(Date date) {
		_date = date;
	}
	
	public static NSDate dateWithDate(Date date) {
		return new NSDate(date);
	}
	
	public static NSDate date() {
		return new NSDate();
	}
	
	public static NSDate dateWithTimeIntervalSinceNow(Long secs) {
		return new NSDate(secs + System.currentTimeMillis());
	}
	
	public static NSDate dateWithTimeIntervalSince1970(Long milliseconds) {
		return new NSDate(milliseconds);
	}
	
	public static NSDate dateWithTimeInterval(Long ti,NSDate date) {
		return null;
	}
	
	public NSDate dateByAddingTimeInterval(Long seconds) {
		return new NSDate(_date.getTime() + seconds);
	}
	
	public Date getDate() {
		return _date;
	}
	
	public void setDate(Date date) {
		_date = date;
	}
	
	@Override
	public String toString() {
		return _date.toString();
	}
}
