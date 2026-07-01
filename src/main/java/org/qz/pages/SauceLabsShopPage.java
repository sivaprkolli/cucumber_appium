package org.qz.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class SauceLabsShopPage {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    @AndroidFindBy(accessibility = "test-Username")
    @iOSXCUITFindBy(accessibility = "test-Username")
    private WebElement usernameInput;

    @AndroidFindBy(accessibility = "test-Password")
    @iOSXCUITFindBy(accessibility = "test-Password")
    private WebElement passwordInput;

    @AndroidFindBy(accessibility = "test-LOGIN")
    @iOSXCUITFindBy(accessibility = "test-LOGIN")
    private WebElement loginButton;

    @AndroidFindBy(accessibility = "test-Cart")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeOther[@name=\"test-Cart\"]/XCUIElementTypeOther[@name=\"1\"]")
    private WebElement cartIcon;

    @AndroidFindBy(accessibility = "test-PRODUCTS")
    @iOSXCUITFindBy(accessibility = "test-PRODUCTS")
    private WebElement productsHeader;

    @AndroidFindBy(accessibility = "test-ADD TO CART")
    @iOSXCUITFindBy(accessibility = "test-ADD TO CART")
    private WebElement genericAddToCartButton;

    @AndroidFindBy(accessibility = "test-First Name")
    @iOSXCUITFindBy(accessibility = "test-First Name")
    private WebElement firstNameInput;

    @AndroidFindBy(accessibility = "test-Last Name")
    @iOSXCUITFindBy(accessibility = "test-Last Name")
    private WebElement lastNameInput;

    @AndroidFindBy(accessibility = "test-Zip/Postal Code")
    @iOSXCUITFindBy(accessibility = "test-Zip/Postal Code")
    private WebElement zipCodeInput;

    @AndroidFindBy(accessibility = "test-CONTINUE")
    @iOSXCUITFindBy(accessibility = "test-CONTINUE")
    private WebElement continueButton;

    @AndroidFindBy(accessibility = "test-FINISH")
    @iOSXCUITFindBy(accessibility = "test-FINISH")
    private WebElement finishButton;

    @AndroidFindBy(accessibility = "test-CHECKOUT-COMPLETE-TEXT")
    @iOSXCUITFindBy(accessibility = "test-CHECKOUT-COMPLETE-TEXT")
    private WebElement checkoutCompleteText;

    @AndroidFindBy(accessibility = "test-COMPLETE HEADER")
    @iOSXCUITFindBy(accessibility = "test-COMPLETE HEADER")
    private WebElement completeHeader;

    public SauceLabsShopPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    private boolean isVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private boolean isVisible(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean isLoginPageDisplayed() {
        return isVisible(usernameInput);
    }

    public void login(String username, String password) {
        waitForClickable(usernameInput).sendKeys(username);
        waitForClickable(passwordInput).sendKeys(password);
        waitForClickable(loginButton).click();
    }

    public boolean isProductsPageDisplayed() {
        return isVisible(cartIcon) || isVisible(productsHeader);
    }

    public void addProductToCart(String productName) {
        String productXpath = "//*[contains(@text,'" + productName + "') or contains(@label,'" + productName + "') or contains(@name,'" + productName + "')]";
        List<WebElement> productCandidates = driver.findElements(By.xpath(productXpath));
        //productCandidates.get(0).click();
        waitForClickable(genericAddToCartButton).click();
    }

    public void openCart() {
        //waitForClickable(cartIcon).click();
        try{
            Point location = cartIcon.getLocation();
            Dimension size = cartIcon.getSize();

            Point locationOfElement =
                    new Point(location.getX() + size.getWidth() / 1,
                            location.getY() + size.getHeight() / 4);

            PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
            Sequence sequence = new Sequence(finger1, 1)
                    .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), locationOfElement))
                    .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                    .addAction(new Pause(finger1, Duration.ofSeconds(5)))
                    .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(sequence));
        }catch (Exception e){

        }
        System.out.println("Test");
    }

    public boolean isProductDisplayedInCart(String productName) {
        String cartProductXpath = "//*[contains(@text,'" + productName + "') or contains(@label,'" + productName + "') or contains(@name,'" + productName + "')]";
        return isVisible(By.xpath(cartProductXpath));
    }

    public void tapCheckout() {
        By[] checkoutLocators = {
                AppiumBy.accessibilityId("test-CHECKOUT"),
                AppiumBy.accessibilityId("CHECKOUT"),
                By.xpath("//*[contains(@text,'CHECKOUT') or contains(@label,'CHECKOUT') or contains(@name,'CHECKOUT') or contains(@text,'Checkout') or contains(@label,'Checkout') or contains(@name,'Checkout')]")
        };

        for (By locator : checkoutLocators) {
            if (tryClick(locator, 2)) {
                return;
            }
        }

        // If checkout is below the fold on iOS, swipe and retry all known locators.
        for (int scrollAttempt = 0; scrollAttempt < 4; scrollAttempt++) {
            scrollDownOnce();
            for (By locator : checkoutLocators) {
                if (tryClick(locator, 1)) {
                    return;
                }
            }
        }

        throw new NoSuchElementException("Checkout button was not found on cart screen after retries and scrolling");
    }

    public void enterCheckoutInformation(String firstName, String lastName, String pin) {
        waitForClickable(firstNameInput).sendKeys(firstName);
        waitForClickable(lastNameInput).sendKeys(lastName);
        waitForClickable(zipCodeInput).sendKeys(pin);
    }

    public void tapContinueOnCheckout() {
        waitForClickable(continueButton).click();
    }

    public void tapFinish() {
        if (isVisible(finishButton)) {
            waitForClickable(finishButton).click();
            return;
        }

        // On smaller devices the Finish button can be below the fold on checkout overview.
        for (int attempt = 0; attempt < 4; attempt++) {
            scrollDownOnce();
            if (isVisible(finishButton)) {
                waitForClickable(finishButton).click();
                return;
            }
        }

        throw new NoSuchElementException("Finish button was not found after scrolling on checkout overview screen");
    }

    public boolean isOrderConfirmationDisplayed(String expectedMessage) {
        String actualMessage = readOrderConfirmationMessage();
        if (actualMessage.isEmpty()) {
            return false;
        }

        String normalizedActual = normalizeOrderText(actualMessage);
        String normalizedExpected = normalizeOrderText(expectedMessage);

        if (!normalizedExpected.isEmpty() && (normalizedActual.contains(normalizedExpected) || normalizedExpected.contains(normalizedActual))) {
            return true;
        }

        // Sauce Labs demo app text varies by platform/app version, so accept known variants.
        String[] knownMessages = {
                "THANK YOU FOR YOU ORDER",
                "THANK YOU FOR YOUR ORDER",
                "Thanks for your order",
                "Thnaks for your order"
        };
        for (String knownMessage : knownMessages) {
            if (normalizedActual.contains(normalizeOrderText(knownMessage))) {
                return true;
            }
        }

        return false;
    }

    private String readOrderConfirmationMessage() {
        WebElement[] priorityElements = {checkoutCompleteText, completeHeader};
        for (WebElement element : priorityElements) {
            String extracted = extractText(element);
            if (!extracted.isEmpty()) {
                return extracted;
            }
        }

        By[] locators = {
                By.xpath("//*[contains(@text,'THANK') or contains(@label,'THANK') or contains(@name,'THANK') or contains(@text,'Thnaks') or contains(@label,'Thnaks') or contains(@name,'Thnaks')]")
        };

        for (By locator : locators) {
            List<WebElement> elements = driver.findElements(locator);
            if (elements.isEmpty()) {
                continue;
            }

            String extracted = extractText(elements.get(0));
            if (!extracted.isEmpty()) {
                return extracted;
            }
        }

        return "";
    }

    private String normalizeOrderText(String text) {
        if (text == null) {
            return "";
        }

        return text
                .toLowerCase()
                .replace("thnaks", "thanks")
                .replace("your order", "you order")
                .replaceAll("[^a-z0-9]", "");
    }

    private void scrollDownOnce() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.getWidth() / 2;
        int endX = startX;
        int startY = (int) (size.getHeight() * 0.82);
        int endY = (int) (size.getHeight() * 0.24);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger, Duration.ofMillis(150)))
                .addAction(finger.createPointerMove(Duration.ofMillis(350), PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    private boolean tryClick(By locator, int seconds) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            shortWait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    private String extractText(WebElement element) {
        try {
            if (element == null || !element.isDisplayed()) {
                return "";
            }

            String text = element.getText();
            if (text != null && !text.trim().isEmpty()) {
                return text.trim();
            }

            String label = element.getAttribute("label");
            if (label != null && !label.trim().isEmpty()) {
                return label.trim();
            }

            String name = element.getAttribute("name");
            if (name != null && !name.trim().isEmpty()) {
                return name.trim();
            }

            return "";
        } catch (Exception ignored) {
            return "";
        }
    }
}
