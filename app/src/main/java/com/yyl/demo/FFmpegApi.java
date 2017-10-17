package com.yyl.demo;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by yuyunlong on 2017/8/2/002.
 * <p>
 * -vf 视频过滤器  -af 音频过滤器
 */
public class FFmpegApi {
    //计划转出视频分辨率960x540
    private static final int scaleW = 640, scaleH = 360;
    private static final int scaleRatio = -1;
    //计划转出的视频最大码率400
    private static final int maxrate = 400;
    ArrayList<String> cmd = new ArrayList<>();


    private String videoPath, videoOutPutPath, videoStartTime, videoSaveTime, logoPath;

    public FFmpegApi(ArrayList<String> cmd) {
        this.cmd = cmd;
    }

    public FFmpegApi(String videoPath, String videoOutPutPath, String videoStartTime, String videoSaveTime, String logoPath) {
        this.videoPath = videoPath;
        this.videoOutPutPath = videoOutPutPath;
        this.videoStartTime = videoStartTime;
        this.videoSaveTime = videoSaveTime;
        this.logoPath = logoPath;
    }

    public String[] getCmd() {
        cmd.clear();
        cmd.add("ffmpeg");
//        cmd.add("-loglevel");
//        cmd.add("verbose");
        catTime(videoStartTime, videoSaveTime);
        inputVideo(videoPath);
        logo();//logo要在文件之后
        threads();
   //     setVideoScale(videoW, videoH, videoRotation);
        cmd.add("-s");
        cmd.add("640x360");
        encoderV();
        encoderA();
       //   crfAuto();
        zerolatency();
        //  speed();
        rateFps();
        cmd.add("-y");
        outputVideo(videoOutPutPath);
        String[] cmds = new String[cmd.size()];
        return cmd.toArray(cmds);
    }

    public String getString() {
        String commend = "";
        for (int i = 0; i < cmd.size(); i++) {
            commend += cmd.get(i) + " ";
        }
        return commend;
    }


    /**
     * 剪切时间
     * -ss 1开始时间(00:00:00) -t 2剪切时间20秒(00:00:20)
     * 在-i之前使用
     */
    private void catTime(String startTime, String saveTime) {
        cmd.add("-ss");
        cmd.add(startTime);
        cmd.add("-t");
        cmd.add(saveTime);
    }


    private void inputVideo(String inputPath) {
        cmd.add("-i");
        cmd.add(inputPath);
    }


    private void outputVideo(String outPutPath) {
        //  cmd.add("-strict");
        //  cmd.add("-2");
        cmd.add(outPutPath);
    }

    //0延迟   PS：没感觉到
    private void zerolatency() {
        cmd.add("-tune");
        cmd.add("zerolatency");
    }

    private void encoderV() {
        cmd.add("-c:v");
        cmd.add("libx264");
    }

    private void encoderA() {
        cmd.add("-c:a");
        cmd.add("aac");
    }

    //fps 改变帧率，不知道内部如何实现，但是应该不是单纯跳帧这么简单，因为耗时很久，所以不推荐使用
    private void rateFps() {
        cmd.add("-r");
        cmd.add("30000/1001");
    }


    //不推荐使用
    private void threads() {
        cmd.add("-threads");
        cmd.add("16");
    }

    private void speed() {
        cmd.add("-preset");
        cmd.add("ultrafast");
    }

    /**
     * main_h - 视频的高度
     * main_w - 视频的宽度
     * overlay_h - 重叠广告的高度
     * overlay_w - 重叠式广告的宽度
     * 水印定位在视频的中心　-filter_complex "overlay=x=(main_w-overlay_w)/2:y=(main_h-overlay_h)/2"
     * 水印横向移动从左到右  -filter_complex "overlay='if(gte(t,1), -w+(t-1)*200, NAN)':(main_h-overlay_h)/2"
     * 有报错就加 -strict -2
     */
    private void logo() {
        if (TextUtils.isEmpty(logoPath))return;
        cmd.add("-i");
        cmd.add(logoPath);
        cmd.add("-filter_complex");
        //    cmd.add("overlay");//左上角
        //  cmd.add("overlay=W-w");//右上角
        //  cmd.add("overlay=0:H-h");//左下角
        // cmd.add("overlay=W-w:H-h");//右下角
        cmd.add("overlay='if(gte(t,1), -w+(t-1)*200, NAN)':(main_h-overlay_h)/2");//移动从左到右
    }

    private void crfAuto() {
        cmd.add("-crf");
        cmd.add("20");
        cmd.add("-maxrate");
        cmd.add(maxrate + "k");
        cmd.add("-bufsize");
        cmd.add(maxrate * 5 + "k");
    }


    //改变rgb 如亮度
    private void colorRGB() {
        cmd.add("-vf");
        cmd.add("colorlevels=romin=0.5:gomin=0.5:bomin=0.5");
    }


    //超过640x360才改变分辨率 90度和270度视频对调360x640
    private void setVideoScale(int videoW, int videoH, int videoRotation) {
        int convertW, convertH;
        boolean isRotation = false;
        if (videoRotation == 90 || videoRotation == 270) {
            convertW = videoH;
            convertH = videoW;
            isRotation = true;
        } else {
            convertW = videoW;
            convertH = videoH;
        }
        if (convertW >= scaleW || convertH >= scaleH || videoW * videoH <= 0) {
            cmd.add("-vf");
            if (isRotation) {
                cmd.add("scale=" + scaleRatio + ":" + scaleW);
            } else {
                cmd.add("scale=" + scaleW + ":" + scaleRatio);
            }
        }
    }

}
