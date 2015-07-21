package com.bono.zero.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.*;

import com.bono.zero.laf.BonoScrollBarUI;

/**
 * <p>Title: DirectoryPanel.java</p>
 * 
 * <p>Description: This class displays the contents of the 'music'
 * folder that is mounted to the MPDserver as the folder containing
 * the music. The directory structure will be displayed as a JTree.
 * When a file is selected it can be added to the playlist.</p>
 * 
 * @author bono
 *
 */
public class DirectoryPanel extends JPanel {
	
	private final String CUE = "cue";
	
	private JTree tree;
	private JScrollPane scrollPane;
	private TreeNode root;
	private TreeSelectionModel selectionModel;
	

	public DirectoryPanel() {
		super();
		setLayout(new BorderLayout());

        tree = new JTree();
		tree.setCellRenderer(new TreeRenderer());

		scrollPane = new JScrollPane(tree);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUI(new BonoScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new BonoScrollBarUI());
		add(scrollPane, BorderLayout.CENTER);
	}

    public void addTreeModel(TreeModel model) {
        tree.setModel(model);
    }

    /**
     * Method for adding the MouseAdapter to the tree.
     */
    public void addMouseAdapter(MouseAdapter mouseAdapter) {
        tree.addMouseListener(mouseAdapter);
    }

	private class TreeRenderer extends DefaultTreeCellRenderer {
		
		@Override
		public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, 
				final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
			
			DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			
			//tree.setBackground(Color.LIGHT_GRAY);
			//renderer.setTextSelectionColor(Color.pink);
			renderer.setBackgroundNonSelectionColor(tree.getBackground());
			renderer.setBackgroundSelectionColor(Color.PINK);
			renderer.setBorderSelectionColor(tree.getBackground());
			return renderer;
		}
	}
	
	
}
