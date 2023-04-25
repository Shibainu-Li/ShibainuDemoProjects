//
// Created by Trust on 2023/4/25.
//


#ifndef DEMOAPP_BASEDECODING_H
#define DEMOAPP_BASEDECODING_H

#include "libavformat/avformat.h"
#include "libavcodec/avcodec.h"

class BaseDecoding {
private:
    AVFormatContext* fmt_ctx = nullptr;
    AVCodec* avCodec = nullptr;
    AVFrame* frame;
    AVPacket* packet;
};


#endif //DEMOAPP_BASEDECODING_H
