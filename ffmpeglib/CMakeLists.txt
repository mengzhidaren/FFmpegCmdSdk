cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_C_STANDARD 99)

#ffmpeg3.2.5
#        src/main/cpp/ffmpeg3_2_5/cmdutils.c
#        src/main/cpp/ffmpeg3_2_5/ffmpeg_filter.c
#        src/main/cpp/ffmpeg3_2_5/ffmpeg.c
#        src/main/cpp/ffmpeg3_2_5/ffmpeg_opt.c
#        src/main/cpp/ffmpeg3_2_5/ffprobe.c

add_library(yylffmpegjni
        SHARED
        #ffmpeg4.1.0
#        src/main/cpp/ffmpeg4_1_0/cmdutils.c
#        src/main/cpp/ffmpeg4_1_0/ffmpeg_filter.c
#        src/main/cpp/ffmpeg4_1_0/ffmpeg.c
#        src/main/cpp/ffmpeg4_1_0/ffmpeg_opt.c
#        src/main/cpp/ffmpeg4_1_0/ffmpeg_hw.c
#        src/main/cpp/ffmpeg4_1_0/ffprobe.c

        #ffmpeg4.1.3
        src/main/cpp/ffmpeg4_1_3/cmdutils.c
        src/main/cpp/ffmpeg4_1_3/ffmpeg_filter.c
        src/main/cpp/ffmpeg4_1_3/ffmpeg.c
        src/main/cpp/ffmpeg4_1_3/ffmpeg_opt.c
        src/main/cpp/ffmpeg4_1_3/ffmpeg_hw.c
        src/main/cpp/ffmpeg4_1_3/ffprobe.c
#        src/main/cpp/ffmpeg4_1_3/ffmpeg_cuvid.c
#        src/main/cpp/ffmpeg4_1_3/ffmpeg_qsv.c
#        src/main/cpp/ffmpeg4_1_3/ffmpeg_videotoolbox.c

        #yyl
        src/main/cpp/yyl_log.c
        src/main/cpp/yyl_config.c
        src/main/cpp/ffmpeg_jni.c
        src/main/cpp/ffprobe_jni.c
        #test
#        src/main/cpp/ffprobe_test.c

        )

add_library(
        yylffmpeg
        SHARED
        IMPORTED
)

message("yyl abi=${ANDROID_ABI}")
set_target_properties(
        yylffmpeg
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libyylffmpeg.so
)
include_directories(
        ${CMAKE_SOURCE_DIR}/ffmpeg4.1.3/ffmpeg
)

target_link_libraries(
        yylffmpegjni
        yylffmpeg
        android
        log)