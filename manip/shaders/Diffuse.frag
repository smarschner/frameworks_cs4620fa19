#version 150
in vec3 vNormal; // Fragment normal in world space.
in vec4 vPosition; // Fragment position in world space.
out vec4 fragColor;

uniform vec3 color; // k_d, the diffuse color.
uniform float mode;

void main() {
    // hard code light position and color
    vec3 lightPos[3];
    lightPos[0] = vec3(12.0, 30.0, -20.0);
    lightPos[1] = vec3(-12.0, -4.0, 10.0);
    lightPos[2] = vec3(14.0, 3.0, -13.0);
    float lightColor[3];
    lightColor[0] = 1.0;
    lightColor[1] = 0.15;
    lightColor[2] = 0.05;
    // hard coding camera
    float exposure = 1200; // Multiply the final color by this.
    
    vec4 finalColor = vec4(0.0, 0.0, 0.0, 0.0);
    for (int i = 0; i < 3; i++) {
        // interpolating normals will change the length of the normal, so renormalize the normal.
        vec3 N = normalize(vNormal);
        float r = length(lightPos[i] - vPosition.xyz);
        vec3 L = normalize(lightPos[i] - vPosition.xyz);
        // calculate diffuse term
        vec4 Idiff = vec4(color, 0.0) * max(dot(N, L), 0.0);
        finalColor += vec4(vec3(lightColor[i]), 0.0) * Idiff / (r*r);
    }
    
    if (mode < 0.9) {
        finalColor = vec4(color, 0.25)/exposure;
    }

    if (mode  == 2){
        finalColor = vec4(0.0, 0.5, 1.0, 1.0);
    }
    fragColor = finalColor * exposure;
}
