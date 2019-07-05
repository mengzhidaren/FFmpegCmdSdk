package com.yyl.ffmpeg;


import android.content.Context;
import android.util.Log;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by yuyunlong on 2017/10/16/016.
 * <p>
 * https://github.com/mengzhidaren
 */
public class FFmpegUtils {

    public static final int RESULT_STOP = 3;
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_ERROR = 1;

    private final FFmpeg ffmpeg;
    private final ReentrantLock ffmpegLock;
    private final ReentrantLock ffprobeLock;
    private static FFmpegUtils utils;
    private static final Object lock = new Object();
    private boolean isShowLog;

    public boolean isRun() {
        return isRun;
    }

    private boolean isRun;

    public static boolean hasCompatibleCPU(Context context) {
        return CpuUtils.hasCompatibleCPU(context);
    }

    public static boolean isSport() {
        return FFmpeg.isSport();
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
        ffmpegLock = new ReentrantLock();
        ffprobeLock = new ReentrantLock();
        ffmpeg = new FFmpeg();
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
        ffmpegLock.lock();
        isRun = true;
        try {
            if (isShowLog) {
                Log.i("FFMPEG", Arrays.toString(cmd));
            }
            return ffmpeg.execffmpeg(cmd, callBack);
        } finally {
            ffmpegLock.unlock();
            isRun = false;
        }
    }

    public String execffprobe(String cmd) {
        String regulation = "[ \\t]+";
        String[] split = cmd.split(regulation);
        return execffprobe(split);
    }

    public String execffprobe(String[] cmd) {
        ffprobeLock.lock();
        try {
            if (isShowLog) {
                Log.i("FFPROBE", Arrays.toString(cmd));
            }
            return ffmpeg.execffprobe(cmd);
        } finally {
            ffprobeLock.unlock();
        }
    }

    public void exitffmpeg() {
        ffmpeg.exitffmpeg();
    }

    public void setDebugMode(boolean debug) {
        ffmpeg.setDebugMode(debug);
    }

    public void isShowLogcat(boolean showLogcat) {
        this.isShowLog = showLogcat;
        ffmpeg.isShowLogcat(showLogcat);
    }

}
