package com.docwei.simpleopengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.docwei.simpleopengl.mutiple.MutipleTextureView;

public class EglMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egl);
        SimpleTextureView simpleTextureView = findViewById(R.id.surface);
        LinearLayout ll = findViewById(R.id.lc);
        simpleTextureView.getSimpleTextureRender().setOnTextureIdReadyListener(new SimpleTextureRender.OnTextureIdReadyListener() {
            @Override
            public void success(int textureId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if ( ll.getChildCount() > 0) {
                            ll.removeAllViews();
                        }

                        for (int i = 0; i < 3; i++) {
                           MutipleTextureView mutiSurfaceVeiw = new  MutipleTextureView(EglMainActivity.this);
                            mutiSurfaceVeiw.setTextureId(textureId, i);
                            mutiSurfaceVeiw.setEGLContext(simpleTextureView.getEGLContext());

                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            lp.width = 200;
                            lp.height = 300;
                            mutiSurfaceVeiw.setLayoutParams(lp);

                           ll.addView(mutiSurfaceVeiw);
                        }
                    }
                });
            }

        });
    }
}