package cc.kostic.gec.endpoints.deribit;

import cc.kostic.gec.instrument.Instrument;
import cc.kostic.gec.instrument.OptionContract;
import cc.kostic.gec.instrument.Ticker;
import cc.kostic.gec.primitives.Expiration;
import cc.kostic.gec.web.Fetcher;
import javafx.beans.property.SimpleIntegerProperty;
import org.json.JSONObject;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetTicker {
	
	private final String reqUrl;
	private DeribitRsp dr;
	
	
	// constructor
	public GetTicker(String instrument_name) {
		this.reqUrl = buildReq(new BaseURL(), instrument_name);
	}
	
	private String buildReq(BaseURL b, String instrument_name){
		// return "https://www.deribit.com/api/v2/public/ticker?instrument_name=BTC-26JUN26-100000-C";
		return b.pub() + "/ticker?instrument_name=" + instrument_name;
	}
	
	public void run(){
		Fetcher f = new Fetcher(this.reqUrl);
		JSONObject jsRsp = f.fetch();
		this.dr = new DeribitRsp(jsRsp);
	}
	
	public String getJsonRpcVer(){
		return dr.getJsonRpcVersion();
	}
	public Ticker getResult(DataSRC dataSource){
		Ticker t = null;
		switch (dataSource){
			case WEB -> {
				try {
					dr = getFromWeb();
					t = new Ticker(dr.getResultObject(HashMap.class));
					Thread.sleep(50);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

			case DISK -> {
				dr = getFromDisk();
				t = new Ticker(dr.getResultObject(HashMap.class));
			}
		}
		return t;
	}
	
	
	
	private DeribitRsp getFromWeb(){
		Fetcher f = new Fetcher(this.reqUrl);
		JSONObject jsRsp = f.fetch();
		return new DeribitRsp(jsRsp);
	}
	
	
	private DeribitRsp getFromDisk() {
		DeribitRsp rezult = null;
		try (FileInputStream fis = new FileInputStream("tickers_oos.txt");
			 BufferedInputStream bis = new BufferedInputStream(fis);
			 ObjectInputStream ois = new ObjectInputStream(bis);){
			Object infile = ois.readObject();
			if (infile instanceof DeribitRsp) {
				rezult = (DeribitRsp) infile;
			}
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
		return rezult;
	}
	
	
	public void writeToDisk() {
		try (FileOutputStream fos = new FileOutputStream("tickers_oos.txt");
			 BufferedOutputStream bos = new BufferedOutputStream(fos);
			 ObjectOutputStream oos = new ObjectOutputStream(bos);) {
			oos.writeObject(this.dr);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	
	
	public Object getErrorObject(){
		return dr.getErrorObject();
	}
	public boolean isTestnet(){
		return dr.isTestnet();
	}
	public Instant getMicroSecIn(){
		return dr.getMicroSecIn();
	}
	public Instant getMicroSecOut(){
		return dr.getMicroSecOut();
	}
	public Long getMicroSecDiff(){
		return dr.getMicroSecDiff();
	}

	
	

}
