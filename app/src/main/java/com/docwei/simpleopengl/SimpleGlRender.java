package com.docwei.simpleopengl;

/**
 * Created by liwk on 2021/1/17.
 */
public interface SimpleGlRender {
    void onSurfaceCreated();
    void onSurfaceChanged(int width,int height);
    void onDrawFrame();
}

