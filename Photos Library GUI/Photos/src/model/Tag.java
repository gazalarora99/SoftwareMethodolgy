/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Tag implements Serializable
 * Tag constitutes of a tag name and value pair for a photo
 *
 */
public class Tag implements Serializable{

	private String name;
	private String value;
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * Constructor for Tag that takes a name and value pair as parameter
	 * @param name - tag's name given by user
	 * @param value - tag's value given by user
	 */
	public Tag(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	
	/**
	 * getTagName() returns the Tag's name as a String object
	 * @return String
	 */
	public String getTagName() {
		return name;
	}
	
	
	/**
	 * getTagValue() return the Tag's value as a String object
	 * @return String
	 */
	public String getTagValue() {
		return value;
	}
	
	/**
	 * @param newName - New tag name that user is adding
	 * Sets the tag's name
	 */
	public void setTagName(String newName) {
        this.name=newName;
    }
	
	/**
	 * @param newValue - New value that user is adding to a tag
	 * Sets the value to a tag
	 */
	public void setTagValue(String newValue) {
        this.value=newValue;
    }
	
	/**
	 * Compares Tag t with current tag's name and value, returns true if they name-value pair match
	 * @param t - Tag to be compared with
	 * @return boolean
	 */
	public boolean equals(Tag t) {
		if(t.getTagName().equals(name) && t.getTagValue().equals(value)) {
			return true;
		}
		return false;
	}
	
	/*
    //need to override this object class method cause hashset uses it to compare photos and tags
    public boolean equals(Object o) {
    	
        return (o instanceof Tag) && (((Tag)o).getTagName()).equals(this.getTagName()) 
        		&& (((Tag)o).getTagValue()).equals(this.getTagValue());
    }

    //need to override this for hashset too
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
	
	*/
	
	/**
	 * Returns the name of this tag as a String
	 * @return String
	 */
	public String toString() {
		return (name);
	}
    
	
}
