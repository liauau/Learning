package com.wow.learning.jni;

/**
 * @author jinjin on 16/8/22.
 */
public class JNIProvider {
    static {
        System.loadLibrary("JNIProvider"); //根据build.gradle文件决定加载哪个so
    }

    public static native String getName();
    public static native int getCount();
    public static native int getMoney();
    public static native void test();
}
