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
	private Long	expiration_timestamp;		// 1751443200000
	private boolean	is_active;					// true
	private String	option_type;				// "call"
	private Long	strike;						// 96000
	
	private SortedSet<Long> expirations = new TreeSet<>();
	
	public ListedOptionContracts() {
	
	}
	
	public void add(JSONObject o){
		this.price_index = o.getString("price_index");
		this.kind = o.getString("kind");
		this.instrument_name = o.getString("instrument_name");
		this.expiration_timestamp = o.optBigDecimal("expiration_timestamp", null).longValue();
		this.is_active = o.getBoolean("is_active");
		this.option_type = o.getString("option_type");
		this.strike = o.optBigDecimal("strike", null).longValue();
		
		expirations.add(expiration_timestamp);
	}
	
	public SortedSet<Long> getTimestamps(){
		return expirations;
	}
	public List<String> getTimeStampsStr(){
		List<String> strs = new ArrayList<>();
		strs = new ArrayList<>();
		for (Long e : expirations) {
			String s = getExpirationString(e);
			strs.add(s);
		}
		return strs;
	}
	
	private String getExpirationString(Long exp_stamp) {
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


