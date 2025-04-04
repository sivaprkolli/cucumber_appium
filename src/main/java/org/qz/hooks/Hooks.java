package org.qz.hooks;

import io.cucumber.java.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.qz.automation.WebDriverFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import static org.qz.automation.WebDriverFactory.driver;

public class Hooks {

    @BeforeAll
    public static void initializeDrivers() throws MalformedURLException, InterruptedException {
        WebDriverFactory.initializeDriver();
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
