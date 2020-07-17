/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package view;



import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.*;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.*;
import java.io.IOException;
import java.util.Optional; 

/**
 * Handles the Selected Album View screen where thumbnails of all photos in the Album are displayed with their caption
 * Manages the actions that can be performed on a selected photo in the album
 * 
 *
 */
public class SelectedAlbumViewController {
	
	@FXML
	private Button backtoalbumlist;
	
	@FXML
	private Button logout; 	
	
	@FXML
	private Button displayphoto;
	
	@FXML
	private Button addphoto; 
	
	@FXML
	private Button removephoto;
	
	@FXML
	private Button recaptionphoto; 	
	
	@FXML
	private Button copyormove;
	
	@FXML
	private Button quit; 
	
	@FXML
	private Label albumname;
	
	@FXML
	private ImageView imageview;
	
	
    @FXML
    ListView<Photo> photosListView;	
	
	/*
    @FXML
    TableView<Photo> photosTableView;
    
    @FXML
    TableColumn<Photo, ImageView> photoThumbnailColumn;
    
    @FXML
    TableColumn<Photo, String> captionColumn;
	
	*/
	
	//ObservableList<Photo> photosList = FXCollections.observableArrayList();
	
	static Photo selectedPhoto;
	
	 //Callback<TableColumn<Photo, Image>, TableCell<Photo, Image>> cellFactory;
    
	
	/*
	
	@FXML
	public ListView<ImageView> imageList;
	
	
	ObservableList<ImageView> items = FXCollections.observableArrayList();
	
	//ListView<ImageView> listView = new listView();
	//ObservableList<ImageView> items = FXCollections.observableArrayList();
	//listView.setItems(items);
	
	@FXML
	public ListView<Photo> photosListView;
	
	ObservableList<Photo> obsList = FXCollections.observableArrayList();
	*/
	
