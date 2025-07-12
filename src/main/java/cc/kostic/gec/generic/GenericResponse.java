package cc.kostic.gec.generic;

import org.json.JSONObject;

import java.time.Instant;

public class GenericResponse {
	public interface fuj{

	}

	private Long id;
	private String jsonRpcVersion;
	private JSONObject rezultat;
	private Instant epochMillisIn;
	private Instant epochMillisOut;
	private Long millisDiff;
	private JSONObject errorObject;

}
