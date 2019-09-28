package ray1.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import egl.math.Vector3;
import egl.math.Vector3d;
import ray1.IntersectionRecord;
import ray1.OBJFace;
import ray1.OBJMesh;
import ray1.Ray;
import ray1.surface.Mesh;
import ray1.surface.Surface;
import ray1.surface.Triangle;

public class TriangleTests {

    @Test
    public void testIntersectNoNormals() {
        System.out.println("\nTesting triangle intersection: owner does not have normals.");
        System.out.println("==============================================");

        // Setup: creating a triangle
        OBJMesh noNormalsMeshData = new OBJMesh();
        noNormalsMeshData.positions.add(new Vector3(-5, -3, 2));
        noNormalsMeshData.positions.add(new Vector3(1, -1, 0));
        noNormalsMeshData.positions.add(new Vector3(3, 8, 6));
        OBJFace face = new OBJFace(3, false, false);
        face.positions[0] = 0;
        face.positions[1] = 1;
        face.positions[2] = 2;
        noNormalsMeshData.faces.add(face);
        Mesh noNormals = new Mesh(noNormalsMeshData);
        Triangle noNormalsTri = new Triangle(noNormals, face, noNormals.getShader());

        IntersectionRecord its = new IntersectionRecord();
        Vector3d rayOrigin = new Vector3d();
        Vector3d rayDirection = new Vector3d();

        Vector3d expectedLocation = new Vector3d();
        Vector3d expectedNormal = new Vector3d();
        double expectedT = 0.0;
        Surface expectedSurface = noNormalsTri;

        // Begin testing
        rayOrigin.set(0, 0, -1);
        rayDirection.set(0, 0, 1);
        Ray ray0 = new Ray(rayOrigin, rayDirection);
        ray0.makeOffsetRay();
        System.out.println("Ray 0: origin = " + ray0.origin + "; dir = " + ray0.direction);
        System.out.println("----------------------------------------------");
        assertTrue(noNormalsTri.intersect(its, ray0));

        System.out.println("Testing intersection location.");
        expectedLocation.set(0.0, 0.0, 1.4);
        TestUtils.assertVector3dEqual(its.location, expectedLocation);
        System.out.println("Testing intersection normal.");
        expectedNormal.set(0.4242640687119285, -0.565685424949238, 0.7071067811865476);
        TestUtils.assertVector3dEqual(its.normal, expectedNormal);
        System.out.println("Testing intersection time.");
        expectedT = 2.4;
        TestUtils.assertDoublesEqual(its.t, expectedT);
        System.out.println("Testing intersection surface.");
        assertTrue(its.surface == expectedSurface);
        System.out.println("Ray 0 tests passed.\n");

        rayOrigin.set(3.5, -1.3, -1.0);
        rayDirection.set(-3.5, 1.0, 2.4);
        rayDirection.normalize();
        Ray ray1 = new Ray(rayOrigin, rayDirection);
        ray1.makeOffsetRay();
        System.out.println("Ray 1: origin = " + ray1.origin + "; dir = " + ray1.direction);
        System.out.println("----------------------------------------------");
        assertTrue(noNormalsTri.intersect(its, ray1));

        System.out.println("Testing intersection location.");
        expectedLocation.set(-1.6799999999999953, 0.17999999999999883, 2.551999999999997);
        TestUtils.assertVector3dEqual(its.location, expectedLocation);
        System.out.println("Testing intersection normal.");
        expectedNormal.set(0.4242640687119285, -0.565685424949238, 0.7071067811865476);
        TestUtils.assertVector3dEqual(its.normal, expectedNormal);
        System.out.println("Testing intersection time.");
        expectedT = 6.45286788955112;
        TestUtils.assertDoublesEqual(its.t, expectedT);
        System.out.println("Testing intersection surface.");
        assertTrue(its.surface == expectedSurface);
        System.out.println("Ray 1 tests passed.\n");

        rayOrigin.set(3.5, -1.0, -1.0);
        rayDirection.set(-3.5, 1.0, 1.0);
        rayDirection.normalize();
        Ray ray2 = new Ray(rayOrigin, rayDirection);
        ray2.makeOffsetRay();
        System.out.println("Ray 2: origin = " + ray2.origin + "; dir = " + ray2.direction);
        System.out.println("----------------------------------------------");
        assertTrue(!noNormalsTri.intersect(its, ray2));
        System.out.println("Ray 2 tests passed.\n");
    }

