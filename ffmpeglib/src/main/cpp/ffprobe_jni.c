//
// Created by Administrator on 2017/10/13/013.
//

#include "ffprobe_jni.h"


static void *ffprobe_thread(FFmpeg_ARG_YYL *arg) {
    int result= yyl_main_ffprobe(arg->argc, arg->argv);
    return (void *)result;
}

JNIEXPORT jstring JNICALL
Java_com_yyl_ffmpeg_FFmpeg_execffprobe(JNIEnv *env,
                                       jobject instance,
                                       jobjectArray commands
                                        ) {
    JSON_RESULT = "";
    int argc = (*env)->GetArrayLength(env, commands);
    char *argv[argc];
    int i;
    for (i = 0; i < argc; i++) {
        jstring js = (jstring) (*env)->GetObjectArrayElement(env, commands, i);
        argv[i] = (char *) (*env)->GetStringUTFChars(env, js, 0);
    }
    FFmpeg_ARG_YYL ffprobe;
    ffprobe.argc = argc;
    ffprobe.argv = argv;
    pthread_t ffprobe_tid;
    pthread_attr_t threadAttr_;
    pthread_attr_init(&threadAttr_);
    pthread_attr_setdetachstate(&threadAttr_, PTHREAD_CREATE_JOINABLE);
    int result_thread = pthread_create(&ffprobe_tid, &threadAttr_, (void *) ffprobe_thread,
                                       (void *) &ffprobe);
    if (result_thread != 0) {
        LOGTAG("FFPROBE","Thread Create Error");
    } else {
        LOGTAG("FFPROBE","Thread Create success");
        void *status;
        pthread_join(ffprobe_tid, &status);
        LOGTAG("FFPROBE","线程结束  status=%d", (int) status);
    }
    return (*env)->NewStringUTF(env, JSON_RESULT);
}

