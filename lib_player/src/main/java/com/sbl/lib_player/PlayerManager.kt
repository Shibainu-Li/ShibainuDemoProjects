package com.sbl.lib_player

import android.util.Log
import android.view.Surface

class PlayerManager {

    init { System.loadLibrary("player-native-lib") }

    fun play(type:PlayType)  = play(type.ordinal)

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


    fun resultAudioData(bytes:ByteArray,size:Int){

    }


    enum class PlayType{
        VIDEO,
        AUDIO,
        VIDEO_AND_AUDIO
    }
}