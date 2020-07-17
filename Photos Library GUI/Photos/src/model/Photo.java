/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package model;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
/**
 * 
 * Makes a photo object from filename, date, and tag attributes
 * Photo objects are stored in an album as an ArrayList
 *
 */
public class Photo implements Serializable{
	
	
	//These "Properties" allow the data to be grouped within the tableview row
	// can be accessed as regular objects by using the get() methods
	//private final StringProperty caption;
	//private final ObjectProperty<Image> image;
	private static final long serialVersionUID = 1L;
	
	//note: the imageview is only needed to physically display the Image
	//regular size image
	//private final ObjectProperty<ImageView> imageview;
	
	//thumbnail size image
	//private final ObjectProperty<ImageView> thumbnail;
	
	/**
	 * List of tags that belong the current photo
	 */
	private ArrayList<Tag> tags;
	private String filename, caption;
	private Pixels image;
	private Calendar date;
	
	
	/**
	 * Constructor to create a photo object from its filename, Image object and date
	 * @param name - String for filename to be assigned to this photo
	 * @param image - Image to be assigned to this photo created from Pixels
	 * @param date - date for this photo
	 */
	public Photo(String name, Pixels image, Calendar date) {
		filename = name;
		this.caption = "";
		this.image = image;
		this.date = date;
		this.tags = new ArrayList<Tag>();
		
		this.date = date;
		this.date.set(Calendar.MILLISECOND, 0);
	}
	
	/**
	 * Constructor to create a photo object from its filename, Image object and date
	 * @param name - String for filename to be assigned to this photo
	 * @param image - Image to be assigned to this photo
	 * @param date - date for this photo
	 */
	public Photo(String name, Image image, Calendar date) {
		filename = name;
		this.caption = "";
		this.image = new Pixels(image);
		this.date = date;
		this.tags = new ArrayList<Tag>();
		this.date = date;
		this.date.set(Calendar.MILLISECOND, 0);
	}
	
	 /**
     * Returns the Image object of this photo
     * @return Image
     */
	public Image getImage() {
		return image.getImage();
	}
	
/*
    public void setImage(Image image) {
        this.image.setImage();
    }
    
    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    
    public ImageView getImageView() {
    	return imageview.get();
    }
    
    
    public ObjectProperty<ImageView> imageViewProperty() {
    	return imageview;
    }
	
    public StringProperty captionProperty() {
        return caption;
    }
    */
    
	 /**
     * Returns the date of this photo
     * @return Calendar
     */
    public Calendar getDate() {
    	return date;
    }
    
    /**
     * Returns the filename of this photo
     * @return String
     */
    public String getFileName() {
    	return filename;
    }
    
    /**
     * Returns the caption of this photo
     * @return String
     */
    public String getCaption() {
        return caption;
    }
    /**
     * Sets given caption to this photo
     * @param caption - caption to be assigned
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }
    
/*
    public ImageView getThumbnail() {
    	ImageView t = thumbnail.get();
    	   t.setFitWidth(50);
           t.setPreserveRatio(true);
           return t;
    }
    
    public ObjectProperty<ImageView> thumbnailViewProperty() {
    	ObjectProperty<ImageView> t = thumbnail;
 	    t.get().setFitWidth(50);
        t.get().setPreserveRatio(true);
    	return t;
    }
 */
    
    /**
     * Compares the filename of this photo with another photo, returns true if they match
     * @param other - photo to be compared with
     * @return boolean
     */
    public boolean equals(Photo other) {
		return this.filename.equals(other.filename);
	}
    
    /*
    
    //need to override this object class method cause hashset uses it to compare photos
    // depending on what our 
    @Override
    public boolean equals(Object o) {
    	
    	//ln(1);
    	
    	//fields to compare
    	boolean sametags = true;
    	boolean samefilename = true;
    	boolean samecaption = true;
    	
    	//ln(2);
    	
    	// first take care of any null pointers
    	if (tags==null && ((Photo)o).tags!=null) {
    		return false;
    	}
    	if (tags!=null && ((Photo)o).tags==null) {
    		return false;
    	}
    	if (tags==null && ((Photo)o).tags==null) {
    		sametags=true;
    	}
    	//ln(3);
    	
     	if (caption==null && ((Photo)o).caption!=null) {
    		return false;
    	}
    	if (caption!=null && ((Photo)o).caption==null) {
    		return false;
    	}
       	if (caption==null && ((Photo)o).caption==null) {
    		samecaption=true;
    	}
     	
       	//ln(4);
       	
       	if (filename==null && ((Photo)o).filename!=null) {
    		return false;
    	}
    	if (filename!=null && ((Photo)o).filename==null) {
    		return false;
    	}
       	if (filename==null && ((Photo)o).filename==null) {
    		samefilename=true;
    	}
    	
       	//ln(5);
    	//iterate through this photos list of tags
    	
       	/*
    	if (tags!=null && ((Photo)o).tags!=null) {
    	
    		
    	ArrayList<Tag> dummylist1 = new ArrayList<Tag>();
    	dummylist1.addAll(this.getTags());
    	
    	ArrayList<Tag> dummylist2 = new ArrayList<Tag>();
    	dummylist2.addAll(((Photo)o).getTags());
    
    		
    	for (int i = 0; i<tags.size(); i++) {
    		//if (!(((Photo)o).getTags().get(i).getTagName().equals(this.getTags().get(i).getTagName()))) {
 
    		//ln(6);
    	//if the object being compared to this photo does not contain the same tags
    	//return false, these objects are not equal	
    	if (!(((Photo)o).getTags().equals(dummylist.get(i)))){
    		sametags = false;
    		return false;
    		//break;
    	}
    	
    	}
    	}
    	
 
       	if (caption!=null && ((Photo)o).caption!=null) {
       		samecaption=(((Photo)o).getCaption()).equals(this.getCaption());
    	}
     	
       	if (filename!=null && ((Photo)o).filename!=null) {
    		samefilename = (((Photo)o).getFileName()).equals(this.getFileName());
       	}
       	
       	
       	if (tags!=null && ((Photo)o).tags!=null) {
    		sametags = ((Photo)o).tags.equals(this.tags);
       	}
       	
       	
       	
    	
    	//compares filenames, captions, and result of sametags
        return ((o instanceof Photo) && samefilename	&& samecaption && sametags);
    }
	
    //need to override this for hashset too
    public int hashCode() {
        int result = 17;
        if (filename!=null) {
        result = 31 * result + filename.hashCode();
        }
        if (caption!=null) {
            result = 31 * result + caption.hashCode();
        }
        if (tags!=null) {
        	 result = 31 * result + tags.size();	
        }
        return result;
    }
    
    */
    
    /**
     * Returns a list of tags of this photo
     * @return ArrayList of Tag objects
     */
    public ArrayList<Tag> getTags(){
    	return tags;
    }
    
    /**
     * Adds a tag to the list of tags of this photo
     * @param newTag - tag to be added
     */
    public void addTagToPhoto(Tag newTag) {
    	tags.add(newTag);
    }
    
    /**
     * Deletes a tag from the list of tags of this photo
     * @param newTag - tag to be deleted 
     */
    public void deleteTag(Tag newTag) {
    	tags.remove(newTag);
    }
	/**
	 * Returns this photo's filename and caption as a String
	 * @return String
	 */
    public String toString() {
		return (filename +", " + caption);
	}

}
