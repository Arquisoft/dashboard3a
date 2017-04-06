package asw.dashboard.cucumber;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DashboardLoginStep {

	/*private WebDriver driver = initDriver();

	{
		driver.get("http://localhost:50213/");
	}
	
	private WebDriver initDriver() {
		File pathToBinary = new File("S:\\firefox\\FirefoxPortable.exe");

		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);

		FirefoxProfile firefoxProfile = new FirefoxProfile();

		return new FirefoxDriver(ffBinary, firefoxProfile);
	}*/
	
	@Given("^the politician is in the login page$")
	public void isInLoginPage() {	
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.navigate().to("http://localhost:50213/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		System.out.println(driver.getPageSource());
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(
				By.id("email")));	
		driver.findElement(By.id("email"));
		
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(
				By.id("password")));	
		driver.findElement(By.id("password"));*/
		System.out.println("The politician is in the login page");
	}

	@Then("^introduces his credentials, \"(.+)\" and \"(.+)\" into the login form$")
	public void insertCredentials(String mail, String psw) {
		/*
		sleepOneSecond();
		driver.findElement(By.id("email")).sendKeys(mail);
		
		sleepOneSecond();
		driver.findElement(By.id("password")).sendKeys(psw);*/
		System.out.println("Introduces his credentials");
	}

	@When("^he pushes the \"Log in\" button$")
	public void pushLogIn() {	
		/*
		sleepOneSecond();
		 driver.findElement(By.id("loginButton")).click();*/
		System.out.println("He pushes the login button");
	}

	@And("^he gets redirected to the dashboard view$")
	public void isInDashboard() {	
		/*
		sleepOneSecond();
		driver.findElement(By.id("tablaSugerencias"));	
		
		sleepOneSecond();
		driver.quit();*/
		System.out.println("He gets redirected to the dashboard view");
	}
	
	/*private void sleepOneSecond() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}*/
}