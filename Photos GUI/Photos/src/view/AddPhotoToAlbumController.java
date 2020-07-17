/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import model.*;
import javafx.scene.image.*;
import javafx.stage.Stage;
import java.io.File;
import javafx.stage.FileChooser.*;
import javafx.stage.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar; 
import java.nio.file.*;

import java.text.SimpleDateFormat;


/**
 * 
 * Addition of a Photo to the selected Album is handled by this controller
 *
 */
public class AddPhotoToAlbumController {
	
	@FXML
	private Button quit; 
	
	@FXML
	private Button logout;
	
	@FXML
	private Button cancel;
	
	@FXML
	private Button ok; 
	
	@FXML
	private Button browse;
	
	@FXML
	private ListView<String> tagView;
	
	@FXML 
	private TextField browsefield;
	
	@FXML 
	private TextField captionfield;
	
	@FXML 
	private TextField exisitingvalue;
	
	@FXML 
	private TextField newname;
	
	@FXML 
	private TextField newvalue;
	
	Album selectedAlbum;
	
	Photo photoToBeAdded;
	
	static String pathname;
	static File selectedFile;
	private ArrayList<String> taglist;
	
	
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
	 * All actions in textfields and browsefields are abandoned, no photo is added to selected album
	 * and user is sent back to Selected Album's View
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
	 * Checks if the file chosen by user is valid, if so it's added to selected album as a photo 
	 * and allows user to add a caption and tags to this photo
	 * Loads back to Selected Album's View which shows the newly added photo's thumbnail and caption
	 */
	public void okPressed() {
		
		
		// only add the photo to the album if they have chosen a valid file
		//checks browse textfield
		if (isValidFile(browsefield.getText().trim())) {

			selectedFile = new File(browsefield.getText().trim());
			
			
			long s = selectedFile.lastModified();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			//ln(sdf.format(selectedFile.lastModified()));
			
			/*
			//creates a new photo with the last modified date of the file
			Date d = new Date(selectedFile.lastModified());
			Calendar cal = Calendar. getInstance();
			cal.set(Calendar.MILLISECOND, 0);
			cal.setTime(d);
			
			cal.set(Calendar.MILLISECOND, 0);
			*/
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(selectedFile.lastModified());
			cal.set(Calendar.MILLISECOND, 0);
			
			
			//ln("line 148");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//ln(sdf2.format(cal.getTime()));
			
			
			 //save as an image and new photo
			 Image image = new Image(selectedFile.toURI().toString());
			 //ln(selectedFile.toURI().toString());
			 
			 //Calendar date = Calendar.getInstance(); 
			 //date.set(Calendar.MILLISECOND, 0);
			 
			 photoToBeAdded = new Photo(selectedFile.toURI().toString(), image, cal);
			
			 
				//ln("line 163");
				SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//ln(sdf3.format(cal.getTime()));
			 
			
			//add to album
			 Album currentAlbum = AlbumsController.getSelectedAlbum();
			 photoToBeAdded.setCaption(captionfield.getText().trim());
			
			 
			 if(exisitingvalue.getText().trim().equals("") && !newname.getText().trim().equals("") && !newvalue.getText().trim().equals("")) {
					//Create a new tag from the textfields and add it to taglist of selected album
					 taglist.add(newname.getText().trim()); 
					 deleteRepeated();
					
					Tag newTag = new Tag (newname.getText().trim(), newvalue.getText().trim());
					photoToBeAdded.addTagToPhoto(newTag);
					LoginController.saveData(LoginController.users);
				
				}
				
				else if (!exisitingvalue.getText().trim().equals("") && newname.getText().trim().equals("") && newvalue.getText().trim().equals("")) {
					if(tagView.getSelectionModel().getSelectedItem()!=null) {
					Tag newTag = new Tag (tagView.getSelectionModel().getSelectedItem(), exisitingvalue.getText().trim());
					
					photoToBeAdded.addTagToPhoto(newTag);
					LoginController.saveData(LoginController.users);
					}
					
					else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("ALERT");
						alert.setHeaderText("No Tag Name Selected");
						alert.setContentText("Tag Name not selected for existing tag value, can't create Tag");
						alert.showAndWait();
						return;
					}
					
					
				}
			 
				else if (!exisitingvalue.getText().trim().equals("") && ((newname.getText().trim().equals("") && !newvalue.getText().trim().equals("")) || (!newname.getText().trim().equals("") && newvalue.getText().trim().equals("")) || (!newname.getText().trim().equals("") && !newvalue.getText().trim().equals(""))) ) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("ALERT");
					alert.setHeaderText("Invalid Input for Tag");
					alert.setContentText("Can't create Tag with incorrect input");
					alert.showAndWait();
					exisitingvalue.setText("");
					newname.setText("");
					newvalue.setText("");
					return;
				}
				else if (exisitingvalue.getText().trim().equals("") && ((newname.getText().trim().equals("") && !newvalue.getText().trim().equals("")) || (!newname.getText().trim().equals("") && newvalue.getText().trim().equals("")))){
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("ALERT");
					alert.setHeaderText("Invalid Input for Tag");
					alert.setContentText("Can't create Tag with incorrect input");
					alert.showAndWait();
					exisitingvalue.setText("");
					newname.setText("");
					newvalue.setText("");
					return;
				}
			 
