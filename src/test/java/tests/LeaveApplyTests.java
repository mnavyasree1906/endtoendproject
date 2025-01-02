package tests;

import com.aventstack.extentreports.ExtentTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LeavePage;
import pages.LoginPage;

public class LeaveApplyTests extends BaseTest {

    private LoginPage loginPage;
    private LeavePage leavePage;

    @BeforeClass
    public void setup() {
        initializeDriver(); // Initializes the WebDriver
        loginPage = new LoginPage(driver);
        leavePage = new LeavePage(driver);
    }

    @Test(priority = 1)
    public void TC_LeaveApply_001_Verify_Leave_Menu() {
        ExtentTest test = extent.createTest("Leave Apply Test");

        try {
            // Step 1: Log in to the OrangeHRM application
            driver.get(properties.getProperty("app.url"));
            test.info("Navigated to " + properties.getProperty("app.url"));

            loginPage.login(properties.getProperty("login.username"), properties.getProperty("login.password"));
            test.info("Logged in with valid credentials.");

            // Step 2: Navigate to Apply Leave page
            leavePage.navigateToApplyLeave();
            test.info("Navigated to Apply Leave page.");

            // Step 3: Verify the "Apply Leave" page is opened
            Assert.assertTrue(leavePage.isApplyLeavePageOpened(), "Apply Leave page did not open");
            test.pass("Apply Leave page opened successfully.");

            // Step 4: Verify the leave types are displayed in the dropdown
            leavePage.navigateToApplyLeave(); // Ensure you're on the Apply Leave page
            Assert.assertTrue(leavePage.isLeaveTypeDropdownDisplayed(), "Leave types dropdown is empty");
            test.pass("Leave types dropdown is displayed.");

            // Step 5: Apply for leave
            leavePage.applyLeave("CAN - FMLA", "2025-01-10", "2025-01-12", "Test Leave Request");
            test.info("Leave applied for CAN - FMLA from 2025-01-10 to 2025-01-12.");

            // Step 6: Verify leave application success
            Assert.assertTrue(leavePage.isLeaveApplicationSuccessful(), "Leave application was not successful");
            test.pass("Leave application was successful.");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        // Quit the driver after the test is done
        if (driver != null) {
            driver.quit();
        }
    }
}
