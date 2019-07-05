#!/bin/bash
#2018-11-23 14:30  test ok
PLATFORM=$NDK/platforms/android-14/arch-arm/
PREBUILT=$NDK/toolchains/arm-linux-androideabi-4.9/prebuilt/darwin-x86_64

# 原文件配置 ok1
FF_CFLAGS1="-DANDROID -fPIC -ffunction-sections -funwind-tables -fstack-protector \
-march=armv7-a -mfloat-abi=softfp -mfpu=vfpv3-d16 -fomit-frame-pointer -fstrict-aliasing -funswitch-loops -finline-limit=300"

#参考B站编译
FF_CFLAGS2="-O3 -Wall -pipe -std=c99 -ffast-math -fstrict-aliasing -Werror=strict-aliasing \
-Wno-psabi -Wa,--noexecstack -DANDROID -DNDEBUG"


#参考B站编译
FF_EXTRA_CFLAGS2="-march=armv7-a -mcpu=cortex-a8 -mfpu=vfpv3-d16 -mfloat-abi=softfp -mthumb"



GENERAL="\
--enable-small \
--enable-cross-compile \
--extra-libs="-lgcc" \
--arch=arm \
--cpu=cortex-a8 \
--enable-runtime-cpudetect \
--cc=$PREBUILT/bin/arm-linux-androideabi-gcc \
--cross-prefix=$PREBUILT/bin/arm-linux-androideabi- \
--nm=$PREBUILT/bin/arm-linux-androideabi-nm \
--extra-cflags="-I${PREFIX}/x264/android/arm/include" \
--extra-ldflags="-L${PREFIX}/x264/android/arm/lib" "


MODULES="\
--enable-gpl \
--enable-libx264"

#编译结果的目录
temp_prefix=${PREFIX}/ffmpeg/android/armeabi-v7a
rm -rf $temp_prefix
export PATH=$PREBUILT/bin/:$PATH/

rm compat/strtod.o
rm compat/strtod.d

function build_ARMv7
{
  ./configure \
  --prefix=${temp_prefix} \
  --target-os=linux \
  --enable-pic \
  ${GENERAL} \
  --sysroot=$PLATFORM \
  --disable-stripping \
  --enable-zlib \
  --enable-static \
  --enable-pthreads \
  --enable-network \
  --enable-swscale \
  --enable-hwaccels \
  --enable-avfilter \
  --enable-asm \
  --enable-inline-asm \
  --enable-version3 \
  --disable-doc \
  --disable-ffplay \
  --disable-ffmpeg \
  --disable-ffprobe \
  --disable-protocol=udp \
  --disable-protocol=udplite \
  --disable-muxers \
  --enable-muxer=mp4 \
  --enable-muxer=h264 \
  --enable-muxer=image2 \
  --enable-muxer=mjpeg \
  --disable-encoders \
  --enable-encoder=aac \
  --enable-encoder=libx264 \
  --enable-encoder=png \
  --enable-encoder=mjpeg \
  ${MODULES} \
  --enable-neon \
  --enable-thumb \
  --extra-cflags="$FF_CFLAGS2 $FF_EXTRA_CFLAGS2" \
  --extra-ldflags="-Wl,-rpath-link=$PLATFORM/usr/lib -Wl,--fix-cortex-a8 -L$PLATFORM/usr/lib -nostdlib -lc -lm -ldl -llog"

  make clean
  make -j10
  make install

   arm-linux-androideabi-ld \
    -rpath-link=${PLATFORM}usr/lib \
    -L${PLATFORM}usr/lib \
    -L$temp_prefix/lib \
    -soname ${SONAME} -shared -nostdlib -Bsymbolic --whole-archive --no-undefined -o \
    $temp_prefix/${SONAME} \
    libavcodec/libavcodec.a \
    libavdevice/libavdevice.a \
    libavfilter/libavfilter.a \
    libswresample/libswresample.a \
    libavformat/libavformat.a \
    libavutil/libavutil.a \
    libswscale/libswscale.a \
    libpostproc/libpostproc.a \
    ${PREFIX}/x264/android/arm/lib/libx264.a \
    -lc -lm -lz -ldl -llog --dynamic-linker=/system/bin/linker \
    $PREBUILT/lib/gcc/arm-linux-androideabi/4.9.x/libgcc.a

    cp $temp_prefix/${SONAME} $temp_prefix/libffmpeg-debug.so
    arm-linux-androideabi-strip --strip-unneeded $temp_prefix/${SONAME}

    echo SO-Dir=${temp_prefix}/${SONAME}
}

build_ARMv7
echo Android ARMv7-a builds finished
