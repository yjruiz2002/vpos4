package application.mediospago;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class mediospagoController {
	@FXML
	private Button btnPagoTarjeta;
	@FXML
	private Button btnPagoC2P;
	@FXML
	private Button btnPagoP2C;

	// Event Listener on Button[#btnPagoTarjeta].onAction
	// Redirige al formulario de tarjeta
	@FXML
	public void redirigirPagoTarjeta(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/mediospago/tarjeta/CompraTarjeta.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/resources/css/application.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm());
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.close();
			stage.setResizable(false);			
            stage.setTitle("Tarjeta");
            stage.setScene(scene);
            stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Event Listener on Button[#btnPagoC2P].onAction
	// Redirige al formulario de C2P
	@FXML
	public void redirigirPagoC2P(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/mediospago/C2P/CompraPagoC2P.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/resources/css/application.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm());
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.close();
			stage.setResizable(false);			
            stage.setTitle("C2P");
            stage.setScene(scene);
            stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Event Listener on Button[#btnPagoP2C].onAction
	// Redirige al formulario de P2C
	@FXML
	public void redirigirPagoP2C(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/mediospago/P2C/CompraPagoP2C.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/resources/css/application.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm());
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.close();
			stage.setResizable(false);			
            stage.setTitle("P2C");
            stage.setScene(scene);
            stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
