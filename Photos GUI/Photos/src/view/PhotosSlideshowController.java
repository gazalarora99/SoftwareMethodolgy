/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package view;

import javafx.scene.image.Image;


import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional; 


/**
 * 
 * Handles the screen with Photos Slideshow, displays the currently selected photo with its tags and caption. Allows user to add or delete tags.
 * Allows user to slide left/right for a slideshow through the current album
 *
 */

public class PhotosSlideshowController {
	
	@FXML
	private Button back; 
	
	@FXML
	private Button logout;	
	
	@FXML
	private Button deletetag; 
	
	@FXML
	private Button addtag;
	
	@FXML
	private Button slideLeft;
	
	@FXML
	private Button slideRight;

	@FXML
	private Label caption;
	
	@FXML
	private Label date;
	
	@FXML
	private Label tagname;
	
	@FXML
	private Label tagvalue;
	
	@FXML
	private Button quit;
	
	@FXML 
	private ImageView imageview;

	@FXML
	private ListView<Tag> tagListView;

	/*
	@FXML
	public TableView<Tag> tagtable;
	
	@FXML
    TableColumn<Tag, String> tagNameColumn;
    
    @FXML
    TableColumn<Tag, String> tagValueColumn;
    
    
    */
    
    //public ArrayList<Tag> tagList = SelectedAlbumViewController.getSelectedPhoto().getTags();
	
	//linked to quit button
	
	/**
	 * Takes the user's selection as an input and Displays the user selected tag's name and value in the tag display area
	 * @param mouse - event when user clicks on an item in the tag list
	 * @throws IOException - throws IO exception
	 */
	@FXML 
	public void userSelection(MouseEvent mouse) throws IOException
	{
		Tag t = tagListView.getSelectionModel().getSelectedItem();
        if(t!=null) {
	        tagname.setText(t.getTagName());
			tagvalue.setText(t.getTagValue());
        }
        else {
        	tagname.setText("");
			tagvalue.setText("");
        }
	}
	
