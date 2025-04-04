package org.qz.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.qz.utils.AppiumActions;

import java.time.Duration;

public class HomePage {
    private final AndroidDriver driver;
    private AppiumActions appiumActions;
    public HomePage(AndroidDriver driver){
        this.driver = driver;
        appiumActions= new AppiumActions(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @AndroidFindBy(xpath = "//*[@resource-id='leftIcon']")
    private WebElement hamburgerMenu;

    @AndroidFindBy(xpath = "//*[@resource-id='leftIcon']/following-sibling::android.view.ViewGroup/*")
    private WebElement plusIcon;

    public void openHamburgerMenu(){
        appiumActions.tapOnMobileElement(hamburgerMenu);
    }
    public void selectMenuItem(String menu){
        String menuItem = "new UiSelector().text(\"%s\")";
        appiumActions.tapOnMobileElement((AppiumBy) AppiumBy.androidUIAutomator(String.format(menuItem, menu)));
        //driver.findElement(AppiumBy.androidUIAutomator(String.format(menuItem, menu))).click();
    }

    public void tapOnPlusIcon(){
        appiumActions.tapOnMobileElement(plusIcon);
    }
}
