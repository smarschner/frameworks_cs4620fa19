#version 120

#define M_PI 3.1415926536897932
#define ncellulose 1.55  //refractive index of cellulose
// You May Use The Following Functions As RenderMaterial Input
// vec4 getDiffuseColor(vec2 uv)
// vec4 getNormalColor(vec2 uv)
// vec4 getSpecularColor(vec2 uv)
// vec4 getFiberColorColor(fUV)
// vec4 getFiberDirectionColor(fUV)

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
varying mat3 mTNB; // tangent-normal-binormal frame (local->world)

void main() {
	// Renormalize and orient the coordinate system 
	// to match the wood texture convention
	mat3 tnb = mat3(
	-normalize(mTNB[0]),
	normalize(mTNB[1]),
    -normalize(mTNB[2])
		);

	// Interpolating normals will change the length of the normal (renormalize the normal)
	vec3 N = normalize(fN);
	// Calculate the light vector
	vec3 V = normalize(worldCam - worldPos.xyz);

	// Get pixel data from textures
	vec3 k_d = getDiffuseColor(fUV).xyz;
	vec3 k_s = getSpecularColor(fUV).xyz;
	vec3 k_f = getFiberColorColor(fUV).xyz;

	// Read the fiber vector from the fiber map and convert it to [-1,1] range
	vec3 u_tex = normalize(getFiberDirectionColor(fUV).xyz * 2.0 - vec3(1.0));

	// Convert fiber direction to world space
	vec3 u_world = normalize(tnb * u_tex);
	
	float beta = k_s.x; // highlight width

	// Initialize color accumulator
	vec3 finalColor = vec3(0.0, 0.0, 0.0);

	for (int i = 0; i < numLights; i++) {

    float r = length(lightPosition[i] - worldPos.xyz); // dist to light src
    vec3 L = normalize(lightPosition[i] - worldPos.xyz); // light vector
    vec3 H = normalize(L + V); // half vector
    
	// Compute refracted incident and reflection angles  
    float psi_r = asin(dot(u_world, L) /ncellulose);
    float psi_i = asin(dot(u_world, V) /ncellulose);
    float psi_h = psi_r + psi_i;
    float psi_d = psi_r - psi_i;
      
	// subsurface gaussian component
    float gauss_fnc = 1.0 /(sqrt(2*M_PI)*beta) * exp(-0.5* psi_h/beta * psi_h/beta);

	// Geometric factor
    float cosA = cos(psi_d /2);
    float cos2A = cosA * cosA;
      
    // Add material contributions (diifuse, specular, subsurface, clamp to visible side of surface)
    vec3 I_d = k_d*max(dot(N, L), 0.0);
    vec3 I_s = k_s*max(pow(dot(N, H),shininess), 0.0)*max(sign(dot(N, L)), 0.0);
    vec3 I_f = k_f*gauss_fnc/cos2A * max(sign(dot(N, L)), 0.0);

    finalColor += vec3(lightIntensity[i] * (I_d + I_s + I_f));
	}

	// Set the framebuffer pixel color scaled by the exposure level
	gl_FragColor = vec4(finalColor * exposure, 1.0);
}
