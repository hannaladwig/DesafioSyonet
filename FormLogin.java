

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TestTheInternet {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest logger;

    @BeforeTest
    public void startReport() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @Test
    public void testFormAuthentication() {

        logger = extent.createTest("Test Form Authentication");

        // Set ChromeDriver path
        System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\chromedriver.exe");
        driver = new ChromeDriver();

        // Open Form Authentication page
        driver.get("http://the-internet.herokuapp.com/login");

        // Enter correct username and password
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verify successful login
        if (driver.findElement(By.cssSelector(".flash.success")).isDisplayed()) {
            logger.log(Status.PASS, MarkupHelper.createLabel("Login Successful!", ExtentColor.GREEN));
        } else {
            logger.log(Status.FAIL, MarkupHelper.createLabel("Login Failed!", ExtentColor.RED));
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "/screenshots/FormAuthenticationFailure.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.fail("Screenshot below: " + logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "/screenshots/FormAuthenticationFailure.png"));
        }

        // Logout
        driver.findElement(By.cssSelector(".icon-2x.icon-signout")).click();

        // Enter incorrect username and password
        driver.findElement(By.id("username")).sendKeys("invalidusername");
        driver.findElement(By.id("password")).sendKeys("invalidpassword");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Verify unsuccessful login
        if (driver.findElement(By.cssSelector(".flash.error")).isDisplayed()) {
            logger.log(Status.PASS, MarkupHelper.createLabel("Login Unsuccessful!", ExtentColor.GREEN));
            } else {
                logger.log(Status.FAIL, MarkupHelper.createLabel("Login Failed!", ExtentColor.RED));
                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "/screenshots/FormAuthenticationFailure.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.fail("Screenshot below: " + logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "/screenshots/FormAuthenticationFailure.png"));
            }
