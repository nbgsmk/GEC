package cc.kostic.gec.primitives;

public enum Kind {
	FUTURE("future"),
	FUTURE_COMBO("future_combo"),
	OPTION("option"),
	OPTION_COMBO("option_combo"),
	UNKNOWN("unknown_instrument_kind")
	;
	
	private final String name;
	
	Kind(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
