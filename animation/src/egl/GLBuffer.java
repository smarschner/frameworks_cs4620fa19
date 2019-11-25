package egl;

import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;

import egl.GL.BufferTarget;
import egl.GL.GLType;

/**
 * Wrapper For An OpenGL Vertex Array Object
 * And Multiple Buffer Objects
 * @author Cristian
 *
 */
public class GLBuffer implements IDisposable {
	/**
	 * Because Java Sucks At Enums
	 */
	private static final int[] BUFFER_TARGETS = {
		BufferTarget.ArrayBuffer,
		BufferTarget.AtomicCounterBuffer,
		BufferTarget.CopyReadBuffer,
		BufferTarget.CopyWriteBuffer,
		BufferTarget.DispatchIndirectBuffer,
		BufferTarget.DrawIndirectBuffer,
		BufferTarget.ElementArrayBuffer,
		BufferTarget.PixelPackBuffer,
		BufferTarget.PixelUnpackBuffer,
		BufferTarget.QueryBuffer,
		BufferTarget.ShaderStorageBuffer,
		BufferTarget.TextureBuffer,
		BufferTarget.TransformFeedbackBuffer,
		BufferTarget.UniformBuffer
	};
	
	/**
	 * Used As Pointer
	 * @author Cristian
	 *
	 */
	private static class Binding {
        private int target;
        public GLBuffer current;

        public Binding(int t) {
            target = t;
            current = null;
        }
        public void unbind() {
            current = null;
            glBindBuffer(target, 0);
        }
    }

	/**
	 * The Bindings To All Of The Buffer Targets
	 */
    private static HashMap<Integer, Binding> currentBindings;
    static {
        currentBindings = new HashMap<Integer, Binding>();
        for(int bt : BUFFER_TARGETS) {
            currentBindings.put(bt, new Binding(bt));
        }
    }
    /**
     * Unbind The Buffer Set To The Current Target Or Clear It
     * When Messing With Wild OpenGL
     * @param t Enum {@link BufferTarget}
     */
    public static void unbind(int t) {
        currentBindings.get(t).unbind();
    }

    /**
     * OpenGL Object ID
     */
    private int id;
    /**
     * Buffer Type And Double Pointer For Checking Usage
     */
    private int target;
    /**
     * Scream At The GPU Where Data Should Be Placed
     */
    private int usageType;
    /**
     * Pointer To A Binding Target
     */
    private Binding refBind;
    /**
     * Buffer Element Information
     */
    private int componentFormat, componentCount, elementByteSize;
    /**
     * Buffer Capacity (In Bytes) And Current Elements Count
     */
    private int bufCapacity;
    
    /**
     * Default Constructor For A Buffer
     * @param target Buffer Purpose - Enum {@link egl.GL.BufferTarget}
     * @param usage Data Storage Hint - Enum {@link egl.GL.BufferUsageHint}
     * @param init True To Call Init Inside In The Constructor (Requires Active OpenGL Context)
     */
    public GLBuffer(int target, int usage, boolean init) {
        // Default Parameters
        id = 0;

        setTarget(target);
        usageType = usage;

        if(init) init();
    }
    /**
     * @see {@link #GLBuffer(int, int, boolean) GLBuffer(target, usage, false)}
     */
    public GLBuffer(int target, int usage) {
    	this(target, usage, false);
    }
    /**
     * Destroy The OpenGL Resources Held By The Buffer
     */
    @Override
    public void dispose() {
        if(!getIsCreated()) return;
        glDeleteBuffers(id);
        id = 0;
    }

