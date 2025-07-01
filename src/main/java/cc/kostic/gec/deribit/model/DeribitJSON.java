package cc.kostic.gec.deribit.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeribitJSON {
	
	public int id;
	public String jsonRpc;
	public JSONArray resultArray;
	public Object errorObject;
	public boolean testnet;
	public int usIn;
	public int usOut;
	public int usDiff;
	
	
	public DeribitJSON(JSONObject jsonObj) {
		
		// setId(jsonObj.getInt("id"));
		setJsonRpc(jsonObj.getString("jsonrpc"));
		setResult(jsonObj.getJSONArray("result"));
		// setErrorObject(jsonObj.getJSONObject("error"));
		setTestnet(jsonObj.getBoolean("testnet"));
		setUsIn(jsonObj.getInt("usIn"));
		setUsOut(jsonObj.getInt("usOut"));
		setUsDiff(jsonObj.getInt("usDiff"));
		
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setJsonRpc(String jsonRpc) {
		this.jsonRpc = jsonRpc;
	}
	
	public void setResult(JSONArray result) {
		this.resultArray = result;
	}
	
	public List<JSONObject> getGenericResponse(){
		ArrayList<JSONObject> tmp = new ArrayList<>();
		for (int i = 0; i < resultArray.length(); i++) {
			JSONObject o = resultArray.getJSONObject(i);
			tmp.add(o);
		}
		return tmp;
	}
	
	public void setErrorObject(Object errorObject) {
		this.errorObject = errorObject;
	}
	
	public void setTestnet(boolean testnet) {
		this.testnet = testnet;
	}
	
	public void setUsIn(int usIn) {
		this.usIn = usIn;
	}
	
	public void setUsOut(int usOut) {
		this.usOut = usOut;
	}
	
	public void setUsDiff(int usDiff) {
		this.usDiff = usDiff;
	}
	
	
	
}
