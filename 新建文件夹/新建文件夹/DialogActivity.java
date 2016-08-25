package com.ittx.android1601.ui.dialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ittx.android1601.R;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mProgressBtn,mDialogConfirmBtn,mDialogSelectBtn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_layout);
        mProgressBtn = (Button) findViewById(R.id.dialog_progress_btn);
        mDialogConfirmBtn = (Button) findViewById(R.id.dialog_alert_confirg_btn);
        mDialogSelectBtn = (Button) findViewById(R.id.dialog_alert_select_btn);
        mProgressBtn.setOnClickListener(this);
        mDialogConfirmBtn.setOnClickListener(this);
        mDialogSelectBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.dialog_progress_btn:
                progressDialog = new ProgressDialog(this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("文件正在下载中...");
                progressDialog.show(); //显示
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 1; i <= 100; i++){
                            progressDialog.setProgress(i);
                            SystemClock.sleep(50);
                        }
                        progressDialog.dismiss();//取消
                    }
                }).start();
                break;
            case R.id.dialog_alert_confirg_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("操作提示");
                builder.setMessage("是否退出程序!");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,"取消成功!",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNeutralButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,"继续成功!",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,"操作成功!",Toast.LENGTH_SHORT).show();
                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.dialog_alert_select_btn:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("兴趣爱好");
                String[] singleChoices = new String[]{"读书","写字","运动","画画"};
                builder.setSingleChoiceItems(singleChoices, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,"你选择是 "+which,Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,"操作成功!",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this,"取消成功!",Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog1 = builder.create();
                dialog1.show();

                break;
        }
    }
}
