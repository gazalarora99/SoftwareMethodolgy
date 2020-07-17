/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package view;

import java.time.LocalDate;
import java.util.Optional;

import javafx.beans.property.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import model.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.*;
import java.io.IOException; 

/**
 * 
 * Loads the Screen with user's list of albums, showing their name, date range and number of photos
 * Handles action to create/delete/rename/display an album or search through all of user's albums
 *
 */
public class AlbumsController {
	
	@FXML
	private Button quit; 
	
	@FXML
	private Button open;	
	
	@FXML
	private Button create; 
	
	@FXML
	private Button rename;	
	
	@FXML
	private Button delete; 
	
	@FXML
	private Button search;	
	
	@FXML
	private Button logout; 
	
	@FXML
    ListView<Album> albumListView;
	
	/*	
	@FXML
    TableView<Album> albumTableView;
    
    @FXML
    TableColumn<Album, String> albumNameColumn;
    
    @FXML
    TableColumn<Album, Integer> numPhotosColumn;
    
    //TableColumn<Person, Number> ageColumn = new TableColumn<Person, Number>("Age");
    
    @FXML
    TableColumn<Album, LocalDate> dateRangeColumn;

    //public ObservableList<Album> albumList = FXCollections.observableArrayList();
	*/
    
    
    
    
     static Album selectedAlbum;
     static User currentUser;
	
    /**
	 * Quits the application 
	 */
	public void quitPressed() {
		System.exit(0); 
		
	}
	
