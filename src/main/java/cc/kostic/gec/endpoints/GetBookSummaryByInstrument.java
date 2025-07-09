package cc.kostic.gec.endpoints;



import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class GetBookSummaryByInstrument implements Comparable<GetBookSummaryByInstrument> {
	
	private BigDecimal	high;						// {BigDecimal@5596} "0.0001"
	private BigDecimal	low;						// {BigDecimal@5598} "0.0001"
	private BigDecimal	last;						// {BigDecimal@5580} "0.0001"
	private String		instrument_name;			// "BTC-26JUN26-100000-C"
	private BigDecimal	bid_price;					// {JSONObject$Null@5610} "null"
	private BigDecimal	ask_price;					// {BigDecimal@5590} "0.0002"
	private BigDecimal	open_interest;				// {BigDecimal@5586} "158.1"
	private BigDecimal	mark_price;					// {BigDecimal@5582} "0.00014501"
	private Long		creation_timestamp;			// {Long@5584} 1751332159669
	private BigDecimal	price_change;				//  {JSONObject$Null@5610} "null" - desava se da nema volume, bid, ask i slicno
	private BigDecimal	mark_iv;					// {BigDecimal@5606} "72.9"
	private BigDecimal	underlying_price;			// {BigDecimal@5578} "107582.52"
	private String		underlying_index;			// "BTC-11JUL25"
	private BigDecimal	interest_rate;				// {BigDecimal@5604} "0.0"
	private BigDecimal	volume;						// {BigDecimal@5594} "95.0"
	private BigDecimal	estimated_delivery_price;	// {BigDecimal@5602} "107449.87"
	private String 		base_currency;				// "BTC"
	private String		quote_currency;				// "BTC"
	private BigDecimal	volume_usd;					// {BigDecimal@5608} "1018.74"
	private BigDecimal	mid_price;					// {JSONObject$Null@5610} "null"
	
	
	
	public GetBookSummaryByInstrument(JSONObject o) {
		this.base_currency = o.getString("base_currency");
		this.price_change = o.optBigDecimal("price_change", null);
		this.underlying_price = o.optBigDecimal("underlying_price", null);
		this.last = o.optBigDecimal("last", null);
		this.mark_price = o.optBigDecimal("mark_price", null);
		this.creation_timestamp = o.getLong("creation_timestamp");
		this.open_interest = o.optBigDecimal("open_interest", null);
		this.quote_currency = o.getString("quote_currency");
		this.ask_price = o.optBigDecimal("ask_price", null);
		this.underlying_index = o.getString("underlying_index");
		this.volume = o.optBigDecimal("volume", null);
		this.high = o.optBigDecimal("high", null);
		this.low = o.optBigDecimal("low", null);
		this.instrument_name = o.getString("instrument_name");
		this.estimated_delivery_price = o.optBigDecimal("estimated_delivery_price", null);
		this.interest_rate = o.optBigDecimal("interest_rate", null);
		this.mark_iv = o.optBigDecimal("mark_iv", null);
		this.volume_usd = o.optBigDecimal("volume_usd", null);
		this.mid_price = o.optBigDecimal("mid_price", null);
		this.bid_price = o.optBigDecimal("bid_price", null);
	}
	
	
	public Instant getExpiration() {
		String samoDatum = underlying_index.substring(underlying_index.indexOf("-")+1);	// BTC-11JUL25 izbacujem "BTC-"
		if (samoDatum.length() == 6) {
			samoDatum = "0" + samoDatum;			// 1JUL25 dodajem "0" -> 01JUL25
		}
		
		DateTimeFormatter fmt = new DateTimeFormatterBuilder()
				.parseCaseInsensitive()
				.appendPattern("ddMMMyy")
				.toFormatter(Locale.ENGLISH);
		
		LocalDate date = LocalDate.parse(samoDatum, fmt);
		ZonedDateTime zdt = date.atStartOfDay(ZoneId.of("UTC"));
		Instant instant = zdt.toInstant();
		return instant;
	}
	
	
	
	
	
	@Override
	public int compareTo(GetBookSummaryByInstrument o) {
		// Return a negative integer if o1 < o2
		// Return a positive integer if o1 > o2
		// Return zero if o1 == o2
		return getExpiration().compareTo(o.getExpiration());
	}
	
	
	
	
	@Override
	public String toString() {
		String s = "";
		s += instrument_name + "\t " + bid_price + "\t " + ask_price;
		return s;
	}
	
	
	/*
	For each option contract i, calculate its dollar‐gamma contribution as:
	GEXᵢ = Γᵢ × ContractSize × OIᵢ × S² × 0.01
	Γᵢ = option’s unit gamma (change in delta per $1 move in S)
	ContractSize = 100 (for standard equity options)
	OIᵢ = open interest
	S² = square of current spot price
	0.01 = 1% move in the underlying
 */
	
	
	
	/*
	https://www.deribit.com/api/v2/public/get_book_summary_by_instrument?instrument_name=BTC-26JUN26-100000-C
	--------------------------------------------------------------------------------
	{
	  "jsonrpc": "2.0",
	    "result": [
		{
		  "high": null,
		  "low": null,
		  "last": 0.247,
		  "instrument_name": "BTC-26JUN26-100000-C",
		  "bid_price": 0.24,
		  "ask_price": 0.245,
		  "open_interest": 1.2,
		  "mark_price": 0.24229668,
		  "creation_timestamp": 1752013414221,
		  "price_change": null,
		  "mark_iv": 45.36,
		  "underlying_price": 116076.41,
		  "underlying_index": "BTC-26JUN26",
		  "interest_rate": 0,
		  "volume": 0,
		  "estimated_delivery_price": 108887,
		  "base_currency": "BTC",
		  "quote_currency": "BTC",
		  "volume_usd": 0,
		  "mid_price": 0.2425
		}
	  ],
	  "usIn": 1752013414221535,
	  "usOut": 1752013414221699,
	  "usDiff": 164,
	  "testnet": false
	}
	
	 */
	

	
}


