#version 120

uniform mat4 World;
uniform mat4 VP;

attribute vec4 vPos;

varying vec3 fPos;

void main() {
  vec4 wPos = World * vPos;
  gl_Position = VP * wPos;
  
  fPos = vPos.xyz;
}
