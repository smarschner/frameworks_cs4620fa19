#version 120

// You May Use The Following Variables As RenderMaterial Input
// uniform vec4 colDiffuse;
// uniform vec4 colSpecular;

// Lighting Information
const int MAX_LIGHTS = 16;
uniform int numLights;
uniform vec3 lightIntensity[MAX_LIGHTS];
uniform vec3 lightPosition[MAX_LIGHTS];
uniform vec3 ambientLightIntensity;

// Camera Information
uniform vec3 worldCam;

// RenderMaterial Information
uniform float shininess;

varying vec3 fN; // Interpolated normal in world-space coordinates
varying vec4 worldPos; // Interpolated position in world-space coordinates

void main() {
  // TODO#PPA2 SOLUTION START

  // don't forget to renormalize!
  vec3 n = normalize(fN);
  vec3 eye = normalize(worldCam - worldPos.xyz);
  vec3 colorRGB = colDiffuse.xyz * ambientLightIntensity;

  for (int i=0; i<numLights; i++) {
    vec3 l = normalize(lightPosition[i] - worldPos.xyz);
    vec3 h = normalize(l + eye);

    float nDotL = max(dot(n, l), 0.0);
    float nDotH = max(dot(n, h), 0.0);

    colorRGB += lightIntensity[i] * (colDiffuse.xyz * nDotL +
                                     colSpecular.xyz * pow(nDotH, shininess) * step(0.0, nDotL));
  }

  gl_FragColor = vec4(colorRGB, 1.0);

  // SOLUTION END
}

