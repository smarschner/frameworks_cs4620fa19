package ray1.tests;

import org.junit.Test;

import egl.math.Colorf;
import egl.math.Vector3;
import ray1.IntersectionRecord;
import ray1.Light;
import ray1.Ray;
import ray1.Scene;
import ray1.camera.OrthographicCamera;
import ray1.shader.Phong;
import ray1.surface.Sphere;

public class PhongTests {

    @Test
    public void testSingleLight() {
        System.out.println("\nTesting Phong shader with a single point source.");
        System.out.println("==============================================");

        // Setting up.
        Phong shader = new Phong();
        shader.setDiffuseColor(new Colorf(0.0f, 0.5f, 0.5f));
        shader.init();
        
        IntersectionRecord its = new IntersectionRecord();
        its.normal.set(0.0, 1.0, 0.0);
        its.location.set(0.0, 1.0, 0.0);

        Scene scene = new Scene();
        Light light0 = new Light();
        light0.setIntensity(new Colorf(100.0f, 100.0f, 100.0f));
        light0.setPosition(new Vector3(5.0f, 5.0f, 5.0f));
        scene.addLight(light0);

        OrthographicCamera cam = new OrthographicCamera();
        scene.setCamera(cam);

        Colorf outIntensity = new Colorf();
        Ray ray = new Ray();
        ray.origin.set(0.0, 2.0, 0.0);
        ray.direction.set(0.0, -1.0, 0.0);
        Colorf expectedIntensity = new Colorf();

        System.out.println("Testing not shadowed, no specular highlight.");
        shader.shade(outIntensity, scene, ray, its, 23);
        expectedIntensity.set(0.64441663f, 0.7631477f, 0.7631477f);
        TestUtils.assertVector3Equal(outIntensity, expectedIntensity);

        System.out.println("Testing not shadowed, with specular highlight");
        light0.setPosition(new Vector3(1.0f, 5.0f, 1.0f));
        shader.shade(outIntensity, scene, ray, its, 23);
        expectedIntensity.set(5.162396f, 5.996022f, 5.996022f);
        TestUtils.assertVector3Equal(outIntensity, expectedIntensity);

        System.out.println("Testing shadowed.");
        Sphere occluder = new Sphere();
        occluder.setCenter(new Vector3(1.0f, 5.0f, 1.0f));
        scene.addSurface(occluder);
        shader.shade(outIntensity, scene, ray, its, 23);
        expectedIntensity.set(0.0f, 0.0f, 0.0f);
        TestUtils.assertVector3Equal(outIntensity, expectedIntensity);

        System.out.println("All tests passed.\n");
    }

    @Test
    public void testMultipleLights() {
        System.out.println("Testing Phong shader with a multiple point sources.");
        System.out.println("==============================================");

        // Setting up.
        Phong shader = new Phong();
        shader.setDiffuseColor(new Colorf(0.0f, 0.5f, 0.5f));
        shader.init();
        
        IntersectionRecord its = new IntersectionRecord();
        its.normal.set(0.0, 1.0, 0.0);
        its.location.set(0.0, 1.0, 0.0);

        Scene scene = new Scene();
        Light light0 = new Light();
        light0.setIntensity(new Colorf(100.0f, 100.0f, 100.0f));
        light0.setPosition(new Vector3(-1.0f, 5.0f, 2.0f));
        scene.addLight(light0);

        Light light1 = new Light();
        light1.setIntensity(new Colorf(0.0f, 30.0f, 50.0f));
        light1.setPosition(new Vector3(1.0f, 5.0f, 1.0f));
        scene.addLight(light1);

        Light light2 = new Light();
        light2.setIntensity(new Colorf(1000.0f, 5.0f, 100.0f));
        light2.setPosition(new Vector3(-1.0f, 3.0f, -1.0f));
        scene.addLight(light1);

        OrthographicCamera cam = new OrthographicCamera();
        scene.setCamera(cam);

        Colorf outIntensity = new Colorf();
        Ray ray = new Ray();
        ray.origin.set(0.0, 2.0, 0.0);
        ray.direction.set(0.0, -1.0, 0.0);
        Colorf expectedIntensity = new Colorf();

        System.out.println("Testing not shadowed.");
        shader.shade(outIntensity, scene, ray, its, 23);
        expectedIntensity.set(4.022259f, 8.2814045f, 10.679813f);

        TestUtils.assertVector3Equal(outIntensity, expectedIntensity);

        System.out.println("Testing shadowed.");
        Sphere occluder = new Sphere();
        occluder.setCenter(new Vector3(1.0f, 5.0f, 1.0f));
        scene.addSurface(occluder);
        shader.shade(outIntensity, scene, ray, its, 23);
        expectedIntensity.set(4.022259f, 4.6837916f, 4.6837916f);

        TestUtils.assertVector3Equal(outIntensity, expectedIntensity);

        System.out.println("All tests passed.\n");
    }

}
