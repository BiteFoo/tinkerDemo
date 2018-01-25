package com.demo;

import android.util.Log;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by John.Lu on 2018/1/18.
 * 反射测试AssetsManager.openNonAsset方法
 * 这个方法是被隐藏的，需要使用反射的方式读取
 */

public class RefUtils {
    public static void ref(){
        String className ="android.content.res.AssetManager";
        String methoeName="openNonAsset";
        Object params[] ={"classes.dex"};

        try {
            Class<?> clazz = Class.forName(className);
            Object instance =clazz.newInstance();
            if(instance == null)
            {
                Log.e("DDDDDDD","instance is null");
            }
            Method declaredMethod = clazz.getDeclaredMethod(methoeName, String.class);
            if(declaredMethod == null)
            {
                Log.e("DDDDDDD","declaredMethod is null");
            }
            declaredMethod.setAccessible(true);
            InputStream inputStream= (InputStream) declaredMethod.invoke(instance,"classes.dex");
            if(inputStream != null)
            {
                Log.d("DEX","call openNonAsset method succ ");
            }
            else
            {
                Log.e("DDDDDDD","call openNonAsset method failed ");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("DDDDDDD","call openNonAsset method ClassNotFoundException ");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.e("DDDDDDD","call openNonAsset method NoSuchMethodException ");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e("DDDDDDD","call openNonAsset method IllegalAccessException ");
        } catch (InstantiationException e) {
            e.printStackTrace();
            Log.e("DDDDDDD","call openNonAsset method InstantiationException ");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Log.e("DDDDDDD","call openNonAsset method InvocationTargetException "+e.getMessage() +" , "+e);
        }

    }

}
