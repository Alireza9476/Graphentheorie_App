package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MainFx extends Application {
	
	public static Stage primaryStage;
	public static Scene scene;
	RootGridPane root;
	
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			root = new RootGridPane();
			scene = new Scene(root,600,550);
			
			//primaryStage.setMaximized(true);
			primaryStage.setScene(scene);
			primaryStage.setTitle("AdjazentMatrix");
			MainFx.primaryStage = primaryStage;
			primaryStage.show();
		}
		catch (Exception e) {
			showAlert(e.getMessage());
		}
		
		MainFx.scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.A &&  event.isControlDown()){
				root.eventOpen();
			}	
		});
	}
	
	public static void showAlert(String message) {
		if(message!=null) {
			Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
			alert.setTitle("Achtung Fehler");
			alert.setHeaderText(null);
			alert.showAndWait(); //View dahinter blockieren. Modale Benutzerführung (wäre nach primaryStag.show())
		}
	}
}
