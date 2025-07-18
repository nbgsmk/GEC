package cc.kostic.gec.instrument;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class Stats {
	
	/*
	STATS je deo odgoovora za endpoint /ticker?

		"stats": {
			"high": 0.256,
			"low": 0.256,
			"price_change": 0,
			"volume": 0.1,
			"volume_usd": 3039.49
		},
	 */
	
	private final Map<String, ?> raw;                             // mozda ce da zatreba
	
	public Stats(Map<String, ?> o) {
		this.raw = o;
	}
	
	
	public BigDecimal getHigh() {
		return new BigDecimal(raw.get("high").toString());
	}
	
	public BigDecimal getLow() {
		return new BigDecimal(raw.get("low").toString());
	}
	
	public BigDecimal getPriceChange() {
		return new BigDecimal(raw.get("price_change").toString());
	}
	
	public BigDecimal getvolume() {
		return new BigDecimal(raw.get("volume").toString());
	}
	
	public BigDecimal getVolumeUSD() {
		return new BigDecimal(raw.get("volume_usd").toString());
	}
	
}
