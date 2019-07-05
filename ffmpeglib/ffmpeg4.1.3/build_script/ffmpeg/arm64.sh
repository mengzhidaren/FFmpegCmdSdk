#!/bin/bash

PLATFORM=$NDK/platforms/android-21/arch-arm64/
PREBUILT=$NDK/toolchains/aarch64-linux-android-4.9/prebuilt/darwin-x86_64

GENERAL="\
--enable-small \
--enable-cross-compile \
--extra-libs="-lgcc" \
--arch=aarch64 \
--cc=$PREBUILT/bin/aarch64-linux-android-gcc \
--cross-prefix=$PREBUILT/bin/aarch64-linux-android- \
--nm=$PREBUILT/bin/aarch64-linux-android-nm \
--extra-cflags="-I${PREFIX}/x264/android/arm64/include" \
--extra-ldflags="-L${PREFIX}/x264/android/arm64/lib" "


MODULES="\
--enable-gpl \
--enable-libx264"


#编译结果的目录
temp_prefix=${PREFIX}/ffmpeg/android/arm64
rm -rf $temp_prefix
export PATH=$PREBUILT/bin/:$PATH/

rm compat/strtod.o
rm compat/strtod.d

function build_arm64
{
  ./configure \
  --logfile=conflog.txt \
  --prefix=${temp_prefix} \
  --target-os=android \
  --cpu=armv8-a \
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
  --extra-cflags="" \
  --extra-ldflags="-lx264 -Wl,-rpath-link=$PLATFORM/usr/lib -L$PLATFORM/usr/lib -nostdlib -lc -lm -ldl -llog"

  make clean
  make -j10
  make install 

   aarch64-linux-android-ld \
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
    ${PREFIX}/x264/android/arm64/lib/libx264.a \
    -lc -lm -lz -ldl -llog --dynamic-linker=/system/bin/linker \
    $PREBUILT/lib/gcc/aarch64-linux-android/4.9.x/libgcc.a

    cp $temp_prefix/${SONAME} $temp_prefix/libffmpeg-debug.so
    aarch64-linux-android-strip --strip-unneeded $temp_prefix/${SONAME}

    echo SO-Dir=${temp_prefix}/${SONAME}
}

build_arm64


echo Android ARM64 builds finished
