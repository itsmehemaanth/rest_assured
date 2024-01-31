package api.base.generic;

import api.actions.ValidatorOperation;
import api.base.utils.Utils;
import api.restassuredFuntions.API;
import api.actions.HttpOperation;
import api.utilities.Helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseMethods extends API {

    public BaseMethods(){}

	public String getMethod(String baseUrl,String uri,String headerName){
		Utils util = new Utils();
		Map<String,String> headers = util.getHeaders(headerName);
		initBase(baseUrl);
		init(uri, HttpOperation.GET);
		setHeaders(headers);
		String response = callIt();
		return response;
	}

	public String postMethod(String baseUrl,String uri,String headerName,String bodyName)  {
		Utils util = new Utils();
		Map<String,String> headers = util.getHeaders(headerName);
		String strBody = util.getBody(bodyName);
		Map<String,String> val =  util.ReadCSV(util.INPUTDATA+"Sample.csv",bodyName);
		String finalBody = util.replacePlaceHolders(strBody,bodyName,val);
		initBase(baseUrl);
		init(uri, HttpOperation.POST);
		setHeaders(headers);
		setBody(finalBody);
		String response = callIt();
		return response;
	}

	public String restMethod(String tcId,String fileName)  {
		Utils util = new Utils();
		Map<String,String> hash =  util.ReadCSV(util.INPUTDATA+fileName,tcId);
		Map<String,String> headers = util.getHeaders(hash.get("HEADER"));
		initBase(hash.get("HOST"));
		if(hash.get("METHOD").equalsIgnoreCase("GET")){
			init(hash.get("URI"), HttpOperation.GET);
		}else if(hash.get("METHOD").equalsIgnoreCase("POST")){
			init(hash.get("URI"), HttpOperation.POST);
			String strBody = util.getBody(tcId);
			String finalBody = util.replacePlaceHolders(strBody,tcId,hash);
			setBody(finalBody);
		}
		setHeaders(headers);
		String response = callIt();
		assertValues(hash);
		return response;
	}

	public void assertValues(Map<String,String> hash){

		if(hash.get("RESPONSE_CODE").length() > 0) {
			assertIt(Integer.parseInt(hash.get("RESPONSE_CODE")));
		}
		for (Map.Entry<String, String> entry : hash.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (key.contains("RESPONSE_VALUE")) {
				if (value.contains(":")) {
					String[] splitValue = value.split(":");
					assertIt(splitValue[1], splitValue[2], ValidatorOperation.valueOf(splitValue[0]));

				}
			}

		}

	}



	
	
}
