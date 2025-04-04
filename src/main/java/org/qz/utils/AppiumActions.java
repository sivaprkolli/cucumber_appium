package org.qz.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;

public class AppiumActions {
    AndroidDriver driver;
    WebDriverWait wait;
    public AppiumActions(AndroidDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void tapOnMobileElement(WebElement element){
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void tapOnMobileElement(AppiumBy by){
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }
    public void typeValue(WebElement element, String data){
        wait.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(data);
    }

    public boolean isElementDisplayed(AppiumBy by){
        return wait.until(ExpectedConditions.presenceOfElementLocated(by)).isDisplayed();
    }

    public boolean isElementDisplayed(WebElement element){
        return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
    }
    public void scrollDown() {
        Dimension size = driver.manage().window().getSize();
        int StartX = size.getWidth() / 2;
        int endX = StartX;

        int StartY = (int) (size.getHeight() * 0.90);
        int endY = (int) (size.getHeight() * 0.20);

        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "Fingername");
        Sequence sequence = new Sequence(finger1, 1)
                .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), StartX, StartY))
                .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger1, Duration.ofMillis(200)))
                .addAction(finger1.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(sequence));
    }

}
