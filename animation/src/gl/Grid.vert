#version 120

uniform mat4 VP;

attribute vec4 vPos;

void main() {
  gl_Position = VP * vPos;
}
