package ray1.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import egl.math.Vector3;
import egl.math.Vector3d;
import ray1.IntersectionRecord;
import ray1.Ray;
import ray1.Scene;
import ray1.surface.Sphere;

public class SceneTests {

    @Test
    public void testAnyIntersection() {
        System.out.println("\nTest getAnyIntersection() method.");
        System.out.println("==============================================");

        // I'm just going to add spheres because they're easier to make.
        Sphere sphere0 = new Sphere();
        Sphere sphere1 = new Sphere();
        sphere1.setCenter(new Vector3(1, 0, 0));

        Scene scene = new Scene();
        scene.addSurface(sphere0);
        scene.addSurface(sphere1);

        Vector3d rayOrigin = new Vector3d();
        Vector3d rayDirection = new Vector3d();

        // Begin tests
        rayOrigin.set(3, 0, 0);
        rayDirection.set(-1, 0, 0);
        Ray ray0 = new Ray(rayOrigin, rayDirection);
        ray0.makeOffsetRay();
        System.out.println("Ray 0: origin = " + ray0.origin + "; dir = " + ray0.direction);
        assertTrue(scene.getAnyIntersection(ray0));

        rayOrigin.set(-3, 0, 0);
        rayDirection.set(1, 0, 0);
        Ray ray1 = new Ray(rayOrigin, rayDirection);
        ray1.makeOffsetRay();
        System.out.println("Ray 1: origin = " + ray1.origin + "; dir = " + ray1.direction);
        assertTrue(scene.getAnyIntersection(ray1));

        rayOrigin.set(5, 0, 0);
        rayDirection.set(0, 1, 0);
        Ray ray2 = new Ray(rayOrigin, rayDirection);
        System.out.println("Ray 2: origin = " + ray2.origin + "; dir = " + ray2.direction);
        assertTrue(!scene.getAnyIntersection(ray2));

        System.out.println("All tests passed.\n");
    }

    @Test
    public void testFirstIntersection() {
        System.out.println("\nTest getFirstIntersection() method.");
        System.out.println("==============================================");

        // I'm just going to add spheres because they're easier to make.
        Sphere sphere0 = new Sphere();
        Sphere sphere1 = new Sphere();
        sphere1.setCenter(new Vector3(1, 0, 0));

        Scene scene = new Scene();
        scene.addSurface(sphere0);
        scene.addSurface(sphere1);

        double expectedT = 0.0;

        IntersectionRecord its = new IntersectionRecord();
        Vector3d rayOrigin = new Vector3d();
        Vector3d rayDirection = new Vector3d();

        rayOrigin.set(3.0, 0.0, 0.0);
        rayDirection.set(-1.0, 0.0, 0.0);
        Ray ray0 = new Ray(rayOrigin, rayDirection);
        ray0.makeOffsetRay();
        System.out.println("Ray 0: origin = " + ray0.origin + "; dir = " + ray0.direction);
        System.out.println("----------------------------------------------");
        assertTrue(scene.getFirstIntersection(its, ray0));
        expectedT = 1.0;
        System.out.println("Testing intersection time.");
        TestUtils.assertDoublesEqual(expectedT, its.t);
        System.out.println("Testing intersection surface");
        assertTrue(its.surface == sphere1);
        System.out.println("Ray 0 tests passed.\n");

        rayOrigin.set(-3.0, 0.0, 0.0);
        rayDirection.set(1.0, 0.0, 0.0);
        Ray ray1 = new Ray(rayOrigin, rayDirection);
        ray1.makeOffsetRay();
        System.out.println("Ray 1: origin = " + ray1.origin + "; dir = " + ray1.direction);
        System.out.println("----------------------------------------------");
        assertTrue(scene.getFirstIntersection(its, ray1));
        expectedT = 2.0;
        System.out.println("Testing intersection time.");
        TestUtils.assertDoublesEqual(expectedT, its.t);
        System.out.println("Testing intersection surface");
        assertTrue(its.surface == sphere0);
        System.out.println("Ray 1 tests passed.\n");
    }

}
