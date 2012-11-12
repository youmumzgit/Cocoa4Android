package org.cocoa4android.ui;

import org.cocoa4android.cg.CGRect;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;

public abstract class UIAppDelegate extends Activity {
	public static Context context;
	
	public UIWindow window;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		//work before onCreate
		//setTheme(android.R.style.Theme_Translucent_NoTitleBar);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        UIApplication.getSharedApplication().setContext(this);
        UIApplication.getSharedApplication().setDelegate(this);
        
        
        
        DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm); 
        float statusBarHeight = 25;
        float density = dm.density;
        float width = dm.widthPixels/density;
        float height = dm.heightPixels/density;
        
        UIScreen.getMainScreen().setDensity(density);
        UIScreen.getMainScreen().setBounds(new CGRect(0,0,width,height));
        UIScreen.getMainScreen().setApplicationFrame(new CGRect(0,0,width,height-statusBarHeight));
        this.window = new UIWindow();
        this.setContentView(this.window.getView());
        //this.setContentView(this.window.getView());
        this.applicationDidFinishLaunching();
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	if(this.window!=null){
	        	UIViewController viewController = this.window.getRootViewController();
	        	if(viewController!=null){
	        		viewController.backKeyDidClicked();
	        	}
        	}
            return false;
        }
        return false;
    }
	public abstract void applicationDidFinishLaunching();
}
