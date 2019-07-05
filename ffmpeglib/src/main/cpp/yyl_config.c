//
// Created by Administrator on 2017/10/16/016.
//
#include "yyl_config.h"
#include "yyl_log.h"

void initCallBack(JNIEnv *env, jobject instance, OnCallBack *onCallBack) {
    onCallBack->env = env;
    (*env)->GetJavaVM(env, &(onCallBack->javaVM));
    jclass clazz = (*env)->GetObjectClass(env, instance);
    onCallBack->instance = (*env)->NewGlobalRef(env, instance);
    onCallBack->onCallBackLog = (*env)->GetMethodID(env, clazz,
                                                    "onCallBackLog",
                                                    "(Ljava/lang/String;)V");
    onCallBack->onCallBackPrint = (*env)->GetMethodID(env, clazz,
                                                      "onCallBackPrint",
                                                      "(Ljava/lang/String;)V");
    onCallBack->onProgress = (*env)->GetMethodID(env, clazz,
                                                 "onProgress",
                                                 "(II)V");
    onCallBack->onSuccess = (*env)->GetMethodID(env, clazz,
                                                "onSuccess", "()V");
    onCallBack->onStart = (*env)->GetMethodID(env, clazz,
                                                "onStart", "()V");
    onCallBack->onFailure = (*env)->GetMethodID(env, clazz,
                                                "onFailure", "(I)V");
}

void release(OnCallBack *onCallBack) {
    onCallBack->javaVM = NULL;
    onCallBack->env = NULL;
    onCallBack->instance = NULL;
    onCallBack->onCallBackLog = NULL;
    onCallBack->onCallBackPrint = NULL;
    onCallBack->onProgress = NULL;
    onCallBack->onSuccess = NULL;
    onCallBack->onFailure = NULL;
    onCallBack->onStart = NULL;
}