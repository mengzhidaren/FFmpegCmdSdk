#!/usr/bin/env bash

chmod a+x build_android_*.sh

src_dir=`pwd`

# Build arm v6 v7a
cp build_android_arm.sh ../../x264/build_android_arm.sh
cd ../../x264
./build_android_arm.sh 

# Build arm64 v8a
#cd $src_dir
#cp build_android_arm64-v8a.sh ../../x264/build_android_arm64-v8a.sh
#cd ../../x264
# ./build_android_arm64-v8a.sh

# Build mips
# ./build_android_mips.sh

# Build mips64
# ./build_android_mips64.sh

# Build x86
# ./build_android_x86.sh

# Build x86_64
# ./build_android_x86_64.sh
