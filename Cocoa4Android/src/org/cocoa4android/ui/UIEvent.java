package org.cocoa4android.ui;

import android.view.MotionEvent;

public class UIEvent {
	private MotionEvent event;

	public UIEvent(MotionEvent event) {
		this.event = event;
	}
	public MotionEvent getEvent() {
		return event;
	}

	public void setEvent(MotionEvent event) {
		this.event = event;
	}
	
}
