package com.ittx.android1601.music;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ittx.android1601.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String MUSIC_PLAYER_RECEIVER = "com.ittx.android1601.MUSIC_PLAYER";
    private ImageView mPlayerMusicImg;
    private TextView mCurrentTimeTxt,mTotalTimeTxt;
    private SeekBar mSeekBar;
    private MusicService.MusicPlayerService musicPlayerService;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
    private boolean isUpdateThreadFalg = true;
    private Handler mHandler = new Handler();
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicPlayerService = (MusicService.MusicPlayerService) service;
            musicPlayerService.IPlayerMusic();
            musicPlayerService.ICustomeNotify();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicPlayerService = null;
        }
    };

    private BroadcastReceiver musicReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MUSIC_PLAYER_RECEIVER)){
                if(musicPlayerService.IIsPlayering()){
                    mPlayerMusicImg.setImageResource(R.drawable.player_pause);
                }else{
                    mPlayerMusicImg.setImageResource(R.drawable.player_play);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player_layout);

        mPlayerMusicImg = (ImageView) findViewById(R.id.player_pause_img);
        mCurrentTimeTxt = (TextView) findViewById(R.id.player_current_time_txt);
        mTotalTimeTxt = (TextView) findViewById(R.id.player_total_time_txt);
        mSeekBar = (SeekBar) findViewById(R.id.player_seekbar);
        mPlayerMusicImg.setOnClickListener(this);

        Intent intent = new Intent(this,MusicService.class);
        startService(intent);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);

        new UpdateMusicThread().start();

        mPlayerMusicImg.setImageResource(R.drawable.player_pause);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(musicReceiver,new IntentFilter(MUSIC_PLAYER_RECEIVER));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(musicReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.player_pause_img:
                musicPlayerService.IPlayerMusic();

                if(musicPlayerService.IIsPlayering()){
                    mPlayerMusicImg.setImageResource(R.drawable.player_pause);
                }else{
                    mPlayerMusicImg.setImageResource(R.drawable.player_play);
                }

                break;
        }
    }

    class UpdateMusicThread extends Thread{
        @Override
        public void run() {
            while (isUpdateThreadFalg){
                if(musicPlayerService != null && musicPlayerService.IIsPlayering()){

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            int curentTime = musicPlayerService.ICurrentTime();
                            int totalTime = musicPlayerService.ITotalTime();
                            mSeekBar.setMax(totalTime);
                            mSeekBar.setProgress(curentTime);
                            mCurrentTimeTxt.setText(simpleDateFormat.format(new Date(curentTime)));
                            mTotalTimeTxt.setText(simpleDateFormat.format(new Date(totalTime)));
                        }
                    });
                    SystemClock.sleep(1000);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serviceConnection != null)
           unbindService(serviceConnection);
    }
}
