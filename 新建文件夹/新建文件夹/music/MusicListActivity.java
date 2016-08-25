package com.ittx.android1601.music;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ittx.android1601.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ittx.android1601.R.id.music_name_txt;

public class MusicListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private final static String MP3 = ".mp3";
    private ListView mListView;
    private ArrayList<MusicBean> mDataLists = new ArrayList<>();
    private MusicListAdapter mMusicListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list_layout);
        mListView = (ListView) findViewById(R.id.music_listview);
        mListView.setOnItemClickListener(this);

        File rootFile = Environment.getExternalStorageDirectory(); //mnt/sdcard/
        searchFile(rootFile);

        mMusicListAdapter = new MusicListAdapter(this);
        mListView.setAdapter(mMusicListAdapter);

        mMusicListAdapter.setDataLists(mDataLists);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MusicListAdapter adapter = (MusicListAdapter) parent.getAdapter();
        MusicBean musicBean = (MusicBean) adapter.getItem(position);

        Intent intent = new Intent(this,MusicActivity.class);
//        intent.putExtra("MUSICBEAN",musicBean);
        intent.putParcelableArrayListExtra("MUSIC_LIST",mDataLists);
        intent.putExtra("CURRENT_POSITION",position);
        startActivity(intent);
    }

    /**
     * 递归遍历获取数据源
     *
     * @param rootFile
     */
    public void searchFile(File rootFile) {
        File[] fileLists = rootFile.listFiles();
        if (fileLists != null) {
            for (File file : fileLists) {
                if (file.isDirectory()) {
                    searchFile(file);
                } else {
                    if (file.getName().endsWith(MP3)) {
                        MusicBean musicBean = new MusicBean();  //XXX.mp3
                        String musicName = file.getName();
                        musicBean.setMusicName(musicName.substring(0, musicName.length() - MP3.length()));
                        musicBean.setMusicPath(file.getPath());

                        mDataLists.add(musicBean);
                    }
                }
            }
        }
    }


    /**
     * 定义适配器
     */
    class MusicListAdapter extends BaseAdapter {
        private List<MusicBean> dataLists = new ArrayList<>();
        private LayoutInflater layoutInflater;

        public MusicListAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        public void setDataLists(List<MusicBean> list) {
            dataLists = list;
            notifyDataSetChanged();
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
            HolderView holderView;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_list_music_layout, null);
                holderView = new HolderView();
                holderView.musicNameTxt = (TextView) convertView.findViewById(music_name_txt);
                holderView.musicPathTxt = (TextView) convertView.findViewById(R.id.music_path_txt);
                convertView.setTag(holderView);
            } else {
                holderView = (HolderView) convertView.getTag();
            }

            MusicBean musicBean = (MusicBean) getItem(position);
            holderView.musicNameTxt.setText(musicBean.getMusicName());
            holderView.musicPathTxt.setText(musicBean.getMusicPath());

            return convertView;
        }

        class HolderView {
            TextView musicNameTxt;
            TextView musicPathTxt;
        }
    }


}
