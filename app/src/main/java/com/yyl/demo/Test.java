package com.yyl.demo;

import android.os.Environment;

import java.io.File;

/**
 * Created by yuyunlong on 2017/10/17/017.
 */

public class Test {

    public static final String inputFile = new File(Environment.getExternalStorageDirectory(), "rmvb.rmvb").getAbsolutePath();
    public static final String outFile = new File(Environment.getExternalStorageDirectory(), "atest.mp4").getAbsolutePath();
    public static final String logoFile = new File(Environment.getExternalStorageDirectory(), "logo.png").getAbsolutePath();


    public static  FFmpegApi  getTest1() {
        return new FFmpegApi(inputFile, outFile, "00:00:10", "00:01:01", logoFile);
    }

}
