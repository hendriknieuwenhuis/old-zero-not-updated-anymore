package backup;


import java.util.Iterator;
import java.util.LinkedHashMap;
//import java.util.Observable;
import java.util.Set;

import com.bono.zero.util.Observable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * <p>Title: Playlist</p>
 * 
 * <p>Description: Class Playlist extends Observable.
 * 
 * Observable methods:
 * void addObserver(Observer o)
 * void notifyObserver(String key, String update, Object arg)
 * 
 * @author bono
 *
 */
public class Playlist extends Observable {
	

	// Song prefixes
	private final String FILE            = "file: ";
	private final String LAST_MODIFIED   = "Last-Modified: ";
	private final String ALBUM           = "Album: ";
	private final String ARTIST          = "Artist: ";
	private final String DATE            = "Date: ";
	private final String TIME            = "time: ";
	private final String TITLE           = "Title: ";
	private final String TRACK           = "Track: ";
	private final String POS             = "Pos: ";
	private final String ID              = "Id: ";
	
	private LinkedHashMap<Integer, Song> elements;
	
	private Set keys;
	
	public Playlist() {
		elements = new LinkedHashMap<Integer , Song>();
	}
	
	/*
	 * Adds a song object to the playlist using the 
	 * HashMaps put method. Using the id as a String
	 * as the key of the song object.  
	 * 
	 */
	public void add(int key, Song song) {
		elements.put(key, song);
		doNotify();
	}
	
	/**
	 * Returns the song specified by the
	 * songs key that is entered.
	 * @param String key of the song object.
	 */
	public Song getSong(int key) {
		return elements.get(key);
	}
	
	/**
	 * Returns the size, the amount of
	 * elements in the hashmap.
	 * @return the size of the map.
	 */
	public int getLength() {
		return elements.size();
	}
	
	private void setKeys() {
		keys = elements.entrySet();
	}
			
	public void emptyPlaylist() {
		elements.clear();
		doNotify();
	}
	
	/**
	 * Method, makes the playlist by creating song objects 
	 * and populate the playlist with them.
	 * @param songs array of Strings
	 */
	public void populatePlaylist(String[] songs) {
		int count = 0;
		elements.clear();
		
		Song song = null;
		for (String s : songs) {
			if (s.startsWith(FILE)) {
				song = new Song();
				song.setFile(s.substring(FILE.length()));
			} else if (s.startsWith(LAST_MODIFIED)) {
				song.setLast_modified(s.substring(LAST_MODIFIED.length()));
			} else if (s.startsWith(TIME)) {
				song.setTime(s.substring(TIME.length()));
			} else if (s.startsWith(ALBUM)) {
				song.setAlbum(s.substring(ALBUM.length()));
			} else if (s.startsWith(ARTIST)) {
				song.setArtist(s.substring(ARTIST.length()));
			} else if (s.startsWith(DATE)) {
				song.setDate(s.substring(DATE.length()));
			} else if (s.startsWith(TITLE)) {
				song.setTitle(s.substring(TITLE.length()));
			} else if (s.startsWith(TRACK)) {
				song.setTrack(s.substring(TRACK.length()));
			} else if (s.startsWith(POS)) {
				song.setPos(s.substring(POS.length()));
			} else if (s.startsWith(ID)) {
				song.setId(s.substring(ID.length()));
				add(count, song);
				count++;
			}
		}
	}
	
	
	private void doNotify() {
		notifyObserver("Playlist", "table", this);
	}
		
		
	

}
