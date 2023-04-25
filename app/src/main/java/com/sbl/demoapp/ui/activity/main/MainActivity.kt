package com.sbl.demoapp.ui.activity.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.sbl.demoapp.databinding.ActivityMainBinding
import com.sbl.demoapp.vm.DbVm
import com.sbl.lib_player.testui.TestPlayerActivity
import com.shibainu.li.baselib.BaseActivity
import com.shibainu.li.baselib.collectIn
import com.shibainu.li.toolslibs.toActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val mAdapter: ViewPagerAdapter by lazy { ViewPagerAdapter(this) }

    private val mDb: DbVm by viewModels()

    override fun initViews(savedInstanceState: Bundle?) {
        viewBinding.acMainVp2.apply {
            adapter = mAdapter
            registerOnPageChangeCallback(object :OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    Log.d("lhh","choose:$position")
                }
            })
        }

        viewBinding.testB.setOnClickListener {
            mDb.getIsLogin()
        }

        viewBinding.toFfmpegPlayer.setOnClickListener {
            toActivity<TestPlayerActivity>()
        }
    }

    override fun initConfig() {
        mDb.isLoginState.collectIn(this, Lifecycle.State.CREATED){
            Log.d("lhh","isLoginState:${this?:"空"}")
        }
        mDb.getIsLogin()
    }

}