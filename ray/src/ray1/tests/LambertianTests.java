package ray1.tests;

import org.junit.Test;

import egl.math.Colorf;
import egl.math.Vector3;
import egl.math.Vector3d;

import ray1.IntersectionRecord;
import ray1.Light;
import ray1.Ray;
import ray1.Scene;
import ray1.shader.Lambertian;
import ray1.surface.Sphere;

public class LambertianTests {

    @Test
    public void testSingleLight() {
        System.out.println("\nTesting Lambertian shader with a single point source.");
        System.out.println("==============================================");

        // Setting up.
        Lambertian shader = new Lambertian();
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
        Colorf outIntensity = new Colorf();
        Ray ray = new Ray(new Vector3d(0,2,0), new Vector3d(0,-1,0));
        Colorf expectedIntensity = new Colorf();

        System.out.println("Testing not shadowed.");
        
        shader.shade(outIntensity, scene, ray, its, 23);
        expectedIntensity.set(0.0f, 0.37300451811919006f, 0.3730045181191900f);
        expectedIntensity.div((float)Math.PI);
        TestUtils.assertVector3Equal(outIntensity, expectedIntensity);

        System.out.println("Testing shadowed.");
        Sphere occluder = new Sphere();
        occluder.setCenter(new Vector3(5.0f, 5.0f, 5.0f));
        scene.addSurface(occluder);
        shader.shade(outIntensity, scene, ray, its, 23);
        expectedIntensity.set(0.0f, 0.0f, 0.0f);
        expectedIntensity.div((float)Math.PI);
        
        TestUtils.assertVector3Equal(outIntensity, expectedIntensity);

        System.out.println("All tests passed.\n");
    }

    @Test
    public void testMultipleLights() {
        System.out.println("\nTesting Lambertian shader with a multiple point sources.");
        System.out.println("==============================================");

        // Setting up.
        Lambertian shader = new Lambertian();
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

        Light light1 = new Light();
        light1.setIntensity(new Colorf(0.0f, 30.0f, 50.0f));
        light1.setPosition(new Vector3(0.0f, 5.0f, 0.0f));
        scene.addLight(light1);

        Light light2 = new Light();
        light2.setIntensity(new Colorf(1000.0f, 5.0f, 100.0f));
        light2.setPosition(new Vector3(-1.0f, 3.0f, -1.0f));
        scene.addLight(light1);

        Colorf outIntensity = new Colorf();
        Ray ray = new Ray(new Vector3d(0,2,0), new Vector3d(0,-1,0));
        Colorf expectedIntensity = new Colorf();

        System.out.println("Testing not shadowed.");
        shader.shade(outIntensity, scene, ray, its, 23);
        expectedIntensity.set(0.0f, 2.24800451811919f, 3.49800451811919f);
        expectedIntensity.div((float)Math.PI);
        TestUtils.assertVector3Equal(outIntensity, expectedIntensity);

        System.out.println("Testing shadowed.");
        Sphere occluder = new Sphere();
        occluder.setCenter(new Vector3(5.0f, 5.0f, 5.0f));
        scene.addSurface(occluder);
        shader.shade(outIntensity, scene, ray, its, 23);
        expectedIntensity.set(0.0f, 1.875f, 3.125f);
        expectedIntensity.div((float)Math.PI);
        TestUtils.assertVector3Equal(outIntensity, expectedIntensity);

        System.out.println("All tests passed.\n");
    }

}
