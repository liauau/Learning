package com.wow.learning.openGL;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author ljinjin on 12/8/16.
 */

public class OpenGLES20Activity extends AppCompatActivity {
    private GLSurfaceView mGlView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGlView = new GLSurfaceView(this);
        setContentView(mGlView);
    }


    class MyGlSurfaceView extends GLSurfaceView {
        public MyRenderer mRenderer;

        public MyGlSurfaceView(Context context) {
            super(context);
            setEGLContextClientVersion(2);
            mRenderer = new MyRenderer();
            setRenderer(mRenderer);
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }

    }

    class MyRenderer implements GLSurfaceView.Renderer {
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0 ,width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        }
    }
}
