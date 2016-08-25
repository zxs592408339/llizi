package com.warmtel.anim;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnimationPropertyActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mValueAnimatorBtn;
    private TextView mAnimatiorTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_main_layout);
        mValueAnimatorBtn = (Button) findViewById(R.id.animator_value_btn);
        mAnimatiorTxt = (TextView) findViewById(R.id.animator_txt);
        mValueAnimatorBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.animator_value_btn) {
            testObjectAmimation();
        }
    }

    public void testValueAnimation(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f,1f);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                Log.e("tag",""+currentValue);
            }
        });
        valueAnimator.start();
    }

    public void testObjectAmimation(){
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mAnimatiorTxt,"alpha",1f,0f,1f);//淡入淡出
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mAnimatiorTxt,"rotation",0,360);//旋转

//        float translationX = mAnimatiorTxt.getTranslationX();
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mAnimatiorTxt,"translationX",translationX,-500f,500f,translationX); //平移

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mAnimatiorTxt,"scaleY",1f,3f);  //缩放
        objectAnimator.setDuration(5000);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                Log.e("tag",""+currentValue);
            }
        });
        objectAnimator.start();
    }
}
