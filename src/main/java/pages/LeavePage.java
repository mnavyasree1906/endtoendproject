package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class LeavePage {

    WebDriver driver;

    // Locators for Leave Apply Page
    private By leaveMenu = By.xpath("//a[@class='oxd-main-menu-item active']");
    private By applyLeaveOption = By.linkText("Apply");
    private By leaveTypeDropdown = By.xpath("//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow']");
    private By startDateField = By.xpath("//div@class='oxd-date-input']");
    private By endDateField = By.id("applyleave_txtToDate");
    private By commentField = By.id("applyleave_txtComment");
    private By applyButton = By.xpath("//button[@type='submit']");
    private By successMessage = By.cssSelector("div.message.success.fadable");

    public LeavePage(WebDriver driver) {
        this.driver = driver;
    }

    // Navigate to Apply Leave page
    public void navigateToApplyLeave() {
        driver.findElement(leaveMenu).click();
        driver.findElement(applyLeaveOption).click();
    }

    // Verify if the "Apply Leave" page is opened
    public boolean isApplyLeavePageOpened() {
        return driver.getTitle().contains("Apply Leave");
    }

    // Apply for leave
    public void applyLeave(String leaveType, String startDate, String endDate, String comment) {
        Select leaveTypeSelect = new Select(driver.findElement(leaveTypeDropdown));
        leaveTypeSelect.selectByVisibleText(leaveType);

        driver.findElement(startDateField).sendKeys(startDate);
        driver.findElement(endDateField).sendKeys(endDate);
        driver.findElement(commentField).sendKeys(comment);
        driver.findElement(applyButton).click();
    }

    // Check if the leave application was successful
    public boolean isLeaveApplicationSuccessful() {
        return driver.findElement(successMessage).isDisplayed();
    }

    // New method: Check if the leave type dropdown is displayed
    public boolean isLeaveTypeDropdownDisplayed() {
        WebElement dropdown = driver.findElement(leaveTypeDropdown);
        return dropdown.isDisplayed() && new Select(dropdown).getOptions().size() > 0;
    }
}
