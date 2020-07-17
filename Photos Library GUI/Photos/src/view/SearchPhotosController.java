/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package view;

import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Calendar;
import java.util.ArrayList;
import java.time.LocalDate;
import model.*;
import javafx.event.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.*;
import java.io.IOException;
import java.text.SimpleDateFormat; 

/**
 * 
 * Loads the user to the Search for Photos screen that allows the user to search for photos based on tags and dates. It allows for a no conjunction, and, or search with tags
 * If search is valid, it loads to search result screen, if search is invalid, it loads to the User's List of Album screen
 *
 */
public class SearchPhotosController {
		
	@FXML
	private DatePicker beginDate;
	
	@FXML
	private DatePicker endDate;

    @FXML
    private ListView<String> TagListView1;
    
    @FXML
    private ListView<String> TagListView2;

	
	@FXML
	private Button cancel;
	
	@FXML
	private Button ok; 
	
	@FXML
	private Button quit;
	
	@FXML
	private Button logout;
	
	
	@FXML 
	private TextField tagvaluefield1;
	
	@FXML 
	private TextField tagvaluefield2;
	
	@FXML
	private RadioButton noConjunction;

	@FXML
	private RadioButton AND;
	
	@FXML
	private RadioButton OR;
	
	@FXML
	private RadioButton dates;

	@FXML
	private RadioButton tags;
	
	@FXML
	private RadioButton tagdate;
	
	@FXML
	private ToggleGroup toggroup1;
	
	@FXML
	private ToggleGroup toggroup2;
	
	private ArrayList<String> tagsarraylist;
	
	static ArrayList<Photo> searchresult;
	
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
	 * Checks if the search criteria input is correct, if its correct then loads the user to search result screen
	 * If incorrect then user is allowed to try again
	 */
	public void okPressed() {
		
		if (searchresult!=null) {
			searchresult.clear();
		}
		
		
		RadioButton criteria = (RadioButton) toggroup2.getSelectedToggle();
		
		if(criteria==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Invalid Search");
			alert.setContentText("No Search criteria selected, try again");
			
			alert.showAndWait();
			
			//backtoAlbumsView(cancel);
			return;
		}
		else if(criteria.equals(dates)) {
			dateSearch();
			deleteRepeatedPhotos();
		}
		else if (criteria.equals(tags)) {
			
			boolean dt = false;
			RadioButton selectedRadioButton = (RadioButton) toggroup1.getSelectedToggle();
			
			if(selectedRadioButton.equals(noConjunction)){
				noConjImplement(dt);
			}
			
			else if (selectedRadioButton.equals(AND)) {
				//ln("going to and method");
				andImplement(dt);
			}
			else if (selectedRadioButton.equals(OR)) {
				//ln("going to or method");
				orImplement(dt);
			}
			
			deleteRepeatedPhotos();
			
		}
		
		else if (criteria.equals(tagdate)) {
			
			boolean dt = true;
			RadioButton selectedRadioButton = (RadioButton) toggroup1.getSelectedToggle();
			if(selectedRadioButton.equals(noConjunction)){
				noConjImplement(dt);
			}
			
			else if (selectedRadioButton.equals(AND)) {
				//ln("going to and method");
				andImplement(dt);
			}
			else if (selectedRadioButton.equals(OR)) {
				//ln("going to or method");
				orImplement(dt);
			}
			
			
			
		}
		
		
		if (searchresult.size()<=0) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("ALERT");
		alert.setHeaderText("No Results");
		alert.setContentText("No photos found which match that search.");
		alert.showAndWait();   
		return;
		}
		
		
		
		
		
		for(int i = 0; i<searchresult.size(); i++) {
			//(searchresult.get(i).getCaption());
		}
		
	
		
		
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchResult.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			SearchResultController controller = loader.<SearchResultController>getController();
			Scene scene = new Scene(parent);
			
