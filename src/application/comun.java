package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class comun {
	
	private static final Logger LOGGER = Logger.getLogger(Application.class.getName()); // Parámetro para establecer mensajes
	private Stage processingStage; //Ventana de procesando
	
	//leer archivos de configuracion para formularios
	public void leerArchivo (String filePath, String sectionTitle, ComboBox boxBancoComercio) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Map<String, String> bancoMap = new HashMap<>();
            boolean inSection = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith(";") || line.isEmpty()) {
                    // Ignorar comentarios y líneas vacías
                    continue;
                } else if (line.startsWith("[")) {
                    // Comprobar si estamos dentro de la sección deseada
                    inSection = line.substring(1, line.length() - 1).equalsIgnoreCase(sectionTitle);
                } else if (inSection && line.contains("=")) {
                    // Extraer valores clave-valor dentro de la sección deseada
                    String[] parts = line.split("=", 2);
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    bancoMap.put(key, value);
                }
            }
            if (!bancoMap.isEmpty()) {
                ObservableList<String> bancos = FXCollections.observableArrayList(bancoMap.values());
                System.out.println(bancos);
                boxBancoComercio.setItems(bancos);
            } else {
                System.out.println("No se encontraron bancos en la sección '" + sectionTitle + "'.");
            }
        } catch (IOException e) {
            LOGGER.warning("Hubo un error: "+ e);
        }
		
	}
	
	// Método para buscar el nombre dado el código del banco
	public static <K, V> K getKey(Map<K, V> map, V value)
    {
        for (Map.Entry<K, V> entry: map.entrySet())
        {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
	
	//desactivar certificado
	public static void disable() throws Exception {
        // Crea un administrador de confianza que no valida los certificados
		TrustManager[] trustAllCerts = new TrustManager[] {
	            new X509TrustManager() {
	                public void checkClientTrusted(X509Certificate[] chain, String authType) {}

	                public void checkServerTrusted(X509Certificate[] chain, String authType) {}

	                public X509Certificate[] getAcceptedIssuers() {
	                    return new X509Certificate[0];
	                }
	            }
	        };

        // Obtén una instancia del contexto SSL
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, null);

        // Establece el contexto SSL predeterminado
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        // Desactiva la verificación del nombre del host
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
    }
	
	//obtener codigo en el archivo
	public String obtenerCodigo(String filePath, String sectionTitle,  String bancoNombre) {
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        Map<String, String> valueMap = new HashMap<>();
	        boolean inSection = false;

	        while ((line = reader.readLine()) != null) {
	            line = line.trim();

	            if (line.startsWith(";") || line.isEmpty()) {
	                // Ignorar comentarios y líneas vacías
	                continue;
	            } else if (line.startsWith("[")) {
	                // Comprobar si estamos dentro de la sección deseada
	                inSection = line.substring(1, line.length() - 1).equalsIgnoreCase(sectionTitle);
	            } else if (inSection && line.contains("=")) {
	                // Extraer valores clave-valor dentro de la sección deseada
	                String[] parts = line.split("=", 2);
	                String key = parts[0].trim();
	                String value = parts[1].trim();

	                valueMap.put(key, value);
	            }
	        }
	        return getKey(valueMap, bancoNombre);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return "";
	}
	
	// Ventana para mostrar mensajes de información al usuario
	public void mostrarVentanaInformacion(String mensaje, Boolean stage) {
		if (stage) {
			processingStage.close();
	    }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
	
	// Ventana para mostrar mensajes de error al usuario
	public void mostrarErrorCamposObligatorios() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, complete todos los campos obligatorios.");
        alert.showAndWait();
    }
	
	// Ventana para mostrar mensajes de error al usuario
	public void mostrarVentanaProcesando() {
		// Crear un nuevo Dialog
        Dialog<Void> dialog = new Dialog<>();
        VBox content = new VBox();

        // Crear un ImageView para mostrar el GIF
        ImageView imageView = new ImageView();
        Image gifImage = new Image("resources/img/Procesando.gif"); 
        imageView.setImage(gifImage);

        // Ajustar el tamaño del ImageView al tamaño de la ventana
        imageView.fitWidthProperty().bind(content.widthProperty());
        imageView.fitHeightProperty().bind(content.heightProperty());

        // Agregar el ImageView al VBox
        content.getChildren().add(imageView);

        // Crear la escena
        Scene scene = new Scene(content, 250, 300);

        // Configurar el estilo del VBox para centrar el ImageView
        content.setAlignment(Pos.CENTER);

        // Configurar el título y la modalidad
        processingStage = (Stage) dialog.getDialogPane().getScene().getWindow();
        processingStage.setTitle("Procesando...");
        processingStage.initModality(Modality.APPLICATION_MODAL);

        // Mostrar el diálogo
        dialog.show();
        processingStage.setScene(scene);
	}
}
