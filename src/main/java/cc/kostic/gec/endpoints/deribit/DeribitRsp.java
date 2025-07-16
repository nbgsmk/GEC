package cc.kostic.gec.endpoints.deribit;

import org.json.JSONObject;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeribitRsp implements Serializable {
	
	private Map<String, Object> webResponse;
	
	public DeribitRsp(JSONObject jsonObj) {
		webResponse = jsonObj.toMap();
	}
	

	public String getJsonRpcVersion() {
		return webResponse.get("jsonrpc").toString();
	}
	public <T> T getResultObject(Class<T> T) {
		Object o = webResponse.get("result");
		return switch (o) {
			case null -> null;
			case Map<?, ?> map -> T.cast(o);
			case ArrayList<?> objects -> T.cast(o);
			case List<?> objects -> T.cast(o);
			default -> T.cast(o);
		};
	}
	public Object getErrorObject() {
		return webResponse.get("error");
	}
	public boolean isTestnet() {
		return (boolean) webResponse.get("testnet");
	}
	public Instant getMicroSecIn() {
		Long l = (Long) webResponse.get("usIn");
		return toi(l);
	}
	public Instant getMicroSecOut() {
		Long l = (Long) webResponse.get("usOut");
		return toi(l);
	}
	public Long getMicroSecDiff() {
		return (Long) webResponse.get("usDiff");
	}

	
	/// ////////////
	/// pomocnici
	/// ////////////
	private Instant toi(Long uSec){
		return Instant.ofEpochSecond(0L, uSec*1000L);
	}
	
	/*
	
		--------------------------------------------------------------------------------
		{
		  "jsonrpc": "2.0",
		  "result": {
		    "price_index": "btc_usd",
		    "rfq": false,
		    "kind": "option",
		    "instrument_name": "BTC-1AUG25-110000-C",
		    "maker_commission": 0.0003,
		    "taker_commission": 0.0003,
		    "instrument_type": "reversed",
		    "expiration_timestamp": 1754035200000,
		    "creation_timestamp": 1752134414000,
		    "is_active": true,
		    "option_type": "call",
		    "contract_size": 1,
		    "tick_size": 0.0001,
		    "strike": 110000,
		    "instrument_id": 484124,
		    "settlement_period": "week",
		    "min_trade_amount": 0.1,
		    "block_trade_commission": 0.0003,
		    "block_trade_min_trade_amount": 25,
		    "block_trade_tick_size": 0.0001,
		    "settlement_currency": "BTC",
		    "base_currency": "BTC",
		    "counter_currency": "USD",
		    "quote_currency": "BTC",
		    "tick_size_steps": [
		      {
		        "tick_size": 0.0005,
		        "above_price": 0.005
		      }
		    ]
		  },
		  "usIn": 1752499103403875,
		  "usOut": 1752499103404200,
		  "usDiff": 325,
		  "testnet": false
		}
	
	
	 */
	
}
