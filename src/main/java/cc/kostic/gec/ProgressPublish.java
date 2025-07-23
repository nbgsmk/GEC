package cc.kostic.gec;

import javafx.application.Platform;
import javafx.beans.property.*;

public class ProgressPublish {

	public static final LongProperty var_x = new SimpleLongProperty();
	public static final LongProperty var_y = new SimpleLongProperty();
	public static final StringProperty status = new SimpleStringProperty();

	public static void pubX(long x){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				var_x.set(x);
			}
		});
	}
	public static void pubY(long y){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				var_x.set(y);
			}
		});
	}
	public static void pubMsg(String msg){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				status.set(msg);
			}
		});
	}
	public static void pub(long x, long y, String msg){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				var_x.set(x);
				var_y.set(y);
				status.set(msg);
			}
		});
	}

}
