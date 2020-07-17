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
import model.Photo;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set; 

/**
 * 
 * Addition of a Tag to the currently selected Photo is handled by this controller, allows to add a new tag by choosing name from preexisting tag names and adding a value to it OR allows user to create a new tag name and add value to it
 *
 */
public class AddTagController {
	
	@FXML
	private Button confirm; 
	
	@FXML
	private Button logout;
	
	@FXML
	private Button cancel;	
	
	@FXML
	private Button quit;
    
    @FXML
    private ListView<String> tagView;
	
	@FXML
	private TextField exisitngtagvalue;
	
	@FXML
	private TextField newtagname;
	
	@FXML
	private TextField newtagvalue;
	
	private ArrayList<String> tagnames;
	
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
	 * Checks if the input for adding a tag is valid, if valid then adds the tag and loads back to Photos slideshow screen with current photo displaying
	 */
	public void confirmPressed() {
		
		if(checkExisting()) {
		
		if(exisitngtagvalue.getText().trim().equals("") && !newtagname.getText().trim().equals("") && !newtagvalue.getText().trim().equals("")) {
			//Create a new tag from the textfields and add it to taglist of selected album
			
			 tagnames.add(newtagname.getText().trim()); 
			 deleteRepeated();
			 tagView.setItems(FXCollections.observableArrayList(tagnames));
			Tag newTag = new Tag (newtagname.getText().trim(), newtagvalue.getText().trim());
			(SelectedAlbumViewController.getSelectedPhoto()).addTagToPhoto(newTag);
			LoginController.saveData(LoginController.users);
		
		}
		
		else if (!exisitngtagvalue.getText().trim().equals("") && newtagname.getText().trim().equals("") && newtagvalue.getText().trim().equals("")) {
			if(tagView.getSelectionModel().getSelectedItem()!=null) {
			Tag newTag = new Tag (tagView.getSelectionModel().getSelectedItem(), exisitngtagvalue.getText().trim());
			//newTag.setTagValue(exisitngtagvalue.getText());
			(SelectedAlbumViewController.getSelectedPhoto()).addTagToPhoto(newTag);
			LoginController.saveData(LoginController.users);
			}
			
			else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("ALERT");
				alert.setHeaderText("No Tag Name Selected");
				alert.setContentText("Tag Name not selected for existing tag value");
				alert.showAndWait();
				return;
			}
			
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ALERT");
			alert.setHeaderText("Invalid Input");
			alert.setContentText("Choose an existing tag name and add a value to it \nOR Create a new tag name and value pair");
			alert.showAndWait();
			return;
		}
	}	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PhotosSlideshow.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			PhotosSlideshowController controller = loader.<PhotosSlideshowController>getController();
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
	
	private boolean checkExisting() {
		Photo p = SelectedAlbumViewController.getSelectedPhoto();
		if(p.getTags()!=null) {
			for(Tag t: p.getTags()) {
				if(t.getTagName().equalsIgnoreCase(newtagname.getText().trim()) && t.getTagValue().equalsIgnoreCase(newtagvalue.getText().trim())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ALERT");
					alert.setHeaderText("Invalid Input");
					alert.setContentText("Tag already exists for Selected photo");
					alert.showAndWait();
					newtagname.setText("");
					newtagvalue.setText("");
					return false;
				}
				
				else if (t.getTagValue().equalsIgnoreCase(exisitngtagvalue.getText().trim()) && tagView.getSelectionModel().getSelectedItem() != null) {
					if(t.getTagName().equalsIgnoreCase(tagView.getSelectionModel().getSelectedItem())) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("ALERT");
						alert.setHeaderText("Invalid Input");
						alert.setContentText("Tag already exists for Selected photo");
						alert.showAndWait();
						exisitngtagvalue.setText("");
						return false;
					}
				
				}
				
				else if (t.getTagName().equalsIgnoreCase("location") && (newtagname.getText().trim().equalsIgnoreCase("location"))) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ALERT");
					alert.setHeaderText("Invalid Input");
					alert.setContentText("Location already exists for Selected photo, \na Photo can have only one location");
					alert.showAndWait();
					newtagname.setText("");
					newtagvalue.setText("");
					return false;
				}
				else if (t.getTagName().equalsIgnoreCase("location") && (tagView.getSelectionModel().getSelectedItem() != null)) {
					if(("location").equalsIgnoreCase(tagView.getSelectionModel().getSelectedItem())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ALERT");
					alert.setHeaderText("Invalid Input");
					alert.setContentText("Location already exists for Selected photo, \na Photo can have only one location");
					alert.showAndWait();
					newtagname.setText("");
					newtagvalue.setText("");
					return false;
					}
				}
				
			}
			
		}
		return true;
	}
	
	/**
	 * All actions in textfields and browsefields are abandoned, no tag is added to selected photo
	 * and user is sent back to Photos Slideshow screen
	 */
	public void cancelPressed() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PhotosSlideshow.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			PhotosSlideshowController controller = loader.<PhotosSlideshowController>getController();
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
	
	/*linked to button
	public void exisitngTagsPressed() {
		
		
	}
	*/
	
	private void deleteRepeated() {
		if(tagnames!=null) {
		for(int i =0; i< tagnames.size(); i++) {
			tagnames.set(i, tagnames.get(i).toLowerCase());
		}
		}
		Set<String> setx = new HashSet<>(tagnames);
		tagnames.clear();
		tagnames.addAll(setx);
		
	}
	
	
	
	/**
	 * Starts the screen for Adding a tag to selected photo. Displays the list of preexisting tag names in a tableview and allows user input for a new tag name/value
	 * @param mainStage - mainStage
	 * @throws IOException - throws IO exception
	 */
	public void start(Stage mainStage) throws IOException {                

		//instead of adding to a static list, you may just want to create this instance
		//ArrayList<Tag> tagsarraylist = new ArrayList<Tag>(); 
		//and add to it
		//otherwise everytime this stage starts you are adding to the static list again
		
		////////
		
		tagnames = new ArrayList<String> ();
		
		tagnames.add("Location");
		tagnames.add("Person");
		tagnames.add("Event");
		
		//ln("here in start tag 1");
		for(Album a: AlbumsController.getCurrentUser().getAlbums()) {
			for(Photo p: a.getPhotos()) {
				if(p.getTags()!=null) {
			
					for(Tag t: p.getTags()) {
								tagnames.add(t.getTagName());
					}
					
				}
			}
		}
		
		deleteRepeated();
		//delete repeated method will not work correctly for an arraylist
		//especially when you are changing size of it while iterating through
		//instead put into hashset (does not allow duplicates)
		//then return to arraylist
		//duplicates will be gone
		
		tagView.setItems(FXCollections.observableArrayList(tagnames));
		
		
		
	}
	
	
	
}	

