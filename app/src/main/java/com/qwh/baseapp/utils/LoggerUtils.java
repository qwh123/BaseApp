package com.qwh.baseapp.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * 创建者：qwh
 * 时间：2017/12/25
 * 说明：
 */
public class LoggerUtils {
    public static interface CustomLogger {

        public abstract void d(String s, String s1);

        public abstract void d(String s, String s1, Throwable throwable);

        public abstract void e(String s, String s1);

        public abstract void e(String s, String s1, Throwable throwable);

        public abstract void i(String s, String s1);

        public abstract void i(String s, String s1, Throwable throwable);

        public abstract void v(String s, String s1);

        public abstract void v(String s, String s1, Throwable throwable);

        public abstract void w(String s, String s1);

        public abstract void w(String s, String s1, Throwable throwable);

        public abstract void w(String s, Throwable throwable);

        public abstract void wtf(String s, String s1);

        public abstract void wtf(String s, String s1, Throwable throwable);

        public abstract void wtf(String s, Throwable throwable);
    }


    public LoggerUtils() {
    }

    public static void configPrint(boolean isDebug) {
        allowD = isDebug;
        allowE = isDebug;
        allowI = isDebug;
        allowV = isDebug;
        allowW = isDebug;
        allowWtf = isDebug;
    }

    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, new Object[]{
                callerClazzName, caller.getMethodName(), Integer.valueOf(caller.getLineNumber())
        });
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : (new StringBuilder(String.valueOf(customTagPrefix))).append(":").append(tag).toString();
        return tag;
    }

    public static void d(String content) {
        if (!allowD)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.d(tag, content);
        else
            Log.d(tag, content);
    }

    public static void d(String content, Throwable tr) {
        if (!allowD)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.d(tag, content, tr);
        else
            Log.d(tag, content, tr);
    }

    public static void e(String content) {
        if (!allowE)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.e(tag, content);
        else
            Log.e(tag, content);
    }

    public static void e(String content, Throwable tr) {
        if (!allowE)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.e(tag, content, tr);
        else
            Log.e(tag, content, tr);
    }

    public static void i(String content) {
        if (!allowI)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.i(tag, content);
        else
            Log.i(tag, content);
    }

    public static void i(String content, Throwable tr) {
        if (!allowI)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.i(tag, content, tr);
        else
            Log.i(tag, content, tr);
    }

    public static void v(String content) {
        if (!allowV)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.v(tag, content);
        else
            Log.v(tag, content);
    }

    public static void v(String content, Throwable tr) {
        if (!allowV)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.v(tag, content, tr);
        else
            Log.v(tag, content, tr);
    }

    public static void w(String content) {
        if (!allowW)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.w(tag, content);
        else
            Log.w(tag, content);
    }

    public static void w(String content, Throwable tr) {
        if (!allowW)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.w(tag, content, tr);
        else
            Log.w(tag, content, tr);
    }

    public static void w(Throwable tr) {
        if (!allowW)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.w(tag, tr);
        else
            Log.w(tag, tr);
    }

    public static void wtf(String content) {
        if (!allowWtf)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.wtf(tag, content);
        else
            Log.wtf(tag, content);
    }

    public static void wtf(String content, Throwable tr) {
        if (!allowWtf)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.wtf(tag, content, tr);
        else
            Log.wtf(tag, content, tr);
    }

    public static void wtf(Throwable tr) {
        if (!allowWtf)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null)
            customLogger.wtf(tag, tr);
        else
            Log.wtf(tag, tr);
    }

    public static String customTagPrefix = "";
    public static boolean allowD = false;
    public static boolean allowE = false;
    public static boolean allowI = false;
    public static boolean allowV = false;
    public static boolean allowW = false;
    public static boolean allowWtf = false;
    public static CustomLogger customLogger;

}