package objects;

import org.openqa.selenium.By;

public class HomePage {

	String locators;
	By by;
	
	public static By searchTextBox() {
		return By.id("searchval");
	}
	
	public static By searchButton() {
		return By.cssSelector("button[type='submit'][value='Search']");
	}
	
	public static By searchResultText() {
		return By.cssSelector("div#product_listing div.ag-item a.description");
	}
	
	public static By addToCartButton(int index) {
		return By.cssSelector("div#product_listing div.ag-item:nth-of-type("+index+") input.btn.btn-cart");
	}
	
	public static By viewCart() {
		return By.cssSelector("a[data-testid='cart-nav-link']");
	}
	
	public static By addedCardItem() {
		return By.cssSelector("div.details span.itemDescription.description a");
	}
	
	public static By emptyCartButton() {
		return By.cssSelector("a.emptyCartButton");
	}
	
	public static By sureEmptyButton() {
		return By.xpath("//button[text()='Empty Cart']");
	}
	
	public static By itemCount() {
		return By.cssSelector("span#cartItemCountSpan");
	}
	
	public static By viewCartNotificationButton() {
		return By.cssSelector("div.notification__action a:nth-of-type(1)");
	}
	
	public static By cartHeader() {
		return By.cssSelector("//h1[contains(.,'Cart')]");
	}
}
