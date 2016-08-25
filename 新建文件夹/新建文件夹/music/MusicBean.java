package com.ittx.android1601.music;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/7/5.
 */
public class MusicBean implements Parcelable{
    private String musicName;
    private String musicPath;

    public MusicBean(){}


    protected MusicBean(Parcel in) {
        musicName = in.readString();
        musicPath = in.readString();
    }

    public static final Creator<MusicBean> CREATOR = new Creator<MusicBean>() {
        @Override
        public MusicBean createFromParcel(Parcel in) {
            return new MusicBean(in);
        }

        @Override
        public MusicBean[] newArray(int size) {
            return new MusicBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(musicName);
        dest.writeString(musicPath);
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }


}
