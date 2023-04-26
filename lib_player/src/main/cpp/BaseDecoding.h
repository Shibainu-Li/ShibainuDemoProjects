//
// Created by Trust on 2023/4/25.
//


#ifndef DEMOAPP_BASEDECODING_H
#define DEMOAPP_BASEDECODING_H

extern "C"{
#include "../../../libs/includes/libavformat/avformat.h"
#include "../../../libs/includes/libavcodec/avcodec.h"
};

#include "tools/Bridge.h"
#include "tools/Log.h"
#include <pthread.h>
class BaseDecoding {
public:


    BaseDecoding(
            int index,Bridge* b,AVFormatContext* avFormatContext,AVCodecContext* avCodecContext,const AVCodec* avCodec
            ){
        stream_index = index;
        fmt_ctx = avFormatContext;
        codec_ctx = avCodecContext;
        codec = avCodec;
        bridge = b;
    };





    int stream_index;
    AVFrame* frame;
    AVPacket* packet;
    AVFormatContext* fmt_ctx;
    AVCodecContext* codec_ctx;
    const AVCodec* codec;
    Bridge* bridge = nullptr;

    int result;

    bool is_playing = false;

    pthread_t pid;

//    virtual void play() = 0;
//    virtual void stop() = 0;

};


#endif //DEMOAPP_BASEDECODING_H
