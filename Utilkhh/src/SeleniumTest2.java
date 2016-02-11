import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Sleeper;
public class SeleniumTest2 {

	public WebDriver driver = null;//new FirefoxDriver();
	public SeleniumTest2() throws Exception {
//		FirefoxProfile profile = new FirefoxProfile();
//	    URL url = this.getClass().getResource("/modify_headers-0.7.1.1-fx.xpi");
////	    File modifyHeaders = modifyHeaders = new File(url.toURI());
//
//	    profile.setEnableNativeEvents(false);
////	    profile.addExtension(modifyHeaders);
//
//	    profile.setPreference("modifyheaders.headers.count", 1);
//	    profile.setPreference("modifyheaders.headers.action0", "Add");
//	   //profile.setPreference("modifyheaders.headers.name0", SOME_HEADER);
//	    profile.setPreference("modifyheaders.headers.value0", "true");
//	    profile.setPreference("modifyheaders.headers.enabled0", true);
//	    profile.setPreference("modifyheaders.config.active", true);
//	    profile.setPreference("modifyheaders.config.alwaysOn", true);
//
//	    DesiredCapabilities capabilities = new DesiredCapabilities();
//	    capabilities.setBrowserName("firefox");
//	    capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
//	    capabilities.setCapability(FirefoxDriver.PROFILE, profile);
//		driver = new FirefoxDriver(capabilities);
//		driver = new FirefoxDriver();
	}
	/**
	 * Open the test website.
	 */
	public void openTestSite() {
//		driver.navigate().to("https://connect.garmin.com/ko-KR/signin");
//		driver.navigate().to("http://127.0.0.1:8080/webTest/");
		
	}

	/**
	 * 
	 * @param username
	 * @param Password
	 * 
	 *            Logins into the website, by entering provided username and
	 *            password
	 * @throws IOException 
	 */
	public void login(String username, String Password) throws IOException {
//		Runtime.getRuntime().exec("C:/Diiinnovation/HideNSeek.exe 0 \"" + "Mozilla Firefox" + "\"");
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//driver.get("http://somedomain/url_that_delays_loading");
//		WebElement myDynamicElement = driver.findElement(By.id("myDynamicElement"));
		//driver.switchTo().defaultContent(); 
		//driver.findElement(By.xpath("//button[text()='OK']")).click(); 
		//driver.switchTo().frame(driver.findElement(By.id("frameId")));
		//driver.switchTo().parentFrame();
		//driver.switchTo().defaultContent();
		driver.switchTo().frame("gauth-widget-frame");
		
		WebElement userName_editbox = driver.findElement(By.id("username"));
		WebElement password_editbox = driver.findElement(By.id("password"));
		WebElement submit_button = driver.findElement(By.id("login-btn-signin"));
		//driver.findElement(By.xpath("//input[@value='Login']"));

		userName_editbox.sendKeys(username);
		password_editbox.sendKeys(Password);
		submit_button.click();

	}

	/**
	 * grabs the status text and saves that into status.txt file
	 * 
	 * @throws IOException
	 */
	public void getText() throws IOException {
		String text = driver.findElement(By.xpath("//div[@id='case_login']/h3")).getText();
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("status.txt"), "utf-8"));
		writer.write(text);
		writer.close();

	}

	/**
	 * Saves the screenshot
	 * 
	 * @throws IOException
	 */
	public void saveScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("screenshot.png"));
	}

	public void closeBrowser() {
		driver.close();
	}

	public static void main(String[] args) throws Exception {
		SeleniumTest2 webSrcapper = new SeleniumTest2();
		webSrcapper.openTestSite();
		webSrcapper.login("visualkhh@gmail.com", "Rnadmfdnlgog01");
		webSrcapper.getText();
		webSrcapper.saveScreenshot();
		webSrcapper.closeBrowser();
	}
}
