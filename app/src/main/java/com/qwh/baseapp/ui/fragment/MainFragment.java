package com.qwh.baseapp.ui.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.qwh.baseapp.R;
import com.qwh.baseapp.ui.activity.MainActivity;

import butterknife.OnClick;

/**
 */
@SuppressLint("ValidFragment")
public class MainFragment extends BaseFragment {

	private String title;

	public MainFragment(String title) {
		this.title = title;
	}

	public MainFragment(int title) {
		this.title = MainActivity.getInstance().getString(title);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mFragmentView = inflater.inflate(R.layout.fragment_main, null);
		super.onCreateView(inflater, container, savedInstanceState);
		return mFragmentView;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initData() {
		setTitle("主页");
	}
@OnClick(R.id.txt_test)
	@Override
	protected void initClickEvent(View view) {
		activity.switchContent(new TestFragment());
	}

	@Override
	protected void initEvent() {

	}


}
