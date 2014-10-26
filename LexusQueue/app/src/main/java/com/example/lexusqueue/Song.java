package com.example.lexusqueue;

import android.net.Uri;

public class Song {
	private Uri uri;
	private String title;
	private String artist;

	public Song(Uri uri, String title, String artist){
		this.uri = uri;
		this.title = title;
		this.artist = artist;
	}

	public Uri getUri(){
		return uri;
	}

	public String getTitle(){
		return title;
	}

	public String getArtist(){
		return artist;
	}

	public String toString(){
		return title + " - " + artist;
	}
}
