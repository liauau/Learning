#include <stdio.h>
#include <stdlib.h>

#include "com_wow_learning_JniActivity.h"

JNIEXPORT jstring
JNICALL Java_com_wow_learning_JniActivity_getStrFromC (JNIEnv *env, jclass class) {
    return (*env)->NewStringUTF(env, "Hello from JNI , it is not normal, but it did happen !");
  }
