package org.qz.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.cucumber.java.en.And;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.qz.utils.AppiumActions;

import java.time.Duration;

public class ContactsPage {
    private AppiumActions appiumActions;
    private AndroidDriver driver;
    public ContactsPage(AndroidDriver driver){
        this.driver = driver;
        appiumActions= new AppiumActions(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }


    @AndroidFindBy(xpath = "//*[@resource-id='closeBtn']/following-sibling::*/*/*")
    private WebElement threeDotsMenu;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Delete\")")
    private WebElement deleteButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"OK\")")
    private WebElement okButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"No records found\")")
    private WebElement emptyRecordMessage;

    @AndroidFindBy(xpath = "//*[@text='Edit']/preceding-sibling::*")
    private WebElement editContactButton;

    @AndroidFindBy(xpath = "//*[@text='ADD Email']/preceding-sibling::*")
    private WebElement addEmailIcon;

    @AndroidFindBy(xpath = "//*[@text='home']/following-sibling::*")
    private WebElement emailAddressInputBox;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Done\")")
    private WebElement doneButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"closeBtn\")")
    private WebElement closeButton;

    public boolean isMessageFound(){
        return appiumActions.isElementDisplayed(emptyRecordMessage);
    }

    public void editContact(String email){
        appiumActions.tapOnMobileElement(editContactButton);
        appiumActions.tapOnMobileElement(addEmailIcon);
        appiumActions.typeValue(emailAddressInputBox, email);
        appiumActions.tapOnMobileElement(doneButton);
        appiumActions.tapOnMobileElement(closeButton);
    }
    public void deleteContact(){
        appiumActions.tapOnMobileElement(threeDotsMenu);
        appiumActions.tapOnMobileElement(deleteButton);
        appiumActions.tapOnMobileElement(okButton);
    }

    public void openContact(String firstName, String lastName){
        String names = "new UiSelector().text(\""+firstName+" "+lastName+"\").instance(0)";
        appiumActions.tapOnMobileElement((AppiumBy) AppiumBy.androidUIAutomator(names));
    }

    public boolean isContactCreated(String firstName, String lastName){
        String names = "new UiSelector().text(\""+firstName+" "+lastName+"\").instance(0)";
        return appiumActions.isElementDisplayed((AppiumBy) AppiumBy.androidUIAutomator(names));
    }

    public boolean isContactUpdated(String email){
        String name = "new UiSelector().textContains(\""+email+"\")";
        return appiumActions.isElementDisplayed((AppiumBy) AppiumBy.androidUIAutomator(name));
    }
}
