package cc.kostic.gec;

import cc.kostic.gec.endpoints.GetExpirations;
import cc.kostic.gec.endpoints.GetInstrument;
import cc.kostic.gec.endpoints.GetInstruments;
import cc.kostic.gec.instrument.OptionContract;
import cc.kostic.gec.primitives.Currency;
import cc.kostic.gec.primitives.Expiration;
import cc.kostic.gec.primitives.Kind;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.json.JSONObject;

import java.util.List;
import java.util.Set;


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
	public void onExpirationsClick(ActionEvent actionEvent) {
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
		// tv_stat.setText("ima " + epirations.size());
		
		System.out.println("expirations");

	}
	
	@FXML
	public void onInstrumentClick(ActionEvent actionEvent) {
		// OK !
		// JSONObject
		GetInstrument gi = new GetInstrument("BTC-27MAR26-105000-C");
		JSONObject ii = gi.getResult();
		// tv_stat.setText(ii.toString());
		
		System.out.println(ii);
		System.out.println("instrument");
	}
	
	@FXML
	public void onInstrumentSClick(ActionEvent actionEvent) {
		// NOK !
		// JSONObject
		GetInstruments gis = new GetInstruments(Currency.ETH, Kind.OPTION);
		List<OptionContract> postojeci_kontrakti = gis.getList(GetInstruments.SRC.WEB);
		System.out.println(postojeci_kontrakti);
		System.out.println("instrument-s");
		
	}
	
	@FXML
	public void onTickerClick(ActionEvent actionEvent) {
	}
	
	public void onGetFromWebClick(ActionEvent actionEvent) {
		
		GetInstruments gis = new GetInstruments(Currency.ETH, Kind.OPTION);
		List<OptionContract> contracts = gis.getList(GetInstruments.SRC.WEB);
		gis.writeToDisk();
		prikaz(gis.getAllExpirations(), contracts);
		System.out.println("wow");
	}

	public void onGetFromDiskClick(ActionEvent actionEvent) {

		GetInstruments gis = new GetInstruments(Currency.ETH, Kind.OPTION);
		List<OptionContract> contracts = gis.getList(GetInstruments.SRC.DISK);
		prikaz(gis.getAllExpirations(), contracts);
		System.out.println("wow");
	}

	private void prikaz(Set<Expiration> exps, List<OptionContract> contracts){

		// for (OptionContract oc : contracts) {
		// 	String s = oc.getInstrument_name();
		// 	Ticker t = new Ticker(s);
		// 	JSONObject gg = t.getResult();
		// 	oc.setGreeks(new Greeks(gg));
		// }

		for (Expiration e : exps) {
			Tab t = new Tab(e.getExpirationShortFmt());
			String expirationName = "-" + t.getText() + "-";
			String ocinfo = "";
			for (OptionContract oc : contracts) {
				if (oc.getInstrument_name().contains(expirationName)) {
					ocinfo += oc.getInstrument_name() + " " + oc.getExpirationString() + " " + oc.getStrike().toPlainString() + " " + oc.getOption_type() + "\n";
				}
			}
			Label l = new Label();
			l.setText(ocinfo);
			ScrollPane scroll = new ScrollPane();
			scroll.setContent(l);
			t.setContent(scroll);
			opt_chains.getTabs().add(t);
		}

	}
}

