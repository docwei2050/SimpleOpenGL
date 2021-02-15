package com.docwei.simpleopengl;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by liwk on 2021/1/13.
 */
public class SimpleRender implements SimpleGlRender{
    public SimpleRender() {
    }

    @Override
    public void onSurfaceCreated() {
        Log.e("openGl","onSurfaceCreated");
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        GLES20.glViewport(0,0,width,height);
        Log.e("openGl","onSurfaceChanged");
    }

    @Override
    public void onDrawFrame() {
        Log.e("openGl","onDrawFrame");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1.0f,1.0f,0.0f,1.0f);
    }
}
