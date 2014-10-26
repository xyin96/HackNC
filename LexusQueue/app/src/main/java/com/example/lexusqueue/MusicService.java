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
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
		MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener{
	private MediaPlayer player;
	private ArrayList<Song> songs, prev;
	private int songPos;
	private final IBinder musicBind = new MusicBinder();
    private boolean paused = true;
    private int pausePosition;

    public MusicService() {

    }

	public void onCreate(){
		super.onCreate();
		songPos = 0;
		player = new MediaPlayer();
		init();
        prev = new ArrayList<Song>();
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

    public void pauseSong(ImageButton arg1){
        arg1.setImageResource(R.drawable.ic_play_button);
        pausePosition = player.getCurrentPosition();
        player.pause();
        paused = true;
        
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
        prev.add(songs.get(0));
        songs.remove(0);
        if(songPos != songs.size()) {

            playSong();
            HostFragment.adapter.notifyDataSetChanged();
        }
	}

	@Override
	public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mediaPlayer) {
		mediaPlayer.start();
	}

    public void handleCallback(ImageButton arg1) {
        if(!player.isPlaying()){
            arg1.setImageResource(R.drawable.ic_pause);
            playSong();
        }
    }

    public void nextSong() {
        prev.add(songs.get(0));
        songs.remove(0);
        if(songPos != songs.size()) {

            playSong();
            HostFragment.adapter.notifyDataSetChanged();
        }

    }

    public void prevSong(){
        songs.add(0, prev.get(prev.size() - 1));
        prev.remove(prev.size() - 1);
        if(songPos != songs.size()) {

            playSong();
            HostFragment.adapter.notifyDataSetChanged();
        }
        playSong();
        HostFragment.adapter.notifyDataSetChanged();

    }

    public void playBtn(ImageButton arg1){
        arg1.setImageResource(R.drawable.ic_pause);
        if(paused){
            player.start();
            player.seekTo(pausePosition);
        } else {
            if (HostFragment.adapter != null && HostFragment.adapter.getCount() > 0) {
                playSong();
            } else {
                Toast.makeText(getApplicationContext(), "Please beam a song first", Toast.LENGTH_LONG);
            }
        }
    }

    public int getSong(){
        return songPos;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public class MusicBinder extends Binder {
		MusicService getService(){
			return MusicService.this;
		}
	}
}
