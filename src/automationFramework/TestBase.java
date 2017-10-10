package automationFramework;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {
	public WebDriver driver = null;
	public boolean browserAlreadyOpen = false;
	public Properties SYSPARAM = null;
	public boolean isAlreadyLogIn = false;
	
	public String appUrl = "http://cafetownsend-angular-rails.herokuapp.com/login";
	
	// To Initialize .properties file.
		public void initData() throws IOException {
			SYSPARAM = new Properties();
			FileInputStream Ist = new FileInputStream(System.getProperty("user.dir") + "//src//SYSPARAM.properties");
			SYSPARAM.load(Ist);
		}

		public void initBrowser() {
			// Check If browser Is already opened during previous test execution.
			if (!browserAlreadyOpen) {
				// Read value of 'BrowserToTestIn' key from SYSPARAM.properties
				// file.
				// If key value Is MMF then execute If statement
				// If key value Is CHRM then execute else statement.
				if (SYSPARAM.getProperty("BrowserToTestIn").equals("MFF")) {
					driver = new FirefoxDriver();
				} else if (SYSPARAM.getProperty("BrowserToTestIn").equals("CHRM")) {
					// Write lines to open chrome browser.
					System.setProperty("webdriver.chrome.driver",
							"C:\\Users\\verayang\\Downloads\\chromedriver_win32\\chromedriver.exe");
					driver = new ChromeDriver();
				}
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				// At last browserAlreadyOpen flag will becomes true to not open new
				// browser when start executing next test.
				browserAlreadyOpen = true;
			}
		}

		// To Close Browser
		public void closeBrowser() {
			driver.quit();
			isAlreadyLogIn = false;
			browserAlreadyOpen = false;
		}
		
		// Can accept username and password as a string
		public void logIn(String username, String password) {
			// To check If already login previously then don't execute this
			// function.
			if (!isAlreadyLogIn) {
				// If Not login then login In to your account.
				String mainTitle = driver.getTitle();
				//System.out.println("Title is =============" + mainTitle);
		        Assert.assertEquals("CafeTownsend-AngularJS-Rails", mainTitle);
				driver.findElement(By.xpath("//input[@ng-model='user.name']")).sendKeys(username);
				driver.findElement(By.cssSelector("#login-form > fieldset > label:nth-child(4) > input")).sendKeys(password);
				driver.findElement(By.cssSelector("button[type='submit']")).click();

			}
			isAlreadyLogIn = true;
		}

}
