package cc.kostic.gec.endpoints.deribit;

import cc.kostic.gec.instrument.Greeks;
import cc.kostic.gec.instrument.OptionContract;
import cc.kostic.gec.primitives.Currency;
import cc.kostic.gec.primitives.Expiration;
import cc.kostic.gec.primitives.Kind;
import cc.kostic.gec.web.Fetcher;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.time.Instant;
import java.util.*;

public class GetInstruments {

	private JSONObject jsResponse;
	private final BaseURL b;
	private final Currency currency;
	private final Kind kind;
	private Instant rspUsIn;
	private Instant rspUsOut;
	
	private final List<OptionContract> allOptionContracts = new ArrayList<>();
	private final SortedSet<Expiration> sortedExpirations = new TreeSet<>();

	public static SimpleIntegerProperty kaunt = new SimpleIntegerProperty();
	
	public enum SRC{
		WEB,
		DISK
	}
	
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


	public List<OptionContract> getContracts(SRC dataSource){
		switch (dataSource){
			case WEB -> {
				jsResponse = getFromWeb();
				JSONArray jsInstruments = jsResponse.getJSONArray(DeribitJSONrsp.glupkey);
				for (int i = 0; i < jsInstruments.length(); i++) {
					JSONObject obj = jsInstruments.getJSONObject(i);
					obj = getGreeksFromWeb(obj);
					OptionContract oc = new OptionContract(obj);
					this.allOptionContracts.add(oc);
					this.sortedExpirations.add(new Expiration(oc.getExpiration_timestamp()));
					kaunt.set(i);
				}
			}

			case DISK -> {
				jsResponse = getFromDisk();
				JSONArray jsInstruments = jsResponse.getJSONArray(DeribitJSONrsp.glupkey);
				for (int i = 0; i < jsInstruments.length(); i++) {
					JSONObject obj = jsInstruments.getJSONObject(i);
					OptionContract oc = new OptionContract(obj);
					this.allOptionContracts.add(oc);
					this.sortedExpirations.add(new Expiration(oc.getExpiration_timestamp()));
					kaunt.set(i);
				}
			}
		}

		return allOptionContracts;
	}
	
	private JSONObject getFromWeb(){
		String reqUrl = buildReq();
		Fetcher f = new Fetcher(reqUrl);
		JSONObject raw = f.fetch();
		DeribitJSONrsp dr = new DeribitJSONrsp(raw);
		rspUsIn = dr.getUsIn();
		rspUsOut = dr.getUsOut();
		return dr.getResultObject();
	}

	private JSONObject getFromDisk() {
		JSONObject rezult = new JSONObject();
		try (FileInputStream fis = new FileInputStream("chain_oos.txt");
			 BufferedInputStream bis = new BufferedInputStream(fis);
			 ObjectInputStream ois = new ObjectInputStream(bis);){
			Object infile = ois.readObject();
			if (infile instanceof String) {
				rezult = new JSONObject((String) infile);	// FULL FREEZE!! cast (String) inace se dobije naizgled ispravan json objekat ali nema potrebne podatke
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
		return rezult;
	}

	public void writeToDisk() {
		try (FileOutputStream fos = new FileOutputStream("chain_oos.txt");
			 BufferedOutputStream bos = new BufferedOutputStream(fos);
			 ObjectOutputStream oos = new ObjectOutputStream(bos);) {
			oos.writeObject(this.jsResponse.toString(4));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	public SortedSet<Expiration> getExpirations() {
		return sortedExpirations;
	}


	private JSONObject getGreeksFromWeb(JSONObject jsContract){
		OptionContract contract = new OptionContract(jsContract);
		String s = contract.getInstrument_name();
		GetTicker t = new GetTicker(s);
		JSONObject gg = t.getResult();
		jsContract.accumulate("greeks", gg.getJSONObject("greeks"));
		return jsContract;
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
