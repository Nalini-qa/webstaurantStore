package testScripts;

import org.testng.annotations.Test;

import objects.HomePage;
import utilities.DriverScript;
import utilities.PropertiesManager;

import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;

public class SearchTest {
	private WebDriver driver = null;
	private DriverScript driverScript = new DriverScript();
	PropertiesManager propertiesManager = new PropertiesManager();
	private WebDriverWait wait = null;

	//check for test search result
	@Test
	public void verifySearchResult() {
		try {
			searchTextInSite(driver);
			//get all the elements and verify whether all
			wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
			List<WebElement> searchElements = driver.findElements(HomePage.searchResultText());
			wait.until(ExpectedConditions.visibilityOfAllElements(searchElements));
			//Check the search criteria
			boolean checkSearch = false;
			String whichResult = null;
			for (int i = 1; i <= searchElements.size(); i++) {
				WebElement webElement = driver.findElement(By.cssSelector("div#product_listing div.ag-item:nth-of-type("+i+") a.description"));
				((JavascriptExecutor) (driver)).executeScript("arguments[0].scrollIntoView(true);", webElement);
				whichResult = webElement.getText().trim();
				checkSearch = whichResult.toLowerCase().contains("table");
				if (!checkSearch) 
					break;
				else
					continue;
				
			}
			assertTrue(checkSearch,"all search result doesnot contains the expected result "+whichResult+" doesnt contain expected result");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	//Check for add to cart and empty
	@Test
	public void verifyAddingIntoCart() {
		try {
			searchTextInSite(driver);
			wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
			List<WebElement> searchElements = driver.findElements(HomePage.searchResultText());
			wait.until(ExpectedConditions.visibilityOfAllElements(searchElements));
			int lastIndex = searchElements.size()-1;
			Reporter.log("last index of search result is "+lastIndex);
			//scroll to the end
			((JavascriptExecutor) (driver)).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.cssSelector("div#product_listing div.ag-item:nth-of-type("+lastIndex+") a.description")));
		//	((JavascriptExecutor) (driver)).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(HomePage.addToCartButton(lastIndex)));
			Reporter.log("Scrolling to the end for last index ",true);
			driverScript.clickElement(driver, 60, HomePage.addToCartButton(lastIndex), "last element in search");
		    driverScript.clickElement(driver, 60, HomePage.viewCartNotificationButton(), "View Cart in notification");
			//click on view cart
			assertEquals(Integer.parseInt(driver.findElement(HomePage.itemCount()).getText().trim()), 1);
			driverScript.clickElement(driver, 60, HomePage.viewCart(), "View Cart button");
			//Click on empty cart
			driverScript.clickElement(driver, 60, HomePage.emptyCartButton(), "Empty Cart Button");
			
			driverScript.clickElement(driver, 60, HomePage.sureEmptyButton(), "Are you sure empty Cart");
			driverScript.waitforVisibility(driver, 60, By.xpath("//p[contains(.,'Your cart is empty.')]"));
			assertEquals(driver.findElement(By.xpath("//p[contains(.,'Your cart is empty.')]")).isDisplayed(), true);
			driverScript.waitforVisibility(driver, 60, HomePage.itemCount());
			assertEquals(Integer.parseInt(driver.findElement(HomePage.itemCount()).getText().trim()), 0);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
	}
	

	@BeforeMethod
	public void settinUpDriver() {
		driver = driverScript.setUp();
		wait = new WebDriverWait(driver, 60);
	}

	@AfterMethod
	public void tearDown() {
		driverScript.tearDown(driver);
	}
	
	public void searchTextInSite(WebDriver driver) {
		Reporter.log("Searching of text begins.....",true);
		driverScript.textInput(driver, 60, HomePage.searchTextBox(), System.getProperty("ui.search.text"), "Search Item text");
		driverScript.clickElement(driver, 60, HomePage.searchButton(), "Search button");
		Reporter.log("Searching of text ends.....",true);
	}

}
