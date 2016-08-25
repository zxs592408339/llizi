package com.ittx.android1601.music;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ittx.android1601.R;
import com.ittx.android1601.datastore.sqlite.DataColumn;
import com.ittx.android1601.logcat.Logs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static com.ittx.android1601.R.id.music_seekbar;

/**
 * 单曲循环
 * 全部循环
 * 顺序播放
 * 随机播放
 */
public class MusicActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int CURRENT_HANDLER_TYPE = 1;//当前时间
    public static int CURRENT_PLAY_MODEL = DataColumn.MusicPlayerModel.LOOPING_MODEL;//当前播放模式
    private Button mPlayPauseMusicBtn, mStopMusicBtn, mNextMusicBtn,mMusicModeBtn;
    private TextView mCurrentTimeTxt, mTotalTimeTxt, mMusicNameTxt;
    private MediaPlayer mMediaPlayer;
    private String mMusicDir;
    private SeekBar mSeekBar;

    private boolean isFlag = true;
    private SimpleDateFormat mDataFormat =new SimpleDateFormat("mm:ss");
    private int mCurrentProgress;
    private ArrayList<MusicBean> mDataLists;
    private int mCurrentPostion;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case CURRENT_HANDLER_TYPE:
                    int currentPostion = msg.arg2;
                    mSeekBar.setProgress(currentPostion);
                    mCurrentTimeTxt.setText(mDataFormat.format(new Date(currentPostion)));
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_layout);
        mPlayPauseMusicBtn = (Button) findViewById(R.id.music_play_btn);
        mStopMusicBtn = (Button) findViewById(R.id.music_stop_btn);
        mNextMusicBtn = (Button) findViewById(R.id.music_next_btn);
        mMusicModeBtn = (Button) findViewById(R.id.music_model_btn);
        mSeekBar = (SeekBar) findViewById(music_seekbar);
        mCurrentTimeTxt = (TextView) findViewById(R.id.music_current_time_txt);
        mTotalTimeTxt = (TextView) findViewById(R.id.music_total_time_txt);
        mMusicNameTxt = (TextView) findViewById(R.id.music_name_title_txt);

        mPlayPauseMusicBtn.setOnClickListener(this);
        mStopMusicBtn.setOnClickListener(this);
        mNextMusicBtn.setOnClickListener(this);
        mMusicModeBtn.setOnClickListener(this);

        Intent intent = getIntent();
        mDataLists = intent.getParcelableArrayListExtra("MUSIC_LIST");
        mCurrentPostion = intent.getIntExtra("CURRENT_POSITION", 0);

        playerCurrentMusic();

        new UpdateUIThread().start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_play_btn:
                playStopMusic();
                break;
            case R.id.music_stop_btn:
                stopResetMusic();
                break;
            case R.id.music_next_btn:
                nextMusic();
                break;
            case R.id.music_model_btn:
                setPlayMode();
                break;
        }
    }

    /**
     * 设置音乐播放模式
     */
    public void setPlayMode(){
        switch (CURRENT_PLAY_MODEL){
            case DataColumn.MusicPlayerModel.LOOPING_MODEL:
                CURRENT_PLAY_MODEL = DataColumn.MusicPlayerModel.ORDERING_MODEL;
                mMusicModeBtn.setText("顺序");
                break;
            case DataColumn.MusicPlayerModel.ORDERING_MODEL:
                CURRENT_PLAY_MODEL = DataColumn.MusicPlayerModel.ALL_LOOPING_MODEL;
                mMusicModeBtn.setText("全部循环");
                break;
            case DataColumn.MusicPlayerModel.ALL_LOOPING_MODEL:
                CURRENT_PLAY_MODEL = DataColumn.MusicPlayerModel.RANDOM_MODEL;
                mMusicModeBtn.setText("随机");
                break;
            case DataColumn.MusicPlayerModel.RANDOM_MODEL:
                CURRENT_PLAY_MODEL = DataColumn.MusicPlayerModel.LOOPING_MODEL;
                mMusicModeBtn.setText("单曲循环");
                break;
        }
    }

    public void nextMusic() {
        if (++mCurrentPostion == mDataLists.size()) {
            Toast.makeText(MusicActivity.this, "已是最后一首", Toast.LENGTH_SHORT).show();
            mCurrentPostion = mDataLists.size()-1;
        } else {
            playerCurrentMusic();
        }
    }

    public void precMusic(){
        if(--mCurrentPostion < 0 ){
            Toast.makeText(MusicActivity.this, "已是第一首", Toast.LENGTH_SHORT).show();
            mCurrentPostion = 0;
        }else{
            playerCurrentMusic();
        }
    }

    /**
     * 设置当前音乐资源并播放
     */
    public void playerCurrentMusic() {
        MusicBean musicBean = mDataLists.get(mCurrentPostion);
        mMusicNameTxt.setText(musicBean.getMusicName());
        mMusicDir = musicBean.getMusicPath();
        inintMusic();
        playStopMusic();
    }


    /**
     * 初始化音乐
     */
    private void inintMusic() {
        if (mMediaPlayer == null)
            mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource("file://" + mMusicDir);
            mMediaPlayer.prepare();

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    singleModel();
                    orderModel();
                    allLoopingModel();
                    randomModel();
                }
            });

            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mCurrentProgress = progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mMediaPlayer.seekTo(mCurrentProgress);
                }
            });

            int duration = mMediaPlayer.getDuration();
            mSeekBar.setMax(duration);
            mTotalTimeTxt.setText(mDataFormat.format(new Date(duration)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单曲播放
     */
    public void singleModel(){
        if(CURRENT_PLAY_MODEL == DataColumn.MusicPlayerModel.LOOPING_MODEL) {
            if (mMediaPlayer != null) {
                mMediaPlayer.setLooping(true); //单曲循环
            }
            playerCurrentMusic();
        }else {
            if (mMediaPlayer != null) {
                mMediaPlayer.setLooping(false); //单曲循环
            }
        }
    }

    /**
     * 顺序播放
     */
    public void orderModel(){
        if(CURRENT_PLAY_MODEL == DataColumn.MusicPlayerModel.ORDERING_MODEL){
            nextMusic();
        }
    }

    /**
     * 全部循环
     */
    public void allLoopingModel(){
        if(CURRENT_PLAY_MODEL == DataColumn.MusicPlayerModel.ALL_LOOPING_MODEL) {
            if (++mCurrentPostion == mDataLists.size()) {
                mCurrentPostion = 0;
            }
            playerCurrentMusic();
        }
    }
    /**
     * 随机播放
     */
    public void randomModel(){
        if(CURRENT_PLAY_MODEL == DataColumn.MusicPlayerModel.RANDOM_MODEL) {
            int number = mDataLists.size();
            Random random = new Random();
            mCurrentPostion = random.nextInt(number);
            playerCurrentMusic();
        }
    }

    /**
     * 播放，暂停
     */
    public void playStopMusic() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            Logs.e("pause >>>>");
            mPlayPauseMusicBtn.setText("播放");
        } else {
            mMediaPlayer.start();
            Logs.e("start >>>>Duration ：" + mMediaPlayer.getDuration() + " currentPostion ：" + mMediaPlayer.getCurrentPosition());
            mPlayPauseMusicBtn.setText("暂停");
        }
    }

    /**
     * 重置，初始化音乐
     */
    public void stopResetMusic() {
        mMediaPlayer.reset();
        Logs.e("reset >>>>");
        inintMusic();
        mPlayPauseMusicBtn.setText("播放");
    }

    /**
     * 更新播放进度
     */
    class UpdateUIThread extends Thread {
        @Override
        public void run() {
            while (isFlag) {
                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    int currentPostion = mMediaPlayer.getCurrentPosition();

                    Message message = Message.obtain();
                    message.arg1 = CURRENT_HANDLER_TYPE;
                    message.arg2 = currentPostion;
                    mHandler.sendMessage(message);

                    SystemClock.sleep(1000);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 释放音乐资源
         */
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            isFlag = false;
        }
    }
}
