package com.qwh.baseapp.base;

import android.os.Environment;
import android.view.KeyEvent;

import com.qwh.baseapp.ui.activity.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class Const {

    public static final String YES = "1";
    public static final String NO = "0";

    public static final String APPS_NAME = AndroidTools.getPackageName(MainActivity.getInstance());
    public static final String SEPARATOR = java.io.File.separator;

    public static final String SHARE_NAME = "Share";


    public static class FileConst {
        /**
         * 默认参数文件
         */
        public final static String PARAMS = "defaultparams.properties";
    }
}



