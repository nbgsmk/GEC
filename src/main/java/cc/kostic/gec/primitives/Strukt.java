package cc.kostic.gec.primitives;

import cc.kostic.gec.instrument.OptionContract;

import java.math.BigDecimal;

public class Strukt {


	public String instrumentName;
	public  BigDecimal strajk = new BigDecimal(0);
	public String expString = "";
	public BigDecimal callGamma = new BigDecimal(0);
	public BigDecimal putGamma = new BigDecimal(0);

	@Override
	public String toString() {
		return instrumentName + " - " + "cg:" + callGamma + "\t pg:" + putGamma;
	}
}
