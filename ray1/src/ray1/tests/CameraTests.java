package ray1.tests;

import org.junit.Test;

import egl.math.Vector3;
import egl.math.Vector3d;
import ray1.Ray;
import ray1.camera.OrthographicCamera;
import ray1.camera.PerspectiveCamera;


public class CameraTests {

    @Test
    public void testCameraGetRay() {
        System.out.println("\nTesting camera getRay method");
        System.out.println("=====================================");
        Vector3 viewPoint = new Vector3(1f, 0.5f, 2f);
        Vector3 viewDir = new Vector3(15.23f, -1.854f, 65.221f);
        viewDir.normalize();
        Vector3 viewUp = new Vector3(1, 0, 0);

        OrthographicCamera orthoCam = new OrthographicCamera();
        orthoCam.setViewPoint(viewPoint);
        orthoCam.setViewDir(viewDir);
        orthoCam.setViewUp(viewUp);
        orthoCam.init();

        PerspectiveCamera perspectiveCam = new PerspectiveCamera();
        perspectiveCam.setViewPoint(viewPoint);
        perspectiveCam.setViewDir(viewDir);
        perspectiveCam.setViewUp(viewUp);
        perspectiveCam.init();

        float u = 0.37123f;
        float v = 0.11343f;
        Ray correctRay0 = new Ray(new Vector3d(0.6235493799051484, 0.36878515466141304, 2.084176425089877),
                                  new Vector3d(0.22730915261287413, -0.027671120744863338, 0.9734294315537928));
        Ray resultRay0 = new Ray();
        orthoCam.getRay(resultRay0, u, v);
        TestUtils.assertRaysEqual("Testing Orthographic camera: u = " + u + ", v = " + v, correctRay0, resultRay0);

        Ray correctRay1 = new Ray(new Vector3d(1.0, 0.5, 2.0),
                                  new Vector3d(-0.13811656557506483, -0.14714072701594028, 0.9794250460177998));
        Ray resultRay1 = new Ray();
        perspectiveCam.getRay(resultRay1, u, v);
        TestUtils.assertRaysEqual("Testing Perspective camera: u = " + u + ", v = " + v, correctRay1, resultRay1);

        u = 0.00234f;
        v = 0.9832f;
        Ray correctRay2 = new Ray(new Vector3d(1.4705511169015408, 0.00566191538843016, 1.8760675818602917),
                                  new Vector3d(0.22730915248394012, -0.027671121060848236, 0.9734294414520264));
        Ray resultRay2 = new Ray();
        orthoCam.getRay(resultRay2, u, v);
        TestUtils.assertRaysEqual("Testing Orthographic camera: u = " + u + ", v = " + v, correctRay2, resultRay2);

        Ray correctRay3 = new Ray(new Vector3d(1.0, 0.5, 2.0),
                                  new Vector3d(0.5734153111840217, -0.4289226336993767, 0.698011644029039));
        correctRay3.makeOffsetRay();
        Ray resultRay3 = new Ray();
        perspectiveCam.getRay(resultRay3, u, v);
        TestUtils.assertRaysEqual("Testing Perspective camera: u = " + u + ", v = " + v, correctRay3, resultRay3);

        System.out.println("All tests passed!");
    }
}
