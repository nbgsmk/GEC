package cc.kostic.gec;

import cc.kostic.gec.deribit.model.DeribitJSON;
import cc.kostic.gec.deribit.model.OptContract;
import cc.kostic.gec.web.Fetcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;


public class HelloController {
	@FXML
	private Label welcomeText;
	
	@FXML
	protected void onHelloButtonClick() {
		welcomeText.setText("Welcome to JavaFX Application!");
	}
	
	
	public void onFetchClick(ActionEvent actionEvent) {
		Fetcher f = new Fetcher("https://www.deribit.com/api/v2/public/get_book_summary_by_currency?currency=BTC&kind=option");
		JSONObject webRsp = f.get();
		DeribitJSON dj = new DeribitJSON(webRsp);
		List<JSONObject> lista = dj.getGenericResponse();
		List<OptContract> chain = new ArrayList<>();
		for (JSONObject row : lista) {
			OptContract oc = new OptContract(row);
			chain.add(oc);
			oc.getExpiration();
			// System.out.println(oc.toString());
		}
		
		Collections.sort(chain);
		for (OptContract oc : chain) {
			System.out.println(oc);
		}
		
		System.out.println("da!");
	}
}