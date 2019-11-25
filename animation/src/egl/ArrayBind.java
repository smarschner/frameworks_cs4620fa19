package egl;


/**
 * Provides Information About A Component Inside Of A Vertex
 * Struct For Correctly Binding Buffers To Programs
 * 
 * @author Cristian
 *
 */
public class ArrayBind {
	/**
	 * Input Location In The Program
	 */
	public int location;
	/**
	 * Vertex Attribute Usage
	 */
    public int semantic;
    /**
     * The Basic Type (Float, Byte, etc.)
     */
    public int compType;
    /**
     * Number Of Components Types Packed Together For The Full Component
     */
    public int compCount;
    /**
     * Bytes Of Offset Into The Struct
     */
    public int offset;
    /**
     * Normalization Upon Being Sent To GPU (For Integral Types Mainly)
     */
    public boolean isNormalized;

    /**
     * Default Full Constructor Before Program Linkage
     * @param sem Enum Semantic
     * @param ct Enum GL.VertexAttribPointerType
     * @param cc Component Counts
     * @param o Bytes Offset Into Vertex Struct
     * @param norm GPU Normalization Of Integral Values To [0-1]
     */
    public ArrayBind(int sem, int ct, int cc, int o, boolean norm) {
        location = 0;
        semantic = sem;
        compType = ct;
        compCount = cc;
        offset = o;
        isNormalized = norm;
    }
    /**
     * @see {@link #ArrayBind(int, int, int, int, boolean) ArrayBind(sem, ct, cc, o, false)}
     */
    public ArrayBind(int sem, int ct, int cc, int o) {
    	this(sem, ct, cc, o, false);
    }
}
