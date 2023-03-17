package com.shibainu.li.baselib

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract open class BaseActivity<T: ViewBinding>:AppCompatActivity() ,IUiView{

    lateinit var viewBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = inflateBindingWithGeneric(layoutInflater)
        setContentView(viewBinding.root)
        initViews(savedInstanceState)
        initConfig()
    }

    abstract fun initViews(savedInstanceState: Bundle?)

    open fun initConfig(){}

    override fun showLoading() { Log.d("base","showLoading") }

    override fun dismissLoading() { Log.d("base","dismissLoading") }

}