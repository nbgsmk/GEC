package cc.kostic.gec.primitives;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.testng.Assert.*;
public class ExpirationTest {

	private final long jul_07_2025_00gmt = 1751846400000L;
	private final long jul_22_2025_00gmt = 1753142400000L;

	@BeforeMethod
	public void setUp() {
	}
	
	@AfterMethod
	public void tearDown() {
	}
	
	@Test
	public void testGetExpirationStr() {
		// ovo je samo proba. ne koristi se inace
		try {
			String exp = "07JUL25";		// podrazumeva 7.jul.2025. 00:00 GMT
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date date = sdf.parse(exp);
			long unixmS = date.getTime();
			System.out.println("Unix Timestamp for " + exp + ": " + unixmS);
			assertEquals(unixmS, jul_07_2025_00gmt, "mora biti 7.jul.2025. 00:00 GMT");
			
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void testGetExpirationInstant() {
	}
	

	@Test
	void getExpirationShortFmt() {
		Expiration e7 = new Expiration(BigDecimal.valueOf(jul_07_2025_00gmt));
		Expiration e11 = new Expiration(BigDecimal.valueOf(jul_22_2025_00gmt));
		assertEquals(e7.getExpirationShortFmt(), "7JUL25", "mora biti 7JUL25 bez vodece nule");
		assertEquals(e11.getExpirationShortFmt(), "22JUL25", "mora biti 22JUL25 sa dve cifre");
	}

	
	@Test
	public void testGetExpiration_timestamp() {
		long mar_27_2026_08hGMT = 1774598400000L;			// Svi Deribit expiration su u 8:00am -> "27MAR26" = Fri Mar 27 2026 08:00 GMT
		Expiration exp27mar26 = new Expiration(BigDecimal.valueOf(mar_27_2026_08hGMT));
		assertEquals(exp27mar26.getExpiration_timestamp(), BigDecimal.valueOf(mar_27_2026_08hGMT), "sam sebe");
		assertEquals(exp27mar26.getExpirationStringISO(), "2026-03-27T08:00:00Z", "iso date");
		assertEquals(exp27mar26.getExpirationShortFmt(), "27MAR26", "deribit kratki format");

		Expiration bezVodeceNule = new Expiration(BigDecimal.valueOf(jul_07_2025_00gmt));				// Sun Mar 08 2026 07:00:00 GMT+0000
		Expiration bezVodeceNulePlus = new Expiration(BigDecimal.valueOf(jul_07_2025_00gmt + 1L));		// plus 1mS ali je u istom danu
		assertEquals(bezVodeceNule.getExpirationShortFmt(), "7JUL25", "deribit kratki format - bez vodece nule");
		assertEquals(bezVodeceNulePlus.getExpirationShortFmt(), "7JUL25", "mora biti u okviru istog datuma");
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
		Expiration a = new Expiration(BigDecimal.valueOf(30L));
		Expiration hi = new Expiration(BigDecimal.valueOf(40L));
		Expiration lo = new Expiration(BigDecimal.valueOf(20L));
		Expiration eq = new Expiration(BigDecimal.valueOf(30L));
		assertTrue(a.compareTo(a)==0, "sam sa sobom");
		assertTrue(a.compareTo(lo)>0, "ja sam veci");
		assertTrue(a.compareTo(hi)<0, "ja sam manji");
		assertTrue(a.compareTo(eq)==0, "isti smo");
		
	}


}