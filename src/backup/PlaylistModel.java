package backup;

import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class PlaylistModel {
	
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
		
	private HashMap<Integer, Song> elements;
		
	public PlaylistModel() {
		elements = new HashMap<Integer , Song>();
	}
		
	/*
	 * Adds a song object to the playlist using the 
	 * HashMaps put method. Using the id as a String
	 * as the key of the song object.  
	 * 
	 */
	public void add(int key, Song song) {
		
		elements.put(key, song);
		
	}
		
	/**
	 * Returns the song specified by the
	 * songs key that is entered.
	 * @param String key of the song object.
	 */
	public Song getSong(String key) {
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
		
	/*
	 * Returns the set of keys of this hashmap.
	 */
	//public Set getKeySet() {
	//	return elements.keySet();
	//}
		
	public void emptyPlaylist() {
		elements.clear();
		//doNotify();
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
/*
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getColumnName(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}*/

}
