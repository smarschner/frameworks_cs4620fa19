#version 120
uniform vec4 AxisColor;

varying vec3 fPos;

void main() {
  vec3 vA = abs(fPos);
  float h = max(max(vA.x, vA.y), vA.z);
  
  gl_FragColor = AxisColor * vec4(h, h, h, 1);
}