	/**
	 * Checks if an album is selected, it yes, then loads to the screen which displays list of photos in that album
	 */
	public void openPressed() {
		
		//get selected album
		Album selectedalbum = albumListView.getSelectionModel().getSelectedItem();
		if (selectedalbum == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Error");
			alert.setContentText("Must select an album to open");
			alert.showAndWait();
			return;	
		}
	
		this.selectedAlbum = selectedalbum;
		
		//ln(selectedalbum.getAlbumName());  
	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectedAlbumView.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			SelectedAlbumViewController controller = loader.<SelectedAlbumViewController>getController();
			Scene scene = new Scene(parent);
			
			// go is the button pressed action event
			Stage stage = (Stage) ((Node) open).getScene().getWindow();
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
	 * Loads to the screen where user can create a new album
	 */
	public void createPressed() {
		//go to albums controller and load corresponding fxml
				FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateAlbum.fxml"));
				try{
					Parent parent = (Parent) loader.load();
					CreateAlbumController controller = loader.<CreateAlbumController>getController();
					Scene scene = new Scene(parent);
					
					// create is button pressed
					Stage stage = (Stage) ((Node) create).getScene().getWindow();
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
	 * Checks if an album is selected, it yes, then loads to the screen which allows to rename the selected album
	 */
	public void renamePressed() {
		
		//get selected album
		Album selectedalbum = albumListView.getSelectionModel().getSelectedItem();
		if (selectedalbum == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("ALERT");
				alert.setHeaderText("Error");
				alert.setContentText("Must select an album to rename");
				alert.showAndWait();
				return;	
		}
		this.selectedAlbum = selectedalbum;
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("RenameAlbum.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			RenameAlbumController controller = loader.<RenameAlbumController>getController();
			Scene scene = new Scene(parent);
			
			// ok is the button pressed action event
			Stage stage = (Stage) ((Node) rename).getScene().getWindow();
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
	 * Checks if an album is selected, it yes, then allows the user to delete the selected album with a confirmation pop-up
	 */
	public void deletePressed() {
		
		//get selection and store as object
		Album selectedalbum = albumListView.getSelectionModel().getSelectedItem();
		
	
		if (selectedalbum == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("ALERT");
				alert.setHeaderText("Error");
				alert.setContentText("Must select an album to delete");
				alert.showAndWait();
				return;	
		}
		
		this.selectedAlbum = selectedalbum;
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("Delete Album");
		alert.setContentText("Do you want to delete the selected album and its contents?");
		
		
		Optional<ButtonType> confirm = alert.showAndWait();
		
		//if the user elects to delete this, remove from observable list
		if (confirm.get() == ButtonType.OK) {
		
		getCurrentUser().getAlbums().remove(selectedalbum);
		LoginController.saveData(LoginController.users);
			
		}
		
		//display updated observable list
		albumListView.setItems(FXCollections.observableArrayList(getCurrentUser().getAlbums()));
		
		
	}
	
	/**
	 * Loads to the screen where user can search for photos throughout all the albums based on tags and dates
	 */
	public void searchPressed() {		
		
		//go to albums controller and load corresponding fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchPhotos.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			SearchPhotosController controller = loader.<SearchPhotosController>getController();
			Scene scene = new Scene(parent);
			
			// ok is the button pressed action event
			Stage stage = (Stage) ((Node) search).getScene().getWindow();
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
	 * 
	 * Starts the screen to display a user's list of albums and allowing multiple actions to a selected album
	 * 
	 * @param user - Current user for the Album list display belonging to that user
	 * @param mainStage - mainStage
	 * @throws IOException - throws IO exception
	 */
	public void start(Stage mainStage, User user) throws IOException {      
		
		//ln("here");
		currentUser = user;
		/*
		albumList.add(new Album("Vacation"));
		albumList.add(new Album("Birthday"));
		albumList.add(new Album("Graduation"));
		albumList.add(new Album("School"));
		albumList.add(new Album("Wedding"));
		albumList.add(new Album("Baby Shower"));
		albumList.add(new Album("Another Vacation"));
		albumList.add(new Album("Last Day"));
		albumList.add(new Album("First Day"));
		*/
		
		
		
		

		/*
		//create new album called cheetah
		Album a = new Album("Cheetah");
		
		//create cheetah photo using file name as string
		Photo pa = new Photo("file:data/stock/Cheetah.jpg");
		
		//set caption for the photo
		pa.setCaption("Caption for Cheetah");
		
		//add cheetah photo to cheetah album
		a.addPhoto(pa);
		
		//add the album to the observable list
		getCurrentUser().addAlbumToUser(a);
		
		//repeat for each album
		Album b = new Album("Leopard");
		Photo pb = new Photo("file:data/stock/img1.jpg");
		pb.setCaption("Caption for Leopard");
		b.addPhoto(pb);
		getCurrentUser().addAlbumToUser(b);
		
		Album c = new Album("Lion");
		Photo pc = new Photo("file:data/stock/img2.jpg");
		pc.setCaption("Caption for Lion");
		c.addPhoto(pc);
		getCurrentUser().addAlbumToUser(c);
		
		Album d = new Album("Panther");
		Photo pd = new Photo("file:data/stock/img3.jpg");
		pd.setCaption("Caption for Panther");
		d.addPhoto(pd);
		getCurrentUser().addAlbumToUser(d);
		
		Album e = new Album("Tiger");
		Photo pe = new Photo("file:data/stock/img3.jpg");
		pe.setCaption("Caption for Tiger");
		e.addPhoto(pe);
		getCurrentUser().addAlbumToUser(e);
		
		Album f = new Album("All Cats");
		f.addPhoto(pa);
		f.addPhoto(pb);
		f.addPhoto(pc);
		f.addPhoto(pd);
		f.addPhoto(pe);
		getCurrentUser().addAlbumToUser(f);
		*/
		
		LoginController.saveData(LoginController.users);
		
        /* no idea
		//albumList = LoginController.user.albumList;
		 * 
		 */
		
        // Initialize the album tableview with the three columns
        //albumNameColumn.setCellValueFactory(cellData -> cellData.getValue().albumNameProperty());
        //numPhotosColumn.setCellValueFactory(cellData -> cellData.getValue().numPhotosProperty().asObject());
        //dateRangeColumn.setCellValueFactory(cellData -> cellData.getValue().dateRangeProperty());
        
		//ln(FXCollections.observableArrayList(getCurrentUser().getAlbums()));
        
        // Add observable list data to the tableview
        albumListView.setItems(FXCollections.observableArrayList(getCurrentUser().getAlbums()));
        
        
	}

	static Album getSelectedAlbum() {
		
		return selectedAlbum;
		
	}
	
	static User getCurrentUser() {
		return currentUser;
	}
	
	
}	

