package com.bono.zero.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.*;

import com.bono.zero.model.ServerStatus;
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
 * Three inner classes are present. Class CurrentSongRenderer render the 
 * cells of the JTable. It renders the background gray and the background
 * of the current played song pink.
 * Class TableListener handles the selection of a row/cell. When clicked on
 * a song it will send the MPDserver the command to play that song.
 * Class UpdateTable Runnable class, updates the JTables model outside
 * the EventQueue.
 * 
 * @author bono
 *
 */
public class PlaylistPanel extends JPanel implements Observer {
	
	//private Zero zero;
	private JTable playlistTable;
	private JScrollPane scrollPane;
	//private JPopupMenu popupMenu;
	//private ListSelectionModel selectionModel;
	private CurrentSongRenderer renderer = new CurrentSongRenderer();
			
	//private int row;    // the row number on right mouse button released, to remove the song.
	
	//private int modelUpdate = 0;

    public PlaylistPanel() {
        super();
        setLayout(new BorderLayout());

        playlistTable = new JTable();
        playlistTable.setRowSelectionAllowed(true);
        playlistTable.setFocusable(true);
        playlistTable.setOpaque(false);
        playlistTable.setShowGrid(true);
        playlistTable.setDefaultRenderer(Object.class, renderer);
        playlistTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        scrollPane = new JScrollPane(playlistTable);
        scrollPane.getViewport().setBackground(Color.LIGHT_GRAY);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }
	/*
	public PlaylistPanel(Zero zero) {
		super();
		this.zero = zero;
		setLayout(new BorderLayout());
		
		playlistTable = new JTable(zero.getPlaylist());
		playlistTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		playlistTable.setRowSelectionAllowed(true);
		playlistTable.setFocusable(true);
		playlistTable.setOpaque(false);
		playlistTable.setShowGrid(true);
		playlistTable.setDefaultRenderer(Object.class, renderer);
		playlistTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//playlistTable.addMouseListener(new TableMouseListener());
		playlistTable.setBackground(Color.GRAY);
		popupMenu = new JPopupMenu();
		JMenuItem remove = new JMenuItem("remove");
		JMenuItem clearAll = new JMenuItem("clear all");
		
		remove.addActionListener(new RemoveListener());
		clearAll.addActionListener(new ClearAllListener());
		
		popupMenu.add(remove);
		popupMenu.add(clearAll);
		
		playlistTable.setComponentPopupMenu(popupMenu);
				
		selectionModel = playlistTable.getSelectionModel();
						
		scrollPane = new JScrollPane(playlistTable);
		scrollPane.getViewport().setBackground(Color.LIGHT_GRAY);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);
	}*/

    public void addPopupMenu(JPopupMenu popup) {
        playlistTable.setComponentPopupMenu(popup);
    }

    public void setTableModel(TableModel dataModel) {
        playlistTable.setModel(dataModel);
    }

    public void addTableMouseListener(MouseAdapter mouseAdapter) {
        playlistTable.addMouseListener(mouseAdapter);
    }
	
	public ListSelectionModel getSelectionModel() {
        return playlistTable.getSelectionModel();
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
		
		if (update.equals(ServerStatus.SONG)){
			if (arg != null) {
				renderer.setSong(Integer.parseInt((String) arg));
				playlistTable.repaint();
			}
		}
	}
	
				
		
	/*
	private class TableMouseListener extends MouseAdapter {

		
		@Override
		public void mouseReleased(MouseEvent e) {
			
			int max=0;
			int min=0;
			
			
			if (e.getClickCount() == 2) {
				// play the double clicked song.
				int row = selectionModel.getLeadSelectionIndex();
				String song = (String) zero.getPlaylist().getElementAt(row, Playlist.ID_C);
				zero.sendCommand(new Command("playid", song));
				selectionModel.clearSelection();
			} else {
				//ListSelecti
				if (e.isShiftDown()) {
					max = selectionModel.getMaxSelectionIndex();
					
				}
				min = selectionModel.getMinSelectionIndex();
				max = selectionModel.getMaxSelectionIndex();
				
				selectionModel.setSelectionInterval(min, max);
				playlistTable.repaint();
			}
			
		}
	}*/
	
	/**
	 * Listener for the remove function in the popup menu
	 * of the JTable. 
	 */ /*
	private class RemoveListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (selectionModel.isSelectionEmpty()) {
				return;
			}else if (selectionModel.getMinSelectionIndex() == selectionModel.getMaxSelectionIndex()) {
				// remove the selected song
				zero.sendCommand(new Command(Server.REMOVE_ID, (String) zero.getPlaylist().getElementAt(selectionModel.getMinSelectionIndex(), Playlist.ID_C)));
				selectionModel.clearSelection();
			} else {
				// remove multiple selected songs
				int start = selectionModel.getMinSelectionIndex();
				int end = selectionModel.getMaxSelectionIndex();
				ArrayList<Command> ids = new ArrayList<Command>();
				ids.add(new Command(Server.START_COM_LIST));
				for (int i = start; i <= end; i++) {
					String param = (String)zero.getPlaylist().getElementAt(i, Playlist.ID_C);
					ids.add(new Command(Server.REMOVE_ID, param));	
				}
				ids.add(new Command(Server.END_COM_LIST));
				try {
					zero.getServer().sendCommand(ids);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * Listener for the <code>clearAll</code> JMenuItem
	 * of the <code>popupMenu</code>. Removes all songs
	 * of the current <code>playlist</code>.
	 * 
	 * @author bono
	 *
	 */ /*
	private class ClearAllListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			zero.sendCommand(new Command(Server.CLEAR));
		
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
		   	
		    		    
		    if (isSelected) {
		    	background = Color.DARK_GRAY;
				foreground = Color.PINK;
		    	
		    } else if (row == song) {
		    	background = Color.PINK;
		    	foreground = Color.BLACK;
		    } else {
		    	background = Color.LIGHT_GRAY;
			    foreground = Color.BLACK;
		    }
		    		      
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
		
}
