package cc.kostic.gec.endpoints;

import cc.kostic.gec.instrument.Currency;
import cc.kostic.gec.instrument.Kind;

public class GetInstruments {
	
	private final String endpoint = "https://www.deribit.com/api/v2/public/get_instruments?currency=BTC&expired=false&kind=option";
	
	private final BaseURL b;
	private final Currency currency;
	private final Kind kind;
	
	public GetInstruments(Currency currency, Kind kind) {
		this.currency = currency;
		this.kind = kind;
		b = new BaseURL();
	}
	
	public String req(){
		return b.pub() + "/get_instruments?currency=" + currency.getName() + "&expired=false&kind=" + kind.getName();
	}
}
