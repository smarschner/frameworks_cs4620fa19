package egl;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;

/**
 * Wrapper For Providing Correct Native Memory Within Java
 * @author Cristian
 *
 */
public class NativeMem {
	/**
	 * There Can Be Only One
	 */
	private static final int MEM_TOGGLE = 2;
	
	/**
	 * Allocates A Native Memory Buffer (One Hopes)
	 * @param size Number Of Bytes To Allocate
	 * @return Something Like A Pointer
	 */
	public static ByteBuffer createByteBuffer(int size) {
		switch (MEM_TOGGLE) {
		case 0: return ByteBuffer.allocate(size);
		case 1: return ByteBuffer.allocateDirect(size);
		default: return BufferUtils.createByteBuffer(size);
		}
	}
	/**
	 * Allocates A Native Memory Buffer
	 * @param size Number Of Shorts To Allocate
	 * @return A Memory Buffer
	 */
	public static ShortBuffer createShortBuffer(int size) {
		return createByteBuffer(size * 2).asShortBuffer();
	}
	/**
	 * Allocates A Native Memory Buffer
	 * @param size Number Of Integers To Allocate
	 * @return A Memory Buffer
	 */
	public static IntBuffer createIntBuffer(int size) {
		return createByteBuffer(size * 4).asIntBuffer();
	}
	/**
	 * Allocates A Native Memory Buffer
	 * @param size Number Of Longs To Allocate
	 * @return A Memory Buffer
	 */
	public static LongBuffer createLongBuffer(int size) {
		return createByteBuffer(size * 8).asLongBuffer();
	}
	/**
	 * Allocates A Native Memory Buffer
	 * @param size Number Of Floats To Allocate
	 * @return A Memory Buffer
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return createByteBuffer(size * 4).asFloatBuffer();
	}
	/**
	 * Allocates A Native Memory Buffer
	 * @param size Number Of Doubles To Allocate
	 * @return A Memory Buffer
	 */
	public static DoubleBuffer createDoubleBuffer(int size) {
		return createByteBuffer(size * 8).asDoubleBuffer();
	}
	/**
	 * Allocates A Native Memory Buffer
	 * @param size Number Of Chars To Allocate
	 * @return A Memory Buffer
	 */
	public static CharBuffer createCharBuffer(int size) {
		return createByteBuffer(size * 2).asCharBuffer();
	}
}
