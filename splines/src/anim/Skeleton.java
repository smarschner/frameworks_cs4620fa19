package anim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import egl.math.Matrix4;

/**
 * You Can Find Many Of These In Your Bedroom Closet
 * @author Cristian
 *
 */
public class Skeleton {
	/**
	 * A Simple Pairing Of Bones
	 * @author Cristian
	 *
	 */
	public static class BoneBinding {
		public final String parent;
		public final String child;
		
		public BoneBinding(String p, String c) {
			parent = p;
			child = c;
		}
	}
	
	public final int boneCount;
	
	/**
	 * A List Of Named Bones
	 */
	private final HashMap<String, Bone> bones = new HashMap<>();
	/**
	 * Bones With Null Parents
	 */
	public final ArrayList<Bone> roots = new ArrayList<>();
	
	public Skeleton(int numBones) {
		boneCount = numBones;
	}
	
	public Bone add(int i, String name) {
		Bone b = new Bone(name, i);
		bones.put(name, b);
		return b;
	}
	public Bone get(String name) {
		return bones.get(name);
	}

	/**
	 * Refreshes The Hierarchy Of Bones
	 * @param binds A List Of Parent Name-Child Name Bindings
	 */
	public void createHierarchy(Collection<BoneBinding> binds) {
		// Clear The Current Hierarchy
		for(Bone b : bones.values()) {
			b.children.clear();
			b.parent = null;
		}
		
		// Create The New Hierarchy
		for(BoneBinding b : binds) {
			bones.get(b.parent).addChild(bones.get(b.child));
		}
		
		// Reset The Root Bones
		roots.clear();
		for(Bone b : bones.values()) {
			if(b.parent == null) {
				roots.add(b);
			}
		}
	}

	/**
	 * Create A List Of Bone Transformations In The Skeleton's Space
	 * @return
	 */
	public Matrix4[] buildTransforms() {
		Matrix4[] m = new Matrix4[boneCount];
		for(Bone b : roots) traverse(b, m);
		return m;
	}
	private void traverse(Bone b, Matrix4[] trans) {
		// Create The Local Transformation
		trans[b.index] = b.createTransformation();
		
		// Apply Parent's Transformation For A Full One
		if(b.parent != null) {
			trans[b.index].mulAfter(trans[b.parent.index]);
		}
		
		// Ripple Through The Children
		for(Bone c : b.children) {
			traverse(c, trans);
		}
	}
	
}
