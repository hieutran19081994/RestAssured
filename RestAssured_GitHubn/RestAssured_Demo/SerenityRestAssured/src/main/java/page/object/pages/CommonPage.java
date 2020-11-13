package page.object.pages;

import core.rest.api.PostmanRequestObject;
import core.rest.api.RestClient;
import core.utils.TransformData;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

import java.util.Map;

public class CommonPage {
    @Step("{0}")
    public void addStepInfo(String info) {
    }
    public void setSessionVariable(String keyName, Object value) {
        Serenity.setSessionVariable(keyName).to(value);
    }
    public <T> T getSessionVariable(String keyName) {
        return Serenity.sessionVariableCalled(keyName);
    }

    public void userCallApiFromFileWithParams(String item, String jsonFile, Map<String, String> params) {
        jsonFile = TransformData.getValue(jsonFile);
        setSessionVariable("${RESPONSE}", RestClient.callApi(jsonFile, item, TransformData.getValue(params)));
    }

    public String collectData(Response response, String jsonPath){
        String result = PostmanRequestObject.getTextByJSONPath(response.body().asString(), jsonPath);
        if (result.startsWith("\"") && result.endsWith("\"")) {
            return result.substring(1, result.length() - 1).trim();
        }
        if (result.startsWith("[\"") && result.endsWith("\"]") && !result.contains(",")) {
            return result.substring(2, result.length() - 2).trim();
        }
        return result;
    }
}
