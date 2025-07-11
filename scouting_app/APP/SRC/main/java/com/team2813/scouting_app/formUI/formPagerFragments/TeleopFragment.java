package com.team2813.scouting_app.formUI.formPagerFragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.team2813.scouting_app.R;
import com.team2813.scouting_app.formUI.FormActivity;
import com.team2813.scouting_app.formUI.formFragments.CounterFragment;
import com.team2813.scouting_app.formUI.formFragments.DataFragmentInterface;

import java.util.ArrayList;

public class TeleopFragment extends Fragment {

    ViewPager viewPager;

    private ArrayList<DataFragmentInterface> dataFragments;

    public TeleopFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fm_fragment_teleop, container, false);

        // Set up the Spinner
        Spinner spinnerOptions = view.findViewById(R.id.defense_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.defense_options, R.layout.dropdown_view_item);

        spinnerOptions.getBackground().setColorFilter(getResources().getColor(R.color.COLOR5), PorterDuff.Mode.SRC_ATOP);

// Keep the dropdown view resource as it is
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerOptions.setAdapter(adapter);


        dataFragments = new ArrayList<>();

        viewPager = getActivity().findViewById(R.id.contentView);


        dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.speaker_counter1));
        dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.speaker_counter3));
        dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.amp_counter1));
        dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.amp_counter3));
        dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.amp_button1));
        dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.herd_counter));

        ((FormActivity) getActivity()).addDataFragments(dataFragments);

        return view;
    }
}
