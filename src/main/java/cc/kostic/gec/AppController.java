package cc.kostic.gec;

import cc.kostic.gec.endpoints.GetExpirations;
import cc.kostic.gec.endpoints.GetInstrument;
import cc.kostic.gec.endpoints.GetInstruments;
import cc.kostic.gec.primitives.Currency;
import cc.kostic.gec.primitives.Kind;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.json.JSONObject;

import java.util.List;


public class AppController {
	@FXML
	public Button b_expirations;
	@FXML
	public Button b_instrument;
	@FXML
	public Button b_instruments;
	@FXML
	public Button b_ticker;
	@FXML
	public TabPane opt_chains;
	@FXML
	private Label tv_stat;
	
	
	@FXML
	public void getExpirationsClick(ActionEvent actionEvent) {
		// OK !
		// JSONObject
		GetExpirations ge = new GetExpirations(Currency.ETH, Kind.OPTION);
		List<String> epirations = ge.getList();
		for (String s : epirations) {
			Tab t = new Tab();
			t.setText(s);
			opt_chains.getTabs().add(t);
		}
		tv_stat.setText("ima " + epirations.size());
		
		System.out.println("expirations");

		
		
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
		
		

	}
	
	@FXML
	public void getInstrumentClick(ActionEvent actionEvent) {
		// OK !
		// JSONObject
		GetInstrument gi = new GetInstrument("BTC-27MAR26-105000-C");
		JSONObject ii = gi.getResult();
		
		System.out.println(ii);
		System.out.println("instrument");
	}
	
	@FXML
	public void getInstrumentSClick(ActionEvent actionEvent) {
		// NOK !
		// JSONObject
		GetInstruments gis = new GetInstruments(Currency.ETH, Kind.OPTION);
		List<String> postojeci_kontrakti = gis.getList();
		System.out.println(postojeci_kontrakti);
		System.out.println("instrument-s");
		
	}
	
	@FXML
	public void getTickerClick(ActionEvent actionEvent) {
	}
}