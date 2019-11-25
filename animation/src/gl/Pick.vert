#version 120

uniform mat4 World;
uniform mat4 VP;

attribute vec4 vPos; // Sem (POSITION 0)

void main() {
  vec4 worldPos = World * vPos;
  gl_Position = VP * worldPos;
}