package cc.kostic.gec.endpoints;

import cc.kostic.gec.deribit.model.DeribitJSONrsp;
import cc.kostic.gec.web.Fetcher;
import org.json.JSONObject;

public class Ticker {
	
	private final BaseURL b;
	private final String instrument_name;
	
	public Ticker(String instrument_name) {
		this.instrument_name = instrument_name;
		b = new BaseURL();
	}
	
	private String buildReq(){
		// return "https://www.deribit.com/api/v2/public/ticker?instrument_name=BTC-26JUN26-100000-C";
		return b.pub() + "/ticker?instrument_name=" + instrument_name;
	}
	
	public JSONObject getResult(){
		String reqUrl = buildReq();
		Fetcher f = new Fetcher(reqUrl);
		DeribitJSONrsp dr = new DeribitJSONrsp(f.fetch());
		return dr.getResultObject();
	}
	
	
	
	// https://www.deribit.com/api/v2/public/ticker?instrument_name=BTC-26JUN26-100000-C
 	//
	// --------------------------------------------------------------------------------
	// {
	//   "jsonrpc": "2.0",
	//   "result": {
	//     "timestamp": 1752057345721,
	//     "state": "open",
	//     "stats": {
	//       "high": null,
	//       "low": null,
	//       "price_change": null,
	//       "volume": 0,
	//       "volume_usd": 0
	//     },
	//     "greeks": {
	//       "delta": 0.71138,
	//       "gamma": 0.00001,
	//       "vega": 389.29255,
	//       "theta": -25.13021,
	//       "rho": 524.77921
	//     },
	//     "index_price": 108827.5,
	//     "instrument_name": "BTC-26JUN26-100000-C",
	//     "last_price": 0.247,
	//     "settlement_price": 0.24231149,
	//     "min_price": 0.1975,
	//     "max_price": 0.3015,
	//     "open_interest": 1.2,
	//     "mark_price": 0.2425,
	//     "best_bid_price": 0.2395,
	//     "mark_iv": 45.43,
	//     "ask_iv": 46.18,
	//     "bid_iv": 44.54,
	//     "underlying_price": 116086.34,
	//     "underlying_index": "BTC-26JUN26",
	//     "best_ask_price": 0.245,
	//     "interest_rate": 0,
	//     "estimated_delivery_price": 108827.5,
	//     "best_ask_amount": 2,
	//     "best_bid_amount": 2
	//   },
	//   "usIn": 1752057346027899,
	//   "usOut": 1752057346028067,
	//   "usDiff": 168,
	//   "testnet": false
	// }
}
