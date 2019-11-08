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
attribute vec2 vUV; // Sem (TEXCOORD 0)

// Shading Information
uniform float dispMagnitude;

varying vec2 fUV;
varying vec3 fN; // normal at the vertex
varying vec4 worldPos; // vertex position in world-space coordinates

void main() {
	// Read the height value
	float height = (getNormalColor(vUV).x + getNormalColor(vUV).y + getNormalColor(vUV).z) / 3.0;
	
	// Translate the vertex in the normal's direction
	vec4 newPosition = vPosition + vec4(vNormal, 0.0) * height * dispMagnitude;
	
	// Calculate Point In World Space
	worldPos = mWorld * newPosition;
	// Calculate Projected Point
	gl_Position = mViewProjection * worldPos;

	// We have to use the inverse transpose of the world transformation matrix for the normal
	fN = normalize((mWorldIT * vNormal).xyz);
	fUV = vUV;
}
