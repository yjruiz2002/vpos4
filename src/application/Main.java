package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("VPOS.fxml")); //Se carga la vista principal
			Scene scene = new Scene(root); 
			//Se cargan los recursos que usara la nueva vista
			scene.getStylesheets().add(getClass().getResource("/resources/css/application.css").toExternalForm()); 
			scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm()); 
			primaryStage.setTitle("Megasoft"); // Título de la ventana
			primaryStage.setResizable(false); // No permitir la modificación del tamaño de la ventana
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Método principal
	public static void main(String[] args) {
		launch(args);
	}
}
