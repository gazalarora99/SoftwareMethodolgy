
/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package model;

import java.util.ArrayList;
import java.time.LocalDate;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Album implements Serializable 
 * An album constitutes a list of Photos, an earliest/latest date and a name
 *
 */
public class Album implements Serializable{
		
	//here
	private String albumName;
	private int numPhotos;
	private static final long serialVersionUID = 1L;
	private ArrayList<Photo> photos = new ArrayList<Photo>();
	
	//need to figure out how dates will work
	//private LocalDate dateRange; 
	
	
	private Calendar beginDate;
	private Calendar endDate;
	
	/**
	 * Default constructor for an Album
	 */
    public Album() {
        this("Unnamed");
    }
    /**
     * Constructor that sets album name to user provided String
     * @param albumName - user input for album name
     */
    public Album(String albumName) {
        this.albumName = albumName;
        this.numPhotos = 0;
        //this.dateRange = (LocalDate.of(2017, 6, 23));
    	
        //Date d = new Date(); //if no date specified, or creating new album, gives current date
		//this.beginDate = Calendar.getInstance();
		//this.beginDate.set(Calendar.MILLISECOND, 0);
		//this.beginDate.setTime(d);
		
		//this.endDate = Calendar.getInstance();
		//this.endDate.set(Calendar.MILLISECOND, 0);
		//this.endDate.setTime(d);
        
		setBeginDate();
		setEndDate();
		
        
    }
    /**
     * Returns Album's Name
     * @return String
     */
    public String getAlbumName() {
        return albumName;
    }
    /**
     * Sets Album name to given string parameter
     * @param albumname - new value for Album's name
     */
    public void setAlbumName(String albumname) {
        this.albumName=albumname;
    }
    
    /**
     * Returns number of photos in the album
     * @return int
     */
    public int getnumPhotos() {
        return numPhotos;
    }

    /**
     * Sets number of photos to given integer
     * @param numPhotos - given int to set number of Photos
     */
    public void setnumPhotos(int numPhotos) {
        this.numPhotos=numPhotos;
    }
    
    /*
    public LocalDate getDateRange() {
        return dateRange;
    }

    public void setDateRange(LocalDate dateRange) {
        this.dateRange=dateRange;
    }
    */
    
    /**
     * Adds a photo to the album
     * @param photo - photo to be added
     */
    public void addPhoto(Photo photo) {
    	setnumPhotos(numPhotos+1); //increment number of photos in album
    	photos.add(photo);
    	
    	//update date range of album
    	setBeginDate(); 
    	setEndDate();
    }
    /**
     * Removes given photo from the album
     * @param photo - photo to be removed
     */
    public void removePhoto(Photo photo) {
    	setnumPhotos(numPhotos-1); //increment number of photos in album
    	photos.remove(photo);
    	
    	//update date range of album
    	setBeginDate(); 
    	setEndDate();
    }
    
    /**
     * Returns an Array List of Photos in this album
     * @return ArrayList of Photo objects
     */
    public ArrayList<Photo> getPhotos(){
    	return photos;
    }
    
    /**
     * Assigns a list of photos to this album
     * @param photos - list of photos to be assigned
     */
    public void setPhotos(ArrayList<Photo> photos){
    	this.photos=photos;
    	//update date range of album
    	setBeginDate(); 
    	setEndDate();
    }
    
    /**
     * Returns a String of Album's name, number of Photos in it and it's date range
     * @return String
     */
	public String toString() {
		Date d = new Date(); //for now, gives current date
		Calendar cal = Calendar. getInstance();
		//Calendar begincal = this.beginDate;
		cal.set(Calendar.MILLISECOND, 0);
		cal.setTime(d);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		////ln(sdf.format(d));
		
		
		//calendar.getTime() returns a date
		
		setBeginDate();
		setEndDate();
		
		if (this.beginDate==null || this.endDate==null) {
			return ("Album Name: " + albumName +"\n" + "Number of Photos: " + numPhotos);
		}
		
		
		return ("Album Name: " + albumName +"\n" + "Number of Photos: " + numPhotos + "\n" + 
		"Date Range: " + sdf.format(beginDate.getTime()) + " - " + sdf.format(endDate.getTime()));
	}
	
	/**
	 * Goes through all photos in the album and sets earliest date as begin date of the album
	 */
	public void setBeginDate() {
		
		//this.beginDate.set(Calendar.MILLISECOND, 0);
		
		if (photos.size()==0) {
			return;
		}
		
		Calendar beginning = photos.get(0).getDate(); //set earliest date to first element
		
		for (int i = 1; photos.size()>i; i++) {
			if (photos.get(i).getDate().compareTo(beginning)<0) {
				beginning = photos.get(i).getDate(); //change begin date each time an earlier date is found
			}
		}
		
		this.beginDate = beginning;
		this.beginDate.set(Calendar.MILLISECOND, 0);
	}
	
	/**
	 * Returns the earliest date of the photos in this album 
	 * @return Calendar
	 */
	public Calendar getBeginDate() {
		
		//this.beginDate.set(Calendar.MILLISECOND, 0);
		
		if (photos.size()==0) {
			return null;
		}
		
		Calendar beginning = photos.get(0).getDate(); //set earliest date to first element
		
		for (int i = 1; photos.size()>i; i++) {
			if (photos.get(i).getDate().compareTo(beginning)<0) {
				beginning = photos.get(i).getDate(); //change begin date each time an earlier date is found
			}
		}
		
		return beginning;
	}
	
	
	/**
	 * Goes through all photos in the album and sets latest date as end date of the album
	 */
	public void setEndDate() {
		
		//this.endDate.set(Calendar.MILLISECOND, 0);
		
		if (photos.size()==0) {
			return;
		}
		
		Calendar beginning = photos.get(0).getDate(); //set earliest date to first element
		////ln(photos.get(0).getFileName());
		
		for (int i = 1; photos.size()>i; i++) {
			if (photos.get(i).getDate().compareTo(beginning)>0) {
				beginning = photos.get(i).getDate(); //change begin date each time an earlier date is found
				////ln(photos.get(i).getFileName());
			}
		}
		
		this.endDate = beginning;
		this.endDate.set(Calendar.MILLISECOND, 0);
		
		////ln();
	}
	
	/**
	 * Returns the latest date of the photos in this album 
	 * @return Calendar
	 */
	public Calendar getEndDate() {
		
		//this.endDate.set(Calendar.MILLISECOND, 0);
		
		if (photos.size()==0) {
			return null;
		}
		
		Calendar beginning = photos.get(0).getDate(); //set earliest date to first element
		
		for (int i = 1; photos.size()>i; i++) {
			if (photos.get(i).getDate().compareTo(beginning)>0) {
				beginning = photos.get(i).getDate(); //change begin date each time an earlier date is found
			}
		}
		
		return beginning;
	}
	
}
