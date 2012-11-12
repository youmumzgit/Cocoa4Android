package org.cocoa4android.ns;

public class NSIndexPath extends NSObject{
	private int section;
	private int row;
	
	public NSIndexPath(int section,int row) {
		this.section = section;
		this.row = row;
	}
	
	public int section() {
		return section;
	}
	public int row() {
		return row;
	}
	

	@Override
	public boolean equals(Object o) {
		if (!this.getClass().isInstance(o)) {
			return false;
		}
		return this.isEqual((NSIndexPath)o);
	}

	@Override
	public int hashCode() {
		return this.hash();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public boolean isEqual(NSObject o) {
		if (!o.isKindOfClass(this.getClass())) {
			return false;
		}
		NSIndexPath obj = (NSIndexPath) o;
		if (this.section != obj.section) {
			return false;
		}
		if (this.row != obj.row) {
			return false;
		}
		return true;
	}
	@Override
	public int hash() {
		return this.section + this.row;
	}
}
