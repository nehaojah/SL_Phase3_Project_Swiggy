package stepDefinitions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StepDefinition {

	public static WebDriver driver;
	public static ExtentSparkReporter spark;
	public static ExtentReports extent = new ExtentReports();
	public static ExtentTest logger;

	@Before
	public void openBrowser() {
		driver = new ChromeDriver();
		WebDriverManager.chromedriver().setup();
		driver.manage().window().maximize();

		spark = new ExtentSparkReporter("./TestReport/ExtentSparkReport.html");

		spark.config().setTheme(Theme.STANDARD);

		extent.attachReporter(spark);

	}

	@Given("The user is on the Webpage")
	public void the_user_is_on_the_webpage() {
		driver.get("https://www.swiggy.com/");
		logger = extent.createTest("Swiggy Add to Cart Testcase");

		logger.info("Test Case Started");

		logger.pass("Browser Opened");
		logger.pass("User is on the Webpage");

	}

	// Negative Testcase1

	@When("The user enters the invalid Delivery location and clicks on Find Food button")
	public void the_user_enters_the_invalid_delivery_location_and_clicks_on_find_food_button(
			io.cucumber.datatable.DataTable dataTable) {
		logger = extent.createTest("Negative testcase -Invalid Delivery Location");

		List<List<String>> data = dataTable.asLists(String.class);
		String del_location = data.get(1).get(0);

		System.out.println(del_location);

		WebElement locationField = driver.findElement(By.id("location"));
		locationField.sendKeys(del_location);
		WebElement findBtn = driver.findElement(By.xpath("//span[text()=\"FIND FOOD\"]"));
		findBtn.click();
		logger.pass("Invalid Delivery location is entered");
	}

	@Then("Verify if the Error Message is displayed")
	public void verify_if_the_error_message_is_displayed(io.cucumber.datatable.DataTable dataTable) {
		WebElement findBtn = driver.findElement(By.xpath("//span[text()=\"FIND FOOD\"]"));
		findBtn.click();
		WebElement ErrorMessage = driver.findElement(By.xpath("//div[text()='Enter your delivery location']"));

		String ExpectedMessage = ErrorMessage.getText();

		List<List<String>> data = dataTable.asLists(String.class);
		String TestData = data.get(1).get(0);

		if (ExpectedMessage.contains(TestData)) {
			System.out.println("Please Enter Valid Loaction");
		} else {
			System.out.println("Redirected to Homepage ");
		}
		logger.pass("Valid Error Message is displayed");
	}

	// Negative Testcase2

	@When("The user does not enter any Delivery location and clicks on Find Food button")
	public void the_user_does_not_enter_any_delivery_location_and_clicks_on_find_food_button() {

		logger = extent.createTest("Negative testcase - Blank Delivery Location");

		WebElement findBtn = driver.findElement(By.xpath("//span[text()=\"FIND FOOD\"]"));
		findBtn.click();
		logger.pass(" Delivery location is kept Blank");
	}

//Positive Testcase 

	@When("The user enters the Delivery location")
	public void the_user_enters_the_delivery_location(DataTable dataTable) {
		// One Header one Line
		logger = extent.createTest(" Positive testcase ");
		List<List<String>> data = dataTable.asLists(String.class);
		String del_location = data.get(1).get(0);

		System.out.println(del_location);

		WebElement locationField = driver.findElement(By.id("location"));
		locationField.sendKeys(del_location);
		logger.pass("Valid Delivery location is entered");
	}

	@Then("The user selects the entered location from the dropdown")
	public void the_user_selects_the_entered_location_from_the_dropdown() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement loc_dropdown = driver
				.findElement(By.xpath("//span[text()='Sector 19, Noida, Uttar Pradesh, India']"));
		loc_dropdown.click();
		logger.pass("User selected Delivery location");

	}

	@And("The user is navigated on Products homepage")
	public void the_user_is_navigated_on_Products_homepage() {

		// Verify the user is on the Products homepage
		String expectedTitle = "Order food online from India's best food delivery service. Order from restaurants near you";
		String actualTitle = driver.getTitle();
		Assert.assertEquals(expectedTitle, actualTitle);
		logger.pass("Products homepage is correct");
	}

	@Then("Verify if the user selected location is displayed")
	public void verify_if_the_user_selected_location_is_displayed(DataTable dataTable) throws InterruptedException {

		List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
		String expectedSector = data.get(0).get("Sector");
		String expectedLocation = data.get(0).get("Location");

		WebElement sectorField = driver.findElement(By.xpath("//span[text()=\"Sector 19\"]"));
		WebElement locationField = driver
				.findElement(By.xpath("//span[text()=\" Noida, Uttar Pradesh 201301, India\"]"));

		String actualSector = sectorField.getText();
		String actualLocation = locationField.getText();

		// Verify the data
		Assert.assertEquals(expectedSector, actualSector);
		Assert.assertEquals(expectedLocation, actualLocation);
		Thread.sleep(3000);
		logger.pass("User Selected location is correct");
	}

	@And("The user clicks on the Search link")
	public void the_user_clicks_on_the_search_link() {

		WebElement searchLink = driver.findElement(By.xpath("//span[text()=\"Search\"]"));
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		searchLink.click();

		logger.pass("User successfully clicked on search");
	}

	@Then("Verify if user is navigated on Search page")
	public void verify_if_user_is_navigated_on_search_page() {
		String currentURL = driver.getCurrentUrl();
		boolean isSearchPage = currentURL.contains("/search");
		Assert.assertTrue(isSearchPage, "The current page is not a search page");
		logger.pass("User is on Search Page");
	}

	@Then("The user enters the Restaurant Name")
	public void the_user_enters_the_restaurant_name(DataTable dataTable) {

		List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
		String restaurant_name = data.get(0).get("Restaurant_Name");
		WebElement searchTextField = driver.findElement(By.xpath("//input[@class='_2FkHZ']"));
		searchTextField.sendKeys(restaurant_name);

		logger.pass("User enters the Restaurant Name successfully");
	}

	@Then("The searched restaurant is displayed")
	public void the_searched_restaurant_is_displayed() {
		WebElement restaurantElement = driver.findElement(By.xpath("//input[@class='_2FkHZ']"));
		boolean isDisplayed = restaurantElement.isDisplayed();
		Assert.assertTrue(isDisplayed, "The searched restaurant is not displayed");

		logger.pass("Searched Restaurant Name is dispalyed successfully");
	}

	@And("The user clicks on the search result")
	public void The_user_clicks_on_the_search_result() {
		WebElement searchResult = driver.findElement(By.xpath("//div[@class='RNzoC']"));
		searchResult.click();

		WebElement restaurantName = driver
				.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[1]/div[2]/div/div/div[3]/div[1]/div/a"));
		restaurantName.click();
		logger.pass("User is able to click on search result successfully");
	}

	@Then("Verify that user is navigated to restaurant Menu page")
	public void verify_that_user_is_navigated_to_restaurant_menu_page() {
		String currentURL = driver.getCurrentUrl();
		boolean isSearchPage = currentURL.contains("/restaurants");
		Assert.assertTrue(isSearchPage, "The current page is not a Restaurant Menu Page");
		logger.pass("Restaurant Menu page is dispalyed successfully");
	}

	@Then("User selects a Product and clicks on ADD button")
	public void user_selects_a_product_and_clicks_on_add_button() {

		// WebElement product =
		// driver.findElement(By.xpath("//h3[@class='styles_itemNameText__3ZmZZ' and
		// contains(text(),'Classic Cold Coffee') ]"));
		WebElement addProduct = driver.findElement(
				By.xpath("//*[@id=\"cid-Sides___Beverage\"]/div[1]/div/div[5]/div[1]/div/div[2]/div[2]/div/div[1]"));
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		addProduct.click();
		logger.pass("User is able to add a selected product to cart successfully");
	}

	@Then("verify that product is added to cart and view cart icon is displayed")
	public void verify_that_product_is_added_to_cart_and_view_cart_icon_is_displayed() {

		WebElement viewCart = driver.findElement(
				By.xpath("//*[@id=\"view-cart-btn\"]/span/span[2]/span"));

		if (viewCart.isDisplayed()) {
			System.out.println("Cart Icon is Displayed");
		} else {
			System.out.println("Cart Icon is not displayed");
		}

		WebElement clickCart = driver.findElement(By.xpath("//a[@class='_1T-E4' and contains(@href, 'checkout')]"));

		clickCart.click();
		logger.pass("User selected product is added to cart successfully");

	}

	@Then("Verify that product and quantity dispalyed is same as what user selected")
	public void verify_that_product_and_quantity_dispalyed_is_same_as_what_user_selected(DataTable dataTable) {

		List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
		String expectedProduct = data.get(0).get("ProductName");
		String expectedQty = data.get(0).get("Quantity");

		WebElement addedItemName = driver.findElement(By.xpath("//div[@class='_33KRy']"));
		WebElement addedItemQty = driver.findElement(By.xpath("//div[@class='_2zAXs']"));

		String actualProduct = addedItemName.getText();
		String actualQty = addedItemQty.getText();
		System.out.println("::" + actualProduct + " " + actualQty);
		// Verify the data
		Assert.assertEquals(expectedProduct, actualProduct);
		Assert.assertEquals(expectedQty, actualQty);
		logger.pass("Product and Quantity in Cart is same as what user has selected");

	}

	@After

	public void closeBrowser() {
		extent.flush();
		driver.quit();
	}
}
