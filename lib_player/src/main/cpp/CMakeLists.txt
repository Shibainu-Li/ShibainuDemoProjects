cmake_minimum_required(VERSION 3.6.0)

file(GLOB allCpp *.cpp)

add_library(
        player-native-lib
        SHARED
        ${allCpp}
)

find_library(
        log-lib
        log
)

# 设置头文件
set(header_path ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/includes)
#设置so所在目录
set(so_path ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/${CMAKE_ANDROID_ARCH_ABI})

# 设置扫描so库对应的请求头目录
include_directories(${header_path})

#导入ffmpeg相关库
# avcodec
add_library(avcodec SHARED IMPORTED)
set_target_properties(avcodec PROPERTIES IMPORTED_LOCATION ${so_path}/libavcodec.so)

#avdevice
add_library(avdevice SHARED IMPORTED)
set_target_properties(avdevice PROPERTIES IMPORTED_LOCATION ${so_path}/libavdevice.so)

#avfilter
add_library(avfilter SHARED IMPORTED)
set_target_properties(avfilter PROPERTIES IMPORTED_LOCATION ${so_path}/libavfilter.so)

#avformat
add_library(avformat SHARED IMPORTED)
set_target_properties(avformat PROPERTIES IMPORTED_LOCATION ${so_path}/libavformat.so)


#avutil
add_library(avutil SHARED IMPORTED)
set_target_properties(avutil PROPERTIES IMPORTED_LOCATION ${so_path}/libavutil.so)

#postproc
add_library(postproc SHARED IMPORTED)
set_target_properties(postproc PROPERTIES IMPORTED_LOCATION ${so_path}/libpostproc.so)

#swresample
add_library(swresample SHARED IMPORTED)
set_target_properties(swresample PROPERTIES IMPORTED_LOCATION ${so_path}/libswresample.so)

#swscale
add_library(swscale SHARED IMPORTED)
set_target_properties(swscale PROPERTIES IMPORTED_LOCATION ${so_path}/libswscale.so)



#链接ffmpeg

target_link_libraries(
        player-native-lib
        avcodec
        avdevice
        avfilter
        avformat
        avutil
        postproc
        swresample
        swscale
        OpenSLES
        android
        ${log-lib}
)