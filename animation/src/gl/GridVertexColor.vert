#version 120

uniform mat4 VP;

attribute vec4 vPos;
attribute vec3 vColor;

varying vec3 fColor;

void main() {
  fColor = vColor;
  gl_Position = VP * vPos;
}
