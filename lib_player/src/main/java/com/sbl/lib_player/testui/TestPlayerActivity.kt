package com.sbl.lib_player.testui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.View.OnAttachStateChangeListener
import com.sbl.lib_player.PlayerManager
import com.sbl.lib_player.R


class TestPlayerActivity : AppCompatActivity() {
    private lateinit var mPlayerManager:PlayerManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_player)


        val sur = findViewById<SurfaceView>(R.id.player_sur)
        sur.holder.addCallback(object :
            SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                mPlayerManager = PlayerManager().apply {
                    setPath(application.getExternalFilesDir(null).toString() + "/yjdwb-1.mp4")
                    setWindow(holder.surface)
                    preper()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {}
        })




        findViewById<View>(R.id.playe_start_video).setOnClickListener {
            mPlayerManager.stop()
            mPlayerManager.play(PlayerManager.PlayType.ONLY_VIDEO)
        }
        findViewById<View>(R.id.playe_start_audio).setOnClickListener {
            mPlayerManager.stop()
            mPlayerManager.play(PlayerManager.PlayType.ONLY_AUDIO)
        }

        findViewById<View>(R.id.start_audio_and_video).setOnClickListener {
            mPlayerManager.stop()
            mPlayerManager.play(PlayerManager.PlayType.VIDEO_AND_AUDIO)
        }

        findViewById<View>(R.id.playe_pause).setOnClickListener { mPlayerManager.pause() }
    }
}