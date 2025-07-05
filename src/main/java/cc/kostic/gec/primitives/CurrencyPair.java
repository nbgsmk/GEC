package cc.kostic.gec.primitives;

public enum CurrencyPair {
	
	BTC_EUR("btc_eurr"),
	BTC_USD("btc_usd"),
	BTC_USDC("btc_usdc"),
	BTC_USDE("btc_usde"),
	BTC_USDT("btc_usdt"),
	BTC_USYC("btc_usyc"),
	BTC_DVOL_USDC("btc_dvol_usdc"),
	
	ETH_BTC("eth_btc" ),
	ETH_EURR("eth_eurr"),
	ETH_USD("eth_usd" ),
	ETH_USDC("eth_usdc"),
	ETH_USDE("eth_usde"),
	ETH_USDT("eth_usdt"),
	ETH_USYC("eth_usyc"),
	ETH_DVOL_USDC("eth_dvol_usdc");
	
	
	public final String btc_eur = "btc_eurr";
	public final String btc_usd = "btc_usd";
	public final String btc_usdc = "btc_usdc";
	public final String btc_usde = "btc_usde";
	public final String btc_usdt = "btc_usdt";
	public final String btc_usyc = "btc_usyc";
	public final String btc_dvol_usdc = "btc_dvol_usdc";
	
	public final String eth_btc = "eth_btc";
	public final String eth_eur = "eth_eurr";
	public final String eth_usd = "eth_usd";
	public final String eth_usdc = "eth_usdc";
	public final String eth_usde = "eth_usde";
	public final String eth_usdt = "eth_usdt";
	public final String eth_usyc = "eth_usyc";
	public final String eth_dvol_usdc = "eth_dvol_usdc";
	
	private final String name;
	
	CurrencyPair(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
}
