/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.*;
import java.io.IOException;
import java.util.Calendar; 

/**
 * Loads the user to the screen where they can choose to copy or move the selected photo to an album of choice
 * Handles copying and moving a photo from one album to another
 *
 */
public class CopyOrMoveController {
	
	@FXML
	private Button quit; 
	
	@FXML
	private Button logout;	
	
	@FXML
	private Button cancel; 
	
	@FXML
	private Button ok;

	@FXML
	private RadioButton Copy;
	
	@FXML
	private RadioButton Move;
	
	@FXML
	private ToggleGroup group1;
	
	//@FXML
	//public MenuButton albumlist;
	
	@FXML
	ListView<Album> albumListView;
	
	/*
    @FXML
    TableView<Album> albumTableView;
    
    @FXML
    TableColumn<Album, String> albumNameColumn;
	*/
	
	@FXML
	private Label currphoto;
	
	@FXML
	private ImageView iv;
	

	

	
	/**
	 * Quits the application 
	 */
	public void quitPressed() {
		System.exit(0);
	}
	
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
	 * All actions in textfields and buttons are abandoned, no photo is copied/moved to any album
	 * and user is sent back to Selected Album's View with alist of Photos in that album
	 */
	public void cancelPressed() {
		
		Album selectedalbum = new Album();	
		
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
	 * Checks user input for copy or for move and follows the appropriate action to the selected album, the selected album will now have this photo or a copy of it
	 */
	public void okPressed() {
		
		//get album selection to move or copy photo to
		Album selectedalbum = albumListView.getSelectionModel().getSelectedItem();
		
		
		
		if (selectedalbum==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Delete");
			alert.setContentText("No album selected");
			alert.showAndWait();
			return;
		}
		
		
		if (selectedalbum==AlbumsController.getSelectedAlbum()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Same Album");
			alert.setContentText("That photo already exists in this album.\n Choose a different album to copy/move this album's photo to.");
			alert.showAndWait();
			return;
		}
		
		
	
		RadioButton selectedRadioButton = (RadioButton) group1.getSelectedToggle();
		
		if (selectedRadioButton == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Delete");
			alert.setContentText("Must elect to Copy or Move");
			alert.showAndWait();
			return;
			
		}
		
		
		String toggleGroupVal = selectedRadioButton.getText().trim();
		
		//ln(group1.getSelectedToggle());
		//ln(selectedRadioButton.getText());
		
		//determine which radio button was pressed
		if (toggleGroupVal.equals("Move")){
			Photo p = SelectedAlbumViewController.getSelectedPhoto();
			
			selectedalbum.addPhoto(p);
			LoginController.saveData(LoginController.users);
			
			//since moving, remove from current album after adding to new album
			Album currentalbum = AlbumsController.getSelectedAlbum();
			currentalbum.removePhoto(p);
			LoginController.saveData(LoginController.users);
			

			
			
		}
		else if (toggleGroupVal.equals("Copy")){
			
			//Photo pz = SelectedAlbumViewController.getSelectedPhoto();
			
			
			
			//Photo p = new Photo(pz.getFileName(), pz.getImage(), pz.getDate());

			Photo p = SelectedAlbumViewController.getSelectedPhoto();
			
			
			selectedalbum.addPhoto(p);
			LoginController.saveData(LoginController.users);
			
		}
		
		
		//update observablelist?
		
		
		//return to selected album view
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectedAlbumView.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			SelectedAlbumViewController controller = loader.<SelectedAlbumViewController>getController();
			Scene scene = new Scene(parent);
			
			// go is the button pressed action event
			Stage stage = (Stage) ((Node) ok).getScene().getWindow();
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
	 * Starts the screen where user can choose to copy of move the photo they selected to any of the albums in their album list
	 * @param mainStage - mainStage
	 * @throws IOException - throws IO exception
	 */
	public void start(Stage mainStage) throws IOException {                
	//albumTableView.setItems(AlbumsController.albumList);
		
		Photo selectedphoto = SelectedAlbumViewController.getSelectedPhoto();
		//ln(SelectedAlbumViewController.getSelectedPhoto().getFileName());
        iv.setImage(selectedphoto.getImage());
        albumListView.setItems(FXCollections.observableArrayList(AlbumsController.getCurrentUser().getAlbums()));
	}

	
	
}	

