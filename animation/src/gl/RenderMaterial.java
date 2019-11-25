package gl;

import java.io.BufferedReader;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL20;

import common.Material;
import common.Material.InputProvider.Type;
import egl.GL;
import egl.GL.TextureTarget;
import egl.GL.TextureUnit;
import egl.GLProgram;
import egl.GLTexture;
import egl.GLUniform;
import egl.IDisposable;
import egl.NativeMem;
import egl.SamplerState;
import egl.ShaderInterface;
import egl.math.Color;
import egl.math.Vector3;
import egl.math.Vector3d;
import egl.math.Vector4;
import ext.java.IOUtils;


public class RenderMaterial implements IDisposable {
	private static interface IProvider {
		void set(GLProgram p);
	}
	private static class ColorProvider implements IProvider {
		String uniformName;
		Vector4 color;

		public ColorProvider(String name, Color c) {
			uniformName = "col" + name;
			color = new Vector4(
					c.r() / 255.0f,
					c.g() / 255.0f,
					c.b() / 255.0f,
					c.a() / 255.0f
					);
		}

		@Override
		public void set(GLProgram p) {
			int uniform = p.getUniform(uniformName);
			if(uniform == GL.BadUniformLocation) return;
			GL20.glUniform4f(uniform, color.x, color.y, color.z, color.w);
		}
	}
	private static class TextureProvider implements IProvider {
		GLTexture t;
		int tUnit, unTextureSampler;

		public TextureProvider(String samplerName, GLProgram p, int unit, String texName, RenderEnvironment env) {
			tUnit = TextureUnit.Texture0 + unit;
			unTextureSampler = p.getUniform("tex" + samplerName);
			t = env.textures.get(texName);
			if(t == null) unTextureSampler = GL.BadUniformLocation;
		}

		@Override
		public void set(GLProgram p) {
			if(unTextureSampler == GL.BadUniformLocation) return;
			t.use(tUnit, unTextureSampler);
			SamplerState.LINEAR_CLAMP.set(TextureTarget.Texture2D);
		}
	}

	private static final String ROOT_DIRECTORY = "gl/shaders/";
	private static final String PROVIDER_FORMAT_TEXTURE = 
			" uniform sampler2D tex%s; " + 
					"vec4 get%sColor(vec2 uv) { " + 
					"  return texture2D(tex%s, uv); " + 
					"} ";
	private static final String PROVIDER_FORMAT_COLOR = 
			" uniform vec4 col%s; " + 
					"vec4 get%sColor(vec2 uv) { " + 
					"  return col%s; " + 
					"} ";
	private static final String PROVIDER_CUBE_MAP = 
			" uniform samplerCube cubeMap; " + 
					"vec4 getEnvironmentColor(vec3 v) { " +
					"	return textureCube(cubeMap, v);" +
					"} ";
	
	private static String getProvider(String provider, String type) {
		return String.format(provider, type, type, type);
	}

	public final GLProgram program = new GLProgram(false);
	public final ShaderInterface shaderInterface = new ShaderInterface(RenderMesh.VERTEX_DECLARATION);
	public final ShaderInterface shaderInterfaceTangentSpace = new ShaderInterface(RenderMesh.VERTEX_DECLARATION_TANGENT_SPACE);
	public final ShaderInterface shaderInterfaceSkinned = new ShaderInterface(RenderMesh.VERTEX_DECLARATION_SKINNED);
	
	private IProvider pDiffuse = null;
	private IProvider pNormal = null;
	private IProvider pSpecular = null;
	private IProvider pFiberColor= null;
	private IProvider pFiberDirection = null;

	public final Material sceneMaterial;
	
	public int
		unWorld, unWorldIT, unWorldBones, unWorldITBones,
		unV, unP, unVP, unWorldCam,
		unLPos, unLIntensity, unLCount,
		unCubeMap, unShininess, unRoughness, unDispMagnitude, unAmbientLIntensity, unExposure,
		unTime;
	private FloatBuffer fbLight = NativeMem.createFloatBuffer(16 * 3);

	public RenderMaterial(Material m) {
		sceneMaterial = m;
	}
	@Override
	public void dispose() {
		program.dispose();
	}
	
	private String readFullResource(String name) {
		BufferedReader reader = IOUtils.openReaderResource(name);
		if(reader == null) return null;
		
		return IOUtils.readFull(reader);
	}
	
