//
// Created by Trust on 2023/4/25.
//

#ifndef DEMOAPP_VIDEODECODING_H
#define DEMOAPP_VIDEODECODING_H


#include "BaseDecoding.h"
#include "tools/Bridge.h"
#include "tools/Log.h"

extern "C"{
//#include "../../../libs/includes/libavformat/avformat.h"
//#include "../../../libs/includes/libavcodec/avcodec.h"
#include "../../../libs/includes/libavformat/avformat.h"
#include "../../../libs/includes/libavcodec/avcodec.h"
#include "../../../libs/includes/libavutil/imgutils.h"
#include "../../../libs/includes/libavutil/time.h"
#include "../../../libs/includes/libswscale/swscale.h"
#include "../../../libs/includes/libavutil/channel_layout.h"
#include "../../../libs/includes/libswresample/swresample.h"
};
#include <pthread.h>


typedef void (*showFrameCallback)(void*,uint8_t *, int, int, int);

class VideoDecoding : public BaseDecoding  {


public:
    VideoDecoding(int index, Bridge *b, AVFormatContext *avFormatContext,
                  AVCodecContext *avCodecContext, const AVCodec* avCodec) : BaseDecoding(index, b, avFormatContext, avCodecContext, avCodec) {

    }


//    virtual void play();
//    virtual void stop();


    void video_thrad();

    void play();
    void pause();

    pthread_t video_pt;

    void* player;

    showFrameCallback frame_call_back;

    void setFrameCallBack(showFrameCallback call_back);
};


#endif //DEMOAPP_VIDEODECODING_H
