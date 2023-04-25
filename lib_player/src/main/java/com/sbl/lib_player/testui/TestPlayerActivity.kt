package com.sbl.lib_player.testui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import com.sbl.lib_player.PlayerManager
import com.sbl.lib_player.R

class TestPlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_player)


        val sur = findViewById<SurfaceView>(R.id.player_sur)

        val playerManager = PlayerManager().apply {
            setPath("")
            setWindow(sur.holder.surface)
            preper()
        }


        findViewById<View>(R.id.playe_start_video).setOnClickListener {
            playerManager.play(PlayerManager.PlayType.VIDEO)
        }
        findViewById<View>(R.id.playe_start_audio).setOnClickListener {
            playerManager.stop()
        }

    }
}