package cc.kostic.gec.endpoints;

public class GetTicker {
	
	private final BaseURL b;
	private final String instrument_name;
	
	public GetTicker(String instrument_name) {
		b = new BaseURL();
		this.instrument_name = instrument_name;
	}
	
	public String req(){
		// req = "https://www.deribit.com/api/v2/public/ticker?instrument_name=BTC-11JUL25-104000-C"
		return b.pub() + "/ticker?instrument_name=" + instrument_name;
	}
	
	
}
