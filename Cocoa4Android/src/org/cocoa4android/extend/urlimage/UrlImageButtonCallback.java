package org.cocoa4android.extend.urlimage;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

public interface UrlImageButtonCallback {
    void onLoaded(View button, Drawable loadedDrawable, String url, boolean loadedFromCache);
}