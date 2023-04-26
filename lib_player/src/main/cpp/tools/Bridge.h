//
// Created by Administrator on 2023/4/24.
//
#include <jni.h>
#ifndef SL_PRACTISEPROJECT_BRIDGE_H
#define SL_PRACTISEPROJECT_BRIDGE_H


class Bridge {

public:
    JavaVM * vm = nullptr;
    JNIEnv * env = nullptr;
    jobject thiz = nullptr;

    void callAudioDataToJava(uint8_t *data,int size) {
        if(vm->AttachCurrentThread(&env, nullptr) == JNI_OK){
            jclass jc = env->GetObjectClass(thiz);
            jmethodID  resultAudioId = env->GetMethodID(jc,"resultAudioPcm","([BI)V");
            jbyteArray array = env->NewByteArray(size);
            env->SetByteArrayRegion(array,0,size,(jbyte *)data);
            env->CallVoidMethod(thiz,resultAudioId,array,size);
        }
    }

    void callPlayerStatus(int status){
        if(vm->AttachCurrentThread(&env, nullptr) == JNI_OK){
            jclass jc = env->GetObjectClass(thiz);
            jmethodID  resultAudioId = env->GetMethodID(jc,"resultPlayerStatus","(I)V");
            env->CallVoidMethod(thiz,resultAudioId,status);
        }
    }


    void callVideoInfo(int width,int height,long times){
        if(vm->AttachCurrentThread(&env, nullptr) == JNI_OK){
            jclass jc = env->GetObjectClass(thiz);
            jmethodID  videoInfoID = env->GetMethodID(jc,"resultVideoInfo","(III)V");
            env->CallVoidMethod(thiz,videoInfoID,width,height,times);
        }
    }

    void callAudioInfo(int sampleRate,int encoding,int channelMask,long times){
        if(vm->AttachCurrentThread(&env, nullptr) == JNI_OK){
            jclass jc = env->GetObjectClass(thiz);
            jmethodID  videoInfoID = env->GetMethodID(jc,"resultAudioInfo","(IIII)V");
            env->CallVoidMethod(thiz,videoInfoID,sampleRate,encoding,channelMask,times);
        }
    }


};


#endif //SL_PRACTISEPROJECT_BRIDGE_H
