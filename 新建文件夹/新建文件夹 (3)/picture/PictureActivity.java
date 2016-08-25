package com.ittx.android1601.picture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ittx.android1601.R;
import com.ittx.android1601.utils.BitmapUtils;

import java.io.IOException;
import java.io.InputStream;

public class PictureActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mImageView;
    private Button mShowPictrueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_layout);
        mImageView = (ImageView) findViewById(R.id.picture_imageviedw);
        mShowPictrueBtn = (Button) findViewById(R.id.picture_btn);
        mShowPictrueBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.picture_btn:
//                getPictrue();
                showPic();
                break;
        }
    }

    public void getPictrue() {
        int size = 80;
        mImageView.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(getResources(),
                R.drawable.picture1, size, size));
    }

    public void showPic() {
        try {
            InputStream inputStream = getAssets().open("m10.jpg");

            //获得图片的宽、高
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, tmpOptions);
            int width = tmpOptions.outWidth;
            int height = tmpOptions.outHeight;

            inputStream = getAssets().open("m10.jpg");
            //设置显示图片的中心区域
            BitmapRegionDecoder bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = bitmapRegionDecoder.decodeRegion(
                    new Rect(width / 2 - 100, height / 2 - 100, width / 2 + 100, height / 2 + 100), options);
            mImageView.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
