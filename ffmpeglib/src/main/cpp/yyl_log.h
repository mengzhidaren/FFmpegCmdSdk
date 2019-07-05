//
// Created by Administrator on 2017/9/28/028.
//

#ifndef HELLO_JNI_YYL_LOG_H
#define HELLO_JNI_YYL_LOG_H

#include <jni.h>
#include <android/log.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include "ffmpeg_jni.h"
#include "ffprobe_jni.h"
#include "yyl_config.h"


extern int YYL_DEBUG_;
extern int YYL_LOGCAT;
extern char *JSON_RESULT;

#define LOGE(format, ...) if(YYL_DEBUG_){__android_log_print(ANDROID_LOG_ERROR, "FFMPEG", format, ##__VA_ARGS__);}
#define LOGI(format, ...) if(YYL_DEBUG_){__android_log_print(ANDROID_LOG_INFO, "FFMPEG", format, ##__VA_ARGS__);}
#define LOGTAG(tag,format, ...) if(YYL_DEBUG_){__android_log_print(ANDROID_LOG_INFO, tag, format, ##__VA_ARGS__);}

void yyl_print_callback_json(const char *fmt, ...);
void yyl_print_log(const char *fmt, ...);
void yyl_callback_log(void *ptr, int level, const char *fmt, va_list vl);


int yyl_main_ffmpeg(int argc, char **argv);

int yyl_main_ffprobe(int argc, char **argv);
void setLogCallback();

#endif //HELLO_JNI_YYL_LOG_H
