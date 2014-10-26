package com.example.lexusqueue;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
		MediaPlayer.OnCompletionListener{
	private MediaPlayer player;
	private ArrayList<Song> songs;
	private int songPos;
	private final IBinder musicBind = new MusicBinder();

	public MusicService() {

    }

	public void onCreate(){
		super.onCreate();
		songPos = 0;
		player = new MediaPlayer();
		init();
	}

	public void init(){
		player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
		player.setOnPreparedListener(this);
		player.setOnCompletionListener(this);
		player.setOnErrorListener(this);
	}

	public void setList(ArrayList<Song> songs){
		this.songs = songs;
	}

	public void playSong(){
		player.reset();
		Song playSong = songs.get(songPos);
		long curSong = playSong.getID();
		Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, curSong);
		try{
			player.setDataSource(getApplicationContext(), uri);
		} catch(Exception e){
			Log.e("MUSIC SERVICE", "Error setting data source", e);
		}
		player.prepareAsync();
	}

	public void setSong(int songPos){
		this.songPos = songPos;
	}

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

	@Override
	public boolean onUnbind(Intent intent) {
		player.stop();
		player.release();
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mediaPlayer) {

	}

	@Override
	public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mediaPlayer) {
		mediaPlayer.start();
	}

	public class MusicBinder extends Binder {
		MusicService getService(){
			return MusicService.this;
		}
	}
}
