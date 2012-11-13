/*
 * Copyright (C) 2012 Wu Tong,chao
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

public class NSObject {
	public static final boolean YES = true;
	public static final boolean NO = false;
	public static void NSLog(){
		
	}
	
	public boolean isEqual(NSObject o) {
		return super.equals(o);
	}
	public boolean isKindOfClass(Class<? extends NSObject> cls) {
		return cls.isInstance(this);
	}
	public int hash() {
		return super.hashCode();
	}
	public String description() {
		return super.toString();
	}
}
