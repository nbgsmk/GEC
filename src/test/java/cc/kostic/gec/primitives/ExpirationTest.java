package cc.kostic.gec.primitives;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.testng.Assert.*;
public class ExpirationTest {
	
	@BeforeMethod
	public void setUp() {
	}
	
	@AfterMethod
	public void tearDown() {
	}
	
	@Test
	public void testGetExpirationStr() {
		Date date = null;
		try {
			String exp = "07JUL25";
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy");
			date = sdf.parse(exp);
			long unixmS = date.getTime();
			System.out.println("Unix Timestamp for " + exp + ": " + unixmS);
			
			
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void testGetExpirationInstant() {
	}
	
	@Test
	public void testIks() {
		Instant instant = Instant.ofEpochMilli(1752103030606L);
		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");		// ISO date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dMMMyy");					// 9JUL20 bez vodece nule
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
		String fs1 = localDateTime.format(formatter).toUpperCase();	// deribit voli uppercase
		System.out.println(fs1);
		
	}
	
	@Test
	public void testGetExpiration_timestamp() {
		Expiration exp27mar26 = new Expiration(1774598400000L);		// Deribit expiration "27MAR26" = Fri Mar 27 2026 08:00 GMT
		assertEquals(exp27mar26.getExpiration_timestamp(), 1774598400000L, "sam sebe");
		assertEquals(exp27mar26.getExpirationStringISO(), "2026-03-27T08:00:00Z", "iso date");
		assertEquals(exp27mar26.getExpirationShortFmt(), "27MAR26", "deribit kratki format");

		Expiration bezVodeceNule = new Expiration(1772953200000L);		// Sun Mar 08 2026 07:00:00 GMT+0000
		Expiration bezVodeceNulePlus = new Expiration(1772953200001L);	// plus 1mS ali je u istom danu
		assertEquals(bezVodeceNule.getExpirationShortFmt(), "8MAR26", "deribit kratki format - bez vodece nule");
		assertEquals(bezVodeceNulePlus.getExpirationShortFmt(), "8MAR26", "mora biti u okviru istog datuma");
	}
	
	@Test
	public void testGetExpirationStringISO() {
	}
	
	@Test
	public void testTestGetExpirationInstant() {
	}
	
	@Test
	public void testGetExpirationShortFmt() {
	}
	
	@Test
	public void testCompareTo() {
		Expiration a = new Expiration(30);
		Expiration hi = new Expiration(40);
		Expiration lo = new Expiration(20);
		Expiration eq = new Expiration(30);
		assertTrue(a.compareTo(a)==0, "sam sa sobom");
		assertTrue(a.compareTo(lo)>0, "ja sam veci");
		assertTrue(a.compareTo(hi)<0, "ja sam manji");
		assertTrue(a.compareTo(eq)==0, "isti smo");
		
	}
}