package com.team2813.scouting_app.formUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.team2813.scouting_app.FileEditor;
import com.team2813.scouting_app.QRPopupActivity;
import com.team2813.scouting_app.QRPopupActivityQualitative;
import com.team2813.scouting_app.R;
import com.team2813.scouting_app.formUI.formFragments.DataFragmentInterface;
import com.team2813.scouting_app.formUI.formPagerFragments.AutonomousFragment;
import com.team2813.scouting_app.formUI.formPagerFragments.EndgameFragment;
import com.team2813.scouting_app.formUI.formPagerFragments.TeleopFragment;
import com.team2813.scouting_app.formUI.formPagerFragments.IntroFragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class FormActivityQualitative extends AppCompatActivity {

    String formName;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;
    FileEditor fileEditor = new FileEditor();
    ArrayList<DataFragmentInterface> items = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_activity_form_qualitative);

        Bundle b = getIntent().getExtras();

        viewPager = findViewById(R.id.formPager);

        toolbar = findViewById(R.id.toolbarForm);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.qrCodeButton) {
            Bundle b = new Bundle();
//
//            // previous issue was the page not rendering
//
            EditText nameInputField = (EditText) findViewById(R.id.name);
            EditText matchNumInputField = (EditText) findViewById(R.id.matchNum);
            EditText teamNumInputField = (EditText) findViewById(R.id.teamNum);
            EditText offenseInputField = (EditText) findViewById(R.id.notes_box);
            EditText defenseInputField = (EditText) findViewById(R.id.notes_box2);

            String scouterName = nameInputField.getText().toString();
            String matchNumber = matchNumInputField.getText().toString();
            String teamNumber = teamNumInputField.getText().toString();
            String offense = offenseInputField.getText().toString();
            String defense = defenseInputField.getText().toString();

            b.putString("name", scouterName);
            b.putString("match_num", matchNumber);
            b.putString("team_num", teamNumber);
            b.putString("offense", offense);
            b.putString("defense", defense);

            Intent i = new Intent(this, QRPopupActivityQualitative.class);
            i.putExtras(b);
            startActivity(i);
            return true;
        }
        else if(id == R.id.saveButton) finish();

        return super.onOptionsItemSelected(item);
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

