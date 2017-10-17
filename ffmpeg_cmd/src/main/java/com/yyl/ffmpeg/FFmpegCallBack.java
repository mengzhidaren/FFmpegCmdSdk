package com.yyl.ffmpeg;


/**
 * Created by yuyunlong on 2017/10/12/012.
 * https://github.com/mengzhidaren
 */

public interface FFmpegCallBack {
    void onStart();

    void onCallBackLog(String log);

    void onCallBackPrint(String msg);

    void onProgress(int frame_number, int milli_second);

    void onSuccess();

    void onFailure(int result);

}
