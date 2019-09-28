package ray1.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import egl.math.Vector3;
import egl.math.Vector3d;
import ray1.IntersectionRecord;
import ray1.Ray;
import ray1.surface.Sphere;
import ray1.surface.Surface;

public class SphereTests {

    @Test
    public void testIntersectCanonical() {
        System.out.println("\nTesting unit sphere at centered at origin.");
        System.out.println("==============================================");

        // Setting things up.
        Sphere sphereCanonical = new Sphere();
        IntersectionRecord its = new IntersectionRecord();
        Vector3d rayOrigin = new Vector3d();
        Vector3d rayDirection = new Vector3d();

        Vector3d expectedLocation = new Vector3d();
        Vector3d expectedNormal = new Vector3d();
        double expectedT = 0.0;
        Surface expectedSurface = sphereCanonical;

        rayOrigin.set(0.0, 0.0, -2.0);
        rayDirection.set(0.0, 0.0, 1.0);
        Ray ray0 = new Ray(rayOrigin, rayDirection);
        ray0.makeOffsetRay();
        System.out.println("Ray 0: origin = " + ray0.origin + "; dir = " + ray0.direction);
        System.out.println("----------------------------------------------");
        assertTrue(sphereCanonical.intersect(its, ray0));

        System.out.println("Testing intersection location.");
        expectedLocation.set(0.0, 0.0, -1.0);
        TestUtils.assertVector3dEqual(its.location, expectedLocation);
        System.out.println("Testing intersection normal.");
        expectedNormal.set(0.0, 0.0, -1.0);
        TestUtils.assertVector3dEqual(its.normal, expectedNormal);
        System.out.println("Testing intersection time.");
        expectedT = 1.0;
        TestUtils.assertDoublesEqual(its.t, expectedT);
        System.out.println("Testing intersection surface.");
        assertTrue(its.surface == expectedSurface);
        System.out.println("Ray 0 tests passed.\n");

        rayOrigin.set(2.0, 4.0, 10.0);
        rayDirection.set(-1.0, -2.0, -5.0);
        Ray ray1 = new Ray(rayOrigin, rayDirection.normalize());
        ray1.makeOffsetRay();
        System.out.println("Ray 1: origin = " + ray1.origin + "; dir = " + ray1.direction);
        System.out.println("----------------------------------------------");
        assertTrue(sphereCanonical.intersect(its, ray1));

        System.out.println("Testing intersection location.");
        expectedLocation.set(rayOrigin.clone().normalize());
        TestUtils.assertVector3dEqual(its.location, expectedLocation);
        System.out.println("Testing intersection normal.");
        expectedNormal.set(expectedLocation.normalize());
        TestUtils.assertVector3dEqual(its.normal, expectedNormal);
        System.out.println("Testing intersection time.");
        expectedT = 9.954451150103317;
        TestUtils.assertDoublesEqual(its.t, expectedT);
        System.out.println("Testing intersection surface.");
        assertTrue(its.surface == expectedSurface);
        System.out.println("Ray 1 tests passed.\n");
    }

