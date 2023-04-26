//
// Created by Trust on 2023/4/25.
//

#include "SL_Player.h"





void showFrame (void* args,uint8_t *data, int linesize, int w, int h){
    SL_Player* player = static_cast<SL_Player *>(args);
    LOGD("showFrame-----");
    pthread_mutex_lock(&(player->mutex));
    if(!player->window){
        pthread_mutex_unlock(&(player->mutex));
        return;
    }

    ANativeWindow_setBuffersGeometry(player->window,w,h,WINDOW_FORMAT_RGBA_8888);

    ANativeWindow_Buffer window_buffer;
    if (ANativeWindow_lock(player->window, &window_buffer, 0)) {
        ANativeWindow_release(player->window);
        player->window = 0;
        pthread_mutex_unlock(&(player->mutex));
        return;
    }
    auto *dst_data = static_cast<uint8_t *>(window_buffer.bits);
    //一行需要多少像素 * 4(RGBA)
    int dst_linesize = window_buffer.stride * 4;
    //一次拷贝一行
    for (int i = 0; i < window_buffer.height; ++i) {
        memcpy(dst_data + i * dst_linesize, data +  i * linesize, linesize);
    }
    ANativeWindow_unlockAndPost(player->window);
    pthread_mutex_unlock(&(player->mutex));
}

void* read_pthread(void* args){
    auto* slPlayer =  static_cast<SL_Player *>(args);
    slPlayer->read_thread();
    return nullptr;
}



void SL_Player::preper() {
    if(file_path == nullptr){
        mBridge->callPlayerStatus(FILE_PATH_IS_NULL);
        return;
    }

    pthread_create(&read_pt, nullptr,read_pthread,this);
}

void SL_Player::read_thread() {
    result = avformat_open_input(&fmt_ctx,file_path, nullptr, nullptr);
    if(result<0){
        LOGD("avformat open error : %d",result);
        mBridge->callPlayerStatus((OPEN_FILE_ERROR));
        return;
    }

    int size = fmt_ctx->nb_streams;
    if(size <= 0){
        LOGD("no find nb_streams");
        mBridge->callPlayerStatus(NO_STREAM);
        return;
    }

    for (int i = 0; i < size; ++i) {
        auto codecpar = fmt_ctx->streams[i]->codecpar;
         const AVCodec* codec = avcodec_find_decoder(codecpar->codec_id);
        if(!codec){
            LOGD("no find avcodec");
            mBridge->callPlayerStatus(OPEN_AVCODEC_ERROR);
            return;
        }

        codec_ctx = avcodec_alloc_context3(codec);

        if(avcodec_parameters_to_context(codec_ctx,codecpar)<0){
            LOGD("cp codec_ctx error");
            mBridge->callPlayerStatus(CODEC_CTX_ERROR);
            return;
        }


        if(avcodec_open2(codec_ctx,codec, nullptr) != 0){
            LOGD("open codec error");
            mBridge->callPlayerStatus(OPEN_AVCODEC_ERROR);
            return;
        }


        switch (codecpar->codec_type) {
            case AVMEDIA_TYPE_UNKNOWN:
                break;
            case AVMEDIA_TYPE_VIDEO:
                if(player_cmd == PlayerCMD::ONLY_VIDEO || player_cmd == PlayerCMD::VIDEO_AND_AUDIO){
                    video_ecoding = new VideoDecoding(i, mBridge, fmt_ctx, codec_ctx, codec);
                    mBridge->callVideoInfo(codec_ctx->width,codec_ctx->height,fmt_ctx->duration);
                    video_ecoding->setFrameCallBack(showFrame);
                    video_ecoding->player = this;
                }
                break;
            case AVMEDIA_TYPE_AUDIO:
                if(player_cmd == PlayerCMD::ONLY_AUDIO || player_cmd == PlayerCMD::VIDEO_AND_AUDIO){
                    audio_ecoding = new AudioDecoding( i, mBridge,fmt_ctx, codec_ctx, codec);
                }

                break;
            case AVMEDIA_TYPE_DATA:
                break;
            case AVMEDIA_TYPE_SUBTITLE:
                break;
            case AVMEDIA_TYPE_ATTACHMENT:
                break;
            case AVMEDIA_TYPE_NB:
                break;
        }
    }
}

void SL_Player::play_video() {
    player_cmd = PlayerCMD::ONLY_VIDEO;
    video_ecoding->play();
}

void SL_Player::play_audio() {
    player_cmd = PlayerCMD::ONLY_AUDIO;
    audio_ecoding->play();
}

void SL_Player::play() {
    player_cmd = PlayerCMD::VIDEO_AND_AUDIO;
    video_ecoding->play();
    audio_ecoding->play();
}

SL_Player::SL_Player() {
    mutex = PTHREAD_MUTEX_INITIALIZER;
}

void SL_Player::stop() {}

void SL_Player::pause() {
    video_ecoding->pause();
    audio_ecoding->pause();
}






