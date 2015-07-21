package com.bono.zero.model;

/**
 * <p>Title: Song</p>
 * 
 * <p>Description: Class song stores the variables of a song given by the
 * MPD server. </p>
 * 
 * 
 * @author bono
 * @version 0.1
 *
 */

public class Song {
	
	private String file;
	private String last_modified;
	private String time;
	private String artist;
	private String album;
	private String title;
	private String track;
	private String genre;
	private String date;
	private String pos;
	private String id;
	
	private int list_pos;
	
	public Song() {}
	
	@Override
	public String toString() {
		String s = "file: "+file+" Last-Modified: "+last_modified+" Time: "+time+"\n Artist: "+artist+" Album: "+album+
				" Title: "+title+" Track: "+track+" Genre: "+genre+" Date: "+date+" Pos: "+pos+" Id: "+id+" list_pos: "+Integer.toString(list_pos);
		return s;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		Song song = (Song) o;
		if (file == null) {
			if (song.file != null) {
				return false;
			}
		} else if (!file.equals(song.file)) {
			return false;
		}
		if (last_modified == null) {
			if (song.last_modified != null) {
				return false;
			}
		} else if (!last_modified.equals(song.last_modified)) {
			return false;
		}
		if (time == null) {
			if (song.time != null) {
				return false;
			}
		} else if (!time.equals(song.time)) {
			return false;
		}
		if (artist == null) {
			if (song.artist != null) {
				return false;
			}
		} else if (!artist.equals(song.artist)) {
			return false;
		}
		if (album == null) {
			if (song.album != null) {
				return false;
			}
		} else if (!album.equals(song.album)) {
			return false;
		}
		if (title == null) {
			if (song.title != null) {
				return false;
			}
		} else if (!title.equals(song.title)) {
			return false;
		}
		if (track == null) {
			if (song.track != null) {
				return false;
			}
		} else if (!track.equals(song.track)) {
			return false;
		}
		if (genre == null) {
			if (song.genre != null) {
				return false;
			}
		} else if (!genre.equals(song.genre)) {
			return false;
		}
		if (date == null) {
			if (song.date != null) {
				return false;
			}
		} else if (!date.equals(song.date)) {
			return false;
		}
		if (pos == null) {
			if (song.pos != null) {
				return false;
			}
		} else if (!pos.equals(song.pos)) {
			return false;
		}
		if (id == null) {
			if (song.id != null) {
				return false;
			}
		} else if (!id.equals(song.id)) {
			return false;
		}
		if (list_pos != song.list_pos) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((last_modified == null) ? 0 : last_modified.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((artist == null) ? 0 : artist.hashCode());
		result = prime * result + ((album == null) ? 0 : album.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((track == null) ? 0 : track.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + list_pos;
		return result;
	}
	
	public int getList_pos() {
		return list_pos;
	}
	
	public void setList_pos(String pos) {
		list_pos = Integer.parseInt(pos);
	}

	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * @return the last_modified
	 */
	public String getLast_modified() {
		return last_modified;
	}

	/**
	 * @param last_modified the last_modified to set
	 */
	public void setLast_modified(String last_modified) {
		this.last_modified = last_modified;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * @param artist the artist to set
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}

	/**
	 * @param album the album to set
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the track
	 */
	public String getTrack() {
		return track;
	}

	/**
	 * @param track the track to set
	 */
	public void setTrack(String track) {
		this.track = track;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the pos
	 */
	public String getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(String pos) {
		this.pos = pos;
		setList_pos(pos);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
