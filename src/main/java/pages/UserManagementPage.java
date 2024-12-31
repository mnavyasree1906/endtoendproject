package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UserManagementPage {
    private WebDriver driver;

    // Locators (Updated to relative XPath as per your example)
    private By adminTab = By.xpath("//span[@class='oxd-text oxd-text--span oxd-main-menu-item--name'][contains(.,'Admin')]");
    private By userManagementTab = By.xpath("//a[@href='#/admin/viewSystemUsers']//span[1]");  // Example XPath
    private By usersTab = By.xpath("//a[@href='#/admin/viewSystemUsers']//span[1]"); // Example XPath
    private By addButton = By.xpath("//button[contains(@class, 'oxd-button--medium') and text()='Add']"); // Example XPath for Add button
    private By employeeNameField = By.xpath("//input[@id='systemUser_employeeName_empName']"); // Relative XPath for employee name
    private By usernameField = By.xpath("//input[@id='systemUser_userName']"); // Relative XPath for username field
    private By statusDropdown = By.xpath("//select[@id='systemUser_status']"); // Relative XPath for status dropdown
    private By passwordField = By.xpath("//input[@id='systemUser_password']"); // Relative XPath for password field
    private By confirmPasswordField = By.xpath("//input[@id='systemUser_confirmPassword']"); // Relative XPath for confirm password field
    private By saveButton = By.xpath("//button[@type='submit' and text()='Save']"); // Relative XPath for save button
    private By successMessage = By.xpath("//div[contains(@class, 'message success')]"); // Relative XPath for success message
    private By userList = By.xpath("//table[@id='resultTable']"); // Relative XPath for user list table

    // Constructor
    public UserManagementPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void navigateToUsersPage() {
        driver.findElement(adminTab).click();
        driver.findElement(userManagementTab).click();
        driver.findElement(usersTab).click();
    }

    public void clickAddButton() {
        driver.findElement(addButton).click();
    }

    public void enterEmployeeName(String employeeName) {
        driver.findElement(employeeNameField).sendKeys(employeeName);
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void selectStatus(String status) {
        WebElement statusElement = driver.findElement(statusDropdown);
        statusElement.sendKeys(status);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void confirmPassword(String password) {
        driver.findElement(confirmPasswordField).sendKeys(password);
    }

    public void clickSaveButton() {
        driver.findElement(saveButton).click();
    }

    public boolean isSuccessMessageDisplayed() {
        return driver.findElement(successMessage).isDisplayed();
    }

    public boolean isUserInUserList(String username) {
        return driver.findElement(userList).getText().contains(username);
    }
}
