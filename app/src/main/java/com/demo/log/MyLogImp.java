package com.demo.log;

import android.util.Log;

import com.tencent.tinker.lib.util.TinkerLog;

/**
 * Created by John.Lu on 2018/1/15.
 */

public class MyLogImp implements TinkerLog.TinkerLogImp {
    private static final  String TAG="MyLogImp";
    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_DEBUG   = 1;
    public static final int LEVEL_INFO    = 2;
    public static final int LEVEL_WARNING = 3;
    public static final int LEVEL_ERROR   = 4;
    public static final int LEVEL_NONE    = 5;
    private static int level = LEVEL_VERBOSE;
    public static void setLevel(final  int level)
    {
        MyLogImp.level =level;
    }


    @Override
    public void v(String tag, String msg, Object... obj) {
        if (level <= LEVEL_VERBOSE) {
            final String log = obj == null ? msg : String.format(msg, obj);
            android.util.Log.v(tag, log);
        }
    }

    @Override
    public void i(String tag, String msg, Object... obj) {
        if (level <= LEVEL_VERBOSE) {
            final String log = obj == null ? msg : String.format(msg, obj);
            android.util.Log.v(tag, log);
        }
    }

    @Override
    public void w(String tag, String msg, Object... obj) {
        if (level <= LEVEL_VERBOSE) {
            final String log = obj == null ? msg : String.format(msg, obj);
            android.util.Log.v(tag, log);
        }
    }

    @Override
    public void d(String tag, String msg, Object... obj) {
        if (level <= LEVEL_VERBOSE) {
            final String log = obj == null ? msg : String.format(msg, obj);
            android.util.Log.v(tag, log);
        }
    }

    @Override
    public void e(String tag, String msg, Object... obj) {
        if (level <= LEVEL_VERBOSE) {
            final String log = obj == null ? msg : String.format(msg, obj);
            android.util.Log.v(tag, log);
        }
    }

    @Override
    public void printErrStackTrace(String tag, Throwable tr, String format, Object... obj) {
        String log = obj == null ? format : String.format(format, obj);
        if (log == null) {
            log = "";
        }
        log = log + "  " + Log.getStackTraceString(tr);
        android.util.Log.e(tag, log);
    }
}
