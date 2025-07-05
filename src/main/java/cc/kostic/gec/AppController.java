package cc.kostic.gec;

import cc.kostic.gec.deribit.model.DeribitJSONrsp;
import cc.kostic.gec.deribit.model.Option_Info;
import cc.kostic.gec.deribit.model.ListedOptionContracts;
import cc.kostic.gec.endpoints.GetExpirations;
import cc.kostic.gec.endpoints.GetInstruments;
import cc.kostic.gec.endpoints.GetTicker;
import cc.kostic.gec.primitives.Currency;
import cc.kostic.gec.primitives.Kind;
import cc.kostic.gec.web.Fetcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.json.JSONObject;

import java.util.*;


public class AppController {
	@FXML
	public Button b_refresh;
	@FXML
	public TabPane opt_chains;
	@FXML
	private Label tv_stat;
	
	
	@FXML
	protected void onHelloButtonClick() {
		tv_stat.setText("Welcome to JavaFX Application!");
	}
	
	
	public void onFetchClick(ActionEvent actionEvent) {
		

	}
	
	public void onRefreshClick(ActionEvent actionEvent) {
		GetExpirations ge = new GetExpirations(Currency.ETH, Kind.OPTION);
		List<String> epirations = ge.getList();
		for (String s : epirations) {
			Tab t = new Tab();
			t.setText(s);
			opt_chains.getTabs().add(t);
		}
		tv_stat.setText("ima " + epirations.size());
		
		// GetInstruments gi = new GetInstruments(Currency.ETH, Kind.OPTION);
		// List<JSONObject> postojeci_kontrakti = gi.getResult();
		
		
		
		
		// f = new Fetcher("https://www.deribit.com/api/v2/public/get_book_summary_by_currency?currency=BTC&kind=option");
		// webRsp = f.fetch();
		// dj = new DeribitJSONrsp(webRsp);
		// List<JSONObject> lista = dj.getResultArray();
		// List<Option_Info> chain = new ArrayList<>();
		// for (JSONObject row : lista) {
		// 	Option_Info oc = new Option_Info(row);
		// 	chain.add(oc);
		// 	oc.getExpiration();
		// 	System.out.println(oc.toString());
		// }
		
		// Collections.sort(chain);
		// for (Option_Info oc : chain) {
		// 	System.out.println(oc);
		// }
		
		
		// GetTicker gt = new GetTicker("BTC-11JUL25-104000-C");
		// f = new Fetcher(gt.req());
		// webRsp = f.fetch();
		
		
		System.out.println("da!");
	}
}