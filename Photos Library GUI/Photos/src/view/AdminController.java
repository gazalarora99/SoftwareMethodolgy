/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package view;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.User;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.*;
import java.io.IOException; 
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

/**
 * Loads the Administrator Dashboard where admin can add, delete and see a list of users.
 * 
 *
 */
public class AdminController {
	
	@FXML
	private Button logout; 
	
	@FXML
	private Button quit;
	
	@FXML
	private Button createuser;	
	
	@FXML
	private Button deleteuser; 
	
	@FXML
	private Button listusers;	
	
	@FXML
	private TextField usertextfield; 
	
	
	
	//need to create user object first
	@FXML
	private ListView<User> userslistview;	
	
	/**
	 * Quits the application 
	 */
	public void quitPressed() {
		System.exit(0);
	}
	
	/**
	 * Logout ends admin's session, loads back to the Login page
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
	 * Allows the admin to create a new user if it doesn't already exist
	 * @return boolean
	 */
	public boolean createUserPressed() {
		String userid = usertextfield.getText().trim();
		if(!userid.equals("")) {
			
			for(User u: LoginController.getUsers()) {
				if(u.getUsername().equals(userid)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Incorrect Username");
					alert.setHeaderText("Please try again");
					alert.setContentText("A User already exists with same username");
					userid = "";
					usertextfield.setText("");
					alert.showAndWait();
					
					return false;
				}
			}
			
			User newUser = new User(userid);
			LoginController.addUser(newUser);
			usertextfield.setText("");
			if(!userslistview.isDisabled()) {
				userslistview.setItems(FXCollections.observableArrayList(LoginController.getUsers()));
			}
			LoginController.saveData(LoginController.users);
			return true;
		}
		return false;
		
	}
	
	/**
	 * Allows the admin to delete the selected user and all its contents
	 */
	public void deleteUserPressed() {
		//not sure about arraylist for following
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("Delete User");
		alert.setContentText("Do you want to delete the selected User and its contents?");
		
		Optional<ButtonType> confirm = alert.showAndWait();
		if (confirm.get() == ButtonType.OK) {
			LoginController.removeUser(userslistview.getSelectionModel().getSelectedItem());
			//userslistview.setDisable(false);
			userslistview.setItems(FXCollections.observableArrayList(LoginController.getUsers()));
			LoginController.saveData(LoginController.users);
		}
	}
	/**
	 * Shows the list of all users present
	 */
	public void listUsersPressed() {
		deleteuser.setDisable(false);
		userslistview.setDisable(false);
		
		userslistview.setItems(FXCollections.observableArrayList(LoginController.users));
		userslistview.getSelectionModel().select(0);
	}
	
	
	/**
	 * Starts and loads the Admin Dashboard screen where admin can manage users
	 * @param mainStage - mainStage
	 * @throws IOException - throws IO exception
	 */
	public void start(Stage mainStage) throws IOException { 
		//ln("in start method");
		createuser.setDisable(false);
		listusers.setDisable(false);
		usertextfield.setDisable(false);
		deleteuser.setDisable(true);
		userslistview.setDisable(true);
		////ln(LoginController.users.size());
		////ln(FXCollections.observableArrayList(LoginController.users));
		//userslistview.setItems(FXCollections.observableArrayList(LoginController.users));
		////ln("before obs list");
		//ObservableList<User> u = FXCollections.observableArrayList(LoginController.getUsers());
		////ln(u.isEmpty());
		////ln(u);
		//userslistview.setItems(u);
		
	
	}

	

	
	
}	

