#version 120

// You May Use The Following Functions As RenderMaterial Input
// vec4 getDiffuseColor(vec2 uv)
// vec4 getNormalColor(vec2 uv)
// vec4 getSpecularColor(vec2 uv)

// Lighting Information
uniform int numLights;
uniform vec3 lightPosition[16];
uniform vec3 lightIntensity[16];

varying vec2 fUV;

void main() {
  gl_FragColor = getDiffuseColor(fUV);
}