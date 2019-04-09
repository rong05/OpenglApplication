package com.cookoo.opengl.renderer;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.cookoo.opengl.shader.ShaderUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TestRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = TestRenderer.class.getName();
    private int mProgram;
    private int mPositionHandle;
    private Context mContext;


    public TestRenderer(Context context){
        this.mContext = context;
    }

//    private int createProgram(String vertexSource,String fragmentSource){
//        int vertexShader = ShaderUtils.compileVertexShader(vertexSource);
//    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        final String vertexSource = ShaderUtils.loadFromAssets("vertex.vsh",mContext.getResources());
        final String fragmentSource = ShaderUtils.loadFromAssets("fragment.fsh",mContext.getResources());

        this.mProgram = ShaderUtils.createProgram(vertexSource,fragmentSource);
        // vPosition 是在 'vertex.vsh' 文件中定义的
        mPositionHandle = GLES30.glGetAttribLocation(mProgram,"vPosition");
        Log.d(TAG, "mPositionHandle: " + mPositionHandle);
        // 背景颜色设置为黑色 RGBA (range: 0.0 ~ 1.0)
        GLES30.glClearColor(0, 0, 0, 1);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 视距区域设置使用 GLSurfaceView 的宽高
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        final int vertexCount = 3;
        final int[] vertexs = {
                0,1,0,
                -1,-1,0,
                1,-1,0
        };
        ByteBuffer vbb = ByteBuffer.allocate(vertexs.length * 4);//一个byte4个字节
        vbb.order(ByteOrder.nativeOrder());
        IntBuffer vertexBuffer = vbb.asIntBuffer();
        vertexBuffer.put(vertexs);
        vertexBuffer.position(0);
//        Buffer vertexBuffer = vff;

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        GLES30.glUseProgram(mProgram);

        GLES30.glVertexAttribPointer(mPositionHandle, vertexCount, GLES30.GL_FIXED, false, 3 * 4, vertexBuffer);
        GLES30.glEnableVertexAttribArray(mPositionHandle);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,vertexCount);

    }
}
