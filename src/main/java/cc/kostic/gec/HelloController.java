package cc.kostic.gec;

import cc.kostic.gec.deribit.model.DeribitJSON;
import cc.kostic.gec.deribit.model.Option_Info;
import cc.kostic.gec.deribit.model.ListedOptionContracts;
import cc.kostic.gec.endpoints.GetInstruments;
import cc.kostic.gec.endpoints.GetTicker;
import cc.kostic.gec.instrument.Currency;
import cc.kostic.gec.instrument.Kind;
import cc.kostic.gec.web.Fetcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import javax.swing.*;
import java.util.*;


public class HelloController {
	@FXML
	private VBox prozorcic;
	@FXML
	private TabPane tabovi;
	@FXML
	private Label welcomeText;
	
	@FXML
	protected void onHelloButtonClick() {
		welcomeText.setText("Welcome to JavaFX Application!");
	}
	
	
	public void onFetchClick(ActionEvent actionEvent) {
		
		GetInstruments go = new GetInstruments(Currency.ETH, Kind.OPTION);
		Fetcher f = new Fetcher(go.req());
		JSONObject webRsp = f.get();
		DeribitJSON dj = new DeribitJSON(webRsp);
		List<JSONObject> postojeci_kontrakti = dj.getGenericResponse();
		ListedOptionContracts ao = new ListedOptionContracts();
		
		for (JSONObject o : postojeci_kontrakti) {
			ao.add(o);
		}

		List<String> epirations = ao.getTimeStampsStr();
		for (String s : epirations) {
			Tab t = new Tab();
			t.setText(s);
			tabovi.getTabs().add(t);
		}
		
		
		
		f = new Fetcher("https://www.deribit.com/api/v2/public/get_book_summary_by_currency?currency=BTC&kind=option");
		webRsp = f.get();
		dj = new DeribitJSON(webRsp);
		List<JSONObject> lista = dj.getGenericResponse();
		List<Option_Info> chain = new ArrayList<>();
		for (JSONObject row : lista) {
			Option_Info oc = new Option_Info(row);
			chain.add(oc);
			oc.getExpiration();
			// System.out.println(oc.toString());
		}
		
		Collections.sort(chain);
		for (Option_Info oc : chain) {
			System.out.println(oc);
		}
		
		
		GetTicker gt = new GetTicker("BTC-11JUL25-104000-C");
		f = new Fetcher(gt.req());
		webRsp = f.get();
		

		System.out.println("da!");
	}
}