package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddEmployeePage {
    private WebDriver driver;

    // Locators
    private By pimMenu = By.xpath("//span[text()='PIM']");
    private By employeeInformationText = By.xpath("//h5[normalize-space()='Employee Information']");
    private By resetButton = By.xpath("//button[normalize-space()='Reset']");
    private By searchButton = By.xpath("//button[normalize-space()='Search']");
    private By addEmployeeButton = By.xpath("//a[text()='Add Employee']");
    private By firstNameField = By.xpath("//input[@placeholder='First Name']");
    private By lastNameField = By.xpath("//input[@placeholder='Last Name']");
    private By saveButton = By.xpath("//button[normalize-space()='Save']");
    private By confirmationMessage = By.xpath("//h6[normalize-space()='Personal Details']");


    // Constructor
    public AddEmployeePage(WebDriver driver) {
        this.driver = driver;
    }

    //Actions
    public void navigateToPIM() {
        driver.findElement(pimMenu).click();
    }

    public boolean isEmployeeInformationDisplayed() {
        return driver.findElement(employeeInformationText).isDisplayed();
    }

    public boolean isResetButtonDisplayed() {
        return driver.findElement(resetButton).isDisplayed();
    }

    public boolean isSearchButtonDisplayed() {
        return driver.findElement(searchButton).isDisplayed();
    }

    public void clickAddEmployee() {
        driver.findElement(addEmployeeButton).click();
    }

    public void enterEmployeeDetails(String firstName, String lastName) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void clickSave() {
        driver.findElement(saveButton).click();
    }

    public String getSuccessMessage() {
        return driver.findElement(confirmationMessage).getText();
    }

    public boolean isSuccessMessageDisplayed() {
        return driver.findElement(confirmationMessage).isDisplayed();
    }
}

