package io.cucumber.task;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {
	WebDriver driver = null;
	
    @Given("open web page")
    public void open_web_page() throws Exception {
    	//Initialize web browser instance.
    	driver = new FirefoxDriver();
    	// Get url parameter from command line, if empty uses default.
    	try {
    		String url = System.getProperty("url", "https://www.amazon.com");
	    	driver.navigate().to(url);
    	} catch(Exception e) {
    		exception_handler("Something went wrong when setting up url", e);
    	}
    }
    
    @When("test web page")
    public void test_web_page() throws Exception {
    	// Set max wait time for driver
    	WebDriverWait wait = new WebDriverWait(driver, 3);
    	String searchTextBox = "twotabsearchtextbox";
    	String lensButton = "nav-input";
    	String dropDownButton = "a-autoid-0-announce";
    	String highToLowSelector = "s-result-sort-select_2";
    	String titlesClass = "a-size-medium";
    	String Nikon = "Nikon";
    	
    	try {
        	// Type text to be searched text to textbox
        	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(searchTextBox))).click();
        	driver.findElement(By.id(searchTextBox)).sendKeys(Nikon);
        	driver.findElement(By.id(searchTextBox)).sendKeys(Keys.ENTER);        	
        	
        	// Click lens button
        	wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(lensButton))).click();
            // Click sort from dropdown menu
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(dropDownButton))).click();
            // Click sort from highest to lowest
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(highToLowSelector))).click();
            // Wait until sort is done
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className(titlesClass)));
            // Get list of elements in page
            List<WebElement> li = driver.findElements(By.className(titlesClass));
            // Click second item from the list
            li.get(1).click();
    	} catch(Exception e) {
    		exception_handler("Something went wrong in testing phase!", e);
    	}
    	
    }
        		
    @Then("assert results")
    public void assert_results() throws Exception {
    	String NikonD3X = "Nikon D3X";
    	try {
	    	// Get web page source
	    	String pageSource = driver.getPageSource();
	        // Check page source contains specific text
	        boolean found = pageSource.contains(NikonD3X);
	        // Assert text is found and raise assertion error if not
	        try {
	        	assertTrue("Text was not found from page source", found);
	        } catch(AssertionError e) {
	        	assertion_handler(e);
	        }
    	} catch(Exception e) {
    		exception_handler("Something went wrong in assertion!", e);
    	}
    }
    
    public void exception_handler(String message, Exception e) throws Exception {
		// Print message
		System.out.println(message);
		// Print StackTrace
		e.printStackTrace();
		//Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		//Call getScreenshotAs method to create image file
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		//Move image file to new destination
		File DestFile=new File("screenshot" + System.currentTimeMillis() + ".jpg");
		//Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);
    	// Quit driver
		driver.quit();
		throw e;
    }

	public void assertion_handler(AssertionError e) throws AssertionError, IOException{
		//Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		//Call getScreenshotAs method to create image file
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		//Move image file to new destination
		File DestFile=new File("screenshot" + System.currentTimeMillis() + ".jpg");
		//Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);
	    // Quit driver
		driver.quit();
		throw e;
	}
}
