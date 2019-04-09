package com.cookoo.opengl.renderer;

import android.opengl.GLSurfaceView;
import com.cookoo.opengl.shader.ShaderUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TestRenderer implements GLSurfaceView.Renderer {

    private int program;
    private int vPosition;
    private int uColor;

//    private int createProgram(String vertexSource,String fragmentSource){
//        int vertexShader = ShaderUtils.compileVertexShader(vertexSource);
//    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
