package com.yyl.ffmpeg;


import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yuyunlong on 2017/10/16/016.
 * https://github.com/mengzhidaren
 */

public class FFmpegUtils {
    // private final Handler mainHandler;
    private final FFmpeg ffmpeg;
    private final ReentrantLock reentrantLock;
    private static FFmpegUtils utils;
    private static final Object lock = new Object();

    public static FFmpegUtils getInstance() {
        if (utils == null) {
            synchronized (lock) {
                if (utils == null) {
                    utils = new FFmpegUtils();
                }
            }
        }
        return utils;
    }

    private FFmpegUtils() {
        //  mainHandler = new Handler();
        ffmpeg = new FFmpeg();
        reentrantLock = new ReentrantLock();
    }


    public int execffmpeg(String cmd) {
        String regulation = "[ \\t]+";
        String[] split = cmd.split(regulation);
        return execffmpeg(split, null);
    }
    public int execffmpeg(String cmd, FFmpegCallBack callBack) {
        String regulation = "[ \\t]+";
        String[] split = cmd.split(regulation);
        return execffmpeg(split, callBack);
    }

    public int execffmpeg(String[] cmd) {
        return execffmpeg(cmd, null);
    }

    public int execffmpeg(String[] cmd, FFmpegCallBack callBack) {
        int code;
        reentrantLock.lock();
        try {
            code = ffmpeg.execffmpeg(cmd, callBack);
        } finally {
            reentrantLock.unlock();
        }
        return code;
    }

    public String execffprobe(String cmd) {
        String regulation = "[ \\t]+";
        String[] split = cmd.split(regulation);
        return execffprobe(split);
    }

    public String execffprobe(String[] cmd) {
        String json;
        reentrantLock.lock();
        try {
            json = ffmpeg.execffprobe(cmd);
        } finally {
            reentrantLock.unlock();
        }
        return json;
    }

    public void exitffmpeg() {
        ffmpeg.exitffmpeg();
    }

    public void setDebugMode(boolean debug) {
        ffmpeg.setDebugMode(debug);
    }
}
