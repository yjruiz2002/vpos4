package application;
	
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX POST Request Example");

        // Crear un botón
        Button sendButton = new Button("Enviar Petición POST");
        sendButton.setOnAction(e -> enviarPeticion());

        // Crear un contenedor para el botón
        VBox root = new VBox(10);
        root.getChildren().add(sendButton);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void enviarPeticion() {
    	try {
    	    URL url = new URL("https://localhost:443/vpos/compra/c2p"); // Cambia la URL según tu backend
    	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    	    connection.setRequestMethod("GET");
    	    connection.setRequestProperty("Content-Type", "application/json");
    	    connection.setDoOutput(true);

    	    // Cuerpo del JSON
    	    String jsonInputString = "{\"first_name\": \"Rodrigo\", \"last_name\": \"Gómez\", \"last_name\": \"Gómez\", \"email\": \"rodrigo.gomez@peticiones.online\", \"username\": \"rodrigo.gomez\", \"password\": \"12345\"}";

    	    
    	    try (OutputStream os = connection.getOutputStream()) {
    	        byte[] input = jsonInputString.getBytes("utf-8");
    	        os.write(input, 0, input.length);
    	    }

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
    	            // Procesa la respuesta del servidor aquí
    	        }
    	    }

    	    connection.disconnect();
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
