package com.docwei.simpleopengl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLES20;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView surfaceView=findViewById(R.id.surface);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
               new Thread(){
                   @Override
                   public void run() {
                       super.run();
                       EglHelper eglHelper=new EglHelper();
                       eglHelper.initEgl(holder.getSurface(),null);
                       while(true){
                           GLES20.glViewport(0,0,width,height);
                          GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
                           GLES20.glClearColor(0.8f,0.5f,0.2f,0.0f);
                           eglHelper.swapBuffers();
                            Log.e("openGL","onSurfaceView");
                           try {
                               Thread.sleep(16);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }

                       }
                   }
               }.start();
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

            }
        });
    }
}