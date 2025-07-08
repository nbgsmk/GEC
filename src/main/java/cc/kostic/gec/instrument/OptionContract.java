package cc.kostic.gec.instrument;

import org.json.JSONObject;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class OptionContract extends Instrument{

	private String 	price_index;				// "btc_usd"
	private String 	kind;						// "option"
	private String	instrument_name;			// "BTC-11JUL25-150000-C"
	private Long	expiration_timestamp;		// 1751875200000
	private Long	creation_timestamp;			// 1751616012000
	private boolean	is_active;					// true
	private String	option_type;				// "call"
	private Long	contract_size;				// 1
	private Long	strike;						// 96000
	private Long	instrument_id;				// 482733
	private Long	min_trade_amount;			// 0.1
	
	
	public OptionContract(JSONObject o) {
		this.price_index = o.getString("price_index");
		this.kind = o.getString("kind");
		this.instrument_name = o.getString("instrument_name");
		this.expiration_timestamp = o.optBigDecimal("expiration_timestamp", null).longValue();
		this.creation_timestamp = o.optBigDecimal("creation_timestamp", null).longValue();
		this.is_active = o.getBoolean("is_active");
		this.option_type = o.getString("option_type");
		this.contract_size = o.optBigDecimal("contract_size", null).longValue();
		this.strike = o.optBigDecimal("strike", null).longValue();
		this.instrument_id = o.optBigDecimal("instrument_id", null).longValue();
		this.min_trade_amount = o.optBigDecimal("min_trade_amount", null).longValue();
		
	}
	
	public String getExpirationStr(){
		Instant instant = Instant.ofEpochMilli(this.expiration_timestamp);
		ZonedDateTime zdt = instant.atZone(ZoneId.of("UTC"));
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("ddMMMyy");
		String s = fmt.format(zdt);
		return s;
	}
	
	public String getInstrument_name() {
		return instrument_name;
	}
	
	public Long getExpirationTimestamp() {
		return this.expiration_timestamp;
	}
	
	public Instant getExpirationInstant() {
		return Instant.ofEpochMilli(this.expiration_timestamp);
	}
	
	public String getOption_type() {
		return option_type;
	}
	
	public Long getStrike() {
		return strike;
	}
	
	@Override
	public String toString() {
		return instrument_name;
	}
	
	
}


