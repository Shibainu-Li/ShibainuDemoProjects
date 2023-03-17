package com.shibainu.li.toolslibs

import android.widget.EditText

fun EditText.initConfig(hintId:Int? = null){

    hintId?.let { this.hint = context.getStr(it) }


}