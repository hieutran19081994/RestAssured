package core.rest.api;
import core.configs.Configuration;
import core.enums.HttpMethodEnum;
import core.utils.ReadFileUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import java.util.Map;

public class RestClient {

    public static Response callApi(String jsonFile, String itemName, Map<String, String> data) {
        String jsonContent = ReadFileUtil.getTextContent(jsonFile);
        PostmanRequestObject requestObject = PostmanRequestObject.parse(itemName, jsonContent, data);
        Response req = null;
        RequestSpecification reqSpec;
        reqSpec = SerenityRest.given().relaxedHTTPSValidation().headers(requestObject.getHeaders());
        if ("basic".equalsIgnoreCase(requestObject.getAuth())) {
            reqSpec = reqSpec.auth().preemptive().basic(requestObject.getUsername(), requestObject.getPasword());
        }

        switch (HttpMethodEnum.valueOf(requestObject.getMethod().toUpperCase())) {
            case GET:
                req = reqSpec.body(requestObject.getBody()).get(requestObject.getUrl());
                break;
            case POST:
                req = reqSpec.body(requestObject.getBody()).post(requestObject.getUrl());
                break;
            case PUT:
                req = reqSpec.body(requestObject.getBody()).put(requestObject.getUrl());
                break;
            case DELETE:
                req = reqSpec.body(requestObject.getBody()).delete(requestObject.getUrl());
                break;
            case PATCH:
                req = reqSpec.body(requestObject.getBody()).patch(requestObject.getUrl());
                break;
            case OPTIONS:
                req = reqSpec.body(requestObject.getBody()).options(requestObject.getUrl());
                break;
            default:
                break;
        }
        return req;
    }
}
