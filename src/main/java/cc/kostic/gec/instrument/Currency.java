package cc.kostic.gec.instrument;

public enum Currency {
	BTC("BTC"),
	ETH("ETH"),
	USDT("USDT"),
	USDC("USDC");
	
	private final String name;

	
	Currency(String name) {
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
