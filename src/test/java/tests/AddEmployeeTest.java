package tests;

import com.aventstack.extentreports.ExtentTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.LoginPage;

public class AddEmployeeTest extends BaseTest {
    private LoginPage loginPage;
    private AddEmployeePage addEmployeePage;

    @BeforeClass
    public void setup() {
        initializeDriver();
        loginPage = new LoginPage(driver);
        addEmployeePage = new AddEmployeePage(driver);
    }

    @Test
    public void testAddEmployee() {
        ExtentTest test = extent.createTest("Add Employee Test");

        try {
            driver.get(properties.getProperty("app.url"));
            test.info("Navigated to " + properties.getProperty("app.url"));

            loginPage.login(properties.getProperty("login.username"), properties.getProperty("login.password"));
            test.info("Logged in with valid credentials.");

            addEmployeePage.navigateToPIM();
            test.info("Navigated to PIM section.");

            Assert.assertTrue(addEmployeePage.isEmployeeInformationDisplayed(), "Employee Information not displayed.");
            test.pass("Employee Information validated.");

            addEmployeePage.clickAddEmployee();
            test.info("Clicked Add Employee button.");

            addEmployeePage.enterEmployeeDetails("Navya", "Sree");
            test.info("Entered employee details: Navya Sree.");

            addEmployeePage.clickSave();
            Assert.assertTrue(addEmployeePage.isSuccessMessageDisplayed(), "Success message not displayed.");
            test.pass("Employee added successfully.");
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
