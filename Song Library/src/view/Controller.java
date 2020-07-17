/*
 * Whitney Alderman and Gazal Arora
 * 
 */
package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.value.ObservableValue;
//////////////////////////ADDED THIS
import javafx.scene.input.MouseEvent;
import java.io.IOException; 

/////////
public class Controller {

	@FXML         
	ListView<Song> listView;
	
	@FXML
	Button add; 
	
	@FXML
	Button edit;
	
	@FXML
	Button delete;
	
	@FXML 
	Label songDetails;
	
	@FXML 
	Label songName;
	
	@FXML 
	Label artist;
	
	@FXML 
	Label album;
	
	@FXML 
	Label year; 
	
	@FXML 
	TextField songField;
	
	@FXML 
	TextField artistField; 
	
	@FXML 
	TextField albumField;
	
	@FXML 
	TextField yearField;
	
	@FXML 
	TextField addSongField;
	
	@FXML 
	TextField addArtistField; 
	
	@FXML 
	TextField addAlbumField;
	
	@FXML 
	TextField addYearField;
	
	@FXML 
	public void userSelection(MouseEvent mouse) throws IOException
	{
	displaydetails();
	}

	ObservableList<Song> songList;  
	
	

	public void start(Stage mainStage) throws IOException {                

		File list = new File ("src/songs3.txt");
		songList = FXCollections.observableArrayList();
		//read from text file while app opens
		if(list.exists() && !list.isDirectory()) {
			Scanner sc = new Scanner(list);
			
			while (sc.hasNextLine()) {
				
				String inputline = sc.nextLine();
				String[] str = inputline.split(",");
				Song s = new Song(str[0], str[1]);
				if(str.length==3) { 
					s.album = str[2];
				}
				else if (str.length==4) {
					s.album = str[2];
					s.year = str[3];
				}
				songList.add(s);
				
			}
			songList.sort(new SongCompare());
		
			sc.close();
		}
		
		
	
		listView.setItems(songList);
		
		
		
		 // default is the first item selected
		listView.getSelectionModel().select(0);
		
		
		displaydetails();
		
       //write to text file on closing application
		mainStage.setOnCloseRequest(event -> {
			PrintWriter write;
			try {
			File file = new File("src/songs3.txt");
			file.createNewFile();
			write = new PrintWriter(file);
			for(int i = 0; i < songList.size(); i++) {
				write.println(songList.get(i).getSongName() + "," + songList.get(i).getArtist() + ","  + songList.get(i).getAlbum() + "," + songList.get(i).getYear());
			}
			write.close();
			}
			catch (Exception e){ 
				e.printStackTrace();
			}
	});
	}
	

	
	//method tied to delete button to delete a song
	public void delete() throws IOException{
		//Song selected = listView.getSelectionModel().getSelectedItem();
		/////////////
		if(songList.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Empty Library");
			alert.setContentText("The Song Library is empty, nothing to delete");
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("ALERT");
			alert.setHeaderText("Are you sure you want to delete this song?");
			
			Optional<ButtonType> confirm = alert.showAndWait();
			
			if (confirm.get() == ButtonType.OK) {
				
				int selectedindex = listView.getSelectionModel().getSelectedIndex();
				songList.remove(selectedindex);
				

				if(songList.isEmpty()) {
					songDetails.setText("");
					
					songField.setText("");
					
					artistField.setText("");
					
					albumField.setText("");
					
					yearField.setText("");
				}
				
				
				
				else if(songList.size()==1) {
					listView.setItems(songList); 
					listView.getSelectionModel().select(0);
					displaydetails();
					songField.setText(songList.get(0).getSongName());
					
					artistField.setText(songList.get(0).getArtist());
					
					albumField.setText(songList.get(0).getAlbum());
					
					yearField.setText(songList.get(0).getYear());
				}
				
				
				
				else if (selectedindex == songList.size()) {
					
					//System.out.println("in less than" + selectedindex + " " + songList.size());
					
					int n = selectedindex - 1 ;
					
					songList.sort(new SongCompare());
					
					listView.setItems(songList); 
					
					listView.getSelectionModel().select(n);
					
					displaydetails();
					
					songField.setText(songList.get(n).getSongName());
					
					artistField.setText(songList.get(n).getArtist());
					
					albumField.setText(songList.get(n).getAlbum());
					
					yearField.setText(songList.get(n).getYear());
				}
				
				else {
				//	System.out.println("in more than");
					int n = selectedindex;
					songList.sort(new SongCompare());
					listView.setItems(songList); 
					
					listView.getSelectionModel().select(n);
					
					displaydetails();
					
					songField.setText(songList.get(n).getSongName());
					
					artistField.setText(songList.get(n).getArtist());
					
					albumField.setText(songList.get(n).getAlbum());
					
					yearField.setText(songList.get(n).getYear());
				
				}

			}
			else {return;}
		}
		
		
			
	///////	
		
		
	}

	
	//Method tied to Add button to add a song
	public void addSong() throws IOException {
		
		
		String songname = addSongField.getText().trim();
		String artist = addArtistField.getText().trim();
		String album = addAlbumField.getText().trim();
		String year = addYearField.getText().trim();
		
		if (!year.equals("")) {
		int c = checkYear(year);
		
	    if (c<0){
	    	
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Incorrect Format");
			alert.setContentText("Year must be a positive integer and less than or equal to 2020");
			alert.showAndWait();
			addSongField.setText("");
			addArtistField.setText("");
			addAlbumField.setText("");
			addYearField.setText("");
			return;
	    	
	    }
		}
		
		if(songname.compareTo("")==0 || artist.compareTo("")==0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Need More Song Information");
			alert.setContentText("New song must have atleast a name and artist's name");
			alert.showAndWait();
			addSongField.setText("");
			addArtistField.setText("");
			addAlbumField.setText("");
			addYearField.setText("");
			return;
		}
		else {
		
		Song s = new Song(songname, artist, album, year);
		
				if(checkDuplicate(songname, artist)==0) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("ALERT");
					alert.setHeaderText("Duplicate Song");
					alert.setContentText("This song already exists in the library");
					alert.showAndWait();
					addSongField.setText("");
					addArtistField.setText("");
					addAlbumField.setText("");
					addYearField.setText("");
					return;
				}
				else {
			
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("ALERT");
					alert.setHeaderText("Are you sure you want to add this song?");
					
					Optional<ButtonType> confirm = alert.showAndWait();
					
						if (confirm.get() == ButtonType.OK) {
							songList.add(s);
							songList.sort(new SongCompare());
							listView.setItems(songList);
							
							int n = songList.indexOf(s);
							
							listView.getSelectionModel().select(n);
						
							displaydetails();
							
							songField.setText(songList.get(n).getSongName());
							
							artistField.setText(songList.get(n).getArtist());
							
							albumField.setText(songList.get(n).getAlbum());
							
							yearField.setText(songList.get(n).getYear());
							
							addSongField.setText("");
							addArtistField.setText("");
							addAlbumField.setText("");
							addYearField.setText("");
							
						}
			
						else { 
							addSongField.setText("");
							addArtistField.setText("");
							addAlbumField.setText("");
							addYearField.setText("");
							return; 
						}
		//SortedList songs = new SortedList <Song> (songList, new SongCompare());
					}
				}
		}
	
	public void edit() throws IOException {
		
		if(!songList.isEmpty()) {
			
			Song s = listView.getSelectionModel().getSelectedItem();
			
			int selectedindex = listView.getSelectionModel().getSelectedIndex();
			
			String songname = songField.getText().trim();
			String artist = artistField.getText().trim();
			String album = albumField.getText().trim();
			String year = yearField.getText().trim();
			
			if(songname.equals("") || artist.equals("")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("ALERT");
				alert.setHeaderText("Incorrect Format");
				alert.setContentText("A song must have a name and an artist");
				alert.showAndWait();
				songField.setText(s.getSongName());
				artistField.setText(s.getArtist());
				albumField.setText(s.getAlbum());
				yearField.setText(s.getYear());
				return;
			}
			
			if (!year.equals("")) {
			int c = checkYear(year);
			
		    if (c<0){
		    	
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("ALERT");
				alert.setHeaderText("Incorrect Format");
				alert.setContentText("Year must be a positive integer and less than or equal to 2020");
				alert.showAndWait();
				songField.setText(s.getSongName());
				artistField.setText(s.getArtist());
				albumField.setText(s.getAlbum());
				yearField.setText(s.getYear());
				return;
		    	
		    }
			}
			
			/*s.setName(songname);
			s.setArtist(artist);
			s.setAlbum(album);
			s.setYear(year);*/
			
				if(editDuplicate(songname, artist,selectedindex)==0) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("ALERT");
					alert.setHeaderText("Duplicate Song");
					alert.setContentText("A song with the same name and artist already exists in the library");
					alert.showAndWait();
					songField.setText(s.getSongName());
					artistField.setText(s.getArtist());
					albumField.setText(s.getAlbum());
					yearField.setText(s.getYear());
					return;
				}
				
				else {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("ALERT");
					alert.setHeaderText("Are you sure you want to edit this song?");
					
					Optional<ButtonType> confirm = alert.showAndWait();
					
					if (confirm.get() == ButtonType.OK) {
						
						s.setName(songname);
						s.setArtist(artist);
						s.setAlbum(album);
						s.setYear(year);
						
						songList.set(selectedindex, s);
						
						songList.sort(new SongCompare());
						
						listView.setItems(songList);
						
						int n = songList.indexOf(s);
					
						
						listView.getSelectionModel().select(n);
						
						displaydetails();
						
						songField.setText(songList.get(n).getSongName());
						
						artistField.setText(songList.get(n).getArtist());
						
						albumField.setText(songList.get(n).getAlbum());
						
						yearField.setText(songList.get(n).getYear());
						
					}
					else { 
						songField.setText(songList.get(selectedindex).getSongName());
						
						artistField.setText(songList.get(selectedindex).getArtist());
						
						albumField.setText(songList.get(selectedindex).getAlbum());
						
						yearField.setText(songList.get(selectedindex).getYear());
						return;
					}
				}
			
		}
		
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ALERT");
			alert.setHeaderText("Empty Library");
			alert.setContentText("No songs in the library to edit");
			alert.showAndWait();
			return;
		}
	}
	
	
	public void displaydetails() throws IOException {
		
	///////////	
		if(!songList.isEmpty()) {
			
		Song selected = listView.getSelectionModel().getSelectedItem();
		
		songField.setText(selected.getSongName());
		
		artistField.setText(selected.getArtist());
		
		albumField.setText(selected.getAlbum());
		
		yearField.setText(selected.getYear());
		
		if(selected.getAlbum().equals("") && selected.getYear().equals("")) {
			songDetails.setText("Song Details: "+selected.getSongName()+", "+selected.getArtist());
		}
		else if (selected.getAlbum().equals("") && !selected.getYear().equals("")) {
			songDetails.setText("Song Details: "+selected.getSongName()+", "+selected.getArtist()+ ", "+ selected.getYear());
		}
		else if (selected.getYear().equals("") && !selected.getAlbum().equals("")) {
			songDetails.setText("Song Details: "+selected.getSongName()+", "+selected.getArtist()+", "+ selected.getAlbum());	
		}
		else {
		songDetails.setText("Song Details: "+selected.getSongName()+", "+selected.getArtist()+", "+ selected.getAlbum()+ ", "+ selected.getYear());
		}
		}
		
		else {
			
			songDetails.setText("");
			
			songField.setText("");
			
			artistField.setText("");
			
			albumField.setText("");
			
			yearField.setText("");
			
			songDetails.setText("");
		}
	////////////	
	}
	
	
	
	
   /* listView.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends String> ov, String old_val, 
                String new_val) -> {
                    artistField.setText(new_val);
                    songField.setText();
        });
        stage.show();
	
	*/
	public int checkDuplicate(String name, String artist) {
		int dup = 1;
		for(int i = 0; i < songList.size(); i++) {
			
			Song s2 = songList.get(i);
			
			if((name.toUpperCase()).compareTo(s2.getSongName().toUpperCase()) == 0 && (artist.toUpperCase()).compareTo(s2.getArtist().toUpperCase()) == 0) {
				dup = 0;
				return dup;
				
			}
		
		}
		return dup;
	}

	
	public int editDuplicate(String name, String artist, int s) {
		int dup = 1;
		for(int i = 0; i < songList.size(); i++) {
			 if(i==s) {
				 continue;
			 }
			Song s2 = songList.get(i);
			
			if((name.toUpperCase()).compareTo(s2.getSongName().toUpperCase()) == 0 && (artist.toUpperCase()).compareTo(s2.getArtist().toUpperCase()) == 0) {
				dup = 0;
				return dup;
				
			}
		
		}
		return dup;
	}

	
class SongCompare implements Comparator<Song>{
		
		@Override
		public int compare(Song o1, Song o2) {
			// TODO Auto-generated method stub
			
			if((o1.getSongName().toUpperCase()).compareTo(o2.getSongName().toUpperCase()) != 0) {
				return ((o1.getSongName().toUpperCase()).compareTo(o2.getSongName().toUpperCase()));
				}
				return (o1.getArtist().toUpperCase()).compareTo(o2.getArtist().toUpperCase());
			
		}
	
		}

public static int checkYear(String year) {
	  try {
		  	if (Integer.parseInt(year)>0 && Integer.parseInt(year)<=2020) {
			    return Integer.parseInt(year);
		  	}
		  	else return -2; // Year needs to be a positive number

		  } catch (NumberFormatException e) {
		    return -1; //The entered number is not an int
		  }
		}



}


