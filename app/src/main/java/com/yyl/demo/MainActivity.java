package com.yyl.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yyl.ffmpeg.FFmpegCallBack;
import com.yyl.ffmpeg.FFmpegUtils;


public class MainActivity extends Activity {

    String tag = "MainActivity";
    private Activity activity;
    LinearLayout outputLayout;
    private EditText editText;
    private ScrollView scrollView;
    private long duration;


    private TextView progressTV;
    FFmpegUtils ffmpeg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;
        setContentView(R.layout.activity_main);
        progressTV = (TextView) findViewById(R.id.progress);
        editText = (EditText) findViewById(R.id.command);
        outputLayout = (LinearLayout) findViewById(R.id.command_output);
        scrollView = (ScrollView) findViewById(R.id.scrollview);
        ffmpeg = FFmpegUtils.getInstance();
        ffmpeg.setDebugMode(true);
    }


    public void onclickrun(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String cmd = editText.getText().toString();
                int code = ffmpeg.execffmpeg(cmd, ffmpegCallBack);
                Log.i(tag, "code=" + code);
            }
        }).start();
    }

    public void onclickstop(View view) {
        ffmpeg.exitffmpeg();
    }

    public void test(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FFmpegApi api = Test.getTest2();
                api.getCmd();
                setCmdText(api.getString());
                int code = ffmpeg.execffmpeg(api.getCmd(), ffmpegCallBack);
                Log.i(tag, "code=" + code);
            }
        }).start();
    }

    public void ffprobe_run(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String cmd = editText.getText().toString();
                String json = ffmpeg.execffprobe(cmd);
                addTextViewToLayout(json);
            }
        }).start();
    }

    public void ffprobe_test(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String cmd = "ffprobe -v quiet -print_format json -show_format -i " + Test.inputFile;
                setCmdText(cmd);
                String json = ffmpeg.execffprobe(cmd);
                addTextViewToLayout(json);
                VideoBeanJson beanJson = VideoBeanJson.getJson(json);
                if (beanJson != null) {
                    duration = beanJson.getTime();
                }
            }
        }).start();
    }

    public FFmpegCallBack ffmpegCallBack = new FFmpegCallBack() {
        @Override
        public void onStart() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    outputLayout.removeAllViews();
                }
            });
        }

        @Override
        public void onCallBackLog(String log) {
            addTextViewToLayout(log);
        }

        @Override
        public void onCallBackPrint(String msg) {
            addTextViewToLayout(msg);
        }

        @Override
        public void onProgress(int frame_number, int milli_second) {
            if (duration > 0 && milli_second > 0) {
                int progress = (int) ((double) milli_second / duration * 100d);
                showProgress(progress);
            }
        }

        @Override
        public void onSuccess() {
            addTextViewToLayout("onSuccess");
            showProgress(100);
        }

        @Override
        public void onFailure(int result) {
            addTextViewToLayout("onFailure   result=" + result);
            showProgress(100);
        }
    };


    private void addTextViewToLayout(String text) {
        final String text1 = text.replace('\n', ' ');
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = new TextView(activity);
                textView.setText(text1);
                outputLayout.addView(textView);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private void showProgress(final int progress) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressTV.setText("当前转码进度：" + progress);
            }
        });
    }

    private void setCmdText(final String cmdText) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.setText(cmdText);
            }
        });
    }
}
