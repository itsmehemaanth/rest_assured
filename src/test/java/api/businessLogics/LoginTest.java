package api.businessLogics;

import org.testng.annotations.Test;

import api.actions.ValidatorOperation;
import api.base.generic.BaseMethods;
import api.listeners.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;

import java.lang.reflect.Method;


public class LoginTest {

    String response;

    @Test
    public void testScenario01(Method method) {
        ExtentTestManager.startTest(method.getName(), "Description: Valid Login Scenario with username and password.");
        BaseMethods response = new BaseMethods();
        response.restMethod("TC001", "Sample");
    }

    @Test
    public void testScenario02(Method method) {
        ExtentTestManager.startTest(method.getName(), "Description: Valid Login Scenario with username and password.");
        BaseMethods response = new BaseMethods();
        response.restMethod("TC002", "Sample");
    }

}
