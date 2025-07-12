package cc.kostic.gec.instrument;

import org.json.JSONObject;

import java.math.BigDecimal;

public class Greeks {
	
	private final BigDecimal	delta;		// "delta":0
	private final BigDecimal gamma;		// "gamma":0
	private final BigDecimal	vega;		// "vega":0.00001
	private final BigDecimal	theta;		// "theta":-0.00003
	private final BigDecimal	rho;		// "rho":0

	
	public Greeks(JSONObject data) {
		this.delta = data.optBigDecimal("delta", null);
		this.gamma = data.optBigDecimal("gamma", null);
		this.vega = data.optBigDecimal("vega", null);
		this.theta = data.optBigDecimal("theta", null);
		this.rho = data.optBigDecimal("rho", null);
	}
	
	
	public BigDecimal getDelta() {
		return delta;
	}
	public BigDecimal getGamma() {
		return gamma;
	}
	public BigDecimal getVega() {
		return vega;
	}
	public BigDecimal getTheta() {
		return theta;
	}
	public BigDecimal getRho() {
		return rho;
	}
	
}
