package com.ittx.android1601.picture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ittx.android1601.R;

import java.io.IOException;
import java.io.InputStream;

public class BigPictureActivity extends AppCompatActivity {
    private LargeImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_picture_layout);
        mImageView = (LargeImageView) findViewById(R.id.big_imageview);

        try {
            InputStream inputStream = getAssets().open("m100.jpg");
            mImageView.setInputStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