	private String addSpecProviders(String code) {
		code = code.replaceFirst("#version 120", "");
		// Cram them into one line so we can get the right line number in case of compile error
		code = getProvider(sceneMaterial.inputSpecular.type == Type.TEXTURE ? PROVIDER_FORMAT_TEXTURE : PROVIDER_FORMAT_COLOR, "Specular") + code;
		code = getProvider(sceneMaterial.inputNormal.type == Type.TEXTURE ? PROVIDER_FORMAT_TEXTURE : PROVIDER_FORMAT_COLOR, "Normal") + code;
		code = getProvider(sceneMaterial.inputDiffuse.type == Type.TEXTURE ? PROVIDER_FORMAT_TEXTURE : PROVIDER_FORMAT_COLOR, "Diffuse") + code;
		code = getProvider(sceneMaterial.inputFiberColor.type == Type.TEXTURE ? PROVIDER_FORMAT_TEXTURE : PROVIDER_FORMAT_COLOR, "FiberColor") + code;
		code = getProvider(sceneMaterial.inputFiberDirection.type == Type.TEXTURE ? PROVIDER_FORMAT_TEXTURE : PROVIDER_FORMAT_COLOR, "FiberDirection") + code;
		code = PROVIDER_CUBE_MAP + code;
		code = "\r\n#version 120\r\n" + code;
		
		return code;
	}
	
	public void loadShaders(RenderEnvironment env) {
		// Read The Vertex Shader Source
		String vsSrc = readFullResource(ROOT_DIRECTORY + sceneMaterial.materialType + ".vert");
		if(vsSrc == null) {
			vsSrc = readFullResource(ROOT_DIRECTORY + Material.T_AMBIENT + ".vert");
			if(vsSrc == null) throw new RuntimeException("Could Not Load A Vertex Shader");
		}

		// Read The Fragment Shader Source
		String fsSrc = readFullResource(ROOT_DIRECTORY + sceneMaterial.materialType + ".frag");
		if(fsSrc == null) {
			fsSrc = readFullResource(ROOT_DIRECTORY + Material.T_AMBIENT + ".frag");
			if(fsSrc == null) throw new RuntimeException("Could Not Load A Fragment Shader");
		}

		// Add In The Special Providers
		vsSrc = this.addSpecProviders(vsSrc);
		fsSrc = this.addSpecProviders(fsSrc);
		
		// Unfortunately some drivers behave differently
		String arrSuffix = "[0]";

		// Create The Program
		program.quickCreateSource(sceneMaterial.materialType, vsSrc, fsSrc, null);
		
		// Create Mappings
		shaderInterface.build(program.semanticLinks);
		shaderInterfaceTangentSpace.build(program.semanticLinks);
		shaderInterfaceSkinned.build(program.semanticLinks);
		System.out.print("Your shader program's registered uniforms: ");
		program.printUniforms();
		
		// Transformation info
		unWorld = program.getUniform("mWorld");
		unWorldIT = program.getUniform("mWorldIT");
		unWorldBones = program.getUniform("mWorldBones");
		unWorldITBones = program.getUniform("mWorldITBones");
		unV = program.getUniform("mView");
		unP = program.getUniform("mProj");
		unVP = program.getUniform("mViewProjection");
		unWorldCam = program.getUniform("worldCam");
		
		// Shading info
		unShininess = program.getUniform("shininess");
		unRoughness = program.getUniform("roughness");
		unDispMagnitude = program.getUniform("dispMagnitude");
		
		// Lighting info
		unLCount = program.getUniform("numLights");
		
		// Try with and without suffix...
		unLPos = program.getUniform("lightPosition");
		if (unLPos == GL.BadUniformLocation) {
			unLPos = program.getUniform("lightPosition" + arrSuffix);
		}
		unLIntensity = program.getUniform("lightIntensity");
		if (unLIntensity == GL.BadUniformLocation) {
			unLIntensity = program.getUniform("lightIntensity" + arrSuffix);
		}
		
		unAmbientLIntensity = program.getUniform("ambientLightIntensity");
		
		// Camera info
		unExposure = program.getUniform("exposure");
				
		// Cube map
		unCubeMap = program.getUniform("cubeMap");
		//TexCubeMap.setupCubeMap(unCubeMap, "data/textures/Envir/");
		env.cubemap.use(TextureUnit.Texture3, unCubeMap);
		
		// Animation Information
		unTime = program.getUniform("time");
		
		createInputProviders(env);
	}

