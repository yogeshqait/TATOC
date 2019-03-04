import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Advancedtatoc {
	public String baseUrl = "http://10.0.1.86/tatoc/advanced/hover/menu";
	public String driverPath = "/home/yogesh/ooo/chromedriver";
	public WebDriver driver;

	@BeforeClass
	public void launchBrowser() {
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(baseUrl);
	}

	@AfterClass
	public void terminateBrowser() {
		//driver.quit();
	}
	
	@Test
	public void hoverMenu() {
		WebElement menu2 = driver.findElement(By.className("menutop m2"));
		WebElement goNext = driver.findElement(By.cssSelector(".menutop m2:nth child(5)"));
		Actions action = new Actions(driver);
		action.moveToElement(menu2).click(goNext).build().perform();
	}
}
