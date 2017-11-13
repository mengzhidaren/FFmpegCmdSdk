package com.yyl.ffmpeg;


import android.content.Context;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yuyunlong on 2017/10/16/016.
 */

public class FFmpegUtils {
    private final FFmpeg ffmpeg;
    private final ReentrantLock reentrantLock;
    private static FFmpegUtils utils;
    private static final Object lock = new Object();

    public boolean isRun() {
        return isRun;
    }

    private boolean isRun;

    public static boolean isSport() {
        return FFmpeg.isSport();
    }

    public static void checkLib(Context context) {
        if (!isSport()) {
            CpuArchHelper.checkCPU(context);
        }
    }


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
        reentrantLock.lock();
        isRun = true;
        try {
            return ffmpeg.execffmpeg(cmd, callBack);
        } finally {
            reentrantLock.unlock();
            isRun = false;
        }
    }

    public String execffprobe(String cmd) {
        String regulation = "[ \\t]+";
        String[] split = cmd.split(regulation);
        return execffprobe(split);
    }

    public String execffprobe(String[] cmd) {
        reentrantLock.lock();
        try {
            return ffmpeg.execffprobe(cmd);
        } finally {
            reentrantLock.unlock();
        }
    }

    public void exitffmpeg() {
        ffmpeg.exitffmpeg();
    }

    public void setDebugMode(boolean debug) {
        ffmpeg.setDebugMode(debug);
    }

    public void isShowLogcat(boolean showLogcat) {
        ffmpeg.isShowLogcat(showLogcat);
    }

}
