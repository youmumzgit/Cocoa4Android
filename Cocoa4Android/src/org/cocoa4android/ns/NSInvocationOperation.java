package org.cocoa4android.ns;

public class NSInvocationOperation extends NSOperation {
	private NSInvocation invocation;
	public NSInvocationOperation(Object target,String selector,Object data){
		NSMethodSignature signature = class2NSClass(target.getClass()).instanceMethodSignatureForSelector(selector);
		invocation = NSInvocation.invocationWithMethodSignature(signature);
		invocation.setTarget(target);
		invocation.setArgument(data, 2);
	}
	@Override
	public void main() {
		invocation.invoke();
	}

}
