package com.example.lexusqueue;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
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
						fm.beginTransaction().replace(R.id.container, new ClientFragment())
								.addToBackStack(null).commit();
						break;
				}
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
