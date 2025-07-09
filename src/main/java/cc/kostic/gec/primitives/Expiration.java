package cc.kostic.gec.primitives;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Expiration implements Comparable<Expiration>{
	
	private final long expiration_timestamp;
	private String expirationStringISO = "";
	private final Instant expirationInstant;
	
	public Expiration(long expiration_timestamp) {
		this.expiration_timestamp = expiration_timestamp;
		this.expirationInstant = Instant.ofEpochMilli(this.expiration_timestamp);
		this.expirationStringISO = expirationInstant.toString();
	}
	
	public long getExpiration_timestamp() {
		return expiration_timestamp;
	}
	
	public String getExpirationStringISO() {
		return expirationStringISO;
	}
	
	public Instant getExpirationInstant() {
		return expirationInstant;
	}
	
	public String getExpirationShortFmt(){
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dMMMyy");		// 9JUL20 bez vodece nule
		LocalDateTime localDateTime = LocalDateTime.ofInstant(this.expirationInstant, ZoneOffset.UTC);
		String s = localDateTime.format(formatter).toUpperCase();	// deribit voli uppercase
		return s;
	}
	
	public long getDTEmillis(){
		return Instant.now().toEpochMilli() - this.expiration_timestamp;
	}
	public long getDTEdays(){
		long days = ChronoUnit.DAYS.between(Instant.now(), getExpirationInstant());
		return days;
	}
	public long getDTEhours(){
		long hrs = ChronoUnit.HOURS.between(Instant.now(), getExpirationInstant());
		return hrs;
	}

	@Override
	public int compareTo(Expiration o) {
		// if (this.expiration_timestamp > o.expiration_timestamp) { return 1; }
		// if (this.expiration_timestamp < o.expiration_timestamp) { return -1; }
		// return 0;
		return Long.compare(this.expiration_timestamp, o.expiration_timestamp);			// normalni sort
		// return Long.compare(o.expiration_timestamp, this.expiration_timestamp);		// najdalji datumi prvo
	}
}
