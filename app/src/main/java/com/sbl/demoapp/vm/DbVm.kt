package com.sbl.demoapp.vm

import com.sbl.demoapp.repository.DbRepository
import com.shibainu.li.baselib.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DbVm: BaseViewModel() {

    private val mDbRepository: DbRepository by lazy { DbRepository() }

    private val _isLogin = MutableStateFlow<Any?>(Any::class.java)
    val isLoginState = _isLogin.asStateFlow()

    fun getIsLogin(){ _isLogin.value = mDbRepository.getIsLogin() }

}