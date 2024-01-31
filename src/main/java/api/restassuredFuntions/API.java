package api.restassuredFuntions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.empty;
import java.io.File;
import java.util.List;
import java.util.Map;

import api.interfaces.IApi;
import api.actions.HttpOperation;
import api.actions.ValidatorOperation;
import api.listeners.ExtentTestManager;
import api.utilities.Helper;

import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.anyOf;


public class API implements IApi {

	RequestSpecification reqSpec;
	HttpOperation method;
	String url;
	Response resp;
	
    public void init(String url, HttpOperation method) {
		this.url = url;
		this.method = method;
		reqSpec = given();
	}
	
	public void initBase(String baseConst) {
		Helper getHelp = new Helper();
		getHelp.set_path("src/main/resources/Constants.properties");
		try {
			RestAssured.baseURI = getHelp.loadProperties(baseConst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		reqSpec = RestAssured.given();
	}

	public void setHeader(String[][] head) {
		for (int row = 0; row < head.length; row++)
			for (int col = 0; col < head[row].length; col++)
				reqSpec.header(head[row][col], head[row][col + 1]);
	}

	public void setHeader(String head, String val) { reqSpec.header(head, val);}

	public void setHeaders(Map<String,String> headers)
	{
		reqSpec.headers(headers);
	}

	public void setBody(String body) { reqSpec.body(body); }

	public void setFormParam(String key, String val) { reqSpec.formParam(key, val);}

	public void setQueryParam(String key, String val) { reqSpec.queryParam(key, val);}

	public String callIt() {
		if (method.toString().equalsIgnoreCase("get")) {
			resp = reqSpec.get(url);
			ExtentTestManager.getTest().log(LogStatus.INFO, "Response : " + resp.asString());
			return resp.asString();
		} else if (method.toString().equalsIgnoreCase("post")) {
			resp = reqSpec.post(url);
			ExtentTestManager.getTest().log(LogStatus.INFO, "Response : " + resp.asString());
			return resp.asString();
		} else if (method.toString().equalsIgnoreCase("patch")) {
			resp = reqSpec.patch(url);
			ExtentTestManager.getTest().log(LogStatus.INFO, "Response : " + resp.asString());
			return resp.asString();
		} else if (method.toString().equalsIgnoreCase("put")) {
			resp = reqSpec.put(url);
			ExtentTestManager.getTest().log(LogStatus.INFO, "Response : " + resp.asString());
			return resp.asString();
		} else if (method.toString().equalsIgnoreCase("delete")) {
			resp = reqSpec.delete(url);
			ExtentTestManager.getTest().log(LogStatus.INFO, "Response : " + resp.asString());
			return resp.asString();
		}
		return "invalid method set for API";
	}

	public API assertIt(String key, Object val, ValidatorOperation operation) {

		switch (operation.toString()) {
		case "EQUALS":
			try {
				resp.then().body(key, equalTo(val));
				ExtentTestManager.getTest().log(LogStatus.PASS, "Expected value : " + val + " found in response" );
			}catch(Exception e){
				ExtentTestManager.getTest().log(LogStatus.FAIL, "Expected : " + val + " Actual : "+e.getMessage());
			}
			break;

		case "KEY_PRESENTS":
			try {
				resp.then().body(key, hasKey(key));
				ExtentTestManager.getTest().log(LogStatus.PASS, "Expected key : " + key + " found in response" );
			}catch(Exception e){
				ExtentTestManager.getTest().log(LogStatus.FAIL, "Expected : " + key + " Actual : "+e.getMessage());
			}
			break;

		case "HAS_ALL":
			break;
			
		case "NOT_EQUALS":
//			resp.then().body(key, not(equalTo(val)));
			try {
				resp.then().body(key, not(equalTo(val)));
				ExtentTestManager.getTest().log(LogStatus.PASS, "Expected val : " + val + " is not equal in response" );
			}catch(Exception e){
				ExtentTestManager.getTest().log(LogStatus.FAIL, "Expected val : " + val + " Actual : "+e.getMessage());
			}
			break;
			
		case "EMPTY":
//			resp.then().body(key, empty());
			try {
				resp.then().body(key, empty());
				ExtentTestManager.getTest().log(LogStatus.PASS, "Expected key : " + key + " is Empty in response" );
			}catch(Exception e){
				ExtentTestManager.getTest().log(LogStatus.FAIL, "Expected key : " + key + " Actual : "+e.getMessage());
			}
			break;
			
		case "NOT_EMPTY":
//			resp.then().body(key, not(emptyArray()));
			try {
				resp.then().body(key, not(emptyArray()));
				ExtentTestManager.getTest().log(LogStatus.PASS, "Expected key : " + key + " is Not Empty in response" );
			}catch(Exception e){
				ExtentTestManager.getTest().log(LogStatus.FAIL, "Expected key : " + key + " Actual : "+e.getMessage());
			}
			break;
		
		case "NOT_NULL":
//			resp.then().body(key, notNullValue());
			try {
				resp.then().body(key, notNullValue());
				ExtentTestManager.getTest().log(LogStatus.PASS, "Expected key : " + key + " is Not Null in response" );
			}catch(Exception e){
				ExtentTestManager.getTest().log(LogStatus.FAIL, "Expected key : " + key + " Actual : "+e.getMessage());
			}
			break;
			
		case "HAS_STRING":
//	        resp.then().body(key, containsString((String)val));
			try {
				resp.then().body(key, containsString((String)val));
				ExtentTestManager.getTest().log(LogStatus.PASS, "Expected value : " + val + " is present in response" );
			}catch(Exception e){
				ExtentTestManager.getTest().log(LogStatus.FAIL, "Expected value : " + val + " Actual : "+e.getMessage());
			}
	        break;
	        
		case "SIZE":
//	        resp.then().body(key, hasSize((int)val));
			try {
				resp.then().body(key, hasSize((int)val));
				ExtentTestManager.getTest().log(LogStatus.PASS, "Expected value : " + val + " matches in response" );
			}catch(Exception e){
				ExtentTestManager.getTest().log(LogStatus.FAIL, "Expected value : " + val + " Actual : "+e.getMessage());
			}
	        break;
		}
		
		return this;
	}

	public void assertIt(List<List<Object>> data) {
		for (List<Object> li : data) {
			switch (((ValidatorOperation) li.get(2)).toString()) {
			case "EQUALS":
				resp.then().body(((String) li.get(0)), equalTo((String) li.get(1)));
				break;

			case "KEY_PRESENTS":
				resp.then().body(((String) li.get(0)), hasKey((String) li.get(1)));
				break;
				
			case "HAS_ALL":
				break;
			}
		}
	}
	
	public API assertIt(int code) {

		resp.then().statusCode(code);

		return this;
	}

	public String extractString(String path) { return resp.jsonPath().getString(path);}
	
	public int extractInt(String path) { return resp.jsonPath().getInt(path);}

	public List<?> extractList(String path) { return resp.jsonPath().getList(path);}

	public Object extractIt(String path) { return resp.jsonPath().get(path); }

	public String extractHeader(String header_name) { return resp.header(header_name);}

	public String getResponseString() { return resp.asString();}
	public String getResponseString1() { return resp.prettyPrint();}

	public void fileUpload(String key, String path, String mime) {
		reqSpec.multiPart(key, new File(path), mime);
	}
	
	public void multiPartString(String key, String input) { reqSpec.multiPart(key,input);}
	
	public void printResp() { resp.print();}
	
	public String getCookieValue(String cookieName) { return resp.getCookie(cookieName); }
	
	public API assertIt(int code, int optionalCode) {
		resp.then().statusCode(anyOf(equalTo(code),equalTo(optionalCode)));
		return this;
	}
	
	public void setRedirection(boolean followRedirects) { 
		reqSpec.when().redirects().follow(followRedirects);
		}
	
	public void ListResponseHeaders()
	{ 
	 // Get all the headers. Return value is of type Headers.
	 Headers allHeaders = resp.headers();
	 // Iterate over all the Headers
	 for(Header header : allHeaders)
	 {
	 System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
	 }
	}
	
	public int getStatusCode() { return resp.getStatusCode(); }
	
	public Headers getAllHeaders() {return resp.getHeaders();}
}
