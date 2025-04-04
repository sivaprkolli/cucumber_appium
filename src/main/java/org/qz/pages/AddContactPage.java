package org.qz.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.qz.utils.AppiumActions;

import java.time.Duration;

public class AddContactPage {
    private AndroidDriver driver;
    private AppiumActions appiumActions;
    public AddContactPage(AndroidDriver driver){
        this.driver = driver;
        appiumActions = new AppiumActions(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"first_name-input\")")
    private WebElement firstNameInputBox;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"last_name-input\")")
    private WebElement lastNameInputBox;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Company\")")
    private WebElement companyLabel;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"OK\")")
    private WebElement okAlertButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Search company\")")
    private WebElement searchCompanyInputBox;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='QualiZeal']")
    private WebElement dropOptionValue;

    @AndroidFindBy(xpath = "//*[@text='Company']/preceding-sibling::*")
    private WebElement backButton;

    @AndroidFindBy(xpath = "//*[@text='ADD Email']/preceding-sibling::*")
    private WebElement addEmailIcon;

    @AndroidFindBy(xpath = "//*[@text='home']/following-sibling::*")
    private WebElement emailAddressInputBox;

    @AndroidFindBy(xpath = "//*[@text='Identifier']/following-sibling::*")
    private WebElement identifierInputBox;

    @AndroidFindBy(uiAutomator = "//new UiSelector().resourceId(\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\")")
    private WebElement whileUsingAlertButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Done\")")
    private WebElement doneButton;
    public void addContact(String firstName, String lastName,String company, String identity){
        appiumActions.typeValue(firstNameInputBox, firstName);
        appiumActions.typeValue(lastNameInputBox, lastName);
        appiumActions.tapOnMobileElement(companyLabel);
        appiumActions.tapOnMobileElement(okAlertButton);
        appiumActions.typeValue(searchCompanyInputBox, company);
        appiumActions.tapOnMobileElement(dropOptionValue);
        appiumActions.tapOnMobileElement(backButton);
//        appiumActions.tapOnMobileElement(addEmailIcon);
//        appiumActions.typeValue(emailAddressInputBox, email);
        //driver.findElement(AppiumBy.xpath("//*[@text='home']/following-sibling::*")).sendKeys(email);
        appiumActions.scrollDown();
        appiumActions.scrollDown();
        appiumActions.typeValue(identifierInputBox, identity);
    }

    public void clickOnDone(){
        appiumActions.tapOnMobileElement(doneButton);
    }


}
