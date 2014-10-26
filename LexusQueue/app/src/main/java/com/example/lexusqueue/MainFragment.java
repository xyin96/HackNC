package com.example.lexusqueue;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    public MainFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		ArrayList<String> data = new ArrayList<String>();
		data.add("Host");
		data.add("Add Song");
		ListView list = (ListView) view.findViewById(R.id.mainList);
		list.setAdapter(new CardAdapter(data, getActivity()));
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				FragmentManager fm = getFragmentManager();
				switch(i){
					case 0:
						fm.beginTransaction().replace(R.id.container, new HostFragment())
								.addToBackStack(null).commit();
						break;
					case 1:
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("*/*");//audio/*");
                        startActivityForResult(intent, 1);
						break;
				}
			}
		});

		return view;
	}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri file = data.getData();
            Uri[] mFileUris = new Uri[1];
            Log.i("hello", file.toString());
            mFileUris[0] = file;
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
            nfcAdapter.setBeamPushUris(mFileUris,getActivity());
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.container, new ClientFragment())
                    .addToBackStack(null).commit();
        }
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
