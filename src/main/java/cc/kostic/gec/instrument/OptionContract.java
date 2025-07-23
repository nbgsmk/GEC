package cc.kostic.gec.instrument;

import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class OptionContract extends Instrument {

	private final String		option_type_str;	// "call",
	private final BigDecimal	strike;				// 100000,
	
	
	// private Ticker ticker;

	public enum OPTION_TYPE {
		CALL,
		PUT
	}

	public OptionContract(Map<String, ?> o) {
		super(o);
		this.option_type_str 	= o.get("option_type").toString();
		this.strike 			= new BigDecimal( o.get("strike").toString() );
	}


	public OPTION_TYPE getOption_type() {
		if(this.option_type_str.equalsIgnoreCase("call")) {
			return OPTION_TYPE.CALL;
		} else {
			return OPTION_TYPE.PUT;
		}
	}

	public BigDecimal getStrike() {
		return strike;
	}
	
}