    @Test
    public void testIntersectNormals() {
        System.out.println("\nTesting triangle intersection: owner has normals.");
        System.out.println("==============================================");

        // Setup: creating a triangle
        OBJMesh meshData = new OBJMesh();
        meshData.positions.add(new Vector3(-5, -3, 2));
        meshData.positions.add(new Vector3(1, -1, 0));
        meshData.positions.add(new Vector3(3, 8, 6));
        meshData.normals.add(new Vector3(1, 0, 0));
        meshData.normals.add(new Vector3(0, 1, 0));
        meshData.normals.add(new Vector3(0, 0, 1));
        OBJFace face = new OBJFace(3, false, true);
        face.positions[0] = 0;
        face.positions[1] = 1;
        face.positions[2] = 2;
        face.normals[0] = 0;
        face.normals[1] = 1;
        face.normals[2] = 2;
        meshData.faces.add(face);
        Mesh mesh = new Mesh(meshData);
        Triangle tri = new Triangle(mesh, face, mesh.getShader());

        IntersectionRecord its = new IntersectionRecord();
        Vector3d rayOrigin = new Vector3d();
        Vector3d rayDirection = new Vector3d();

        Vector3d expectedLocation = new Vector3d();
        Vector3d expectedNormal = new Vector3d();
        double expectedT = 0.0;
        Surface expectedSurface = tri;

        // Begin testing
        rayOrigin.set(0, 0, -1);
        rayDirection.set(0, 0, 1);
        Ray ray0 = new Ray(rayOrigin, rayDirection);
        ray0.makeOffsetRay();
        System.out.println("Ray 0: origin = " + ray0.origin + "; dir = " + ray0.direction);
        System.out.println("----------------------------------------------");
        assertTrue(tri.intersect(its, ray0));

        System.out.println("Testing intersection location.");
        expectedLocation.set(0.0, 0.0, 1.4);
        TestUtils.assertVector3dEqual(its.location, expectedLocation);
        System.out.println("Testing intersection normal.");
        expectedNormal.set(0.32493790519706517, 0.9157340964644564, 0.23631847650695648);
        TestUtils.assertVector3dEqual(its.normal, expectedNormal);
        System.out.println("Testing intersection time.");
        expectedT = 2.4;
        TestUtils.assertDoublesEqual(its.t, expectedT);
        System.out.println("Testing intersection surface.");
        assertTrue(its.surface == expectedSurface);
        System.out.println("Ray 0 tests passed.\n");

        rayOrigin.set(3.5, -1.3, -1.0);
        rayDirection.set(-3.5, 1.0, 2.4);
        rayDirection.normalize();
        Ray ray1 = new Ray(rayOrigin, rayDirection);
        ray1.makeOffsetRay();
        System.out.println("Ray 1: origin = " + ray1.origin + "; dir = " + ray1.direction);
        System.out.println("----------------------------------------------");
        assertTrue(tri.intersect(its, ray1));

        System.out.println("Testing intersection location.");
        expectedLocation.set(-1.6799999999999953, 0.17999999999999883, 2.551999999999997);
        TestUtils.assertVector3dEqual(its.location, expectedLocation);
        System.out.println("Testing intersection normal.");
        expectedNormal.set(0.8464296715703657, 0.3541707236027082, 0.397642942384266);
        TestUtils.assertVector3dEqual(its.normal, expectedNormal);
        System.out.println("Testing intersection time.");
        expectedT = 6.45286788955112;
        TestUtils.assertDoublesEqual(its.t, expectedT);
        System.out.println("Testing intersection surface.");
        assertTrue(its.surface == expectedSurface);
        System.out.println("Ray 1 tests passed.\n");
    }

    @Test
    public void testIntersectTimes() {
    	OBJMesh noNormalsMeshData = new OBJMesh();
        noNormalsMeshData.positions.add(new Vector3(-5, -3, 2));
        noNormalsMeshData.positions.add(new Vector3(1, -1, 0));
        noNormalsMeshData.positions.add(new Vector3(3, 8, 6));
        OBJFace face = new OBJFace(3, false, false);
        face.positions[0] = 0;
        face.positions[1] = 1;
        face.positions[2] = 2;
        noNormalsMeshData.faces.add(face);
        Mesh noNormals = new Mesh(noNormalsMeshData);
        Triangle noNormalsTri = new Triangle(noNormals, face, noNormals.getShader());

        IntersectionRecord its = new IntersectionRecord();
        Vector3d rayOrigin = new Vector3d();
        Vector3d rayDirection = new Vector3d();

        rayOrigin.set(0, 0, -1);
        rayDirection.set(0, 0, 1);
        Ray ray0 = new Ray(rayOrigin, rayDirection);
        ray0.makeOffsetRay();

        System.out.println("\nTesting if intersect returns true iff ray.start < its.t < ray.end");
        System.out.println("===================================================================");

        System.out.println("Testing if ray.start > its.t.");
        ray0.start = 3.0;
        assertTrue(!noNormalsTri.intersect(its, ray0));

        System.out.println("Testing if ray.end < its.t.");
        ray0.start = 1E-6;
        ray0.end = 2.0;
        assertTrue(!noNormalsTri.intersect(its, ray0));

        System.out.println("All tests passed.\n");
    }

}
