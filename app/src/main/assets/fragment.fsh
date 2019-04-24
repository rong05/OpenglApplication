#version 300 es

precision mediump float;
out vec4 fragColor;

void main() {
    fragColor = vec4 ( 1.0, 0.0, 0.0, 1.0 );//表示有4个元素的数组：红色、绿色、蓝色和alpha（透明度）
}