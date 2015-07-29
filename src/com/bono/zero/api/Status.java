package com.bono.zero.api;

import java.util.List;

/**
 * Created by hendriknieuwenhuis on 27/07/15.
 */
public class Status {



    //volume: 0-100
    private String volume;

    //repeat: 0 or 1
    private String repeat;

    //random: 0 or 1
    private String random;

    //single: [2] 0 or 1
    private String single;

    //consume: [2] 0 or 1
    private String consume;

    //playlist: 31-bit unsigned integer, the playlist version number
    private String playlist;

    //playlistlength: integer, the length of the playlist
    private String playlistlength;

    //state: play, stop, or pause
    private String state;

    //song: playlist song number of the current song stopped on or playing
    private String song;

    //songid: playlist songid of the current song stopped on or playing
    private String songid;

    //nextsong: [2] playlist song number of the next song to be played
    private String nextsong;

    //nextsongid: [2] playlist songid of the next song to be played
    private String nextsongid;

    //time: total time elapsed (of current playing/paused song)
    private String time;

    //elapsed: [3] Total time elapsed within the current song, but with higher resolution.
    private String elapsed;

    //duration: [4] Duration of the current song in seconds.
    private String duration;

    //bitrate: instantaneous bitrate in kbps
    private String bitrate;

    //xfade: crossfade in seconds
    private String xfade;

    //mixrampdb: mixramp threshold in dB
    private String mixrampdb;

    //mixrampdelay: mixrampdelay in seconds
    private String mixrampdelay;

    //audio: sampleRate:bits:channels
    private String audio;

    //updating_db: job id
    private String updating;

    //error: if there is an error, returns message here
    private String error;

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public String getConsume() {
        return consume;
    }

    public void setConsume(String consume) {
        this.consume = consume;
    }

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    public String getPlaylistlength() {
        return playlistlength;
    }

    public void setPlaylistlength(String playlistlength) {
        this.playlistlength = playlistlength;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSongid() {
        return songid;
    }

    public void setSongid(String songid) {
        this.songid = songid;
    }

    public String getNextsong() {
        return nextsong;
    }

    public void setNextsong(String nextsong) {
        this.nextsong = nextsong;
    }

    public String getNextsongid() {
        return nextsongid;
    }

    public void setNextsongid(String nextsongid) {
        this.nextsongid = nextsongid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getElapsed() {
        return elapsed;
    }

    public void setElapsed(String elapsed) {
        this.elapsed = elapsed;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getXfade() {
        return xfade;
    }

    public void setXfade(String xfade) {
        this.xfade = xfade;
    }

    public String getMixrampdb() {
        return mixrampdb;
    }

    public void setMixrampdb(String mixrampdb) {
        this.mixrampdb = mixrampdb;
    }

    public String getMixrampdelay() {
        return mixrampdelay;
    }

    public void setMixrampdelay(String mixrampdelay) {
        this.mixrampdelay = mixrampdelay;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getUpdating() {
        return updating;
    }

    public void setUpdating(String updating) {
        this.updating = updating;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Status{" +
                "volume='" + volume + '\'' +
                ", repeat='" + repeat + '\'' +
                ", random='" + random + '\'' +
                ", single='" + single + '\'' +
                ", consume='" + consume + '\'' +
                ", playlist='" + playlist + '\'' +
                ", playlistlength='" + playlistlength + '\'' +
                ", state='" + state + '\'' +
                ", song='" + song + '\'' +
                ", songid='" + songid + '\'' +
                ", nextsong='" + nextsong + '\'' +
                ", nextsongid='" + nextsongid + '\'' +
                ", time='" + time + '\'' +
                ", elapsed='" + elapsed + '\'' +
                ", duration='" + duration + '\'' +
                ", bitrate='" + bitrate + '\'' +
                ", xfade='" + xfade + '\'' +
                ", mixrampdb='" + mixrampdb + '\'' +
                ", mixrampdelay='" + mixrampdelay + '\'' +
                ", audio='" + audio + '\'' +
                ", updating='" + updating + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
