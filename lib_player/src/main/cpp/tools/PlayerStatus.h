//
// Created by Trust on 2023/4/25.
//

#ifndef DEMOAPP_PLAYERSTATUS_H
#define DEMOAPP_PLAYERSTATUS_H


enum PlayerStatus {
    //正常

    //准备ok
    PREPER_OK,

    //正在播放
    PLAYING,

    //播放完毕
    PLAY_END,

    //异常


    //文件路径为null
    FILE_PATH_IS_NULL,

    //打开文件失败
    OPEN_FILE_ERROR,

    //找不到音频
    NO_AUDIO,

    //找不到视频
    NO_VIDEO,

    //找不到任何流
    NO_STREAM,

    //打开codec失败
    OPEN_AVCODEC_ERROR,

    //codec ctx error
    CODEC_CTX_ERROR,



};


#endif //DEMOAPP_PLAYERSTATUS_H
