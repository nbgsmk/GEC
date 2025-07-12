package cc.kostic.gec.deribit.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;

public class DeribitJSONrsp {
	
	public final static String glupkey = "a";
	private Long id;
	private String jsonRpc;
	private JSONObject resultObj;
	private Object errorObject;
	private boolean testnet;
	private Instant usIn;
	private Instant usOut;
	private Long usDiff;
	
	
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
		setUsIn(jsonObj.getLong("usIn"));
		setUsOut(jsonObj.getLong("usOut"));
		setUsDiff(jsonObj.getLong("usDiff"));
	}
	
	
	private void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
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
	
	private void setUsIn(Long usIn) {
		this.usIn = Instant.ofEpochSecond(0L, usIn*1000L);
	}
	public Instant getUsIn() {
		return usIn;
	}
	
	private void setUsOut(Long usOut) {
		this.usOut = Instant.ofEpochSecond(0L, usOut*1000L);
	}
	public Instant getUsOut() {
		return usOut;
	}
	
	private void setUsDiff(Long usDiff) {
		this.usDiff = usDiff;
	}
	public Long getUsDiff() {
		return usDiff;
	}
	
}
