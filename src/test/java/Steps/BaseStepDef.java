package Steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class BaseStepDef {

    private static final String application_URL = "https://www.purgomalum.com/";
    protected static WebDriver driver;

    public BaseStepDef() {


    }


    @Before
    public WebDriver setUp(){
            if(driver==null) {
                System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/chromedriver.exe");
                driver = new ChromeDriver();
                driver.get(application_URL);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            }

        return driver;
    }





    @After
    public void tearDown(){

        driver.quit();
        driver=null;

    }

}

