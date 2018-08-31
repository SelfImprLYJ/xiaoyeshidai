package com.xiaoyeshidai.jf;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by user on 18-7-26.
 */

public class PermissionUtil {
    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int REQUEST_EXTERNAL_STORAGE = 2;
    public static final int REQUEST_EXTERNAL_STORAGE_CAMERA = 3;

    public static void requestCameraPermission(Context context){
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_REQUEST_CODE);
        }
    }


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};



    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to
     * grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    private static String[] PERMISSIONS_STORAGE_CAMERA = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};

    public static void verfyStoryCameraPermissions(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so pompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE_CAMERA,
                    REQUEST_EXTERNAL_STORAGE_CAMERA);

        }}
}





