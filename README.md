----
本库是基于RecyclerViewHeader的扩展。
###ffmpeg  ffprobe 

# 特性
1. ffmpeg日志回调接口
2. ffmpeg增加监听百分比进度（看着demo用吧懒得改了）
3. ffprobe 只实现了json回调 

# 截图
## HeaderVideo

<image src="./img/111.gif" width="400px"/>

## 引入
* Gradle
```groovy
compile 'com.yyl.ffmpeg:ffmpeg_cmd:1.0.0'
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