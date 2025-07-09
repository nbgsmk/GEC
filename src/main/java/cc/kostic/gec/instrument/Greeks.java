package cc.kostic.gec.instrument;

import org.json.JSONObject;

public class Greeks {
	
	private final Double	delta;		// "delta":0
	private final Double	gamma;		// "gamma":0
	private final Double	vega;		// "vega":0.00001
	private final Double	theta;		// "theta":-0.00003
	private final Double	rho;		// "rho":0

	
	public Greeks(JSONObject data) {
		this.delta = data.optBigDecimal("delta", null).doubleValue();
		this.gamma = data.optBigDecimal("gamma", null).doubleValue();
		this.vega = data.optBigDecimal("vega", null).doubleValue();
		this.theta = data.optBigDecimal("theta", null).doubleValue();
		this.rho = data.optBigDecimal("rho", null).doubleValue();
	}
	
	
	public Double getDelta() {
		return delta;
	}
	public Double getGamma() {
		return gamma;
	}
	public Double getVega() {
		return vega;
	}
	public Double getTheta() {
		return theta;
	}
	public Double getRho() {
		return rho;
	}
	
}
