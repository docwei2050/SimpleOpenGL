package com.docwei.simpleopengl;

import java.lang.ref.WeakReference;

import javax.microedition.khronos.egl.EGLContext;

import static com.docwei.simpleopengl.AbsGlSurfaceView.RENDERMODE_CONTINUOUSLY;
import static com.docwei.simpleopengl.AbsGlSurfaceView.RENDERMODE_WHEN_DIRTY;

/**
 * Created by liwk on 2021/1/14.
 */
public class SimpleEGLThread extends Thread {
    public WeakReference<AbsGlSurfaceView> eglSurfaceViewWeakReference;
    public EglHelper eglHelper = null;
    public Object object = null;

    public boolean isExit = false;
    public boolean isCreate = false;
    public boolean isChange = false;
    public boolean isStart = false;

    public int width;
    public int height;

    public SimpleEGLThread (WeakReference<AbsGlSurfaceView> wleglSurfaceViewWeakReference) {
        this.eglSurfaceViewWeakReference = wleglSurfaceViewWeakReference;
    }

    @Override
    public void run() {
        super.run();
        isExit = false;
        isStart = false;
        object = new Object();
        eglHelper = new EglHelper();
        eglHelper.initEgl(eglSurfaceViewWeakReference.get().mSurface, eglSurfaceViewWeakReference.get().mEGLContext);

        while (true) {
            if (isExit) {
                //释放资源
                release();
                break;
            }

            if (isStart) {
                if (eglSurfaceViewWeakReference.get().mRenderMode == RENDERMODE_WHEN_DIRTY) {
                    synchronized (object) {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (eglSurfaceViewWeakReference.get().mRenderMode == RENDERMODE_CONTINUOUSLY) {
                    try {
                        Thread.sleep(1000 / 60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new RuntimeException("mRenderMode is wrong value");
                }
            }


            onCreate();
            onChange(width, height);
            onDraw();

            isStart = true;


        }


    }

    private void onCreate() {
        if (isCreate && eglSurfaceViewWeakReference.get().mSimpleRender != null) {
            isCreate = false;
            eglSurfaceViewWeakReference.get().mSimpleRender.onSurfaceCreated();
        }
    }

    private void onChange(int width, int height) {
        if (isChange && eglSurfaceViewWeakReference.get().mSimpleRender != null) {
            isChange = false;
            eglSurfaceViewWeakReference.get().mSimpleRender.onSurfaceChanged(width, height);
        }
    }

    private void onDraw() {
        if (eglSurfaceViewWeakReference.get().mSimpleRender != null && eglHelper != null) {
            eglSurfaceViewWeakReference.get().mSimpleRender.onDrawFrame();
            if (!isStart) {
                eglSurfaceViewWeakReference.get().mSimpleRender.onDrawFrame();
            }
            eglHelper.swapBuffers();

        }
    }

    public void requestRender() {
        if (object != null) {
            synchronized (object) {
                object.notifyAll();
            }
        }
    }

    public void onDestory() {
        isExit = true;
        requestRender();
    }


    public void release() {
        if (eglHelper != null) {
            eglHelper.destroyEgl();
            eglHelper = null;
            object = null;
            eglSurfaceViewWeakReference = null;
        }
    }

    public EGLContext getEglContext() {
        if (eglHelper != null) {
            return eglHelper.getEglContext();
        }
        return null;
    }

}

