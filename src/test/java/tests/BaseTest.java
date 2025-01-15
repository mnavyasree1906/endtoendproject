package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.time.Duration;

public abstract class BaseTest {
    protected static ExtentReports extent;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Properties properties;

    @BeforeSuite
    public void initializeSuite() throws IOException {
        // ExtentReports setup
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/index.html");
        sparkReporter.config().setReportName("Automation Test Report");
        sparkReporter.config().setDocumentTitle("Automation Report - Selenium TestNG");
        sparkReporter.config().setEncoding("UTF-8");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add system information
        extent.setSystemInfo("Tester", "Navya Sree");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
    }

    protected void initializeDriver() {
        // Load properties
        if (properties == null) {
            properties = new Properties();
            try (InputStream configStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
                if (configStream == null) {
                    throw new IOException("config.properties file not found in resources");
                }
                properties.load(configStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Set Chrome options
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        //options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        // Initialize WebDriver
        driver = new ChromeDriver(options);

        // Implicit Wait
        int implicitWait = Integer.parseInt(properties.getProperty("implicit.wait.duration", "10"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        // WebDriverWait
        int assertionWait = Integer.parseInt(properties.getProperty("assertion.wait.duration", "10"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(assertionWait));
    }

    @AfterSuite
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
        }
    }
}
