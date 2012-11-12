package org.cocoa4android.ns;

public class NSObject {
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
