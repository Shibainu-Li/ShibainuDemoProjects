//
// Created by Trust on 2023/4/25.
//

#include "VideoDecoding.h"

void* video_pthread(void* args){
    auto decoding = static_cast<VideoDecoding*>(args);
    decoding->video_thrad();
    return nullptr;
}



void VideoDecoding::video_thrad() {
    packet = av_packet_alloc();
    frame = av_frame_alloc();
    SwsContext * sws_ctx;
    uint8_t *dst_data[4];
    int dst_linesize[4];

    while (is_playing){
        result = av_read_frame(fmt_ctx,packet);
        if(result >= 0){
            result = avcodec_send_packet(codec_ctx,packet);

            if(result == AVERROR(EAGAIN)){
                LOGD("error send packet\n");
            }else{
                av_packet_unref(packet);
            }


            while (result >= 0 && is_playing){
                result = avcodec_receive_frame(codec_ctx,frame);

                if(result == AVERROR(EAGAIN)){
                    LOGD("EAGAIN");
                    continue;
                }
                else if(result == AVERROR_EOF){
                    LOGD("AVERROR_EOF");
                    is_playing = 0;
                    break;
                }
                else if(result <0 ){
                    LOGD("error during decoding");
                    is_playing = 0;
                    break;
                }


                sws_ctx = sws_getContext(
                        codec_ctx->width,codec_ctx->height,codec_ctx->pix_fmt,
                        codec_ctx->width,codec_ctx->height,AV_PIX_FMT_RGBA,
                        SWS_BILINEAR,0,0,0);

                av_image_alloc(dst_data,dst_linesize,codec_ctx->width,codec_ctx->height,AV_PIX_FMT_RGBA,1);

                sws_scale(
                        sws_ctx,
                        reinterpret_cast<const uint8_t *const *>(frame->data),
                        frame->linesize,0,frame->height,
                        dst_data,dst_linesize
                );
                frame_call_back(player,dst_data[0],dst_linesize[0],codec_ctx->width,codec_ctx->height);
            }

        }else{
            LOGD("read packet error result:%d",result);
        }
    }
}

void VideoDecoding::play() {
    is_playing = true;
    pthread_create(&video_pt, nullptr,video_pthread,(void *)this);
}

void VideoDecoding::setFrameCallBack(showFrameCallback call_back) {
    frame_call_back = call_back;
}

void VideoDecoding::pause() {
    is_playing = false;
}



