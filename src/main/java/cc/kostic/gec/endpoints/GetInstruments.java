package cc.kostic.gec.endpoints;

import cc.kostic.gec.deribit.model.DeribitJSONrsp;
import cc.kostic.gec.primitives.Currency;
import cc.kostic.gec.primitives.Kind;
import cc.kostic.gec.web.Fetcher;
import org.json.JSONObject;

import java.util.List;

public class GetInstruments {
	
	private final BaseURL b;
	private final Currency currency;
	private final Kind kind;
	
	public GetInstruments(Currency currency, Kind kind) {
		this.currency = currency;
		this.kind = kind;
		b = new BaseURL();
	}
	
	private String buildReq(){
		// return https://www.deribit.com/api/v2/public/get_instruments?currency=BTC&expired=false&kind=option
		return b.pub() + "/get_instruments?currency=" + currency + "&expired=false&kind=" + kind;
	}
	
	public JSONObject getResult(){
		String reqUrl = buildReq();
		Fetcher f = new Fetcher(reqUrl);
		DeribitJSONrsp dr = new DeribitJSONrsp(f.fetch());
		return dr.getResultArray();
	}
}
