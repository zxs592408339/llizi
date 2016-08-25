package com.ittx.android1601.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ittx.android1601.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageViewFragment extends Fragment {
    private static final String IMAGE_URL = "image_url";
    private static final String PAGE_NO = "page_no";
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private TextView mPageNoTxt;
    private String mImageUrl;
    private int mPageNo;

    public static Fragment newInstance(String imageUrl,int postion) {
        ImageViewFragment fragment = new ImageViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_URL, imageUrl);
        bundle.putInt(PAGE_NO, postion);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() == null ? null : getArguments().getString(IMAGE_URL);
        mPageNo = getArguments() == null ? -1 : getArguments().getInt(PAGE_NO);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_view_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImageView = (ImageView) getView().findViewById(R.id.imageview_fragment_img);
        mProgressBar = (ProgressBar) getView().findViewById(R.id.image_fragment_progressbar);
        mPageNoTxt = (TextView) getView().findViewById(R.id.imageview_pageno_txt);

        mProgressBar.setVisibility(View.VISIBLE);
        Picasso.with(getContext()).load(mImageUrl).into(mImageView, new Callback() {
            @Override
            public void onSuccess() {
                mProgressBar.setVisibility(View.GONE);
                mPageNoTxt.setText(String.valueOf(mPageNo+1));
            }

            @Override
            public void onError() {
                mImageView.setImageResource(R.drawable.m1);
            }
        });
    }
}
