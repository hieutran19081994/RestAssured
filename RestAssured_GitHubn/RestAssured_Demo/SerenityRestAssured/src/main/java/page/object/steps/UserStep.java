package page.object.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import page.object.pages.CommonPage;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UserStep {
    @Steps
    CommonPage commonPage;

    @Given("User login {int} times")
    public void userLoginWithTimes(int loginTimes, Map<String, String> params) {
        for (int i = 1; i <= loginTimes; i++) {
            commonPage.userCallApiFromFileWithParams("Login", "${ts.assignment.collection}", params);
        }
    }
}
