#version 120

// Lighting Information
const int MAX_LIGHTS = 16;
uniform vec3 lightPosition[MAX_LIGHTS];

// RenderCamera Input
uniform mat4 mViewProjection;

// RenderObject Input
uniform mat4 mWorld;
uniform mat3 mWorldIT;

// RenderMesh Input
attribute vec4 vPosition; // Sem (POSITION 0)
attribute vec3 vNormal; // Sem (NORMAL 0)

varying vec3 fN; // Normal at the vertex in world-space coordinates
varying vec4 worldPos; // Vertex position in world-space coordinates

const float PI = 3.1415926535;
// The height of the flower in object coordinates
const float height = 3.0;

void main() {
  float L_x = sqrt(lightPosition[0].x * lightPosition[0].x +
                   lightPosition[0].z * lightPosition[0].z);
  float L_y = lightPosition[0].y;

  if (L_x < 0.00001) {
    // If the light is too close to directly above the flower, the math
    // for the bending of the flower becomes unstable, so just render
    // the unbent flower

    // TODO#PPA2 SOLUTION START

    // Calculate Point In World Space
    worldPos = mWorld * vPosition;
    // Calculate Projected Point
    gl_Position = mViewProjection * worldPos;

    // We have to use the inverse transpose of the world transformation matrix for the normal
    fN = normalize(mWorldIT * vNormal);

    // SOLUTION END

  } else {
    // These matrices map between the frame of the flower mesh's vertices and a frame in which
    // the light lies on the z>0 part of the x-y plane
    mat4 frameToObj = mat4(lightPosition[0].x / L_x, 0, lightPosition[0].z / L_x, 0,
                           0,                        1,  0,                        0,
                           -lightPosition[0].z / L_x, 0,  lightPosition[0].x / L_x, 0,
                           0,                        0,  0,                        1);

    // Find inverse of frameToObj
    mat4 objToFrame = transpose(frameToObj);

    // TODO#PPA2 SOLUTION START

    // the angle theta from the diagram in pa2a.pdf
    float theta = atan(L_y / L_x);

    // Calculate the value "R" and its inverse according to the formula
    // for the bending of the flower
    float R = height / (0.5f * PI - theta);
    float Rinv = 1.0f / R;

    // find vertex/normal in axis-aligned frame
    vec3 frameV = (objToFrame * vPosition).xyz;
    vec3 frameN = (objToFrame * vec4(vNormal, 0.0)).xyz;

    // compute commonly-used values
    float cosHeight = cos(Rinv * frameV.y);
    float sinHeight = sin(Rinv * frameV.y);

    // find transformed vertex/normal in local frame
    vec3 bentFrameV = vec3(-(R - frameV.x) * cosHeight + R,
                           (R - frameV.x) * sinHeight,
                           frameV.z);

    vec3 bentFrameN = vec3(cosHeight * frameN.x + sinHeight * frameN.y,
                           cosHeight * frameN.y - sinHeight * frameN.x,
                           frameN.z);

    // map transformed vertex/normal back to object frame...
    vec4 bentObjectV = frameToObj * vec4(bentFrameV, 1.0);
    vec3 bentObjectN = (frameToObj * vec4(bentFrameN, 0.0)).xyz;

    // ... and to eye/screen space
    worldPos = mWorld * bentObjectV;
    gl_Position = mViewProjection * worldPos;
    fN = mWorldIT * bentObjectN;

    // SOLUTION END
  }
}
