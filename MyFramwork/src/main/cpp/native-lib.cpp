//
//在native-lib.cpp文件中添加新的方法，必须添加在extern"C" { } 中，
// 或者在每个方法前加extern"C", 否则会报找不到方法。如果源文件为C，
// 则须将extern“C”部分去掉，因为extern "C"的作用就是告诉编译器以C方式编译。
// Created by li on 2018/6/29.
//

#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_sanshang_li_mybaseframwork_MainActivity_stringFromJNI1(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_sanshang_li_mybaseframwork_jni_JniUtils_getName(JNIEnv *env, jobject instance) {

    // TODO
    std::string hello = "I am i,different fire";
    return env->NewStringUTF(hello.c_str());
}