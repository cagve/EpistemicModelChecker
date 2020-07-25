package org.interfazfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal encargada de ejecutar la interfaz.
 * @version 3.01
 * @author Carlos Aguilera
 */
public class App extends Application {
    //SampleController.java controller = new SampleController.java();
/**
*@param primaryStage  Recibe un marco vacío
*@return  Stage Devuelve la escena correspondiente a la interfaz
 */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("org/interfazfx/top.fxml"));
        Scene scene = new Scene(root,1000,700);
        primaryStage.setTitle("EMC " );
        primaryStage.setScene(scene);
        primaryStage.show();
    }

  /**
  * Método principal de la clase
   */
    public static void main(String[] args) {
        launch(args);
    }

}
