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

public class MainFragment extends Fragment implements View.OnClickListener{

    public MainFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
        view.findViewById(R.id.hostButton).setOnClickListener(this);
        view.findViewById(R.id.selectButton).setOnClickListener(this);

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
            fm.beginTransaction().replace(R.id.container, ClientFragment.newInstance(mFileUris[0]))
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

    @Override
    public void onClick(View view) {
        FragmentManager fm = getFragmentManager();
        switch(view.getId()){
            case R.id.hostButton:
                fm.beginTransaction().replace(R.id.container, new HostFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.selectButton:
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("audio/*");//audio/*");
                startActivityForResult(intent, 1);
                break;
        }
    }
}
