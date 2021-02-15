package com.docwei.simpleopengl;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by liwk on 2021/1/31.
 */
public class SimpleGlSurfaceView extends AbsGlSurfaceView{
    public SimpleGlSurfaceView(Context context) {
        this(context,null);
    }

    public SimpleGlSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleGlSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSimpleRender(new SimpleRender());
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }
}
