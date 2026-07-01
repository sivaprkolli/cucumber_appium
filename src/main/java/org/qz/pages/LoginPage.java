package org.qz.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.qz.utils.AppiumActions;

import java.time.Duration;

public class LoginPage {
    private AppiumActions appiumActions;
    public LoginPage(AppiumDriver driver){
        appiumActions = new AppiumActions(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"email\")")
    private WebElement emailInputBox;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"password\")")
    private WebElement passwordInputBox;

    @AndroidFindBy( uiAutomator= "new UiSelector().text(\"Login\")")
    private WebElement loginButton;

    @AndroidFindBy( uiAutomator= "new UiSelector().text(\"Allow\")")
    private WebElement allowButton;


    public boolean isLoginPageDisplayed(){
        appiumActions.tapOnMobileElement(allowButton);
        return emailInputBox.isDisplayed();
    }
    public void login(String userName, String password){
        appiumActions.typeValue(emailInputBox, userName);
        appiumActions.typeValue(passwordInputBox, password);
        appiumActions.tapOnMobileElement(loginButton);
    }

}
