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

import java.lang.reflect.InvocationTargetException;

public class NSInvocation extends NSObject {
	private NSMethodSignature sig;
	private Object target = null;
	private Object[] arguments;
	
	public Object target() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
		arguments[0] = target;
	}
	public static NSInvocation invocationWithMethodSignature(NSMethodSignature sig){
		return new NSInvocation(sig);
	}
	private NSInvocation(NSMethodSignature sig){
		this.sig = sig;
		arguments = new Object[sig.numberOfArguments()+2];
		arguments[0] = target;
		arguments[1] = sig.selector;
	}
	public void setArgument(Object argumentLocation,int idx) {
		if (idx<arguments.length) {
			arguments[idx] = argumentLocation;
		}
		
	}
	public Object getArgument(int idx){
		if (idx<arguments.length) {
			return arguments[idx];
		}else{
			return null;
		}
	}
	public void invoke(){
		Object[] args = new Object[arguments.length-2];
		for (int i = 0; i < arguments.length-2; i++) {
			args[i] = arguments[i+2];
		}
		try {
			sig.method.invoke(target, args);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	public String selector(){
		return sig.selector;
	}
	public void invokeWithTarget(Object target){
		this.setTarget(target);
		this.invoke();
	}
	
	public int getParamsCount(){
		return sig.numberOfArguments();
	}
	
}
