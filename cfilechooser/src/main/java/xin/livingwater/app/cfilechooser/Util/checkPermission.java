package xin.livingwater.app.cfilechooser.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;

public class checkPermission {

    public static void checkPermission(Context context) {
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions((Activity) context, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }
}