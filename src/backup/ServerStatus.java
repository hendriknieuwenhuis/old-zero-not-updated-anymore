package backup;

import com.bono.zero.util.Observable;

/**
 * Class ServerStatus represents the current Server state
 * Observable methods:
 * void addObserver(Observer 0)
 * protected void clearChanged()
 * int countObservers();
 * void deleteObserver(Observer o)
 * void deleteObservers() 
 * @author bono
 *
 */

public class ServerStatus extends Observable {
	
	// status prefixes
	private final String VOLUME          = "volume: ";
	private final String REPEAT          = "repeat: ";
	private final String RANDOM          = "random: ";
	private final String SINGLE          = "single: ";
	private final String CONSUME         = "consume: ";
	private final String PLAYLIST        = "playlist: ";
	private final String PLAYLIST_LENGTH = "playlistlength: ";
	private final String XFADE           = "xfade: ";
	private final String MIXRAMP_DB      = "mixrampdb: ";
	private final String STATE           = "state: ";
	private final String SONG            = "song: ";
	private final String SONG_ID         = "songid: ";
	private final String TIME            = "time: ";
	private final String BIT_RATE        = "bitrate: ";
	private final String AUDIO           = "audio: ";
	private final String NEXT_SONG       = "nextsong: ";
	private final String NEXT_SONG_ID    = "nextsongid: ";
	
				
	// state: play, stop or pause.
	private String state = null;
	// elapsed and total time of current playing or paused song.
	private String time = null;
	// sample rate : bits : channels
	private String audio = null;
	// mixramp delay in seconds
	private String mixrampDelay = null;
	// error message if error occurs.
	private String error = null;
	// current volume value set;
	private int volume;
	// repeat 0 no, 1 yes.
	private String repeat;
	// random 0 no, 1 yes.
	private String random;
	// single 0 no, 1 yes.
	private String single;
	// consume 0 no. 1 yes.
	private String consume;
	// playlist version number
	private int playlist;
	// length of the playlist
	private int playlistLength;
	// playlist song number of current song
	private String song = null;
	// playlist song id number of current song
	private String songID = null;                       // changed to string
	// playlist next song number 
	private String nextSong = null;
	// playlist song id number of next song
	private String nextSongID = null;
	// bitrate in kbps
	private int bitRate;
	// cross fade in seconds
	private int xFade;
	// mixramp threshold in db
	private double mixrampDb;
	// job id
	private int updatingDb;
		
		
		
	public ServerStatus() {
			
	}
	
	
	public void populate(String[] status) {
		
		for (String state : status) {
			if (state.startsWith(VOLUME)) {
				int newVolume = Integer.parseInt(state.substring(VOLUME.length()));
				setVolume(newVolume);
				
			} else if (state.startsWith(REPEAT)) {
				String newRepeat = state.substring(REPEAT.length());
				setRepeat(newRepeat);
				
			} else if (state.startsWith(RANDOM)) {
				String newRandom = state.substring(RANDOM.length());
				setRandom(newRandom);
				
			} else if (state.startsWith(SINGLE)) {
				String newSingle = state.substring(SINGLE.length());
				setSingle(newSingle);
				
			} else if (state.startsWith(CONSUME)) {
				//int newConsume = Integer.parseInt(state.substring(CONSUME.length()));
				String newConsume = state.substring(CONSUME.length());
				setConsume(newConsume);
				
			} else if (state.startsWith(PLAYLIST)) {
				int newPlaylist = Integer.parseInt(state.substring(PLAYLIST.length()));
				setPlaylist(newPlaylist);
				
			} else if (state.startsWith(PLAYLIST_LENGTH)) {
				int newPlaylistLength = Integer.parseInt(state.substring(PLAYLIST_LENGTH.length()));
				setPlaylistLength(newPlaylistLength);
				
			} else if (state.startsWith(XFADE)) {
				int newXfade = Integer.parseInt(state.substring(XFADE.length()));
				setxFade(newXfade);
				
			} else if (state.startsWith(MIXRAMP_DB)) {
				double newMixrampDb = Double.parseDouble(state.substring(MIXRAMP_DB.length()));
				setMixrampDb(newMixrampDb);
				
			} else if (state.startsWith(STATE)) {
				String newState = state.substring(STATE.length());
				setState(newState);
				
			} else if (state.startsWith(SONG)) {
				String newSong = state.substring(SONG.length());
				setSong(newSong);
				
			} else if (state.startsWith(SONG_ID)) {
				String newSongID = state.substring(SONG_ID.length());
				setSongID(newSongID);
				
			} else if (state.startsWith(TIME)) {
				String newTime = state.substring(TIME.length());
				setTime(newTime);
				
			} else if (state.startsWith(BIT_RATE)) {
				int newBitrate = Integer.parseInt(state.substring(BIT_RATE.length()));
				setBitRate(newBitrate);
				
			} else if (state.startsWith(AUDIO)) {
				String newAudio = state.substring(AUDIO.length());
				setAudio(newAudio);
				
			} else if (state.startsWith(NEXT_SONG)) {
				String newNextSong = state.substring(NEXT_SONG.length());
				setNextSong(newNextSong);
				
			}else if (state.startsWith(NEXT_SONG_ID)) {
				String newNextSongID = state.substring(NEXT_SONG_ID.length());
				setNextSongID(newNextSongID);
				
			}
			
		}
		
	}
	
	

