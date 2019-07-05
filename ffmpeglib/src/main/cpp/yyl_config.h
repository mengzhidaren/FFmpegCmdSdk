//
// Created by Administrator on 2017/10/16/016.
//


#ifndef FFMPEGANDROIDCMD_YYL_CONFIG_H
#define FFMPEGANDROIDCMD_YYL_CONFIG_H
#include <jni.h>

typedef struct OnCallBack {
    // jclass clazz;
    JavaVM *javaVM;
    JNIEnv *env;
    jobject instance;
    jmethodID onCallBackLog;
    jmethodID onCallBackPrint;
    jmethodID onProgress;
    jmethodID onSuccess;
    jmethodID onFailure;
    jmethodID onStart;
} OnCallBack;

void initCallBack(JNIEnv *env, jobject instance,OnCallBack *onCallBack);

void release(OnCallBack *onCallBack);

#endif //FFMPEGANDROIDCMD_YYL_CONFIG_H