    /**
     * Returns 0 If Uninitialized
     * @return OpenGL Buffer ID
     */
    public int getID() {
    	return id;
    }
    /**
     * 
     * @return True If The OpenGL Buffer Is Initialized
     */
    public boolean getIsCreated() {
        return id != 0;
    }
    /**
     * Return This Target
     * @return The OpenGL Buffer Bind {@link egl.GL.BufferTarget Target}
     */
    public int getTarget() {
        return target;
    }
    /**
     * 
     * @return True If GLBuffer Recognizes This As The Currently Bound Buffer
     */
    public boolean getIsBound() {
        return refBind != null && refBind.current == this;
    }
    /**
     * @see Enum {@link egl.GL.BufferUsageHint}
     * @return Data Storage Hints For OpenGL
     */
    public int getUsageType() {
    	return usageType;
    }
    /**
     * @see {@link egl.GL.GLType}
     * @return Format Of A Single Element Of The Buffer
     */
    public int getComponentFormat() {
    	return componentFormat;
    }
    /**
     * 
     * @return Number Of Components In A Full Element
     */
    public int getComponentCount() {
    	return componentCount;
    }
    /**
     * 
     * @return Element Stride In Bytes
     */
    public int getElementByteSize() {
    	return elementByteSize;
    }
    /**
     * 
     * @return Buffer Capacity On The GPU In Bytes
     */
	public int getBufCapacity() {
    	return bufCapacity;
    }    
    
    /**
     * Create The OpenGL Buffer Resource In The Active Context
     * @return Self
     */
    public GLBuffer init() {
        if(getIsCreated()) return this;
        id = glGenBuffers();
        return this;
    }

    /**
     * Set This Buffer's Type
     * @param value {@link BufferTarget}
     */
    private void setTarget(int value) {
        target = value;
        refBind = currentBindings.get(target);
    }

    /**
     * Set The Element Format For This Buffer
     * @param format Enum {@link egl.GL.GLType}
     * @param count Number Of Components Of Type Format
     * @return Self
     */
    public GLBuffer setElementFormat(int format, int count) {
        // Use A New Element Format Basis
        componentFormat = format;
        componentCount = count;
        elementByteSize = componentCount * GLUtil.sizeOf(componentFormat);
        return this;
    }
    /**
     * Turns This Buffer Into An Index Buffer Of 32-Bit Indices
     * @return Self
     */
    public GLBuffer setAsIndexInt() {
        setTarget(BufferTarget.ElementArrayBuffer);
        return setElementFormat(GLType.UnsignedInt, 1);
    }
    /**
     * Turns This Buffer Into An Index Buffer Of 16-Bit Indices
     * @return Self
     */
    public GLBuffer setAsIndexShort() {
    	setTarget(BufferTarget.ElementArrayBuffer);
        return setElementFormat(GLType.UnsignedShort, 1);
    }
    /**
     * Turns This Buffer Into A Vertex Buffer Using A Single 32-Bit Float
     * @return Self
     */
    public GLBuffer setAsVertexFloat() {
    	setTarget(BufferTarget.ArrayBuffer);
        return setElementFormat(GLType.Float, 1);
    }
    /**
     * Turns This Buffer Into A Vertex Buffer Using 2 32-Bit Floats
     * @return Self
     */
    public GLBuffer setAsVertexVec2() {
    	setTarget(BufferTarget.ArrayBuffer);
        return setElementFormat(GLType.Float, 2);
    }
    /**
     * Turns This Buffer Into A Vertex Buffer Using 3 32-Bit Floats
     * @return Self
     */
    public GLBuffer setAsVertexVec3() {
    	setTarget(BufferTarget.ArrayBuffer);
        return setElementFormat(GLType.Float, 3);
    }
    /**
     * Turns This Buffer Into A Vertex Buffer Using 4 32-Bit Floats
     * @return Self
     */
    public GLBuffer setAsVertexVec4() {
    	setTarget(BufferTarget.ArrayBuffer);
        return setElementFormat(GLType.Float, 4);
    }
    /**
     * Turns This Buffer Into A Vertex Buffer Using A Vertex Struct
     * @param vSize Size Of A Vertex Element In Bytes
     * @return Self
     */
    public GLBuffer setAsVertex(int vSize) {
    	setTarget(BufferTarget.ArrayBuffer);
        return setElementFormat(GLType.UnsignedByte, vSize);
    }

