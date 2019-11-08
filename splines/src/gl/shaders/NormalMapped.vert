#version 120

// Note: We multiply a vector with a matrix from the left side (M * v)!
// mProj * mView * mWorld * pos

// RenderCamera Input
uniform mat4 mViewProjection;

// RenderObject Input
uniform mat4 mWorld;
uniform mat3 mWorldIT;

// RenderMesh Input
attribute vec4 vPosition; // Sem (POSITION 0)
attribute vec3 vNormal; // Sem (NORMAL 0)
attribute vec3 vTangent; // Sem (TANGENT 0)
attribute vec3 vBitangent; // Sem (BINORMAL 0)
attribute vec2 vUV; // Sem (TEXCOORD 0)

varying vec2 fUV;
varying vec4 worldPos; // vertex position in world-space coordinates
varying mat3 mTBN;

void main() {
	// Calculate Point In World Space
	worldPos = mWorld * vPosition;
	// Calculate Projected Point
	gl_Position = mViewProjection * worldPos;

	mTBN = mat3(
        mWorldIT * vTangent,
        mWorldIT * vBitangent,
        mWorldIT * vNormal
    );
	fUV = vUV;
}