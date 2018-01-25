package com.demo;

import android.util.Log;

/**
 * Created by John.Lu on 2018/1/11.
 */

public class Test {


    public static void show()
    {
        Log.d("Test","show ===================");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Test","run  show ===================");
            }
        }).start();


        new Inner(5).test();

    }

    static class Inner{
        private float height =18.0f;
        public Inner(int h)
        {
            height =h;
        }
        public  void test()
        {
            Log.d("Test","height  show ===================" + height);
        }

    }

    public static  int add(int a,int b)
    {
        return a+b;
    }




}
