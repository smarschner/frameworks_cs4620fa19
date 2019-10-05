package ray1.accel;

import java.util.Arrays;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import ray1.OBJMesh;
import ray1.OBJFace;
import ray1.Ray;
import ray1.surface.Mesh;
import ray1.surface.Sphere;
import ray1.surface.Surface;
import ray1.surface.Triangle;
import egl.math.Matrix4d;
import egl.math.Vector3d;
import egl.math.Vector3;

public class BvhTests {
    static Surface surfaces1[]  = new Surface[1];
    static Surface surfaces20[] = new Surface[20];
    static Surface surfaces40[] = new Surface[40];
    
    static Bvh bvh1 = new Bvh();
    static Bvh bvh20 = new Bvh();
    static Bvh bvh40 = new Bvh();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Sphere s = new Sphere();
//        s.setTransformation(new Matrix4d(), new Matrix4d(), new Matrix4d());
        s.computeBoundingBox();
        surfaces1[0] = s; surfaces20[0] = s; surfaces40[0] = s;
        
        for(int i = 1; i < 20; ++i) {
            Sphere si = new Sphere();
            si.setCenter(new Vector3(0, i, 0));
            si.computeBoundingBox();
//            si.setTransformation(new Matrix4d(), new Matrix4d(), new Matrix4d());
            surfaces20[i] = si;
        }
        
        for(int i = 1; i < 40; ++i) {
            Sphere si = new Sphere();
            si.setCenter(new Vector3(30 * (i % 2), i / 2, 0));
            si.computeBoundingBox();
//            si.setTransformation(new Matrix4d(), new Matrix4d(), new Matrix4d());
            surfaces40[i] = si;
        }
        
