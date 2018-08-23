# 简洁的文件选择器：CFileChooser
## 功能简介：
    这是一个简洁的文件选择器。
    
    您可以方便的使用它获取手机文件所在的路径。
    
    目前版本号是：1.0.0
    
    当前仅支持单文件选择，不支持文件夹以及多文件。
    
    后期会继续完善，并且加入更多的自定义属性。
### 使用示例：

    首先在build.gradle文件中加入依赖
```
 implementation 'com.livingwater:CFileChooser:1.0.0' 
```
### 使用示例代码：
```
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
            String path=data.getStringExtra("path");
            //这里的path就是我们获取到的文件路径 大家可以根据需要自己写一些逻辑
            Toast.makeText(MainActivity.this,path, Toast.LENGTH_LONG).show();
        }
    }
```
### 效果图：
![](http://wx3.sinaimg.cn/mw690/0060lm7Tly1fuk04uv2fkj30u01hcwgs.jpg)
### 项目GitHub主页：[传送门](https://github.com/livingwaterCao/CFileChooser/) 
### 项目简书主页：[传送门](https://www.jianshu.com/p/dd30379a8c6e) 
### 项目CSDN主页：[传送门](https://blog.csdn.net/qq_30936979/article/details/81989776) 
### 欢迎大家star！ 以及提出宝贵建议！
