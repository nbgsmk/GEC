package cc.kostic.gec.instrument;

import cc.kostic.gec.primitives.Kind;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Instrument implements Serializable {
	
	/*
	
	OPTION
	
	https://www.deribit.com/api/v2/public/get_instrument
	?instrument_name=
	BTC-26JUN26-100000-C
	--------------------------------------------------------------------------------
	{
		"jsonrpc": "2.0",
		"result": {
			"price_index": "btc_usd",
			"rfq": false,
			"kind": "option",                                // OPTION / FUTURE specific
			"instrument_name": "BTC-26JUN26-100000-C",
			"maker_commission": 0.0003,
			"taker_commission": 0.0003,
			"instrument_type": "reversed",
			"expiration_timestamp": 1782460800000,
			"creation_timestamp": 1750924810000,
			"is_active": true,
			"option_type": "call",                           // OPTION only
			"contract_size": 1,
			"tick_size": 0.0001,
			"strike": 100000,                                // OPTION only
			"instrument_id": 479190,
			"settlement_period": "month",
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
		"usIn": 1752066535270080,
		"usOut": 1752066535270470,
		"usDiff": 390,
		"testnet": false
	}
	
	*/


	/*
	
	FUTURE
	
	https://www.deribit.com/api/v2/public/get_instrument
	?instrument_name=
	BTC_USDC-PERPETUAL
	--------------------------------------------------------------------------------
	{
	  "jsonrpc": "2.0",
	  "result": {
	    "price_index": "btc_usdc",
	    "rfq": false,
	    "kind": "future",
	    "instrument_name": "BTC_USDC-PERPETUAL",
	    "maker_commission": 0,
	    "taker_commission": 0.0005,
	    "instrument_type": "linear",
	    "expiration_timestamp": 32503708800000,
	    "creation_timestamp": 1646824342000,
	    "is_active": true,
	    "contract_size": 0.001,
	    "tick_size": 1,
	    "instrument_id": 211704,
	    "settlement_period": "perpetual",
	    "min_trade_amount": 0.001,
	    "future_type": "linear",                     // FUTURE only
	    "max_leverage": 50,                          // FUTURE only
	    "max_liquidation_commission": 0.0075,        // FUTURE only
	    "block_trade_commission": 0.0001,
	    "block_trade_min_trade_amount": 200000,
	    "block_trade_tick_size": 1,
	    "settlement_currency": "USDC",
	    "base_currency": "BTC",
	    "counter_currency": "USDC",
	    "quote_currency": "USDC",
	    "tick_size_steps": []
	  },
	  "usIn": 1752066289979842,
	  "usOut": 1752066289980054,
	  "usDiff": 212,
	  "testnet": false
	}
	
	*/

	private final Map<String, ?> raw;                             // mozda ce da zatreba
	
	public Instrument(Map<String, ?> o) {
		this.raw = o;
		
	}


	/// ////////////////
	// BOJLER
	/// ////////////////
	public Map<String,?> getRawMap(){
		return this.raw;
	}
	public String getPrice_index() {
		return raw.get("price_index").toString();
	}
	public Boolean getRfq() {
		return (Boolean) raw.get("rfq");
	}
	public Kind getKind() {
		String k = (String) raw.get("kind");
		return switch (k) {
			case "option" -> Kind.OPTION;
			case "future" -> Kind.FUTURE;
			case "option_combo" -> Kind.OPTION_COMBO;
			case "future_combo" -> Kind.FUTURE_COMBO;
			default -> Kind.UNKNOWN;
		};
	}
	public String getInstrument_name() {
		return raw.get("instrument_name").toString();
	}
	public BigDecimal getMaker_commission() {
		return new BigDecimal(  raw.get("maker_commission").toString() );
	}
	public BigDecimal getTaker_commission() {
		return new BigDecimal(  raw.get("taker_commission").toString() );
	}
	public String getInstrument_type() {
		return raw.get("instrument_type").toString();
	}
	public BigDecimal getExpiration_timestamp() {
		return new BigDecimal(  raw.get("expiration_timestamp").toString() );
	}
	public BigDecimal getCreation_timestamp() {
		return new BigDecimal(  raw.get("creation_timestamp").toString() );
	}
	public Boolean getIs_active() {
		return (Boolean) raw.get("is_active");
	}
	public BigDecimal getContract_size() {
		return new BigDecimal(  raw.get("contract_size").toString() );
	}
	public BigDecimal getTick_size() {
		return new BigDecimal(  raw.get("tick_size").toString() );
	}
	public BigDecimal getInstrument_id() {
		return new BigDecimal(  raw.get("instrument_id").toString() );
	}
	public String getSettlement_period() {
		return raw.get("settlement_period").toString();
	}
	public BigDecimal getMin_trade_amount() {
		return new BigDecimal(  raw.get("min_trade_amount").toString() );
	}
	public BigDecimal getBlock_trade_commission() {
		return new BigDecimal(  raw.get("block_trade_commission").toString() );
	}
	public BigDecimal getBlock_trade_min_trade_amount() {
		return new BigDecimal(  raw.get("block_trade_min_trade_amount").toString() );
	}
	public BigDecimal getBlock_trade_tick_size() {
		return new BigDecimal(  raw.get("block_trade_tick_size").toString() );
	}
	public String getSettlement_currency() {
		return raw.get("settlement_currency").toString();
	}
	public String getBase_currency() {
		return raw.get("base_currency").toString();
	}
	public String getCounter_currency() {
		return raw.get("counter_currency").toString();
	}
	public String getQuote_currency() {
		return raw.get("quote_currency").toString();
	}


	/// /////////////////////
	// moj specific
	/// /////////////////////

	public String getExpirationString(){
		Instant instant = Instant.ofEpochMilli(getExpiration_timestamp().longValue());
		ZonedDateTime zdt = instant.atZone(ZoneId.of("UTC"));
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("ddMMMyy");
		String s = fmt.format(zdt);
		return s;
	}

	public Instant getExpirationInstant() {
		return Instant.ofEpochMilli(getExpiration_timestamp().longValue());
	}


	@Override
	public String toString() {
		return this.getInstrument_name();
	}
	
	
}
