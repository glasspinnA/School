package com.github.beetsbyninn.beets;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Fragment class that represent fragment where you choose a song to play with
 */
public class SongListFragment extends Fragment {
    private ArrayList<Song> mSongList = new ArrayList<Song>();
    private MainActivity activity ;
    public SongListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = (MainActivity) getActivity();
        mSongList = activity.getSongList();

        return inflater.inflate(R.layout.fragment_songlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        SongListAdapter listAdapter = new SongListAdapter(getActivity(), mSongList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // move to gauge

                Song song = (Song) parent.getItemAtPosition(position);
                activity.setSong(song);
                activity.initGaugeFragment();
            }
        });
    }
}
