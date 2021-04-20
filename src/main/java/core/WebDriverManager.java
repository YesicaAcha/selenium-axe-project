package core;

import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static io.github.bonigarcia.wdm.config.DriverManagerType.CHROME;


public class WebDriverManager {
    private WebDriver webDriver;
    private WebDriverWait webDriverWait;

    private static WebDriverManager instance = null;

    /**
     * Constructor of page WebDriverManager.
     */
    protected WebDriverManager() {
        initialize();
    }

    /**
     * Gets Instance of a WebElement.
     *
     * @return Instance of WebElement.
     */
    public static WebDriverManager getInstance() {
        if (instance == null || instance.webDriver == null) {
            instance = new WebDriverManager();
        }
        return instance;
    }

    /**
     * Initializes the settings for the driver.
     */
    private void initialize() {
        int implicitWaitTime = 20;
        int explicitWaitTime = 30;
        int waitSleepTime = 10;

        ChromeDriverManager.getInstance(CHROME).setup();
//        FirefoxDriverManager.getInstance(FIREFOX).setup();
        this.webDriver = new ChromeDriver();
        this.webDriver.manage().window().maximize();
        this.webDriver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        webDriverWait = new WebDriverWait(webDriver, explicitWaitTime,waitSleepTime);
    }

    /**
     * Gets the WebDriver.
     *
     * @return WebDriver.
     */
    public WebDriver getWebDriver() {
        return webDriver;
    }

    /**
     * Gets the WebDriver Wait.
     *
     * @return WebDriverWait.
     */
    public WebDriverWait getWait() {
        return webDriverWait;
    }

    /**
     * Closes all the browser instances.
     */
    public void quitDriver() {
          webDriver.quit();
          webDriver = null;
    }
}
