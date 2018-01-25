package com.demo.count;

/**
 * Created by John.Lu on 2018/1/20.
 */

public class Counter {
    private  static  final  String TAG="Counter";

    private static Counter instance=null;
    private Counter(){}
    public static Counter getInstance()
    {
        if(instance == null)
        {
            instance = new Counter();
        }

        return instance;
    }

    public String getName()
    {
        return "Counter";
    }
    public String getMsg(){
        return "from Counter class ";
    }

}
