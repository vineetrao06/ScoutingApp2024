package com.team2813.scouting_app.formUI.formPagerFragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.team2813.scouting_app.R;
import com.team2813.scouting_app.formUI.FormActivity;
import com.team2813.scouting_app.formUI.formFragments.CounterFragment;
import com.team2813.scouting_app.formUI.formFragments.DataFragmentInterface;
import com.team2813.scouting_app.formUI.formFragments.TextFragment;
import com.team2813.scouting_app.formUI.formFragments.ToggleFragment;

import java.util.ArrayList;

public class EndgameFragment extends Fragment {

    ViewPager viewPager;

    private ArrayList<DataFragmentInterface> dataFragments;

    public EndgameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fm_fragment_endgame, container, false);

        viewPager = getActivity().findViewById(R.id.contentView);

        // Initialize your EditTexts here
        EditText climbStartEditText = (EditText) view.findViewById(R.id.climb_start_textbox);
        EditText climbEndEditText = (EditText) view.findViewById(R.id.climb_end_textbox);

        // Define the InputFilter for time format
        InputFilter timeFormatFilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if (source.length() == 0) {
                    return null; // Allow deletion
                }

                // Replace spaces with colons
                String replacement = source.toString().replace(" ", ":");

                // Build the resulting text to check its format
                String newText = dest.subSequence(0, dstart) + replacement + dest.subSequence(dend, dest.length());

                // Check if we should automatically insert a colon after two digits
                if (newText.matches("[0-9]{2}") && dstart == 2 && dend - dstart == 0) {
                    replacement += ":";
                } else if (!newText.matches("([0-9]{1,2}):?([0-9]{0,2})?")) {
                    return ""; // Reject any format that doesn't match
                }

                // Allow the modified input
                return replacement;
            }
        };



        // Set the same InputFilter to both EditTexts
        climbStartEditText.setFilters(new InputFilter[]{timeFormatFilter});
        climbEndEditText.setFilters(new InputFilter[]{timeFormatFilter});

        dataFragments = new ArrayList<>();

        viewPager = getActivity().findViewById(R.id.contentView);

        dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.buddy_climb_counter));
        dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.spotlight_counter));

        ((FormActivity) getActivity()).addDataFragments(dataFragments);

        return view;
    }
}
