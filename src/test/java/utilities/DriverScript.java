package utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class DriverScript {

	WebDriver driver;
	WebDriverWait webDriverWait;
	PropertiesManager propertiesManager = new PropertiesManager();
	int waitTime = Integer.parseInt(System.getProperty("ui.wait"));
	

	// Setting up browser driver
	public WebDriver setUp() {
		String browser = System.getProperty("ui.browser");
		try {
			switch (browser.toLowerCase()) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
				driver = new ChromeDriver();
				break;
			case "ie":
				// TODO
				break;
			case "ff":
				// TODO
				break;
			default:
				break;
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
			driver.get(System.getProperty("ui.url"));
			Reporter.log("Setting up " + browser + " driver with Url " + System.getProperty("ui.url"), true);
		} catch (Exception e) {
			Reporter.log("Issue in setting up " + browser + " driver with Url " + System.getProperty("ui.url")
					+ " due to " + e.getMessage(), true);
			Assert.fail(e.getMessage());
		}
		return driver;
	}

	public void tearDown(WebDriver driver) {
		if (driver != null) {
			driver.quit();
		}
	}

	public void textInput(WebDriver driver, int time, By locator, String data, String textBoxName)
			throws ElementNotFoundException, ElementNotVisibleException {
		webDriverWait = new WebDriverWait(driver, time == 0 ? waitTime : time);
		WebElement element = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		element.clear();
		element.sendKeys(data);
		Reporter.log(data + " is being entered in " + textBoxName);
	}

	public void clickElement(WebDriver driver, int time, By locator,String elementName)
			throws ElementNotFoundException, ElementNotVisibleException,ElementClickInterceptedException {
		webDriverWait = new WebDriverWait(driver, time == 0 ? waitTime : time);
		webDriverWait
				.until(ExpectedConditions.and(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator),
						ExpectedConditions.elementToBeClickable(locator)));
		driver.findElement(locator).click();
		Reporter.log(elementName+" has been clicked ");
	}
	
	public boolean  waitforVisibility(WebDriver driver, int time,By locator) {
		webDriverWait = new WebDriverWait(driver, time == 0 ? waitTime : time);
		return webDriverWait
		.until(ExpectedConditions.and(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)));
	}
	
	public boolean  waitforClickablity(WebDriver driver, int time,By locator) {
		webDriverWait = new WebDriverWait(driver, time == 0 ? waitTime : time);
		return webDriverWait
		.until(ExpectedConditions.and(ExpectedConditions.and(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator),
				ExpectedConditions.elementToBeClickable(locator))));
	}
}
