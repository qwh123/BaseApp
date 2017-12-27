package com.qwh.baseapp.interfaces;
/**
 * 线程运行回调
 * 
 * @author qwh
 */
public interface ThreadCallBack {

	/**
	 * 在后台执行不会阻塞UI
	 */
	void onBackGround();
	
	/**
	 * 在主线程执行，会阻塞UI
	 */
	void onMain();
}