        bvh1.build(Arrays.copyOf(surfaces1, 1));
        bvh20.build(Arrays.copyOf(surfaces20, 20));
        bvh40.build(Arrays.copyOf(surfaces40, 40));
    }
    
    @Test
    public static void testComputeBoundingBox() {
        // Sphere Bound and Triangle Bound
        //================================================================================================
       
        // Sphere
        Sphere s = new Sphere();
        s.computeBoundingBox();
        
        if (vectorsEqual(s.getMinBound(), new Vector3d(-1, -1, -1)) &&
                vectorsEqual(s.getMaxBound(), new Vector3d(1, 1, 1)))
        	System.out.print("Succeed: Sphere 1 Bounding Box. \n");
        else System.out.print(" Failed: Sphere 1 Bounding Box: \n"
            		+ "bbox should be:\n"
                    + "min <-1, -1, -1>\n"
                    + "max <1, 1, 1>\n"
                    + "got:\n"
                    + "min " + s.getMinBound() + "\n"
                    + "max " + s.getMaxBound() + "\n");
        
        if (vectorsEqual(s.getAveragePosition(), new Vector3d()))
        	System.out.print("Succeed: Sphere 1 Average Position. \n");
        else System.out.print("Failed: Sphere 1 Average Position: \n"
        			+ "avg position should be:\n"
        			+ "<0, 0, 0>\n"
        			+ "got " + s.getAveragePosition()
        		);
      
        Sphere s1 = new Sphere();
        s1.setCenter(new Vector3(4, 4, 4));
        s1.setRadius(5);
        s1.computeBoundingBox();
        
        if (vectorsEqual(s1.getMinBound(), new Vector3d(-1, -1, -1)) &&
                vectorsEqual(s1.getMaxBound(), new Vector3d(9, 9, 9)))
        	System.out.print("Succeed: Sphere 2 Bounding Box. \n");
        else System.out.print(" Failed: Sphere 2 Bounding Box: \n"
            		+ "bbox should be:\n"
                    + "min <-1, -1, -1>\n"
                    + "max <9, 9, 9>\n"
                    + "got:\n"
                    + "min " + s1.getMinBound() + "\n"
                    + "max " + s1.getMaxBound() + "\n");
        
        if (vectorsEqual(s1.getAveragePosition(), new Vector3d(4)))
        	System.out.print("Succeed: Sphere 2 Average Position. \n");
        else System.out.print("Failed: Sphere 1 Average Position: \n"
        			+ "avg position should be:\n"
        			+ "<4, 4, 4>\n"
        			+ "got " + s1.getAveragePosition()
        		);
        
        
        // Triangle
        OBJMesh md = new OBJMesh();

        md.positions.add(new Vector3(-1f,-1f,0f));
        md.positions.add(new Vector3(1f,-1f,0f));
        md.positions.add(new Vector3(0f,1f,-1f));
        
        OBJFace tri1 = new OBJFace(3,true,true);
        tri1.setVertex(0, 0, 0, 0);
        tri1.setVertex(1, 1, 0, 0);
        tri1.setVertex(2, 2, 0, 0);
        
        md.faces.add(tri1);
        Triangle t = new Triangle(new Mesh(md), tri1, null);
        t.computeBoundingBox();
        
        if (vectorsEqual(t.getMinBound(), new Vector3d(-1, -1, -1)) &&
                vectorsEqual(t.getMaxBound(), new Vector3d(1, 1, 0))) 
        	System.out.print("Succeed: Triangle 1 Bounding Box.\n");
        else System.out.print("Failed: Triangle Bounding Box should be: \n"
       		 + "verts:\n"
             + "<-1, -1, 0>,\n"
             + "<1, -1, 0>, \n"
             + "<0, 1, -1>.\n"
             + "bbox should be:\n"
             + "min <-1, -1, -1>\n"
             + "max <1, 1, 0>\n"
             + "got:\n"
             + "min " + t.getMinBound() + "\n"
             + "max " + t.getMaxBound() + "\n");
        
        if (vectorsEqual(t.getAveragePosition(), new Vector3d(0, -1.0/3, -1.0/3)))
        	System.out.print("Succeed: Triangle 1 Average Possition.\n");
        else System.out.print("Failed: Triangle 1 Average Position:\n"
                + "verts:\n"
                + "<-1, -1, 0>,\n"
                + "<1, -1, 0>, \n"
                + "<0, 1, -1>.\n"
                + "avg position should be:\n"
                + "<0, -1/3, -1/3>\n"
                + "got " + t.getAveragePosition());

        OBJMesh md2 = new OBJMesh();

        md2.positions.add(new Vector3(-2f,-1f,0f));
        md2.positions.add(new Vector3(3f,-1f,0f));
        md2.positions.add(new Vector3(0f,1f,-4f));
        
        OBJFace tri2 = new OBJFace(3,true,true);
        tri2.setVertex(0, 0, 0, 0);
        tri2.setVertex(1, 1, 0, 0);
        tri2.setVertex(2, 2, 0, 0);
        
        md2.faces.add(tri2);
        Triangle t2 = new Triangle(new Mesh(md2), tri2, null);
        t2.computeBoundingBox();
        
        if (vectorsEqual(t2.getMinBound(), new Vector3d(-2, -1, -4)) &&
                vectorsEqual(t2.getMaxBound(), new Vector3d(3, 1, 0))) 
        	System.out.print("Succeed: Triangle 2 Bounding Box.\n");
        else System.out.print("Failed: Triangle 2 Bounding Box should be: \n"
             + "bbox should be:\n"
             + "min <-2, -1, -4>\n"
             + "max <3, 1, 0>\n"
             + "got:\n"
             + "min " + t2.getMinBound() + "\n"
             + "max " + t2.getMaxBound() + "\n");
        
        if (vectorsEqual(t2.getAveragePosition(), new Vector3d(1.0/3, -1.0/3, -4.0/3)))
        	System.out.print("Succeed: Triangle 2 Average Possition.\n");
        else System.out.print("Failed: Triangle 2 Average Position:\n"
                + "verts:\n"
                + "<-2, -1, 0>,\n"
                + "<3, -1, 0>, \n"
                + "<0, 1, -4>.\n"
                + "avg position should be:\n"
                + "<1/3, -1/3, -4/3>\n"
                + "got " + t2.getAveragePosition());
    }

    @Test
    public static void testBvhNodeIntersection() {
        BvhNode node = new BvhNode(new Vector3d(0, 0, 0), new Vector3d(1, 1, 1), null, null, 0, 0);
        Ray ray = new Ray(new Vector3d(-1, -1, -1), new Vector3d(1, 1, 1));
        ray.makeOffsetRay();
        
        assertTrue("Standard bbox failed.\n"
                + "\nRay: \norigin <-1, -1, -1> \ndir <1, 1, 1> \n"
                + "failed to intersect with "
                + "bbox: \nmin<0, 0, 0> \nmax<1, 1, 1>.", node.intersects(ray));
        
        ray.origin.set(new Vector3d(0, 0, -1));
        ray.direction.set(new Vector3d(1, 0, 0));
        assertTrue("Standard bbox failed.\n"
                + "Ray: \norigin <0, 0, -1> \ndir <1, 0, 0> \n"
                + "intersected with "
                + "bbox: \nmin<0, 0, 0> \nmax<1, 1, 1>.", !node.intersects(ray));
        
        node = new BvhNode(new Vector3d(5, 5, 5), new Vector3d(6, 6, 6), null, null, 0, 0);
        ray.origin.set(new Vector3d(-1, -1, -1));
        ray.direction.set(new Vector3d(1, 1, 1));
        assertTrue("Off-center bbox failed.\n"
                + "Ray: \norigin <-1, -1, -1> \ndir <1, 1, 1> \n"
                + "failed to intersect with "
                + "bbox: \nmin<5, 5, 5> \nmax<6, 6, 6>.", node.intersects(ray));
        
        ray.origin.set(new Vector3d(5, 4.5, 5));
        ray.direction.set(new Vector3d(1, 0, 0));
        assertTrue("Off-center bbox failed.\n"
                + "Ray: \norigin <5, 4.5, 5> \ndir <1, 0, 0> \n"
                + "intersected with "
                + "bbox: \nmin<5, 5, 5> \nmax<6, 6, 6>.", !node.intersects(ray));
        
        node = new BvhNode(new Vector3d(-6, 1, 0), new Vector3d(1, 2, 2), null, null, 0, 0);
        ray.origin.set(new Vector3d(3, 3, 3));
        ray.direction.set(new Vector3d(-3, -2, -2));
        assertTrue("Scaled + offcenter bbox failed.\n"
                + "Ray: \norigin <3, 3, 3> \ndir <-3, -2, -2> \n"
                + "failed to intersect with "
                + "bbox: \nmin<-6, 1, 0> \nmax<1, 2, 2>.", node.intersects(ray));
        
        ray.origin.set(new Vector3d(1, 2, 3));
        ray.direction.set(new Vector3d(0, -1, 0));
        assertTrue("Scaled + offcenter bbox failed.\n"
                + "Ray: \norigin <1, 2, 3> \ndir <0, -1, 0> \n"
                + "intersected with "
                + "bbox: \nmin<-6, 1, 0> \nmax<1, 2, 2>.", !node.intersects(ray));
    }
    
    @Test
    /**
     * This test will only work if the student has decided that they should have at most 10
     * surfaces on a leaf node.
     */
    public static void testBvhTreeCreation() throws Exception {
    	setUpBeforeClass();
        // Testing bvh with 1 element
        //================================================================================================
        assertTrue("A bvh tree with one element should only contain a leaf node.", bvh1.root.isLeaf());
        assertTrue("Bvh1 does not contain the correct element.", 
                    vectorsEqual(bvh1.root.maxBound, surfaces1[0].getMaxBound()) &&
                    vectorsEqual(bvh1.root.minBound, surfaces1[0].getMinBound()));
        
        
        // Testing bvh with 20 elements
        //================================================================================================
        assertTrue("The root of bvh tree with 20 elements should not be a leaf node.", !bvh20.root.isLeaf());
        assertTrue("Bvh20's left child should be a leaf node.", bvh20.root.child[0].isLeaf());
        assertTrue("Bvh20's right child should be a leaf node.", bvh20.root.child[1].isLeaf());
        
        assertTrue("Bvh20's left child should contain 10 elements.", 
                   bvh20.root.child[0].surfaceIndexEnd - bvh20.root.child[0].surfaceIndexStart == 10);
        assertTrue("Bvh20's right child should contain 10 elements.", 
                   bvh20.root.child[1].surfaceIndexEnd - bvh20.root.child[1].surfaceIndexStart == 10);
        
        assertTrue("Minbound of bvh20 should be <-1, -1, -1>.", 
                   vectorsEqual(bvh20.root.minBound, new Vector3d(-1, -1, -1)));
        assertTrue("Maxbound of bvh20 should be <1, 20, 1>.",
                   vectorsEqual(bvh20.root.maxBound, new Vector3d(1, 20, 1)));
        
        assertTrue("One child of bvh20 should have a minbound <-1, 9, -1>.", 
                   (vectorsEqual(bvh20.root.child[0].minBound, new Vector3d(-1, 9, -1))  &&
                   !vectorsEqual(bvh20.root.child[1].minBound, new Vector3d(-1, 9, -1))) ||
                   (vectorsEqual(bvh20.root.child[1].minBound, new Vector3d(-1, 9, -1))  &&
                   !vectorsEqual(bvh20.root.child[0].minBound, new Vector3d(-1, 9, -1))));
        
        assertTrue("One child of bvh20 should have a maxbound <1, 20, 1>.",
                    (vectorsEqual(bvh20.root.child[0].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh20.root.child[1].maxBound, new Vector3d(1, 20, 1))) ||
                    (vectorsEqual(bvh20.root.child[1].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh20.root.child[0].maxBound, new Vector3d(1, 20, 1))));
        
        assertTrue("One child of bvh20 should have a minbound <-1, -1, -1>.", 
                    (vectorsEqual(bvh20.root.child[0].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh20.root.child[1].minBound, new Vector3d(-1, -1, -1))) ||
                    (vectorsEqual(bvh20.root.child[1].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh20.root.child[0].minBound, new Vector3d(-1, -1, -1))));
     
        assertTrue("One child of bvh20 should have a maxbound <1, 10, 1>.",
                    (vectorsEqual(bvh20.root.child[0].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh20.root.child[1].maxBound, new Vector3d(1, 10, 1))) ||
                    (vectorsEqual(bvh20.root.child[1].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh20.root.child[0].maxBound, new Vector3d(1, 10, 1))));
        
        
        // Testing bvh with 40 elements
        //================================================================================================
        assertTrue("The root of bvh tree with 40 elements should not be a leaf node.", !bvh40.root.isLeaf());
        assertTrue("Bvh40's left child should not be a leaf node.", !bvh40.root.child[0].isLeaf());
        assertTrue("Bvh40's right child should not be a leaf node.", !bvh40.root.child[1].isLeaf());
        assertTrue("Bvh40's 4 grandchildren should be leaf nodes.", 
                    bvh40.root.child[0].child[0].isLeaf() &&
                    bvh40.root.child[0].child[1].isLeaf() &&
                    bvh40.root.child[1].child[0].isLeaf() &&
                    bvh40.root.child[1].child[1].isLeaf());
        
        assertTrue("Bvh40's left child should contain 20 elements.", 
                   bvh40.root.child[0].surfaceIndexEnd - bvh40.root.child[0].surfaceIndexStart == 20);
        assertTrue("Bvh40's right child should contain 20 elements.", 
                   bvh40.root.child[1].surfaceIndexEnd - bvh40.root.child[1].surfaceIndexStart == 20);
        assertTrue("Bvh40's 4 grandchildren should all contain 10 elements.",
                    bvh40.root.child[0].child[0].surfaceIndexEnd - bvh40.root.child[0].child[0].surfaceIndexStart == 10 &&
                    bvh40.root.child[0].child[1].surfaceIndexEnd - bvh40.root.child[0].child[1].surfaceIndexStart == 10 &&
                    bvh40.root.child[1].child[0].surfaceIndexEnd - bvh40.root.child[1].child[0].surfaceIndexStart == 10 &&
                    bvh40.root.child[1].child[1].surfaceIndexEnd - bvh40.root.child[1].child[1].surfaceIndexStart == 10);
        
        assertTrue("Minbound of bvh40 should be <-1, -1, -1>.", 
                   vectorsEqual(bvh40.root.minBound, new Vector3d(-1, -1, -1)));
        assertTrue("Maxbound of bvh40 should be <31, 20, 1>.",
                   vectorsEqual(bvh40.root.maxBound, new Vector3d(31, 20, 1)));
        
        assertTrue("One child of bvh40 should have a minbound <29, -1, -1>.", 
                    (vectorsEqual(bvh40.root.child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].minBound, new Vector3d(29, -1, -1))) ||
                    (vectorsEqual(bvh40.root.child[1].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].minBound, new Vector3d(29, -1, -1))));

        assertTrue("One child of bvh40 should have a maxbound <31, 20, 1>.",
                   (vectorsEqual(bvh40.root.child[0].maxBound, new Vector3d(31, 20, 1))  &&
                   !vectorsEqual(bvh40.root.child[1].maxBound, new Vector3d(31, 20, 1))) ||
                   (vectorsEqual(bvh40.root.child[1].maxBound, new Vector3d(31, 20, 1))  &&
                   !vectorsEqual(bvh40.root.child[0].maxBound, new Vector3d(31, 20, 1))));

        assertTrue("One child of bvh40 should have a minbound <-1, -1, -1>.", 
                   (vectorsEqual(bvh40.root.child[0].minBound, new Vector3d(-1, -1, -1))  &&
                   !vectorsEqual(bvh40.root.child[1].minBound, new Vector3d(-1, -1, -1))) ||
                   (vectorsEqual(bvh40.root.child[1].minBound, new Vector3d(-1, -1, -1))  &&
                   !vectorsEqual(bvh40.root.child[0].minBound, new Vector3d(-1, -1, -1))));

        assertTrue("One child of bvh40 should have a maxbound <1, 20, 1>.",
                   (vectorsEqual(bvh40.root.child[0].maxBound, new Vector3d(1, 20, 1))  &&
                   !vectorsEqual(bvh40.root.child[1].maxBound, new Vector3d(1, 20, 1))) ||
                   (vectorsEqual(bvh40.root.child[1].maxBound, new Vector3d(1, 20, 1))  &&
                   !vectorsEqual(bvh40.root.child[0].maxBound, new Vector3d(1, 20, 1))));
        
        assertTrue("One grandchild of bvh40 should have a minbound <-1, -1, -1>.",
                    (vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(-1, -1, -1))) ||
                    
                    (vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(-1, -1, -1))) ||
                    
                    (vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(-1, -1, -1))) ||
                    
                    (vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(-1, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(-1, -1, -1))));
        
        assertTrue("One grandchild of bvh40 should have a maxBound <1, 10, 1>.",
                    (vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(1, 10, 1))) ||
                
                    (vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(1, 10, 1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(1, 10, 1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(1, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(1, 10, 1))));
        
        assertTrue("One grandchild of bvh40 should have a minbound <29, -1, -1>.",
                    (vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(29, -1, -1))) ||
                
                    (vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(29, -1, -1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(29, -1, -1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(29, -1, -1))));
        
        assertTrue("One grandchild of bvh40 should have a maxbound <31, 10, 1>.",
                    (vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(31, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(31, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(31, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(31, 10, 1))) ||
                
                    (vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(31, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(31, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(31, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(31, 10, 1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(31, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(31, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(31, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(31, 10, 1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(31, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(31, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(31, 10, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(31, 10, 1))));
        
        assertTrue("One grandchild of bvh40 should have a minbound <-1, 9, -1>.",
                    (vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(-1, 9, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(-1, 9, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(-1, 9, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(-1, 9, -1))) ||
                
                    (vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(-1, 9, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(-1, 9, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(-1, 9, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(-1, 9, -1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(-1, 9, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(-1, 9, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(-1, 9, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(-1, 9, -1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(-1, 9, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(-1, 9, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(-1, 9, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(-1, 9, -1))));
        
        assertTrue("One grandchild of bvh40 should have a maxbound <1, 20, 1>.",
                    (vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(1, 20, 1))) ||
                
                    (vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(1, 20, 1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(1, 20, 1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(1, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(1, 20, 1))));
        
        assertTrue("One grandchild of bvh40 should have a minbound <29, -1, -1>.",
                    (vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(29, -1, -1))) ||
                
                    (vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(29, -1, -1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(29, -1, -1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[1].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].minBound, new Vector3d(29, -1, -1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].minBound, new Vector3d(29, -1, -1))));
        
        assertTrue("One grandchild of bvh40 should have a maxbound <31, 20, 1>.",
                    (vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(31, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(31, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(31, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(31, 20, 1))) ||
                
                    (vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(31, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(31, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(31, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(31, 20, 1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(31, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(31, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(31, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(31, 20, 1))) ||
                
                    (vectorsEqual(bvh40.root.child[1].child[1].maxBound, new Vector3d(31, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[1].maxBound, new Vector3d(31, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[1].child[0].maxBound, new Vector3d(31, 20, 1))  &&
                    !vectorsEqual(bvh40.root.child[0].child[0].maxBound, new Vector3d(31, 20, 1))));
    }
    
    // Simple element-wise comparison.
    private static boolean vectorsEqual(Vector3d v0, Vector3d v1) {
        double epsilon = 1e-4;
        return (Math.abs(v0.x - v1.x) < epsilon &&
                Math.abs(v0.y - v1.y) < epsilon && 
                Math.abs(v0.z - v1.z) < epsilon);
    }
    
    public static void main(String args[]) throws Exception {
    	System.out.print("Bounding Box Computation Test:\n");
    	testComputeBoundingBox();
    	System.out.print("Bounding Box Intersection Test:\n");
    	testBvhNodeIntersection();
    	System.out.print("Build Tree Test: \n");
    	testBvhTreeCreation();
    }

}