    /**
     * Bind This Buffer To It's Target If Not Already Bound
     */
    public void bind() {
        if(!getIsBound()) {
            refBind.current = this;
            glBindBuffer(target, id);
            GLError.get("Buffer Bind");
        }
    }
    /**
     * Unbind This Buffer If It Is Recognized As Being Bound
     */
    public void unbind() {
        if(getIsBound()) refBind.unbind();
    }
    /**
     * Use This As A Vertex Buffer With Elements Bound To An Attribute
     * @param loc Attribute Location
     * @param offset Starting Element
     * @param instDiv Instancing Count
     * @param norm True To Normalize Integer Components On The GPU
     */
    public void useAsAttrib(int loc, int offset, int instDiv, boolean norm) {
        bind();
        glEnableVertexAttribArray(loc);
        GLError.get("Enable VAA");
        glVertexAttribPointer(loc, componentCount, componentFormat, norm, elementByteSize, offset * elementByteSize);
        if(instDiv > 0)
            glVertexAttribDivisor(loc, instDiv);
        GLError.get("VAP");
        unbind();
    }
    /**
     * @see {@link #useAsAttrib(int, int, int, boolean) useAsAttrib(loc, offset, instDiv, false)}
     */
    public void useAsAttrib(int loc, int offset, int instDiv) {
    	useAsAttrib(loc, offset, instDiv, false);
    }
    /**
     * @see {@link #useAsAttrib(int, int, int, boolean) useAsAttrib(loc, offset, 0, false)}
     */
    public void useAsAttrib(int loc, int offset) {
    	useAsAttrib(loc, offset, 0);
    }
   /**
    * @see {@link #useAsAttrib(int, int, int, boolean) useAsAttrib(loc, 0, instDiv, false)}
    */
    public void useAsAttrib(int loc) {
    	useAsAttrib(loc, 0);
    }
    /**
     * Use This As A Vertex Buffer With Struct Elements Bound To An Interface Of Attributes
     * @param si Interface For Binding Each Element In The Vertex Struct
     * @param offset Starting Element
     * @param instDiv Instancing Count
     */
    public void useAsAttrib(ShaderInterface si, int offset, int instDiv) {
        bind();
        // Calculate Stride And Bytes Of Offset
        offset *= elementByteSize;
        for(ArrayBind bind : si.binds) {
            if(bind.location < 0) continue;
            glEnableVertexAttribArray(bind.location);
            GLError.get("Enable VAA");
            glVertexAttribPointer(bind.location, bind.compCount, bind.compType, bind.isNormalized, elementByteSize, offset + bind.offset);
            if(instDiv > 0)
                glVertexAttribDivisor(bind.location, instDiv);
            GLError.get("VAP");
        }
        unbind();
    }
    /**
     * @see {@link #useAsAttrib(ShaderInterface, int, int) useAsAttrib(si, offset, 0)}
     */
    public void useAsAttrib(ShaderInterface si, int offset) {
    	useAsAttrib(si, offset, 0);
    }
    /**
     * @see {@link #useAsAttrib(ShaderInterface, int, int) useAsAttrib(si, 0, 0)}
     */
    public void useAsAttrib(ShaderInterface si) {
    	useAsAttrib(si, 0);
    }

    /**
     * Resize This Buffer (And Discard Buffer Data)
     * <br/><br/>
     * <code>
     * glBufferData(target, bytes, 0, usageType);
     * </code>
     * @param bytes New Capacity In Bytes
     */
    public void setSizeInBytes(int bytes) {
        bufCapacity = bytes;
        bind();
        glBufferData(target, bufCapacity, usageType);
        unbind();
    }
    /**
     * @see {@link #setSizeInBytes(int) setSizeInBytes(elements * getElementByteSize())}
     * @param elements Element Capacity
     */
    public void setSizeInElements(int elements) {
        setSizeInBytes(elements * elementByteSize);
    }
    
