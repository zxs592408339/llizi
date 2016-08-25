package com.ittx.android1601.datastore.file;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileSearchActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    private Button mSearchBtn;
    private ListView mListView;
    private List<String> mDataLists = new ArrayList<>();
    private FileDirSearchAdapter mFileSearchAdpater;
    private File mCurrentFile; //向上返回使用
    private File mRootFile;    //判断是否退出activity

    private Handler mHandler = new Handler() {
        /**
         * 处理消息
         */
        @Override
        public void handleMessage(Message msg) {
            List<File> list = (List<File>) msg.obj;
//            mFileSearchAdpater.setDataLists(list);
//            mListView.setSelection(list.size() - 1);
            mFileSearchAdpater.setDataLists(list);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_search_layout);
        mSearchBtn = (Button) findViewById(R.id.file_search_btn);
        mListView = (ListView) findViewById(R.id.file_search_listview);

        mSearchBtn.setOnClickListener(this);
        mListView.setOnItemClickListener(this);

        mFileSearchAdpater = new FileDirSearchAdapter(this);
        mListView.setAdapter(mFileSearchAdpater);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.file_search_btn:
                mRootFile = Environment.getExternalStorageDirectory();
                mCurrentFile = mRootFile;
                startThreadSearchFile(mRootFile);
                break;
        }
    }

    /**
     * 启动子线程搜索指定文件目录通过Handler发送给UI主线程处理
     */
    public void startThreadSearchFile(final File dirFile){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<File> dataLists = searchFileDir(dirFile);

                Message message = Message.obtain();
                message.obj = dataLists;
                mHandler.sendMessage(message);        //发送消息
            }
        }).start();
    }


    /**
     * 递归遍历文件目录
     */
    public void searchFile(File directorFile) {
        File[] files = directorFile.listFiles(); //返回directorFile下所有文件和文件目录
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchFile(file);
                } else {
                    if (file.getName().endsWith(".mp3")) {
                        Logs.e(file.getPath());
                        mDataLists.add(file.getPath());

                    }
                }
            }
        }
    }

    public  List<File> searchFileDir(File rootFile){
        List<File> mDataLists = new ArrayList<>();
        File[] fileLists = rootFile.listFiles();
        if(fileLists !=null){
            for(File file : fileLists){
                mDataLists.add(file);
            }
        }
        return mDataLists;
    }

    /**
     * 过虑map3文件，添加到数据源mDataLists
     *
     * @param file
     */
    public void initDataListsMp3(File file) {
        String fileName = file.getName(); //xx.mp3  xxx/xx
        int index = fileName.indexOf(".");
        if (index == -1) {
            return;
        }
        String fileType = fileName.substring(index);
        if (fileType.trim().equalsIgnoreCase(".mp3")) {
            Logs.e(file.getPath());
            mDataLists.add(file.getPath());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileDirSearchAdapter adapter = (FileDirSearchAdapter) parent.getAdapter();
        File itemFile = (File) adapter.getItem(position);
        mCurrentFile = itemFile;

        if(itemFile.isDirectory()){
            startThreadSearchFile(itemFile);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(mRootFile.getPath().equals(mCurrentFile.getPath())){
                finish();
            }else{
                mCurrentFile = mCurrentFile.getParentFile();
                startThreadSearchFile(mCurrentFile);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 隐示调用音乐播放器
     * @param item
     */
    public void playMusicByAction(String item){
        Uri uri = Uri.parse("file://"+item);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri,"audio/mp3");
        startActivity(intent);
    }

    /**
     * 通过MediaPlayer实现音乐播放
     * @param fileDir: "file:///mnt/sdcard/music/BLUE.mp3";
     */
    public void playMusic(String fileDir){
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(fileDir);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class FileDirSearchAdapter extends BaseAdapter {
        private List<File> dataLists = new ArrayList<>();
        private LayoutInflater inflater;

        public FileDirSearchAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setDataLists(List<File> dataLists) {
            this.dataLists = dataLists;
            notifyDataSetChanged();//通知刷新ListView 适配器数据源
        }

        @Override
        public int getCount() {
            return dataLists.size();
        }

        @Override
        public Object getItem(int position) {
            return dataLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO: 2016/7/1 ListView每项View获取  布局和控件
            HolderView holderView;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.file_list_item, null);
                TextView fileNameTxt = (TextView) convertView.findViewById(R.id.file_name);
                ImageView fileIconImg = (ImageView) convertView.findViewById(R.id.file_icon);

                holderView = new HolderView();
                holderView.fileNameTxt = fileNameTxt;
                holderView.fileIconImg = fileIconImg;
                convertView.setTag(holderView);
            } else {
                holderView = (HolderView) convertView.getTag();
            }
            // TODO: 2016/7/1 ListView每项数据源 getItem 
            File item = (File) getItem(position);

            // TODO: 2016/7/1 数据项和控件关连
            if(item.isDirectory()){
                holderView.fileIconImg.setImageResource(R.drawable.folder);
            }else{
                holderView.fileIconImg.setImageResource(R.drawable.file);
            }
            holderView.fileNameTxt.setText(item.getName());

            return convertView;
        }
        class HolderView{
            TextView fileNameTxt;
            ImageView fileIconImg;
        }
    }


    class FileSearchAdapter extends BaseAdapter {
        private List<String> dataLists = new ArrayList<>();
        private LayoutInflater inflater;

        public FileSearchAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void setDataLists(List<String> dataLists) {
            this.dataLists = dataLists;
            notifyDataSetChanged();//通知刷新ListView 适配器数据源
        }

        @Override
        public int getCount() {
            return dataLists.size();
        }

        @Override
        public Object getItem(int position) {
            return dataLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView itemTxt;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_adapter_array_view, null);
                itemTxt = (TextView) convertView.findViewById(R.id.item_text);
                convertView.setTag(itemTxt);
            } else {
                itemTxt = (TextView) convertView.getTag();
            }

            String item = (String) getItem(position);
            itemTxt.setText(item);

            return convertView;
        }
    }
}
