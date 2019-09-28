package ray1.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;;

public class TestRunner {
    public static void main(String args[]) {
        Result result = JUnitCore.runClasses(TestSuite.class);
        if(result.getFailureCount() > 0){
            System.out.println("Failures in the following tests:");
            System.out.println("============================================");
        }
        for(Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("============================================");
        System.out.println("Tests were succesful: " + result.wasSuccessful());
    }
}
