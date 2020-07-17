
/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package app;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.*;


//All buttons and scenes are connected
//except still need to figure out how drop downs will work
//All textfields connected
//Added file chooser to "browse" button
//All neccessary objects must go in model class
//each window now has a Quit button in the same spot as specified in the spec
//Can now store albums as objects within an observable list
//the observable list populates the tableview in Albums view
// delete button fully functional in Albums view

//TODO
// 1) Figure out how to store photo data (serializable persistent data)
// each user should have its own storage of photos
// 2) Controller Logic based on how we are storing photos




public class Photos extends Application {
	@Override
	
	/**
	 * 
	 * Photos starts the main stage by extending Application
	 * @param primaryStage - starting stage of Application
	 */

	public void start(Stage primaryStage) 
	throws IOException {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/Login.fxml"));
		
		
		AnchorPane root = (AnchorPane)loader.load();		
		LoginController listController = loader.getController();
		
		
		listController.start(primaryStage);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show(); 
	}

	/**
	 * main(String[] args)
	 * : main method to start the application and take user input
	 * @param args - arguments on command line
	 * 
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
