package ray1.tests;

import static org.junit.Assert.assertTrue;

import egl.math.Vector3;
import egl.math.Vector3d;
import ray1.Ray;

public class TestUtils {
    public static final double EPSILON_D = 1e-6;

    public static void assertRaysEqual(String message, Ray ray0, Ray ray1) {
        System.out.println(message);
        assertRaysEqual(ray0, ray1);
    }

    public static void assertRaysEqual(Ray ray0, Ray ray1) {
        Vector3d dir0 = new Vector3d(ray0.direction);
        Vector3d dir1 = new Vector3d(ray1.direction);
        assertVector3dEqual(dir0.normalize(), dir1.normalize());
        assertVector3dEqual(ray0.origin, ray1.origin);
    }

    public static void assertVector3dEqual(Vector3d v0, Vector3d v1) {
        assertTrue(v0.equalsApprox(v1, 1e-6));
    }

    public static void assertVector3Equal(Vector3 v0, Vector3 v1) {
    	assertTrue(v0.equalsApprox(v1));
    }

    public static void assertDoublesEqual(double d0, double d1) {
        assertTrue(Math.abs(d0 - d1)< EPSILON_D);
    }
}
