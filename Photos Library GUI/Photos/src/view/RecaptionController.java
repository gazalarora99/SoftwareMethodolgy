/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package view;

import java.util.ArrayList;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import javafx.scene.control.*;
import javafx.scene.*;
import java.io.IOException; 

/**
 * 
 * Handles the screen which allows the user to recaption the selected photo in an album
 *
 */
public class RecaptionController {
	
	@FXML
	public Button logout;
	
	@FXML
	public Button quit; 	
	
	@FXML
	public Button confirm;
	
	@FXML
	public Button cancel; 
	
	@FXML
	public Label oldcaption;
	
	@FXML
	public TextField newcaption;
	
	

	
	/**
	 * Logout ends current user's session, loads back to the Login page
	 */
	public void logoutPressed() {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			LoginController controller = loader.<LoginController>getController();
			Scene scene = new Scene(parent);
			
			// go is the button pressed action event
			Stage stage = (Stage) ((Node) logout).getScene().getWindow();
			controller.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		
	}
	
	/**
	 * Quits the application 
	 */
	public void quitPressed() {
		
		System.exit(0);
		
	}
	
	/**
	 * checks if the input is valid and if valid then recaptions the selected photo and loads the user back to Selected Album's View: the screen with list of Photos in the Album.
	 */
	public void confirmPressed() {
		
		Photo selectedphoto = SelectedAlbumViewController.getSelectedPhoto();
		
		ArrayList<Photo> arrphotos = AlbumsController.getSelectedAlbum().getPhotos();
		
		//trying to access just the one photo
		int i = 0;
		while (arrphotos.size()>i) {
		if (selectedphoto.getFileName().equals(arrphotos.get(i).getFileName())) {
			//ln("herez");
			selectedphoto = arrphotos.get(i);
		}
		i++;
		}
		
		//Photo selectedphoto2 = AlbumsController.getCurrentUser
		
		selectedphoto.setCaption(newcaption.getText().trim());
		
		LoginController.saveData(LoginController.users);
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectedAlbumView.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			SelectedAlbumViewController controller = loader.<SelectedAlbumViewController>getController();
			Scene scene = new Scene(parent);
			
			// go is the button pressed action event
			Stage stage = (Stage) ((Node) confirm).getScene().getWindow();
			controller.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		
	}
	
	/**
	 * All actions in textfields are abandoned, no photo is recaptioned
	 * and user is sent back to Selected Album's View with list of photos in that Album
	 */
	public void cancelPressed() {
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectedAlbumView.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			SelectedAlbumViewController controller = loader.<SelectedAlbumViewController>getController();
			Scene scene = new Scene(parent);
			
			// go is the button pressed action event
			Stage stage = (Stage) ((Node) cancel).getScene().getWindow();
			controller.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		
	}
	

	/**
	 * Starts the screen where a user can input a new Caption for the selected photo
	 *@param mainStage - mainStage
	 * @throws IOException - throws IO exception
	 */
	public void start(Stage mainStage) throws IOException {         
		
		if (SelectedAlbumViewController.getSelectedPhoto().getCaption().trim().equals("")) {
			oldcaption.setText("(None)");	
		}
		else {
			oldcaption.setText(SelectedAlbumViewController.getSelectedPhoto().getCaption());	
		}

	}

	
	
}	

