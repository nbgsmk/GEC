package cc.kostic.gec.primitives;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Expiration {
	
	private String expirationStr = null;
	private Instant expirationInstant = null;
	
	public Expiration(String expirationStr) {
		this.expirationStr = expirationStr;
		// if (expirationStr.length() == 6) {
		// 	expirationStr = "0" + expirationStr;
		// }
		// DateTimeFormatter fmt = DateTimeFormatter.ofPattern("ddMMMyy");
		// LocalDateTime ld = LocalDateTime.parse(expirationStr, fmt);
		// Instant instant = ld.toInstant(ZoneOffset.UTC);
		this.expirationInstant = Instant.parse(expirationStr);
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
