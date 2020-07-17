/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package view;


import java.text.SimpleDateFormat;
import java.util.Date;
import model.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.*;
import java.io.IOException; 
import view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;




/**
 * 
 * Handles the Login Screen that allows logic for the admin, stock user or a regular user and loads to their individual screens
 * Serializes each user's data and saves it in binary form
 *
 */
public class LoginController {

	//ObservableList<Album> obsList = FXCollections.observableArrayList();
	static ArrayList<User> users = new ArrayList<User>();
	private final String path = "data/data.dat";
	//public static final String storeDir = "dat";
	//public static final String storeFile = "data.dat";
	//User stockUser;
	@FXML
	private Button go; 
	
	@FXML
	private Button quit; 
	
	@FXML 
	private TextField username;
	
	//User user;
	
	
	/**Starts the Login screen that allows user input for username
	 * 
	 * @param mainStage - mainStage
	 * @throws IOException - throws IO exception
	 */
	
	public void start(Stage mainStage) throws IOException {          


	}
	
	 static void saveData(ArrayList<User> users) {
		try {
			FileOutputStream fos = new FileOutputStream("data/data.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//("here2");
			
			//int size = users.size();
			//ArrayList<User> temp = new ArrayList<User>();
			////("here in temp" + users.size() + "  " + temp.size());
			//int i = 0;
			/*for(User u: users) {
				temp.add(i, u);
				i++;
			}
			//ln(temp.size());
			
			*/
			oos.writeObject(users);
			//("writing in temp");
			oos.close();
			fos.close();
		} catch (Exception exception) {
			//ln("Got exception");
			exception.printStackTrace();
		}
	}
	
	/**
	 * Quits the application 
	 */
	public void quitPressed() {
		System.exit(0);
	}
	
	 static ArrayList<User> getUsers(){
		return users;
	}
	
	 static void removeUser(User u){
		 users.remove(u);
	}
	
	 static void addUser(User u){
		 users.add(u);
	}
	
	/**
	 * Checks if username is valid, if valid, then takes the user to their Albums Screen which shows a list of albums
	 * Special username stock is preloaded with a stock album
	 * Special username admin can manage the list of all users and is loaded to the admin dashboard screen
	 */
	public void goPressed() {
		
		String uName = username.getText().trim();
		
		File data = new File(path);
		
	if (!data.exists() || !data.isFile()) {
		//if(uName.contentEquals("stock")){
			try {
				data.createNewFile();
				//("here7");
				////ln("file creation " );
				Album stockAlbum = new Album("stock");
				File photo;
				for (int current = 1; current <= 5; current++) {
					photo = new File("data/stock/img" + Integer.toString(current) + ".jpg");

					if (photo != null) {
						
						Image image = new Image(photo.toURI().toString());
						String name = photo.getName();
						Calendar date = Calendar.getInstance();
						
						
						
						date.set(Calendar.MILLISECOND, 0);
						Date d = new Date(photo.lastModified());
						date.setTime(d);
						date.set(Calendar.MILLISECOND, 0);
						
						//ln("line 136: Login Controller");
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						//ln(sdf.format(date.getTime()));
						
						//date.setTimeInMillis(photo.lastModified());
						Photo newPhoto = new Photo(name, image, date);

						stockAlbum.addPhoto(newPhoto);
						//("here3");
					}
				}

				User stockUser = new User("stock");
				
				//("here10");
				//if(stockUser.getAlbums()!=null)
				stockUser.addAlbumToUser(stockAlbum);
				//ln("here11");
				users.add(stockUser);
				//ln("here12" + users.size());
				

				saveData(users);
				//("here1");
			} catch (Exception exception) {
				exception.printStackTrace();
			}
	}

		
		//ln("Go button pressed");
		//ln(username.getText().trim());
		
		String loginname = username.getText().trim();
			
		try {
			
			//commenting out till fis.close will take you to admin page
			FileInputStream fis = new FileInputStream(path);
			//("here6");
			ObjectInputStream ois = new ObjectInputStream(fis);
			//("here7.1");
			users = (ArrayList<User>) ois.readObject();
			/*List<User> list = (List<User>) ois.readObject(); 
			//("here8");
			users = FXCollections.observableArrayList(list);
			//("here9");
			*/
			ois.close();
			
			fis.close();
			
			
			
			User user = null;

			for (User currentUser : users) {
				//if(currentUser!=null) {
				if (currentUser.getUsername().equals(uName)) {
					user = currentUser;

				//}
				}
			}
			

			if (uName.equals("admin") || user != null) {
				FXMLLoader loader;
				Parent parent;

				if (uName.equals("admin")) {
					loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
					try {
						parent = (Parent) loader.load();
						AdminController controller = loader.<AdminController>getController();
						Scene scene = new Scene(parent);
						Stage stage = (Stage) ((Node) go).getScene().getWindow();
						controller.start(stage);
						stage.setScene(scene);
						stage.centerOnScreen();
						stage.show();
					} 
					catch(Exception e) {
						e.printStackTrace();
					}
		
				}
				
				//else, go to albums controller and load corresponding fxml
				else {
					loader = new FXMLLoader(getClass().getResource("Albums.fxml"));
					try{
						parent = (Parent) loader.load();
						AlbumsController controller = loader.<AlbumsController>getController();
						Scene scene = new Scene(parent);
						
						// go is the button pressed action event
						Stage stage = (Stage) ((Node) go).getScene().getWindow();
						controller.start(stage, user);
						stage.setScene(scene);
						stage.centerOnScreen();
						stage.show();
					}
					catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			}
			
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Login Error");
				alert.setHeaderText("User not found.");
				alert.setContentText("This user does not exist.");

				alert.showAndWait();
			}
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
				
	}
	
}	

	
	
	
