package cc.kostic.gec;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("app.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("GEXCalc");
		stage.setScene(scene);
		stage.setX(1605);
		stage.setY(5);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}