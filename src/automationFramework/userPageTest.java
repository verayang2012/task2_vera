package automationFramework;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class userPageTest extends TestBase  {
	
	@Before
	public void StartBrowser() throws IOException {
		initData();
		initBrowser();
		driver.get(appUrl);
		logIn("Luke", "Skywalker");
	}

	@After
	public void ClosingBrowser() {
		closeBrowser();
	}
	
	
	@Test
	public void addUserSuccesstest() {
		
		User user=new User();
		user.randomUser();
		addUser(driver,user);
		
		verifyUserCreated(user.firstName,user.lastName);
	}
	
	@Test
	public void editUserSuccessTest() {
		
		User user=new User();
		user.randomUser();
		addUser(driver,user);		
		selectEditUser(driver,user);
		User nuser=new User();
		nuser.randomUser();
		updateUser(driver,nuser);
		verifyUserCreated(nuser.firstName,nuser.lastName);
	}
	
	@Test
	public void editAndDeleteTest() {
		
		User user=new User();
		user.randomUser();
		addUser(driver,user);		
		selectEditUser(driver,user);
		driver.findElement(By.cssSelector("body > div > div > div > form > fieldset > div > p")).click();
		Alert alert = driver.switchTo().alert();
	    alert.accept(); // for OK
		verifyUserNotExisting(user.firstName,user.lastName);
	}
	
	@Test
	public void deleteFromListTest() {
		
		User user=new User();
		user.randomUser();
		addUser(driver,user);
		selectUser(driver,user);
		driver.findElement(By.id("bDelete")).click();
		Alert alert = driver.switchTo().alert();
	    alert.accept(); // for OK
	    closeBrowser();
	    //initData();
		initBrowser();
		driver.get(appUrl);
		logIn("Luke", "Skywalker");
	  
	    verifyUserNotExisting(user.firstName,user.lastName);
	}
	
	

	public void verifyUserCreated(String firstName, String lastName) {
		
		Boolean found=userFound(firstName,lastName);
		if(!found){
			fail("Not found");
		}
		
	}
	
	public void verifyUserNotExisting(String firstName, String lastName){
		
		Boolean found=userFound(firstName,lastName);
		if(found){
			fail("found");
		}
		
	}
	
	Boolean userFound(String firstName, String lastName)
	{
		Boolean found=false;
		WebElement elist=driver.findElement(By.id("employee-list"));
		List<WebElement> listOfLiTags = elist.findElements(By.tagName("li"));
		for(WebElement li : listOfLiTags) {
		    String text = li.getText();
		    //System.out.println(text);
		    if(text.contains(firstName+" "+lastName)) {
		    	found=true;
		    	//System.out.println("Found");
		        break;
		    }
		}
		return found;
		
	}

    public void addUser(WebDriver driver, User user){
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("bAdd")));
		
		driver.findElement(By.id("bAdd")).click();
		driver.findElement(By.cssSelector("input[ng-model='selectedEmployee.firstName']")).sendKeys(user.firstName);
		driver.findElement(By.cssSelector("input[ng-model='selectedEmployee.lastName']")).sendKeys(user.lastName);
		driver.findElement(By.cssSelector("input[ng-model='selectedEmployee.startDate']")).sendKeys(user.date);
		driver.findElement(By.cssSelector("input[ng-model='selectedEmployee.email']")).sendKeys(user.email);
		
		driver.findElement(By.cssSelector("button[type='submit'][class='main-button']")).click();
	}
    
    public void selectUser(WebDriver driver, User user){
	
	    String searchText = user.firstName+" "+user.lastName;
	    WebElement elist = driver.findElement(By.id("employee-list"));
	    
	    List<WebElement> options = elist.findElements(By.tagName("li"));
	    for (WebElement option : options){
	    	if (option.getText().contains(searchText)){
	        option.click(); // click the desired option
	        break;}
	    	}
	}
    
    public void selectEditUser(WebDriver driver, User user){
    	
	    selectUser(driver,user);
	    driver.findElement(By.id("bEdit")).click();
	}
    
public void updateUser(WebDriver driver, User user){
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sub-nav")));
		
		driver.findElement(By.cssSelector("input[ng-model='selectedEmployee.firstName']")).clear();
		driver.findElement(By.cssSelector("input[ng-model='selectedEmployee.firstName']")).sendKeys(user.firstName);
		driver.findElement(By.cssSelector("input[ng-model='selectedEmployee.lastName']")).clear();
		driver.findElement(By.cssSelector("input[ng-model='selectedEmployee.lastName']")).sendKeys(user.lastName);
		driver.findElement(By.cssSelector("input[ng-model='selectedEmployee.startDate']")).clear();
		driver.findElement(By.cssSelector("input[ng-model='selectedEmployee.startDate']")).sendKeys(user.date);
		driver.findElement(By.cssSelector("input[ng-model='selectedEmployee.email']")).clear();
		driver.findElement(By.cssSelector("input[ng-model='selectedEmployee.email']")).sendKeys(user.email);
		
		driver.findElement(By.cssSelector("button[type='submit'][class='main-button']")).click();
	}
}
