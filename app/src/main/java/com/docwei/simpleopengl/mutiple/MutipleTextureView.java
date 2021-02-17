package com.docwei.simpleopengl.mutiple;

import android.content.Context;
import android.util.AttributeSet;

import com.docwei.simpleopengl.AbsGlSurfaceView;
import com.docwei.simpleopengl.SimpleTextureRender;

/**
 * Created by liwk on 2021/2/17.
 */
public class MutipleTextureView extends AbsGlSurfaceView {
    private MutipleTextureRender mSimpleTextureRender;

    public MutipleTextureView(Context context) {
        this(context, null);
    }

    public MutipleTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MutipleTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSimpleTextureRender = new MutipleTextureRender((context));
        setSimpleRender(mSimpleTextureRender);
    }

   public void setTextureId(int textureId,int index){
        mSimpleTextureRender.setTextureid(textureId,index);
   }
}
