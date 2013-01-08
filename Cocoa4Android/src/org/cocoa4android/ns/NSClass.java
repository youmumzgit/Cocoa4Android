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

public class NSClass extends NSObject {
	private Class<? extends Object> reflectClass;
	public Class<? extends Object> getReflectClass() {
		return reflectClass;
	}
	public NSClass(Class<? extends Object> aClass){
		this.reflectClass = aClass;
	}
	public NSMethodSignature instanceMethodSignatureForSelector(String aSelector){
		NSMethodSignature methodSignature = new NSMethodSignature();
		
		if (methodSignature.reflectMethod(reflectClass, aSelector)) {
			return methodSignature;
		}else{
			return null;
		}
	}
}
