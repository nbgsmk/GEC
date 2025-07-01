package cc.kostic.gec.web;

import org.json.JSONObject;
import org.json.JSONString;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Fetcher {
	
	private String url = "";
	
	public Fetcher(String url) {
		this.url = url;
	}
	
	public JSONObject get(){
		JSONObject response = new JSONObject();
		try (HttpClient client = HttpClient.newHttpClient()) {
			HttpRequest req = HttpRequest.newBuilder(URI.create(url)).build();
			String s = client.send(req, HttpResponse.BodyHandlers.ofString()).body();
			response = new JSONObject(s);
		} catch (IOException | InterruptedException e) {
			// throw new RuntimeException(e);
			// vracam prazan objekat??
		}
		return response;
	}
	
}