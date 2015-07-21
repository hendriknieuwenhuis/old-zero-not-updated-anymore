package backup;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Observer;

import javax.swing.JPanel;


import com.bono.zero.view.buttons.NextButton;
import com.bono.zero.view.buttons.PlayPauseButton;
import com.bono.zero.view.buttons.PreviousButton;
import com.bono.zero.view.buttons.StopButton;

/**
 * <p> Title: PlayerPanel.java</p>
 * 
 * <p> Description: Class PlayerPanel constructs the player panel
 * of the user interface. It contains the buttons to control the player
 * functions of the server, stop, pause / play, previous and next.
 * A slider that controls the volume and a slider that makes it possible 
 * for the user to scroll the current song. There is also a label panel 
 * that displays the song that is currently played. The Panel is 
 * build out of tree sub panels, player, song and slider.</p>
 * 
 * @author bono
 * @version 1.0
 *
 */
public class PlayerPanel extends JPanel {
	
	private JPanel player;
	private JPanel song;
	private JPanel slider;
	
	private PreviousButton previousButton;
	private PlayPauseButton playPauseButton;
	private StopButton stopButton;
	private NextButton nextButton;
	
	public PlayerPanel() {
		buildPanel();
	}
	
	private void buildPanel() {
		//setLayout(new GridLayout(0,3));
		add(buildPlayer());
	}
	
	/*
	 * Builds the panel containing the previous, stop,
	 * pause / play and next button. Using a flow layout
	 * that is left aligned.
	 */
	private JPanel buildPlayer() {
		player = new JPanel();
		player.setLayout(new FlowLayout(FlowLayout.LEFT));
		previousButton = new PreviousButton();
		playPauseButton = new PlayPauseButton();
		stopButton = new StopButton();
		nextButton = new NextButton();
		player.add(previousButton);
		player.add(stopButton);
		player.add(playPauseButton);
		player.add(nextButton);
		return player;
	}
	
	public void addPlayPauseObserver(Observer o) {
		playPauseButton.addObserver(o);
	}
	
	public void addStopObserver(Observer o) {
		stopButton.addObserver(o);
	}
	
	public void addNextObserver(Observer o) {
		nextButton.addObserver(o);
	}
	
	public void addPreviousObserver(Observer o) {
		previousButton.addObserver(o);
	}
	
	public Observer getPlayPauseObserver() {
		return playPauseButton.getObserver();
	}
	
	//public void setPlayerIcon(String icon) {
	//	playPauseButton.setIcon(icon);
	//}
	

}
