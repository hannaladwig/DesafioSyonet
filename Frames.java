
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.*;
import com.aventstack.extentreports.reporter.*;

public class TesteAutomatizado {

  private WebDriver driver;
  private ExtentReports extent;
  private ExtentTest logger;

  @BeforeTest
  public void beforeTest() {
    extent = new ExtentReports();
    ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("report.html");
    extent.attachReporter(htmlReporter);
  }

  @BeforeMethod
  public void beforeMethod() {
    driver = new ChromeDriver();
    logger = extent.createTest("Teste de exemplo", "Ler o texto no frame superior do meio");
  }

  @Test
  public void testFramesNestedFrames() {
    driver.get("https://the-internet.herokuapp.com/nested_frames");
    logger.log(Status.INFO, "Acessando página Frames / Nested frames");
    
    driver.switchTo().frame("frame-top");
    logger.log(Status.INFO, "Mudando para o frame superior");
    
    WebElement middleFrame = driver.findElement(By.cssSelector("frame[src='frame_middle.html']"));
    driver.switchTo().frame(middleFrame);
    logger.log(Status.INFO, "Mudando para o frame do meio");
    
    String text = driver.findElement(By.tagName("body")).getText();
    logger.log(Status.INFO, "Lendo o texto presente no frame superior do meio: " + text);
  }

  @AfterMethod
  public void afterMethod() {
    driver.quit();
  }

  @AfterTest
  public void afterTest() {
    extent.flush();
  }

}
