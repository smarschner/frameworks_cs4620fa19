package egl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import org.lwjgl.opengl.*;

/**
 * MEGA-ENUM Hierarchy For OpenGL Enums (Most Of The Important Ones)
 * @author Cristian
 *
 */
public class GL {
	/**
	 * OpenGL Value For A Bad Uniform Location
	 */
	public static final int BadUniformLocation = -1;
	/**
	 * OpenGL Value For A Bad Attribute Location
	 */
	public static final int BadAttributeLocation = -1;
	
	/**
	 * Enum For Classifying OpenGL Buffer Objects
	 * @author Cristian
	 *
	 */
	public static class BufferTarget {
		public static final int ArrayBuffer = GL_ARRAY_BUFFER;
		public static final int AtomicCounterBuffer = GL_ATOMIC_COUNTER_BUFFER;
		public static final int CopyReadBuffer = GL_COPY_READ_BUFFER;
		public static final int CopyWriteBuffer = GL_COPY_WRITE_BUFFER;
		public static final int DispatchIndirectBuffer = GL_DISPATCH_INDIRECT_BUFFER;
		public static final int DrawIndirectBuffer = GL_DRAW_INDIRECT_BUFFER;
		public static final int ElementArrayBuffer = GL_ELEMENT_ARRAY_BUFFER;
		public static final int PixelPackBuffer = GL_PIXEL_PACK_BUFFER;
		public static final int PixelUnpackBuffer = GL_PIXEL_UNPACK_BUFFER;
		public static final int QueryBuffer = GL_QUERY_BUFFER;
		public static final int ShaderStorageBuffer = GL_SHADER_STORAGE_BUFFER;
		public static final int TextureBuffer = GL31.GL_TEXTURE_BUFFER;
		public static final int TextureBuffer43 = GL43.GL_TEXTURE_BUFFER;
		public static final int TransformFeedbackBuffer = GL_TRANSFORM_FEEDBACK_BUFFER;
		public static final int UniformBuffer = GL_UNIFORM_BUFFER;
	}
	/**
	 * Enum For Hinting At OpenGL Where To Place Data
	 * @author Cristian
	 *
	 */
	public static class BufferUsageHint {
		public static final int DynamicCopy = GL_DYNAMIC_COPY;
		public static final int DynamicDraw = GL_DYNAMIC_DRAW;
		public static final int DynamicRead = GL_DYNAMIC_READ;
		public static final int StaticCopy = GL_STATIC_COPY;
		public static final int StaticDraw = GL_STATIC_DRAW;
		public static final int StaticRead = GL_STATIC_READ;
		public static final int StreamCopy = GL_STREAM_COPY;
		public static final int StreamDraw = GL_STREAM_DRAW;
		public static final int StreamRead = GL_STREAM_READ;
	}
	/**
	 * Enum For Various OpenGL Primitive Types
	 * @author Cristian
	 *
	 */
	public static class GLType {
		public static final int Byte = GL_BYTE;
		public static final int Double = GL_DOUBLE;
		public static final int Fixed = GL_FIXED;
		public static final int Float = GL_FLOAT;
		public static final int HalfFloat = GL_HALF_FLOAT;
		public static final int Int = GL_INT;
		public static final int Int2101010Rev = GL_INT_2_10_10_10_REV;
		public static final int Short = GL_SHORT;
		public static final int UnsignedByte = GL_UNSIGNED_BYTE;
		public static final int UnsignedInt = GL_UNSIGNED_INT;
		public static final int UnsignedInt2101010Rev = GL_UNSIGNED_INT_2_10_10_10_REV;
		public static final int UnsignedShort = GL_UNSIGNED_SHORT;
	}
	/**
	 * Enum For Classifying OpenGL Texture Objects
	 * @author Cristian
	 *
	 */
	public static class TextureTarget {
		public static final int ProxyTexture1D = GL_PROXY_TEXTURE_1D;
		public static final int ProxyTexture1DArray = GL_PROXY_TEXTURE_1D_ARRAY;
		public static final int ProxyTexture2D = GL_PROXY_TEXTURE_2D;
		public static final int ProxyTexture2DArray = GL_PROXY_TEXTURE_2D_ARRAY;
		public static final int ProxyTexture2DMultisample = GL_PROXY_TEXTURE_2D_MULTISAMPLE;
		public static final int ProxyTexture2DMultisampleArray = GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY;
		public static final int ProxyTexture3D = GL_PROXY_TEXTURE_3D;
		public static final int ProxyTextureCubeMap = GL_PROXY_TEXTURE_CUBE_MAP;
		public static final int ProxyTextureCubeMapArray = GL_PROXY_TEXTURE_CUBE_MAP_ARRAY;
		public static final int ProxyTextureRectangle = GL_PROXY_TEXTURE_RECTANGLE;
		public static final int Texture1D = GL11.GL_TEXTURE_1D;
		public static final int Texture1D43 = GL43.GL_TEXTURE_1D;
		public static final int Texture1DArray = GL30.GL_TEXTURE_1D_ARRAY;
		public static final int Texture1DArray43 = GL43.GL_TEXTURE_1D_ARRAY;
		public static final int Texture2D = GL11.GL_TEXTURE_2D;
		public static final int Texture2D43 = GL43.GL_TEXTURE_2D;
		public static final int Texture2DArray = GL30.GL_TEXTURE_2D_ARRAY;
		public static final int Texture2DArray43 = GL43.GL_TEXTURE_2D_ARRAY;
		public static final int Texture2DMultisample = GL32.GL_TEXTURE_2D_MULTISAMPLE;
		public static final int Texture2DMultisample43 = GL43.GL_TEXTURE_2D_MULTISAMPLE;
		public static final int Texture2DMultisampleArray = GL32.GL_TEXTURE_2D_MULTISAMPLE_ARRAY;
		public static final int Texture2DMultisampleArray43 = GL43.GL_TEXTURE_2D_MULTISAMPLE_ARRAY;
		public static final int Texture3D = GL12.GL_TEXTURE_3D;
		public static final int Texture3D43 = GL43.GL_TEXTURE_3D;
		public static final int TextureBaseLevel = GL_TEXTURE_BASE_LEVEL;
		public static final int TextureBindingCubeMap = GL_TEXTURE_BINDING_CUBE_MAP;
		public static final int TextureBuffer = GL31.GL_TEXTURE_BUFFER;
		public static final int TextureBuffer43 = GL43.GL_TEXTURE_BUFFER;
		public static final int TextureCubeMap = GL13.GL_TEXTURE_CUBE_MAP;
		public static final int TextureCubeMap43 = GL43.GL_TEXTURE_CUBE_MAP;
		public static final int TextureCubeMapArray = GL40.GL_TEXTURE_CUBE_MAP_ARRAY;
		public static final int TextureCubeMapArray43 = GL43.GL_TEXTURE_CUBE_MAP_ARRAY;
		public static final int TextureCubeMapNegativeX = GL_TEXTURE_CUBE_MAP_NEGATIVE_X;
		public static final int TextureCubeMapNegativeY = GL_TEXTURE_CUBE_MAP_NEGATIVE_Y;
		public static final int TextureCubeMapNegativeZ = GL_TEXTURE_CUBE_MAP_NEGATIVE_Z;
		public static final int TextureCubeMapPositiveX = GL_TEXTURE_CUBE_MAP_POSITIVE_X;
		public static final int TextureCubeMapPositiveY = GL_TEXTURE_CUBE_MAP_POSITIVE_Y;
		public static final int TextureCubeMapPositiveZ = GL_TEXTURE_CUBE_MAP_POSITIVE_Z;
		public static final int TextureMaxLevel = GL_TEXTURE_MAX_LEVEL;
		public static final int TextureMaxLod = GL_TEXTURE_MAX_LOD;
		public static final int TextureMinLod = GL_TEXTURE_MIN_LOD;
		public static final int TextureRectangle = GL31.GL_TEXTURE_RECTANGLE;
		public static final int TextureRectangle43 = GL43.GL_TEXTURE_RECTANGLE;
	}
	/**
	 * Enum For Internal Pixel Representation Of OpenGL Texture Objects
	 * @author Cristian
	 *
	 */
	public static class PixelInternalFormat {
		public static final int Alpha = GL_ALPHA;
		public static final int CompressedAlpha = GL_COMPRESSED_ALPHA;
		public static final int CompressedIntensity = GL_COMPRESSED_INTENSITY;
		public static final int CompressedLuminance = GL_COMPRESSED_LUMINANCE;
		public static final int CompressedLuminanceAlpha = GL_COMPRESSED_LUMINANCE_ALPHA;
		public static final int CompressedRed = GL_COMPRESSED_RED;
		public static final int CompressedRedRgtc1 = GL_COMPRESSED_RED_RGTC1;
		public static final int CompressedRg = GL_COMPRESSED_RG;
		public static final int CompressedRgb = GL_COMPRESSED_RGB;
		public static final int CompressedRgba = GL_COMPRESSED_RGBA;
		public static final int CompressedRgbaBptcUnorm = GL_COMPRESSED_RGBA_BPTC_UNORM;
		public static final int CompressedRgbBptcSignedFloat = GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT;
		public static final int CompressedRgbBptcUnsignedFloat = GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT;
		public static final int CompressedRgRgtc2 = GL_COMPRESSED_RG_RGTC2;
		public static final int CompressedSignedRedRgtc1 = GL_COMPRESSED_SIGNED_RED_RGTC1;
		public static final int CompressedSignedRgRgtc2 = GL_COMPRESSED_SIGNED_RG_RGTC2;
		public static final int CompressedSluminance = GL_COMPRESSED_SLUMINANCE;
		public static final int CompressedSluminanceAlpha = GL_COMPRESSED_SLUMINANCE_ALPHA;
		public static final int CompressedSrgb = GL_COMPRESSED_SRGB;
		public static final int CompressedSrgbAlpha = GL_COMPRESSED_SRGB_ALPHA;
		public static final int Depth24Stencil8 = GL_DEPTH24_STENCIL8;
		public static final int Depth32fStencil8 = GL_DEPTH32F_STENCIL8;
		public static final int DepthComponent = GL_DEPTH_COMPONENT;
		public static final int DepthComponent16 = GL_DEPTH_COMPONENT16;
		public static final int DepthComponent24 = GL_DEPTH_COMPONENT24;
		public static final int DepthComponent32 = GL_DEPTH_COMPONENT32;
		public static final int DepthComponent32f = GL_DEPTH_COMPONENT32F;
		public static final int DepthStencil = GL_DEPTH_STENCIL;
		public static final int Float32UnsignedInt248Rev = GL_FLOAT_32_UNSIGNED_INT_24_8_REV;
		public static final int Four = 4;
		public static final int Luminance = GL_LUMINANCE;
		public static final int LuminanceAlpha = GL_LUMINANCE_ALPHA;
		public static final int One = GL_ONE;
		public static final int R11fG11fB10f = GL_R11F_G11F_B10F;
		public static final int R16 = GL_R16;
		public static final int R16f = GL_R16F;
		public static final int R16i = GL_R16I;
		public static final int R16Snorm = GL_R16_SNORM;
		public static final int R16ui = GL_R16UI;
		public static final int R32f = GL_R32F;
		public static final int R32i = GL_R32I;
		public static final int R32ui = GL_R32UI;
		public static final int R3G3B2 = GL_R3_G3_B2;
		public static final int R8 = GL_R8;
		public static final int R8i = GL_R8I;
		public static final int R8Snorm = GL_R8_SNORM;
		public static final int R8ui = GL_R8UI;
		public static final int Rg16 = GL_RG16;
		public static final int Rg16f = GL_RG16F;
		public static final int Rg16i = GL_RG16I;
		public static final int Rg16Snorm = GL_RG16_SNORM;
		public static final int Rg16ui = GL_RG16UI;
		public static final int Rg32f = GL_RG32F;
		public static final int Rg32i = GL_RG32I;
		public static final int Rg32ui = GL_RG32UI;
		public static final int Rg8 = GL_RG8;
		public static final int Rg8i = GL_RG8I;
		public static final int Rg8Snorm = GL_RG8_SNORM;
		public static final int Rg8ui = GL_RG8UI;
		public static final int Rgb = GL_RGB;
		public static final int Rgb10 = GL_RGB10;
		public static final int Rgb10A2 = GL_RGB10_A2;
		public static final int Rgb10A2ui = GL_RGB10_A2UI;
		public static final int Rgb12 = GL_RGB12;
		public static final int Rgb16 = GL_RGB16;
		public static final int Rgb16f = GL_RGB16F;
		public static final int Rgb16i = GL_RGB16I;
		public static final int Rgb16Snorm = GL_RGB16_SNORM;
		public static final int Rgb16ui = GL_RGB16UI;
		public static final int Rgb32f = GL_RGB32F;
		public static final int Rgb32i = GL_RGB32I;
		public static final int Rgb32ui = GL_RGB32UI;
		public static final int Rgb4 = GL_RGB4;
		public static final int Rgb5 = GL_RGB5;
		public static final int Rgb5A1 = GL_RGB5_A1;
		public static final int Rgb8 = GL_RGB8;
		public static final int Rgb8i = GL_RGB8I;
		public static final int Rgb8Snorm = GL_RGB8_SNORM;
		public static final int Rgb8ui = GL_RGB8UI;
		public static final int Rgb9E5 = GL_RGB9_E5;
		public static final int Rgba = GL_RGBA;
		public static final int Rgba12 = GL_RGBA12;
		public static final int Rgba16 = GL_RGBA16;
		public static final int Rgba16f = GL_RGBA16F;
		public static final int Rgba16i = GL_RGBA16I;
		public static final int Rgba16Snorm = GL_RGBA16_SNORM;
		public static final int Rgba16ui = GL_RGBA16UI;
		public static final int Rgba2 = GL_RGBA2;
		public static final int Rgba32f = GL_RGBA32F;
		public static final int Rgba32i = GL_RGBA32I;
		public static final int Rgba32ui = GL_RGBA32UI;
		public static final int Rgba4 = GL_RGBA4;
		public static final int Rgba8 = GL_RGBA8;
		public static final int Rgba8i = GL_RGBA8I;
		public static final int Rgba8Snorm = GL_RGBA8_SNORM;
		public static final int Rgba8ui = GL_RGBA8UI;
		public static final int Sluminance = GL_SLUMINANCE;
		public static final int Sluminance8 = GL_SLUMINANCE8;
		public static final int Sluminance8Alpha8 = GL_SLUMINANCE8_ALPHA8;
		public static final int SluminanceAlpha = GL_SLUMINANCE_ALPHA;
		public static final int Srgb = GL_SRGB;
		public static final int Srgb8 = GL_SRGB8;
		public static final int Srgb8Alpha8 = GL_SRGB8_ALPHA8;
		public static final int SrgbAlpha = GL_SRGB_ALPHA;
		public static final int Three = 3;
		public static final int Two = 2;
	}
	/**
	 * Enum For Pixel Representation Of External Texture Memory
	 * @author Cristian
	 *
	 */
	public static class PixelFormat {
		public static final int Alpha = GL_ALPHA;
		public static final int AlphaInteger = GL_ALPHA_INTEGER;
		public static final int Bgr = GL_BGR;
		public static final int Bgra = GL_BGRA;
		public static final int BgraInteger = GL_BGRA_INTEGER;
		public static final int BgrInteger = GL_BGR_INTEGER;
		public static final int Blue = GL_BLUE;
		public static final int BlueInteger = GL_BLUE_INTEGER;
		public static final int ColorIndex = GL_COLOR_INDEX;
		public static final int DepthComponent = GL_DEPTH_COMPONENT;
		public static final int DepthStencil = GL_DEPTH_STENCIL;
		public static final int Green = GL_GREEN;
		public static final int GreenInteger = GL_GREEN_INTEGER;
		public static final int Luminance = GL_LUMINANCE;
		public static final int LuminanceAlpha = GL_LUMINANCE_ALPHA;
		public static final int Red = GL_RED;
		public static final int RedInteger = GL_RED_INTEGER;
		public static final int Rg = GL_RG;
		public static final int Rgb = GL_RGB;
		public static final int Rgba = GL_RGBA;
		public static final int RgbaInteger = GL_RGBA_INTEGER;
		public static final int RgbInteger = GL_RGB_INTEGER;
		public static final int RgInteger = GL_RG_INTEGER;
		public static final int StencilIndex = GL_STENCIL_INDEX;
		public static final int UnsignedInt = GL_UNSIGNED_INT;
		public static final int UnsignedShort = GL_UNSIGNED_SHORT;
	}
	/**
	 * Enum For Representing Pixel Component Types
	 * @author Cristian
	 *
	 */
	public static class PixelType {
		public static final int Byte = GL_BYTE;
		public static final int Float = GL_FLOAT;
		public static final int Float32UnsignedInt248Rev = GL_FLOAT_32_UNSIGNED_INT_24_8_REV;
		public static final int HalfFloat = GL_HALF_FLOAT;
		public static final int Int = GL_INT;
		public static final int Short = GL_SHORT;
		public static final int UnsignedByte = GL_UNSIGNED_BYTE;
		public static final int UnsignedByte332 = GL_UNSIGNED_BYTE_3_3_2;
		public static final int UnsignedInt = GL_UNSIGNED_INT;
		public static final int UnsignedInt1010102 = GL_UNSIGNED_INT_10_10_10_2;
		public static final int UnsignedInt248 = GL_UNSIGNED_INT_24_8;
		public static final int UnsignedInt8888 = GL_UNSIGNED_INT_8_8_8_8;
		public static final int UnsignedShort = GL_UNSIGNED_SHORT;
		public static final int UnsignedShort4444 = GL_UNSIGNED_SHORT_4_4_4_4;
		public static final int UnsignedShort5551 = GL_UNSIGNED_SHORT_5_5_5_1;
		public static final int UnsignedShort565 = GL_UNSIGNED_SHORT_5_6_5;
	}
	/**
	 * Enum For Pointing OpenGL Texture Objects Towards A GPU Texture Unit
	 * @author Cristian
	 *
	 */
	public static class TextureUnit {
		public static final int Texture0 = GL_TEXTURE0;
		public static final int Texture1 = GL_TEXTURE1;
		public static final int Texture2 = GL_TEXTURE2;
		public static final int Texture3 = GL_TEXTURE3;
		public static final int Texture4 = GL_TEXTURE4;
		public static final int Texture5 = GL_TEXTURE5;
		public static final int Texture6 = GL_TEXTURE6;
		public static final int Texture7 = GL_TEXTURE7;
		public static final int Texture8 = GL_TEXTURE8;
		public static final int Texture9 = GL_TEXTURE9;
		public static final int Texture10 = GL_TEXTURE10;
		public static final int Texture11 = GL_TEXTURE11;
		public static final int Texture12 = GL_TEXTURE12;
		public static final int Texture13 = GL_TEXTURE13;
		public static final int Texture14 = GL_TEXTURE14;
		public static final int Texture15 = GL_TEXTURE15;
		public static final int Texture16 = GL_TEXTURE16;
		public static final int Texture17 = GL_TEXTURE17;
		public static final int Texture18 = GL_TEXTURE18;
		public static final int Texture19 = GL_TEXTURE19;
		public static final int Texture20 = GL_TEXTURE20;
		public static final int Texture21 = GL_TEXTURE21;
		public static final int Texture22 = GL_TEXTURE22;
		public static final int Texture23 = GL_TEXTURE23;
		public static final int Texture24 = GL_TEXTURE24;
		public static final int Texture25 = GL_TEXTURE25;
		public static final int Texture26 = GL_TEXTURE26;
		public static final int Texture27 = GL_TEXTURE27;
		public static final int Texture28 = GL_TEXTURE28;
		public static final int Texture29 = GL_TEXTURE29;
		public static final int Texture30 = GL_TEXTURE30;
		public static final int Texture31 = GL_TEXTURE31;
	}
	/**
	 * Enum For Specifying Properties Of An OpenGL Texture Object
	 * @author Cristian
	 *
	 */
	public static class TextureParameterName {
		public static final int ClampToBorder = GL_CLAMP_TO_BORDER;
		public static final int ClampToEdge = GL_CLAMP_TO_EDGE;
		public static final int DepthTextureMode = GL_DEPTH_TEXTURE_MODE;
		public static final int GenerateMipmap = GL_GENERATE_MIPMAP;
		public static final int TextureBaseLevel = GL_TEXTURE_BASE_LEVEL;
		public static final int TextureBorderColor = GL_TEXTURE_BORDER_COLOR;
		public static final int TextureCompareFunc = GL_TEXTURE_COMPARE_FUNC;
		public static final int TextureCompareMode = GL_TEXTURE_COMPARE_MODE;
		public static final int TextureDepth = GL_TEXTURE_DEPTH;
		public static final int TextureLodBias = GL_TEXTURE_LOD_BIAS;
		public static final int TextureMagFilter = GL_TEXTURE_MAG_FILTER;
		public static final int TextureMaxLevel = GL_TEXTURE_MAX_LEVEL;
		public static final int TextureMaxLod = GL_TEXTURE_MAX_LOD;
		public static final int TextureMinFilter = GL_TEXTURE_MIN_FILTER;
		public static final int TextureMinLod = GL_TEXTURE_MIN_LOD;
		public static final int TexturePriority = GL_TEXTURE_PRIORITY;
		public static final int TextureSwizzleA = GL_TEXTURE_SWIZZLE_A;
		public static final int TextureSwizzleB = GL_TEXTURE_SWIZZLE_B;
		public static final int TextureSwizzleG = GL_TEXTURE_SWIZZLE_G;
		public static final int TextureSwizzleR = GL_TEXTURE_SWIZZLE_R;
		public static final int TextureSwizzleRgba = GL_TEXTURE_SWIZZLE_RGBA;
		public static final int TextureWrapR = GL_TEXTURE_WRAP_R;
		public static final int TextureWrapS = GL_TEXTURE_WRAP_S;
		public static final int TextureWrapT = GL_TEXTURE_WRAP_T;
	}
	/**
	 * Enum For Classifying OpenGL Shader Objects
	 * @author Cristian
	 *
	 */
	public static class ShaderType {
		public static final int ComputeShader = GL_COMPUTE_SHADER;
		public static final int FragmentShader = GL_FRAGMENT_SHADER;
		public static final int GeometryShader = GL_GEOMETRY_SHADER;
		public static final int TessControlShader = GL_TESS_CONTROL_SHADER;
		public static final int TessEvaluationShader = GL_TESS_EVALUATION_SHADER;
		public static final int VertexShader = GL_VERTEX_SHADER;
	}
	/**
	 * Enum For Retrieving Properties Of OpenGL Shader Objects
	 * @author Cristian
	 *
	 */
	public static class ShaderParameter {
		public static final int CompileStatus = GL_COMPILE_STATUS;
		public static final int DeleteStatus = GL_DELETE_STATUS;
		public static final int InfoLogLength = GL_INFO_LOG_LENGTH;
		public static final int ShaderSourceLength = GL_SHADER_SOURCE_LENGTH;
		public static final int ShaderType = GL_SHADER_TYPE;
	}
	/**
	 * Enum For Retrieving Properties Of OpenGL Program Objects
	 * @author Cristian
	 *
	 */
	public static class GetProgramParameterName {
		public static final int ActiveAtomicCounterBuffers = GL_ACTIVE_ATOMIC_COUNTER_BUFFERS;
		public static final int ActiveAttributeMaxLength = GL_ACTIVE_ATTRIBUTE_MAX_LENGTH;
		public static final int ActiveAttributes = GL_ACTIVE_ATTRIBUTES;
		public static final int ActiveUniformBlockMaxNameLength = GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH;
		public static final int ActiveUniformBlocks = GL_ACTIVE_UNIFORM_BLOCKS;
		public static final int ActiveUniformMaxLength = GL_ACTIVE_UNIFORM_MAX_LENGTH;
		public static final int ActiveUniforms = GL_ACTIVE_UNIFORMS;
		public static final int AttachedShaders = GL_ATTACHED_SHADERS;
		public static final int DeleteStatus = GL_DELETE_STATUS;
		public static final int GeometryInputType = GL_GEOMETRY_INPUT_TYPE;
		public static final int GeometryOutputType = GL_GEOMETRY_OUTPUT_TYPE;
		public static final int GeometryShaderInvocations = GL_GEOMETRY_SHADER_INVOCATIONS;
		public static final int GeometryVerticesOut = GL_GEOMETRY_VERTICES_OUT;
		public static final int InfoLogLength = GL_INFO_LOG_LENGTH;
		public static final int LinkStatus = GL_LINK_STATUS;
		public static final int MaxComputeWorkGroupSize = GL_MAX_COMPUTE_WORK_GROUP_SIZE;
		public static final int ProgramBinaryRetrievableHint = GL_PROGRAM_BINARY_RETRIEVABLE_HINT;
		public static final int ProgramSeparable = GL_PROGRAM_SEPARABLE;
		public static final int TessControlOutputVertices = GL_TESS_CONTROL_OUTPUT_VERTICES;
		public static final int TessGenMode = GL_TESS_GEN_MODE;
		public static final int TessGenPointMode = GL_TESS_GEN_POINT_MODE;
		public static final int TessGenSpacing = GL_TESS_GEN_SPACING;
		public static final int TessGenVertexOrder = GL_TESS_GEN_VERTEX_ORDER;
		public static final int TransformFeedbackBufferMode = GL_TRANSFORM_FEEDBACK_BUFFER_MODE;
		public static final int TransformFeedbackVaryingMaxLength = GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH;
		public static final int TransformFeedbackVaryings = GL_TRANSFORM_FEEDBACK_VARYINGS;
		public static final int ValidateStatus = GL_VALIDATE_STATUS;
	}
	/**
	 * Enum For Classifying OpenGL Framebuffer Objects
	 * @author Cristian
	 *
	 */
	public static class FramebufferTarget {
		public static final int DrawFramebuffer = GL_DRAW_FRAMEBUFFER;
		public static final int Framebuffer = GL_FRAMEBUFFER;
		public static final int ReadFramebuffer = GL_READ_FRAMEBUFFER;
	}
	/**
	 * Enum For Describing Texture-To-Framebuffer Attachments
	 * @author Cristian
	 *
	 */
	public static class FramebufferAttachment {
		public static final int Aux0 = GL_AUX0;
		public static final int Aux1 = GL_AUX1;
		public static final int Aux2 = GL_AUX2;
		public static final int Aux3 = GL_AUX3;
		public static final int BackLeft = GL_BACK_LEFT;
		public static final int BackRight = GL_BACK_RIGHT;
		public static final int Color = GL_COLOR;
		public static final int ColorAttachment0 = GL_COLOR_ATTACHMENT0;
		public static final int ColorAttachment1 = GL_COLOR_ATTACHMENT1;
		public static final int ColorAttachment10 = GL_COLOR_ATTACHMENT10;
		public static final int ColorAttachment11 = GL_COLOR_ATTACHMENT11;
		public static final int ColorAttachment12 = GL_COLOR_ATTACHMENT12;
		public static final int ColorAttachment13 = GL_COLOR_ATTACHMENT13;
		public static final int ColorAttachment14 = GL_COLOR_ATTACHMENT14;
		public static final int ColorAttachment15 = GL_COLOR_ATTACHMENT15;
		public static final int ColorAttachment2 = GL_COLOR_ATTACHMENT2;
		public static final int ColorAttachment3 = GL_COLOR_ATTACHMENT3;
		public static final int ColorAttachment4 = GL_COLOR_ATTACHMENT4;
		public static final int ColorAttachment5 = GL_COLOR_ATTACHMENT5;
		public static final int ColorAttachment6 = GL_COLOR_ATTACHMENT6;
		public static final int ColorAttachment7 = GL_COLOR_ATTACHMENT7;
		public static final int ColorAttachment8 = GL_COLOR_ATTACHMENT8;
		public static final int ColorAttachment9 = GL_COLOR_ATTACHMENT9;
		public static final int Depth = GL_DEPTH;
		public static final int DepthAttachment = GL_DEPTH_ATTACHMENT;
		public static final int DepthStencilAttachment = GL_DEPTH_STENCIL_ATTACHMENT;
		public static final int FrontLeft = GL_FRONT_LEFT;
		public static final int FrontRight = GL_FRONT_RIGHT;
		public static final int Stencil = GL_STENCIL;
		public static final int StencilAttachment = GL_STENCIL_ATTACHMENT;
	}
	/**
	 * Enum For Classifying OpenGL Renderbuffer Objects
	 * @author Cristian
	 *
	 */
	public static class RenderbufferTarget {
		public static final int Renderbuffer = GL30.GL_RENDERBUFFER;
		public static final int Renderbuffer43 = GL43.GL_RENDERBUFFER;
	}
	/**
	 * Enum For Data Type Of OpenGL Renderbuffer Objects
	 * @author Cristian
	 *
	 */
	public static class RenderbufferStorage {
		public static final int Depth24Stencil8 = GL_DEPTH24_STENCIL8;
		public static final int Depth32fStencil8 = GL_DEPTH32F_STENCIL8;
		public static final int DepthComponent = GL_DEPTH_COMPONENT;
		public static final int DepthComponent16 = GL_DEPTH_COMPONENT16;
		public static final int DepthComponent24 = GL_DEPTH_COMPONENT24;
		public static final int DepthComponent32 = GL_DEPTH_COMPONENT32;
		public static final int DepthComponent32f = GL_DEPTH_COMPONENT32F;
		public static final int DepthStencil = GL_DEPTH_STENCIL;
		public static final int R11fG11fB10f = GL_R11F_G11F_B10F;
		public static final int R16 = GL_R16;
		public static final int R16f = GL_R16F;
		public static final int R16i = GL_R16I;
		public static final int R16ui = GL_R16UI;
		public static final int R32f = GL_R32F;
		public static final int R32i = GL_R32I;
		public static final int R32ui = GL_R32UI;
		public static final int R3G3B2 = GL_R3_G3_B2;
		public static final int R8 = GL_R8;
		public static final int R8i = GL_R8I;
		public static final int R8ui = GL_R8UI;
		public static final int Rg16 = GL_RG16;
		public static final int Rg16f = GL_RG16F;
		public static final int Rg16i = GL_RG16I;
		public static final int Rg16ui = GL_RG16UI;
		public static final int Rg32f = GL_RG32F;
		public static final int Rg32i = GL_RG32I;
		public static final int Rg32ui = GL_RG32UI;
		public static final int Rg8 = GL_RG8;
		public static final int Rg8i = GL_RG8I;
		public static final int Rg8ui = GL_RG8UI;
		public static final int Rgb10 = GL_RGB10;
		public static final int Rgb10A2 = GL_RGB10_A2;
		public static final int Rgb10A2ui = GL_RGB10_A2UI;
		public static final int Rgb12 = GL_RGB12;
		public static final int Rgb16 = GL_RGB16;
		public static final int Rgb16f = GL_RGB16F;
		public static final int Rgb16i = GL_RGB16I;
		public static final int Rgb16ui = GL_RGB16UI;
		public static final int Rgb32f = GL_RGB32F;
		public static final int Rgb32i = GL_RGB32I;
		public static final int Rgb32ui = GL_RGB32UI;
		public static final int Rgb4 = GL_RGB4;
		public static final int Rgb5 = GL_RGB5;
		public static final int Rgb8 = GL_RGB8;
		public static final int Rgb8i = GL_RGB8I;
		public static final int Rgb8ui = GL_RGB8UI;
		public static final int Rgb9E5 = GL_RGB9_E5;
		public static final int Rgba12 = GL_RGBA12;
		public static final int Rgba16 = GL_RGBA16;
		public static final int Rgba16f = GL_RGBA16F;
		public static final int Rgba16i = GL_RGBA16I;
		public static final int Rgba16ui = GL_RGBA16UI;
		public static final int Rgba2 = GL_RGBA2;
		public static final int Rgba32f = GL_RGBA32F;
		public static final int Rgba32i = GL_RGBA32I;
		public static final int Rgba32ui = GL_RGBA32UI;
		public static final int Rgba4 = GL_RGBA4;
		public static final int Rgba8 = GL_RGBA8;
		public static final int Rgba8i = GL_RGBA8I;
		public static final int Rgba8ui = GL_RGBA8UI;
		public static final int Srgb8 = GL_SRGB8;
		public static final int Srgb8Alpha8 = GL_SRGB8_ALPHA8;
		public static final int StencilIndex1 = GL_STENCIL_INDEX1;
		public static final int StencilIndex16 = GL_STENCIL_INDEX16;
		public static final int StencilIndex4 = GL_STENCIL_INDEX4;
		public static final int StencilIndex8 = GL_STENCIL_INDEX8;
	}
	/**
	 * Enum For Specifying Pixel Output Location
	 * @author Cristian
	 *
	 */
	public static class DrawBufferMode {
		public static final int Back = GL_BACK;
		public static final int BackLeft = GL_BACK_LEFT;
		public static final int BackRight = GL_BACK_RIGHT;
		public static final int ColorAttachment0 = GL_COLOR_ATTACHMENT0;
		public static final int ColorAttachment1 = GL_COLOR_ATTACHMENT1;
		public static final int ColorAttachment10 = GL_COLOR_ATTACHMENT10;
		public static final int ColorAttachment11 = GL_COLOR_ATTACHMENT11;
		public static final int ColorAttachment12 = GL_COLOR_ATTACHMENT12;
		public static final int ColorAttachment13 = GL_COLOR_ATTACHMENT13;
		public static final int ColorAttachment14 = GL_COLOR_ATTACHMENT14;
		public static final int ColorAttachment15 = GL_COLOR_ATTACHMENT15;
		public static final int ColorAttachment2 = GL_COLOR_ATTACHMENT2;
		public static final int ColorAttachment3 = GL_COLOR_ATTACHMENT3;
		public static final int ColorAttachment4 = GL_COLOR_ATTACHMENT4;
		public static final int ColorAttachment5 = GL_COLOR_ATTACHMENT5;
		public static final int ColorAttachment6 = GL_COLOR_ATTACHMENT6;
		public static final int ColorAttachment7 = GL_COLOR_ATTACHMENT7;
		public static final int ColorAttachment8 = GL_COLOR_ATTACHMENT8;
		public static final int ColorAttachment9 = GL_COLOR_ATTACHMENT9;
		public static final int Front = GL_FRONT;
		public static final int FrontAndBack = GL_FRONT_AND_BACK;
		public static final int FrontLeft = GL_FRONT_LEFT;
		public static final int FrontRight = GL_FRONT_RIGHT;
		public static final int Left = GL_LEFT;
		public static final int None = GL_NONE;
		public static final int Right = GL_RIGHT;
	}
	/**
	 * Enum For Errors With The OpenGL Framebuffer Objects
	 * @author Cristian
	 *
	 */
	public static class FramebufferErrorCode {
		public static final int FramebufferComplete = GL_FRAMEBUFFER_COMPLETE;
		public static final int FramebufferIncompleteAttachment = GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT;
		public static final int FramebufferIncompleteDrawBuffer = GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER;
		public static final int FramebufferIncompleteLayerTargets = GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS;
		public static final int FramebufferIncompleteMissingAttachment = GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT;
		public static final int FramebufferIncompleteMultisample = GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE;
		public static final int FramebufferIncompleteReadBuffer = GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER;
		public static final int FramebufferUndefined = GL_FRAMEBUFFER_UNDEFINED;
		public static final int FramebufferUnsupported = GL_FRAMEBUFFER_UNSUPPORTED;
	}
	/**
	 * Enum For General OpenGL Errors
	 * @author Cristian
	 *
	 */
	public static class ErrorCode {
		public static final int InvalidEnum = GL_INVALID_ENUM;
		public static final int InvalidFramebufferOperation = GL_INVALID_FRAMEBUFFER_OPERATION;
		public static final int InvalidOperation = GL_INVALID_OPERATION;
		public static final int InvalidValue = GL_INVALID_VALUE;
		public static final int NoError = GL_NO_ERROR;
		public static final int OutOfMemory = GL_OUT_OF_MEMORY;
	}
	/**
	 * Enum For Toggling OpenGL Pipeline Features
	 * @author Cristian
	 *
	 */
	public static class EnableCap {
		public static final int Blend = GL_BLEND;
		public static final int ClipDistance0 = GL_CLIP_DISTANCE0;
		public static final int ClipDistance1 = GL_CLIP_DISTANCE1;
		public static final int ClipDistance2 = GL_CLIP_DISTANCE2;
		public static final int ClipDistance3 = GL_CLIP_DISTANCE3;
		public static final int ClipDistance4 = GL_CLIP_DISTANCE4;
		public static final int ClipDistance5 = GL_CLIP_DISTANCE5;
		public static final int ClipDistance6 = GL_CLIP_DISTANCE6;
		public static final int ClipDistance7 = GL_CLIP_DISTANCE7;
		public static final int ClipPlane0 = GL_CLIP_PLANE0;
		public static final int ClipPlane1 = GL_CLIP_PLANE1;
		public static final int ClipPlane2 = GL_CLIP_PLANE2;
		public static final int ClipPlane3 = GL_CLIP_PLANE3;
		public static final int ClipPlane4 = GL_CLIP_PLANE4;
		public static final int ClipPlane5 = GL_CLIP_PLANE5;
		public static final int ColorLogicOp = GL_COLOR_LOGIC_OP;
		public static final int ColorSum = GL_COLOR_SUM;
		public static final int CullFace = GL_CULL_FACE;
		public static final int DebugOutput = GL_DEBUG_OUTPUT;
		public static final int DebugOutputSynchronous = GL_DEBUG_OUTPUT_SYNCHRONOUS;
		public static final int DepthClamp = GL_DEPTH_CLAMP;
		public static final int DepthTest = GL_DEPTH_TEST;
		public static final int Dither = GL_DITHER;
		public static final int FogCoordArray = GL_FOG_COORD_ARRAY;
		public static final int FramebufferSrgb = GL_FRAMEBUFFER_SRGB;
		public static final int LineSmooth = GL_LINE_SMOOTH;
		public static final int Multisample = GL_MULTISAMPLE;
		public static final int PointSprite = GL_POINT_SPRITE;
		public static final int PolygonOffsetFill = GL_POLYGON_OFFSET_FILL;
		public static final int PolygonOffsetLine = GL_POLYGON_OFFSET_LINE;
		public static final int PolygonOffsetPoint = GL_POLYGON_OFFSET_POINT;
		public static final int PolygonSmooth = GL_POLYGON_SMOOTH;
		public static final int PrimitiveRestart = GL_PRIMITIVE_RESTART;
		public static final int PrimitiveRestartFixedIndex = GL_PRIMITIVE_RESTART_FIXED_INDEX;
		public static final int ProgramPointSize = GL_PROGRAM_POINT_SIZE;
		public static final int RasterizerDiscard = GL_RASTERIZER_DISCARD;
		public static final int RescaleNormal = GL_RESCALE_NORMAL;
		public static final int SampleAlphaToCoverage = GL_SAMPLE_ALPHA_TO_COVERAGE;
		public static final int SampleAlphaToOne = GL_SAMPLE_ALPHA_TO_ONE;
		public static final int SampleCoverage = GL_SAMPLE_COVERAGE;
		public static final int SampleMask = GL_SAMPLE_MASK;
		public static final int SampleShading = GL_SAMPLE_SHADING;
		public static final int ScissorTest = GL11.GL_SCISSOR_TEST;
		public static final int ScissorTest41 = GL41.GL_SCISSOR_TEST;
		public static final int SecondaryColorArray = GL_SECONDARY_COLOR_ARRAY;
		public static final int StencilTest = GL_STENCIL_TEST;
		public static final int Texture1D = GL11.GL_TEXTURE_1D;
		public static final int Texture1D43 = GL43.GL_TEXTURE_1D;
		public static final int Texture2D = GL11.GL_TEXTURE_2D;
		public static final int Texture2D43 = GL43.GL_TEXTURE_2D;
		public static final int TextureCubeMap = GL13.GL_TEXTURE_CUBE_MAP;
		public static final int TextureCubeMap43 = GL43.GL_TEXTURE_CUBE_MAP;
		public static final int TextureCubeMapSeamless = GL_TEXTURE_CUBE_MAP_SEAMLESS;
		public static final int TextureRectangle = GL31.GL_TEXTURE_RECTANGLE;
		public static final int TextureRectangle43 = GL43.GL_TEXTURE_RECTANGLE;
		public static final int VertexProgramPointSize = GL_VERTEX_PROGRAM_POINT_SIZE;
		public static final int VertexProgramTwoSide = GL_VERTEX_PROGRAM_TWO_SIDE;
	}
	/**
	 * Enum For OpenGL Texture Minification Sampling
	 * @author Cristian
	 *
	 */
	public static class TextureMinFilter {
		public static final int Linear = GL_LINEAR;
		public static final int LinearMipmapLinear = GL_LINEAR_MIPMAP_LINEAR;
		public static final int LinearMipmapNearest = GL_LINEAR_MIPMAP_NEAREST;
		public static final int Nearest = GL_NEAREST;
		public static final int NearestMipmapLinear = GL_NEAREST_MIPMAP_LINEAR;
		public static final int NearestMipmapNearest = GL_NEAREST_MIPMAP_NEAREST;
	}
	/**
	 * Enum For OpenGL Texture Magnification Sampling
	 * @author Cristian
	 *
	 */
	public static class TextureMagFilter {
		public static final int Linear = GL_LINEAR;
		public static final int Nearest = GL_NEAREST;
	}
	/**
	 * Enum For OpenGL Texture Wrapping Mode
	 * @author Cristian
	 *
	 */
	public static class TextureWrapMode {
		public static final int ClampToBorder = GL_CLAMP_TO_BORDER;
		public static final int ClampToEdge = GL_CLAMP_TO_EDGE;
		public static final int MirroredRepeat = GL_MIRRORED_REPEAT;
		public static final int Repeat = GL_REPEAT;
	}
	/**
	 * Enum For Depth Value Comparisons Used To Determine Pixel Output
	 * @author Cristian
	 *
	 */
	public static class DepthFunction {
		public static final int Always = GL_ALWAYS;
		public static final int Equal = GL_EQUAL;
		public static final int Gequal = GL_GEQUAL;
		public static final int Greater = GL_GREATER;
		public static final int Lequal = GL_LEQUAL;
		public static final int Less = GL_LESS;
		public static final int Never = GL_NEVER;
		public static final int Notequal = GL_NOTEQUAL;
	}
	/**
	 * Enum For Specifying Which Faces Of A Triangle To Cull
	 * @author Cristian
	 *
	 */
	public static class CullFaceMode {
		public static final int Back = GL_BACK;
		public static final int Front = GL_FRONT;
		public static final int FrontAndBack = GL_FRONT_AND_BACK;
	}
	/**
	 * Enum For Specifying What Vertex Winding Leads To A Front Face On A Triangle
	 * @author Cristian
	 *
	 */
	public static class FrontFaceDirection {
		public static final int Ccw = GL_CCW;
		public static final int Cw = GL_CW;
	}
	/**
	 * Enum For Setting General Pixel Blending Operation
	 * @author Cristian
	 *
	 */
	public static class BlendEquationMode {
		public static final int FuncAdd = GL_FUNC_ADD;
		public static final int FuncReverseSubtract = GL_FUNC_REVERSE_SUBTRACT;
		public static final int FuncSubtract = GL_FUNC_SUBTRACT;
		public static final int Max = GL_MAX;
		public static final int Min = GL_MIN;
	}
	/**
	 * Enum For Setting Multiplicative Factor Of Source Pixel In Blend Equation
	 * @author Cristian
	 *
	 */
	public static class BlendingFactorSrc {
		public static final int ConstantAlpha = GL_CONSTANT_ALPHA;
		public static final int ConstantColor = GL_CONSTANT_COLOR;
		public static final int DstAlpha = GL_DST_ALPHA;
		public static final int DstColor = GL_DST_COLOR;
		public static final int One = GL_ONE;
		public static final int OneMinusConstantAlpha = GL_ONE_MINUS_CONSTANT_ALPHA;
		public static final int OneMinusConstantColor = GL_ONE_MINUS_CONSTANT_COLOR;
		public static final int OneMinusDstAlpha = GL_ONE_MINUS_DST_ALPHA;
		public static final int OneMinusDstColor = GL_ONE_MINUS_DST_COLOR;
		public static final int OneMinusSrc1Alpha = GL_ONE_MINUS_SRC1_ALPHA;
		public static final int OneMinusSrc1Color = GL_ONE_MINUS_SRC1_COLOR;
		public static final int OneMinusSrcAlpha = GL_ONE_MINUS_SRC_ALPHA;
		public static final int OneMinusSrcColor = GL_ONE_MINUS_SRC_COLOR;
		public static final int Src1Alpha = GL15.GL_SRC1_ALPHA;
		public static final int Src1Alpha33 = GL33.GL_SRC1_ALPHA;
		public static final int Src1Color = GL_SRC1_COLOR;
		public static final int SrcAlpha = GL_SRC_ALPHA;
		public static final int SrcAlphaSaturate = GL_SRC_ALPHA_SATURATE;
		public static final int SrcColor = GL_SRC_COLOR;
		public static final int Zero = GL_ZERO;
	}
	/**
	 * Enum For Setting Multiplicative Factor Of Destination Pixel In Blend Equation
	 * @author Cristian
	 *
	 */
	public static class BlendingFactorDest {
		public static final int ConstantAlpha = GL_CONSTANT_ALPHA;
		public static final int ConstantColor = GL_CONSTANT_COLOR;
		public static final int DstAlpha = GL_DST_ALPHA;
		public static final int DstColor = GL_DST_COLOR;
		public static final int One = GL_ONE;
		public static final int OneMinusConstantAlpha = GL_ONE_MINUS_CONSTANT_ALPHA;
		public static final int OneMinusConstantColor = GL_ONE_MINUS_CONSTANT_COLOR;
		public static final int OneMinusDstAlpha = GL_ONE_MINUS_DST_ALPHA;
		public static final int OneMinusDstColor = GL_ONE_MINUS_DST_COLOR;
		public static final int OneMinusSrc1Alpha = GL_ONE_MINUS_SRC1_ALPHA;
		public static final int OneMinusSrc1Color = GL_ONE_MINUS_SRC1_COLOR;
		public static final int OneMinusSrcAlpha = GL_ONE_MINUS_SRC_ALPHA;
		public static final int OneMinusSrcColor = GL_ONE_MINUS_SRC_COLOR;
		public static final int Src1Alpha = GL15.GL_SRC1_ALPHA;
		public static final int Src1Alpha33 = GL33.GL_SRC1_ALPHA;
		public static final int Src1Color = GL_SRC1_COLOR;
		public static final int SrcAlpha = GL_SRC_ALPHA;
		public static final int SrcAlphaSaturate = GL_SRC_ALPHA_SATURATE;
		public static final int SrcColor = GL_SRC_COLOR;
		public static final int Zero = GL_ZERO;
	}
	/**
	 * Enum For Specifying Topology And Render Method Of The Input Data
	 * @author Cristian
	 *
	 */
	public static class PrimitiveType {
		public static final int LineLoop = GL_LINE_LOOP;
		public static final int Lines = GL_LINES;
		public static final int LinesAdjacency = GL_LINES_ADJACENCY;
		public static final int LineStrip = GL_LINE_STRIP;
		public static final int LineStripAdjacency = GL_LINE_STRIP_ADJACENCY;
		public static final int Patches = GL_PATCHES;
		public static final int Points = GL_POINTS;
		public static final int Quads = GL_QUADS;
		public static final int TriangleFan = GL_TRIANGLE_FAN;
		public static final int Triangles = GL_TRIANGLES;
		public static final int TrianglesAdjacency = GL_TRIANGLES_ADJACENCY;
		public static final int TriangleStrip = GL_TRIANGLE_STRIP;
		public static final int TriangleStripAdjacency = GL_TRIANGLE_STRIP_ADJACENCY;
	}
}
