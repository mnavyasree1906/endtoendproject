package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.LoginPage;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class AddEmployeeTest {
    private WebDriver driver;
    private AddEmployeePage addEmployeePage;
    private LoginPage loginPage;
    private Properties properties;
    private WebDriverWait wait;

    // ExtentReports objects
    private static ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void setup() throws IOException {
        // Set up ExtentReports with detailed configurations
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/index.html");
        sparkReporter.config().setReportName("Automation Test Report");
        sparkReporter.config().setDocumentTitle("Automation Report - Selenium TestNG");
        sparkReporter.config().setEncoding("UTF-8");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add system information to the report
        extent.setSystemInfo("Tester", "Navya Sree");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        // Load config.properties using class loader
        properties = new Properties();
        InputStream configStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        if (configStream == null) {
            throw new IOException("config.properties file not found in resources");
        }
        properties.load(configStream);

        // Set Chrome options for headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Enable headless mode
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080"); // Optional: Set window size for headless mode
        options.addArguments("--remote-allow-origins=*");

        // Initialize WebDriver with options
        driver = new ChromeDriver(options);

        // Implicit Wait
        int implicitWait = Integer.parseInt(properties.getProperty("implicit.wait.duration"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        // Initialize Page Objects
        loginPage = new LoginPage(driver);
        addEmployeePage = new AddEmployeePage(driver);

        // WebDriverWait
        int assertionWait = Integer.parseInt(properties.getProperty("assertion.wait.duration"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(assertionWait));

        // Log setup success to Extent Report
        test = extent.createTest("Setup Test");
        test.info("Setup completed successfully.");
    }

    @Test
    public void testAddEmployee() {

        // Create a new ExtentTest instance for the test
        test = extent.createTest("Add Employee Test");

        try {
            // Navigate to OrangeHRM login page
            driver.get(properties.getProperty("app.url"));
            test.info("Navigated to " + properties.getProperty("app.url"));

            // Login actions using the new login method
            loginPage.login(properties.getProperty("login.username"), properties.getProperty("login.password"));
            test.info("Logged in with username: " + properties.getProperty("login.username"));

            // Navigate to PIM section
            addEmployeePage.navigateToPIM();
            test.info("Navigated to PIM section.");

            // Validate Employee Information, Reset, and Search buttons are displayed
            Assert.assertTrue(addEmployeePage.isEmployeeInformationDisplayed(), "Employee Information is not displayed!");
            Assert.assertTrue(addEmployeePage.isResetButtonDisplayed(), "Reset button is not displayed!");
            Assert.assertTrue(addEmployeePage.isSearchButtonDisplayed(), "Search button is not displayed!");
            test.pass("Validated Employee Information, Reset, and Search buttons are displayed.");

            // Add Employee
            addEmployeePage.clickAddEmployee();
            test.info("Clicked on Add Employee button.");

            addEmployeePage.enterEmployeeDetails("Navya", "Sree");
            test.info("Entered employee details: First Name = Navya, Last Name = Sree.");

            addEmployeePage.clickSave();
            test.info("Clicked on Save button.");

            // Verify success message
            Assert.assertTrue(addEmployeePage.isSuccessMessageDisplayed(), "Personal Details message is not displayed!");
            Assert.assertEquals(addEmployeePage.getSuccessMessage(), "Personal Details", "Confirmation Message mismatch!");
            test.pass("Employee added successfully and Personal Details message is displayed.");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            test.info("Driver closed successfully.");
        }
        // Flush the report to the output file
        extent.flush();
    }
}
