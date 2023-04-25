//
// Created by Trust on 2023/4/25.
//

#include <jni.h>
#include <android/native_window_jni.h>

#include "tools/Log.h"
#include "SL_Player.h"
#include "tools/Bridge.h"

JavaVM* mVm;
JNIEnv* mEnv;

SL_Player* mSL_Player;
Bridge* mBridge;

SL_Player* getInstance(){
    if(mSL_Player == nullptr){
        mSL_Player = new SL_Player();
        mSL_Player->mBridge = mBridge;
    }
    return mSL_Player;
}


void setPath(JNIEnv* env,jobject thiz,jstring file_path){
    mBridge->thiz = thiz;
    const char* path = env->GetStringUTFChars(file_path, nullptr);
    getInstance()->file_path = nullptr;
}

void setWindow(JNIEnv* env,jobject thiz,jobject surface){
    LOGD("setWindow");
    getInstance()->window =  ANativeWindow_fromSurface(env,surface);
}


void preper(JNIEnv* env,jobject thiz){
    getInstance()->preper();
}

void play(JNIEnv* env,jobject thiz,jint type){}
void pause(JNIEnv* env,jobject thiz){}
void stop(JNIEnv* env,jobject thiz){}


/**
* 函数关系表
*/

JNINativeMethod methods[] = {
        { "setPath", "(Ljava/lang/String;)V",(void *)setPath },
        { "setWindow", "(Landroid/view/Surface;)V",(void *)setWindow },
        { "play", "(I)V",(void *)play},
        { "pause", "()V",(void *)pause},
        { "stop", "()V",(void *)stop},
        { "preper", "()V",(void *)preper}
};


jint JNI_OnLoad(JavaVM* vm, void* reserved){
    mVm = vm;
    mBridge = new Bridge();
    mBridge->vm = mVm;
    if(mVm->GetEnv((void **)&mEnv,JNI_VERSION_1_6) != JNI_OK){ return JNI_ERR;}

    jclass claszz = mEnv->FindClass("com/sbl/lib_player/PlayerManager");
    if(claszz == nullptr){ return JNI_ERR; }

    if(mEnv->RegisterNatives(claszz,methods,6 ) < 0){ return JNI_ERR; }

    return JNI_VERSION_1_6;
}