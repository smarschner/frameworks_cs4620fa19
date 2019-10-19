#version 150

uniform mat4 worldMat;
uniform mat4 viewProjMat;
uniform mat3 worldMatIT;

in vec3 position;
in vec3 normal;

out vec3 vNormal; // Normal in world space.
out vec4 vPosition; // Position in world space.

void main() {
    vPosition = worldMat * vec4(position, 1.0);
    gl_Position = viewProjMat * worldMat * vec4(position, 1.0);
    vNormal = normalize((worldMatIT * normal).xyz);
}
