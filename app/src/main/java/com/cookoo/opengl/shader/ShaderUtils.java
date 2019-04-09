package com.cookoo.opengl.shader;

import android.opengl.GLES20;
import android.util.Log;

public class ShaderUtils {

    private static final String TAG = ShaderUtils.class.getName();

    private ShaderUtils(){
        throw new IllegalArgumentException("ShaderUtils ,This class does not allow creation of objects, illegal operations");
    }

    /**
     * 顶点着色器
     */
    public static final String VERTEX_SHADER = ""+
            //vec4: 4个分量的向量：x、y、z、w
            "attribute vec4 a_Position;\n"+
            "void main()\n"+
            "{\n"+
            //gl_Position：GL中默认定义的输出变量，决定了当前顶点的最终位置
            "   gl_Position = a_Position;\n"+
            //gl_PointSize：GL中默认定义的输出变量，决定了当前顶点的大小
            "   gl_PointSize = 40.0;\n"+
            "}";

    /**
     * 片段着色器
     */
    public static final String FRAGMENT_SHADER = ""+
            //定义所有浮点数据类型的默认精度；有lowp、mediump、highp 三种，但只有部分硬件支持片段着色器使用highp。（顶点着色器默认highp）
            "prevision mediump float;\n" +
            "uniform mediump vec4 u_Color;\n"+
            "void main()\n"+
            "{\n"+
            //gl_PointSize：GL中默认定义的输出变量，决定了当前片段的最终颜色
            "   gl_PointSize = u_Color;\n"+
            "}";

    public static int compileVertexShader(String shaderCode){
        return compileShader(GLES20.GL_VERTEX_SHADER,shaderCode);
    }

    public static int compileFragmentShader(String shaderCode){
        return compileShader(GLES20.GL_FRAGMENT_SHADER,shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        //1.创建一个新的着色器对象
        final int shaderObjectId = GLES20.glCreateShader(type);
        //2.获取创建状态
        if(shaderObjectId == GLES20.GL_NO_ERROR){
            //在openGL中，都是通过整形值去作为openGL对象的引用。之后进行操作的时候都是将这个整型值传回给openGL进行操作。
            //返回值0代表着创建对象失败。
            Log.w(TAG, "Could not create new shader.");
            return GLES20.GL_NO_ERROR;
        }

        //3.将着色器代码上传到着色器对象中
        GLES20.glShaderSource(shaderObjectId,shaderCode);

        //4.编译着色器对象
        GLES20.glCompileShader(shaderObjectId);

        //5.获取编译状态：opengl将想要获取的值放入长度为1的数组的首位
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId,GLES20.GL_COMPILE_STATUS,compileStatus,0);

        // 打印编译的着色器信息
        Log.v(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:"
                + GLES20.glGetShaderInfoLog(shaderObjectId));

        //6.验证编译状态
        if(compileStatus[0] == GLES20.GL_NO_ERROR){
            //如果编译失败，则删除创建的着色器对象
            GLES20.glDeleteShader(shaderObjectId);
            Log.w(TAG, "Compilation of shader failed.");
            //7.返回着色器对象：失败，为0
            return GLES20.GL_NO_ERROR;
        }
        //7.返回着色器对象：成功，非0
        return shaderObjectId;
    }

//    public static
}
