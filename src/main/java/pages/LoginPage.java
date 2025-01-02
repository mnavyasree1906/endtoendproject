package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    // Locators
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//button[@type='submit']");
    private By dashboardBreadcrumb = By.className("oxd-topbar-header-breadcrumb");
    private By invalidLoginErrorMessage = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public boolean isDashboardDisplayed() {
        return driver.findElement(dashboardBreadcrumb).isDisplayed();
    }

    public String getDashboardText() {
        return driver.findElement(dashboardBreadcrumb).getText();
    }
    // New method to enter both username and password
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            WebElement errorMessage = driver.findElement(invalidLoginErrorMessage);
            return errorMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}