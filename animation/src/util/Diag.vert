#version 120

attribute vec4 vPos; // Sem (POSITION 0)
attribute vec2 vUV; // Sem (TEXCOORD 0)

varying vec2 fUV;

void main() {
  gl_Position = vPos;
  fUV = vUV;
}
