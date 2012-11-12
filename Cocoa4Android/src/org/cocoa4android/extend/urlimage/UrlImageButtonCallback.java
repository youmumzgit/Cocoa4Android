package org.cocoa4android.extend.urlimage;

import android.graphics.drawable.Drawable;
import android.widget.Button;

public interface UrlImageButtonCallback {
    void onLoaded(Button button, Drawable loadedDrawable, String url, boolean loadedFromCache);
}