module cc.kostic.gec {
	requires javafx.controls;
	requires javafx.fxml;
	
	requires org.controlsfx.controls;
	requires org.kordamp.ikonli.javafx;
	requires java.net.http;
	requires java.desktop;
	requires org.json;
	requires jdk.jfr;
	
	opens cc.kostic.gec to javafx.fxml;
	exports cc.kostic.gec;
}