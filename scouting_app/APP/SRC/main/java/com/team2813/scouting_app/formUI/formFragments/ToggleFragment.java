package com.team2813.scouting_app.formUI.formFragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.team2813.scouting_app.R;

public class ToggleFragment extends Fragment implements DataFragmentInterface{

    private String name;
    private ToggleButton toggleButton;

    boolean checked = false;

    public ToggleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dt_fragment_toggle, container, false);

        toggleButton = view.findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(v -> {
            checked = toggleButton.isChecked();
        });

        return view;
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.Data_Fragment);

        CharSequence getName = a.getText(R.styleable.Data_Fragment_dt_name);
        if(getName != null) {
            name = (String) getName;
        }

        CharSequence getValue = a.getText(R.styleable.Data_Fragment_dt_value);
        if(getValue != null) {
            setValue((String) getValue);
        }

        a.recycle();
    }

    @Override
    public String getValue() {
        if(checked) return "t";
        else return "f";
    }

    @Override
    public void setValue(String value) {
        if(value.equals("t")) {
            checked = true;
            toggleButton.setChecked(true);
        }
    }

    @Override
    public String getType() {
        return "toggle";
    }

    @Override
    public String getName() {
        return name;
    }
}
