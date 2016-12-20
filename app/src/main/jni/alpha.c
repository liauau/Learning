#include "com_wow_learning_jni_JNIProvider.h"
#include "android/log.h"

JNIEXPORT void JNICALL
Java_com_wow_learning_jni_JNIProvider_test(JNIEnv *env, jclass type) {
    __android_log_write(ANDROID_LOG_ERROR, "liauau", "TEST");
}