	/**
	 * Quits the application 
	 */
	public void quitPressed() {
		System.exit(0);
	}

	
	/**
	 * Loads the user back to Selected Album's screen which shows list of Photos in the album abandoning non confirmed changes
	 */
	public void backPressed() {
				
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectedAlbumView.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			SelectedAlbumViewController controller = loader.<SelectedAlbumViewController>getController();
			Scene scene = new Scene(parent);
			
			// go is the button pressed action event
			Stage stage = (Stage) ((Node) back).getScene().getWindow();
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
	 * Allows the user to delete the selected tag after an alert confirmation, removes the tag from list of tags for the selected photo
	 */
	public void deleteTagPressed() {
		Tag dl = tagListView.getSelectionModel().getSelectedItem();
		
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("Delete Tag");
		alert.setContentText("Do you want to delete the selected tag?");
		Optional<ButtonType> confirm = alert.showAndWait();
		if (confirm.get() == ButtonType.OK) {
			SelectedAlbumViewController.getSelectedPhoto().deleteTag(dl);
			tagname.setText("");
			tagvalue.setText("");
			tagListView.setItems(FXCollections.observableArrayList(SelectedAlbumViewController.getSelectedPhoto().getTags()));
			LoginController.saveData(LoginController.users);
		}
		
	}
	
	/**
	 * Loads the user to a new screen where they can add input to create a new tag for the selected photo
	 */
	public void addTagPressed() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AddTag.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			AddTagController controller = loader.<AddTagController>getController();
			Scene scene = new Scene(parent);
			
			// go is the button pressed action event
			Stage stage = (Stage) ((Node) addtag).getScene().getWindow();
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
	 * Allows the user to go to the Photo in the current album that appears right before the selected one. User can keep sliding left till they hit the very first photo in the album
	 */
	public void slideLeftPressed() {
		Photo newP;
		Photo selectedphoto = SelectedAlbumViewController.getSelectedPhoto();
		int k = AlbumsController.getSelectedAlbum().getPhotos().indexOf(selectedphoto);
		
		if(k > 0) {
			newP = AlbumsController.getSelectedAlbum().getPhotos().get(k-1);
		
		//set the imageview to that photo
        imageview.setImage(newP.getImage());
        caption.setText(newP.getCaption());
        date.setText("" + newP.getDate().getTime());
        ////ln(selectedphoto.getDate().getTime());
        tagListView.setItems(FXCollections.observableArrayList(newP.getTags()));
        tagListView.getSelectionModel().select(0);
        Tag t = tagListView.getSelectionModel().getSelectedItem();
	        if(t!=null) {
		        tagname.setText(t.getTagName());
				tagvalue.setText(t.getTagValue());
	        }
	        else {
	        	tagname.setText("");
				tagvalue.setText("");
	        }
	        SelectedAlbumViewController.selectedPhoto = newP;
	        //ln(SelectedAlbumViewController.getSelectedPhoto());
	        if (k - 1 <AlbumsController.getSelectedAlbum().getPhotos().size()-1) slideRight.setDisable(false);
		}
		
		else {
			slideLeft.setDisable(true);
			if (k<AlbumsController.getSelectedAlbum().getPhotos().size()-1) slideRight.setDisable(false);
	        
		}
	}
	
	/**
	 * Allows the user to go to the Photo in the current album that appears right after the selected one. User can keep sliding right till they hit the very last photo in the album
	 */
	public void slideRightPressed() {
		Photo newP;
		Photo selectedphoto = SelectedAlbumViewController.getSelectedPhoto();
		int k = AlbumsController.getSelectedAlbum().getPhotos().indexOf(selectedphoto);
		
		if(k < AlbumsController.getSelectedAlbum().getPhotos().size()-1) {
			newP = AlbumsController.getSelectedAlbum().getPhotos().get(k+1);
			
		//set the imageview to that photo
        imageview.setImage(newP.getImage());
        caption.setText(newP.getCaption());
        date.setText("" + newP.getDate().getTime());
        ////ln(selectedphoto.getDate().getTime());
        tagListView.setItems(FXCollections.observableArrayList(newP.getTags()));
        tagListView.getSelectionModel().select(0);
        Tag t = tagListView.getSelectionModel().getSelectedItem();
	        if(t!=null) {
		        tagname.setText(t.getTagName());
				tagvalue.setText(t.getTagValue());
	        }
	        else {
	        	tagname.setText("");
				tagvalue.setText("");
	        }
        SelectedAlbumViewController.selectedPhoto = newP;
        //ln(SelectedAlbumViewController.getSelectedPhoto());
        if(k+1>0) slideLeft.setDisable(false);
		}
		
		else {
			slideRight.setDisable(true);
			if(k>0) slideLeft.setDisable(false);
		}
        
       
	}
	
	
	/**
	 * Starts the screen that displays the currently selected photo with its tag list and caption, allows for a slideshow through the album
	 * @param mainStage - mainStage
	 * @throws IOException - throws IO exception
	 */
	public void start(Stage mainStage) throws IOException {                
		
		//tagNameColumn.setCellValueFactory(cellData -> cellData.getValue().getTagNameProperty());
        //tagValueColumn.setCellValueFactory(cellData -> cellData.getValue().getTagNameProperty());
        
        
		//get selected photo
		Photo selectedphoto = SelectedAlbumViewController.getSelectedPhoto();
        
		//set the imageview to that photo
        imageview.setImage(selectedphoto.getImage());
        caption.setText(selectedphoto.getCaption());
        date.setText(""+ selectedphoto.getDate().getTime());
        ////ln(selectedphoto.getDate().getTime());
        tagListView.setItems(FXCollections.observableArrayList(selectedphoto.getTags()));
        tagListView.getSelectionModel().select(0);
        Tag t = tagListView.getSelectionModel().getSelectedItem();
        if(t!=null) {
        tagname.setText(t.getTagName());
		tagvalue.setText(t.getTagValue());
        }
        else {
        	tagname.setText("");
			tagvalue.setText("");
        }
        int k = AlbumsController.getSelectedAlbum().getPhotos().indexOf(selectedphoto);
        
        if(k<=0) slideLeft.setDisable(true);
        else if (k>=AlbumsController.getSelectedAlbum().getPhotos().size()-1) slideRight.setDisable(true);
        
	}
	

	
	
}	

