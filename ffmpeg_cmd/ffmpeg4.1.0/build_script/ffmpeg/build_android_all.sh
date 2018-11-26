#!/usr/bin/env bash

chmod a+x build_android_*.sh

src_dir=`pwd`

# Build arm v6 v7a
# ./build_android_armeabi.sh

cd $src_dir
cp build_android_armeabi_v7a.sh ../../ffmpeg/build_android_armeabi_v7a.sh
cd ../../ffmpeg
./build_android_armeabi_v7a.sh

# Build arm64 v8a
#cd $src_dir
#cp build_android_arm64_v8a.sh ../../ffmpeg/build_android_arm64_v8a.sh
#cd ../../ffmpeg
#./build_android_arm64_v8a.sh

# Build x86
# ./build_android_x86.sh

# Build x86_64
# ./build_android_x86_64.sh

# Build mips
# ./build_android_mips.sh

# Build mips64   //may fail
# ./build_android_mips64.sh
