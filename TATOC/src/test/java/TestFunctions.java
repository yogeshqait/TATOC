import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestFunctions {
	public String baseUrl = "http://10.0.1.86/tatoc/basic/grid/gate";
	public String driverPath = "/home/yogesh/ooo/chromedriver";
	public WebDriver driver;

	@BeforeClass
	public void launchBrowser() {
		//ChromeOptions options = new ChromeOptions();
        	//options.addArguments("--no-sandbox");
		//options.addArguments('--headless');
        	//options.addArguments("--disable-dev-shm-usage");
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver(options);
		driver.get(baseUrl);
	}

	@AfterClass
	public void terminateBrowser() {
		driver.quit();
	}

	@Test
	public void gridGate() {
		WebElement greenBox = driver.findElement(By.className("greenbox"));
		greenBox.click();
		System.out.println(driver.getCurrentUrl());
	}

	@Test(dependsOnMethods = { "gridGate" })
	public void frameDungeon() {
		WebDriver mainFrame = driver.switchTo().frame("main");
		WebElement repaint = driver.findElement(By.linkText("Repaint Box 2"));
		WebElement answerbox = mainFrame.findElement(By.id("answer"));
		String answer = answerbox.getAttribute("class");
		WebDriver childFrame = driver.switchTo().frame("child");
		WebElement box = childFrame.findElement(By.id("answer"));
		String boxans = box.getAttribute("class");
		driver.switchTo().parentFrame();
		while (!answer.contentEquals(boxans)) {
			repaint.click();
			WebDriver chldFrame = driver.switchTo().frame("child");
			WebElement boxx = chldFrame.findElement(By.id("answer"));
			String boxansw = boxx.getAttribute("class");
			boxans = boxansw;
			driver.switchTo().parentFrame();
		}
		WebElement next = driver.findElement(By.linkText("Proceed"));
		next.click();
	}

	@Test(dependsOnMethods = { "frameDungeon" })
	public void dragAround() {
		WebElement dragbox = driver.findElement(By.id("dragbox"));
		WebElement dropbox = driver.findElement(By.id("dropbox"));
		Actions act = new Actions(driver);
		act.dragAndDrop(dragbox, dropbox).build().perform();
		WebElement next = driver.findElement(By.linkText("Proceed"));
		next.click();
	}

	@Test(dependsOnMethods = { "dragAround" })
	public void launchPopupWindow() {
		WebElement popupWindow = driver.findElement(By.linkText("Launch Popup Window"));
		popupWindow.click();
		List<String> windows = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(windows.get(1));
		WebElement name = driver.findElement(By.id("name"));
		name.sendKeys("ogden");
		WebElement submit = driver.findElement(By.id("submit"));
		submit.click();
		driver.switchTo().window(windows.get(0));
		WebElement next = driver.findElement(By.linkText("Proceed"));
		next.click();
	}
	
	@Test(dependsOnMethods = { "launchPopupWindow" })
	public void cookieHandling() {
		WebElement getToken = driver.findElement(By.linkText("Generate Token"));
		getToken.click();
		WebElement token = driver.findElement(By.id("token"));
		String tokenValue = token.getText();
		String[] tt = tokenValue.split(": ");
		Cookie tokenCookie = new Cookie("Token", tt[1]);
		driver.manage().addCookie(tokenCookie);
		WebElement next = driver.findElement(By.linkText("Proceed"));
		next.click();
	}

}