	/**
	 * Linked to the back button to load back to the stage with list of Albums
	 */
	public void backToAlbumListPressed() {
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Albums.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			AlbumsController controller = loader.<AlbumsController>getController();
			Scene scene = new Scene(parent);
			
			// go is the button pressed action event
			Stage stage = (Stage) ((Node) backtoalbumlist).getScene().getWindow();
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
	 * Loads the stage with Photos Slideshow with the selected photo being displayed
	 */
	public void displayPhotoPressed() {
		
		
		//get selected photo and change static global variable in selectedalbumview controller
		Photo selectedphoto = photosListView.getSelectionModel().getSelectedItem();
			//if no selection, return
			if (selectedphoto == null) {
				
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("ALERT");
				alert.setHeaderText("Error");
				alert.setContentText("No photo selected to display");
				alert.showAndWait();
				
				return;
			}
		
		
		this.selectedPhoto = selectedphoto;
		
		
		
		//System.out.println("display pressed");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PhotosSlideshow.fxml"));
		//System.out.println("FXML loaded");
		try{
			Parent parent = (Parent) loader.load();
			PhotosSlideshowController controller = loader.<PhotosSlideshowController>getController();
			Scene scene = new Scene(parent);
			
			// go is the button pressed action event
			Stage stage = (Stage) ((Node) displayphoto).getScene().getWindow();
			controller.start(stage);
			//System.out.println("about to set scene");
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}	
		
	}
	
	/**
	 * Loads the user to the Add Photo screen where they can browse a photo from their system
	 */
	public void addPhotoPressed() {
		
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPhoto.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			AddPhotoToAlbumController controller = loader.<AddPhotoToAlbumController>getController();
			Scene scene = new Scene(parent);
			
			// go is the button pressed action event
			Stage stage = (Stage) ((Node) addphoto).getScene().getWindow();
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
	 * Removes the selected photo from the album
	 */
	public void removePhotoPressed() {
		
		
		//get selected photo and change static global variable in selectedalbumview controller
		Photo selectedphoto = photosListView.getSelectionModel().getSelectedItem();
		this.selectedPhoto = selectedphoto;
		
		
		//if no selection, return
		if (selectedphoto == null) {
			
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Delete");
			alert.setContentText("No photo selected to delete");
			alert.showAndWait();
			
			return;
		}
		
		
		
		
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("Delete Photo");
		alert.setContentText("Do you want to delete the selected photo?");
		Optional<ButtonType> confirm = alert.showAndWait();
		
		//if the user elects to delete this, remove photo from album 
		//and remove photo from observable list
		if (confirm.get() == ButtonType.OK) {

		AlbumsController.getSelectedAlbum().removePhoto(selectedPhoto);	
		AlbumsController.getSelectedAlbum().getPhotos().remove(selectedPhoto);
		LoginController.saveData(LoginController.users);
		}
		
		//display updated observable list
		photosListView.setItems(FXCollections.observableArrayList(AlbumsController.getSelectedAlbum().getPhotos()));
		
	}
	
	/**
	 * Allows the user to recaption the selected photo by loading them to the Recaption screen
	 */
	public void recaptionPressed() {
		
		
		//get selected photo and change static global variable in selectedalbumview controller
		Photo selectedphoto = photosListView.getSelectionModel().getSelectedItem();
		this.selectedPhoto = selectedphoto;
		
		
		//if no selection, return
		if (selectedphoto == null) {
			
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Error");
			alert.setContentText("No photo selected to caption");
			alert.showAndWait();
			
			return;
		}
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Recaption.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			RecaptionController controller = loader.<RecaptionController>getController();
			Scene scene = new Scene(parent);
			
			// go is the button pressed action event
			Stage stage = (Stage) ((Node) recaptionphoto).getScene().getWindow();
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
	 * Loads the user to the copy or move screen where they can copy/move the selected photo
	 */
	public void copyOrMovePressed() {
		
		Photo selectedphoto = photosListView.getSelectionModel().getSelectedItem();
		this.selectedPhoto = selectedphoto;
		
		if (selectedPhoto==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Error");
			alert.setContentText("Must select a photo to copy or move");
			alert.showAndWait();
			
			return;	
			
		}
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CopyMove.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			CopyOrMoveController controller = loader.<CopyOrMoveController>getController();
			Scene scene = new Scene(parent);
			
			// go is the button pressed action event
			Stage stage = (Stage) ((Node) copyormove).getScene().getWindow();
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
	public void QuitPressed() {
		
		System.exit(0);
		
	}
	
	
	/**
	 * Starts the screen with Selected Albums display that displays all the photos in the album
	 * @param mainStage - mainStage
	 * @throws IOException - throws IO exception
	 */
	public void start(Stage mainStage) throws IOException { 
		
		albumname.setText("Album: " + AlbumsController.getSelectedAlbum().getAlbumName());
		
		//selectedAlbum = selectedalbum;
		
		//System.out.println("this");
		
		//Album a = AlbumsController.getSelectedAlbum();
		//photosList.addAll(a.photos);
		
		
		// Initialize the album tableview with the three columns
       // photoThumbnailColumn.setCellValueFactory(cellData -> cellData.getValue().thumbnailViewProperty());
        //captionColumn.setCellValueFactory(cellData -> cellData.getValue().captionProperty());

        

            
		//photosListView.setItems(FXCollections.observableArrayList(selectedAlbum.getPhotos()));  
		

		
		/*
	photosListView.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
			
			@Override
			public ListCell<Photo> call(ListView<Photo> p) {
				
				return new PhotoCell();
			}
		});	
		*/
		
		
		
		
		photosListView.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {
			@Override
			public ListCell<Photo> call(ListView<Photo> photoList) {
				return new ThumbnailCell();
			}
		});
		
		
		
		
		
        // Add observable list data to the tableview
       photosListView.setItems(FXCollections.observableArrayList(AlbumsController.getSelectedAlbum().getPhotos()));
		
		//Photo selectedphoto = photosListView.getSelectionModel().getSelectedItem();
		//selectedPhoto = selectedphoto;
	
		
		//photosListView.setItems(obsList);
		
		
		
		

	}
	
	static Photo getSelectedPhoto() {
		
		return selectedPhoto;
		
	}
	
	
	
}	

