#version 300 es

layout(location = 0) in vec4 vPosition;//位置变量的属性值为0

void main() {
    gl_Position = vPosition;//gl_Position设置的值会成为该顶点着色器的输出
}