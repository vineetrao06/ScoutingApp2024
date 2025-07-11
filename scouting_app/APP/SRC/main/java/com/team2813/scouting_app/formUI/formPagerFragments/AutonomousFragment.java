package com.team2813.scouting_app.formUI.formPagerFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.team2813.scouting_app.R;
import com.team2813.scouting_app.formUI.FormActivity;
import com.team2813.scouting_app.formUI.formFragments.CounterFragment;
import com.team2813.scouting_app.formUI.formFragments.DataFragmentInterface;

import java.util.ArrayList;

public class AutonomousFragment extends Fragment {

    ViewPager viewPager;

    private ArrayList<DataFragmentInterface> dataFragments;

    public AutonomousFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fm_fragment_autonomous, container, false);

        viewPager = getActivity().findViewById(R.id.contentView);

        dataFragments = new ArrayList<>();
        dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.speaker_counter_auto));
        dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.amp_counter_auto));
        dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.wing_counter));
        dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.midline_counter));
        dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.missed_counter_auto));
        ((FormActivity) getActivity()).addDataFragments(dataFragments);

        return view;
    }
}
