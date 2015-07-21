package backup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import com.bono.zero.model.Command;
import com.bono.zero.model.Playlist;
import com.bono.zero.model.Song;
import com.bono.zero.util.Observer;

/**
 * <p>Title: PlaylistPanel.java</p>
 * 
 * <p>Description: Class PlaylistPanel creates a JPanel that holds a
 * JTable that displays the playlist currently played by the MPDServer.
 * The JTable is wrapped in a JScrollPane, in case it needs scrollbars
 * they will be added. 
 * The class implements the Observer interface to Observe the Playlist
 * class and ServerStatus class to keep the table model up to date.
 * <p>
 * Three inner class are present. Class CurrentSongRenderer render the 
 * cells of the JTable. It renders the background gray and the background
 * of the current played song pink.
 * Class TableListener handels the selection of a row/cell. When clicked on
 * a song it will send the MPDserver the command to play that song.
 * Class UpdateTable Runnable class, updates the JTables model outside
 * the EventQueue.
 * 
 * @author bono
 *
 */
public class PlaylistPanel extends JPanel implements Observer {
	
	private JTable playlistTable;
	private AbstractTableModel tableModel;
	private JScrollPane scrollPane;
	private JPopupMenu popupMenu;
	private TableListener tableListener = new TableListener();
	private CurrentSongRenderer renderer = new CurrentSongRenderer();
	private ListSelectionModel selectionModel;
	private Zero zero;
	
	private int row;    // the row number on right mouse button released, to remove the song.
	
	public PlaylistPanel(Zero zero) {
		super();
		this.zero = zero;
		setLayout(new BorderLayout());
				
		playlistTable = new JTable();
		playlistTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		playlistTable.setRowSelectionAllowed(false);
		playlistTable.setFocusable(false);
		playlistTable.setDefaultRenderer(Object.class, renderer);
		playlistTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		playlistTable.addMouseListener(new TableMouseListener());
		
		popupMenu = new JPopupMenu();
		JMenuItem remove = new JMenuItem("remove");
		remove.addActionListener(new RemoveListener());
		popupMenu.add(remove);
		playlistTable.setComponentPopupMenu(popupMenu);
				
		selectionModel = playlistTable.getSelectionModel();
		
		selectionModel.addListSelectionListener(tableListener);
		
		scrollPane = new JScrollPane(playlistTable);
		
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	/*
	 * updateTable turns the playlist in a tablemodel
	 * for the JTable. The model sets the columns
	 * Song, Album and Artist.
	 */
	private void updateTable(Playlist playlist) {
		Song song = null;
		
		String[] rows = {"Song", "Album", "Artist"};
		this.tableModel = new DefaultTableModel(rows, playlist.getLength());
		
		for (int i = 0; i < playlist.getLength(); i++) {
			song = playlist.getSong(i);
			
			tableModel.setValueAt(song.getTitle(), i, 0);
			tableModel.setValueAt(song.getAlbum(), i, 1);
			tableModel.setValueAt(song.getArtist(), i, 2);
			
		}
		
	}
	
	/*
	 * Update method implemented by the Observer interface
	 * gets invoked by the Playlist class or ServerStatus 
	 * class. The parameter update tells witch Observable
	 * invokes the method and were the arg parameter 
	 * goes to.
	 */
	@Override
	public void update(String update, Object arg) {
		
		if (update.equals("table")) {
			// build a new model
			updateTable((Playlist) arg);
			// update the JTable outside the Eventqueue
			try {
				EventQueue.invokeAndWait(new UpdateTable());
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (update.equals("song")){
			int song = Integer.parseInt((String) arg);
			renderer.setSong(song);
			playlistTable.repaint();
			
		}
		
	}
	
	private class TableListener implements ListSelectionListener {
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			System.out.println(e.getFirstIndex());
			
			
			if (!e.getValueIsAdjusting()) {
			//	Song song = zero.getPlaylist().getSong(playlistTable.getSelectedRow());
				//zero.sendCommand(new Command("playid", song.getId()));
				
			}
		}
	}
	
	private class TableMouseListener extends MouseAdapter {

		
		@Override
		public void mouseReleased(MouseEvent e) {
			JTable table = (JTable) e.getSource();
			row = table.rowAtPoint(e.getPoint());
		}
		
	}
	
	private class RemoveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// remove the song
			//Song song = zero.getPlaylist().getSong(row);
			//zero.sendCommand(new Command("deleteid", song.getId()));
			 
			
		}
		
	}
			
	/*
	 * Renderer class that renders the cells of the table. In
	 * particular the color of the cells. The background color
	 * of the table cells will be rendered gray and the cells
	 * that hold the information of the current played song
	 * will be rendered pink.
	 */
	private class CurrentSongRenderer implements TableCellRenderer {

		  public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
		  
		  private int song;

		  public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column) {
		    Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		    Color foreground, background;
		   
		    //if (isSelected) {
		    //	background = Color.PINK;
			//    foreground = Color.BLACK;
		    //} else {
		    	if (row == song) {
		    		background = Color.PINK;
		    		foreground = Color.BLACK;
		    	} else {
		    		background = Color.LIGHT_GRAY;
				    foreground = Color.BLACK;
		    	}
		    //}
		      
		    renderer.setForeground(foreground);
		    renderer.setBackground(background);
		    return renderer;
		  }
		    
		  /*
		   * Gives integer song the value of the
		   * row that holds the information of 
		   * the current played song.
		   */
		  public void setSong(int song) {
			  
			  this.song = song;
		  }
	  
	}
	    
	
	
	
	/*
	 * Inner class that updates the JTables model as
	 * a runnable.
	 */
	private class UpdateTable implements Runnable {
		
		
		@Override
		public void run() {
			playlistTable.setModel(tableModel);
		}
	}

	
}
