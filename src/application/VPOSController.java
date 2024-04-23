package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class VPOSController {
	@FXML
	private Button btnCompra;

	// Event Listener on Button[#btnCompra].onAction 
	// Evento que redirige a la seccion de opciones de compra al seleccionar la opcion compra
	@FXML
	public void redirigirOpcionesCompra(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/mediospago/mediospago.fxml")); //Vista de medios de pagos
	        Parent root = loader.load();
	        
	        Stage stage = new Stage();
	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("/resources/css/application.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm());
			stage.setResizable(false);
	        stage.setTitle("Medios de pago");
	        stage.setScene(scene);
	        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.close(); //Se cierra la ventana actual
	        stage.show(); // Se abre la ventana de opciones de compra
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}