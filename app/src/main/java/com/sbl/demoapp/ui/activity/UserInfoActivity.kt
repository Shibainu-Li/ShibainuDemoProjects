package com.sbl.demoapp.ui.activity

import android.content.Intent
import android.os.Bundle
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

    override fun initConfig() {}

}