	@Override
	public String toString() {
		String result;
		result = "[ Volume: "+Integer.toString(volume)+"| Repeat: "+repeat+"| Random: "+random
		+"| Single: "+single+"| Consume: "+consume+"| Playlist: "+Integer.toString(playlist)+
		"| PlaylistLength: "+Integer.toString(playlistLength)+"| Xfade: "+Integer.toString(xFade)+"| MixrampDb: "+Double.toString(mixrampDb)
		+"\n| State: "+state+"| Song: "+song+"| SingID: "+songID+"| Time: "+time+"| Bitrate: "+Integer.toString(bitRate)+"| Audio: "+audio+
		"| NextSong: "+nextSong+"| NextSongID: "+nextSongID+" ]";
		return result;
	}
	
	public String getState() {
		return state;
	}



	private void setState(String state) {
		this.state = state;
		notifyObserver("State", "state", getState());
	}



	public String getTime() {
		return time;
	}



	private void setTime(String time) {
		this.time = time;
	}



	public String getAudio() {
		return audio;
	}



	private void setAudio(String audio) {
		this.audio = audio;
	}



	public String getError() {
		return error;
	}



	public void setError(String error) {
		this.error = error;
	}



	public int getVolume() {
		return volume;
	}



	private void setVolume(int volume) {
		this.volume = volume;
	}



	public String getRepeat() {
		return repeat;
	}



	private void setRepeat(String repeat) {
		this.repeat = repeat;
		notifyObserver("Repeat", "repeat", getRepeat());
	}



	public String getRandom() {
		return random;
	}



	private void setRandom(String random) {
		this.random = random;
		notifyObserver("Random", "random", getRandom());
	}



	public String getSingle() {
		return single;
	}



	private void setSingle(String single) {
		this.single = single;
		notifyObserver("Single", "single", getSingle());
	}



	public String getConsume() {
		return consume;
	}



	private void setConsume(String consume) {
		this.consume = consume;
		notifyObserver("Consume", "consume", getConsume());
	}



	public int getPlaylist() {
		return playlist;
	}



	private void setPlaylist(int playlist) {
		this.playlist = playlist;
	}



	public int getPlaylistLength() {
		return playlistLength;
	}



	private void setPlaylistLength(int playlistLength) {
		this.playlistLength = playlistLength;
	}



	public String getSong() {
		return song;
	}



	private void setSong(String song) {
		this.song = song;
		notifyObserver("Song", "song", getSong());
	}



	public String getSongID() {
		return songID;
	}



	private void setSongID(String songID) {
		this.songID = songID;
		
	}



	public String getNextSong() {
		return nextSong;
	}



	private void setNextSong(String nextSong) {
		this.nextSong = nextSong;
	}



	public String getNextSongID() {
		return nextSongID;
	}



	private void setNextSongID(String nextSongID) {
		this.nextSongID = nextSongID;
	}



	public int getBitRate() {
		return bitRate;
	}



	private void setBitRate(int bitRate) {
		this.bitRate = bitRate;
	}



	public int getxFade() {
		return xFade;
	}



	private void setxFade(int xFade) {
		this.xFade = xFade;
	}



	public double getMixrampDb() {
		return mixrampDb;
	}



	private void setMixrampDb(double mixrampDb) {
		this.mixrampDb = mixrampDb;
	}



	public String getMixrampDelay() {
		return mixrampDelay;
	}



	private void setMixrampDelay(String mixrampDelay) {
		this.mixrampDelay = mixrampDelay;
	}



	public int getUpdatingDb() {
		return updatingDb;
	}



	private void setUpdatingDb(int updatingDb) {
		this.updatingDb = updatingDb;
	}

}
