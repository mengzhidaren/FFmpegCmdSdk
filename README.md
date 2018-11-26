#ffmpeg and ffprobe android command line tools
--
## FFMPEG(4.1.0   官网2018-11-23最新版本)
1. ffmpeg日志回调接口
2. ffmpeg增加监听百分比进度（需要获取转码后视频的总时间 具体看demo）
```
ffmpeg需要单例运行  
如：转码mp4 CPU利用率80%以上 其它视频不低于60%间波动  （冬天暖手宝）
```
## FFPROBE
1. ffprobe 只实现了返回 json 回调 
2. ffprobe线程和ffmpeg线程独立运行

## review runing

<image src="./img/222.gif" width="500px"/>

## Gradle
```
implementation 'com.yyl.ffmpeg:ffmpeg_cmd:4.1.0'

目前支持的库 armeabi-v7a   也可以参考build_android.sh脚本定制
 ndk {
            abiFilters "armeabi-v7a"
      }
```
## java Code
```
    
   //ffprobe -v error -print_format json -select_streams v:0 -show_entries stream=width,height,index inputFile
    String cmd = "ffprobe -v quiet -print_format json -show_format -i " +inputFile;
    String json = FFmpegUtils.getInstance().execffprobe(cmd);
  
    String cmd = "ffmpeg -h"
    int code = FFmpegUtils.getInstance().execffmpeg(cmd,callback);
    
```

####编译版本号官网2018-11-26最新版本ffmpeg和h264

### License
[MIT License](https://opensource.org/licenses/MIT).