			// ok is the button pressed action event
			Stage stage = (Stage) ((Node) ok).getScene().getWindow();
			controller.start(stage);
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}	
	//go to albums controller and load corresponding fxml
	//backtoAlbumsView(ok);	
	}
	
	/**
	 * All actions in textfields and browsefields are abandoned, no search is done
	 * and user is sent back to the screen with user's list of albums
	 */
	public void cancelPressed() {
		
		if (searchresult!=null) {
			searchresult.clear();
		}	
	backtoAlbumsView(cancel);	
	}
	
	/**
	 * Starts the screen where user can search for photos based on 
	 * @param mainStage - mainStage
	 * @throws IOException - throws IO exception
	 */
	public void start(Stage mainStage) throws IOException { 
		
		//if (searchresult!=null) {
		//	searchresult.clear();
		//}
		
		searchresult = new ArrayList<Photo>();
		tagsarraylist = new ArrayList<String>();
		
		tagsarraylist.add("Location");
		tagsarraylist.add("Person");
		tagsarraylist.add("Event");
		ArrayList<Album> albumsarraylist = AlbumsController.getCurrentUser().getAlbums();
		
		for (int i=0; albumsarraylist.size()>i; i++) {
		ArrayList<Photo> photosarraylist = albumsarraylist.get(i).getPhotos(); //get the photos in every album
			if(photosarraylist!=null) {
			for (int j=0; photosarraylist.size()>j; j++) {
				ArrayList<Tag> tags = photosarraylist.get(j).getTags();
				if(tags!=null) {
				for(int k = 0; k < tags.size(); k++) {
						tagsarraylist.add(tags.get(k).getTagName());
					}
				}
				}
			}
		}
		deleteRepeated();
		TagListView1.setItems(FXCollections.observableArrayList(tagsarraylist));
		TagListView2.setItems(FXCollections.observableArrayList(tagsarraylist));
		

		beginDate.setDisable(true);
		endDate.setDisable(true);
		TagListView1.setDisable(true);
		TagListView2.setDisable(true);
		AND.setDisable(true);
		OR.setDisable(true);
		noConjunction.setDisable(true);
		tagvaluefield1.setDisable(true);
		tagvaluefield2.setDisable(true);
		
		

	}
	
	/**
	 * Method linked to dates only search criteria button and allows to input for date
	 */
	public void dates() {
		
		beginDate.setDisable(false);
		endDate.setDisable(false);
		TagListView1.setDisable(true);
		TagListView2.setDisable(true);
		AND.setDisable(true);
		OR.setDisable(true);
		noConjunction.setDisable(true);
		tagvaluefield1.setDisable(true);
		tagvaluefield2.setDisable(true);
	}

	/**
	 * Method linked to tags only search criteria button and allows to input for tags
	 */
	public void tags() {
		beginDate.setDisable(true);
		endDate.setDisable(true);
		TagListView1.setDisable(false);
		//TagListView2.setDisable(true);
		AND.setDisable(false);
		OR.setDisable(false);
		noConjunction.setDisable(false);
		tagvaluefield1.setDisable(false);
		//tagvaluefield2.setDisable(true);
		
	}
	/**
	 * Method linked to tags within a date range search criteria button and allows to input for dates and tags
	 */
	public void tagdate() {
		beginDate.setDisable(false);
		endDate.setDisable(false);
		TagListView1.setDisable(false);
		//TagListView2.setDisable(false);
		AND.setDisable(false);
		OR.setDisable(false);
		noConjunction.setDisable(false);
		tagvaluefield1.setDisable(false);
		//tagvaluefield2.setDisable(false);
		
	}
	private void andImplement(boolean date) {
		//ln("and method 1");
		if((tagvaluefield1.getText().trim().equals("")) || (tagvaluefield2.getText().trim().equals("")) ) {
			//ln("and method 2");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Search Incomplete");
			alert.setContentText("Tag input is invalid");
			
			alert.showAndWait();
			return;
		}
		/*
		if((!tagvaluefield1.getText().trim().equals("") || (!tagvaluefield2.getText().trim().equals(""))) && ((TagListView1.getSelectionModel().getSelectedItem()==null) || (TagListView2.getSelectionModel().getSelectedItem()==null))) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Searching using Dates without Tag attribute");
			alert.setContentText("Tag selection input invalid: Searching using Dates only, without Tag attribute");
			alert.showAndWait();
			//ln("and method 3");
			return;
		}
		*/
		
		Tag t1 = new Tag("","");
		Tag t2 = new Tag("","");
		if(TagListView1.getSelectionModel().getSelectedItem()!=null) {
			if(!tagvaluefield1.getText().trim().equals("") && !TagListView1.getSelectionModel().getSelectedItem().equals("")) {
				//ln("going to and method 2");
				t1.setTagName(TagListView1.getSelectionModel().getSelectedItem().trim());
				t1.setTagValue(tagvaluefield1.getText().trim());
			}
			}
			
			if(TagListView2.getSelectionModel().getSelectedItem()!=null) {
			if(!tagvaluefield2.getText().trim().equals("") && !TagListView2.getSelectionModel().getSelectedItem().equals("")) {
				//ln("going to and method 3");

				t2.setTagName(TagListView2.getSelectionModel().getSelectedItem().trim());
				t2.setTagValue(tagvaluefield2.getText().trim());
			}
			}
		
			ArrayList<Photo> result = new ArrayList<Photo>();
		ArrayList<Album> albumsarraylist = AlbumsController.getCurrentUser().getAlbums();
		
		if(albumsarraylist!=null) {
		//iterate through array of albums
		for (int i=0; albumsarraylist.size()>i; i++) {
			
			//get photos list from each album
			ArrayList<Photo> photosarraylist = albumsarraylist.get(i).getPhotos(); //get the photos in every album
			if(photosarraylist!=null) {
				
			for (Photo p : photosarraylist) {
				if(p.getTags()!=null) {
					for(Tag t : p.getTags()) {
						//ln("and method 4");
						boolean k = t.getTagName().equalsIgnoreCase(t1.getTagName());
						boolean l = t.getTagValue().equalsIgnoreCase(t1.getTagValue());
						boolean n = t.getTagValue().equalsIgnoreCase(t2.getTagValue());
						boolean m = t.getTagName().equalsIgnoreCase(t2.getTagName());
						
						if ((k&&l)) {
							//if((m&&n)) {
							//ln("and method 5");
							result.add(p);
							//ln("In AndImplement, Adding:  "+  p);
							//}
						}
					}
				}
				
				
				
			}
				
			}
		}
		}
		
		if(result!=null) {
			for (Photo p : result) {
				if(p.getTags()!=null) {
					for(Tag t : p.getTags()) {
						if((t.getTagName().equalsIgnoreCase(t2.getTagName()) && t.getTagValue().equalsIgnoreCase(t2.getTagValue()))) {
							searchresult.add(p);
						}
					}
				}
			}
		}
		
		//ln("Line 408, Before dateTagSearch" + searchresult);
		
		if(date) {
			datetagSearch();
		}
		
		//ln("Line 414, After dateTagSearch" + searchresult);
		
		deleteRepeatedPhotos();
		
		//ln("Line 418, After deleterepeated" + searchresult);
		//dateSearch();
		
		//return searchresult;

	}
	
	/**
	 * Method linked to the And conjunction for search criteria including two tags
	 */
	public void and() {
		TagListView2.setDisable(false);
		tagvaluefield2.setDisable(false);
		
				
	}
	
	private void orImplement(boolean date) {
		if((tagvaluefield1.getText().trim().equals("")) || (tagvaluefield2.getText().trim().equals("")) ) {
			//ln("going to or method 1");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Search Incomplete");
			alert.setContentText("Tag search input invalid");
			alert.showAndWait();
			return ;
		}

		
		Tag t1 = new Tag("","");
		Tag t2 = new Tag("","");
		if(TagListView1.getSelectionModel().getSelectedItem()!=null) {
		if(!tagvaluefield1.getText().trim().equals("") && !TagListView1.getSelectionModel().getSelectedItem().equals("")) {
			//ln("going to or method 2");
			t1.setTagName(TagListView1.getSelectionModel().getSelectedItem().trim());
			t1.setTagValue(tagvaluefield1.getText().trim());
		}
		}
		
		if(TagListView2.getSelectionModel().getSelectedItem()!=null) {
		if(!tagvaluefield2.getText().trim().equals("") && !TagListView2.getSelectionModel().getSelectedItem().equals("")) {
			//ln("going to or method 3");

			t2.setTagName(TagListView2.getSelectionModel().getSelectedItem().trim());
			t2.setTagValue(tagvaluefield2.getText().trim());
		}
		}
		

		
		ArrayList<Album> albumsarraylist = AlbumsController.getCurrentUser().getAlbums();
		
		if(albumsarraylist!=null) {
			
			////ln("going to or method 4");

			
		//iterate through array of albums
		for (int i=0; albumsarraylist.size()>i; i++) {
			//ln("going to or method 4");

			//get photos list from each album
			ArrayList<Photo> photosarraylist = albumsarraylist.get(i).getPhotos(); //get the photos in every album
			if(photosarraylist!=null) {
				////ln("going to or method 5");

			for (Photo p : photosarraylist) {
				//ln("going to or method 6");

				if(p.getTags()!=null) {
					for(Tag t : p.getTags()) {
						//ln("going to or method 7");
						if ((t.getTagName().equalsIgnoreCase(t1.getTagName()) && t.getTagValue().equalsIgnoreCase(t1.getTagValue())) || ((t.getTagName().equalsIgnoreCase(t2.getTagName())) && (t.getTagValue().equalsIgnoreCase(t2.getTagValue())))) {
						//if (t.equals(t1) && t.equals(t2)) {
							searchresult.add(p);
							//ln("or method 8");
							////ln(p);
						}
					}
				}
				
				
				
			}
				
			}
		}
		}
		
		
		if(date) {
			datetagSearch();
		}
		
		deleteRepeatedPhotos();
		
		//return searchresult;
	}
	
	/**
	 * Method linked to the Or conjunction for search criteria including two tags
	 */
	public void or() {
		TagListView2.setDisable(false);
		tagvaluefield2.setDisable(false);
	}
	
	private void noConjImplement(boolean date) {

		if((tagvaluefield1.getText().trim().equals(""))) {
			return ;
		}
		
		if(!(tagvaluefield1.getText().trim().equals("")) && (TagListView1.getSelectionModel().getSelectedItem()==null)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Search Incomplete");
			alert.setContentText("Tagname not selected from list");
			alert.showAndWait();
			return ;
		}
		
		Tag t1 = new Tag("","");
		if(!tagvaluefield1.getText().trim().equals("") && !TagListView1.getSelectionModel().getSelectedItem().equals("")) {
			t1.setTagName(TagListView1.getSelectionModel().getSelectedItem().trim());
			t1.setTagValue(tagvaluefield1.getText().trim());
		}
		
		ArrayList<Album> albumsarraylist = AlbumsController.getCurrentUser().getAlbums();
		
		if(albumsarraylist!=null) {
		//iterate through array of albums
		for (int i=0; albumsarraylist.size()>i; i++) {
			
			//get photos list from each album
			ArrayList<Photo> photosarraylist = albumsarraylist.get(i).getPhotos(); //get the photos in every album
			if(photosarraylist!=null) {
				
			for (Photo p : photosarraylist) {
				if(p.getTags()!=null) {
					for(Tag t : p.getTags()) {
						if ((t.getTagName().equalsIgnoreCase(t1.getTagName())) && (t.getTagValue().equalsIgnoreCase(t1.getTagValue()))) {
							searchresult.add(p);
							////ln(p);
						}
					}
				}
				
				
				
			}
				
			}
		}
		}
		
		if(date) {
			datetagSearch();
		}
		
		deleteRepeatedPhotos();
		
		//return searchresult;
	}
	
	/**
	 * Method linked to the No conjunction for search criteria button, allowing to search with only one tag
	 */
	public void noConj() {
		TagListView2.setDisable(true);
		tagvaluefield2.setDisable(true);
		
			
	}
	
	private void datetagSearch() {
		if(beginDate.getValue() == null || endDate.getValue() == null) {
			return;
		}
			//local dates from calendar UI input
			LocalDate beginlocaldate = beginDate.getValue();
			LocalDate endlocaldate = endDate.getValue();
			
			//convert local dates to dates
			java.util.Date begindate = java.sql.Date.valueOf(beginlocaldate);
			java.util.Date enddate = java.sql.Date.valueOf(endlocaldate);
			
			//date.setTime(date)
			
			//convert input to Calendar objects
			Calendar begincal = Calendar. getInstance();
			begincal.set(Calendar.MILLISECOND,0);
			begincal.setTime(begindate);
			//begincal.set(beginlocaldate.getYear(), beginlocaldate.getMonthValue(), beginlocaldate.getDayOfMonth());
			
			Calendar endcal = Calendar. getInstance();
			endcal.set(Calendar.MILLISECOND,0);
			endcal.setTime(enddate);
			endcal.set(Calendar.HOUR,23);
			endcal.set(Calendar.MINUTE,59);
			endcal.set(Calendar.SECOND,59);
			//endcal.set(endlocaldate.getYear(), endlocaldate.getMonthValue(), endlocaldate.getDayOfMonth());
		
			//ln(begincal.getTime());
			//ln(endcal.getTime());
			
			
			//ArrayList<Album> albumsarraylist = AlbumsController.getCurrentUser().getAlbums();
			
			//if(albumsarraylist!=null) {
			//iterate through array of albums
			//for (int i=0; albumsarraylist.size()>i; i++) {
				
				//get photos list from each album
				//ArrayList<Photo> photosarraylist = albumsarraylist.get(i).getPhotos(); //get the photos in every album
				if(searchresult!=null) {
				
				Iterator<Photo> itr = searchresult.iterator(); 
				while (itr.hasNext()) {
					Photo searchresult = itr.next(); 
					if ((searchresult.getDate().getTime().compareTo(begincal.getTime())<0) || (searchresult.getDate().getTime().compareTo(endcal.getTime())>0)) { 
						itr.remove();
					}
				}

								
				
				
				
				
			}
			
			
			//deleteRepeatedPhotos();
	}
	
	 private void dateSearch() {
		
			if(beginDate.getValue() == null || endDate.getValue() == null) {
				return;
			}
				//local dates from calendar UI input
				LocalDate beginlocaldate = beginDate.getValue();
				LocalDate endlocaldate = endDate.getValue();
				
				//convert local dates to dates
				java.util.Date begindate = java.sql.Date.valueOf(beginlocaldate);
				java.util.Date enddate = java.sql.Date.valueOf(endlocaldate);
				
				//date.setTime(date)
				
				//convert input to Calendar objects
				Calendar begincal = Calendar. getInstance();
				begincal.set(Calendar.MILLISECOND,0);
				begincal.setTime(begindate);
				//begincal.set(beginlocaldate.getYear(), beginlocaldate.getMonthValue(), beginlocaldate.getDayOfMonth());
				
				Calendar endcal = Calendar. getInstance();
				endcal.set(Calendar.MILLISECOND,0);
				endcal.setTime(enddate);
				endcal.set(Calendar.HOUR,23);
				endcal.set(Calendar.MINUTE,59);
				endcal.set(Calendar.SECOND,59);
				//endcal.set(endlocaldate.getYear(), endlocaldate.getMonthValue(), endlocaldate.getDayOfMonth());
			
				//ln(begincal.getTime());
				//ln(endcal.getTime());
				
				
				ArrayList<Album> albumsarraylist = AlbumsController.getCurrentUser().getAlbums();
				
				if(albumsarraylist!=null) {
				//iterate through array of albums
				for (int i=0; albumsarraylist.size()>i; i++) {
					
					//get photos list from each album
					ArrayList<Photo> photosarraylist = albumsarraylist.get(i).getPhotos(); //get the photos in every album
					if(photosarraylist!=null) {
						
					for (Photo p : photosarraylist) { //for every photo, check date
						
						
						//if the photo is between the two specified dates, add to search result
						
						if ((p.getDate().getTime().compareTo(begincal.getTime())>=0) && (p.getDate().getTime().compareTo(endcal.getTime())<=0)) {
							searchresult.add(p);
							////ln(p);
						}
					}
						
					}
				}
				}
				
				deleteRepeatedPhotos();
				
				
	}
	
	private void deleteRepeated() {
		//this is fine
		if(tagsarraylist!=null) {
			for(int i =0; i< tagsarraylist.size(); i++) {
				tagsarraylist.set(i, tagsarraylist.get(i).toLowerCase());
			}
			}
		Set<String> setx = new HashSet<>(tagsarraylist);
		tagsarraylist.clear();
		tagsarraylist.addAll(setx);
			
	}
	
	private void deleteRepeatedPhotos() {
		
		//may need to edit this later, depending on paramters for seach
		// and duplicate photos
		Set<Photo> setx = new HashSet<>(searchresult);
		searchresult.clear();
		searchresult.addAll(setx);
	}
	
	
	
	
	
	 void backtoAlbumsView(Button b) {
		//go to albums controller and load corresponding fxml
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Albums.fxml"));
		try{
			Parent parent = (Parent) loader.load();
			AlbumsController controller = loader.<AlbumsController>getController();
			Scene scene = new Scene(parent);
			
			// ok is the button pressed action event
			Stage stage = (Stage) ((Node) b).getScene().getWindow();
			controller.start(stage, AlbumsController.getCurrentUser());
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.show();
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	 static ArrayList<Photo> getSearchResult(){
		return searchresult;
	}
	
	
	
}	

