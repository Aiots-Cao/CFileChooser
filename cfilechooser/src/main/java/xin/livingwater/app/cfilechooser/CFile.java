package xin.livingwater.app.cfilechooser;

import android.app.Activity;
import android.content.Intent;

public class CFile {
    public static void startFileChooser(Activity activity) {
        activity.startActivityForResult(new Intent(activity,CFileChooser.class),8888);
    }
}
