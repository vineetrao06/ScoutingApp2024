package com.team2813.scouting_app.formUI.formFragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.team2813.scouting_app.R;

public class CounterFragment extends Fragment implements DataFragmentInterface {

    private int counterNumber = 0;
    private String name;

    public CounterFragment() {
    }

    TextView name_view;
    TextView num_view;
    Button addButton;
    Button subButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dt_fragment_counter, container, false);
        name_view = (TextView) view.findViewById(R.id.nameView);
        name_view.setText(name);

        num_view = (TextView) view.findViewById(R.id.numView);
        addButton = (Button) view.findViewById(R.id.addButton);
        subButton = (Button) view.findViewById(R.id.subButton);

        addButton.setOnClickListener(v -> {
            counterNumber += 1;
            num_view.setText(Integer.toString(counterNumber));
        });

        subButton.setOnClickListener(v -> {
            if (counterNumber >= 1) {
                counterNumber -= 1;
                num_view.setText(Integer.toString(counterNumber));
            }
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

    public String getValue(){
        return Integer.toString(counterNumber);
    }

    @Override
    public void setValue(String value) {
        counterNumber = Integer.parseInt(value);
        num_view.setText(Integer.toString(counterNumber));
    }

    public String getType() {
        return "counter";
    }
    public String getName() {
        return name;
    }
}
