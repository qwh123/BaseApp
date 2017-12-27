package com.qwh.baseapp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;


import com.qwh.baseapp.base.AndroidTools;
import com.qwh.baseapp.ui.activity.MainActivity;
import com.qwh.baseapp.listener.KeyDownListener;
import com.qwh.baseapp.utils.LoggerUtils;

import butterknife.ButterKnife;

/**
 * 基础类
 * 继承该类需将mFragmentView = inflater.inflate(R.layout.xxx, null);方法写在super.onCreateView(inflater, container, savedInstanceState);前面
 *
 * @author qwh
 * @ClassName: BaseYYFragment
 * @date 2014年9月11日 下午3:10:37
 */
public abstract class BaseFragment extends Fragment {

    protected Context context;
    protected MainActivity activity;
    protected View mFragmentView;
    private boolean isShowStatus = true;
    private String title;


    /**
     * 该方法调用顺序优于onCreate
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isShowStatus) {
            LoggerUtils.v(getClass().getSimpleName() + "->setUserVisibleHint[isVisibleToUser:" + isVisibleToUser
                    + ",this.isVisible():" + this.isVisible() + "]");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isShowStatus) {
            LoggerUtils.v(getClass().getSimpleName() + "-> onCreate");
        }
        this.context = getActivity();
        this.activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (isShowStatus) {
            LoggerUtils.v(getClass().getSimpleName() + "-> onCreateView");
        }
        ButterKnife.bind(this, mFragmentView);

        //立即加载，只执行于此
        rushLoad();

        activity.setKeyDownListener(new KeyDownListener() {

            @Override
            public boolean onHome() {
                // HOME键处理
                final Fragment topFragment = AndroidTools.getTopFragment(activity);
                if (topFragment != null) {
                    return ((BaseFragment) topFragment).doClickHomeEvent();
                } else {
                    return doClickHomeEvent();
                }
            }

            @Override
            public boolean onBack() {
                // back键处理
                final Fragment topFragment = AndroidTools.getTopFragment(activity);
                if (topFragment != null) {
                    return ((BaseFragment) topFragment).doClickBackEvent();
                } else {
                    return doClickBackEvent();
                }
            }
        });

        /**
         * 设置标题栏上面返回按钮事件
         */
        activity.setTitleBarGoBackEvent(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final Fragment topFragment = AndroidTools.getTopFragment(activity);
                boolean result = false;
                if (topFragment != null) {
                    result = ((BaseFragment) topFragment).doClickBackEvent();
                } else {
                    result = doClickBackEvent();
                }
                if (result) {
                    activity.backFragment();
                }
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    public boolean doClickBackEvent() {

        final Fragment topFragment = AndroidTools.getTopFragment(activity);
        if (topFragment != null) {
            LoggerUtils.d("111 doClickBackEvent 栈顶界面：" + topFragment.getClass().getName());
            if (((BaseFragment) topFragment).onBackKeyDown()) {
                return false;
            }
        }
        return false;
    }

    // 处理Home事件响应
    protected boolean doClickHomeEvent() {
        Fragment topFragment = AndroidTools.getTopFragment(activity);
        return false;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (isShowStatus) {
            LoggerUtils.v(getClass().getSimpleName() + "-> onActivityCreated");
        }
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化点击事件
     *
     * @return 返回"是否执行中" true为执行中,false为未执行中
     */
    protected abstract void initClickEvent(View view);

    /**
     * 初始化事件
     */
    protected abstract void initEvent();


    /**
     * 立即
     */
    protected void rushLoad() {
        initData();
        initEvent();
    }

    /**
     * 拦截返回事件，返回true-拦截
     *
     * @return
     */
    protected boolean onBackKeyDown() {
        return false;
    }

    /**
     * 是否取消交易点击确认
     */
    protected void onTransCancel() {
    }

    ;

    /**
     * @param @param  id
     * @param @return 设定文件
     * @return View    返回类型
     * @throws
     * @Title: findViewById
     */
    public View findViewById(int id) {
        return mFragmentView.findViewById(id);

    }

    /**
     * Fragment从栈中销毁的时候回调
     */
    @Override
    public void onDestroyView() {
        if (isShowStatus) {
            LoggerUtils.v(getClass().getSimpleName() + "-> onDestroyView");
        }
        super.onDestroyView();
    }


    /**
     * 记录fragment是否处于展示状态
     * 由应用维护该专题
     */
    private boolean isShowing;

    public boolean isShowing() {
        return isShowing;
    }

    /**
     * fragment被覆盖时调用
     */
    public void onFragmentHide() {
        LoggerUtils.v(getClass().getSimpleName() + "-> onFragmentHide");
        isShowing = false;
    }

    /**
     * fragment展示在界面上时调用
     */
    public void onFragmentShow() {

        LoggerUtils.v(getClass().getSimpleName() + "-> onFragmentShow");
        if (title != null) {
            activity.showTitle();
            activity.setTitle(title);
            activity.setMainStyle();
        } else {
//            activity.hideTitle();
        }
        isShowing = true;
        if(this instanceof MainFragment){
            activity.setManageMode();
        }else if(this instanceof TestFragment){
            activity.setSignInMode();
        }else{
            activity.closeActionBar();
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public void onAttach(Activity activity) {
        if (isShowStatus) {
            LoggerUtils.v(getClass().getSimpleName() + "-> onAttach");
        }
        super.onAttach(activity);
    }

    @Override
    public void onDestroy() {
        if (isShowStatus) {
            LoggerUtils.v(getClass().getSimpleName() + "-> onDestroy");
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        if (isShowStatus) {
            LoggerUtils.v(getClass().getSimpleName() + "-> onDetach");
        }
        super.onDetach();
    }

    @Override
    public void onPause() {
        if (isShowStatus) {
            LoggerUtils.v(getClass().getSimpleName() + "-> onPause");
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (isShowStatus) {
            LoggerUtils.v(getClass().getSimpleName() + "-> onResume");
        }
        super.onResume();
    }

    @Override
    public void onStart() {
        if (isShowStatus) {
            LoggerUtils.v(getClass().getSimpleName() + "-> onStart");
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if (isShowStatus) {
            LoggerUtils.v(getClass().getSimpleName() + "-> onStop");
        }
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (isShowStatus) {
            LoggerUtils.v(getClass().getSimpleName() + "-> onViewCreated");
        }
        super.onViewCreated(view, savedInstanceState);
    }

    public void setTitle(int resTitle) {
        title = getString(resTitle);
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
