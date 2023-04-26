//
// Created by Trust on 2023/4/25.
//

#ifndef DEMOAPP_AUDIODECODING_H
#define DEMOAPP_AUDIODECODING_H

extern "C"{
#include "../../../libs/includes/libavformat/avformat.h"
#include "../../../libs/includes/libavcodec/avcodec.h"
#include "../../../libs/includes/libswresample/swresample.h"

};
#include <pthread.h>

#include "BaseDecoding.h"

class AudioDecoding :BaseDecoding{
public:
    AudioDecoding(int index, Bridge *b, AVFormatContext *avFormatContext,
                  AVCodecContext *avCodecContext,const AVCodec *avCodec);


    uint8_t * data = 0;
    int out_channels;
    int out_sample_size;
    int out_sample_rate;

    SwrContext* swr_ctx = 0;


    void audio_thread();

    void play();
    void pause();
};


#endif //DEMOAPP_AUDIODECODING_H
