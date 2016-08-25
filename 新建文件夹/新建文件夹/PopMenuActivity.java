package com.ittx.android1601.ui.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ittx.android1601.R;

public class PopMenuActivity extends AppCompatActivity {
    private Button mPopMenuBtn;
    private ImageView mPopMenuImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_menu_layout);
        mPopMenuBtn = (Button) findViewById(R.id.menu_popmenu_btn);
        mPopMenuImg = (ImageView) findViewById(R.id.menu_pop_img);

        mPopMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(PopMenuActivity.this,v);
                popupMenu.inflate(R.menu.pop_menu);
                popupMenu.show();
            }
        });

        mPopMenuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(PopMenuActivity.this,v);
                popupMenu.inflate(R.menu.my_option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.my_option_add:
                                Toast.makeText(PopMenuActivity.this,"添加成功!",Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}
