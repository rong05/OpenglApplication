package com.cookoo.opengl;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.widget.TextView;
import com.cookoo.opengl.renderer.TestRenderer;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();
    private GLSurfaceView mGlSurfaceView;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkOpenGLES30()) {
            Log.e(TAG, "con't support OpenGL ES 3.0!");
            finish();
        }
        mGlSurfaceView = new GLSurfaceView(this);
        mGlSurfaceView.setEGLContextClientVersion(3);
        mGlSurfaceView.setRenderer(new TestRenderer(this.getApplicationContext()));
        mGlSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(mGlSurfaceView);
        handler = new Handler(MainActivity.this.getMainLooper());
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    String s = null;
                    if( Integer.parseInt(s) == 1){
                    }
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            TextView tv = new TextView(MainActivity.this.getApplicationContext());
//                            setContentView(tv);
//                            String s = "ssdd111";
//                            tv.setText("" + Integer.parseInt(s));
//                        }
//                    }, 1000 * 2);
                }
            }
        }).start();

    }



    private boolean checkOpenGLES30() {
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x30000);
    }

    @Override
    protected void onPause() {
        mGlSurfaceView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mGlSurfaceView.onResume();
        super.onResume();
    }

}
