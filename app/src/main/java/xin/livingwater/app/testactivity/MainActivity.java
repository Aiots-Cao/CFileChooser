package xin.livingwater.app.testactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xin.livingwater.app.cfilechooser.CFile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CFile cFile = new CFile();
        cFile.startFileChooser(MainActivity.this);
    }
}
