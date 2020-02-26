/*
 * Whitney Alderman and Gazal Arora
 * 
 */


package view;



public class Song {
	
	String name;
	String artist;
	String album;
	String year;
	
	
	public Song(String name, String artist) {
		this.name = name;
		this.artist = artist;
	}

	public Song(String name, String artist, String album, String year) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	
	public String getSongName() {
		return name;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getAlbum() {
		if (album==null) {
			return "";
		}
		else return album;
	}
	
	public String getYear() {
		if (year==null) {
			return "";
		}
		else return year;
	}
	
	public void setName(String name) {
		this.name = name;
		
	}
	
	public void setArtist(String artist) {
		
		this.artist = artist;
		
	}
	
	public void setAlbum(String album) {
		this.album = album;
			
	}
	
	public void setYear(String year) {
		this.year = year;
		
	}
	

	
	public String toString() {
		return (name +", " + artist);
	}
	
	
	}
	


