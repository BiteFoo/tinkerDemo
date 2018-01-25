package com.demo;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.count.Counter;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private String TAG ="Loopher";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"Hello World!");
        textView = findViewById(R.id.tv);
        textView.setText("abi1 ="+getArch1() + ",abi2="+getArch2() +"\n cmd is ="+getArch());
        Test.show();
        Master.showTTTT();
        Toast.makeText(this,"MainActivity",Toast.LENGTH_SHORT).show();
        loadPatchDex();
        NativeHelper.test(getApplicationContext()); //加载native-lib.so
        showToast();//新添加的方法
        Emulator.init();
//        testttt();
//        update();
//        add();
    }


    @SuppressLint("SetTextI18n")
    private void update(){

        NativeHelper helper =new NativeHelper();
        textView.setText(helper.stringFromJNI() +"\n 10+23="+helper.intFromJNI(10,23)+"\n 22-15="+helper.subFromJNI(22,15)
                +"\n\n"+"abi1 ="+getArch1() + ",abi2="+getArch2() +"\n cmd is ="+getArch()
        );
    }

    // 热更新失败当前方法
    @SuppressLint("SetTextI18n")
    private void testttt(){
        Log.e(TAG,"addd testtttttt function ");
        textView.setText(Counter.getInstance().getName()+" ,msg "+Counter.getInstance().getMsg());

    }

    private String getArch(){
        try {
            String abi = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("getprop ro.product.cpu.abi").getInputStream())).readLine();
            if (!TextUtils.isEmpty(abi))
            {
                return abi;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "arm";
    }

    private String getArch1(){
        return Build.CPU_ABI;
    }
    private String getArch2(){
        return Build.CPU_ABI2;
    }

    private void add()
    {
        Log.d(TAG,"*******************************************");
        Log.d(TAG,"add from mainactivity ");
        Log.d(TAG,"*******************************************");
    }

    private void showToast(){
        NativeHelper helper =new NativeHelper();
//        Toast.makeText(this,"热更新方法 "+Emulator.getString()+" , "+helper.showMsg(),Toast.LENGTH_SHORT).show();
//        MyLogUtils.d("calling Optimus and boombee!!!");//新添加的类
    }

    /**
     * 加载差分包
     *
     * 默认tinker加载路径
     *  /data/data/com.demo/tinker 到这里去加载差分包
     *  tinker patch directory: /data/data/com.demo/tinker
     *  tryLoadPatchFiles:patch dir not exist:/data/data/com.demo/tinker
     *进程被杀死后，重启应用每次都会加载
     *
     *  can't find patch file, 存在/data/data/com.demo/tinker，没有patchDexFile ,
     *
     *差分包最好是
     *
     * */
    private void loadPatchDex(){


        String sdcard = "/sdcard/tinker_test";
        String fileName ="patch_signed_7zip.apk";
        File sdFile =new File(sdcard);
        if(!sdFile.exists() && !sdFile.isDirectory())
        {
            sdFile.mkdirs();
        }
        File patchDex = new File(sdFile,fileName);
        if(!patchDex.exists() && !patchDex.isFile()){
            Log.w(TAG,"not found \""+patchDex.getAbsolutePath()+"\" , ignored it  ===============================>>> ");
            return ;
        }
        File tinkerPathDir = new File("/data/data/com.demo/tinker");
        if(! tinkerPathDir.exists() && ! tinkerPathDir.isDirectory()){
            tinkerPathDir.mkdirs();
            Log.d(TAG,"create patch dir ="+"\""+tinkerPathDir.getAbsolutePath()+"\"");
        }
        //复制文件
        File patch =new File(tinkerPathDir,fileName);
        if(copyPathDex(patchDex,patch))
        {
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),patch.getAbsolutePath());
            Log.d(TAG,"load patch dex succ ===============================>>>");
        }
        else
        {
            Log.e(TAG,"load patch dex failed  ===============================>>>");
        }

    }

    private  boolean copyPathDex(File src,File dst){

        boolean flag =false ;
        try {
            FileInputStream fileInputStream = new FileInputStream(src);
            FileOutputStream fileOutputStream = new FileOutputStream(dst);
            int len =-1;
            byte buff[] = new byte[1024];
            while (true)
            {
                len = fileInputStream.read(buff);
                if( -1 == len)
                {
                    break;
                }
                fileOutputStream.write(buff,0,len);
            }
            fileOutputStream.close();
            fileInputStream.close();
            flag =true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 复制差分包到data目录
     * param: patchDexName 指定需要加载的差分包
     * */
    private boolean copyPatchDexToDataDir(String patchDexName, File save){

        boolean flag;
        if(patchDexName.equals("") || TextUtils.isEmpty(patchDexName))
        {
            Log.e(TAG,"not found \""+patchDexName+"\"");
            return false;
        }
        try {
            InputStream inputStream = getAssets().open(patchDexName);
            FileOutputStream fileOutputStream = new FileOutputStream(save);
            int len;
            byte[] buff =new byte[1024];
            while (true)
            {
                len = inputStream.read(buff);
                if(-1 ==len)
                {
                    break;
                }
                fileOutputStream.write(buff,0,len);
            }

            fileOutputStream.close();
            inputStream.close();
            flag =true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"copy patch dex failed : "+e.getLocalizedMessage());
            flag=false;
        }
        return flag;
    }

    /**
     * 加载差分库so
     *
     * */
    private  void loadPatchLibraries(){

    }

}
