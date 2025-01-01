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
import pages.LoginPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class LoginTest {
    private WebDriver driver;
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
        extent.setSystemInfo("Tester", "Vikram Damodar");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        // Set Chrome options for headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        // Load config.properties using class loader
        properties = new Properties();
        InputStream configStream = getClass().getClassLoader().getResourceAsStream("config.properties");

        if (configStream == null) {
            throw new IOException("config.properties file not found in resources");
        }

        properties.load(configStream);

        // Implicit Wait
        int implicitWait = Integer.parseInt(properties.getProperty("implicit.wait.duration"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        // Initialize Page Object
        loginPage = new LoginPage(driver);

        // WebDriverWait
        int assertionWait = Integer.parseInt(properties.getProperty("assertion.wait.duration"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(assertionWait));

        // Log setup success to Extent Report
        test = extent.createTest("Setup Test");
        test.info("Setup completed successfully.");
    }

    @Test
    public void testValidLogin() {
        // Create a new ExtentTest instance for the test
        test = extent.createTest("Login with Valid Credentials");

        try {
            // Navigate to URL
            driver.get(properties.getProperty("app.url"));
            test.info("Navigated to " + properties.getProperty("app.url"));

            // Login actions using the new login method
            loginPage.login(properties.getProperty("login.username"), properties.getProperty("login.password"));
            test.info("Login attempt with username: " + properties.getProperty("login.username"));

            // Assertion
            Assert.assertTrue(loginPage.isDashboardDisplayed(), "Login failed: Dashboard not displayed.");
            String dashboardText = loginPage.getDashboardText();
            Assert.assertTrue(dashboardText.contains("Dashboard"), "Login failed: Incorrect dashboard text.");
            test.pass("Login successful and dashboard is displayed correctly.");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidLogin() {
        // Create a new ExtentTest instance for the test
        ExtentTest test = extent.createTest("Login with Invalid Credentials");

        try {
            // Navigate to URL
            driver.get(properties.getProperty("app.url"));
            test.info("Navigated to " + properties.getProperty("app.url"));

            // Attempt to login with invalid credentials
            loginPage.login("invalidUser", "invalidPassword");
            test.info("Login attempt with invalid credentials");

            // Assertion
            Assert.assertFalse(loginPage.isDashboardDisplayed(), "Login should have failed but dashboard is displayed.");
            test.pass("Login failed as expected.");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
            test.info("Driver closed successfully.");
        }
        // Flush the report to the output file
        extent.flush();
    }
}
