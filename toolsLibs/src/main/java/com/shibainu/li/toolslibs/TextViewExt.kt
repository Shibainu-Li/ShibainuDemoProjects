package com.shibainu.li.toolslibs

import android.widget.Button
import android.widget.TextView

fun TextView.showStr(strId:Int) = context.getStr(strId).also { text = it }
fun Button.showStr(strId:Int) = context.getStr(strId).also { text = it }