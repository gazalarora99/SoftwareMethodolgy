/*
 * Whitney Alderman and Gazal Arora
 * 
 */
package app;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.Controller;

public class SongLib extends Application {
	@Override
	public void start(Stage primaryStage) 
	throws IOException {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/SongLibGUI.fxml"));
		AnchorPane root = (AnchorPane)loader.load();


		Controller listController = loader.getController();
		listController.start(primaryStage);

		Scene scene = new Scene(root, 750, 650);
		primaryStage.setScene(scene);
		primaryStage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}

}
