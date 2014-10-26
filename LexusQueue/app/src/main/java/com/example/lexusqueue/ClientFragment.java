package com.example.lexusqueue;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ClientFragment extends Fragment {
    private Uri uri;

    public ClientFragment(){};

	public static ClientFragment newInstance(Uri uri){
        ClientFragment c = new ClientFragment();
        c.uri = uri;
        return c;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_client, container, false);

        MediaMetadataRetriever m = new MediaMetadataRetriever();
        m.setDataSource(getActivity(), uri);



        Bitmap bm = null;
        try {
            bm = BitmapFactory.decodeByteArray(m.getEmbeddedPicture(), 0, m.getEmbeddedPicture().length);
            if (bm != null) {
                ((ImageView) view.findViewById(R.id.album_art_imageview)).setImageBitmap(bm);
            }
        } catch( Exception e ){

        }
        ((TextView)view.findViewById(R.id.client_title)).setText(m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        ((TextView)view.findViewById(R.id.client_artist)).setText(m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));


        (view.findViewById(R.id.client_confirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
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
}
