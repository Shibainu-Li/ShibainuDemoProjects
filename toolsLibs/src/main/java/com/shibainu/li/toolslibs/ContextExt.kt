package com.shibainu.li.toolslibs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

inline fun <reified T> Context.toActivity() = startActivity(Intent(this,T::class.java))
//inline fun Context.toActivity():Boolean{
//    return true
//}


fun Context.getStr(strId:Int) = resources.getString(strId)



//val s = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
//    if (result.resultCode == AppCompatActivity.RESULT_OK) {
//        val data = result.data?.getStringExtra("data")
//        Log.d("FirstActivity", "data =${data}")
//    }
//}