    /**
     * Set This Buffer's Data Equivalent To The Given Buffer
     * <br/><br/>
     * <code>
     * glBufferData(target, "sizeof(data)", data, usageType);
     * </code>
     * @param data Buffer Data
     */
    public void setDataInitial(ByteBuffer data) {
        bufCapacity = data.limit();
        bind();
        glBufferData(target, data, usageType);
        unbind();
    }
    /**
     * Set This Buffer's Data Equivalent To The Given Buffer
     * <br/><br/>
     * <code>
     * glBufferData(target, "sizeof(data)", data, usageType);
     * </code>
     * @param data Buffer Data
     */
    public void setDataInitial(ShortBuffer data) {
        bufCapacity = data.limit() << 1;
        bind();
        glBufferData(target, data, usageType);
        unbind();
    }
    /**
     * Set This Buffer's Data Equivalent To The Given Buffer
     * <br/><br/>
     * <code>
     * glBufferData(target, "sizeof(data)", data, usageType);
     * </code>
     * @param data Buffer Data
     */
    public void setDataInitial(IntBuffer data) {
        bufCapacity = data.limit() << 2;
        bind();
        glBufferData(target, data, usageType);
        unbind();
    }
    /**
     * Set This Buffer's Data Equivalent To The Given Buffer
     * <br/><br/>
     * <code>
     * glBufferData(target, "sizeof(data)", data, usageType);
     * </code>
     * @param data Buffer Data
     */
    public void setDataInitial(FloatBuffer data) {
        bufCapacity = data.limit() << 2;
        bind();
        glBufferData(target, data, usageType);
        unbind();
    }

    /**
     * If Necessary, Resizes Buffer To Fit The Appropriate Data Size
     * <br/>
     * (This Will Clear All Buffer Data On Resize)
     * @param bytes Desired Byte Size
     * @return True If This Buffer Was Resized
     */
    public boolean checkResizeInBytes(int bytes) {
        if(bytes <= bufCapacity / 4 || bytes > bufCapacity) {
            // Resize To Double The Desired Size
            setSizeInBytes(bytes * 2);
            return true;
        }
        return false;
    }
    /**
     * @see {@link #checkResizeInBytes(int) checkResizeInBytes(elements * getElementByteSize())}
     * @param elements Desired Number Of Elements
     * @return True If This Buffer Was Resized
     */
    public boolean checkResizeInElements(int elements) {
        return checkResizeInBytes(elements * elementByteSize);
    }

    public void setData(ByteBuffer data, int len, int off) {
        bind();
        data.limit(off + len);
        glBufferSubData(target, off, data);
        unbind();
    }
    public void setData(ByteBuffer data, long off) {
        bind();
        glBufferSubData(target, off, data);
        unbind();
    }
    public void setData(byte[] data, int len, int off) {
    	if(len <= 0) len = data.length - off;
    	ByteBuffer b = NativeMem.createByteBuffer(len);
    	b.put(data, off, len);
    	b.flip();
    	setData(b, len, 0);
    }
    public void setData(short[] data, int len, int off) {
    	if(len <= 0) len = data.length - off;
    	ByteBuffer bb = NativeMem.createByteBuffer(len << 1);
    	bb.asShortBuffer().put(data, off, len);
    	bb.limit(bb.capacity());
    	setData(bb, bb.capacity(), 0);
    }
    public void setData(int[] data, int len, int off) {
    	if(len <= 0) len = data.length - off;
    	ByteBuffer bb = NativeMem.createByteBuffer(len << 2);
    	bb.asIntBuffer().put(data, off, len);
    	bb.limit(bb.capacity());
    	setData(bb, bb.capacity(), 0);
    }
    public void setData(long[] data, int len, int off) {
    	if(len <= 0) len = data.length - off;
    	ByteBuffer bb = NativeMem.createByteBuffer(len << 3);
    	bb.asLongBuffer().put(data, off, len);
    	bb.limit(bb.capacity());
    	setData(bb, bb.capacity(), 0);
    }
    public void setData(float[] data, int len, int off) {
    	if(len <= 0) len = data.length - off;
    	ByteBuffer bb = NativeMem.createByteBuffer(len << 2);
    	bb.asFloatBuffer().put(data, off, len);
    	bb.limit(bb.capacity());
    	setData(bb, bb.capacity(), 0);
    }
    public void setData(double[] data, int len, int off) {
    	if(len <= 0) len = data.length - off;
    	ByteBuffer bb = NativeMem.createByteBuffer(len << 3);
    	bb.asDoubleBuffer().put(data, off, len);
    	bb.limit(bb.capacity());
    	setData(bb, bb.capacity(), 0);
    }
    public void setData(IVertexType[] data, int len, int off) {
    	if(len <= 0) len = data.length - off;
    	int vs = data[0].getByteSize();
    	int e = off + len;
    	ByteBuffer bb = NativeMem.createByteBuffer(len * vs);
    	for(int i = off;i < e;i++) {
    		data[i].appendToBuffer(bb);
    	}
    	bb.flip();
    	setData(bb, bb.capacity(), 0);
    }

