package com.docwei.simpleopengl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

import javax.microedition.khronos.egl.EGLContext;

/**
 * Created by liwk on 2021/1/13.
 */
public abstract class AbsGlSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public Surface mSurface;
    public EGLContext mEGLContext;
    public int mRenderMode = RENDERMODE_CONTINUOUSLY;
    public SimpleEGLThread  mSimpleEGLThread;
    public SimpleGlRender mSimpleRender;

    public final static int RENDERMODE_WHEN_DIRTY = 0;
    public final static int RENDERMODE_CONTINUOUSLY = 1;

    public AbsGlSurfaceView(Context context) {
        this(context, null);
    }

    public AbsGlSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsGlSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    public void setRenderMode(int renderMode) {
        if (mSimpleRender == null) {
            throw new RuntimeException("must set render before");
        }
        mRenderMode = renderMode;
    }

    public void setSimpleRender(SimpleGlRender simpleRender) {
        mSimpleRender = simpleRender;
    }

    public void setSurface(Surface surface) {
        mSurface = surface;
    }

    public void setEGLContext(EGLContext EGLContext) {
        mEGLContext = EGLContext;
    }

    public EGLContext getEGLContext() {
        if (mSimpleEGLThread != null) {
            return mSimpleEGLThread.getEglContext();
        }
        return null;
    }

    public void requestRender() {
        if (mSimpleEGLThread != null) {
            mSimpleEGLThread.requestRender();
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if (mSurface == null) {
            mSurface = holder.getSurface();
        }
        mSimpleEGLThread = new SimpleEGLThread(new WeakReference<AbsGlSurfaceView>(this));
        mSimpleEGLThread.isCreate = true;
        mSimpleEGLThread.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        mSimpleEGLThread.width = width;
        mSimpleEGLThread.height = height;
        mSimpleEGLThread.isChange = true;
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        mSimpleEGLThread.onDestory();
        mSimpleEGLThread = null;
        mSurface = null;
        mEGLContext = null;
    }


}
