package com.bono.zero.view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import com.bono.zero.control.Server;
import com.bono.zero.model.ServerStatus;
import com.bono.zero.util.Observer;
import com.bono.zero.laf.BonoIcon;
import com.bono.zero.laf.BonoIconFactory;

/**
 * Observer listens to STATE, RANDOM, REPEAT
 * @author bono
 *
 */
public class PlaybackControls extends ButtonPanel implements Observer {
	
	private final BonoIcon PLAY_ICON     = BonoIconFactory.getPlayButtonIcon();
	private final BonoIcon PAUSE_ICON    = BonoIconFactory.getPauseButtonIcon();
	private final BonoIcon NEXT_ICON     = BonoIconFactory.getNextButtonIcon();
	private final BonoIcon PREVIOUS_ICON = BonoIconFactory.getPreviousButtonIcon();
	private final BonoIcon STOP_ICON     = BonoIconFactory.getStopButtonIcon();
	private final BonoIcon REPEAT_ICON   = BonoIconFactory.getRepeatIcon();
	private final BonoIcon RANDOM_ICON   = BonoIconFactory.getRandomIcon();
	
	public PlaybackControls() {
		super(new FlowLayout(FlowLayout.LEFT));

		addToggleButton(RANDOM_ICON, Server.RANDOM);
		addToggleButton(REPEAT_ICON, Server.REPEAT);
		addSeperator(SwingConstants.VERTICAL);
		addButton(PREVIOUS_ICON, Server.PREVIOUS);
		addButton(STOP_ICON, Server.STOP);
		addButton(PLAY_ICON, Server.PLAY);
		addButton(NEXT_ICON, Server.NEXT);
	}
	
	
	
	@Override
	public void update(String update, Object arg) {
		String status = (String) arg;
		// update the play / pause button
		if (update.equals(ServerStatus.STATE)) {
			// if playback state is PLAY.
			if (status.equals("play")) {
				((JButton) getButton(Server.PLAY)).setIcon(PAUSE_ICON);
				((JButton) getButton(Server.PLAY)).setActionCommand(Server.PAUSE);
			// else playback state is PAUSE or STOP	
			} else {
				((JButton) getButton(Server.PLAY)).setIcon(PLAY_ICON);
				((JButton) getButton(Server.PLAY)).setActionCommand(Server.PLAY);
			}
		// update the RANDOM button	
		} else if (update.equals(ServerStatus.RANDOM)) {
			// if RANDOM state is "0" (false)
			if (status.equals("0")) {
				((JToggleButton) getButton(Server.RANDOM)).setSelected(false);
			// else RANDOM state is "1" (true)	
			} else  {
				((JToggleButton) getButton(Server.RANDOM)).setSelected(true);
			}
		// update the REPEAT button	
		} else if (update.equals(ServerStatus.REPEAT)) {
			// if REPEAT state is "0" (false)
			if (status.equals("0")) {
				((JToggleButton) getButton(Server.REPEAT)).setSelected(false);
			// else REPEAT state is "1" (true)	
			} else  {
				((JToggleButton) getButton(Server.REPEAT)).setSelected(true);
			}
		}
	}

}
