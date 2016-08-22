#include <stdio.h>
#include <jni.h>

JNIEXPORT jstring
JNICALL Java_com_wow_learning_jni_JNIProvider_getName (JNIEnv *env, jclass class) {
    return (*env)->NewStringUTF(env, "my name is apple, this is a beautiful world. ");
}

JNIEXPORT jint
JNICALL Java_com_wow_learning_jni_JNIProvider_getCount(JNIEnv *env, jclass class) {
    return 10;
}

JNIEXPORT jint
JNICALL Java_com_wow_learning_jni_JNIProvider_getMoney (JNIEnv *env, jclass class) {
    return 10000;
}
