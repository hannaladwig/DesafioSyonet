
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = ((AuthenticationTest)result.getInstance()).driver;

        if (driver != null) {
            TakesScreenshot screenshot = (TakesScreenshot)driver;
            byte[] screenshotData = screenshot.getScreenshotAs(OutputType.BYTES);

            String testName = result.getName();
            String screenshotName = testName + "_" + System.currentTimeMillis() + ".png";

            Reporter.log("<br><img src='data:image/png;base64," + Base64.getEncoder().encodeToString(screenshotData) + "' width='50%' height='50%'/><br>");
            Reporter.log("<br><a href='" + screenshotName + "'>" + screenshotName + "</a><br>");

            try {
                Files.write(Paths.get(screenshotName), screenshotData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
