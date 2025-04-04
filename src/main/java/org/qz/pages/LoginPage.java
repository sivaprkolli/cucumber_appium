package org.qz.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.qz.utils.AppiumActions;

import java.lang.reflect.Field;
import java.time.Duration;

import static org.openqa.selenium.By.id;

public class LoginPage {
    private final AndroidDriver driver;
    private AppiumActions appiumActions;
    public LoginPage(AndroidDriver driver){
        this.driver = driver;
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
