# CFileChooser
这是一个简洁的文件选择器。

您可以方便的使用它获取手机文件所在的路径。

目前版本号是：1.0.0

当前仅支持单文件选择，不支持文件夹以及多文件。

后期会继续完善，并且加入更多的自定义属性。

使用示例：

首先在build.gradle文件中加入依赖

implementation 'com.livingwater:CFileChooser:1.0.0'

在Activity中：

public class MainActivity extends AppCompatActivity {

    private CFile cFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cFile = new CFile();
        cFile.startFileChooser(MainActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == cFile.getRequestCode() && resultCode == cFile.getResultCode()) {
        //这里可以写入自己的一些逻辑 data.getStringExtra("path") 就可以获得所选择的文件路径
        String path=data.getStringExtra("path");
        Toast.makeText(MainActivity.this,path, Toast.LENGTH_LONG).show();
        }
    }
}

