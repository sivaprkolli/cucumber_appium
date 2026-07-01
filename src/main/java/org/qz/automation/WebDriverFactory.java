package org.qz.automation;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class WebDriverFactory {
    public static AppiumDriver driver;

    public static void initializeDriver()  {
        initializeDriver(System.getProperty("mobile.platform", "android"));
    }

    public static synchronized void initializeDriver(String platform) {
        quitSession();
        try {
            String normalizedPlatform = platform == null ? "android" : platform.trim().toLowerCase();

            if ("ios".equals(normalizedPlatform)) {
                XCUITestOptions xcuiTestOptions = new XCUITestOptions();
                xcuiTestOptions.setPlatformName("iOS");
                xcuiTestOptions.setPlatformVersion(System.getProperty("ios.platformVersion", "26.0"));
                xcuiTestOptions.setDeviceName(System.getProperty("ios.deviceName", "iPhone 17 Pro"));
                xcuiTestOptions.setAutomationName("XCUITest");
                xcuiTestOptions.setNewCommandTimeout(Duration.ofSeconds(90000));
                xcuiTestOptions.setCapability("noReset", false);
                xcuiTestOptions.setApp(System.getProperty("user.dir") + "/src/main/resources/iOSSauceLabs.app");
                driver = new IOSDriver(new URL(System.getProperty("appium.server.url", "http://127.0.0.1:4723")), xcuiTestOptions);
                return;
            }

            UiAutomator2Options uiAutomator2Options = new UiAutomator2Options();
            uiAutomator2Options.setPlatformVersion(System.getProperty("android.platformVersion", "16.0"));
            uiAutomator2Options.setPlatformName("Android");
            uiAutomator2Options.setDeviceName(System.getProperty("android.deviceName", "Samsung Galaxy S24"));
            uiAutomator2Options.setAutomationName("UiAutomator2");
            uiAutomator2Options.setNewCommandTimeout(Duration.ofSeconds(90000));
            uiAutomator2Options.setCapability("noReset", false);

            // Keep Cogmento as default for existing tests unless explicitly overridden.
            String androidAppType = System.getProperty("android.app.type", "cogmento").trim().toLowerCase();
            if ("saucelabs".equals(androidAppType)) {
                uiAutomator2Options.setCapability("appPackage", "com.swaglabsmobileapp");
                uiAutomator2Options.setCapability("appActivity", "com.swaglabsmobileapp.MainActivity");
                uiAutomator2Options.setApp(System.getProperty("user.dir") + "/src/main/resources/AndroidSauceLabs.apk");
            } else {
                uiAutomator2Options.setCapability("appPackage", "com.cogmento.app");
                uiAutomator2Options.setCapability("appActivity", "com.cogmento.app.MainActivity");
                uiAutomator2Options.setApp(System.getProperty("user.dir") + "/src/main/resources/Cogmento.apk");
            }

            driver = new AndroidDriver(new URL(System.getProperty("appium.server.url", "http://127.0.0.1:4723")), uiAutomator2Options);
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
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

}
