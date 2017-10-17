package com.yyl.ffmpeg;

/**
 * Created by yuyunlong on 2017/10/11/011.
 * https://github.com/mengzhidaren
 */
public class FFmpeg {

    static {
        try {
            System.loadLibrary("yylffmpeg");
            System.loadLibrary("yylffmpegjni");
            isSport = true;
        } catch (UnsatisfiedLinkError error) {
            isSport = false;
        }

    }

    public static boolean isSport;

    public native int execffmpeg(String[] cmd, FFmpegCallBack callBack);

    public native void exitffmpeg();


    public native String execffprobe(String[] cmd);

    public native void setDebugMode(boolean debugMode);

//    public native String execffprobe(String[] cmd, FFmpegCallBack callBack);


}
