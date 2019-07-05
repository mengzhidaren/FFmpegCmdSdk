//
// Created by Administrator on 2017/10/12/012.
//

#ifndef FFMPEGANDROIDCMD_FFMPEG_JNI_H
#define FFMPEGANDROIDCMD_FFMPEG_JNI_H
extern volatile int YYL_EXIT_;
typedef struct yyl_ffmpeg_arg {
    int argc;
    char **argv;
} FFmpeg_ARG_YYL;
void onCallbackLog(const char *log);

void onCallbackPrint(const char *msg);

void onCallbackProgress(int frame_number, int milli_second);
void onProgramExitThread(int error);

#endif //FFMPEGANDROIDCMD_FFMPEG_JNI_H
