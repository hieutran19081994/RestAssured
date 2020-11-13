package page.object.steps;

import core.enums.CompareTypes;
import core.utils.ProConstants;
import core.utils.ReadFileUtil;
import core.utils.TransformData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import page.object.pages.CommonPage;

import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.assertj.core.api.Assertions.assertThat;

public class CommonStep {

    @Steps
    CommonPage commonPage;

    @When("User call api {string} from file {string} with params")
    public void userCallApiFromFileWithParams(String item, String jsonFile, Map<String, String> params) {
        commonPage.userCallApiFromFileWithParams(item, jsonFile, params);
    }

    @Then("User verify response {string} has code {int}")
    public void userVerifyResponseHasCode(String resKey, int code) {
        Response response = commonPage.getSessionVariable(resKey);
        assertThat(response.getStatusCode()).isEqualTo(code);
    }

    @And("User verify response {string} against schema {string}")
    public void userVerifyResponseAgainstSchema(String resKey, String schemaPath) {
        Response response = commonPage.getSessionVariable(resKey);
        String schemaJson = ReadFileUtil.getTextContent(TransformData.getValue(schemaPath));
        response.then().body(matchesJsonSchema(schemaJson));
    }

    @And("User verify json data from response {string}")
    public void userVerifyJsonDataFromResponse(String resKey, DataTable dataTable) {
        List<Map<String, String>> lstData = dataTable.asMaps(String.class, String.class);
        Response response = Serenity.sessionVariableCalled(resKey);
        for (Map<String, String> map : lstData) {
            String value = TransformData.getValue(map.get(ProConstants.VALUE));
            String jsonPath = TransformData.getValue(map.get(ProConstants.JSON_PATH));
            String compareType = map.get(ProConstants.COMPARE_TYPE);
            commonPage.addStepInfo("Jsonpath: " + jsonPath + " <" + compareType + "> " + value);
            switch (CompareTypes.valueOf(compareType.toUpperCase())) {
                case EQUALS:
                    assertThat(commonPage.collectData(response, jsonPath)).isEqualTo(value);
                    break;
                case EQUALS_IGNORE_CASE:
                    assertThat(commonPage.collectData(response, jsonPath)).isEqualToIgnoringCase(value);
                    break;
                case NOT_EQUAL_IGNORE_CASE:
                    assertThat(commonPage.collectData(response, jsonPath)).isNotEqualToIgnoringCase(value);
                    break;
                case NOT_EQUAL:
                    assertThat(commonPage.collectData(response, jsonPath)).isNotEqualTo(value);
                    break;
                case MATCHES:
                    assertThat(commonPage.collectData(response, jsonPath)).matches(value);
                    break;
                case NOT_MATCH:
                    assertThat(commonPage.collectData(response, jsonPath)).doesNotMatch(value);
                    break;
                case CONTAINS:
                    assertThat(commonPage.collectData(response, jsonPath)).contains(value);
                    break;
                case NOT_CONTAIN:
                    assertThat(commonPage.collectData(response, jsonPath)).doesNotContain(value);
                    break;
                default:
                    break;
            }
        }
    }

    @When("User collect values from response {string}")
    public void userCollectValuesFromResponse(String resKey, DataTable dataTable) {
        List<Map<String, String>> lstData = dataTable.asMaps(String.class, String.class);
        Response response = commonPage.getSessionVariable(resKey);
        for (Map<String, String> map : lstData) {
            commonPage.setSessionVariable(map.get(ProConstants.KEY), commonPage.collectData(response, map.get(ProConstants.JSON_PATH)));
        }
    }
}
