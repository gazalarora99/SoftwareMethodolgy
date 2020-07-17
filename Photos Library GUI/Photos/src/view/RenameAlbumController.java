/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package view;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Album;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.*;
import java.io.IOException; 

/**
 * 
 * Handles Renaming the selected album 
 *
 */
public class RenameAlbumController {
	
	@FXML
	public Button ok; 
	
	@FXML
	public Button cancel;
	
	@FXML 
	public Label oldalbumname;
	
	@FXML 
	public TextField newalbumname;
	
	@FXML
	public Button quit;
	
	
	
	/**
	 * Quits the application 
	 */
	public void quitPressed() {
		System.exit(0);
	}
	
	
	/**
	 * Checks if the input is a valid name for the album, renames the album if input is valid and loads back to the screen with a list of albums
	 */
	public void okPressed() {
		
		
		if (newalbumname.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Alert");
			alert.setContentText("Must provide a valid name or cancel.");
			alert.showAndWait();
			return;	
			
		}
		
		
		
		
		AlbumsController.getSelectedAlbum().setAlbumName(newalbumname.getText().trim());
		LoginController.saveData(LoginController.users);
		
		//go to albums controller and load corresponding fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Albums.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			AlbumsController controller = loader.<AlbumsController>getController();
			Scene scene = new Scene(parent);
			
			// ok is the button pressed action event
			Stage stage = (Stage) ((Node) ok).getScene().getWindow();
			controller.start(stage, AlbumsController.getCurrentUser());
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}	
	}
	
	/**
	 * All actions in textfields are abandoned, no album is renamed
	 * and user is loaded back to the screen with List of all Albums
	 */
	public void cancelPressed() {
		
		//go to albums controller and load corresponding fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Albums.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			AlbumsController controller = loader.<AlbumsController>getController();
			Scene scene = new Scene(parent);
			
			// ok is the button pressed action event
			Stage stage = (Stage) ((Node) ok).getScene().getWindow();
			controller.start(stage, AlbumsController.getCurrentUser());
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}	
	}
	
	/**
	 * Starts the screen where user can input a new name for selected Album
	 * @param mainStage - mainStage
	 * @throws IOException - throws IO exception
	 */
	public void start(Stage mainStage) throws IOException {   
		
		
		//AlbumsController.getSelectedAlbum();
		
		oldalbumname.setText(AlbumsController.getSelectedAlbum().getAlbumName());

	}
	
	
}	

