package com.ittx.android1601;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RadioButtonActivity extends AppCompatActivity {
    public RadioButton mManRadioBtn, mWomanRadioBtn;
    public RadioGroup mRadioGroup, mFunRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_button_layout);
        mManRadioBtn = (RadioButton) findViewById(R.id.widget_radio_man_btn);
        mWomanRadioBtn = (RadioButton) findViewById(R.id.widget_radio_woman_btn);
        mRadioGroup = (RadioGroup) findViewById(R.id.widget_radioGroup);
        mFunRadioGroup = (RadioGroup) findViewById(R.id.fun_raidoGroup);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                System.out.println("checkedId :" + checkedId);

                if (checkedId == R.id.widget_radio_man_btn) {
                    RadioButton radioButton = (RadioButton) group.getChildAt(0);
                    System.out.println("你选中是 " + radioButton.getText());
                    Toast.makeText(RadioButtonActivity.this, "你选中是 " + radioButton.getText(), Toast.LENGTH_SHORT).show();
                }
                if (checkedId == R.id.widget_radio_woman_btn) {
                    RadioButton radioButton = (RadioButton) group.getChildAt(1);
                    System.out.println("你选中是 " + radioButton.getText());
                    Toast.makeText(RadioButtonActivity.this, "你选中是 " + radioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        mFunRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });


        RadioGroup customRadioGroup = (RadioGroup)findViewById(R.id.widget_custom_radiogroup);
        if(customRadioGroup != null) {
            customRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.widget_custome_radio_l_btn:
                            Snackbar.make(group,"老年",Snackbar.LENGTH_SHORT).show();
                            break;
                        case R.id.widget_custome_radio_q_btn:
                            Snackbar.make(group,"青年",Snackbar.LENGTH_SHORT).show();
                            break;
                        case R.id.widget_custome_radio_z_btn:
                            Snackbar.make(group,"中年",Snackbar.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }
    }
}
