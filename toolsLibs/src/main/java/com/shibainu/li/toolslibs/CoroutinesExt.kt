package com.shibainu.li.toolslibs

import kotlinx.coroutines.*

inline fun csMain(crossinline block:()->Unit) = GlobalScope.launch(Dispatchers.Main) { block() }
inline fun csThread(crossinline block:()->Unit) = GlobalScope.launch(Dispatchers.IO) { block() }

inline fun csSuspendThread(crossinline block: suspend () -> Unit) =  GlobalScope.launch(Dispatchers.IO) { block() }

inline fun csCountDown(count:Int, delayTime:Long, crossinline block: (Int) -> Unit) = csSuspendThread {
    var ct = count
    while (ct > 0){
        block(ct--)
        delay(delayTime)
    }
}

inline fun csWaitTime(count:Int, delayTime:Long, crossinline block: () -> Unit) = csCountDown(count,delayTime){
    if(it == 1){
        block()
    }
}