package cc.kostic.gec.endpoints.deribit;

import cc.kostic.gec.instrument.Instrument;
import cc.kostic.gec.web.Fetcher;
import org.json.JSONObject;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class GetInstrument {
	
	private final String reqUrl;
	private DeribitRsp dr;
	
	// constructor
	public GetInstrument(String instrument_name) {
		this.reqUrl = buildReq(new BaseURL(), instrument_name);
	}
	
	private String buildReq(BaseURL b, String instrument_name){
		// return "https://www.deribit.com/api/v2/public/get_instrument?instrument_name=BTC-27MAR26-90000-C";
		return b.pub() + "/get_instrument?instrument_name=" + instrument_name;
	}
	
	public void run(){
		Fetcher f = new Fetcher(this.reqUrl);
		JSONObject jsRsp = f.fetch();
		this.dr = new DeribitRsp(jsRsp);
	}
	
	public String getJsonRpcVer(){
		return dr.getJsonRpcVersion();
	}
	public Instrument getResult(){
		Map<String, ?> mapa = dr.getResultObject(HashMap.class);
		 Instrument i = new Instrument(mapa);
		 return i;
	}
	public Object getErrorObject(){
		return dr.getErrorObject();
	}
	public boolean isTestnet(){
		return dr.isTestnet();
	}
	public Instant getMicroSecIn(){
		return dr.getMicroSecIn();
	}
	public Instant getMicroSecOut(){
		return dr.getMicroSecOut();
	}
	public Long getMicroSecDiff(){
		return dr.getMicroSecDiff();
	}
	
}
