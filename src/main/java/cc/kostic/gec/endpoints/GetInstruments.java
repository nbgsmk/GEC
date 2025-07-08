package cc.kostic.gec.endpoints;

import cc.kostic.gec.deribit.model.DeribitJSONrsp;
import cc.kostic.gec.deribit.model.ListedOptionContracts;
import cc.kostic.gec.instrument.OptionContract;
import cc.kostic.gec.primitives.Currency;
import cc.kostic.gec.primitives.Kind;
import cc.kostic.gec.web.Fetcher;
import javafx.collections.transformation.SortedList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetInstruments {
	
	private final BaseURL b;
	private final Currency currency;
	private final Kind kind;
	
	private List<OptionContract> listedOptions = new ArrayList<>();
	
	public GetInstruments(Currency currency, Kind kind) {
		this.currency = currency;
		this.kind = kind;
		b = new BaseURL();
	}
	
	private String buildReq(){
		// return https://www.deribit.com/api/v2/public/get_instruments?currency=BTC&expired=true&kind=option
		// return https://www.deribit.com/api/v2/public/get_instruments?currency=BTC&expired=false&kind=option
		// return https://www.deribit.com/api/v2/public/get_instruments?currency=BTC&kind=option
		return b.pub() + "/get_instruments?currency=" + currency + "&expired=false&kind=" + kind;
	}
	
	public List<OptionContract> getList(){
		JSONObject jSrsp = getJSrsp();
		JSONArray jsInstruments = jSrsp.getJSONArray(DeribitJSONrsp.glupkey);
		for (int i = 0; i < jsInstruments.length(); i++) {
			listedOptions.add(new OptionContract(jsInstruments.getJSONObject(i)));
		}
		return listedOptions;
	}
	
	private JSONObject getJSrsp(){
		String reqUrl = buildReq();
		Fetcher f = new Fetcher(reqUrl);
		DeribitJSONrsp dr = new DeribitJSONrsp(f.fetch());
		return dr.getResultObject();
	}
	
	
	// https://www.deribit.com/api/v2/public/get_instruments?currency=ETH&expired=false&kind=option
	// expired=false
 	// kind=option
	// --------------------------------------------------------------------------------
	// {
	//   "jsonrpc": "2.0",
	//   "result": [
	//     {
	//       "price_index": "eth_usd",
	//       "rfq": false,
	//       "kind": "option",
	//       "instrument_name": "ETH-6JUL25-1500-C",
	//       "maker_commission": 0.0003,
	//       "taker_commission": 0.0003,
	//       "instrument_type": "reversed",
	//       "expiration_timestamp": 1751788800000,
	//       "creation_timestamp": 1751529610000,
	//       "is_active": true,
	//       "option_type": "call",
	//       "contract_size": 1,
	//       "tick_size": 0.0001,
	//       "strike": 1500,
	//       "instrument_id": 481742,
	//       "settlement_period": "day",
	//       "min_trade_amount": 1,
	//       "block_trade_commission": 0.0003,
	//       "block_trade_min_trade_amount": 250,
	//       "block_trade_tick_size": 0.0001,
	//       "settlement_currency": "ETH",
	//       "base_currency": "ETH",
	//       "counter_currency": "USD",
	//       "quote_currency": "ETH",
	//       "tick_size_steps": [
	//         {
	//           "tick_size": 0.0005,
	//           "above_price": 0.005
	//         }
	//       ]
	//     },
	//     {
	//       "price_index": "eth_usd",
	//       "rfq": false,
	//       "kind": "option",
	//       "instrument_name": "ETH-6JUL25-1500-P",
	//       "maker_commission": 0.0003,
	//       "taker_commission": 0.0003,
	//       "instrument_type": "reversed",
	//       "expiration_timestamp": 1751788800000,
	//       "creation_timestamp": 1751529610000,
	//       "is_active": true,
	//       "option_type": "put",
	//       "contract_size": 1,
	//       "tick_size": 0.0001,
	//       "strike": 1500,
	//       "instrument_id": 481743,
	//       "settlement_period": "day",
	//       "min_trade_amount": 1,
	//       "block_trade_commission": 0.0003,
	//       "block_trade_min_trade_amount": 250,
	//       "block_trade_tick_size": 0.0001,
	//       "settlement_currency": "ETH",
	//       "base_currency": "ETH",
	//       "counter_currency": "USD",
	//       "quote_currency": "ETH",
	//       "tick_size_steps": [
	//         {
	//           "tick_size": 0.0005,
	//           "above_price": 0.005
	//         }
	//       ]
	//     }
	//   ],
	//   "usIn": 1751758217174939,
	//   "usOut": 1751758217175538,
	//   "usDiff": 599,
	//   "testnet": false
	// }
}
