package com.http.qnhlli.myhttptest2.util;

import android.util.Log;

import com.http.qnhlli.myhttptest2.Constant;


/**
 * Created by qnhlli on 2016/6/16.
 */
public class LogUtils {

    private static final String TAG = "qhl";

    public static void printLogErr(String msg) {

        if (Constant.DEBUG) {

            Log.e(TAG, msg);
        }
    }

    public static void printLogVerbose(String msg) {

        if (Constant.DEBUG) {

            Log.v(TAG, msg);
        }
    }

    public static void printLogDebug(String msg) {

        if (Constant.DEBUG) {

            Log.d(TAG, msg);
        }
    }

    public static void printLogWarn(String msg) {

        if (Constant.DEBUG) {

            Log.w(TAG, msg);
        }
    }

    public static void printLogAssert(String msg) {

        if (Constant.DEBUG) {

            Log.i(TAG, msg);
        }
    }

}
