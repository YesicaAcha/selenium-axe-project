import com.deque.html.axecore.extensions.WebDriverExtensions;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import core.WebDriverManager;
import core.utils.ViolationsReporter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class PivotalAccessibilityTests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Get WebDriver instance, for this example new ChromeDriver()
        driver = WebDriverManager.getInstance().getWebDriver();

        // Navigate to Pivotal Tracker login page
        driver.get("https://www.pivotaltracker.com/");
    }

    @AfterEach
    public void tearDown() {
        WebDriverManager.getInstance().quitDriver();
    }

    /**
     * Tests accessibility for Pivotal Login page and prints a JSON report.
     */
    @Test
    public void testLoginPageAccessibilityWithJsonReport() throws IOException, OperationNotSupportedException {

        // Analyze the page with WebDriverExtensions
        Results axeResult = WebDriverExtensions.analyze(driver);

        // Assert the page has no violations and if it has add the report in Json format
        ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonReport = writer.writeValueAsString(axeResult);
        Assertions.assertTrue(axeResult.violationFree(), jsonReport);
    }

    /**
     * Tests accessibility for Pivotal Login page and prints a custom report.
     */
    @Test
    public void testLoginPageAccessibilityWithCustomReport() {

        // Analyze the page creating a new AxeBuilder
        Results axeResult = new AxeBuilder().analyze(driver);

        // Assert the page has no violations and if it has add the custom report
        Assertions.assertTrue(axeResult.violationFree(), ViolationsReporter.buildCustomReport(axeResult));
    }

    /**
     * Tests the accessibility for a specific element and its children.
     */
    @Test
    public void testElementAccessibility() throws IOException, OperationNotSupportedException {
        WebElement header = driver.findElement(By.cssSelector(".header"));
        Results axeResult = WebDriverExtensions.analyze(driver, header);
        Assertions.assertTrue(axeResult.violationFree(), ViolationsReporter.buildCustomReport(axeResult));
    }


    /**
     * Tests the accessibility for a specific element and its children.
     */
    @Test
    public void testElementAccessibilityWithInclude() {
        Results axeResult = new AxeBuilder()
                .include(Collections.singletonList(".header"))
                .analyze(driver);
        Assertions.assertTrue(axeResult.violationFree(), ViolationsReporter.buildCustomReport(axeResult));
    }

    /**
     * Tests the accessibility for a page excluding a specific element and its children.
     */
    @Test
    public void testAccessibilityExcludingElement() {
        Results axeResult = new AxeBuilder()
                .exclude(Collections.singletonList(".header"))
                .analyze(driver);
        Assertions.assertTrue(axeResult.violationFree(), ViolationsReporter.buildCustomReport(axeResult));
    }

    /**
     * Tests accessibility for Pivotal Login page with specific rules.
     */
    @Test
    public void testAccessibilityWithOnlyRules() {
        Results axeResult = new AxeBuilder()
                .withOnlyRules(Arrays.asList("color-contrast", "duplicate-id"))
                .analyze(driver);
        Assertions.assertTrue(axeResult.violationFree(), ViolationsReporter.buildCustomReport(axeResult));
    }

    /**
     * Tests accessibility for Pivotal Login page disabling rules.
     */
    @Test
    public void testAccessibilityDisablingRules() {
        Results axeResult = new AxeBuilder()
                .disableRules(Arrays.asList("link-name", "region"))
                .analyze(driver);
        Assertions.assertTrue(axeResult.violationFree(), ViolationsReporter.buildCustomReport(axeResult));
    }

    /**
     * Tests accessibility for Pivotal Login page with tags.
     */
    @Test
    public void testAccessibilityWithTags() {
        Results axeResult = new AxeBuilder()
                .withTags(Arrays.asList("wcag2a", "wcag21aa"))
                .analyze(driver);
        Assertions.assertTrue(axeResult.violationFree(), ViolationsReporter.buildCustomReport(axeResult));
    }

    /**
     * Tests accessibility for Pivotal Login page with tags and disabling rules.
     */
    @Test
    public void testAccessibilityWithTagsDisablingRules() {
        Results axeResult = new AxeBuilder()
                .withTags(Collections.singletonList("wcag2a"))
                .disableRules(Collections.singletonList("color-contrast"))
                .analyze(driver);
        Assertions.assertTrue(axeResult.violationFree(), ViolationsReporter.buildCustomReport(axeResult));
    }
}
