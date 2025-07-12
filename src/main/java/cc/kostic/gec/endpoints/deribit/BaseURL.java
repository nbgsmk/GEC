package cc.kostic.gec.endpoints.deribit;

import java.util.StringJoiner;

public class BaseURL {
	
	private static final String url = "https://www.deribit.com/api";
	private static final String ver = "v2";
	private static final String pub = "public";
	private static final String priv = "private";
	
	public BaseURL() {
	}
	
	public String pub(){
		// https://www.deribit.com/api/v2/public/
		return sj(new String[]{url, ver, pub});
	}
	
	public String priv(){
		return sj(new String[]{url, ver, priv});
	}
	
	private String sj(String[] elementi){
		StringJoiner sj = new StringJoiner("/");
		for (String s : elementi) {
			sj.add(s);
		}
		return sj.toString();
	}
}
