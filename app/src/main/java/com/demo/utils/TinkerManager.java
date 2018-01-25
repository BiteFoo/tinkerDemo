package com.demo.utils;

import android.util.Log;

import com.demo.crash.SampleUncaughtExceptionHandler;
import com.demo.report.SampleLoadReporter;
import com.demo.report.SamplePatchListener;
import com.demo.report.SamplePatchReporter;
import com.demo.service.SampleResultService;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.lib.util.UpgradePatchRetry;
import com.tencent.tinker.loader.app.ApplicationLike;


/**
 * Created by John.Lu on 2018/1/15.
 */

public class TinkerManager {
    private static  final String TAG ="TinkerManager";

    private static ApplicationLike applicationLike;
    private static  SampleUncaughtExceptionHandler uncaughtExceptionHandler;
    private static  boolean isInstalled =false;

    public static  void setTinkerApplicationLike(ApplicationLike applike)
    {
        applicationLike =applike;
    }
    public static ApplicationLike getTinkerApplicationLike()
    {
        return applicationLike;
    }
    public static  void initFastCrashProtect()
    {
        Log.d(TAG,"initFastCrashReport =========================>");
        if(uncaughtExceptionHandler  == null)
        {
            uncaughtExceptionHandler = new SampleUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
    }
    public static  void setUpgradeRetryEnable(boolean enable)
    {
        UpgradePatchRetry.getInstance(applicationLike.getApplication()).setRetryEnable(enable);
    }
    public static  void sampleInstallTinker(ApplicationLike applike)
    {
        if(isInstalled)
        {
            TinkerLog.w(TAG,"installed tinker,but has installed ,ignore");
            return;
        }
        TinkerInstaller.install(applike);
        isInstalled =true;
    }

    public static  void installTinker(ApplicationLike appLike)
    {
        if(isInstalled)
        {
            TinkerLog.w(TAG,"installed tinker,but has installed ,ignore");
            return ;
        }
        //or you can just use DefaultLoadReporter
        LoadReporter loadReporter = new SampleLoadReporter(appLike.getApplication());
        //or you can just use DefaultPatchReporter
        PatchReporter patchReporter = new SamplePatchReporter(appLike.getApplication());
        //or you can just use DefaultPatchListener
        PatchListener patchListener = new SamplePatchListener(appLike.getApplication());
        //you can set your own upgrade patch if you need
        AbstractPatch upgradePatchProcessor = new UpgradePatch();

        TinkerInstaller.install(appLike,
                loadReporter, patchReporter, patchListener,
                SampleResultService.class, upgradePatchProcessor);
        isInstalled = true;


    }


}
