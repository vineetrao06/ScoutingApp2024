package com.team2813.scouting_app.formUI.formFragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.team2813.scouting_app.R;

public class TextFragment extends Fragment implements DataFragmentInterface {

    private String name;
    private String text;

    public TextFragment(){
    }

    TextView name_view;
    EditText input_view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dt_fragment_text, container, false);
        name_view = view.findViewById(R.id.nameView);
        input_view = view.findViewById(R.id.notes_box);

        name_view.setText(name);

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
        text = input_view.getText().toString() + " ";
        return text;
    }

    @Override
    public void setValue(String value) {
        Log.d("asdf", "Set" + value);
        text = value;
        input_view.setText(text);
    }

    @Override
    public String getType() {
        return "text";
    }

    @Override
    public String getName() {
        return name;
    }
}
