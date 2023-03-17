package com.shibainu.li.baselib

import androidx.lifecycle.LifecycleOwner

interface IUiView :LifecycleOwner {

    fun showLoading()

    fun dismissLoading()

}