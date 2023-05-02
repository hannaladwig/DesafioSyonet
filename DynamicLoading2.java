
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.*;
import com.aventstack.extentreports.reporter.*;

public class DynamicLoading {

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
    logger = extent.createTest("Teste de exemplo", "Verificar o texto do elemento de Dynamic loading 2");
  }

  @Test
  public void testDynamicLoading2() {
    driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
    logger.log(Status.INFO, "Acessando página Dynamic Loading 2");
    
    WebElement startButton = driver.findElement(By.cssSelector("#start button"));
    startButton.click();
    logger.log(Status.INFO, "Clicando no botão Start");
    
    WebDriverWait wait = new WebDriverWait(driver, 10);
    By loadingIndicator = By.id("loading");
    wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIndicator));
    logger.log(Status.INFO, "Aguardando a carga do elemento");
    
    WebElement finishText = driver.findElement(By.id("finish"));
    String expectedText = "Hello World!";
    String actualText = finishText.getText();
    logger.log(Status.INFO, "Lendo o texto do elemento Finish");
    
    try {
      Assert.assertEquals(actualText, expectedText);
      logger.log(Status.PASS, "O texto esperado foi encontrado");
    } catch (AssertionError e) {
      logger.log(Status.FAIL, "O texto esperado não foi encontrado");
      String screenshotPath = "screenshot.png";
      takeScreenshot(screenshotPath);
      logger.fail(MarkupHelper.createLabel("Screenshot da falha:", ExtentColor.RED));
      logger.addScreenCaptureFromPath(screenshotPath);
    }
  }

  @AfterMethod
  public void afterMethod() {
    driver.quit();
  }

  @AfterTest
  public void afterTest() {
    extent.flush();
  }

  private void takeScreenshot(String screenshotPath) {
    try {
      TakesScreenshot ts = (TakesScreenshot)driver;
      File source = ts.getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(source, new File(screenshotPath));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
