package tests;

import com.aventstack.extentreports.ExtentTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeClass
    public void setup() {
        initializeDriver();
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testValidLogin() {
        ExtentTest test = extent.createTest("Login with Valid Credentials");

        try {
            driver.get(properties.getProperty("app.url"));
            test.info("Navigated to " + properties.getProperty("app.url"));

            loginPage.login(properties.getProperty("login.username"), properties.getProperty("login.password"));
            test.info("Login attempt with username: " + properties.getProperty("login.username"));

            Assert.assertTrue(loginPage.isDashboardDisplayed(), "Dashboard not displayed after login.");
            test.pass("Login successful and dashboard displayed.");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testInvalidLogin() {
        ExtentTest test = extent.createTest("Login with Invalid Credentials");

        try {
            driver.get(properties.getProperty("app.url"));
            test.info("Navigated to " + properties.getProperty("app.url"));

            loginPage.login("invalidUsername", "invalidPassword");
            test.info("Login attempt with invalid credentials.");

            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message not displayed for invalid login.");
            test.pass("Invalid login test passed as expected.");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
