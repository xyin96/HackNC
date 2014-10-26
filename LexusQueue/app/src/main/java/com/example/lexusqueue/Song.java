package com.example.lexusqueue;

import android.graphics.Bitmap;

public class Song {
	private long id;
	private String title;
	private String artist;
    private Bitmap bm;

	public Song(long id, String title, String artist, Bitmap bm){
		this.id = id;
		this.title = title;
		this.artist = artist;
        this.bm = bm;
	}

	public long getID(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getArtist(){
		return artist;
	}

	public String toString(){
		return title + " - " + getArtist();
	}

    public Bitmap getBm() {
        return bm;
    }
}
