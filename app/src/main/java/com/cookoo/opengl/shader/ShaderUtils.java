package com.cookoo.opengl.shader;

import android.content.res.Resources;
import android.opengl.GLES30;
import android.opengl.GLES30;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class ShaderUtils {

    private static final String TAG = ShaderUtils.class.getName();
    private static final int GL_ERROR = 0;

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

    public static int loadVertexShader(String shaderCode){
        return loadShader(GLES30.GL_VERTEX_SHADER,shaderCode);
    }

    public static int loadFragmentShader(String shaderCode){
        return loadShader(GLES30.GL_FRAGMENT_SHADER,shaderCode);
    }

    private static int loadShader(int type, String shaderCode) {
        //1.创建一个新的着色器对象
        final int shaderObjectId = GLES30.glCreateShader(type);
        //2.获取创建状态
        if(shaderObjectId == GL_ERROR){
            //在openGL中，都是通过整形值去作为openGL对象的引用。之后进行操作的时候都是将这个整型值传回给openGL进行操作。
            //返回值0代表着创建对象失败。
            Log.e(TAG, "Could not create new shader. type: " + type);
            return GL_ERROR;
        }

        //3.将着色器代码上传到着色器对象中
        GLES30.glShaderSource(shaderObjectId,shaderCode);

        //4.编译着色器对象
        GLES30.glCompileShader(shaderObjectId);

        //5.获取编译状态：opengl将想要获取的值放入长度为1的数组的首位
        final int[] compileStatus = new int[1];
        GLES30.glGetShaderiv(shaderObjectId,GLES30.GL_COMPILE_STATUS,compileStatus,0);

        // 打印编译的着色器信息
        Log.v(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:"
                + GLES30.glGetShaderInfoLog(shaderObjectId));

        //6.验证编译状态
        if(compileStatus[0] == GL_ERROR){
            //如果编译失败，则删除创建的着色器对象
            GLES30.glDeleteShader(shaderObjectId);
            Log.e(TAG, "Compilation of shader failed. type: " + type);
            //7.返回着色器对象：失败，为0
            return GL_ERROR;
        }
        //7.返回着色器对象：成功，非0
        return shaderObjectId;
    }

    public static int createProgram(String vertexSource, String fragmentSource){
        //创建顶点着色器
        final int vertexShader = loadVertexShader(vertexSource);
        if(vertexShader == GL_ERROR){
            Log.e(TAG, "load vertex shader failed! ");
            return GL_ERROR;
        }
        //创建片段着色器
        final int fragmentShader = loadFragmentShader(fragmentSource);
        if(fragmentShader == GL_ERROR){
            Log.e(TAG, "load fragment shader failed! ");
            return GL_ERROR;
        }

        //创建着色器程序
        final int program = GLES30.glCreateProgram();
        if (program == GL_ERROR){
            Log.e(TAG, "create program failed! ");
            return GL_ERROR;
        }

        //将编译的着色器对象附加到着色器程序中
        GLES30.glAttachShader(program,vertexShader);
        GLES30.glAttachShader(program,fragmentShader);

        //删除着色器对象
        GLES30.glDeleteShader(vertexShader);
        GLES30.glDeleteShader(fragmentShader);

        //链接着色器程序
        GLES30.glLinkProgram(program);


        //检查链接状态
        final int[] linkStatus = new int[1];
        GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == GL_ERROR) { // link failed
            Log.e(TAG, "Error link program: ");
            Log.e(TAG, GLES30.glGetProgramInfoLog(program));
            GLES30.glDeleteProgram(program); // delete program
            return GL_ERROR;
        }
        return program;
    }

    public static String loadFromAssets(String fileName, Resources resources) {
        String result = null;
        try {
            InputStream is = resources.getAssets().open(fileName);
            int length = is.available();
            byte[] data = new byte[length];
            is.read(data);
            is.close();
            result = new String(data, "UTF-8");
            result.replace("\\r\\n", "\\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
