package com.ittx.android1601.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.ittx.android1601.R;
import com.ittx.android1601.ui.ProgressBarActivity;

import java.lang.reflect.Field;

/**
 * Menu 菜单
 * 三种类型
 *   选项菜单      optionMenu       两种型式
 *                                       1.硬件Menu菜单
 *                                       2.ActionBar
 *                                       3.ToolBar
 *   上下文菜单    contextMenu
 *   弹出菜单      popMenu
 */
public class MenuOptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_option_layout);

        setOverFlowShowingAlways();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_option_menu,menu);

//        menu.add(1,100,1,"设置");
//        menu.add(1,101,1,"添加");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.my_option_add:
                Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_option_delete:
                startActivity(new Intent(this, ProgressBarActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *实现手机硬件有菜单Menu键也作为ActionBar动作条形式显示
     */
    private void setOverFlowShowingAlways() {
        ViewConfiguration config = new ViewConfiguration().get(this);
        Field menuKeyField = null;
        try {
            menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
