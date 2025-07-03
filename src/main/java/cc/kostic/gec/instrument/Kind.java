package cc.kostic.gec.instrument;

public enum Kind {
	FUTURE("future"),
	FUTURE_COMBO("future_combo"),
	OPTION("option"),
	OPTION_COMBO("option_combo");
	
	private final String name;
	
	Kind(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
