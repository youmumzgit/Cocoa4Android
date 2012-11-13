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
        UIApplication.sharedApplication().setContext(this);
        UIApplication.sharedApplication().setDelegate(this);
        
        
        
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
	        	UIViewController viewController = this.window.rootViewController();
	        	if(viewController!=null){
	        		return viewController.backKeyDidClicked();
	        	}
        	}
            return true;
        }
        return false;
    }
	public abstract void applicationDidFinishLaunching();
}
