package asw.dashboard.cucumber;

import cucumber.api.java.en.Given;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DashboardLoginStep {

	private WebDriver driver = (WebDriver) new FirefoxDriver();

	@Given("^the politician is in the login page$")
	public void isInLoginPage() {	
		sleepOneSecond();
		driver.navigate().to("http://localhost:8090/");

		sleepOneSecond();		
		driver.findElement(By.id("email"));
		
		sleepOneSecond();
		driver.findElement(By.id("password"));
	}

	@Then("^introduces his credentials, \"(.+)\" and \"(.+)\" into the login form$")
	public void insertCredentials(String mail, String psw) {
		
		sleepOneSecond();
		driver.findElement(By.id("email")).sendKeys(mail);
		
		sleepOneSecond();
		driver.findElement(By.id("password")).sendKeys(psw);
	}

	@When("^he pushes the \"Log in\" button$")
	public void pushLogIn() {	
		
		sleepOneSecond();
		 driver.findElement(By.id("loginButton")).click();
	}

	@And("^he gets redirected to the dashboard view$")
	public void isInDashboard() {	
		
		sleepOneSecond();
		driver.findElement(By.id("tablaSugerencias"));	
		
		sleepOneSecond();
		driver.quit();
	}
	
	private void sleepOneSecond() {
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}