package com.ittx.android1601.fragment.life;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ittx.android1601.R;
import com.ittx.android1601.logcat.Logs;

/**
 * A simple {@link Fragment} subclass.
 */
public class LifeFragment extends Fragment {

    public LifeFragment() {
        // Required empty public constructor
        Logs.e("LifeFragment() 构造方法");
    }

    /**
     * 绑定Fragment
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logs.e("LifeFragment onAttach  >>>>");
    }

    /**
     * 创建Fragment
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logs.e("LifeFragment onCreate  >>>>");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Logs.e("LifeFragment onCreateView  >>>>");
        return inflater.inflate(R.layout.fragment_life_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logs.e("LifeFragment onActivityCreated  >>>>");
    }

    @Override
    public void onStart() {
        super.onStart();
        Logs.e("LifeFragment onStart  >>>>");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logs.e("LifeFragment onResume  >>>>");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logs.e("LifeFragment onPause  >>>>");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logs.e("LifeFragment onStop  >>>>");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logs.e("LifeFragment onDestroyView  >>>>");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logs.e("LifeFragment onDestroy  >>>>");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logs.e("LifeFragment onDetach  >>>>");
    }
}
