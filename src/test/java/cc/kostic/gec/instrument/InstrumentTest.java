package cc.kostic.gec.instrument;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class InstrumentTest {
	
	@Test
	public void testGetBlock_trade_tick_size() {
		Map<String, ?> m2 = Map.of("a", 200L);
		Map<String, ?> m3 = Map.of("a", 325037088L);
		
		BigDecimal bdc = (BigDecimal) m3.get("a");
		BigDecimal bdL = BigDecimal.valueOf( (Long) m3.get("a") );
		BigDecimal bdD = BigDecimal.valueOf( (Double) m3.get("a") );
		
		System.out.println("da");
	}
}