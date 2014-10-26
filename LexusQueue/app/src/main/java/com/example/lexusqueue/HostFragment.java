package com.example.lexusqueue;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class HostFragment extends Fragment {
	private Intent playIntent;
	private boolean musicBound = false;
	private CardAdapter adapter;
	private ArrayList<Song> songs;
    protected Uri[] fUri = new Uri[1];
	private MusicService musicSrv;

	public HostFragment() {

    }

	@Override
	public void onStart() {
		super.onStart();
		if(playIntent == null){
			Context context = getActivity();
			playIntent = new Intent(context, MusicService.class);
			context.bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
			context.startService(playIntent);
		}
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		PathFileObserver myFileObserver = new PathFileObserver(Environment.getExternalStorageDirectory().toString()+"/beam/");
		myFileObserver.startWatching();
		Log.d("test", myFileObserver.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_host_list, container, false);

		songs = new ArrayList<Song>();
		ListView list = (ListView) view.findViewById(R.id.list);
		ImageButton play = (ImageButton) view.findViewById(R.id.playButton);
		ImageButton back = (ImageButton) view.findViewById(R.id.backButton);
		ImageButton next = (ImageButton) view.findViewById(R.id.nextButton);

		adapter = new CardAdapter(songs, getActivity());
		list.setAdapter(adapter);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				musicSrv.setSong(i);
				musicSrv.playSong();
			}
		});

		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});

		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
		super.onDetach();
    }

	@Override
	public void onDestroy() {
		getActivity().stopService(playIntent);
		musicSrv = null;
		super.onDestroy();
	}


	private ServiceConnection musicConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
			musicSrv = binder.getService();
			musicSrv.setList(songs);
			musicBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			musicBound = false;
		}
	};

	public class PathFileObserver extends FileObserver{
		static final String TAG="FILEOBSERVER";
		/**
		 * should be end with File.separator
		 */
		String rootPath;
		static final int mask = FileObserver.MOVED_TO;

		String file;
		public PathFileObserver(String root){
			super(root, mask);
			file = root;
			/*
			if (! root.endsWith(File.separator)){
				root += File.separator;
			}
			*/
			rootPath = root;
		}

		public void onEvent(int event, String path) {
			switch(event){
				case FileObserver.MOVED_TO:
					File f = new File(file+path);
					if(!f.toString().substring(f.toString().length()-4).equals(".mp3")) {
                        f.renameTo(new File(file + path + ".mp3"));
                    }
					Log.d("test", "@" + f);


                    final String filePathThis = file + path;
                    Log.i("test",path);

                    MediaScannerConnection.MediaScannerConnectionClient mediaScannerClient = new
                            MediaScannerConnection.MediaScannerConnectionClient() {
                                private MediaScannerConnection msc = null;
                                {
                                    msc = new MediaScannerConnection(getActivity().getApplicationContext(), this);
                                    msc.connect();
                                }

                                public void onMediaScannerConnected(){
                                    msc.scanFile(filePathThis, null);
                                }


                                public void onScanCompleted(String path, Uri uri) {
                                    Log.d("test", "#" + uri.toString());

                                    Uri uri2 = Uri.parse(uri.toString());
                                    fUri[0] = uri2;
                                    Log.d("test", "$" + uri2.toString());
                                    msc.disconnect();
                                    callback();
                                }
                            };


					break;
							}
		}

        void callback(){
            //Uri uri = Uri.fromFile(f);
            //Log.d("test", "#" + uri.toString());
            Log.d("test", "!" + fUri[0].toString());
            Cursor cursor = getActivity().getContentResolver()
                    .query(fUri[0], null, null, null, null, null);
            if(cursor.moveToFirst()){
                int titleColumn = cursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.TITLE);
                int idColumn = cursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media._ID);
                int artistColumn = cursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.ARTIST);
                do {
                    long id = cursor.getLong(idColumn);
                    String title = cursor.getString(titleColumn);
                    String artist = cursor.getString(artistColumn);
                    songs.add(new Song(id, title, artist));
                } while (cursor.moveToNext());
            }
            Handler mainHandler = new Handler(Looper.getMainLooper());
            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();

                }
            };
            mainHandler.post(myRunnable);
        }

		public void close(){
			super.finalize();
		}
	}
}
