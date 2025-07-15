package cc.kostic.gec.instrument;

import org.json.JSONObject;

import java.math.BigDecimal;

public class FutureContract extends Instrument {

	private final String		future_type;					// "linear",
	private final BigDecimal 	max_leverage;					// 50,
	private final BigDecimal 	max_liquidation_commission;		// 0.0075,


	public FutureContract(JSONObject o) {
		super(null);
		this.future_type   		 	        = o.getString("future_type");
		this.max_leverage    		 		= o.getBigDecimal("max_leverage");
		this.max_liquidation_commission    	= o.getBigDecimal("max_liquidation_commission");
	}


	public String getFuture_type() {
		return future_type;
	}
	public BigDecimal getMax_leverage() {
		return max_leverage;
	}
	public BigDecimal getMax_liquidation_commission() {
		return max_liquidation_commission;
	}


}


