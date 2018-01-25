//
// Created by John.Lu on 2018/1/16.
//

#include <jni.h>
#include <android/log.h>
#include <string>

#define  TAG "Loopher"
#define LOGD(fmt,x...) __android_log_print(ANDROID_LOG_DEBUG,TAG,fmt,##x)

int add(int a, int b){
        int c = a+b+10;
        return c;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_demo_NativeHelper_stringFromJNI(JNIEnv* env,jobject obj){
        std::string hello="Tinker native test method";
        LOGD("native  sttttttttttttttttt *************************>>>>>");
        return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_demo_NativeHelper_intFromJNI(JNIEnv* env,jobject obj,jint a,jint b){
        std::string hello="Tinker native test method";
        LOGD("native  add *************************>>>>>");

        return add(a,b);
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_demo_NativeHelper_subFromJNI(JNIEnv *env, jobject instance, jint a, jint b) {

    // TODO
    if(a<b){
        a+=10;
    }
    int result =a -b;
    LOGD("native sub function : a =%d , b =%d ",a,b);
    LOGD("value = %d",result);

    return result;
//secidea.helper
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_demo_NativeHelper_showMsg(JNIEnv *env, jobject instance) {

    // TODO
    LOGD("Java_com_demo_NativeHelper_showMsg ******************************>>>>>");
    return env->NewStringUTF("showMsg");

}