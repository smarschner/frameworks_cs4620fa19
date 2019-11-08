#version 120

// You May Use The Following Functions As RenderMaterial Input
// vec4 getDiffuseColor(vec2 uv) // samples fire.png
// vec4 getNormalColor(vec2 uv)  // samples noise.png

uniform float time;

const vec3 texture_scales = vec3(1.0, 2.0, 3.0);
const vec3 scroll_speeds = vec3(1.0, 1.0, 1.0);

varying vec2 fUV;
varying vec3 fPos;

void main() {
  // TODO#PPA2 SOLUTION START
	vec2 texCoord1 = vec2(texture_scales.x * fUV.x, texture_scales.x * fUV.y - scroll_speeds.x * time);
	vec2 texCoord2 = vec2(texture_scales.y * fUV.x, texture_scales.y * fUV.y - scroll_speeds.y * time);
	vec2 texCoord3 = vec2(texture_scales.z * fUV.x, texture_scales.z * fUV.y - scroll_speeds.z * time);
	
	vec4 noise1 = getNormalColor(vec2(texCoord1.x - floor(texCoord1.x), texCoord1.y - floor(texCoord1.y)));
	vec4 noise2 = getNormalColor(vec2(texCoord2.x - floor(texCoord2.x), texCoord2.y - floor(texCoord2.y)));
	vec4 noise3 = getNormalColor(vec2(texCoord3.x - floor(texCoord3.x), texCoord3.y - floor(texCoord3.y)));
	
	vec4 noiseSample = (noise1 + noise2 + noise3)/3;
	
	vec4 color = getDiffuseColor(clamp(noiseSample.xy, 0.0, 1.0));
	
	gl_FragColor = (1.0 - fPos.y) * color * length(color.xyz);
  // SOLUTION END
}