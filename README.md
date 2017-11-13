----
###
###ffmpeg and ffprobe android command line tools

# FFMPEG
1. ffmpeg日志回调接口
2. ffmpeg增加监听百分比进度（具体看demo）
```
ffmpeg最好只运行一个进程线程  
如：转码mp4 CPU利用率80%以上 其它视频不低于60%间波动  （冬天暖手宝）
```
# FFPROBE
1. ffprobe 只实现了json回调 
2. ffprobe线程和ffmpeg线程独立运行
# FFMPEG 


# 截图

<image src="./img/222.gif" width="500px"/>

## 引入
* Gradle
```groovy
compile 'com.yyl.ffmpeg:ffmpeg_cmd:1.0.1'

目前支持的库 armeabi-v7a
 ndk {
            abiFilters "armeabi-v7a"
      }
```
## 开发
```
    
   //ffprobe -v error -print_format json -select_streams v:0 -show_entries stream=width,height,index inputFile
    String cmd = "ffprobe -v quiet -print_format json -show_format -i " +inputFile;
    String json = FFmpegUtils.getInstance().execffprobe(cmd);
  
    String cmd = "ffmpeg -h"
    int code = FFmpegUtils.getInstance().execffmpeg(cmd,callback);
    
```

####编译版本号官网2017-10-17最新版本ffmpeg和h264