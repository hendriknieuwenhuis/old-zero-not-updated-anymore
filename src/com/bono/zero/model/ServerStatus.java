package com.bono.zero.model;

import java.util.HashMap;
import com.bono.zero.util.Observable;

public class ServerStatus extends Observable {
	
	// status prefixes
	public static final String VOLUME          = "volume";
	public static final String REPEAT          = "repeat";
	public static final String RANDOM          = "random";
	public static final String SINGLE          = "single";
	public static final String CONSUME         = "consume";
	public static final String PLAYLIST        = "playlist";
	public static final String PLAYLIST_LENGTH = "playlistlength";
	public static final String XFADE           = "xfade";
	public static final String MIXRAMP_DB      = "mixrampdb";
	public static final String STATE           = "state";
	public static final String SONG            = "song";
	public static final String SONG_ID         = "songid";
	public static final String TIME            = "time";
	public static final String BIT_RATE        = "bitrate";
	public static final String AUDIO           = "audio";
	public static final String NEXT_SONG       = "nextsong";
	public static final String NEXT_SONG_ID    = "nextsongid";
	
	private HashMap<String, String> elements = new HashMap<String, String>();
	
	public ServerStatus() {}
	
	/**
	 * Set the status query of the MPDserver in t
	 * the <code>hashmap</code>. Every string
	 * in the query will be split in a key and
	 * value and stored.
	 * @param query the status of the MPDserver.
	 */
	public void setStatus(String[] query) {
		elements.clear();     
		for (String status : query) {
			status = status.replaceAll("\\s","");
			String[] stats = status.split(":");
			elements.put(stats[0], stats[1]);
		}
		doNotify();
	}
	
	public String getStatus(String key) {
		return elements.get(key);
	}
	
	private void doNotify() {
		notifyObserver("State", STATE, getStatus(STATE));
		notifyObserver("Repeat", REPEAT, getStatus(REPEAT));
		notifyObserver("Random", RANDOM, getStatus(RANDOM));
		notifyObserver("Consume", CONSUME, getStatus(CONSUME));
		notifyObserver("Song", SONG, getStatus(SONG));
	}
	
}
