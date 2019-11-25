package gl.manip;

public class Manipulator {
	public static class Type {
		public static final int SCALE = 0;
		public static final int ROTATE = 1;
		public static final int TRANSLATE = 2;
	}
	public static class Axis {
		public static final int X = 0;
		public static final int Y = 1;
		public static final int Z = 2;
	}
	
	public static final Manipulator ScaleX = new Manipulator(Type.SCALE, Axis.X);
	public static final Manipulator ScaleY = new Manipulator(Type.SCALE, Axis.Y);
	public static final Manipulator ScaleZ = new Manipulator(Type.SCALE, Axis.Z);
	public static final Manipulator RotateX = new Manipulator(Type.ROTATE, Axis.X);
	public static final Manipulator RotateY = new Manipulator(Type.ROTATE, Axis.Y);
	public static final Manipulator RotateZ = new Manipulator(Type.ROTATE, Axis.Z);
	public static final Manipulator TranslateX = new Manipulator(Type.TRANSLATE, Axis.X);
	public static final Manipulator TranslateY = new Manipulator(Type.TRANSLATE, Axis.Y);
	public static final Manipulator TranslateZ = new Manipulator(Type.TRANSLATE, Axis.Z);
	
	public final int type;
	public final int axis;
	
	private Manipulator(int t, int a) {
		type = t;
		axis = a;
	}
}
