//
// Created by Trust on 2023/4/25.
//



#ifndef DEMOAPP_SL_PLAYER_H
#define DEMOAPP_SL_PLAYER_H

#include <android/native_window_jni.h>

extern "C"{
#include <libavformat/avformat.h>
#include <libavcodec/avcodec.h>
};


#include "decoding/VideoDecoding.h"
#include "decoding/AudioDecoding.h"
#include "tools/Bridge.h"

class SL_Player {
private:

    AVFormatContext* fmt_ctx = nullptr;

    VideoDecoding* videoDecoding = nullptr;
    AudioDecoding* audioDecoding = nullptr;

public:
    ANativeWindow* window = nullptr;
    const char* file_path = nullptr;
    Bridge* mBridge = nullptr;
public:
    void preper();

};


#endif //DEMOAPP_SL_PLAYER_H
