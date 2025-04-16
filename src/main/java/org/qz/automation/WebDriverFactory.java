package org.qz.automation;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class WebDriverFactory {
    public static AndroidDriver driver;

    public static void initializeDriver()  {
        UiAutomator2Options uiAutomator2Options = new UiAutomator2Options();
        uiAutomator2Options.setPlatformVersion("14.0");
        uiAutomator2Options.setPlatformName("Android");
        uiAutomator2Options.setDeviceName("Samsung Galaxy S24");
        uiAutomator2Options.setAutomationName("UiAutomator2");
        uiAutomator2Options.setCapability("noReset", false);
        uiAutomator2Options.setCapability("appPackage","com.cogmento.app");
        uiAutomator2Options.setCapability("appActivity","com.cogmento.app.MainActivity");
        uiAutomator2Options.setApp(System.getProperty("user.dir")+"/src/main/resources/Cogmento.apk");
        try {
            driver = new AndroidDriver(new URL(" http://127.0.0.1:4723"), uiAutomator2Options);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setUp() throws MalformedURLException, InterruptedException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        UiAutomator2Options uiAutomator2Options = new UiAutomator2Options();
        uiAutomator2Options.setAutomationName("UiAutomator2");
        uiAutomator2Options.setPlatformVersion("14.0");
        uiAutomator2Options.setPlatformName("Android");
        uiAutomator2Options.setDeviceName("Google Pixel 8");
        //uiAutomator2Options.setApp(System.getProperty("user.dir")+"/src/main/resources/NoBroker.apk");
        uiAutomator2Options.setApp("bs://9932230731e9f6598e43255ff204c7905e1143b4");
        uiAutomator2Options.setCapability("appPackage","com.cogmento.app");
        uiAutomator2Options.setCapability("appActivity","com.cogmento.app.MainActivity");

        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("interactiveDebugging",true);
        browserstackOptions.put("browserstack.debug",true);
        browserstackOptions.put("appiumVersion", "2.0.0");
        desiredCapabilities.setCapability("bstack:options", browserstackOptions);
        uiAutomator2Options.merge(desiredCapabilities);
        driver = new AndroidDriver(new URL("https://userId:password@hub.browserstack.com/wd/hub"), uiAutomator2Options);
        //androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), uiAutomator2Options);
        //wait = new WebDriverWait(androidDriver, Duration.ofSeconds(30));
        Thread.sleep(5000);
    }

    public static void quitSession(){
        driver.quit();
    }

}
