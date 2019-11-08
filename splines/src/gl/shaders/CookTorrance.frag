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
// 0 : smooth, 1: rough
uniform float roughness;

varying vec2 fUV;
varying vec3 fN; // normal at the vertex
varying vec4 worldPos; // vertex position in world coordinates

void main()
{
    // set important material values
    float F0 = 0.04; // fresnel reflectance at normal incidence
    
	// interpolating normals will change the length of the normal, so renormalize the normal.
	vec3 N = normalize(fN);
	vec3 V = normalize(worldCam - worldPos.xyz);
	
	vec4 finalColor = vec4(0.0, 0.0, 0.0, 0.0);
    
	for (int i = 0; i < numLights; i++) {
		float r = length(lightPosition[i] - worldPos.xyz);
		vec3 L = normalize(lightPosition[i] - worldPos.xyz); 
		float NdotL = max(dot(N, L), 0.0);
		
		// calculate diffuse term
	    vec4 Idiff = getDiffuseColor(fUV) * NdotL;
		
		float specular = 0.0;
		if(NdotL > 0.0)
		{
			// calculate intermediary values
			vec3 H = normalize(L + V);
			float NdotH = dot(N, H); 
			float NdotV = dot(N, V); // note: this could also be NdotL, which is the same value
			float VdotH = dot(V, H);
			float mSquared = roughness * roughness;
		 
			// fresnel
			// Schlick approximation
			float fresnelTerm = F0 + (1.0 - F0)*pow(1.0 - VdotH, 5.0);
			
			// roughness (or: microfacet distribution function)
			// beckmann distribution function
			float r1 = 1.0 / (mSquared * pow(NdotH, 4.0));
			float r2 = (NdotH * NdotH - 1.0) / (mSquared * NdotH * NdotH);
			float roughnessTerm = r1 * exp(r2);
			
			// geometric attenuation
			float NH2 = 2.0 * NdotH;
			float g1 = (NH2 * NdotV) / VdotH;
			float g2 = (NH2 * NdotL) / VdotH;
			float geoAttTerm = min(1.0, min(g1, g2));
			
			specular = (fresnelTerm * roughnessTerm * geoAttTerm) / (NdotV * NdotL * 3.14);
		}
		vec4 Ispec = getSpecularColor(fUV) * NdotL * specular;

		finalColor += vec4(lightIntensity[i], 0.0) * (Idiff + Ispec) / (r*r);
	}
    
	// calculate ambient term
	vec4 Iamb = getDiffuseColor(fUV);
		
    gl_FragColor = (finalColor + vec4(ambientLightIntensity, 0.0) * Iamb) * exposure;
}