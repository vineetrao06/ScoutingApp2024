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

public class FormActivity extends AppCompatActivity {

    String formName;
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar;

    private String teamNumber = "2813";
    private String matchNumber = "303";
    private boolean isRedAlliance = false;

    FileEditor fileEditor = new FileEditor();
    ArrayList<DataFragmentInterface> items = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_activity_form);

        Bundle b = getIntent().getExtras();

        viewPager = findViewById(R.id.formPager);

        tabLayout = findViewById(R.id.formTabLayout);
        tabLayout.setupWithViewPager(viewPager);

        toolbar = findViewById(R.id.toolbarForm);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(pagerAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(!checkIsNew()) {
            Log.d("asdf", "test1");
            formName = getIntent().getExtras().getString("fileName");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isRedAlliance) toolbar.setBackgroundColor(getResources().getColor(R.color.COLOR3));
        else toolbar.setBackgroundColor(Color.parseColor("#800000"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    private String isCheckedToString(CheckBox checkBox) {
        return checkBox.isChecked() ? "Yes" : "No";
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

            // previous issue was the page not rendering

            EditText nameInputField = (EditText) findViewById(R.id.name);
            EditText matchNumInputField = (EditText) findViewById(R.id.matchNum);
            EditText teamNumInputField = (EditText) findViewById(R.id.teamNum);
            EditText notesInputField = (EditText) findViewById(R.id.notes_box);
            EditText climbStart =  (EditText) findViewById(R.id.climb_start_textbox);
            EditText climbEnd =  (EditText) findViewById(R.id.climb_end_textbox);


            Spinner startingPosSpinner = (Spinner) findViewById(R.id.starting_zone);
            Spinner defenseSpinner = (Spinner) findViewById(R.id.defense_spinner);

            CheckBox leaveCheckbox = (CheckBox) findViewById(R.id.leave_checkbox);
            CheckBox coopCheckbox = (CheckBox) findViewById(R.id.coop_check);
            CheckBox intakeSourceCheckbox = (CheckBox) findViewById(R.id.source_check);
            CheckBox intakeGroundCheckbox = (CheckBox) findViewById(R.id.ground_check);
            CheckBox trapCheckbox = (CheckBox) findViewById(R.id.trap_checkbox);
            CheckBox preScoutingCheckbox = (CheckBox) findViewById(R.id.prescouting_check);
            CheckBox qualsCheckbox = (CheckBox) findViewById(R.id.quals_check);
            CheckBox playoffsCheckbox = (CheckBox) findViewById(R.id.playoffs_check);
            CheckBox climbCheckbox = (CheckBox) findViewById(R.id.climb_check);



            String scouterName = nameInputField.getText().toString();
            String matchNumber = matchNumInputField.getText().toString();
            String teamNumber = teamNumInputField.getText().toString();
            String startingPosition = startingPosSpinner.getSelectedItem().toString();
            String defense = defenseSpinner.getSelectedItem().toString();
            String climbStartValue = climbStart.getText().toString();
            String climbEndValue = climbEnd.getText().toString();
            String leave = isCheckedToString(leaveCheckbox);
            String coop = isCheckedToString(coopCheckbox);

            String intakeSource = isCheckedToString(intakeSourceCheckbox);
            String intakeGround = isCheckedToString(intakeGroundCheckbox);
            String trap = isCheckedToString(trapCheckbox);
            String preScouting = isCheckedToString(preScoutingCheckbox);
            String quals = isCheckedToString(qualsCheckbox);
            String playoffs = isCheckedToString(playoffsCheckbox);
            String climb = isCheckedToString(climbCheckbox);
            String notes = notesInputField.getText().toString();


            ArrayList<String> fragments = new ArrayList<String>();

            for (DataFragmentInterface c : items) {
                if (c instanceof Fragment) {
                    Fragment fragment = (Fragment) c;
                    int fragmentId = fragment.getId();
                    String fragmentValue = c.getValue();

                    // Assuming you have a valid ID, and it's not 0
                    if (fragmentId != 0) {
                        // Retrieve the fragment tag using the ID
                        String fragmentTag = getResources().getResourceEntryName(fragmentId);
                        fragments.add(fragmentValue);

                        // Log the fragment tag to the console
                        Log.d("FragmentTag", "The fragment tag is: " + fragmentTag);
                    } else {
                        // Handle the case where the fragment ID is invalid (e.g., fragment not attached)
                        Log.w("FragmentTag", "The fragment is not attached or has an invalid ID");
                    }
                }
            }

            Log.d("speaker auto", fragments.get(5));
            Log.d("array", fragments.toString());
            Log.d("array length", Integer.toString(fragments.size()));

            // Fragments
            b.putString("speaker_counter_auto", fragments.get(0));
            b.putString("amp_counter_auto", fragments.get(1));
            b.putString("wing_counter", fragments.get(2));
            b.putString("midline_counter", fragments.get(3));
            b.putString("missed_counter_auto", fragments.get(4));
            b.putString("speaker_counter1", fragments.get(5));
            b.putString("speaker_counter3", fragments.get(6));
            b.putString("amp_counter1", fragments.get(7));
            b.putString("amp_counter3", fragments.get(8));
            b.putString("amp_button1", fragments.get(9));
            b.putString("herd_counter", fragments.get(10));
            b.putString("buddy_climb_counter", fragments.get(11));
            b.putString("spotlight_counter", fragments.get(12));

            // EditTexts
            b.putString("name", scouterName);
            b.putString("match_num", matchNumber);
            b.putString("team_num", teamNumber);

            b.putString("starting_pos", startingPosition);
            b.putString("climb", climb);
            b.putString("defense", defense);
            b.putString("climbStart", climbStartValue);
            b.putString("climbEnd", climbEndValue);
            b.putString("leave", leave);
            b.putString("coop", coop);
            b.putString("intakeSource", intakeSource);
            b.putString("intakeGround", intakeGround);
            b.putString("trap", trap);
            b.putString("notes", notes);
            b.putString("prescouting", preScouting);
            b.putString("quals", quals);
            b.putString("playoffs", playoffs);

            Intent i = new Intent(this, QRPopupActivity.class);
            i.putExtras(b);
            startActivity(i);
            return true;
        }
        else if(id == R.id.saveButton) finish();
        return super.onOptionsItemSelected(item);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private String[] tabTitles = {"Intro", "Autonomous", "Teleoperated", "End Game"};

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new IntroFragment();
                case 1:
                    return new AutonomousFragment();
                case 2:
                    return new TeleopFragment();
                case 3:
                    return new EndgameFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Log.d("asdf", "arg");
                teamNumber = data.getExtras().getString("teamNum");
                matchNumber = data.getExtras().getString("matchNum");
                isRedAlliance = data.getExtras().getBoolean("isRed");
            }
        }
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

    public void addDataFragments(ArrayList<DataFragmentInterface> d){
        Log.d("asdf", "b3");
        items.addAll(d);
        if(!checkIsNew()) loadData();
    }

    private void loadData() {
        try {
            JSONObject infoFile = new JSONObject(getIntent().getExtras().getString("fileJSONObjectString"));
            JSONArray infoData = infoFile.getJSONArray("formItems");
            for (DataFragmentInterface d : items) {
                for (int i = 0; i < infoData.length(); i++) {
                    JSONObject ob = infoData.getJSONObject(i);
                    if (ob.getString("name").equals(d.getName())) {
                        d.setValue(ob.getString("value"));
                        break;
                    }
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private Boolean checkIsNew(){
        if(getIntent().hasExtra("isNew")) return getIntent().getExtras().getBoolean("isNew") == true;
        else return true;
    }

    private Boolean checkIsPreMadeNew(){
        if(getIntent().hasExtra("isMadeNew")) return getIntent().getExtras().getBoolean("isMadeNew") == true;
        else return false;
    }
}

