import java.io.*;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
public class SeleniumTest {

	public WebDriver driver = new FirefoxDriver();

	/**
	 * Open the test website.
	 */
	public void openTestSite() {
		driver.navigate().to("http://testing-ground.scraping.pro/login");
	}

	/**
	 * 
	 * @param username
	 * @param Password
	 * 
	 *            Logins into the website, by entering provided username and
	 *            password
	 */
	public void login(String username, String Password) {

//		WebElement userName_editbox = driver.findElement(By.id("usr"));
//		WebElement password_editbox = driver.findElement(By.id("pwd"));
//		WebElement submit_button = driver.findElement(By.xpath("//input[@value='Login']"));
//
//		userName_editbox.sendKeys(username);
//		password_editbox.sendKeys(Password);
//		submit_button.click();

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

	public static void main(String[] args) throws IOException {
		SeleniumTest webSrcapper = new SeleniumTest();
		webSrcapper.openTestSite();
		webSrcapper.login("admin", "12345");
		webSrcapper.getText();
		webSrcapper.saveScreenshot();
		webSrcapper.closeBrowser();
	}
}