			// tagView.setItems(FXCollections.observableArrayList(taglist));
			
			 currentAlbum.addPhoto(photoToBeAdded);	
			 LoginController.saveData(LoginController.users);
			 
		}
		
		else {
			//ln("alert2");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Invalid");
			alert.setContentText("Invalid pathname. Must be jpg, jpeg, gif, png or bmp.");
			alert.showAndWait();
			return;	
		}
		
	
		
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
	 * Allows to choose a file from user's system when browse is clicked.
	 *
	 */
	public void browsePressed() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Photo");
		 fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Image Files", "*.png", "*.jpeg", "*.jpg", "*.gif", "*.bmp", "*.PNG", "*.JPEG", "JPG", "*.GIF", "*.BMP"));
		Stage stage = (Stage) ((Node) browse).getScene().getWindow();
		selectedFile = fileChooser.showOpenDialog(stage);
		 if (selectedFile != null) {
			 //this will give the path of the selected file
			 //ln(selectedFile.getAbsolutePath());
			 pathname = selectedFile.getAbsolutePath();
			 browsefield.setText(selectedFile.getAbsolutePath());
			 	captionfield.setDisable(false);
				tagView.setDisable(false);
				exisitingvalue.setDisable(false);
				newname.setDisable(false);
				newvalue.setDisable(false);
				deleteRepeated();
				tagView.setItems(FXCollections.observableArrayList(taglist));
			 }
		 else {
			 pathname = null;
		 }
		 
		 
		 

		 
		
		 
		 
	}
	
	/*//linked to browse button
	private void exisitngTagsDropdownPressed() {
		
	}*/
	
	private void deleteRepeated() {
		if(taglist!=null) {
			for(int i =0; i< taglist.size(); i++) {
				taglist.set(i, taglist.get(i).toLowerCase());
			}
			}
		Set<String> setx = new HashSet<>(taglist);
		taglist.clear();
		taglist.addAll(setx);
		
	}
	
	/**
	 * Starts the AddPhoto to Album stage
	 * @param mainStage - mainStage
	 * @throws IOException - throws IO exception
	 */
	public void start(Stage mainStage) throws IOException { 
		
		
		
		//ln(AlbumsController.getSelectedAlbum().getAlbumName());
		
		
		taglist = new ArrayList<String> ();
		taglist.add("Location");
		taglist.add("Person");
		taglist.add("Event");
		////ln("here in start tag 1");
		for(Album a: AlbumsController.getCurrentUser().getAlbums()) {
			for(Photo p: a.getPhotos()) {
				if(p.getTags()!=null) {
					////ln("here in start tag 2");

					for(Tag t: p.getTags()) {
						////ln("here in start tag 3");
								taglist.add(t.getTagName());
							
					}
					
				}
			}
		}

		deleteRepeated();
		
		captionfield.setDisable(true);
		tagView.setDisable(true);
		exisitingvalue.setDisable(true);
		newname.setDisable(true);
		newvalue.setDisable(true);
		

		
	}
		
		
		//selectedAlbum = selectedalbum;


	

	private static boolean isValidFile(String pathname) {
	    //ln("pathname: " + pathname);
		
		try {
	        Paths.get(pathname);
	    } catch (InvalidPathException | NullPointerException ex) {
	    	//ln("in catch block");
	        return false;
	    }
	    
	    String filetype = "";

	    int i = pathname.lastIndexOf('.');
	    if (i > 0) {
	        filetype = pathname.toLowerCase().substring(i+1);
	    }
	    
	    if (!(filetype.equals("png")) && !(filetype.equals("gif")) && !(filetype.equals("jpeg")) && !(filetype.equals("bmp")) && !(filetype.equals("jpg"))) {
	    	//ln("file type");
	    	//ln(filetype);
	    	return false;
	    }
	    
	    return true;
	}
	
	
	
}	

