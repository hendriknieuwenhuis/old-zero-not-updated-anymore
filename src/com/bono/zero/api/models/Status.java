package com.bono.zero.api.models;


/**
 * Created by hendriknieuwenhuis on 30/07/15.
 */
public class Status {

    //volume: 0-100
    private ServerProperty volume = new ServerProperty();

    //repeat: 0 or 1
    private ServerProperty repeat = new ServerProperty();

    //random: 0 or 1
    private ServerProperty random = new ServerProperty();

    //single: [2] 0 or 1
    private ServerProperty single = new ServerProperty();

    //consume: [2] 0 or 1
    private ServerProperty consume = new ServerProperty();

    //playlist: 31-bit unsigned integer, the playlist version number
    private ServerProperty playlist = new ServerProperty();

    //playlistlength: integer, the length of the playlist
    private ServerProperty playlistlength = new ServerProperty();

    //state: play, stop, or pause
    private ServerProperty state = new ServerProperty();

    //song: playlist song number of the current song stopped on or playing
    private ServerProperty song = new ServerProperty();

    //songid: playlist songid of the current song stopped on or playing
    private ServerProperty songid = new ServerProperty();

    //nextsong: [2] playlist song number of the next song to be played
    private ServerProperty nextsong = new ServerProperty();

    //nextsongid: [2] playlist songid of the next song to be played
    private ServerProperty nextsongid = new ServerProperty();

    //time: total time elapsed (of current playing/paused song)
    private ServerProperty time = new ServerProperty();

    //elapsed: [3] Total time elapsed within the current song, but with higher resolution.
    private ServerProperty elapsed = new ServerProperty();

    //duration: [4] Duration of the current song in seconds.
    private ServerProperty duration = new ServerProperty();

    //bitrate: instantaneous bitrate in kbps
    private ServerProperty bitrate = new ServerProperty();

    //xfade: crossfade in seconds
    private ServerProperty xfade = new ServerProperty();

    //mixrampdb: mixramp threshold in dB
    private ServerProperty mixrampdb = new ServerProperty();

    //mixrampdelay: mixrampdelay in seconds
    private ServerProperty mixrampdelay = new ServerProperty();

    //audio: sampleRate:bits:channels
    private ServerProperty audio = new ServerProperty();

    //updating_db: job id
    private ServerProperty updating = new ServerProperty();

    //error: if there is an error, returns message here
    private ServerProperty error = new ServerProperty();

    public ServerProperty getVolume() {
        return volume;
    }

    public ServerProperty getRepeat() {
        return repeat;
    }

    public ServerProperty getRandom() {
        return random;
    }

    public ServerProperty getSingle() {
        return single;
    }

    public ServerProperty getConsume() {
        return consume;
    }

    public ServerProperty getPlaylist() {
        return playlist;
    }

    public ServerProperty getPlaylistlength() {
        return playlistlength;
    }

    public ServerProperty getState() {
        return state;
    }

    public ServerProperty getSong() {
        return song;
    }

    public ServerProperty getSongid() {
        return songid;
    }

    public ServerProperty getNextsong() {
        return nextsong;
    }

    public ServerProperty getNextsongid() {
        return nextsongid;
    }

    public ServerProperty getTime() {
        return time;
    }

    public ServerProperty getElapsed() {
        return elapsed;
    }

    public ServerProperty getDuration() {
        return duration;
    }

    public ServerProperty getBitrate() {
        return bitrate;
    }

    public ServerProperty getXfade() {
        return xfade;
    }

    public ServerProperty getMixrampdb() {
        return mixrampdb;
    }

    public ServerProperty getMixrampdelay() {
        return mixrampdelay;
    }

    public ServerProperty getAudio() {
        return audio;
    }

    public ServerProperty getUpdating() {
        return updating;
    }

    public ServerProperty getError() {
        return error;
    }

    @Override
    public String toString() {
        return "Status{" +
                "volume=" + volume.toString() +
                ", repeat=" + repeat.toString() +
                ", random=" + random.toString() +
                ", single=" + single.toString() +
                ", consume=" + consume.toString() +
                ", playlist=" + playlist.toString() +
                ", playlistlength=" + playlistlength.toString() +
                ", state=" + state.toString() +
                ", song=" + song.toString() +
                ", songid=" + songid.toString() +
                ", nextsong=" + nextsong.toString() +
                ", nextsongid=" + nextsongid.toString() +
                ", time=" + time.toString() +
                ", elapsed=" + elapsed.toString() +
                ", duration=" + duration.toString() +
                ", bitrate=" + bitrate.toString() +
                ", xfade=" + xfade.toString() +
                ", mixrampdb=" + mixrampdb.toString() +
                ", mixrampdelay=" + mixrampdelay.toString() +
                ", audio=" + audio.toString() +
                ", updating=" + updating.toString() +
                ", error=" + error.toString() +
                '}';
    }
}
