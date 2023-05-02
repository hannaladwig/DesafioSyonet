
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
public class AuthenticationTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ladwig\\Downloads\\chromedriver_win32/chromedriver.exe"); // ou seu caminho para o driver do chrome
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://the-internet.herokuapp.com/login");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test(priority = 1)
    public void testSuccessfulLogin() {
        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));

        username.sendKeys("tomsmith");
        password.sendKeys("SuperSecretPassword!");
        submitButton.click();

        WebElement successMessage = driver.findElement(By.cssSelector(".flash.success"));
        Assert.assertTrue(successMessage.getText().contains("You logged into a secure area!"), "Login realizado com sucesso");
    }

    @Test(priority = 2)
    public void testInvalidUsername() {
        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));

        username.sendKeys("invalidUsername");
        password.sendKeys("SuperSecretPassword!");
        submitButton.click();

        WebElement errorMessage = driver.findElement(By.cssSelector(".flash.error"));
        Assert.assertTrue(errorMessage.getText().contains("Your username is invalid!"), "Mensagem de erro de nome de usuário inválido exibida");
    }

    @Test(priority = 3)
    public void testInvalidPassword() {
        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));

        username.sendKeys("tomsmith");
        password.sendKeys("invalidPassword");
        submitButton.click();

        WebElement errorMessage = driver.findElement(By.cssSelector(".flash.error"));
        Assert.assertTrue(errorMessage.getText().contains("Your password is invalid!"), "Mensagem de erro de senha inválida exibida");
    }
}
