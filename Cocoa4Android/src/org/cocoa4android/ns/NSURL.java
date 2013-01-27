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

import java.net.MalformedURLException;
import java.net.URL;

public class NSURL {
	private String URLString;
	private URL url = null;
	
	private NSURL(URL url){
		this.url = url;
	}
	private NSURL(String URLString){
		try {
			this.url = new URL(URLString);
			this.URLString = URLString;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public static NSURL URLWithString(String URLString) {
		return new NSURL(URLString);
	}
	public String absoluteString(){
		return URLString;
	}
}
