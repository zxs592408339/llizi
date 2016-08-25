package com.ittx.android1601.music;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.ittx.android1601.R;

import java.io.IOException;

public class MusicService extends Service {
    private String mMusicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)+"/jinsha.mp3";
    private MediaPlayer mMediaPlayer;
    private NotificationManager mNotificationManager;
    interface MusicPlayerService{
        boolean IIsPlayering(); //是否正在播放
        void IPlayerMusic();    //播放暂停音乐
        int ICurrentTime();  //当前播放时间
        int ITotalTime();  //播放总时间
        void ICustomeNotify();
    }

    class MusicPlayerBinder extends Binder implements  MusicPlayerService{

        @Override
        public boolean IIsPlayering() {
            return mMediaPlayer.isPlaying();
        }

        @Override
        public void IPlayerMusic() {
            if(IIsPlayering()){
                mMediaPlayer.pause();
            }else{
                mMediaPlayer.start();
            }

        }

        @Override
        public int ICurrentTime() {
            return mMediaPlayer.getCurrentPosition();
        }

        @Override
        public int ITotalTime() {
            return mMediaPlayer.getDuration();
        }

        @Override
        public void ICustomeNotify() {
            customeNotify();
        }
    }

    public MusicPlayerBinder musicPlayerBinder = new MusicPlayerBinder();

    private BroadcastReceiver musicReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.ittx.android1601.MUSIC_NOTIFY_PLAYER")){
                musicPlayerBinder.IPlayerMusic();
                customeNotify();

                sendBroadcast(new Intent(MusicPlayerActivity.MUSIC_PLAYER_RECEIVER));
            }
        }
    };
    @Override
    public void onCreate() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        inintMusic();

        IntentFilter intentFilter = new IntentFilter("com.ittx.android1601.MUSIC_NOTIFY_PLAYER");
        registerReceiver(musicReceiver,intentFilter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicPlayerBinder;
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
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        unregisterReceiver(musicReceiver);
    }

    /**
     * 自定义通知
     */
    public void customeNotify(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
        builder.setContent(remoteViews);
        builder.setSmallIcon(R.drawable.list1);
        builder.setTicker("音乐播放器通知");

        Intent intent = new Intent("com.ittx.android1601.MUSIC_NOTIFY_PLAYER");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_pause,pendingIntent);

        if(musicPlayerBinder.IIsPlayering()) {
            remoteViews.setImageViewResource(R.id.iv_pause, R.drawable.nc_pause);
        }else {
            remoteViews.setImageViewResource(R.id.iv_pause, R.drawable.nc_play);
        }
        Notification notification = builder.build();
        mNotificationManager.notify(11,notification);
    }
}
