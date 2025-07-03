package cc.kostic.gec.deribit.model;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class Option_Info implements Comparable<Option_Info> {

	private String 		base_currency;				// "BTC"
	private BigDecimal	price_change;				//  {JSONObject$Null@5610} "null" - desava se da nema volume, bid, ask i slicno
	private BigDecimal	underlying_price;			// {BigDecimal@5578} "107582.52"
	private BigDecimal	last;						// {BigDecimal@5580} "0.0001"
	private BigDecimal	mark_price;					// {BigDecimal@5582} "0.00014501"
	private Long		creation_timestamp;			// {Long@5584} 1751332159669
	private BigDecimal	open_interest;				// {BigDecimal@5586} "158.1"
	private String		quote_currency;				// "BTC"
	private BigDecimal	ask_price;					// {BigDecimal@5590} "0.0002"
	private String		underlying_index;			// "BTC-11JUL25"
	private BigDecimal	volume;						// {BigDecimal@5594} "95.0"
	private BigDecimal	high;						// {BigDecimal@5596} "0.0001"
	private BigDecimal	low;						// {BigDecimal@5598} "0.0001"
	private String		instrument_name;			// "BTC-11JUL25-150000-C"
	private BigDecimal	estimated_delivery_price;	// {BigDecimal@5602} "107449.87"
	private BigDecimal	interest_rate;				// {BigDecimal@5604} "0.0"
	private BigDecimal	mark_iv;					// {BigDecimal@5606} "72.9"
	private BigDecimal	volume_usd;					// {BigDecimal@5608} "1018.74"
	private BigDecimal	mid_price;					// {JSONObject$Null@5610} "null"
	private BigDecimal	bid_price;					// {JSONObject$Null@5610} "null"
	
	
	public enum PC {
		PUT,
		CALL
	}
	
	public Option_Info(JSONObject o) {
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
	
	public PC getPutCall(){
		return instrument_name.endsWith("-P") ? PC.PUT : PC.CALL;
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
	public int compareTo(Option_Info o) {
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
	GET https://www.deribit.com/api/v2/public/get_instruments
     ?currency=BTC
     &kind=option
     &expired=false
	
	 */
	
	/*
	https://www.deribit.com/api/v2/public/ticker?instrument_name=BTC-30JUN23-65000-C

	 */
	
}


