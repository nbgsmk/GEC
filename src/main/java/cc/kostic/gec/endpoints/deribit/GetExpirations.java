package cc.kostic.gec.endpoints.deribit;

import cc.kostic.gec.primitives.Currency;
import cc.kostic.gec.primitives.CurrencyPair;
import cc.kostic.gec.primitives.Kind;
import cc.kostic.gec.web.Fetcher;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetExpirations {
	
	private final BaseURL b;
	private final Currency currency;
	private final Kind kind;
	private final CurrencyPair currencyPair;
	
	
	public GetExpirations(Currency currency, Kind kind, CurrencyPair currencyPair) {
		this.currency = currency;
		this.kind = kind;
		this.currencyPair = currencyPair;
		b = new BaseURL();
	}
	public GetExpirations(Currency currency, Kind kind) {
		this.currency = currency;
		this.kind = kind;
		this.currencyPair = null;
		b = new BaseURL();
	}
	
	private String buildReq(){
		// return "https://www.deribit.com/api/v2/public/ get_expirations? currency=ETH & kind=option";
		// return "https://www.deribit.com/api/v2/public/ get_expirations? currency=ETH & currency_pair=eth_usdc & kind=option";
		
		if (currencyPair == null) {
			return b.pub() + "/get_expirations?currency=" + currency.getName() + "&kind=" + kind.getName();
		} else {
			return b.pub() + "/get_expirations?currency=" + currency.getName() + "&kind=" + kind.getName() + "&currency_pair=" + currencyPair;
		}
	}

	private JSONObject getJSrsp(){
		String reqUrl = buildReq();
		Fetcher f = new Fetcher(reqUrl);
		DeribitJSONrsp dr = new DeribitJSONrsp(f.fetch());
		return dr.getResultObject();
	}
	
	public List<String> getList(){
		JSONObject jSrsp = getJSrsp();
		JSONObject currnc = jSrsp.getJSONObject(currency.getName().toLowerCase());
		JSONArray knd = currnc.getJSONArray(kind.getName().toLowerCase());
		List<String> rezultat = new ArrayList<>();
		for (int i = 0; i < knd.length(); i++) {
			rezultat.add(knd.getString(i));
		}
		return rezultat;
	}
	
	
	
	
	// https://www.deribit.com/api/v2/public/get_expirations?currency=ETH&kind=any
	// kind = any
	// --------------------------------------------------------------------------------
	// {
	//   "jsonrpc": "2.0",
	//   "result": {
	//     "eth": {
	//       "option": [
	//         "6JUL25",
	//         "7JUL25",
	//         "8JUL25",
	//         "11JUL25",
	//         "18JUL25",
	//         "25JUL25",
	//         "29AUG25",
	//         "26SEP25",
	//         "26DEC25",
	//         "27MAR26",
	//         "26JUN26"
	//       ],
	//       "future": [
	//         "11JUL25",
	//         "18JUL25",
	//         "25JUL25",
	//         "29AUG25",
	//         "26SEP25",
	//         "26DEC25",
	//         "27MAR26",
	//         "26JUN26",
	//         "PERPETUAL"
	//       ]
	//     }
	//   },
	//   "usIn": 1751730924094340,
	//   "usOut": 1751730924094498,
	//   "usDiff": 158,
	//   "testnet": false
	// }
	
	
	// https://www.deribit.com/api/v2/public/get_expirations?currency=ETH&kind=option
	// kind = option
	// --------------------------------------------------------------------------------
	// {
	//   "jsonrpc": "2.0",
	//   "result": {
	//     "eth": {
	//       "option": [
	//         "6JUL25",
	//         "7JUL25",
	//         "8JUL25",
	//         "11JUL25",
	//         "18JUL25",
	//         "25JUL25",
	//         "29AUG25",
	//         "26SEP25",
	//         "26DEC25",
	//         "27MAR26",
	//         "26JUN26"
	//       ]
	//     }
	//   },
	//   "usIn": 1751730976619923,
	//   "usOut": 1751730976620097,
	//   "usDiff": 174,
	//   "testnet": false
	// }
	
	
}
