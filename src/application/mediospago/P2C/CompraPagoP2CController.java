package application.mediospago.P2C;

import application.comun;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CompraPagoP2CController implements Initializable {
	@FXML
	private TextField fieldMonto;
	@FXML
	private ComboBox comboBoxMonedaPago;
	@FXML
	private ComboBox boxBancoCliente;
	@FXML
	private TextField fieldTelefonoCliente;
	@FXML
	private ComboBox boxCodigoTelefonoCliente;
	@FXML 
	private comun comun = new comun();
	
	// Comprueba que los campos no esten vacíos
	private boolean camposObligatoriosCompletados() {
        return !fieldMonto.getText().isEmpty() &&
               !fieldTelefonoCliente.getText().isEmpty() &&
               !(comboBoxMonedaPago.getValue() == null) &&
               !(boxBancoCliente.getValue() == null) &&
               !(boxCodigoTelefonoCliente.getValue() == null);
    }
	
	@FXML
	// Envía los datos de la petición de P2C
	public void enviarDatosPeticionP2C(ActionEvent event) {
		// Verificar si se completaron los campos obligatorios
		if(camposObligatoriosCompletados()) 
		{
			try {
				comun.mostrarVentanaProcesando();
				comun.disable(); // Desactiva el certificado
	    	    URL url = new URL("https://localhost:443/vpos/compra/p2c"); 
	    	    // Establecer la conexión HTTP
	    	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    	    connection.setRequestMethod("POST");
	    	    connection.setRequestProperty("Content-Type", "application/json");
	    	    connection.setDoOutput(true);
	
	    	    // Construir el cuerpo del JSON a enviar
	    	    String jsonInputString = "{\"monto\": \"" + fieldMonto.getText() + 
	    	    		"\", \"moneda\": \"" +  comun.obtenerCodigo("C:\\workspaces2023\\prueba2\\src\\configuracion\\conf.ini", "parmTipoMoneda", comboBoxMonedaPago.getValue().toString()) + 
	    	    		"\", \"bancoCliente\": \"" + comun.obtenerCodigo("C:\\workspaces2023\\prueba2\\src\\configuracion\\conf.ini", "acqcode", boxBancoCliente.getValue().toString()) + 
	    	    		"\", \"telefonoCliente\": \"" + comun.obtenerCodigo("C:\\workspaces2023\\prueba2\\src\\configuracion\\conf.ini", "parmAreaTelefonico", boxCodigoTelefonoCliente.getValue().toString()) + fieldTelefonoCliente.getText()+ 
	    	    		"\", \"referencia\": \"123456\", \"vtid\": \"yruiz01\", \"telefonoComercio\": \"4241767908\", \"bancoComercio\": \"0105\"}";
	 
	    	    System.out.println(jsonInputString);
	    	    
	    	    // Enviar el cuerpo del JSON al servidor
	    	    try (OutputStream os = connection.getOutputStream()) {
	    	        byte[] input = jsonInputString.getBytes("utf-8");
	    	        os.write(input, 0, input.length);
	    	        
	    	    }
	
	    	    // Obtener el código de respuesta del servidor
	    	    int responseCode = connection.getResponseCode();
	    	    System.out.println("Response Code: " + responseCode);
	
	    	    // Leer la respuesta del servidor
	    	    if (responseCode == HttpURLConnection.HTTP_OK) {
	    	        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
	    	            String responseLine;
	    	            StringBuilder response = new StringBuilder();
	    	            while ((responseLine = br.readLine()) != null) {
	    	                response.append(responseLine.trim());
	    	            }
	    	            System.out.println("Response: " + response.toString());
	    	            comun.mostrarVentanaInformacion("Procesado",true);
	    	            regresarMenuPrincipal(event);
	    	        }
	    	    } else {
	    	    	// Mostrar mensaje de error si la respuesta no es exitosa
	    	    	comun.mostrarVentanaInformacion("Error", true);
	    	    }
	    	    // Cerrar la conexión
	    	    connection.disconnect();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
		}
		else {
			// Mostrar mensaje de error si no se completaron los campos obligatorios
			comun.mostrarErrorCamposObligatorios();
		}
	}
	
	@FXML
	public void regresarMenuPrincipal (ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/VPOS.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/resources/css/application.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm());
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.close();
			stage.setResizable(false);			
            stage.setTitle("Megasoft");
            stage.setScene(scene);
            stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	// Se inicializan la listas de los box
	public void initialize(URL arg0, ResourceBundle arg1) {
		comun.leerArchivo("C:\\workspaces2023\\prueba2\\src\\configuracion\\conf.ini", "acqcode", boxBancoCliente);
		comun.leerArchivo("C:\\workspaces2023\\prueba2\\src\\configuracion\\conf.ini", "parmTipoMoneda", comboBoxMonedaPago);
		comun.leerArchivo("C:\\workspaces2023\\prueba2\\src\\configuracion\\conf.ini", "parmAreaTelefonico", boxCodigoTelefonoCliente);
	}
}
