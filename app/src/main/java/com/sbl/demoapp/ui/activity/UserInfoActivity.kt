package com.sbl.demoapp.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.FrameMetrics
import android.view.Window.OnFrameMetricsAvailableListener
import androidx.annotation.RequiresApi
import com.sbl.demoapp.databinding.ActivityUserinfoBinding
import com.shibainu.li.baselib.BaseActivity
import kotlin.properties.Delegates

class UserInfoActivity : BaseActivity<ActivityUserinfoBinding>() {
    private var mPos by Delegates.notNull<Int>()


    override fun initViews(savedInstanceState: Bundle?) {
        mPos = intent.getIntExtra("pos",-1)
        viewBinding.activityHomeTv.let {
            it.setOnClickListener {
                when (mPos) {
                    1  -> { setResult(RESULT_CANCELED) }
                    2  -> {
                        setResult(RESULT_OK,Intent().apply { putExtra("extra","第二个Fragment返回成功") })
                    }
                }
                finish()
            }

            it.text = "homeActivity:$mPos"

        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun initConfig() {
        var lastFrameFinishTime: Long = 0

        val handlerThread = HandlerThread("testFrameMertrics")
        handlerThread.start()
        val monitorHandler = Handler(handlerThread.looper)

        val frameMetriceListener:OnFrameMetricsAvailableListener =
            OnFrameMetricsAvailableListener { window, frameMetrics, dropCountSinceLastInvocation ->
                // TODO 解析FrameMetrics中的数据
                //因为获取到的时间是纳秒需要转换成ms
                val NANOS_PER_MS: Long = 1000000
                //获取本机刷新时间 Vsync信号刷新的时间
                val display = windowManager.defaultDisplay
                val vsyncDuration = Math.floor(1000.0 / display.refreshRate.toInt()).toLong()

                val vsync_timestamp = frameMetrics.getMetric(FrameMetrics.VSYNC_TIMESTAMP)
                val intended_vsync_timestamp =
                    frameMetrics.getMetric(FrameMetrics.INTENDED_VSYNC_TIMESTAMP)
                val total_duration = frameMetrics.getMetric(FrameMetrics.TOTAL_DURATION)

                if (vsync_timestamp >= lastFrameFinishTime && intended_vsync_timestamp == vsync_timestamp) {
                    lastFrameFinishTime = vsync_timestamp + vsyncDuration * NANOS_PER_MS
                }

                val jankTime: Long
                jankTime = if (vsync_timestamp >= lastFrameFinishTime) {
                    total_duration / NANOS_PER_MS
                } else {
                    (vsync_timestamp + total_duration - lastFrameFinishTime) / NANOS_PER_MS
                }

                // 掉帧数 = (超出耗时 / vsync间隔耗时)<需向上取整>

                // 掉帧数 = (超出耗时 / vsync间隔耗时)<需向上取整>
                val lossFrameNum = Math.ceil(jankTime * 1.0 / (vsyncDuration * 1.0)).toInt()

                val log =
                    ("jankTime: " + jankTime + (if (jankTime > 300) " ***************" else "")
                            + ", jankFrame: " + lossFrameNum)
                Log.e("MyJank", log)

                val selfTotalDuration =
                    (total_duration - frameMetrics.getMetric(FrameMetrics.UNKNOWN_DELAY_DURATION)) / NANOS_PER_MS
                val totalDurationWithDisplay =
                    Math.ceil(selfTotalDuration * 1.0 / (vsyncDuration * 1.0))
                        .toInt() * vsyncDuration + vsyncDuration
                lastFrameFinishTime = vsync_timestamp + totalDurationWithDisplay * NANOS_PER_MS
            }

        window.addOnFrameMetricsAvailableListener(frameMetriceListener,monitorHandler)
        //取消监听
        window.removeOnFrameMetricsAvailableListener(frameMetriceListener)
    }

}