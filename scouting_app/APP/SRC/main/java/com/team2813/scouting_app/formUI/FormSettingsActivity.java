package com.team2813.scouting_app.formUI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.team2813.scouting_app.R;

public class FormSettingsActivity extends AppCompatActivity {

    TextView formName;
    TextView formMatch;
    ToggleButton formColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_activity_form_settings);

        Toolbar toolbar = findViewById(R.id.toolbarFormSettings);
        formName = findViewById(R.id.formName);
        formName.setText(getIntent().getExtras().getString("teamNum"));
        formMatch = findViewById(R.id.formMatch);
        formMatch.setText(getIntent().getExtras().getString("matchNum"));
        formColor = findViewById(R.id.formColor);
        formColor.setChecked(getIntent().getExtras().getBoolean("isRed"));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("teamNum", formName.getText().toString());
                intent.putExtra("matchNum", formMatch.getText().toString());
                intent.putExtra("isRed", formColor.isChecked());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    Log.d("focus", "touchevent");
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
