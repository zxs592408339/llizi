package com.warmtel.volleyapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyErrorHelper;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import static com.warmtel.volleyapp.R.id.volley_image;


public class MainActivity extends AppCompatActivity {
    private String httpUrl = "http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html";
    private Button mGetBtn;
    private TextView mContentTxt;
    private ImageView mImageView;
    private NetworkImageView mNetWorkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGetBtn = (Button) findViewById(R.id.volley_get_data);
        mContentTxt = (TextView) findViewById(R.id.volley_content_txt);
        mImageView = (ImageView) findViewById(volley_image);
        mNetWorkImageView = (NetworkImageView) findViewById(R.id.volley_netimageview);

        mGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                doBitmap();
//                doBitmapByImageLoader();
                doBitmapByNetImageView();
            }
        });
    }
    public void doBitmapByNetImageView(){
        String imageUrl = "http://a.hiphotos.baidu.com/pic/w%3D230/sign=f71ba7639c16fdfad86cc1ed848e8cea/241f95cad1c8a78699e316466609c93d71cf50a8.jpg";
        mNetWorkImageView.setDefaultImageResId(android.R.drawable.btn_star_big_off);
        mNetWorkImageView.setErrorImageResId(android.R.drawable.ic_delete);
        mNetWorkImageView.setImageUrl(imageUrl,RequestManager.getImageLoader());
    }


    public void doBitmapByImageLoader() {

        String imageUrl = "http://d.hiphotos.baidu.com/pic/w%3D230/sign=e92db34bc995d143da76e32043f18296/8601a18b87d6277f35a56d7029381f30e824fcc7.jpg";
        ImageLoader imageLoader = RequestManager.getImageLoader();
        imageLoader.get(imageUrl, ImageLoader.getImageListener(mImageView, android.R.drawable.btn_star, android.R.drawable.ic_delete));
    }


    public void doBitmap() {
        String imageUrl = "http://a.hiphotos.baidu.com/pic/w%3D230/sign=bf59456cc9fcc3ceb4c0ce30a244d6b7/4afbfbedab64034f80b90b48aec379310a551d0c.jpg";
        ImageRequest request = new ImageRequest(imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        mImageView.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorstry = VolleyErrorHelper.getMessage(error, MainActivity.this);
                        mContentTxt.setText(errorstry);
                    }
                });

        RequestManager.addRequest(request, this);
    }

    /**
     * 获取字符串数据
     */
    public void doString() {
        StringRequest request = new StringRequest(Request.Method.GET, httpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mContentTxt.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorstry = VolleyErrorHelper.getMessage(error, MainActivity.this);
                        mContentTxt.setText(errorstry);
                    }
                }
        );

        RequestManager.addRequest(request, this);


    }


}
