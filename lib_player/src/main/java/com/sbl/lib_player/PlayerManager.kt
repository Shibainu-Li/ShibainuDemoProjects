package com.sbl.lib_player

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.util.Log
import android.view.Surface

class PlayerManager {

    init { System.loadLibrary("player-native-lib") }


    private lateinit var mAudioTrack:AudioTrack



    fun play(type:PlayType) {
        if(type == PlayType.ONLY_AUDIO || type == PlayType.VIDEO_AND_AUDIO){
            mAudioTrack.play()
        }
        play(type.ordinal)
    }

    external fun setPath(file_path:String)

    external fun setWindow(surface: Surface)

    external fun preper()

    private external fun play(type:Int)

    external fun pause()

    external fun stop()


    //c++回调

    fun resultPlayerStatus(status: Int){
        Log.d("lhh","我是c返回:$status")
    }

    fun resultVideoInfo(width:Int,height:Int,times:Int){
        Log.d("lhh","width:$width|height : $height |times:$times")
    }

    fun resultAudioInfo(sampleRate:Int,encoding:Int,channelMask:Int,times:Int){
        Log.d("lhh","sampleRate:$sampleRate|encoding : $encoding |channelMask:$channelMask|times:$times")
        initAudioTrackConfig(sampleRate)
    }


    fun resultAudioPcm(bytes:ByteArray,size:Int){
        mAudioTrack.write(bytes,0,size)
    }




    private fun initAudioTrackConfig(sampleRate:Int){
        mAudioTrack = object :AudioTrack(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build(),
            AudioFormat.Builder()
                .setSampleRate(44100)
                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                .setChannelMask(AudioFormat.CHANNEL_OUT_STEREO)
                .build(),
            AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT),
            AudioTrack.MODE_STREAM,
            AudioManager.AUDIO_SESSION_ID_GENERATE
        ){}
    }


    enum class PlayType{
        ONLY_AUDIO,
        ONLY_VIDEO,
        VIDEO_AND_AUDIO
    }
}