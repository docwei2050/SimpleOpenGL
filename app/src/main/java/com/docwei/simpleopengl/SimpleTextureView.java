package com.docwei.simpleopengl;

import android.content.Context;
import android.util.AttributeSet;


/**
 * Created by liwk on 2021/2/15.
 */
public class SimpleTextureView extends AbsGlSurfaceView{
    public SimpleTextureView(Context context) {
        this(context,null);
    }

    public SimpleTextureView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSimpleRender(new SimpleTextureRender(context));
    }
}
