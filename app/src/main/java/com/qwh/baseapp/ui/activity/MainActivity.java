package com.qwh.baseapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qwh.baseapp.R;
import com.qwh.baseapp.base.CommonThread;
import com.qwh.baseapp.interfaces.ThreadCallBack;
import com.qwh.baseapp.listener.KeyDownListener;
import com.qwh.baseapp.utils.LoggerUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 收单主界面
 *
 * @author CB
 * @time 2015-5-6 上午9:40:01
 */
public class MainActivity extends BaseActivity {


    /**
     * 栈顶fragment
     */
    public Fragment lastFragment;

    /**
     * 当前的fragment
     */
    public Fragment currentFragment;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.txt_center)
    TextView mTxtCenter;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.ll_right)
    LinearLayout mLlRight;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.fl_fragment)
    FrameLayout mFlFragment;

    /**
     * 是否锁屏
     */
    @SuppressWarnings("unused")
    private boolean isLock = false;

    private static MainActivity mainActivity;

    private KeyDownListener keyDownListener;

    private Intent intent;
    private CommonThread queueThread;
    private List<ThreadCallBack> queueTasks;
    private boolean isSecond = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        View view = View.inflate(this, R.layout.activity_main, null);
        setContentView(view);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onStart() {
        super.onStart();
        this.intent = getIntent();
        if (isSecond) {
            return;
        }
//        new SelfCheckFragment().thirdInvokeCheckInit(context);
        returnFirstMainMenu();
        isSecond = true;
    }

    @Override
    protected void initData() {
        mainActivity = this;
        queueTasks = new ArrayList<ThreadCallBack>();
        if (queueThread != null) {
            queueThread.stop();
            queueThread = null;
        }
        queueThread = new CommonThread(false, 100, true, new ThreadCallBack() {
            ThreadCallBack task = null;

            @Override
            public void onBackGround() {
                if (queueTasks.size() > 0) {
                    try {
                        task = queueTasks.get(0);
                        task.onBackGround();
                    } catch (Exception e) {
                        LoggerUtils.d("queueThread onBackGround", e);
                    }
                }
            }

            @Override
            public void onMain() {
                if (task != null) {
                    try {
                        task.onMain();
                    } catch (Exception e) {
                        LoggerUtils.d("queueThread onMain", e);
                    }
                    queueTasks.remove(task);
                    task = null;
                }
            }
        });
        queueThread.start();
    }

    @OnClick({R.id.iv_left})
    @Override
    protected void initClickEvent(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                backFragment();
                break;
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        if (queueThread != null) {
            queueThread.stop();
            queueThread = null;
        }
        super.onDestroy();
    }

    /**
     * 添加队列任务
     *
     * @param threadCallBack
     */
    public void addQueueTask(ThreadCallBack threadCallBack) {
        if (queueThread != null) {
            queueTasks.add(threadCallBack);
        }
    }

    public static MainActivity getInstance() {
        return mainActivity;
    }

    /**
     * 主界面样式
     */
    public void setMainStyle() {
        mIvLeft.setVisibility(View.GONE);
    }

    /**
     * 设置标题
     */
    public void setTitle(int title) {
        setTitle(title, true);
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        setTitle(title, true);
    }

    public void setTitle(int title, boolean isAddBack) {
        setTitle(getString(title), isAddBack);
    }

    public void setTitle(String title, boolean isAddBack) {
        LoggerUtils.i("title:" + title);
        mTxtCenter.setText(title);
        showTitle();
    }

    /**
     * 获取标题
     */
    public String getMainTitle() {
        if (mTxtCenter.getText() != null) {
            return mTxtCenter.getText().toString();
        } else {
            return null;
        }
    }

    /**
     * 设置标题栏上的返回图标的按键事件
     */
    public void setTitleBarGoBackEvent(OnClickListener onClickListener) {
        if (mIvLeft != null) {
            mIvLeft.setOnClickListener(onClickListener);
        }
    }

    /**
     * 锁定
     */
    public void lock() {
        isLock = true;
    }

    /**
     * 解锁
     */
    public void unLock() {
        isLock = false;
    }

    public void hideTitle() {
        mRlTitle.setVisibility(View.GONE);
    }

    public void showTitle() {
        if (!mRlTitle.isShown())
            mRlTitle.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LoggerUtils.e("Main onKeyDown");
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            if (keyDownListener != null) {
                return keyDownListener.onHome();
            }
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean result = false;
            if (keyDownListener != null) {
                result = keyDownListener.onBack();
            }

            if (result) {
                backFragment();
            }
        }
        return false;
    }

    /**
     * 设置返回监听
     *
     * @param keyDownListener
     */
    public void setKeyDownListener(KeyDownListener keyDownListener) {
        this.keyDownListener = keyDownListener;
    }


    /**
     * < 设置导航栏模式
     */

    public void setSignInMode() {
        mLlRight.setVisibility(View.VISIBLE);
        mIvRight.setImageResource(R.mipmap.ic_launcher);
        mTvRight.setText("测试");
    }
    public void setManageMode(){
        mLlRight.setVisibility(View.VISIBLE);
        mIvRight.setImageResource(android.R.drawable.ic_menu_send);
        mTvRight.setText("管理");
    }
    public void closeActionBar() {
        mLlRight.setVisibility(View.GONE);
    }

    public void setActionBarListener(OnClickListener listener) {
        mLlRight.setOnClickListener(listener);
    }


}
