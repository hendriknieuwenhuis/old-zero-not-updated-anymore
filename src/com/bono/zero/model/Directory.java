package com.bono.zero.model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import com.bono.zero.util.Observable;

public class Directory extends Observable {
	
	/**
	 * String prefixes to recognize or remove from the return messages from the server.
	 * Used in method makeModel(String[] path).
	 */
	private final String DIRECTORY_PREFIX  = "directory: ";
	private final String FILE_PREFIX       = "file: ";
	private final String PLAYLIST_PREFIX   = "playlist: ";
	private final String TOKEN             = "/";
	
	private DefaultTreeModel directory;         // stores the directory structure as a tree model.
	
	private DefaultMutableTreeNode music;       // root folder, mounted to the server
	
	public Directory() {
		music = new DefaultMutableTreeNode("music",true);
		directory = new DefaultTreeModel(music);
	}

	public Directory(List<String> entry) {
		this();
		setDirectory(entry);
	}

	@Deprecated
	public TreeModel getDirectory() {
		return directory;
	}

	public TreeModel getModel() {
		return directory;
	}
	
	public void setDirectory(List<String> directory) {
		
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) this.directory.getRoot();
		DefaultMutableTreeNode node = null;
						
		boolean hasChildren = true;		
		
		if (directory == null) {
			return;
		} else {
			// walk though the array
			for (String s : directory) {
				// see if it is a folder or file and remove the prefix
				if (s.startsWith(DIRECTORY_PREFIX)) {
					s = s.substring(DIRECTORY_PREFIX.length());
					hasChildren = true;
				} else if (s.startsWith(FILE_PREFIX)) {
					s = s.substring(FILE_PREFIX.length());
					hasChildren = false;
				} else if (s.startsWith(PLAYLIST_PREFIX)) {
					s = s.substring(PLAYLIST_PREFIX.length());
					hasChildren = false;
				}
				
				String[] temPath = s.split(TOKEN);
				
				node = root;
				
				for (int i = 0; i < temPath.length; i++) {
					// for every element in the array a node is made to check
					DefaultMutableTreeNode checkNode = new DefaultMutableTreeNode(temPath[i].toString());
					
					if (index(checkNode, node) != -1) {
						// node already exists so become that node and continue loop (to next array element)
						node =  (DefaultMutableTreeNode) node.getChildAt(index(checkNode, node));
					} else {
						// set hasChildren and add the checked node as a child.
						checkNode.setAllowsChildren(hasChildren);
						node.add(checkNode);
					}
				}
			}
		}
		node = null;
		this.directory.reload();
	}
	
	/* Enumerate the children of a given node, to
	 * check if the parent already has it as a child.
	 * uses variable node (DefaultMutableTreeNode)
	 */
	@SuppressWarnings("unchecked")
	private int index(DefaultMutableTreeNode checkNode, DefaultMutableTreeNode parent) {
		Enumeration<DefaultMutableTreeNode> find;
		find = parent.children();                     // unchecked
		int i = -1;
		while (find.hasMoreElements()) {
			i++;
			if (find.nextElement().toString().equals(checkNode.toString())) {
				return i;
			}
		}
		return -1;
	}
	
	public void doNotify() {
		notifyObserver("Directory", "update", getDirectory());
	}

}
