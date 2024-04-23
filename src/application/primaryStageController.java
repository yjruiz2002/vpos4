package application;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import javafx.scene.control.Label;

public class primaryStageController {
	@FXML
	private Label labPrincipal;
	@FXML
	private Label labSecundario1;
	@FXML
	private Label labSecundario2;

	// Event Listener on Button.onAction
	@FXML
	public void cambiarLabel(ActionEvent event) {
		labPrincipal.setText("Bienvenido");
	}
	// Event Listener on Button.onAction
	@FXML
	public void cambiarLabel1(ActionEvent event) {
		labSecundario1.setText("Elegida");
	}
	// Event Listener on Button.onAction
	@FXML
	public void cambiarLabel2(ActionEvent event) {
		labSecundario1.setText("Elegida");
	}
}
