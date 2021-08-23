package Steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class e2eStepDef {
    BaseStepDef baseStepDef = new BaseStepDef();
    WebDriver driver = baseStepDef.setUp();
    private static Response response;
    private static String jsonString;
    private static List<WebElement> profanity_list;
    private static final String base_URL= "https://www.purgomalum.com/";
    RequestSpecification request = RestAssured.given();

    @Given("^profanity service is available$")
    public void givenProfanityService(){

        driver.findElement(By.cssSelector("ul:nth-of-type(4) [href='profanitylist.html']")).click();
        request.baseUri(base_URL);
        response=request.get();
        Assert.assertEquals(200,response.getStatusCode());

    }

    @Given("^asterik is displayed as the default fill when fill text is not added in the (.*) parameter$")
    public void asterikDisplayedAsDefault(String type) {
        String text;
        profanity_list = driver.findElements(By.tagName("li"));
        System.out.println((long) profanity_list.size());
        System.out.println(profanity_list.stream().toString());
        request.baseUri(base_URL);
        request = getBasePath(type,request);
        for (WebElement we : profanity_list) {
            text = "This is a sample " + we.getText();
            System.out.println(text);
            response = request.queryParam("text", text).get();
            Assert.assertEquals(200, response.getStatusCode());
            Assert.assertFalse(response.asString().contains(we.getText()));
            Assert.assertTrue(response.asString().contains("***"));
            System.out.println(response.asString());
        }
    }


    @And("^profanity word is replaced by the (.*) when input as parameter in the (.*)$")
    public void responseReceivedFromAPI(String filltext, String type){
        profanity_list = driver.findElements(By.tagName("li"));
        request.baseUri(base_URL);
        request = getBasePath(type,request);
        for (WebElement we : profanity_list) {
            String text = "This is a sample " + we.getText();
            System.out.println(text);
            response = request.queryParam("text", text).queryParam("fill_text", filltext).get();

            Assert.assertEquals(200, response.getStatusCode());
            Assert.assertFalse(response.asString().contains(we.getText()));
            Assert.assertTrue(response.asString().contains(filltext));
            System.out.println(response.asString());
        }
    }

    @Then("^profanity service returns success when input text has profanity word in the (.*)$")
    public void profanityServiceResponse(String type){

        profanity_list = driver.findElements(By.tagName("li"));
        request.baseUri(base_URL);
        request.basePath("service/containsprofanity");
        for (WebElement we : profanity_list) {
            String text = "This is a sample " + we.getText();
            System.out.println(text);
            response = request.queryParam("text", text).get();

            Assert.assertEquals(200, response.getStatusCode());
            Assert.assertTrue(response.asString().contains("true"));
            System.out.println(response.asString());
        }

    }

    @Then("^profanity service returns false when input (.*) does not have profanity word in the (.*)$")
    public void validateProfanityServiceFalseResponse(String text,String type){
        request.baseUri(base_URL);
        request.basePath("service/containsprofanity");
        response = request.queryParam("text", text).get();

        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotEquals("False", response.asString());
        System.out.println(response.asString());

    }

    @Then("^error message is returned in the response when required parameter is not provided in (.*)$")
    public void errorMessageReturnedNoInput(String type){
        request.baseUri(base_URL);
        request = getBasePath(type,request);
        response=request.get();
        Assert.assertEquals(200,response.getStatusCode());
        Assert.assertTrue(response.asString().contains("No Input"));
  }


  @Then("^error message is returned when replacement text exceeds 20 characters in (.*)$")
  public void errorMessageReturnedReplacementText(String type){
      String text = "This is sample arse";
      String filltext = "This is a really really long text";
      request.baseUri(base_URL);
      request = getBasePath(type,request);
      response = request.queryParam("text", text).queryParam("fill_text", filltext).get();
      Assert.assertEquals(200, response.getStatusCode());
      Assert.assertTrue(response.asString().contains("Exceeds Limit of 20 Characters"));
  }

  @And("^profanityword is replaced by the (.*) in the (.*)$")
  public void errorMessageReplacementCharacter(String character,String type){
     String fillchar;
     if(character.equals("pipe")){
         fillchar="||";
     }else{
         fillchar=character;
     }
      profanity_list = driver.findElements(By.tagName("li"));
      request.baseUri(base_URL);
      request = getBasePath(type,request);
      for (WebElement we : profanity_list) {
          String text = "This is a sample " + we.getText();
          System.out.println(text);
          response = request.queryParam("text", text).queryParam("fill_char", fillchar).get();

          Assert.assertEquals(200, response.getStatusCode());
          Assert.assertFalse(response.asString().contains(we.getText()));
          Assert.assertTrue(response.asString().contains(fillchar));
          System.out.println(response.asString());
      }

  }

    @Then("^error message is provided in the response when invalid fill character is used to replace (.*)$")
    public void errorMessageFillCharacter(String text){
        request.baseUri(base_URL);
        request.basePath("service/xml");
        response = request.queryParam("text", text).queryParam("fill_char", '!').get();
        System.out.println(response.asString());
        Assert.assertEquals(200,response.getStatusCode());

        Assert.assertTrue(response.asString().contains("Invalid User Replacement "));


    }



    public RequestSpecification getBasePath(String type, RequestSpecification requestSpecification){
        switch (type) {
            case "xml":
                requestSpecification.basePath("service/xml");
                break;
            case "json":
                requestSpecification.basePath("service/json");
                break;
            case "plaintext":
                requestSpecification.basePath("service/plain");
                break;
        }
        return requestSpecification;
    }

}
