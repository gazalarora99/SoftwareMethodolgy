/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */

package view;

import model.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.*;
import java.io.IOException;

/**
 * Loads the user to the screen where they can create a new album
 * Handles user input for a new album name and adds it to user's list of albums if input is valid
 *
 */
public class CreateAlbumController {
	
	@FXML
	private Button ok; 
	
	@FXML
	private Button cancel;
	
	@FXML
	private Button quit;
	
	@FXML 
	private TextField albumname;
	
	
	
	/**
	 * Quits the application 
	 */
	public void quitPressed() {
		System.exit(0);
	}
	
	
	/**
	 * Checks if user input to create a new album is valid
	 * If valid, creates a new album of input name and adds to users list of albums and loads back to the screen with user's list of Albums
	 */
	public void okPressed() {
		
		if (albumname.getText().trim().toString().equals("")) {
			
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Error");
			alert.setContentText("Must enter an album name.");
			alert.showAndWait();
			return;
		}
	
		String albumName = albumname.getText().trim().toString();
		
		if (checkForDuplicateAlbumNames(albumName)==true) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Duplicate Names");
			alert.setContentText("An album already has that name. \n Enter a new name.");
			alert.showAndWait();
			return;
		}
		
		
	
		Album newalbum = new Album(albumName);
		AlbumsController.getCurrentUser().addAlbumToUser(newalbum);
		LoginController.saveData(LoginController.users);
		
		
		//go to albums controller and load corresponding fxml
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Albums.fxml"));
				try{
					Parent parent = (Parent) loader.load();
					AlbumsController controller = loader.<AlbumsController>getController();
					Scene scene = new Scene(parent);
					
					// ok is the button pressed action event
					Stage stage = (Stage) ((Node) ok).getScene().getWindow();
					controller.start(stage,AlbumsController.getCurrentUser());
					stage.setScene(scene);
					stage.centerOnScreen();
					stage.show();
				}
				catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		
	
	/**
	 * All actions in textfields are abandoned, no album is added to selected user's album list
	 * and user is sent back to screen with Users list of Albums
	 */
	public void cancelPressed() {
		
		
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Albums.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			AlbumsController controller = loader.<AlbumsController>getController();
			Scene scene = new Scene(parent);
			
			// ok is the button pressed action event
			Stage stage = (Stage) ((Node) ok).getScene().getWindow();
			controller.start(stage,AlbumsController.getCurrentUser());
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}	
	}
	
	/**
	 * Starts the screen where user can input a name and create a new album of same name
	 * @param mainStage - mainStage
	 * @throws IOException - throws IO exception
	 */
	public void start(Stage mainStage) throws IOException {                

	}
	

	static boolean checkForDuplicateAlbumNames(String albumName) {
	int i = 0;
	while (AlbumsController.getCurrentUser().getAlbums().size()>i) {
		if (AlbumsController.getCurrentUser().getAlbums().get(i).getAlbumName().trim().toLowerCase().equals(albumName.trim().toLowerCase())) {
			

			return true;
			
			
		}
		i++;
	}
	
	return false;
	}
	
	

	
	
}	

