package com.example.lexusqueue;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class HostFragment extends Fragment {

    public HostFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_host_list, container, false);

		ArrayList<String> data = new ArrayList<String>();
		ListView list = (ListView) view.findViewById(R.id.list);
		ImageButton play = (ImageButton) view.findViewById(R.id.playButton);
		ImageButton back = (ImageButton) view.findViewById(R.id.backButton);
		ImageButton next = (ImageButton) view.findViewById(R.id.nextButton);

		list.setAdapter(new CardAdapter(data, getActivity()));

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

}
