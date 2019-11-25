#version 120

// RenderCamera Input
uniform mat4 mViewProjection;

// RenderObject Input
uniform mat4 mWorld;

// RenderMesh Input
attribute vec4 vPosition; // Sem (POSITION 0)
attribute vec2 vUV; // Sem (TEXCOORD 0)

varying vec2 fUV;
varying vec3 fPos;

void main() {
	fUV = vUV;
  fPos = vPosition.xyz;
	gl_Position = mViewProjection * (mWorld * vPosition);
}