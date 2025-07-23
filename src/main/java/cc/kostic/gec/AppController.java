package cc.kostic.gec;

import cc.kostic.gec.endpoints.deribit.*;
import cc.kostic.gec.generic.DiskCache;
import cc.kostic.gec.instrument.Instrument;
import cc.kostic.gec.instrument.OptionContract;
import cc.kostic.gec.instrument.Ticker;
import cc.kostic.gec.primitives.Currency;
import cc.kostic.gec.primitives.Expiration;
import cc.kostic.gec.primitives.Kind;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;
import java.util.ArrayList;
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
	public TabPane tp_chains;
	@FXML
	public Label tv_timestamp;
	@FXML
	public Label tv_status_line;
	@FXML
	public TabPane tp_test;
	@FXML
	public Button b_gamma;
	
	public final static String CONTSfn = "contracts.oos";
	public final static String EXPSfn = "expirations.oos";
	public final static String TICKSfn = "tickers.oos";

	public static final IntegerProperty kaunt = new SimpleIntegerProperty();
	public static final StringProperty status = new SimpleStringProperty();


	/// ////////////////////////////////////
	/// CHAINS
	/// ////////////////////////////////////
	
	
	public void onGetFromWebClick(ActionEvent actionEvent) {
		GetInstruments gis = new GetInstruments(Currency.ETH, Kind.OPTION);
		List<OptionContract> contracts = gis.getResult(DataSRC.WEB);
		List<Expiration> expirations = gis.getExpirations();
		DiskCache.writeToStorage(contracts, CONTSfn);
		DiskCache.writeToStorage(expirations, EXPSfn);

		System.out.println("Fetching " + contracts.size() + " elements will take " + (contracts.size()/10) + " seconds");
		int kaunt = 0;
		List<Ticker> tickerList = new ArrayList<>();
		for (OptionContract oc : contracts) {
			String n = oc.getInstrument_name();
			GetTicker gt = new GetTicker(n);
			Ticker t = gt.getResult(DataSRC.WEB);
			tickerList.add(t);
			
			kaunt ++;
			if ( (kaunt % 10) == 0) {
				// System.out.println(t);
				System.out.print("." + kaunt);
			}
		}
		
		System.out.println();
		DiskCache.writeToStorage(tickerList, TICKSfn);
		System.out.println("wow");
	}


	public void onGetFromDiskClick(ActionEvent actionEvent) {
		GetInstruments gis = new GetInstruments(Currency.ETH, Kind.OPTION);
		List<OptionContract> contracts = gis.getResult(DataSRC.DISK);
		prikaz(gis.getExpirations(), contracts);
		
		System.out.println("wow");
	}



	private void prikaz(List<Expiration> exps, List<OptionContract> contracts){
		// tv_timestamp.textProperty().bind(GetInstruments.kaunt.asString());
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
			tp_chains.getTabs().add(t);
			// tv_timestamp.setText("todo");
			tv_status_line.setText("exp/instr: " + exps.size() + "/" + contracts.size());
		}

	}
	
	
	
	
	/// ////////////////////////////////////
	/// GAMMA
	/// ////////////////////////////////////
	
	public void onGammaClick(ActionEvent actionEvent) {
		// https://github.com/Matteo-Ferrara/gex-tracker
		// https://www.reddit.com/r/options/comments/16t9pc8/skewadjusted_gex/
		// https://www.cboe.com/insights/posts/volatility-insights-evaluating-the-market-impact-of-spx-0-dte-options/
		// https://perfiliev.com/blog/how-to-calculate-gamma-exposure-and-zero-gamma-level/
		// open_interest * gamma * 100 * k * spot_price * (1% * spot_price)
		// 1) CALLgamma = open_interest * gamma * spot_price * 100 * (0.01 * spot_price)
		// 2) PUTgamma  = open_interest * gamma * spot_price * 100 * (0.01 * spot_price) * (-1)
		// 3) TOTSTRIKEgamma = CALLgamma+PUTgamma
		// 4) EXPIRATIONgamma za sve strajkove u tom expirationu
		//
 		// 5) isto to samo zameni traded_volume umesto open_interest

		GetInstruments gis = new GetInstruments(Currency.ETH, Kind.OPTION);
		List<OptionContract> contracts = gis.getResult(DataSRC.DISK);
		List<Expiration>  expirations = gis.getExpirations();
		// fori contract
		/*
			fori expiration
				fori strike
					CALLg ...
					PUTg ...
					TOTSTRIKEg ...
				}
				EXPIRATIONg ...
			}

			graf: call & put gamma / strike
			graf: tot_strike gamma  / strike
		 */
		
		
		for (OptionContract oc : contracts) {
			BigDecimal strajk = oc.getStrike();
			
		}

		System.out.println("wow");

	}
	
	
	
	
	
	
	
	
	
	/// ////////////////////////////////////
	/// TEST
	/// ////////////////////////////////////
	
	@FXML
	public void onExpirationsClick(ActionEvent actionEvent) {
		// OK !
		// JSONObject
		GetExpirations ge = new GetExpirations(Currency.ETH, Kind.OPTION);
		ge.run();
		List<String> epirations = ge.getExpirationStrings();
		if ( ! tp_test.getTabs().isEmpty() ) {
			tp_test.getTabs().clear();
		}
		for (String s : epirations) {
			Tab t = new Tab();
			t.setText(s);
			tp_test.getTabs().add(t);
			GridPane gp = new GridPane();
			t.setContent(gp);
			gp.add(new Label("Row 0, Col 0"), 0, 0);
			gp.add(new Label("Row 1, Col 1"), 1, 1);
		}
		// tv_stat.setText("ima " + epirations.size());
		
		System.out.println("expirations");
		
	}
	
	@FXML
	public void onInstrumentClick(ActionEvent actionEvent) {
		GetInstrument gi = new GetInstrument("BTC-27MAR26-105000-C");
		gi.run();
		Instrument ii = gi.getResult();
		System.out.println(ii);
		System.out.println("instrument");
	}
	
	@FXML
	public void onInstrumentSClick(ActionEvent actionEvent) {
		GetInstruments gis = new GetInstruments(Currency.ETH, Kind.OPTION);
		List<OptionContract> postojeci_kontrakti = gis.getResult(DataSRC.WEB);
		System.out.println(postojeci_kontrakti);
		System.out.println("instrument-s");
	}
	
	@FXML
	public void onTickerClick(ActionEvent actionEvent) {
		
		GetTicker gt = new GetTicker("BTC-27MAR26-100000-C");
		Ticker ttt = gt.getResult(DataSRC.WEB);
		System.out.println(ttt.getInstrumentName() + " underlying price=" + ttt.getUnderlyingPrice() + ", IV: " + ttt.getMarkIV());
		System.out.println("ticker");
		
	}
	
}

