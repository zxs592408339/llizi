package com.ittx.android1601.service.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ittx.android1601.R;

public class DownLoadFileActivity extends AppCompatActivity implements OnClickListener{
    private Button mDownFile1Btn,mDownFile2Btn,mDownFile3Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load_file_layout);

        mDownFile1Btn = (Button) findViewById(R.id.down_file1_btn);
        mDownFile2Btn = (Button) findViewById(R.id.down_file2_btn);
        mDownFile3Btn = (Button) findViewById(R.id.down_file3_btn);

        mDownFile1Btn.setOnClickListener(this);
        mDownFile2Btn.setOnClickListener(this);
        mDownFile3Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case  R.id.down_file1_btn:
                startDownService("文件1","http://xx.xxx.xxx/file1.txt");
                break;
            case R.id.down_file2_btn:
                startDownService("文件2","http://xx.xxx.xxx/file2.txt");
                break;
            case R.id.down_file3_btn:
                startDownService("文件3","http://xx.xxx.xxx/file3.txt");
                break;
        }
    }

    /**
     * 后台下载文件
     * @param fileName
     * @param fileDir
     */
    public void startDownService(String fileName, final String fileDir){
        Intent intent = new Intent(this,DownLoadIntentService.class);
        intent.putExtra("FileName",fileName);
        intent.putExtra("FileDir",fileDir);
        startService(intent);
    }



}
