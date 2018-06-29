//
// Created by li on 2018/6/29.
//

#include <jni.h>
#include <string>
#include <android/log.h>
#define LOG_TAG "--TAG--"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)



extern "C"
JNIEXPORT jstring JNICALL
Java_com_sanshang_li_mybaseframwork_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from my C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_sanshang_li_mybaseframwork_jni_JniUtils_getString(JNIEnv *env, jobject instance) {

    // TODO

    std::string returnValue = "I came from China!";

    LOGD("我是底层日志%s",returnValue.c_str());

    return env->NewStringUTF(returnValue.c_str());
}