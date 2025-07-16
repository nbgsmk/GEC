package cc.kostic.gec.endpoints.deribit;

import cc.kostic.gec.primitives.Currency;
import cc.kostic.gec.primitives.CurrencyPair;
import cc.kostic.gec.primitives.Expiration;
import cc.kostic.gec.primitives.Kind;
import cc.kostic.gec.web.Fetcher;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetExpirations {
	
	private final String reqUrl;
	private DeribitRsp dr;
	
	private final Currency currency;
	private final Kind kind;
	private final CurrencyPair currencyPair;
	
	public GetExpirations(Currency currency, Kind kind, CurrencyPair currencyPair) {
		this.currency = currency;
		this.kind = kind;
		this.currencyPair = currencyPair;
		this.reqUrl = buildReq(new BaseURL(), currency, kind, currencyPair);
	}

	public GetExpirations(Currency currency, Kind kind) {
		this.currency = currency;
		this.kind = kind;
		this.currencyPair = null;
		this.reqUrl = buildReq(new BaseURL(), currency, kind, this.currencyPair);
	}
	
	private String buildReq(BaseURL b, Currency currency, Kind kind, CurrencyPair currencyPair){
		// return "https://www.deribit.com/api/v2/public/ get_expirations? currency=ETH & kind=option";
		// return "https://www.deribit.com/api/v2/public/ get_expirations? currency=ETH & currency_pair=eth_usdc & kind=option";
		
		if (currencyPair == null) {
			return b.pub() + "/get_expirations?currency=" + currency.getName() + "&kind=" + kind.getName();
		} else {
			return b.pub() + "/get_expirations?currency=" + currency.getName() + "&kind=" + kind.getName() + "&currency_pair=" + currencyPair;
		}
	}

	public void run(){
		Fetcher f = new Fetcher(this.reqUrl);
		JSONObject jsRsp = f.fetch();
		this.dr = new DeribitRsp(jsRsp);
	}
	
	public String getJsonRpcVer(){
		return dr.getJsonRpcVersion();
	}
	
	public List<String> getExpirationStrings(){
		Map<String, ?> mapa = dr.getResultObject(HashMap.class);
		// rezultat je nested mapa / mapa / List
		// "result" / "eth" / "option" / -> lista expirations-a
		Map<String, ?> crnc = (Map<String, ?>) mapa.get(currency.getName().toLowerCase());
		List<String> knd = (List<String>) crnc.get(kind.getName().toLowerCase());
		return knd;
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
	
	
	
	/*
		https://www.deribit.com/api/v2/public/get_expirations?currency=ETH&kind=any
		kind = any
		--------------------------------------------------------------------------------
		{
		  "jsonrpc": "2.0",
		  "result": {
			"eth": {
			  "option": [
				"6JUL25",
				"7JUL25",
				"8JUL25",
				"11JUL25",
				"18JUL25",
				"25JUL25",
				"29AUG25",
				"26SEP25",
				"26DEC25",
				"27MAR26",
				"26JUN26"
			  ],
			  "future": [
				"11JUL25",
				"18JUL25",
				"25JUL25",
				"29AUG25",
				"26SEP25",
				"26DEC25",
				"27MAR26",
				"26JUN26",
				"PERPETUAL"
			  ]
			}
		  },
		  "usIn": 1751730924094340,
		  "usOut": 1751730924094498,
		  "usDiff": 158,
		  "testnet": false
		}
	*/
	
	
	/*
		https://www.deribit.com/api/v2/public/get_expirations?currency=ETH&kind=option
		kind = option
		--------------------------------------------------------------------------------
		{
		  "jsonrpc": "2.0",
		  "result": {
			"eth": {
			  "option": [
				"6JUL25",
				"7JUL25",
				"8JUL25",
				"11JUL25",
				"18JUL25",
				"25JUL25",
				"29AUG25",
				"26SEP25",
				"26DEC25",
				"27MAR26",
				"26JUN26"
			  ]
			}
		  },
		  "usIn": 1751730976619923,
		  "usOut": 1751730976620097,
		  "usDiff": 174,
		  "testnet": false
		}
	*/
	
	
}
