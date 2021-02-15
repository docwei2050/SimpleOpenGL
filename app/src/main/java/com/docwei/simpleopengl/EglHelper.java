package com.docwei.simpleopengl;


import android.opengl.EGL14;

import android.view.Surface;



import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;


/**
 * Created by liwk on 2021/1/13.
 * EGL为OpenGL提供绘制表面。或者说EGL是OpenGl ES的渲染画布。EGL作为OpenGL ES与显示设备的桥梁，让OpenGL ES绘制的内容能够在呈现当前设备上。
 * a：Display(EGLDisplay) 是对实际显示设备的抽象。
 *
 * b：Surface（EGLSurface）是对用来存储图像的内存区域FrameBuffer 的抽象，包括 Color Buffer， Stencil Buffer ，Depth Buffer。
 *
 * c：Context (EGLContext) 存储 OpenGL ES绘图的一些状态信息。
 */
public class EglHelper {
    private EGL10 mEgl;
    private EGLDisplay mEGLDisplay;
    private EGLContext mEGLContext;
    private EGLSurface mEGLSurface;

    public void initEgl(Surface surface, EGLContext eglContext) {

        mEgl = (EGL10) EGLContext.getEGL();

        //获取 EGL Display 对象：通过eglGetDisplay()方法来返回EGLDisplay作为OpenGL ES的渲染目标。
        mEGLDisplay = mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (mEGLDisplay == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("eglDisplay failed");
        }

       // 初始化与 EGLDisplay 之间的连接：eglInitialize()。第一参数代表Major版本，第二个代表Minor版本。如果不关心版本号，传0或者null就可以了。
        int[] version = new int[2];
        if (!mEgl.eglInitialize(mEGLDisplay, version)) {
            throw new RuntimeException("eglInitialize failed");
        }

        int[] attrbutes = new int[]{
                EGL10.EGL_RED_SIZE, 8,
                EGL10.EGL_GREEN_SIZE, 8,
                EGL10.EGL_BLUE_SIZE, 8,
                EGL10.EGL_ALPHA_SIZE, 8,
                EGL10.EGL_DEPTH_SIZE, 8,
                EGL10.EGL_STENCIL_SIZE, 8,
                EGL10.EGL_RENDERABLE_TYPE, 4,
                EGL10.EGL_NONE};

        int[] num_config = new int[1];
        if (!mEgl.eglChooseConfig(mEGLDisplay, attrbutes, null, 1, num_config)) {
            throw new IllegalArgumentException("eglChooseConfig failed");
        }


        int numConfig = num_config[0];
        if (numConfig <= 0) {
            throw new IllegalArgumentException("No configs match configSpec");
        }
        //获取 EGLConfig 对象：eglChooseConfig()。
        EGLConfig[] configs = new EGLConfig[numConfig];
        if (!mEgl.eglChooseConfig(mEGLDisplay, attrbutes, configs, numConfig, num_config)) {
            throw new IllegalArgumentException("eglChooseCofig#2 failed");
        }

        int[] attrib_list = {
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 2,
                EGL10.EGL_NONE
        };
        //创建 EGLContext 实例：接下来我们需要创建OpenGL的上下文环境 EGLContext 实例，这里值得留意的是，
        // OpenGL的任何一条指令都是必须在自己的OpenGL上下文环境中运行
        if (eglContext != null) {
            mEGLContext = mEgl.eglCreateContext(mEGLDisplay, configs[0], eglContext, attrib_list);
        } else {
            mEGLContext = mEgl.eglCreateContext(mEGLDisplay, configs[0], EGL10.EGL_NO_CONTEXT, attrib_list);
        }
        //创建 EGLSurface 实例：通过eglCreateWindowSurface()方法创建一个实际可以显示的EGLSurface。
        mEGLSurface = mEgl.eglCreateWindowSurface(mEGLDisplay, configs[0], surface, null);

        //通过 eglMakeCurrent()方法来绑定该线程的显示设备及上下文
        if (!mEgl.eglMakeCurrent(mEGLDisplay, mEGLSurface, mEGLSurface, mEGLContext)) {
            throw new RuntimeException("eglMakeCurrent fail");
        }


    }
    //双缓冲
    public boolean swapBuffers() {
        if (mEgl != null) {
            return mEgl.eglSwapBuffers(mEGLDisplay, mEGLSurface);
        } else {
            throw new RuntimeException("egl is null");
        }
    }

    public EGLContext getEglContext() {
        return mEGLContext;
    }

    public void destroyEgl() {
        if (mEgl != null) {
            mEgl.eglMakeCurrent(mEGLDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        }

        mEgl.eglDestroySurface(mEGLDisplay, mEGLSurface);
        mEGLSurface = null;
        //断开并释放与 EGLSurface 关联的 EGLContext 对象
        mEgl.eglDestroyContext(mEGLDisplay, mEGLContext);
        mEgl.eglTerminate(mEGLDisplay);
        mEGLDisplay = null;
        mEgl = null;
    }


}
