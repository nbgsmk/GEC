package cc.kostic.gec.instrument;

import org.json.JSONObject;

public class OptionContract extends Instrument {

	private final String    option_type;	                // "call",
	private final Double    strike;				            // 100000,
	
	private Greeks greeks;

	public enum OPTION_TYPE {
		CALL,
		PUT
	}

	public OptionContract(JSONObject o) {
		super(o);
		this.option_type   		 	        = o.getString("option_type");
		this.strike    		 		        = o.getDouble("strike");
	}


	public OPTION_TYPE getOption_type() {
		if(this.option_type.equalsIgnoreCase("call")) {
			return OPTION_TYPE.CALL;
		} else {
			return OPTION_TYPE.PUT;
		}
	}

	public Double getStrike() {
		return strike;
	}
	
	public Greeks getGreeks() {
		return greeks;
	}
	
	public void setGreeks(Greeks greeks) {
		this.greeks = greeks;
	}
}


