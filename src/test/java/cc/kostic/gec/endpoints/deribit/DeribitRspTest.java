package cc.kostic.gec.endpoints.deribit;

import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class DeribitRspTest {
	
	@BeforeMethod
	public void setUp() {
	}
	
	@AfterMethod
	public void tearDown() {
	}
	
	@Test
	public void testGetResultObject() {
		List<String> l = new ArrayList<>();
		ArrayList<String> al = new ArrayList<>();
		Map m = new HashMap<>();
		Map<String, Object> mso = new HashMap<>();
		
		assertTrue(gro(l, List.class) instanceof List, "nije lista");
		assertTrue(gro(l, List.class) instanceof List<?>, "nije lista <?>");
		assertTrue(gro(al, ArrayList.class) instanceof ArrayList, "nije array list");
		assertTrue(gro(m, Map.class) instanceof Map, "nije generic Map");
		assertTrue(gro(mso, Map.class) instanceof HashMap, "nije Map");
		assertTrue(gro(mso, HashMap.class) instanceof HashMap<?,?>, "nije HashMap");
		// assertTrue(gro(mso, List.class) instanceof List<?>, "e ovo mora da failuje");
		
	}
	
	
	private <T> T gro(Object o, Class<T> T) {
		return switch (o) {
			case null -> null;
			case Map<?, ?> map -> T.cast(o);
			case ArrayList<?> objects -> T.cast(o);
			case List<?> objects -> T.cast(o);
			default -> T.cast(o);
		};
	}
}