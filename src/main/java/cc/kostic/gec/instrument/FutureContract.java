package cc.kostic.gec.instrument;

import org.json.JSONObject;

public class FutureContract extends Instrument {

	private final String    future_type;	                // "linear",
	private final Double    max_leverage;				    // 50,
	private final Double    max_liquidation_commission;		// 0.0075,


	public FutureContract(JSONObject o) {
		super(o);
		this.future_type   		 	        = o.getString("future_type");
		this.max_leverage    		 		= o.getDouble("max_leverage");
		this.max_liquidation_commission    	= o.getDouble("max_liquidation_commission");
	}


	public String getFuture_type() {
		return future_type;
	}
	public Double getMax_leverage() {
		return max_leverage;
	}
	public Double getMax_liquidation_commission() {
		return max_liquidation_commission;
	}


}


