package xin.livingwater.app.cfilechooser;

import android.app.Activity;
import android.content.Intent;

public class CFile {
    public static int getResultCode() {
        return resultCode;
    }

    public static int getRequestCode() {
        return requestCode;
    }

    final static int resultCode = 7777;
    final static int requestCode = 8888;

    public static void startFileChooser(Activity activity) {
        activity.startActivityForResult(new Intent(activity, CFileChooser.class), requestCode);
    }
}
