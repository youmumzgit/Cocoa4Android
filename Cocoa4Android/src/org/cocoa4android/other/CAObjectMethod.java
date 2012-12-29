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
package org.cocoa4android.other;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CAObjectMethod{
	private Object target;
	private Method method;
	private String selector;
	public String getSelector() {
		return selector;
	}
	public void setSelector(String selector) {
		this.selector = selector;
	}
	private Object data;
	
	
	public CAObjectMethod(Object target,String selector){
		this.setTarget(target);
		Method[] ms = target.getClass().getMethods();
		Method m = null;
		for(int i=0;i<ms.length;i++){
			Method method = ms[i];
			if(method.getName().equals(selector)){
				m = method;
				this.selector = selector;
				break;
			}
		}
		this.setMethod(m);
	}
	public CAObjectMethod(Object target,String selector,Object data){
		this(target, selector);
		this.data = data;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public void invoke(Object sender){
		try {
			Object[] params = this.method.getParameterTypes();
			if(params.length==1){
				if(this.data==null){
					this.method.invoke(target, new Object[]{sender});
				}else{
					this.method.invoke(target, new Object[]{data});
				}
			}else if(params.length==0){
				this.method.invoke(target, (Object[]) null);
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void invoke(Object sender,Object data){
		try {
			Object[] params = this.method.getParameterTypes();
			if(params.length==2){
				this.method.invoke(target, new Object[]{sender,data});
			}else if(params.length==1){
				this.method.invoke(target, new Object[]{sender});
			}else if(params.length==0){
				this.method.invoke(target, (Object[]) null);
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

