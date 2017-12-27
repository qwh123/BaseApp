package com.qwh.baseapp.ui.activity;

import android.app.Application;

import com.qwh.baseapp.base.ActivityManager;


public class App extends Application {

	private static App app;
	public static String version;

	// 获取单例
	public static App getInstance() {
		return app;
	}

	@Override
	public void onCreate() {
		app = this;
		super.onCreate();
		version="100001";//AndroidTools.getApplicationVersionName(this).substring(0, 6);
	}

	/**
	 * 退出程序
	 * 
	 * 退出程序只需要把Activity全部销毁就可以，application系统过段时间会自动回收，不会马上销毁
	 * 要想完整的退出程序，在Activity跳转的时候及时销毁没用的，对Activity生命周期进行严格的控制
	 */
	public void exit() {
		ActivityManager.finishAllActivity();
	}

}
