package com.demo.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.demo.NativeHelper;
import com.demo.service.SampleResultService;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by John.Lu on 2018/1/15.
 */

@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.demo.app.MyApplication",
            flags =  ShareConstants.TINKER_ENABLE_ALL,
            loadVerifyFlag = false
    )
public class StubApplicationLike extends DefaultApplicationLike {
    public StubApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // SampleResultService 这个类不做加固 尽可能少的操作tinker的类
        MultiDex.install(base); //多个dex的情况,需要在build.gradle(app)中开启
        TinkerInstaller.install(this,new DefaultLoadReporter(this.getApplication()),new DefaultPatchReporter(getApplication()),
                new DefaultPatchListener(getApplication()), SampleResultService.class,new UpgradePatch());
//        NativeHelper.test(base); //加载native-lib.so
        NativeHelper.loadLibrary(base);
//        NativeHelper.init(base); //加载so 必须在installer之后加载，否则会失败 Caused by: com.tencent.tinker.loader.TinkerRuntimeException: Tinker Exception:you must install tinker before get tinker sInstance
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Loopher","---------------StubApplicationLike onCreate()----------->");
        // init
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public  void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks){
        getApplication().registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }
}
