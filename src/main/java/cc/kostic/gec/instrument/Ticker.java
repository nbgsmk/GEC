package cc.kostic.gec.instrument;

import cc.kostic.gec.primitives.Kind;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Ticker {
	
	/*
	OPTION TICKER
	
	https://www.deribit.com/api/v2/public/
	ticker?
	instrument_name=
	BTC-27MAR26-100000-C
	--------------------------------------------------------------------------------
	{
	  "jsonrpc": "2.0",
	  "result": {
		"timestamp": 1752785118436,
		"state": "open",
		"stats": {
		  "high": 0.256,
		  "low": 0.256,
		  "price_change": 0,
		  "volume": 0.1,
		  "volume_usd": 3039.49
		},
		"greeks": {
		  "delta": 0.78899,
		  "gamma": 0.00001,
		  "vega": 301.89834,
		  "theta": -26.48201,
		  "rho": 462.09342
		},
		"index_price": 119323.1,
		"instrument_name": "BTC-27MAR26-100000-C",
		"last_price": 0.256,
		"settlement_price": 0.25486038,
		"min_price": 0.2175,
		"max_price": 0.3065,
		"open_interest": 1083.8,
		"mark_price": 0.2571,
		"best_bid_price": 0.2555,
		"mark_iv": 44.29,
		"ask_iv": 45.08,
		"bid_iv": 43.63,
		"underlying_price": 125599.49,
		"underlying_index": "BTC-27MAR26",
		"best_ask_price": 0.259,
		"interest_rate": 0,
		"estimated_delivery_price": 119323.1,
		"best_ask_amount": 7.5,
		"best_bid_amount": 5.9
	  },
	  "usIn": 1752785119376671,
	  "usOut": 1752785119376984,
	  "usDiff": 313,
	  "testnet": false
	}
	
	*/


	/*
	
	FUTURE TICKER = TODO: NIJE IMPLEMENTIRANO
	
	https://www.deribit.com/api/v2/public/
	ticker?
	instrument_name=
	ETH-PERPETUAL
	--------------------------------------------------------------------------------
	{
	  "jsonrpc": "2.0",
	  "result": {
		"timestamp": 1752785500504,
		"state": "open",
		"stats": {
		  "high": 3484,
		  "low": 3311.25,
		  "price_change": 0.6886,
		  "volume": 135372.161496,
		  "volume_usd": 461695956,
		  "volume_notional": 461695956
		},
		"index_price": 3406.92,
		"instrument_name": "ETH-PERPETUAL",
		"last_price": 3407.2,
		"settlement_price": 3448.98,
		"min_price": 3355.4,
		"max_price": 3457.65,
		"open_interest": 448470865,
		"mark_price": 3406.54,
		"interest_value": 0.2049387411731667,
		"best_bid_price": 3406.5,
		"best_ask_price": 3406.55,
		"estimated_delivery_price": 3406.92,
		"best_ask_amount": 4289,
		"best_bid_amount": 39599,
		"current_funding": 0,
		"funding_8h": 0.000003
	  },
	  "usIn": 1752785500521853,
	  "usOut": 1752785500522030,
	  "usDiff": 177,
	  "testnet": false
	}

	*/

	private final Map<String, ?> raw;                             // mozda ce da zatreba
	
	public Ticker(Map<String, ?> o) {
		this.raw = o;
		
	}


	/// ////////////////
	// BOJLER
	/// ////////////////
	public Map<String,?> getRawMap(){
		return this.raw;
	}
	
	
	public BigDecimal getTimestamp() {
		return new BigDecimal(raw.get("timestamp").toString());
	}
	public String getState() {
		return (String) raw.get("state");
	}
	public Stats getStats(){
		Map<String, ?> m = new HashMap<>();
		m = (Map<String, ?>) raw.get("stats");		// STATS je nested mapa -> key "stats")
		return new Stats(m);
	}
	public Stats getGreeks(){
		Map<String, ?> m = new HashMap<>();
		m = (Map<String, ?>) raw.get("greeks");		// GREEKS je nested mapa -> key "greeks"
		return new Stats(m);
	}
	public BigDecimal getIndexPrice() {
		return new BigDecimal(raw.get("index_price").toString());
	}
	public String getInstrumentName() {
		return (String) raw.get("instrument_name");
	}
	public BigDecimal getLastPrice() {
		return new BigDecimal(raw.get("last_price").toString());
	}
	public BigDecimal getSettlementPrice() {
		return new BigDecimal(raw.get("settlement_price").toString());
	}
	public BigDecimal getMinPrice() {
		return new BigDecimal(raw.get("min_price").toString());
	}
	public BigDecimal getMaxPrice() {
		return new BigDecimal(raw.get("max_price").toString());
	}
	public BigDecimal getOpenInterest() {
		return new BigDecimal(raw.get("open_interest").toString());
	}
	public BigDecimal getMarkPrice() {
		return new BigDecimal(raw.get("mark_price").toString());
	}
	public BigDecimal getBestBidPrice() {
		return new BigDecimal(raw.get("best_bid_price").toString());
	}
	public BigDecimal getMarkIV() {
		return new BigDecimal(raw.get("mark_iv").toString());
	}
	public BigDecimal getAskIV() {
		return new BigDecimal(raw.get("ask_iv").toString());
	}
	public BigDecimal getBidIV() {
		return new BigDecimal(raw.get("bid_iv").toString());
	}
	public BigDecimal getUnderlyingPrice() {
		return new BigDecimal(raw.get("underlying_price").toString());
	}
	public String getUnderlyingIndex() {
		return (String) raw.get("underlying_index");
	}
	public BigDecimal getBestAskPrice() {
		return new BigDecimal(raw.get("best_ask_price").toString());
	}
	public BigDecimal getInterestRate() {
		return new BigDecimal(raw.get("interest_rate").toString());
	}
	public BigDecimal getEstimatedDeliveryPrice() {
		return new BigDecimal(raw.get("estimated_delivery_price").toString());
	}
	public BigDecimal getBestAskAmount() {
		return new BigDecimal(raw.get("best_ask_amount").toString());
	}
	public BigDecimal getBestBidAmount() {
		return new BigDecimal(raw.get("best_bid_amount").toString());
	}

	
	
	

	/// /////////////////////
	// moj specific
	/// /////////////////////

	public String getTimestampString(){
		Instant instant = Instant.ofEpochMilli(getTimestamp().longValue());
		ZonedDateTime zdt = instant.atZone(ZoneId.of("UTC"));
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("ddMMMyy");
		String s = fmt.format(zdt);
		return s;
	}

	public Instant getTimestampInstant() {
		return Instant.ofEpochMilli(getTimestamp().longValue());
	}


	@Override
	public String toString() {
		return this.getInstrumentName();
	}
	
	
}
