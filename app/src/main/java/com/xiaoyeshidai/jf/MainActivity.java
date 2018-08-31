package com.xiaoyeshidai.jf;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private WebView webView;
    private ProgressBar progressBar;
    private long exitTime = 0;
    private static final String URL = "http://www.xwsdjf.com/xiaov/user/index";

    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调</span>
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionUtil.verfyStoryCameraPermissions(MainActivity.this);

        progressBar= (ProgressBar)findViewById(R.id.progressbar);//进度条

        webView = (WebView) findViewById(R.id.webview);
//        webView.loadUrl("file:///android_asset/test.html");//加载asset文件夹下html

        //使用webview显示html代码
//        webView.loadDataWithBaseURL(null,"<html><head><title> 欢迎您 </title></head>" +
//                "<body><h2>使用webview显示 html代码</h2></body></html>", "text/html" , "utf-8", null);

        webView.addJavascriptInterface(this,"android");//添加js监听 这样html就能调用客户端


        initWebViewSetting();
    }

    private void initWebViewSetting() {
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        settings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        settings.setAllowFileAccessFromFileURLs(true);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        settings.setAllowUniversalAccessFromFileURLs(false);
        //开启JavaScript支持
        settings.setJavaScriptEnabled(true);
        // 支持缩放
        settings.setSupportZoom(true);
        //辅助WebView设置处理关于页面跳转，页面请求等操作
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL=filePathCallback;
                take();
                return true;
            }
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage=uploadMsg;
                take();
            }
            public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType) {
                mUploadMessage=uploadMsg;
                take();
            }
            public void openFileChooser(ValueCallback<Uri> uploadMsg,String acceptType, String capture) {
                mUploadMessage=uploadMsg;
                take();
            }
        });
        //辅助WebView处理图片上传操作
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
            }
        });
        //加载地址
        webView.loadUrl(URL);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
//        else {
//            if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
//                        if((System.currentTimeMillis()-exitTime) > 2000){
//                                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                                exitTime = System.currentTimeMillis();
//                            } else {
//                               finish();
//                                System.exit(0);
//                             }
//                        return true;
//                     }
//
//        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * JS调用android的方法
     * @param str
     * @return
     */
    @JavascriptInterface //仍然必不可少
    public void  getClient(String str){
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //释放资源
        webView.destroy();
        webView=null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtil.CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "相机权限已dakai", Toast.LENGTH_SHORT).show();
//                tvPermissionStatus.setTextColor(Color.GREEN);
//                tvPermissionStatus.setText("相机权限已申请");
            } else {
                //用户勾选了不再询问
                //提示用户手动打开权限
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "相机权限已被禁止", Toast.LENGTH_SHORT).show();
                }
            }
        }else if (requestCode == PermissionUtil.REQUEST_EXTERNAL_STORAGE){

        }else if (requestCode == PermissionUtil.REQUEST_EXTERNAL_STORAGE_CAMERA){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            }
            else  if (mUploadMessage != null) {
                Log.e("result",result+"");
                if(result==null){
//	            		mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage.onReceiveValue(imageUri);
                    mUploadMessage = null;

                    Log.e("imageUri",imageUri+"");
                }else {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }


            }
        }
    }



    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }

        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if(results!=null){
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }else{
            results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        }

        return;
    }



    private void take(){
        File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyApp");
        // Create the storage directory if it does not exist
        if (! imageStorageDir.exists()){
            imageStorageDir.mkdirs();
        }
        File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent i = new Intent(captureIntent);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            i.setPackage(packageName);
            i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            cameraIntents.add(i);

        }
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent chooserIntent = Intent.createChooser(i,"Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));
        MainActivity.this.startActivityForResult(chooserIntent,  FILECHOOSER_RESULTCODE);
    }


}


