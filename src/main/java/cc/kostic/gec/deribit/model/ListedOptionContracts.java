package cc.kostic.gec.deribit.model;

import org.json.JSONObject;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ListedOptionContracts {

	private String 	price_index;				// "btc_usd"
	private String 	kind;						// "option"
	private String	instrument_name;			// "BTC-11JUL25-150000-C"
	private Long	expiration_timestamp;		// 1751875200000
	private Long	creation_timestamp;			// 1751616012000
	private boolean	is_active;					// true
	private String	option_type;				// "call"
	private Long	strike;						// 96000
	private Long	min_trade_amount;			// 0.1
	
	private SortedSet<Long> expirations = new TreeSet<>();
	private SortedSet<String> instrumentNames = new TreeSet<>();
	
	public ListedOptionContracts() {
	
	}
	
	public void add(JSONObject o){
		this.price_index = o.getString("price_index");
		this.kind = o.getString("kind");
		this.instrument_name = o.getString("instrument_name");
		this.expiration_timestamp = o.optBigDecimal("expiration_timestamp", null).longValue();
		this.creation_timestamp = o.optBigDecimal("creation_timestamp", null).longValue();
		this.is_active = o.getBoolean("is_active");
		this.option_type = o.getString("option_type");
		this.strike = o.optBigDecimal("strike", null).longValue();
		this.min_trade_amount = o.optBigDecimal("min_trade_amount", null).longValue();
		
		expirations.add(expiration_timestamp);
		instrumentNames.add(instrument_name);
	}
	
	public SortedSet<Long> getExpirationTimestamps(){
		return expirations;
	}
	public List<String> getExpirationsStrings(){
		List<String> strs = new ArrayList<>();
		strs = new ArrayList<>();
		for (Long e : expirations) {
			String s = toExpStr(e);
			strs.add(s);
		}
		return strs;
	}
	
	public List<String> getInstrumentNames() {
		// return instrumentNames;
		List<String> strs = new ArrayList<>();
		strs = new ArrayList<>();
		strs.addAll(instrumentNames);
		return strs;
	}
	
	private String toExpStr(Long exp_stamp) {
		Instant instant = Instant.ofEpochMilli(exp_stamp);
		ZonedDateTime zdt = instant.atZone(ZoneId.of("UTC"));
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yy");
		String output = fmt.format(zdt);
		return output;
	}
	
	
	@Override
	public String toString() {
		return instrument_name;
	}
	
	
}


