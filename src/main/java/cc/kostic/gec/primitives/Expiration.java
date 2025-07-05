package cc.kostic.gec.primitives;

import java.time.Instant;

public class Expiration {
	
	private String expirationStr = null;
	private Instant expirationInstant = null;
	
	public Expiration(String expirationStr) {
		this.expirationStr = expirationStr;
	}
	
	public Expiration(Instant expirationInstant) {
		this.expirationInstant = expirationInstant;
	}
	
	public String getExpirationStr() {
		return expirationStr;
	}
	
	public Instant getExpirationInstant() {
		return expirationInstant;
	}
}
