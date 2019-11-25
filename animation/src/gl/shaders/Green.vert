#version 410

// RenderMesh Input
in vec4 in_Position;

void main() {
    gl_Position = in_Position;
}