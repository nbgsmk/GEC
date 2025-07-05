package cc.kostic.gec.endpoints;

import cc.kostic.gec.deribit.model.DeribitJSONrsp;
import cc.kostic.gec.web.Fetcher;
import org.json.JSONObject;

import java.util.List;

public class GetTicker {
	
	private final BaseURL b;
	private final String instrument_name;
	
	public GetTicker(String instrument_name) {
		this.instrument_name = instrument_name;
		b = new BaseURL();
	}
	
	private String buildReq(){
		// return "https://www.deribit.com/api/v2/public/ticker?instrument_name=BTC-11JUL25-104000-C";
		return b.pub() + "/ticker?instrument_name=" + instrument_name;
	}
	
	public JSONObject getResult(){
		String reqUrl = buildReq();
		Fetcher f = new Fetcher(reqUrl);
		DeribitJSONrsp dr = new DeribitJSONrsp(f.fetch());
		return dr.getResultArray();
	}
	
}
