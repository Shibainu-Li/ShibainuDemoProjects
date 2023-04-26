//
// Created by Trust on 2023/4/25.
//

#include <android/native_window_jni.h>
#include <pthread.h>



#ifndef DEMOAPP_SL_PLAYER_H
#define DEMOAPP_SL_PLAYER_H

#include "VideoDecoding.h"
#include "AudioDecoding.h"



extern "C"{
#include <libavformat/avformat.h>
#include <libavcodec/avcodec.h>
};


#include "tools/Bridge.h"
#include "tools/Log.h"
#include "tools/PlayerStatus.h"
#include "tools/PlayerCMD.h"




class SL_Player {
private:

    AVFormatContext* fmt_ctx = nullptr;
    AVCodecContext* codec_ctx = nullptr;

    VideoDecoding* video_ecoding = nullptr;
    AudioDecoding* audio_ecoding = nullptr;

    pthread_t read_pt;
    int result;
    short player_cmd = VIDEO_AND_AUDIO;



public:
    pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
    ANativeWindow* window = nullptr;
    const char* file_path = nullptr;
    Bridge* mBridge = nullptr;
public:
    SL_Player();

    void preper();
    void read_thread();
    void play_video();
    void play_audio();
    void play();
    void stop();
    void pause();


//    static SL_Player* mSL_Player;

//    static SL_Player* getInstance(){
//        if(mSL_Player == nullptr){ mSL_Player = new SL_Player(); }
//        return mSL_Player;
//    }
};




#endif //DEMOAPP_SL_PLAYER_H
