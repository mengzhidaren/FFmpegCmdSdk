#!/usr/bin/env bash
export NDK=/Users/long/Desktop/YYL/android-ndk-r14b
#声明前缀
export PREFIX=`pwd`/build
#声明编译生成的动态库名字  C/C++ 静态链接库(.a) 与 动态链接库(.so)
export SONAME=libyylffmpeg.so

echo NDK-Dir=${NDK}
echo PREFIX=${PREFIX}

#获取当前目录
root_dir=`pwd`

cd $root_dir/build_script/x264

./build_android_all.sh


cd $root_dir/build_script/ffmpeg

./build_android_all.sh



#执行当前角本$ chmod +x build_android.sh
