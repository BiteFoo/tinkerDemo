package com.demo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.tinker.lib.library.TinkerLoadLibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by John.Lu on 2018/1/16.
 */

public class NativeHelper {
    //com_demo_NativeHelper_stringFromJNI
//    static {
//        System.loadLibrary("native-lib");
//    }
    /**
     * 测试加载x86或者arm库so文件
     * */
    public static  void test(Context context)
    {

        String libAbi =getArch();
        if("x86".equals(libAbi))
        {
            Log.e("Loopher","init -------x86 libs --------->");
            TinkerLoadLibrary.installNavitveLibraryABI(context,"x86");
            TinkerLoadLibrary.loadArmLibrary(context, "native-lib");

        }
        else
        {
            Log.e("Loopher","init -------arm libs --------->");
            TinkerLoadLibrary.installNavitveLibraryABI(context,"armeabi");
            TinkerLoadLibrary.loadArmLibrary(context, "native-lib");
        }
    }

    /**
     * 实际在项目中需要使用的函数
     * */
    public static void loadLibrary(Context context)
    {

        String libAbi =getArch();
        if("x86".equals(libAbi))
        {
            Log.e("Loopher","init -------x86 libs --------->");
            TinkerLoadLibrary.installNavitveLibraryABI(context,"x86");
            TinkerLoadLibrary.loadArmLibrary(context, "secidea");
        }
        else
        {
            Log.e("Loopher","init -------arm libs --------->");
            TinkerLoadLibrary.installNavitveLibraryABI(context,"armeabi");
            TinkerLoadLibrary.loadArmLibrary(context, "secidea");
        }


    }

    private static String getArch(){
        try {
            String abi = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop ro.product.cpu.abi").getInputStream())).readLine();
            if (!TextUtils.isEmpty(abi) && abi.contains("x86"))
            {
                return "x86";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "arm";  //异常情况，默认返回arm架构
        }
        return "arm";
    }

    public native  String stringFromJNI();
    public native  int intFromJNI(int a,int b);
    public native int  subFromJNI(int a,int b);

    public native String showMsg();


}
