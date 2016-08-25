package com.ittx.android1601.music;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
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
import com.ittx.android1601.logcat.Logs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ittx.android1601.R.id.music_seekbar;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CURRENT_HANDLER_TYPE = 1;//当前时间
    private Button mPlayPauseMusicBtn, mStopMusicBtn;
    private TextView mCurrentTimeTxt,mTotalTimeTxt;
    private MediaPlayer mMediaPlayer;
    private String mMusicDir;
    private SeekBar mSeekBar;
    private boolean isFlag = true;
    private SimpleDateFormat mDataFormat = new SimpleDateFormat("mm:ss");
    private int mCurrentProgress;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1){
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
        mSeekBar = (SeekBar) findViewById(music_seekbar);
        mCurrentTimeTxt = (TextView) findViewById(R.id.music_current_time_txt);
        mTotalTimeTxt = (TextView) findViewById(R.id.music_total_time_txt);

        mPlayPauseMusicBtn.setOnClickListener(this);
        mStopMusicBtn.setOnClickListener(this);

        mMusicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/BLUE.mp3"; //mnt/sdcard/music/BLUE.mp3
        inintMusic();
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
        }
    }
    /**
     * 初始化音乐
     */
    private void inintMusic() {
        if (mMediaPlayer == null)
            mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource("file://" + mMusicDir);
            mMediaPlayer.prepare();

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Toast.makeText(MusicActivity.this,"播放完成",Toast.LENGTH_SHORT).show();
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
     * 播放，暂停
     */
    public void playStopMusic() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            Logs.e("pause >>>>");
            mPlayPauseMusicBtn.setText("播放");
        } else {
            mMediaPlayer.start();
            Logs.e("start >>>>Duration ："+mMediaPlayer.getDuration() + " currentPostion ："+mMediaPlayer.getCurrentPosition());
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
    class UpdateUIThread extends Thread{
        @Override
        public void run() {
            while (isFlag) {
                if(mMediaPlayer != null && mMediaPlayer.isPlaying()) {
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
