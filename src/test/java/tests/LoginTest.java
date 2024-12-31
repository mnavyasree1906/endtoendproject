package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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

    @BeforeClass
    public void setup() throws IOException {
        // Load config.properties using class loader
        properties = new Properties();
        
        // Use class loader to get the resource from the resources folder
        InputStream configStream = getClass().getClassLoader().getResourceAsStream("config.properties");

        if (configStream == null) {
            throw new IOException("config.properties file not found in resources");
        }

        properties.load(configStream);

        // Initialize WebDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Implicit Wait
        int implicitWait = Integer.parseInt(properties.getProperty("implicit.wait.duration"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        // Initialize Page Object
        loginPage = new LoginPage(driver);

        // WebDriverWait
        int assertionWait = Integer.parseInt(properties.getProperty("assertion.wait.duration"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(assertionWait));
    }

    @Test
    public void testValidLogin() {
        // Navigate to URL
        driver.get(properties.getProperty("app.url"));

        // Login actions using the new login method
        loginPage.login(properties.getProperty("login.username"), properties.getProperty("login.password"));

        // Assertion
        Assert.assertTrue(loginPage.isDashboardDisplayed(), "Login failed: Dashboard not displayed.");
        String dashboardText = loginPage.getDashboardText();
        Assert.assertTrue(dashboardText.contains("Dashboard"), "Login failed: Incorrect dashboard text.");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
