package testNG_project;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CRM_TestNG {
	String browser;
	WebDriver driver;
	String url;
	
	By USERNAME = By.xpath("//input[@id='username']");
	By PASSWORD = By.xpath("//input[@id='password']");
	By SIGNIN_BUTTON = By.xpath("//button[@class='btn btn-success block full-width']");
	By DASBORAD_HEADER = By.xpath("//h2[text()=' Dashboard ']");
	By CUSTOMERS_BUTTON = By.xpath("//span[text()='Customers']");
	By ADD_CUSTOMERS_BUTTON = By.xpath("//a[text()='Add Customer']");
	By ADD_CONTACT_HEADER = By.xpath("//h5[text()='Add Contact']");
	
	By FULL_NAME = By.xpath("//input[@id='account']");
	By COMPANY_NAME = By.xpath("//select[@id='cid']");
	By EMAIL_ADDRESS = By.xpath("//input[@id='email']");
	By PHONE_NUMBER = By.xpath("//input[@id='phone']");
	By HOME_ADDRESS = By.xpath("//input[@id='address']");
	By CITY = By.xpath("//input[@id='city']");
	By STATE = By.xpath("//input[@id='state']");
	By ZIPCODE = By.xpath("//input[@id='zip']");
	By COUNTRY = By.xpath("//select[@id='country']");
//	By TAGS = By.xpath("//span[@class='select2-selection select2-selection--multiple']/descendant::input");
//	By CURRENCY = By.xpath("//select[@id='currency']/child::option");
//	By GROUP = By.xpath("//select[@name='group']");
//	By SAVE_BUTTON = By.xpath("//button[@class='md-btn md-btn-primary waves-effect waves-light']");
//	
	
	@BeforeTest
	public void readconfig() {
		//fileReader/inputstream/bufferreader/Scanner >>these gives us provision to read files
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used = " + browser );
			url = prop.getProperty("url");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver_31.exe");
			driver = new FirefoxDriver();
		}
		else {
			System.out.println("Failed");
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(url);;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(priority=1)
	public void loginTest() {

		

		driver.findElement(USERNAME).sendKeys("demo@techfios.com");
		driver.findElement(PASSWORD).sendKeys("abc123");
		driver.findElement(SIGNIN_BUTTON).click();

		String dashBoardHeaderText = driver.findElement(DASBORAD_HEADER).getText();
		System.out.println(dashBoardHeaderText);
		Assert.assertEquals(dashBoardHeaderText, "Dashboard", "Wrong Page!!");

	}
	
	@Test(priority=2)
	public void addCustomerTest() {
		loginTest();
		
		driver.findElement(CUSTOMERS_BUTTON).click();
		driver.findElement(ADD_CUSTOMERS_BUTTON).click();
		
		String addContactHeaderText= driver.findElement(ADD_CONTACT_HEADER).getText();
		System.out.println(addContactHeaderText);
		Assert.assertEquals(addContactHeaderText, "Add Contact","Wrong PAge");
		
		
		driver.findElement(FULL_NAME).sendKeys("Neon" + generateNum(999));	
		
		
		selectFromDropdown(COMPANY_NAME,"Amazon");

		driver.findElement(EMAIL_ADDRESS).sendKeys("abcd" + generateNum(99) + "@gmail.com");
		driver.findElement(PHONE_NUMBER).sendKeys("508-765-1" + generateNum(9999));
		driver.findElement(HOME_ADDRESS).sendKeys(generateNum(999) + "dale ave");
		driver.findElement(CITY).sendKeys("Malone");
		driver.findElement(STATE).sendKeys("Alabama");
		driver.findElement(ZIPCODE).sendKeys("09876");
	
		selectFromDropdown(COUNTRY,"United States");
//		driver.findElement(TAGS).sendKeys("asd789");
//		driver.findElement(CURRENCY).click();
//		driver.findElement(GROUP).sendKeys("Java");
//		driver.findElement(SAVE_BUTTON).click();
//		
	}

	public void selectFromDropdown(By locator, String visibleText) {
		Select sel = new Select(driver.findElement(locator));
		sel.selectByVisibleText(visibleText);
		
	}

	public int generateNum(int boundaryNum) {
		Random rnd = new Random();
		rnd.nextInt(boundaryNum);
		return boundaryNum;
	}

	
	
	

}
