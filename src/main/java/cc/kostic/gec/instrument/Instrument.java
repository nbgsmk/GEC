package cc.kostic.gec.instrument;

import cc.kostic.gec.primitives.Kind;
import org.json.JSONObject;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Instrument {

	private final JSONObject rawJs;                             // mozda ce da zatreba

	private final String    price_index;                    // "btc_usd",
	private final Boolean   rfq;				            // false,
	private final String    kind;				            // "option",
	private final String    instrument_name;                // "BTC-26JUN26-100000-C",
	private final Double    maker_commission;               // 0.0003,
	private final Double    taker_commission;               // 0.0003,
	private final String    instrument_type;                // "reversed",
	private final Long      expiration_timestamp;           // 1782460800000,
	private final Long      creation_timestamp;             // 1750924810000,
	private final Boolean   is_active;		                // true,
	// OPTION // private final String    option_type;	    // "call",
	private final Double    contract_size;	                // 1,
	private final Double    tick_size;		                // 0.0001,
	// OPTION // private final Double    strike;		    // 100000,
	private final Long      instrument_id;                  // 479190,
	private final String    settlement_period;				// "month",
	private final Double    min_trade_amount;				// 0.1,
	private final Double    block_trade_commission;			// 0.0003,
	private final Double    block_trade_min_trade_amount;	// 25,
	private final Double    block_trade_tick_size;			// 0.0001,
	private final String    settlement_currency;			// "BTC",
	private final String    base_currency;				    // "BTC",
	private final String    counter_currency;				// "USD",
	private final String    quote_currency;				    // "BTC",
	// private final List<Double> tick_size_steps;			// [ { "tick_size": 0.0005,  "above_price": 0.005 } ]


	// OPTION
	//
	// https://www.deribit.com/api/v2/public/get_instrument
	// ?instrument_name=
	// BTC-26JUN26-100000-C
	// --------------------------------------------------------------------------------
	// {
	//   "jsonrpc": "2.0",
	//   "result": {
	//     "price_index": "btc_usd",
	//     "rfq": false,
	//     "kind": "option",                                // OPTION / FUTURE specific
	//     "instrument_name": "BTC-26JUN26-100000-C",
	//     "maker_commission": 0.0003,
	//     "taker_commission": 0.0003,
	//     "instrument_type": "reversed",
	//     "expiration_timestamp": 1782460800000,
	//     "creation_timestamp": 1750924810000,
	//     "is_active": true,
	//     "option_type": "call",                           // OPTION only
	//     "contract_size": 1,
	//     "tick_size": 0.0001,
	//     "strike": 100000,                                // OPTION only
	//     "instrument_id": 479190,
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
	//   "usIn": 1752066535270080,
	//   "usOut": 1752066535270470,
	//   "usDiff": 390,
	//   "testnet": false
	// }


	// FUTURE
	//
	// https://www.deribit.com/api/v2/public/get_instrument
	// ?instrument_name=
	// BTC_USDC-PERPETUAL
	// --------------------------------------------------------------------------------
	// {
	//   "jsonrpc": "2.0",
	//   "result": {
	//     "price_index": "btc_usdc",
	//     "rfq": false,
	//     "kind": "future",
	//     "instrument_name": "BTC_USDC-PERPETUAL",
	//     "maker_commission": 0,
	//     "taker_commission": 0.0005,
	//     "instrument_type": "linear",
	//     "expiration_timestamp": 32503708800000,
	//     "creation_timestamp": 1646824342000,
	//     "is_active": true,
	//     "contract_size": 0.001,
	//     "tick_size": 1,
	//     "instrument_id": 211704,
	//     "settlement_period": "perpetual",
	//     "min_trade_amount": 0.001,
	//     "future_type": "linear",                     // FUTURE only
	//     "max_leverage": 50,                          // FUTURE only
	//     "max_liquidation_commission": 0.0075,        // FUTURE only
	//     "block_trade_commission": 0.0001,
	//     "block_trade_min_trade_amount": 200000,
	//     "block_trade_tick_size": 1,
	//     "settlement_currency": "USDC",
	//     "base_currency": "BTC",
	//     "counter_currency": "USDC",
	//     "quote_currency": "USDC",
	//     "tick_size_steps": []
	//   },
	//   "usIn": 1752066289979842,
	//   "usOut": 1752066289980054,
	//   "usDiff": 212,
	//   "testnet": false
	// }


	public Instrument(JSONObject o) {
		this.rawJs = o;

		this.price_index                    = o.getString("price_index");
		this.rfq    		 		        = o.getBoolean("rfq");
		this.kind    		 		        = o.getString("kind");
		this.instrument_name                = o.getString("instrument_name");
		this.maker_commission               = o.getDouble("maker_commission");
		this.taker_commission               = o.getDouble("taker_commission");
		this.instrument_type                = o.getString("instrument_type");
		// this.expiration_timestamp        = o.optBigDecimal("expiration_timestamp", null).longValue();
		this.expiration_timestamp           = o.getLong("expiration_timestamp");
		this.creation_timestamp             = o.getLong("creation_timestamp");
		this.is_active    		 	        = o.getBoolean("is_active");
		// samo OPTION   this.option_type   = o.getString("option_type");
		this.contract_size   		        = o.getDouble("contract_size");
		this.tick_size    		 	        = o.getDouble("tick_size");
		// samo OPTION  this.strike         = o.getDouble("strike");
		this.instrument_id                  = o.getLong("instrument_id");
		this.settlement_period    	        = o.getString("settlement_period");
		this.min_trade_amount    	        = o.getDouble("min_trade_amount");
		this.block_trade_commission         = o.getDouble("block_trade_commission");
		this.block_trade_min_trade_amount   = o.getDouble("block_trade_min_trade_amount");
		this.block_trade_tick_size          = o.getDouble("block_trade_tick_size");
		this.settlement_currency   	        = o.getString("settlement_currency");
		this.base_currency    		        = o.getString("base_currency");
		this.counter_currency    	        = o.getString("counter_currency");
		this.quote_currency    		        = o.getString("quote_currency");
		// this.tick_size_steps    	        = o.getJSONArray("tick_size_steps");

	}


	/// ////////////////
	// BOJLER
	/// ////////////////
	public JSONObject getRawJSON(){
		return this.rawJs;
	}
	public String getPrice_index() {
		return price_index;
	}
	public Boolean getRfq() {
		return rfq;
	}
	public Kind getKind() {
		return switch (kind) {
			case "option" -> Kind.OPTION;
			case "future" -> Kind.FUTURE;
			case "option_combo" -> Kind.OPTION_COMBO;
			case "future_combo" -> Kind.FUTURE_COMBO;
			default -> Kind.UNKNOWN;
		};
	}
	public String getInstrument_name() {
		return instrument_name;
	}
	public Double getMaker_commission() {
		return maker_commission;
	}
	public Double getTaker_commission() {
		return taker_commission;
	}
	public String getInstrument_type() {
		return instrument_type;
	}
	public Long getExpiration_timestamp() {
		return expiration_timestamp;
	}
	public Long getCreation_timestamp() {
		return creation_timestamp;
	}
	public Boolean getIs_active() {
		return is_active;
	}
	public Double getContract_size() {
		return contract_size;
	}
	public Double getTick_size() {
		return tick_size;
	}
	public Long getInstrument_id() {
		return instrument_id;
	}
	public String getSettlement_period() {
		return settlement_period;
	}
	public Double getMin_trade_amount() {
		return min_trade_amount;
	}
	public Double getBlock_trade_commission() {
		return block_trade_commission;
	}
	public Double getBlock_trade_min_trade_amount() {
		return block_trade_min_trade_amount;
	}
	public Double getBlock_trade_tick_size() {
		return block_trade_tick_size;
	}
	public String getSettlement_currency() {
		return settlement_currency;
	}
	public String getBase_currency() {
		return base_currency;
	}
	public String getCounter_currency() {
		return counter_currency;
	}
	public String getQuote_currency() {
		return quote_currency;
	}


	/// /////////////////////
	// moj specific
	/// /////////////////////

	public String getExpirationString(){
		Instant instant = Instant.ofEpochMilli(this.expiration_timestamp);
		ZonedDateTime zdt = instant.atZone(ZoneId.of("UTC"));
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("ddMMMyy");
		String s = fmt.format(zdt);
		return s;
	}

	public Instant getExpirationInstant() {
		return Instant.ofEpochMilli(this.expiration_timestamp);
	}


	public String toString() {
		return this.getInstrument_name();
	}


}
