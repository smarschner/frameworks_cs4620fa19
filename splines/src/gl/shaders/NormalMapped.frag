#version 120

// You May Use The Following Functions As RenderMaterial Input
// vec4 getDiffuseColor(vec2 uv)
// vec4 getNormalColor(vec2 uv)
// vec4 getSpecularColor(vec2 uv)

// RenderObject Input
uniform mat3 mWorldIT;

// Lighting Information
const int MAX_LIGHTS = 16;
uniform int numLights;
uniform vec3 lightIntensity[MAX_LIGHTS];
uniform vec3 lightPosition[MAX_LIGHTS];
uniform vec3 ambientLightIntensity;

// Camera Information
uniform vec3 worldCam;
uniform float exposure;

// Shading Information
uniform float shininess;

varying vec2 fUV;
varying vec4 worldPos; // vertex position in world coordinates
varying mat3 mTBN;

void main() {
	mat3 tbn = mat3(
		normalize(mTBN[0]),
		normalize(mTBN[1]),
		normalize(mTBN[2])
		);

	// Read the normal vector from the normal map
	vec3 normFromMap = normalize(getNormalColor(fUV).xyz * 2.0 - vec3(1.0));
	vec3 N = normalize(mTBN * normFromMap);
	//gl_FragColor = vec4((N + vec3(1.0))*0.5, 1.0);
	//return;

	// interpolating vectors will change the length of the view vector, so we should renormalize.
	vec3 V = normalize(worldCam - worldPos.xyz);
	
	vec4 finalColor = vec4(0.0, 0.0, 0.0, 0.0);

	for (int i = 0; i < numLights; i++) {
	  float r = length(lightPosition[i] - worldPos.xyz);
	  vec3 L = normalize(lightPosition[i] - worldPos.xyz); 
	  vec3 H = normalize(L + V);

	  // calculate diffuse term
	  vec4 Idiff = getDiffuseColor(fUV) * max(dot(N, L), 0.0);

	  // calculate specular term
	  vec4 Ispec = getSpecularColor(fUV) * pow(max(dot(N, H), 0.0), shininess);

	  finalColor += vec4(lightIntensity[i], 0.0) * (Idiff + Ispec) / (r*r);
	}
    
	// calculate ambient term
	vec4 Iamb = getDiffuseColor(fUV);
		
    gl_FragColor = (finalColor + vec4(ambientLightIntensity, 0.0) * Iamb) * exposure;
    //gl_FragColor = vec4(tbn[1], 1.0);
}
