
/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package model;

import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * 
 * User implements Seriablizable 
 * User consists on list of Albums and a Username with which they can Login
 * 
 *
 */
public class User implements Serializable{

	private String username;
	private static final long serialVersionUID = 1L;
	private ArrayList<Album> albumList= new ArrayList<Album> ();
	
	/**
	 * Constructor that allows administrator to set a username while instantiating
	 * @param username - username to be given to this user
	 */
	public User(String username) {
		this.username=username;
		
	}
	/**
	 * Returns username of this user
	 * @return String
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Sets a new username for this user
	 * @param newUserName - userName to be set to
	 */
	public void setUsername(String newUserName) {
		this.username=newUserName;
	}
	/**
	 * Adds an Album to User's list of Albums
	 * @param album - album to be added
	 */
	public void addAlbumToUser(Album album) {
		//("here5");
		albumList.add(album);
		
	}
	/**
	 * Removes an Album from User's list of Albums
	 * @param album - album to be removed
	 */
	public void removeAlbumFromUser(Album album) {
		albumList.remove(album);
		
	}
	
	/**
	 * Returns the list of Albums of this User
	 * @return ArrayList of Album objects
	 */
	public ArrayList<Album> getAlbums() {
		return albumList;
	}
	
	/**
	 * Sets a list of Albums for this user
	 * @param newAlbumList - album list to be assigned
	 */
	public void setAlbums(ArrayList<Album> newAlbumList) {
		this.albumList=newAlbumList;
	}
	
	/**
	 * Return's a String of User's username
	 * @return String
	 */
	public String toString() {
		return ("Username:" + username);
	}
	
}
