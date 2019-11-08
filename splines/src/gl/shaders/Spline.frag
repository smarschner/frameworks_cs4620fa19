#version 120

// You May Use The Following Functions As RenderMaterial Input
// vec4 getDiffuseColor(vec2 uv)
// vec4 getNormalColor(vec2 uv)
// vec4 getSpecularColor(vec2 uv)

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
varying vec3 fN; // normal at the vertex
varying vec4 worldPos; // vertex position in world coordinates

void main() {
	// interpolating normals will change the length of the normal, so renormalize the normal.
	vec3 N = normalize(fN);
	vec3 V = normalize(worldCam-worldPos.xyz);
	
	
	vec4 baseColor;
	
	if(gl_FrontFacing) {
		baseColor = vec4(.6, 0, 0, 1);
	}
	else {
		baseColor = vec4(.2, .2, .2, 1);
		N = -N;
	}

	vec4 finalColor = vec4(0.0, 0.0, 0.0, 0.0);

	for (int i = 0; i < numLights; i++) {
		float r = length(lightPosition[i] - worldPos.xyz);
		vec3 L = normalize(lightPosition[i] - worldPos.xyz); 
		vec3 H = normalize(L + V);
		
		// calculate diffuse term
		vec4 Idiff = baseColor * max(dot(N, L), 0.0);
		
		// calculate specular term
		vec4 Ispec = vec4(1,1,1,1) * pow(max(dot(N, H), 0.0), shininess);
		
		finalColor += vec4(lightIntensity[i], 0.0) * (Idiff + Ispec) / (r*r);
	}

	// calculate ambient term
	gl_FragColor = finalColor  * exposure;
		
		

	
}