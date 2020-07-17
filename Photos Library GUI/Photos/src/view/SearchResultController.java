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
import javafx.util.Callback;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.*;
import java.io.IOException; 


/**
 * 
 * Search Result is displayed using this controller by displaying Photo thumbnails
 *
 */
public class SearchResultController {
	
	@FXML
	private Button quit;
	
	@FXML
	private Button logout; 	
	
	
	@FXML
	private Button close;
	
	@FXML
	private Button createalbumfromresult; 
	
	@FXML
	private ListView<Photo> photosListView;
	
	
	
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
	 * No Album is added to Current user's album list
	 * and user is sent back to Album View with list of all albums
	 */
	public void closePressed() {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchPhotos.fxml"));
				try{
					Parent parent = (Parent) loader.load();
					SearchPhotosController controller = loader.<SearchPhotosController>getController();
					Scene scene = new Scene(parent);
					
					// ok is the button pressed action event
					Stage stage = (Stage) ((Node) close).getScene().getWindow();
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
	 * Creates an album from the search result and adds it to User's list of albums
	 * and Loads user back to Albums View with list of albums
	 */
	public void createalbumPressed() {
		
		//ln("createalbumfromresultbutton");
		
		
		String searchResultAlbum = "Search Result";
		
		//iterates through exisiting albums, and enumerates Search Result (number) 
		//until there will be no duplicate names
		int i = 2;
		while (CreateAlbumController.checkForDuplicateAlbumNames(searchResultAlbum)==true) {
			searchResultAlbum = "Search Result " + "(" + Integer.toString(i) + ")";
			i++;
			
		}
		
		
		Album searchresalbum = new Album(searchResultAlbum);
		
		searchresalbum.setPhotos(SearchPhotosController.getSearchResult());
		searchresalbum.setnumPhotos(SearchPhotosController.getSearchResult().size());
		
		//ln(searchresalbum);
		
		
		//add album to user so it is stored and displays in album view later
		AlbumsController.getCurrentUser().addAlbumToUser(searchresalbum);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Albums.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			AlbumsController controller = loader.<AlbumsController>getController();
			Scene scene = new Scene(parent);
			
			// go is the button pressed action event
			Stage stage = (Stage) ((Node) createalbumfromresult).getScene().getWindow();
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
	 * Starts the search result stage and displays the result of the search criteria input by User
	 * @param mainStage - mainStage
	 * @throws IOException - throws IO exception
	 */
	public void start(Stage mainStage) throws IOException {    
		//SearchPhotosController.getSearchResult();
		
		//ln("heresrc");
		
		photosListView.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {
			@Override
			public ListCell<Photo> call(ListView<Photo> photoList) {
				return new ThumbnailCell();
			}
		});
		
		//System.out.println("heresrc2");
		//System.out.println(SearchPhotosController.getSearchResult());
        // Add observable list data to the tableview
       photosListView.setItems(FXCollections.observableArrayList(SearchPhotosController.getSearchResult()));
		
       if (SearchPhotosController.getSearchResult().isEmpty()) {
    	   /*
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("No Results");
			alert.setContentText("No photos found which match that search.");
			alert.showAndWait();   
			*/ 	   
    	   createalbumfromresult.setDisable(true);   
       }
       else {
    	   createalbumfromresult.setDisable(false); 
       }
       
       
       //ln("heresrc3");

	}
	
	
	
}	

