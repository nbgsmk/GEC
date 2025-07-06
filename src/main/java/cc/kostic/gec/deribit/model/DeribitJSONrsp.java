package cc.kostic.gec.deribit.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class DeribitJSONrsp {
	
	public final static String glupkey = "a";
	public int id;
	public String jsonRpc;
	public JSONObject resultObj;
	public Object errorObject;
	public boolean testnet;
	public int usIn;
	public int usOut;
	public int usDiff;
	
	
	public DeribitJSONrsp(JSONObject jsonObj) {
		
		// setId(jsonObj.getInt("id"));
		setJsonRpcVersion(jsonObj.getString("jsonrpc"));
		
		Object iks = jsonObj.get("result");
		if (iks instanceof JSONArray) {
			JSONObject tmp = new JSONObject();
			tmp.put(glupkey, iks);
			setResultObject(tmp);
		} else {
			setResultObject(jsonObj.getJSONObject("result"));
		}
		
		// setErrorObject(jsonObj.getJSONObject("error"));
		setTestnet(jsonObj.getBoolean("testnet"));
		setUsIn(jsonObj.getInt("usIn"));
		setUsOut(jsonObj.getInt("usOut"));
		setUsDiff(jsonObj.getInt("usDiff"));
	}
	
	
	private void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	
	private void setJsonRpcVersion(String jsonRpc) {
		this.jsonRpc = jsonRpc;
	}
	public String getJsonRpcVersion() {
		return jsonRpc;
	}
	
	private void setResultObject(JSONObject result) {
		this.resultObj = result;
	}
	public JSONObject getResultObject() {
		return resultObj;
	}
	
	private void setErrorObject(Object errorObject) {
		this.errorObject = errorObject;
	}
	public Object getErrorObject() {
		return errorObject;
	}
	
	private void setTestnet(boolean testnet) {
		this.testnet = testnet;
	}
	public boolean isTestnet() {
		return testnet;
	}
	
	private void setUsIn(int usIn) {
		this.usIn = usIn;
	}
	public int getUsIn() {
		return usIn;
	}
	
	private void setUsOut(int usOut) {
		this.usOut = usOut;
	}
	public int getUsOut() {
		return usOut;
	}
	
	private void setUsDiff(int usDiff) {
		this.usDiff = usDiff;
	}
	public int getUsDiff() {
		return usDiff;
	}
	
}
