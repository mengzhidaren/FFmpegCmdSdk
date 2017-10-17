----
本库是基于RecyclerViewHeader的扩展。
###ffmpeg  ffprobe 

# FFMPEG
1. ffmpeg日志回调接口
2. ffmpeg增加监听百分比进度（看着demo用吧懒得改了）
```
ffmpeg最好只运行一个进程线程  
如：转码mp4 CPU利用率90%以上 其它视频不低于60%波动  （冬天暖手宝）
```
# FFPROBE
1. ffprobe 只实现了json回调 
2. ffprobe线程和ffmpeg线程独立运行
# FFMPEG 


# 截图

<image src="./img/111.gif" width="400px"/>

## 引入
* Gradle
```groovy
compile 'com.yyl.ffmpeg:ffmpeg_cmd:1.0.0'

目前支持的库 只有 armeabi-v7a 其它的等我更新
```
## 开发
```
    
    String cmd = "ffprobe -v quiet -print_format json -show_format -i " +inputFile;
    String json = FFmpegUtils.getInstance().execffprobe(cmd);
  
    String cmd = "ffmpeg -h"
    int code = FFmpegUtils.getInstance().execffmpeg(cmd,callback);
    
```
### 参考代码
本库的编译角本参考了https://github.com/wysaid/android-gpuimage-plus
感谢作者开源库。
###编译版本号官网2017-10-17最新版本