package egl;

import egl.GL.GLType;

public class GLUtil {
	/**
	 * 
	 * @param t {@link GLType OpenGL Basic Type}
	 * @return Size Of The Type In Bytes
	 */
	public static int sizeOf(int t) {
        switch(t) {
            case GLType.UnsignedByte:
            case GLType.Byte:
                return 1;
            case GLType.UnsignedShort:
            case GLType.Short:
            case GLType.HalfFloat:
                return 2;
            case GLType.UnsignedInt:
            case GLType.Int:
            case GLType.Float:
            case GLType.Int2101010Rev:
            case GLType.UnsignedInt2101010Rev:
            case GLType.Fixed:
                return 4;
            case GLType.Double:
                return 8;
        }
        return 0;
    }
}