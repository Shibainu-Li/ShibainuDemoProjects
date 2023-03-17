package com.shibainu.li.baselib

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

inline fun Fragment.toActivityResultLauncher(crossinline block: (Boolean,Intent?)->Unit) = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    block(it.resultCode == AppCompatActivity.RESULT_OK,it.data)
}