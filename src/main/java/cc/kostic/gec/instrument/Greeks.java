package cc.kostic.gec.instrument;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Map;

public class Greeks {
	
	/*
	GREEKS je deo odgoovora za endpoint /ticker?
	
		"greeks": {
			"delta": 0.78899,
			"gamma": 0.00001,
			"vega": 301.89834,
			"theta": -26.48201,
			"rho": 462.09342
		},
	
	*/
	
	private final Map<String, ?> raw;                             // mozda ce da zatreba
	
	
	public Greeks(Map<String, ?> o) {
			this.raw = o;
		}
	
	
	public BigDecimal getDelta() {
		return new BigDecimal(raw.get("delta").toString());
	}
	public BigDecimal getGamma() {
		return new BigDecimal(raw.get("gamma").toString());
	}
	public BigDecimal getVega() {
		return new BigDecimal(raw.get("vega").toString());
	}
	public BigDecimal getTheta() {
		return new BigDecimal(raw.get("theta").toString());
	}
	public BigDecimal getRho() {
		return new BigDecimal(raw.get("rho").toString());
	}
	
}
