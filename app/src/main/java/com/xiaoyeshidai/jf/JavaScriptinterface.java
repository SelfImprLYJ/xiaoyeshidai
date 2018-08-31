package com.xiaoyeshidai.jf;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * Created by user on 18-7-27.
 */

public class JavaScriptinterface {

    private Context context;

    public JavaScriptinterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void requestPermission(){

    }
}
