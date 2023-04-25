package com.sbl.demoapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.sbl.demoapp.databinding.FragmentOneBinding
import com.sbl.demoapp.ui.activity.UserInfoActivity
import com.shibainu.li.baselib.BaseFragment
import com.shibainu.li.baselib.testui.NivigationTestActivity
import com.shibainu.li.baselib.toActivityResultLauncher

class OtherFragment (private val mPos:Int): BaseFragment<FragmentOneBinding>() {


    private val toHomeActivity =  toActivityResultLauncher{ status,resultIntent->
        Log.d("lhh","是否达到预期：$status |${resultIntent?.getStringExtra("extra")}")
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        viewBinding.fgTv.apply {
            text = "我是第:${mPos}个Fragment"
            setOnClickListener {
//                toHomeActivity.launch(Intent(context, UserInfoActivity::class.java).apply { putExtra("pos",mPos) })
                toHomeActivity.launch(Intent(context, NivigationTestActivity::class.java).apply { putExtra("pos",mPos) })
            }
        }
    }

    override fun initData() {}
}