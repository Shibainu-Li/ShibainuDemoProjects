//
// Created by Trust on 2023/4/25.
//

#include "AudioDecoding.h"


void* audio_pthread(void* args){
    auto decoding = static_cast<AudioDecoding*>(args);
    decoding->audio_thread();
    return nullptr;
}

AudioDecoding::AudioDecoding(int index, Bridge *b, AVFormatContext *avFormatContext,
                             AVCodecContext *avCodecContext, const AVCodec *avCodec) : BaseDecoding(
        index, b, avFormatContext, avCodecContext, avCodec) {

    out_channels = av_get_channel_layout_nb_channels(AV_CH_LAYOUT_STEREO);
    out_sample_size = av_get_bytes_per_sample(AV_SAMPLE_FMT_S16);
    out_sample_rate = 44100;
    //44100个16位 44100 * 2
    // 44100*(双声道)*(16位)
    data = static_cast<uint8_t *>(malloc(out_sample_rate * out_channels * out_sample_size));
    memset(data,0,out_sample_rate * out_channels * out_sample_size);


    //0+输出声道+输出采样位+输出采样率+  输入的3个参数
    swr_ctx = swr_alloc_set_opts(0, AV_CH_LAYOUT_STEREO, AV_SAMPLE_FMT_S16, out_sample_rate,
                                    codec_ctx->channel_layout, codec_ctx->sample_fmt,
                                    codec_ctx->sample_rate, 0, 0);
    swr_init(swr_ctx);

    bridge->callAudioInfo(out_sample_rate,out_channels,out_channels,fmt_ctx->duration);

}

void AudioDecoding::audio_thread() {

    packet = av_packet_alloc();
    frame = av_frame_alloc();

    while (is_playing){
        result = av_read_frame(fmt_ctx,packet);
        if(result >=0){
            result = avcodec_send_packet(codec_ctx,packet);

            if(result == AVERROR(EAGAIN)){
                LOGD("error send packet");
            }else{
                av_packet_unref(packet);
            }


            while (result >=0 && is_playing){
                result = avcodec_receive_frame(codec_ctx,frame);

                if(result == AVERROR(EAGAIN)){
                    continue;
                }else if(result == AVERROR_EOF){
                    LOGD("error AVERROR_EOF");
                    is_playing = false;
                    break;
                } else if(result <0 ){
                    LOGD("error during decoding");
                    is_playing = false;
                    break;
                }

                LOGD("---audio 解码成功--");

                //48000HZ 8位 =》 44100 16位
                //重采样
                // 假设我们输入了10个数据 ，swrContext转码器 这一次处理了8个数据
                // 那么如果不加delays(上次没处理完的数据) , 积压
                int64_t delays = swr_get_delay(swr_ctx,frame->sample_rate);
                // 将 nb_samples 个数据 由 sample_rate采样率转成 44100 后 返回多少个数据
                // 10  个 48000 = nb 个 44100
                // AV_ROUND_UP : 向上取整 1.1 = 2
                int64_t max_samples =  av_rescale_rnd(delays+frame->nb_samples,out_sample_rate,frame->sample_rate,AV_ROUND_UP);
                //上下文+输出缓冲区+输出缓冲区能接受的最大数据量+输入数据+输入数据个数
                //返whil回 每一个声道的输出数据个数
                int samples = swr_convert(swr_ctx, &data, max_samples, (const uint8_t **)frame->data, frame->nb_samples);
                //获得   samples 个   * 2 声道 * 2字节（16位）
                int data_size =  samples * out_sample_size * out_channels;

                bridge->callAudioDataToJava(data,data_size);
            }

        }

    }

}

void AudioDecoding::play() {
    is_playing = true;
    pthread_create(&pid, nullptr,audio_pthread,this);
}

void AudioDecoding::pause() {
    is_playing = false;
}
