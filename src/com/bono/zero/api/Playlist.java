package com.bono.zero.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendriknieuwenhuis on 29/07/15.
 */
public class Playlist {

    private static  final String FILE = "file:";
    private static final String LAST_MODIFIED = "Last-Modified:";
    private static final String TITLE = "Title:";
    private static final String ALBUM = "Album:";
    private static final String ARTIST = "Artist:";
    private static final String GENRE = "Genre:";
    private static final String DATE = "Date:";
    private static final String TRACK = "Track:";
    private static final String ALBUM_ARTIST = "AlbumArtist:";
    private static final String TIME = "Time:";
    private static final String POS = "Pos:";
    private static final String ID = "Id:";

    private List<Song> playlist;

    /*
    Creates a new ArrayList called playlist holding
    song objects containing the information of the songs
    in the playlist.
     */
    public void populatePlaylist(List<String> entry) {
        playlist = new ArrayList<>();
        Song song = null;
        for (String line : entry) {
            if (line.startsWith(FILE)) {
                song = new Song();
                song.setFile(line.substring((FILE.length() + 1)));
            } else if (line.startsWith(LAST_MODIFIED)) {
                song.setLastModified(line.substring((LAST_MODIFIED.length() + 1)));
            } else if (line.startsWith(TITLE)) {
                song.setTitle(line.substring((TITLE.length() + 1)));
            } else  if (line.startsWith(ALBUM)) {
                song.setAlbum(line.substring((ALBUM.length() + 1)));
            } else if (line.startsWith(ARTIST)) {
                song.setArtist(line.substring((ARTIST.length() + 1)));
            } else if (line.startsWith(GENRE)) {
                song.setGenre(line.substring((GENRE.length() + 1)));
            } else if (line.startsWith(DATE)) {
                song.setDate(line.substring((DATE.length() + 1)));
            } else if (line.startsWith(TRACK)) {
                song.setTrack(line.substring((TRACK.length() + 1)));
            } else if (line.startsWith(ALBUM_ARTIST)) {
                song.setAlbumArtist(line.substring((ALBUM_ARTIST.length() + 1)));
            } else if (line.startsWith(TIME)) {
                song.setTime(line.substring((TIME.length() + 1)));
            } else if (line.startsWith(POS)) {
                song.setPos(line.substring((POS.length() + 1)));
            } else if (line.startsWith(ID)) {
                song.setId(line.substring((ID.length() + 1)));
                playlist.add(song);
            }
        }
    }

    public List<Song> getPlaylist() {
        return playlist;
    }
}
