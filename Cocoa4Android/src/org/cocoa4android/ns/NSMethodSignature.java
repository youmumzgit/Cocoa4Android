package org.cocoa4android.ns;

import java.lang.reflect.Method;


public class NSMethodSignature extends NSObject {
	Class<? extends Object> reflectClass;
	String selector;
	Method method;
	
	private int numberOfArguments;
	
	public int numberOfArguments() {
		return numberOfArguments;
	}
	
	boolean isValid = NO;
	
	public boolean reflectMethod(Class<? extends Object> reflectClass,String selector){
		this.reflectClass = reflectClass;
		int numberOfParams = 0;
		String methodName;
		String[] parts = selector.split(":");
		if (selector.indexOf(":")>0) {
			numberOfParams = parts.length;
			methodName = parts[0];
		}else{
			methodName = selector;
		}
		
		 
		Method[] ms = reflectClass.getMethods();
		for(int i=0;i<ms.length;i++){
			Method method = ms[i];
			if(method.getName().equals(methodName)){
				Object[] params = method.getParameterTypes();
				if (params.length==numberOfParams) {
					this.method = method;
					this.selector = selector;
					numberOfArguments = params.length;
					isValid = YES;
					break;
				}
			}
		}
		if (!isValid) {
			throw new IllegalArgumentException("No Such Method");
		}
		return isValid;
	}
	
}
