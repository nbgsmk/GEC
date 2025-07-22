package cc.kostic.gec.endpoints.deribit;

import cc.kostic.gec.generic.DiskCache;
import cc.kostic.gec.instrument.OptionContract;
import cc.kostic.gec.primitives.Currency;
import cc.kostic.gec.primitives.Expiration;
import cc.kostic.gec.primitives.Kind;
import cc.kostic.gec.web.Fetcher;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class GetInstruments {
	
	private final String reqUrl;
	private DeribitRsp dr;
	
	private final List<OptionContract> allOptionContracts = new ArrayList<>();
	private final SortedSet<Expiration> sortedExpirations = new TreeSet<>();
	
	public static final IntegerProperty kaunt = new SimpleIntegerProperty();
	public static final StringProperty status = new SimpleStringProperty();
	
	
	// constructor
	public GetInstruments(Currency currency, Kind kind) {
		this.reqUrl = buildReq(new BaseURL(), currency, kind);
	}
	
	private String buildReq(BaseURL b, Currency currency, Kind kind){
		// return https://www.deribit.com/api/v2/public/get_instruments?currency=BTC&expired=true&kind=option
		// return https://www.deribit.com/api/v2/public/get_instruments?currency=BTC&expired=false&kind=option
		// return https://www.deribit.com/api/v2/public/get_instruments?currency=BTC&kind=option
		return b.pub() + "/get_instruments?currency=" + currency + "&expired=false&kind=" + kind;
	}

	// public void run(){
	// 	Fetcher f = new Fetcher(reqUrl);
	// 	JSONObject jsRsp = f.fetch();
	// 	this.dr = new DeribitRsp(jsRsp);
	// }
	
	
	
	public List<OptionContract> getResult(DataSRC dataSource){
		switch (dataSource){
			case WEB -> {
				dr = getFromWeb();
				List<?> hashMapovi = dr.getResultObject(List.class);
				for (int i = 0; i < hashMapovi.size(); i++) {
					Map<String, ?> hm = (Map<String, ?>) hashMapovi.get(i);
					OptionContract oc = new OptionContract(hm);
					this.allOptionContracts.add(oc);
					this.sortedExpirations.add(new Expiration(oc.getExpiration_timestamp()));
					int finalI = i;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							kaunt.set(finalI);
							status.set(finalI + "/" + hashMapovi.size());
						}
					});
				}
			}

			case DISK -> {
				// dr = getFromDisk();
				dr = (DeribitRsp) DiskCache.getFromStorage("option_chains.oos");
				List<Map<String, ?>> list = dr.getResultObject(List.class);
				for (int i = 0; i < list.size(); i++) {
					OptionContract oc = new OptionContract(list.get(i));
					this.allOptionContracts.add(oc);
					this.sortedExpirations.add(new Expiration(oc.getExpiration_timestamp()));
					int finalI = i;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							kaunt.set(finalI);
							status.set(finalI + "/" + list.size());
						}
					});
				}
			}
		}

		return allOptionContracts;
	}
	
	private DeribitRsp getFromWeb(){
		Fetcher f = new Fetcher(this.reqUrl);
		JSONObject jsRsp = f.fetch();
		return new DeribitRsp(jsRsp);
	}

	

	public void writeToDisk(){
		DiskCache.writeToStorage(dr, "option_chains.oos");
	}



	
	public SortedSet<Expiration> getExpirations() {
		return sortedExpirations;
	}
	
	

	/*
	
	https://www.deribit.com/api/v2/public/get_instruments?currency=ETH&expired=false&kind=option
	expired=false
 	kind=option
	--------------------------------------------------------------------------------
	{
	  "jsonrpc": "2.0",
	  "result": [
	    {
	      "price_index": "eth_usd",
	      "rfq": false,
	      "kind": "option",
	      "instrument_name": "ETH-6JUL25-1500-C",
	      "maker_commission": 0.0003,
	      "taker_commission": 0.0003,
	      "instrument_type": "reversed",
	      "expiration_timestamp": 1751788800000,
	      "creation_timestamp": 1751529610000,
	      "is_active": true,
	      "option_type": "call",
	      "contract_size": 1,
	      "tick_size": 0.0001,
	      "strike": 1500,
	      "instrument_id": 481742,
	      "settlement_period": "day",
	      "min_trade_amount": 1,
	      "block_trade_commission": 0.0003,
	      "block_trade_min_trade_amount": 250,
	      "block_trade_tick_size": 0.0001,
	      "settlement_currency": "ETH",
	      "base_currency": "ETH",
	      "counter_currency": "USD",
	      "quote_currency": "ETH",
	      "tick_size_steps": [
	        {
	          "tick_size": 0.0005,
	          "above_price": 0.005
	        }
	      ]
	    },
	    {
	      "price_index": "eth_usd",
	      "rfq": false,
	      "kind": "option",
	      "instrument_name": "ETH-6JUL25-1500-P",
	      "maker_commission": 0.0003,
	      "taker_commission": 0.0003,
	      "instrument_type": "reversed",
	      "expiration_timestamp": 1751788800000,
	      "creation_timestamp": 1751529610000,
	      "is_active": true,
	      "option_type": "put",
	      "contract_size": 1,
	      "tick_size": 0.0001,
	      "strike": 1500,
	      "instrument_id": 481743,
	      "settlement_period": "day",
	      "min_trade_amount": 1,
	      "block_trade_commission": 0.0003,
	      "block_trade_min_trade_amount": 250,
	      "block_trade_tick_size": 0.0001,
	      "settlement_currency": "ETH",
	      "base_currency": "ETH",
	      "counter_currency": "USD",
	      "quote_currency": "ETH",
	      "tick_size_steps": [
	        {
	          "tick_size": 0.0005,
	          "above_price": 0.005
	        }
	      ]
	    }
	  ],
	  "usIn": 1751758217174939,
	  "usOut": 1751758217175538,
	  "usDiff": 599,
	  "testnet": false
	}
	
	*/
}
