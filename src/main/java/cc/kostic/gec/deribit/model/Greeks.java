package cc.kostic.gec.deribit.model;

import org.json.JSONObject;

public class Greeks {
	
	private final Float	delta;		// "delta":0
	private final Float	gamma;		// "gamma":0
	private final Float	vega;		// "vega":0.00001
	private final Float	theta;		// "theta":-0.00003
	private final Float	rho;		// "rho":0

	
	public Greeks(JSONObject data) {
		this.delta = data.optBigDecimal("delta", null).floatValue();
		this.gamma = data.optBigDecimal("gamma", null).floatValue();
		this.vega = data.optBigDecimal("vega", null).floatValue();
		this.theta = data.optBigDecimal("theta", null).floatValue();
		this.rho = data.optBigDecimal("rho", null).floatValue();
	}
	
	
	public Float getDelta() {
		return delta;
	}
	
	public Float getGamma() {
		return gamma;
	}
	
	public Float getVega() {
		return vega;
	}
	
	public Float getTheta() {
		return theta;
	}
	
	public Float getRho() {
		return rho;
	}
}
