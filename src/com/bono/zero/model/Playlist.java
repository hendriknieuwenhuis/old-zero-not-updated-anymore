package com.bono.zero.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.bono.zero.util.Observable;

/**
 * <p>Title; Playlist.java</p>
 * 
 * <p>Description: Class <code>Playlist.java</code> represents a playlist of the 
 * MPDserver. The data of the playlist is stored in an two-dimensional
 * vector. Each row is a song.
 * @author bono
 *
 */
@Deprecated
public class Playlist extends Observable implements TableModel {
	
	private final String SLASH           = "/";
	
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
	
	// columnNames prefixes
	public static final int FILE_C          = 0;
	public static final int LAST_MODIFIED_C  = 1;
	public static final int ALBUM_C         = 2;
	public static final int ARTIST_C        = 3;
	public static final int DATE_C          = 4;
	public static final int TIME_C          = 5;
	public static final int TITLE_C         = 6;
	public static final int TRACK_C         = 7;
	public static final int POS_C           = 8;
	public static final int ID_C            = 9;
		
	private EventListenerList listenerList = new EventListenerList();
	
	private Vector columnNames;
	
	private Vector elements;
	
	private int visibleColumns;    // amount of visible columns
	
	private int[] columnsShown;    // array holding the indexes of the columns to be shown
	
	private String[] col;          // array of the columns, Strings.
	
	/**
	 * Constructor builds a playlist with size 0;
	 */
		public Playlist() {
		super();
		col = new String[]{"file", "last modified", "album", "artist", "date", "time", "title", "track", "position", "id"}; 
		columnNames = new Vector(Arrays.asList(col));
		elements = newVector(0);
		elements.add(newVector(10));
	}
		
	
	/*
	 * Make a new vector, set size and return it.
	 */
	private Vector newVector(int size) {
		Vector vector = new Vector();
		vector.setSize(size);
		return vector;
	}
			
	/**
	 * Populate the playlist with the playlistinfo MPDserver query
	 * result as an array of Strings. Each song is a row containing all
	 * the song variables as String objects or null objects.
	 * @param query array of Strings
	 */
	public void populatePlaylist(String[] query) {
		Vector songRow = null;
		// point elements to a new empty vector.
		elements = null;
		elements = newVector(0);
		// populate the playlist vector
		for (String line : query) {
						
			if (line.startsWith(FILE)) {
				/*
				 * Make a new row and add it to
				 * the elements vector. This is 
				 * done here because every song 
				 * has a file.
				 */
				songRow = newVector(10);
				elements.add(songRow);
				/*
				 * If the directory path were the 
				 * file is found also is displayed
				 * in the  query, remove it.
				 * The title column will also be
				 * set with the file name, when there
				 * is no title information in the file,
				 * else it will be replaced with
				 * the title information later.  
				 */
				if (line.contains(SLASH)) {
					String[] lineArray = line.split(SLASH);
					line = lineArray[(lineArray.length-1)];
					songRow.setElementAt(line, FILE_C);
					songRow.setElementAt(line, TITLE_C);
				} else {
					songRow.setElementAt(line.substring(FILE.length()), FILE_C);
					songRow.setElementAt(line.substring(FILE.length()), TITLE_C);
				}
			} else if (line.startsWith(LAST_MODIFIED)) {
				songRow.setElementAt(line.substring(LAST_MODIFIED.length()), LAST_MODIFIED_C);
			} else if (line.startsWith(TIME)) {
				songRow.setElementAt(line.substring(TIME.length()), TIME_C);
			} else if (line.startsWith(ALBUM)) {
				songRow.setElementAt(line.substring(ALBUM.length()), ALBUM_C);
			} else if (line.startsWith(ARTIST)) {
				songRow.setElementAt(line.substring(ARTIST.length()), ARTIST_C);
			} else if (line.startsWith(DATE)) {
				songRow.setElementAt(line.substring(DATE.length()), DATE_C);
			} else if (line.startsWith(TITLE)) {
				songRow.setElementAt(line.substring(TITLE.length()), TITLE_C);
			} else if (line.startsWith(TRACK)) {
				songRow.setElementAt(line.substring(TRACK.length()), TRACK_C);
			} else if (line.startsWith(POS)) {
				songRow.setElementAt(line.substring(POS.length()), POS_C);
			} else if (line.startsWith(ID)) {
				songRow.setElementAt(line.substring(ID.length()), ID_C);
			}
		}
		fireTableChanged(new TableModelEvent(this));
		
	}
	
	
	@Override
	public int getRowCount() {
		return elements.size();
	}

