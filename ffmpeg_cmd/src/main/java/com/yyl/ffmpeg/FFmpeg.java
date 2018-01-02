package com.yyl.ffmpeg;


/**
 * Created by yuyunlong on 2017/10/11/011.
 */

public class FFmpeg {
//    static {
//        try {
//            System.loadLibrary("yylffmpeg");
//            System.loadLibrary("yylffmpegjni");
//            isSport = true;
//        } catch (UnsatisfiedLinkError error) {
//            isSport = false;
//            error.printStackTrace();
//            Log.i("yyl", "error");
//        }
//    }

    public FFmpeg() {
        loadLibrariesOnce();
    }

    private static volatile boolean isSport=false;

    public static void loadLibrariesOnce() {
        synchronized (FFmpeg.class) {
            if (!isSport) {
                try {
                    System.loadLibrary("yylffmpeg");
                    System.loadLibrary("yylffmpegjni");
                    isSport = true;
                } catch (UnsatisfiedLinkError error) {
                    error.printStackTrace();
                } catch (SecurityException error) {
                    error.printStackTrace();
                }
            }
        }
    }

    public static boolean isSport() {
        loadLibrariesOnce();
        return isSport;
    }



    public native void isShowLogcat(boolean show);

    public native int execffmpeg(String[] cmd, FFmpegCallBack callBack);

    public native void exitffmpeg();


    public native String execffprobe(String[] cmd);

    public native void setDebugMode(boolean debugMode);

//    public native String execffprobe(String[] cmd, FFmpegCallBack callBack);


}
