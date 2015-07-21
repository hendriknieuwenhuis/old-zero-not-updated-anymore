package backup;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.bono.zero.model.Command;

import con.bono.zero.laf.BonoIcon;
import con.bono.zero.laf.BonoIconFactory;

public class PlaybackControls extends ButtonPanel{

	private ControlsHandler controlsHandler = new ControlsHandler();
	private BonoIcon play = BonoIconFactory.getPlayButtonIcon();
	private BonoIcon pause = BonoIconFactory.getPauseButtonIcon();
	private BonoIcon next = BonoIconFactory.getNextButtonIcon();
	private BonoIcon previous = BonoIconFactory.getPreviousButtonIcon();
	private BonoIcon stop = BonoIconFactory.getStopButtonIcon();
	
	public PlaybackControls(Zero zero) {
		super(zero);
		
		setLayout(new FlowLayout());
		
		addButton(previous, "previous", controlsHandler, new Command("previous"));
		addButton(stop, "stop", controlsHandler, new Command("stop"));
		addButton(pause, "pause", controlsHandler, new Command("pause","1"));
		addButton(play, "play", controlsHandler, new Command("play"));
		addButton(next, "next", controlsHandler, new Command("next"));
	}
	
	private class ControlsHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String key = e.getActionCommand();
			Command command = getCommand(key);
			zero.sendCommand(command);
		}
		
	}
}
