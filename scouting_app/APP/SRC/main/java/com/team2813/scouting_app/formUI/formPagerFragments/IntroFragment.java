package com.team2813.scouting_app.formUI.formPagerFragments;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.team2813.scouting_app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IntroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroFragment newInstance(String param1, String param2) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro, container, false);

        // Set up the Spinner
        Spinner spinnerOptions = view.findViewById(R.id.defense_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.starting_position_options, R.layout.dropdown_view_item);

        // Change the color of the Spinner arrow
        spinnerOptions.getBackground().setColorFilter(getResources().getColor(R.color.COLOR5), PorterDuff.Mode.SRC_ATOP);

        // Set the adapter to the Spinner
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerOptions.setAdapter(adapter);

        // Uncomment and update these lines if you have other fragments inside this fragment
        // dataFragments = new ArrayList<>();
        // dataFragments.add((CounterFragment) getChildFragmentManager().findFragmentById(R.id.some_child_fragment));
        // ((FormActivity) getActivity()).addDataFragments(dataFragments);

        return view;
    }

}