package cc.kostic.gec;

import cc.kostic.gec.endpoints.GetExpirations;
import cc.kostic.gec.endpoints.GetInstrument;
import cc.kostic.gec.endpoints.GetInstruments;
import cc.kostic.gec.instrument.OptionContract;
import cc.kostic.gec.primitives.Currency;
import cc.kostic.gec.primitives.Kind;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.spreadsheet.Grid;
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
			GridPane gp = new GridPane();
			gp.setGridLinesVisible(true);		// STOPSHIP
			t.setContent(gp);
			gp.add(new Label("Row 0, Col 0"), 0, 0);
			gp.add(new Label("Row 1, Col 1"), 1, 1);
		}
		tv_stat.setText("ima " + epirations.size());
		
		System.out.println("expirations");

	}
	
	@FXML
	public void getInstrumentClick(ActionEvent actionEvent) {
		// OK !
		// JSONObject
		GetInstrument gi = new GetInstrument("BTC-27MAR26-105000-C");
		JSONObject ii = gi.getResult();
		tv_stat.setText(ii.toString());
		
		System.out.println(ii);
		System.out.println("instrument");
	}
	
	@FXML
	public void getInstrumentSClick(ActionEvent actionEvent) {
		// NOK !
		// JSONObject
		GetInstruments gis = new GetInstruments(Currency.ETH, Kind.OPTION);
		List<OptionContract> postojeci_kontrakti = gis.getList();
		System.out.println(postojeci_kontrakti);
		System.out.println("instrument-s");
		
	}
	
	@FXML
	public void getTickerClick(ActionEvent actionEvent) {
	}
}