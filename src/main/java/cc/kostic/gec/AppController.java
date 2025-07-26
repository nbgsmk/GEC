package cc.kostic.gec;

import cc.kostic.gec.endpoints.deribit.*;
import cc.kostic.gec.generic.DiskCache;
import cc.kostic.gec.instrument.Greeks;
import cc.kostic.gec.instrument.Instrument;
import cc.kostic.gec.instrument.OptionContract;
import cc.kostic.gec.instrument.Ticker;
import cc.kostic.gec.primitives.Currency;
import cc.kostic.gec.primitives.Expiration;
import cc.kostic.gec.primitives.Kind;
import cc.kostic.gec.primitives.Strukt;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Callable;


public class AppController implements Initializable {

	public Tab tab_chains;
	public VBox vbox_chains;

	public Tab tab_greeks;
	public VBox vbox_greeks;
	public ScrollPane scroll_charts;

	public Tab tab_test;
	public VBox vbox_test;


	@FXML public TabPane tabPane_chainsByExpiration;

	@FXML
	public Button b_expirations;
	@FXML
	public Button b_instrument;
	@FXML
	public Button b_instruments;
	@FXML
	public Button b_ticker;
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

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

	}


	/// ////////////////////////////////////
	/// CHAINS
	/// ////////////////////////////////////
	
	
	public void onGetFromWebClick(ActionEvent actionEvent) {
		GetInstruments gis = new GetInstruments(Currency.ETH, Kind.OPTION);
		List<OptionContract> contracts = gis.getResult(DataSRC.WEB);
		List<Expiration> expirations = gis.getExpirations();
		DiskCache.writeToStorage(contracts, CONTSfn);
		DiskCache.writeToStorage(expirations, EXPSfn);

		prikaz(expirations, contracts);

		System.out.println("Fetching " + contracts.size() + " elements will take " + (contracts.size()/10) + " seconds");
		int i = 0;
		List<Ticker> tickerList = new ArrayList<>();
		for (OptionContract oc : contracts) {
			String n = oc.getInstrument_name();
			GetTicker gt = new GetTicker(n);
			Ticker t = gt.getResult(DataSRC.WEB);
			tickerList.add(t);
			
			i ++;
			if ( ( (i % 10) == 0) || (i == contracts.size()) ){
				System.out.print("." + i);
			}
		}
		
		System.out.println();
		DiskCache.writeToStorage(tickerList, TICKSfn);
		System.out.println("wow");
	}


	public void onGetFromDiskClick(ActionEvent actionEvent) {
		List<OptionContract> contracts = (List<OptionContract>) DiskCache.getFromStorage(CONTSfn);
		List<Expiration> expirations = (List<Expiration>) DiskCache.getFromStorage(EXPSfn);
		List<Ticker> tickers = (List<Ticker>) DiskCache.getFromStorage(TICKSfn);
		prikaz(expirations, contracts);
		System.out.println("wow");
	}



	private void prikaz(List<Expiration> exps, List<OptionContract> contracts){
		tv_status_line.setText(exps.size() + " expirations / " + contracts.size() + " contracts");
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
			tabPane_chainsByExpiration.getTabs().add(t);
		}

	}
	
	
	
	
	/// ////////////////////////////////////
	/// GREEKS
	/// ////////////////////////////////////
	private Map<String, List<Strukt>> gammasetVsExpirations;

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

		List<OptionContract> contracts = (List<OptionContract>) DiskCache.getFromStorage(CONTSfn);
		List<Expiration> expirations = (List<Expiration>) DiskCache.getFromStorage(EXPSfn);
		List<Ticker> tickers = (List<Ticker>) DiskCache.getFromStorage(TICKSfn);

		// Map<String, OptionContract> moc = new LinkedHashMap<>();	// potrebno zbog strike
		// for (OptionContract oc : contracts) {
		// 	moc.put(oc.getInstrument_name(), oc);
		// }

		Map<String, Ticker> mot = new LinkedHashMap<>();			// potrebno zbog underlying price i greeks
		for (Ticker t : tickers) {
			mot.put(t.getInstrumentName(), t);
		}

		gammasetVsExpirations = new LinkedHashMap<>();
		for (Expiration e : expirations){
			List<Strukt> game = new ArrayList<>();
			for (OptionContract oc : contracts) {
				String ocExp = oc.getExpirationString();
				if ( ! e.getExpirationShortFmt().equalsIgnoreCase(ocExp)) {
					continue;
				}
				Ticker t = mot.get(oc.getInstrument_name());
				Strukt strukt = new Strukt();

				BigDecimal gg = t.getGreeks().getGamma(); // + - * / TODO i tako dalje
				strukt.instrumentName = oc.getInstrument_name();
				strukt.strajk = oc.getStrike();
				strukt.expString = oc.getExpirationString();
				if (oc.getOption_type() == OptionContract.OPTION_TYPE.CALL) { strukt.callGamma = strukt.callGamma.add(gg); }
				if (oc.getOption_type() == OptionContract.OPTION_TYPE.PUT) { strukt.putGamma = strukt.putGamma.add(gg); }

				game.add(strukt);
			}
			gammasetVsExpirations.put(e.getExpirationShortFmt(), game);
			// System.out.println("game su");
			// System.out.println("=======");
			// System.out.println(game);
		}



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


		System.out.println("wow");


	}



	public void onCharticClick(ActionEvent actionEvent) {
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("strajkovi");

		//Defining the y axis
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("ɣ Г ex");

		BarChart chart = new BarChart(xAxis, yAxis);
		XYChart.Series tg = new XYChart.Series();
		tg.setName("tot ɣ");
		// List<Strukt> jedan = gammasetVsExpirations.get("8AUG25");
		tg.getData().add("2");
		tg.getData().add("7");
		tg.getData().add("3");
		// for (Strukt strukt : jedan){
		// 	tg.getData().add(strukt.strajk.longValue());
		// }
		chart.getData().add(tg);

		vbox_greeks.getChildren().add(chart);

		// scroll_charts.setContent(vbox_greeks);
		// sviChartovi.getChildren().add(chart);
		// scroll_charts.setContent(sviChartovi);

		// Scene scene = new Scene(vbox, 400, 200);

		// primaryStage.setScene(scene);
		// primaryStage.setHeight(300);
		// primaryStage.setWidth(1200);
		//
		// primaryStage.show();

		// xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Speed", "User rating", "Milage", "Safety")));
		//
		// //Creating the Bar chart
		// BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
		// barChart.setTitle("Comparison between various cars");
		//
		// //Prepare XYChart.Series objects by setting data
		// XYChart.Series<String, Number> series1 = new XYChart.Series<>();
		// series1.setName("Fiat");
		// series1.getData().add(new XYChart.Data<>("Speed", 1.0));
		// series1.getData().add(new XYChart.Data<>("User rating", 3.0));
		// series1.getData().add(new XYChart.Data<>("Milage", 5.0));
		// series1.getData().add(new XYChart.Data<>("Safety", 5.0));
		//
		// XYChart.Series<String, Number> series2 = new XYChart.Series<>();
		// series2.setName("Audi");
		// series2.getData().add(new XYChart.Data<>("Speed", 5.0));
		// series2.getData().add(new XYChart.Data<>("User rating", 6.0));
		//
		// series2.getData().add(new XYChart.Data<>("Milage", 10.0));
		// series2.getData().add(new XYChart.Data<>("Safety", 4.0));
		//
		// XYChart.Series<String, Number> series3 = new XYChart.Series<>();
		// series3.setName("Ford");
		// series3.getData().add(new XYChart.Data<>("Speed", 4.0));
		// series3.getData().add(new XYChart.Data<>("User rating", 2.0));
		// series3.getData().add(new XYChart.Data<>("Milage", 3.0));
		// series3.getData().add(new XYChart.Data<>("Safety", 6.0));
		//
		// barChart.getData().addAll(series1, series2, series3);
		//
		// Group root = new Group(barChart);
		// Scene scene = new Scene(root ,600, 300);
		// primaryStage.setTitle("Sample Application");
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