    @Test
    public void testIntersectTimes() {
        // Setting things up.
        Sphere sphereCanonical = new Sphere();
        IntersectionRecord its = new IntersectionRecord();
        Vector3d rayOrigin = new Vector3d();
        Vector3d rayDirection = new Vector3d();

        Vector3d expectedLocation = new Vector3d();
        Vector3d expectedNormal = new Vector3d();
        double expectedT = 0.0;
        Surface expectedSurface = sphereCanonical;

        rayOrigin.set(2.0, 4.0, 10.0);
        rayDirection.set(-1.0, -2.0, -5.0);
        Ray ray1 = new Ray(rayOrigin, rayDirection.normalize());
        ray1.makeOffsetRay();

        System.out.println("\nTesting if intersect returns true iff ray.start < its.t < ray.end");
        System.out.println("===================================================================");

        System.out.println("Testing if ray.start > its.t.");
        ray1.start = 12.0;
        assertTrue(!sphereCanonical.intersect(its, ray1));

        System.out.println("Testing if ray.end < its.t.");
        ray1.start = 1E-6;
        ray1.end = 9.5;
        assertTrue(!sphereCanonical.intersect(its, ray1));

        System.out.println("Testing if ray begins inside of the sphere:");
        ray1.start = 10.0;
        ray1.end = Double.POSITIVE_INFINITY;
        assertTrue(sphereCanonical.intersect(its, ray1));
        System.out.println("Testing intersection location.");
        expectedLocation.set(-0.1825741858350569, -0.3651483716701138, -0.9128709291752841);
        System.out.println("location: " + its.location);
        TestUtils.assertVector3dEqual(its.location, expectedLocation);
        System.out.println("Testing intersection normal.");
        expectedNormal.set(-0.18257418583505544, -0.3651483716701109, -0.9128709291752768);
        TestUtils.assertVector3dEqual(its.normal, expectedNormal);
        System.out.println("Testing intersection time.");
        expectedT = 11.954451150103331;
        TestUtils.assertDoublesEqual(its.t, expectedT);
        System.out.println("Testing intersection surface.");
        assertTrue(its.surface == expectedSurface);
        System.out.println("Intersection tests passed.\n");
    }

    @Test
    public void testIntersect() {
        System.out.println("\nTesting general sphere.");
        System.out.println("==============================================");

        // Setting things up.
        Sphere sphere = new Sphere();
        sphere.setCenter(new Vector3(0.0f, 5.0f, 0.0f));
        sphere.setRadius(2.0f);
        IntersectionRecord its = new IntersectionRecord();
        Vector3d rayOrigin = new Vector3d();
        Vector3d rayDirection = new Vector3d();

        Vector3d expectedLocation = new Vector3d();
        Vector3d expectedNormal = new Vector3d();
        double expectedT = 0.0;
        Surface expectedSurface = sphere;

        rayOrigin.set(0.0, 5.0, -3.0);
        rayDirection.set(0.0, 0.0, 1.0);
        Ray ray0 = new Ray(rayOrigin, rayDirection);
        ray0.makeOffsetRay();
        System.out.println("Ray 0: origin = " + ray0.origin + "; dir = " + ray0.direction);
        System.out.println("----------------------------------------------");
        assertTrue(sphere.intersect(its, ray0));

        System.out.println("Testing intersection location.");
        expectedLocation.set(0.0, 5.0, -2.0);
        TestUtils.assertVector3dEqual(its.location, expectedLocation);
        System.out.println("Testing intersection normal.");
        expectedNormal.set(0.0, 0.0, -1.0);
        TestUtils.assertVector3dEqual(its.normal, expectedNormal);
        System.out.println("Testing intersection time.");
        expectedT = 1.0;
        TestUtils.assertDoublesEqual(its.t, expectedT);
        System.out.println("Testing intersection surface.");
        assertTrue(its.surface == expectedSurface);
        System.out.println("Ray 0 tests passed.\n");

        rayOrigin.set(2.0, 10.0, 10.0);
        rayDirection.set(-1.0, -2.0, -5.0);
        Ray ray1 = new Ray(rayOrigin, rayDirection.normalize());
        ray1.makeOffsetRay();
        System.out.println("Ray 1: origin = " + ray1.origin + "; dir = " + ray1.direction);
        System.out.println("----------------------------------------------");
        assertTrue(sphere.intersect(its, ray1));

        System.out.println("Testing intersection location.");
        expectedLocation.set(0.2565119904944235, 6.513023980988847, 1.282559952472118);
        TestUtils.assertVector3dEqual(its.location, expectedLocation);
        System.out.println("Testing intersection normal.");
        expectedNormal.set(0.12825599524721126, 0.7565119904944205, 0.6412799762360565);
        TestUtils.assertVector3dEqual(its.normal, expectedNormal);
        System.out.println("Testing intersection time.");
        expectedT = 9.549477115459858;
        TestUtils.assertDoublesEqual(its.t, expectedT);
        System.out.println("Testing intersection surface.");
        assertTrue(its.surface == expectedSurface);
        System.out.println("Ray 1 tests passed.\n");
    }

}
