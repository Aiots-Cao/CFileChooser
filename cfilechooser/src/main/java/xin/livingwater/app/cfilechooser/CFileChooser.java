package xin.livingwater.app.cfilechooser;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xin.livingwater.app.cfilechooser.Util.checkPermission;

public class CFileChooser extends AppCompatActivity {

    private ListView FileChooseListView;
    private Button choose;
    private List<HashMap<String, Object>> list = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private MyAdapter myAdapter;
    private String checkPath = "";
    private TextView pathShow;
    private ImageView pathBack;
    File path = Environment.getExternalStorageDirectory();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_choose);
        hideBar();
        checkPermission.checkPermission(CFileChooser.this);
        initData();
        initView();
        deal();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(CFileChooser.this, "您需要同意本权限否则APP无法正常运行！", Toast.LENGTH_SHORT).show();
                    //   checkPermission.checkPermission(MainActivity.this);
                    finish();
                    break;
                }
            }
        }
    }

    public void initData() {
        getAllFileAndFolder(path);
    }

    public void initView() {
        FileChooseListView = (ListView) findViewById(R.id.fileChooseListView);
        choose = (Button) findViewById(R.id.fileChooseListView_choose);
        pathShow = (TextView) findViewById(R.id.pathShow);
        pathBack = (ImageView) findViewById(R.id.pathBack);
        pathShow.setText(path.toString());
    }


    public void deal() {
        myAdapter = new MyAdapter();
        FileChooseListView.setAdapter(myAdapter);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPath.isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra("path", checkPath);
                    setResult(CFile.resultCode, intent);
                    finish();
                } else {
                    Toast.makeText(CFileChooser.this, "您未选择任何文件！", Toast.LENGTH_LONG).show();
                }
            }
        });
        FileChooseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                //判断是文件夹 还是普通文件
                if ((Boolean) list.get(postion).get("isFolder")) {
                    changeFolder(true, list.get(postion).get("fileName").toString());
                } else {
                    CheckBox fileCheckBox = (CheckBox) view.findViewById(R.id.fileListView_item_filecheck);
                    boolean checkStatus = fileCheckBox.isChecked();
                    //将所有checkbox设为不选中状态
                    for (int i = 0; i < views.size(); i++) {
                        View tmpview = views.get(i);
                        CheckBox checkBox = (CheckBox) tmpview.findViewById(R.id.fileListView_item_filecheck);
                        checkBox.setChecked(false);
                    }
                    if (!checkStatus) {
                        fileCheckBox.setChecked(true);
                        choose.setText("选   择(1)");
                        checkPath = list.get(postion).get("filePath").toString();
                    } else {
                        choose.setText("选   择");
                        checkPath = "";
                    }
                }
            }
        });
        pathBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!path.equals(Environment.getExternalStorageDirectory())) {
                    changeFolder(false, "");
                } else {
                    Toast.makeText(CFileChooser.this, "已退回到根目录！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addViewToList() {
        for (int i = 0; i < list.size(); i++) {
            View view = View.inflate(CFileChooser.this, R.layout.filelistview_item, null);
            views.add(view);
        }
    }

    public void hideBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public Object getItem(int i) {
            return views.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int postion, View view, ViewGroup viewGroup) {
            View itemView = views.get(postion);
            TextView fileName = (TextView) itemView.findViewById(R.id.fileListView_item_filename);
            TextView fileSize = (TextView) itemView.findViewById(R.id.fileListView_item_filesize);
            TextView tmptextview = (TextView) itemView.findViewById(R.id.fileListView_tmp);
            ImageView fileIV = (ImageView) itemView.findViewById(R.id.fileListView_item_iv);
            final CheckBox fileCheckBox = (CheckBox) itemView.findViewById(R.id.fileListView_item_filecheck);
            fileName.setText(list.get(postion).get("fileName").toString());
            fileSize.setText(list.get(postion).get("fileMemory") + "B");
            //如果子项是文件夹的操作
            if ((Boolean) list.get(postion).get("isFolder")) {
                fileIV.setImageResource(R.drawable.folder_icon);
                fileCheckBox.setVisibility(View.GONE);
                fileSize.setVisibility(View.GONE);
                tmptextview.setVisibility(View.GONE);
            }
            fileCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    boolean checkStatus = b;
                    //将所有checkbox设为不选中状态
                    for (int i = 0; i < views.size(); i++) {
                        View tmpview = views.get(i);
                        CheckBox checkBox = (CheckBox) tmpview.findViewById(R.id.fileListView_item_filecheck);
                        checkBox.setChecked(false);
                    }
                    if (checkStatus) {
                        fileCheckBox.setChecked(true);
                        choose.setText("选   择(1)");
                        checkPath = list.get(postion).get("filePath").toString();
                    } else {
                        choose.setText("选   择");
                        checkPath = "";
                    }
                }
            });
            return itemView;
        }
    }

    public void getAllFileAndFolder(File path) {
        list.clear();
        views.clear();
        try {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("filePath", files[i].getAbsolutePath());
                map.put("fileName", files[i].getName());
                map.put("fileMemory", files[i].length());
                if (files[i].isDirectory()) {
                    map.put("isFolder", true);
                } else {
                    map.put("isFolder", false);
                }
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化存储View的list
        addViewToList();
        if (myAdapter != null) {
            myAdapter.notifyDataSetChanged();
        }
    }

    public void changeFolder(boolean isNext, String folderName) {
        if (isNext) {
            path = new File((path.toString() + "/" + folderName));
        } else {

            String pathtmp = path.toString().substring(0, path.toString().lastIndexOf("/"));
            path = new File(pathtmp);
        }
        getAllFileAndFolder(path);
        pathShow.setText(path.toString());
    }
}
