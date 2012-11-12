package org.cocoa4android.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CAObjectMethod{
	private Object target;
	private Method method;
	private Object data;
	
	
	public CAObjectMethod(Object target,String selector){
		this.setTarget(target);
		Method[] ms = target.getClass().getMethods();
		Method m = null;
		for(int i=0;i<ms.length;i++){
			Method method = ms[i];
			if(method.getName().equals(selector)){
				m = method;
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