	public void createInputProviders(RenderEnvironment env) {
		if(sceneMaterial.inputDiffuse.type == Type.TEXTURE)
			pDiffuse = new TextureProvider("Diffuse", program, 0, sceneMaterial.inputDiffuse.texture, env);
		else
			pDiffuse = new ColorProvider("Diffuse", sceneMaterial.inputDiffuse.color);
		
		if(sceneMaterial.inputNormal.type == Type.TEXTURE)
			pNormal = new TextureProvider("Normal", program, 1, sceneMaterial.inputNormal.texture, env);
		else
			pNormal = new ColorProvider("Normal", sceneMaterial.inputNormal.color);
		
		if(sceneMaterial.inputSpecular.type == Type.TEXTURE)
			pSpecular = new TextureProvider("Specular", program, 2, sceneMaterial.inputSpecular.texture, env);
		else
			pSpecular = new ColorProvider("Specular", sceneMaterial.inputSpecular.color);
		
		if(sceneMaterial.inputFiberColor.type == Type.TEXTURE)
			pFiberColor = new TextureProvider("FiberColor", program, 3, sceneMaterial.inputFiberColor.texture, env);
		else
			pFiberColor = new ColorProvider("FiberColor", sceneMaterial.inputFiberColor.color);
		
		if(sceneMaterial.inputFiberDirection.type == Type.TEXTURE)
			pFiberDirection = new TextureProvider("FiberDirection", program, 4, sceneMaterial.inputFiberDirection.texture, env);
		else
			pFiberDirection = new ColorProvider("FiberDirection", sceneMaterial.inputFiberDirection.color);
		

	}
	
	public void useMaterialProperties() {
		pDiffuse.set(program);
		pNormal.set(program);
		pSpecular.set(program);
		pFiberColor.set(program);
		pFiberDirection.set(program);
		
		if (unShininess != GL.BadUniformLocation) {
			GL20.glUniform1f(unShininess, sceneMaterial.shininess);
		}
		if (unRoughness != GL.BadUniformLocation) {
			GL20.glUniform1f(unRoughness, sceneMaterial.roughness);
		}
		if (unDispMagnitude != GL.BadUniformLocation) {
			GL20.glUniform1f(unDispMagnitude, sceneMaterial.dispMagnitude);
		}
	}
	
	public void useObject(RenderObject o) {
		if(unWorld != GL.BadUniformLocation) {
			GLUniform.setST(unWorld, o.mWorldTransform, false);
		}
		if(unWorldIT != GL.BadUniformLocation) {
			GLUniform.setST(unWorldIT, o.mWorldTransformIT, false);
		}
	}
	
	public void useCameraAndLights(RenderCamera c, ArrayList<RenderLight> lights, int s, int lightCount) {
		// Use camera
		if(unV != GL.BadUniformLocation) {
			GLUniform.setST(unV, c.mView, false);
		}
		if(unP != GL.BadUniformLocation) {
			GLUniform.setST(unP, c.mProj, false);
		}
		if(unVP != GL.BadUniformLocation) {
			GLUniform.setST(unVP, c.mViewProjection, false);
		}
		if(unExposure != GL.BadUniformLocation) {
			GL20.glUniform1f(unExposure, c.sceneCamera.exposure);
		}
		
		// Use lights
		int nonAmbientLightCount = 0;
		// Default color is black
		Vector3d ambientLightColor = new Vector3d(0.0);
		
		if(unLPos != GL.BadUniformLocation) {
			fbLight.clear();
			Vector3 pos = new Vector3();
			for(int i = 0;i < lightCount;i++) {
				RenderLight rl = lights.get(s + i);
				
				// We skip ambient lights here
				if (!rl.sceneLight.isAmbient) {
					nonAmbientLightCount++;
					
					rl.mWorldTransform.getTrans(pos);
					fbLight.put(pos.x);
					fbLight.put(pos.y);
					fbLight.put(pos.z);
					pos.set(0);
				} else {
					ambientLightColor = rl.sceneLight.intensity;
				}
			}
			fbLight.rewind();
			GL20.glUniform3(unLPos, fbLight);
		}
		
		if(unWorldCam != GL.BadUniformLocation) {
			Vector3 camTrans = new Vector3();
			c.mWorldTransform.getTrans(camTrans);
			GLUniform.set(unWorldCam, camTrans);
		}
		
		if(unLIntensity != GL.BadUniformLocation) {
			fbLight.clear();
			for(int i = 0;i < lightCount;i++) {
				RenderLight rl = lights.get(s + i);
				
				// We skip ambient lights here
				if (!rl.sceneLight.isAmbient) {
					fbLight.put((float)rl.sceneLight.intensity.x);
					fbLight.put((float)rl.sceneLight.intensity.y);
					fbLight.put((float)rl.sceneLight.intensity.z);
				}
			}
			fbLight.rewind();
			GL20.glUniform3(unLIntensity, fbLight);
		}
		if(unLCount != GL.BadUniformLocation) {
			GL20.glUniform1i(unLCount, nonAmbientLightCount);
		}
		if (unAmbientLIntensity != GL.BadUniformLocation) {
			GL20.glUniform3f(unAmbientLIntensity, (float)ambientLightColor.x, (float)ambientLightColor.y, (float)ambientLightColor.z);
		}
	}
	
	public void useTime(float time) {
		if (unTime != GL.BadUniformLocation) {
			GL20.glUniform1f(unTime, time);
		}
	}
}
