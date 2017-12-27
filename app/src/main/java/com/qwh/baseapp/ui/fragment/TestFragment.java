package com.qwh.baseapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qwh.baseapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends BaseFragment {


    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_test, null);
        super.onCreateView(inflater, container, savedInstanceState);
        return mFragmentView;
    }

    @Override
    protected void initData() {
        setTitle("第二个测试界面");
    }

    @Override
    protected void initClickEvent(View view) {

    }

    @Override
    protected void initEvent() {

    }

}