	@Override
	public int getColumnCount() {
		return visibleColumns;
	}

	@Override
	public String getColumnName(int columnIndex) {
		int column = columnsShown[columnIndex];
		return (String) columnNames.elementAt(column);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (getValueAt(0, columnIndex) == null) {
			return Object.class;
		}
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * ----> JTable uses this method to retrieve data <----
	 * 
	 * Returns the value of an object at an index.
	 * 
	 * Can only be used by the <code>JTable</code> because
	 * it does not retrieve the data from the actual 
	 * given index. It returns the data from one of the
	 * columns that is set to be shown, using the column 
	 * indexes stored in the <code>columnsShown</code> 
	 * array. 
	 * A <code>columnIndex</code> outside the columnShown
	 * array index will give an out of bounds error.
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		int column = columnsShown[columnIndex];
		
		Vector row = (Vector) elements.elementAt(rowIndex);
		//if ((columnIndex == 0) && (row.elementAt(columnIndex) == null)) {
		//	return row.elementAt(columnIndex);
		//}
		return row.elementAt(column);
		
	}
	
	/**
	 * Get the stored value at an index. The index is
	 * given by the row and the the column index.
	 *   ------> USE TO ACCESS ALL COLUMNS <-------
	 * @param row
	 * @param column
	 * @return
	 */
	public Object getElementAt(int rowIndex, int columnIndex) {
		
		Vector row = (Vector) elements.elementAt(rowIndex);
		//if ((columnIndex == 0) && (row.elementAt(columnIndex) == null)) {
		//	return row.elementAt(columnIndex);
		//}
		return row.elementAt(columnIndex);
		
	}
	
	/**
	 * Converts a list of integers to an array
	 * of integers, by a iterating over
	 * the list by a for loop, and returns it.
	 */
	private int[] toArray(List<Integer> list) {
		int[] arr = new int[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}
	
	// get the column index of a column by its name
	private int getColumn(String columnName) {
		Iterator i = columnNames.iterator();
		int columnIndex = -1;
		while (i.hasNext()) {
			columnIndex++;
			if (i.next().equals(columnName)) {
				return columnIndex;
			}
		}
		return -1; // column does not exist!!!
	}
	
		
	/**
	 * Sets the columns that have to be shown by
	 * putting their index in the <code>columnNames</code>
	 *  <code>vector</code> in an array of integers.
	 *  Sets the amount of <code>visibleColumns</code>.
	 * @param columns
	 */
	public void showColumns(String[] columns) {
		List<Integer> columnList = new ArrayList<Integer>();
		for (String column : columns) {
			if (getColumn(column) != -1) {
				columnList.add(getColumn(column));
			}
		}
		visibleColumns = columnList.size();
		columnsShown = toArray(columnList);
		fireTableChanged(new TableModelEvent(this, TableModelEvent.HEADER_ROW));
		
	}
	

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}
	
	@Override
	public void addTableModelListener(TableModelListener l) {
		listenerList.add(TableModelListener.class, l);
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		listenerList.remove(TableModelListener.class, l);
		
	}
	
	private void doNotify(String key, String update, Object arg) {
		notifyObserver(key, update, arg);
	}
	
	/*
	 * Stolen from abstracttablemodel
	 */
	public void fireTableChanged(TableModelEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TableModelListener.class) {
                ((TableModelListener)listeners[i+1]).tableChanged(e);
            }
        }
    }

}
