package org.qz.hooks;

import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.qz.automation.WebDriverFactory;

import java.io.File;
import java.io.IOException;

import static org.qz.automation.WebDriverFactory.driver;

public class Hooks {

    @Before(order = 0)
    public void initializeDrivers(Scenario scenario) {
        if (scenario.getSourceTagNames().contains("@iOS")) {
            System.setProperty("mobile.platform", "ios");
            WebDriverFactory.initializeDriver("ios");
            return;
        }

        System.setProperty("mobile.platform", "android");
        if (scenario.getSourceTagNames().contains("@SauceLabsCart")) {
            System.setProperty("android.app.type", "saucelabs");
        } else {
            System.setProperty("android.app.type", "cogmento");
        }
        WebDriverFactory.initializeDriver("android");
    }

    @After
    public void tearDown() {
        WebDriverFactory.quitSession();
    }

    public void getScreenShot() throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceImage = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationImage = new File(System.getProperty("user.dir")+"/screenshots/testImage.png");
        FileUtils.copyFile(sourceImage, destinationImage);
    }

   // @AfterStep
    public static void forEveryScenarioCase(Scenario scenario){
      if (!scenario.isFailed()) {
          byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
          scenario.attach(screenshot, "image/png", screenshot.toString());
      }
    }
}