    public void smartSetData(ByteBuffer data, int len, int off) {
        checkResizeInBytes(off + len);
        setData(data, len, off);
    }
    public void smartSetData(byte[] data, int len, int off) {
        checkResizeInBytes(off + len);
        setData(data, len, off);
    }
    public void smartSetData(short[] data, int len, int off) {
        checkResizeInBytes((off + len) << 1);
        setData(data, len, off);
    }
    public void smartSetData(int[] data, int len, int off) {
        checkResizeInBytes((off + len) << 2);
        setData(data, len, off);
    }
    public void smartSetData(long[] data, int len, int off) {
        checkResizeInBytes((off + len) << 3);
        setData(data, len, off);
    }
    public void smartSetData(float[] data, int len, int off) {
        checkResizeInBytes((off + len) << 2);
        setData(data, len, off);
    }
    public void smartSetData(double[] data, int len, int off) {
        checkResizeInBytes((off + len) << 3);
        setData(data, len, off);
    }
    public void smartSetData(IVertexType[] data, int len, int off) {
        checkResizeInBytes((off + len) * data[0].getByteSize());
        setData(data, len, off);
    }

    /**
     * Initialize This As A Vertex Buffer Containing Data
     * @param data Buffer Vertex Data
     * @param vecDim Components Per Element
     * @return Self
     */
    public GLBuffer initAsVertex(float[] data, int vecDim) {
        init();
        setElementFormat(GLType.Float, vecDim);
        setTarget(BufferTarget.ArrayBuffer);
        smartSetData(data, data.length, 0);
        return this;
    }
    /**
     * Initialize This As An Index Buffer Containing Data
     * @param data Buffer Index Data
     * @return Self
     */
    public GLBuffer initAsIndex(int[] data) {
        init();
        setAsIndexInt();
        smartSetData(data, data.length, 0);
        return this;
    }
    /**
     * Initialize This As An Index Buffer Containing Data
     * @param data Buffer Index Data
     * @return Self
     */
    public GLBuffer initAsIndex(short[] data) {
        init();
        setAsIndexShort();
        smartSetData(data, data.length, 0);
        return this;
    }
    
    /**
     * Initialize This As A Vertex Buffer Containing Data
     * @param data Buffer Vertex Data
     * @param vecDim Components Per Element
     * @return Self
     */
    static public GLBuffer createAsVertex(float[] data, int vecDim, int usage) {
    	GLBuffer buf = new GLBuffer(BufferTarget.ArrayBuffer, usage);
        buf.init();
        buf.setTarget(BufferTarget.ArrayBuffer);
        buf.setElementFormat(GLType.Float, vecDim);
        
        FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
		fb.put(data);
		fb.position(0);
		fb.limit(data.length);
		buf.setDataInitial(fb);
		
        return buf;
    }
    /**
     * Initialize This As An Index Buffer Containing Data
     * @param data Buffer Index Data
     * @return Self
     */
    static public GLBuffer createAsIndex(int[] data, int usage) {
    	GLBuffer buf = new GLBuffer(BufferTarget.ElementArrayBuffer, usage);
        buf.init();
        buf.setAsIndexInt();
        
        IntBuffer intb = BufferUtils.createIntBuffer(data.length);
		intb.put(data);
		intb.position(0);
		intb.limit(data.length);
		
		buf.setDataInitial(intb);
        return buf;
    }
}
