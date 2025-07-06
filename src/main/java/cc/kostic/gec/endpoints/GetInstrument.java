package cc.kostic.gec.endpoints;

import cc.kostic.gec.deribit.model.DeribitJSONrsp;
import cc.kostic.gec.web.Fetcher;
import org.json.JSONObject;

public class GetInstrument {
	
	private final BaseURL b;
	private final String instrument_name;
	
	public GetInstrument(String instrument_name) {
		this.instrument_name = instrument_name;
		b = new BaseURL();
	}
	
	private String buildReq(){
		// return "https://www.deribit.com/api/v2/public/get_instrument?instrument_name=BTC-27MAR26-90000-C";
		return b.pub() + "/get_instrument?instrument_name=" + instrument_name;
	}
	
	public JSONObject getResult(){
		String reqUrl = buildReq();
		Fetcher f = new Fetcher(reqUrl);
		DeribitJSONrsp dr = new DeribitJSONrsp(f.fetch());
		return dr.getResultObject();
	}
	
	
	
	// https://www.deribit.com/api/v2/public/get_instrument?instrument_name=BTC-27MAR26-90000-C
 	//
	// --------------------------------------------------------------------------------
	// {
	//   "jsonrpc": "2.0",
	//   "result": {
	//     "price_index": "btc_usd",
	//     "rfq": false,
	//     "kind": "option",
	//     "instrument_name": "BTC-27MAR26-90000-C",
	//     "maker_commission": 0.0003,
	//     "taker_commission": 0.0003,
	//     "instrument_type": "reversed",
	//     "expiration_timestamp": 1774598400000,
	//     "creation_timestamp": 1743062460000,
	//     "is_active": true,
	//     "option_type": "call",
	//     "contract_size": 1,
	//     "tick_size": 0.0001,
	//     "strike": 90000,
	//     "instrument_id": 448328,
	//     "settlement_period": "month",
	//     "min_trade_amount": 0.1,
	//     "block_trade_commission": 0.0003,
	//     "block_trade_min_trade_amount": 25,
	//     "block_trade_tick_size": 0.0001,
	//     "settlement_currency": "BTC",
	//     "base_currency": "BTC",
	//     "counter_currency": "USD",
	//     "quote_currency": "BTC",
	//     "tick_size_steps": [
	//       {
	//         "tick_size": 0.0005,
	//         "above_price": 0.005
	//       }
	//     ]
	//   },
	//   "usIn": 1751792834338650,
	//   "usOut": 1751792834339069,
	//   "usDiff": 419,
	//   "testnet": false
	// }
}
