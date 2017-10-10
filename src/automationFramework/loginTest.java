package automationFramework;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class loginTest extends TestBase  {
	
	
	
	@Before
	public void StartBrowser() throws IOException {
		initData();
	}

	@After
	public void ClosingBrowser() {
		closeBrowser();
	}
	
	@Test
	public void successLogin() {
		initBrowser();
		driver.get(appUrl);

		// Enter your real User Name and Password bellow.
		logIn("Luke", "Skywalker");
		Assert.assertEquals("Hello Luke", driver.findElement(By.id("greetings")).getText());
	}
	
	@Test
	// User name case sensitive
	public void failLogin() {
		initBrowser();
		driver.get(appUrl);

		// Enter your real User Name and Password bellow.
		logIn("luke", "Skywalker");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("#login-form > fieldset > p.error-message.ng-binding"),"Invalid username or password!"));
		Assert.assertEquals("Invalid username or password!", driver.findElement(By.cssSelector("#login-form > fieldset > p.error-message.ng-binding")).getText());
	}
	
	@Test
	// invalid user and password
	public void invalidLogin() {
		initBrowser();
		driver.get(appUrl);

		// Enter your real User Name and Password bellow.
		logIn("aa", "1ywalker");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("#login-form > fieldset > p.error-message.ng-binding"),"Invalid username or password!"));
		Assert.assertEquals("Invalid username or password!", driver.findElement(By.cssSelector("#login-form > fieldset > p.error-message.ng-binding")).getText());
	}
	
	@Test
	// abnormal user name
	public void abnormalLogin() {
		initBrowser();
		driver.get(appUrl);

		// Enter your real User Name and Password bellow.
		logIn("******aa12412@!@#$&*254asdfasgsfgsdfgdsgsdfgdshsh", "Skywalker");
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("#login-form > fieldset > p.error-message.ng-binding"),"Invalid username or password!"));
		Assert.assertEquals("Invalid username or password!", driver.findElement(By.cssSelector("#login-form > fieldset > p.error-message.ng-binding")).getText());
	}

}
