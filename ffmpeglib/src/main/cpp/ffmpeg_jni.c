#include "ffmpeg_jni.h"
#include "yyl_log.h"

volatile int YYL_EXIT_ = 1;
volatile int YYL_RUN_ = 0;
int YYL_PROGRAM_EXIT;
int YYL_LOOP_THREAD;
int YYL_CALLBACK_INIT;
pthread_t tid;
OnCallBack onCallback;

void loopStartThread() {
    if (!YYL_LOOP_THREAD && YYL_CALLBACK_INIT) {
        jint res = (*onCallback.javaVM)->AttachCurrentThreadAsDaemon(onCallback.javaVM,
                                                                     &onCallback.env, NULL);
        YYL_LOOP_THREAD = 1;
        LOGI("AttachCurrentThread open=%d  tid=%ld", res, tid);
    }
}

void loopStopThread() {
    if (YYL_LOOP_THREAD && YYL_CALLBACK_INIT) {
        jint res = (*onCallback.javaVM)->DetachCurrentThread(onCallback.javaVM);
        YYL_LOOP_THREAD = 0;
        LOGI("DetachCurrentThread close=%d  tid=%ld", res, tid);
    }
}

void onCallbackLog(const char *msg) {
    if (YYL_CALLBACK_INIT && YYL_LOOP_THREAD && YYL_LOGCAT && pthread_self() == tid) {
        JNIEnv *env = onCallback.env;
        jstring javaMsg = (*env)->NewStringUTF(env, msg);
        (*env)->CallVoidMethod(env, onCallback.instance, onCallback.onCallBackLog, javaMsg);
        (*env)->DeleteLocalRef(env, javaMsg);
    }
}


void onCallbackPrint(const char *msg) {
    if (YYL_CALLBACK_INIT && YYL_LOOP_THREAD && YYL_LOGCAT && pthread_self() == tid) {
        JNIEnv *env = onCallback.env;
        jstring javaMsg = (*env)->NewStringUTF(env, msg);
        (*env)->CallVoidMethod(env, onCallback.instance, onCallback.onCallBackPrint, javaMsg);
        (*env)->DeleteLocalRef(env, javaMsg);
    }
}

void onCallbackProgress(int frame_number, int milli_second) {
    if (YYL_CALLBACK_INIT && YYL_LOOP_THREAD) {
        JNIEnv *env = onCallback.env;
        (*env)->CallVoidMethod(env, onCallback.instance, onCallback.onProgress, frame_number,
                               milli_second);
    }
}

void onProgramExitThread(int error) {
    YYL_PROGRAM_EXIT = 1;
    if (pthread_self() == tid) {
        loopStopThread();
    }
    LOGI("exit_program   pthread_exit=%d", error);
    pthread_exit((void *) error);
    LOGI("正常情况 这句日志不能被执行 error =%d", error);
}

JNIEXPORT void JNICALL
Java_com_yyl_ffmpeg_FFmpeg_exitffmpeg(JNIEnv *env, jobject instance) {
    YYL_EXIT_ = 1;
}


static void *ffmpeg_thread(FFmpeg_ARG_YYL *arg) {
    loopStartThread();
    setLogCallback();
    int result = yyl_main_ffmpeg(arg->argc, arg->argv);
    loopStopThread();
    return (void *) result;
}

JNIEXPORT jint JNICALL
Java_com_yyl_ffmpeg_FFmpeg_execffmpeg(JNIEnv *env, jobject instance,
                                      jobjectArray commands,
                                      jobject objcallback) {
    if (YYL_RUN_) {
        return 1;
    }
    YYL_RUN_ = 1;
    YYL_EXIT_ = 0;
    YYL_PROGRAM_EXIT = 0;
    YYL_LOOP_THREAD = 0;
    time_t timeStart;
    time(&timeStart);
    if (objcallback == NULL) {
        YYL_CALLBACK_INIT = 0;
    } else {
        initCallBack(env, objcallback, &onCallback);
        YYL_CALLBACK_INIT = 1;
        (*env)->CallVoidMethod(env, objcallback, onCallback.onStart);
    }
    int argc = (*env)->GetArrayLength(env, commands);
    char *argv[argc];
    int i;
    for (i = 0; i < argc; i++) {
        jstring js = (jstring) (*env)->GetObjectArrayElement(env, commands, i);
        argv[i] = (char *) (*env)->GetStringUTFChars(env, js, 0);
    }
    FFmpeg_ARG_YYL ffmpeg;
    ffmpeg.argc = argc;
    ffmpeg.argv = argv;
    pthread_attr_t threadAttr_;
    pthread_attr_init(&threadAttr_);
    pthread_attr_setdetachstate(&threadAttr_, PTHREAD_CREATE_JOINABLE);
    int result_thread = pthread_create(&tid, &threadAttr_, (void *) ffmpeg_thread,
                                       (void *) &ffmpeg);
    if (result_thread != 0) {
        LOGI("Thread Create Error");
    } else {
        void *status;
        pthread_join(tid, &status);
        result_thread = (int) status;
        //   LOGI("线程结束  status=%d",result_thread);
        if (YYL_PROGRAM_EXIT) {
            LOGI("线程任务异常中断并退出");
           //result_thread = 2;这里打印log也中断 不能报错
        }
        if (YYL_EXIT_) {
            LOGI("主动退出线程任务 exitffmpeg()");
            result_thread = 3;
        }
    }
    if (YYL_CALLBACK_INIT) {
        YYL_CALLBACK_INIT = 0;
        if (YYL_EXIT_ || result_thread != 0) {
            (*env)->CallVoidMethod(env, objcallback, onCallback.onFailure, result_thread);
        } else {
            (*env)->CallVoidMethod(env, objcallback, onCallback.onSuccess);
        }
        release(&onCallback);
    }
    if(YYL_DEBUG_){
        time_t timeEnd;
        time(&timeEnd);
        long time=timeEnd-timeStart;
        LOGI("任务结束总耗时：%ld秒  code=%d",time,result_thread);
    }
    YYL_RUN_ = 0;
    return result_thread;
}


JNIEXPORT void JNICALL
Java_com_yyl_ffmpeg_FFmpeg_setDebugMode(JNIEnv *env, jobject instance, jboolean debugMode) {
    YYL_DEBUG_ = debugMode ? 1 : 0;
}

JNIEXPORT void JNICALL
Java_com_yyl_ffmpeg_FFmpeg_isShowLogcat(JNIEnv *env, jobject instance, jboolean show) {
    // TODO
    YYL_LOGCAT = show ? 1 : 0;
}