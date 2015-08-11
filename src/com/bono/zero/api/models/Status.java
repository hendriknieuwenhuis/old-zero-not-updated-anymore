package com.bono.zero.api.models;


/**
 * Created by hendriknieuwenhuis on 30/07/15.
 */
public class Status {

    //volume: 0-100
    private Property volume = new Property("volume");

    //repeat: 0 or 1
    private Property repeat = new Property("repeat");

    //random: 0 or 1
    private Property random = new Property("random");

    //single: [2] 0 or 1
    private Property single = new Property("single");

    //consume: [2] 0 or 1
    private Property consume = new Property("consume");

    //playlist: 31-bit unsigned integer, the playlist version number
    private Property playlist = new Property("playlist");

    //playlistlength: integer, the length of the playlist
    private Property playlistlength = new Property("playlistlength");

    //state: play, stop, or pause
    private Property state = new Property("state");

    //song: playlist song number of the current song stopped on or playing
    private Property song = new Property("song");

    //songid: playlist songid of the current song stopped on or playing
    private Property songid = new Property("songid");

    //nextsong: [2] playlist song number of the next song to be played
    private Property nextsong = new Property("nextsong");

    //nextsongid: [2] playlist songid of the next song to be played
    private Property nextsongid = new Property("nextsongid");

    //time: total time elapsed (of current playing/paused song)
    private Property time = new Property("time");

    //elapsed: [3] Total time elapsed within the current song, but with higher resolution.
    private Property elapsed = new Property("elapsed");

    //duration: [4] Duration of the current song in seconds.
    private Property duration = new Property("duration");

    //bitrate: instantaneous bitrate in kbps
    private Property bitrate = new Property("bitrate");

    //xfade: crossfade in seconds
    private Property xfade = new Property("xfade");

    //mixrampdb: mixramp threshold in dB
    private Property mixrampdb = new Property("mixrampdb");

    //mixrampdelay: mixrampdelay in seconds
    private Property mixrampdelay = new Property("mixrampdelay");

    //audio: sampleRate:bits:channels
    private Property audio = new Property("audio");

    //updating_db: job id
    private Property updating = new Property("updating");

    //error: if there is an error, returns message here
    private Property error = new Property("error");

    public Property getVolume() {
        return volume;
    }

    public Property getRepeat() {
        return repeat;
    }

    public Property getRandom() {
        return random;
    }

    public Property getSingle() {
        return single;
    }

    public Property getConsume() {
        return consume;
    }

    public Property getPlaylist() {
        return playlist;
    }

    public Property getPlaylistlength() {
        return playlistlength;
    }

    public Property getState() {
        return state;
    }

    public Property getSong() {
        return song;
    }

    public Property getSongid() {
        return songid;
    }

    public Property getNextsong() {
        return nextsong;
    }

    public Property getNextsongid() {
        return nextsongid;
    }

    public Property getTime() {
        return time;
    }

    public Property getElapsed() {
        return elapsed;
    }

    public Property getDuration() {
        return duration;
    }

    public Property getBitrate() {
        return bitrate;
    }

    public Property getXfade() {
        return xfade;
    }

    public Property getMixrampdb() {
        return mixrampdb;
    }

    public Property getMixrampdelay() {
        return mixrampdelay;
    }

    public Property getAudio() {
        return audio;
    }

    public Property getUpdating() {
        return updating;
    }

    public Property getError() {
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
