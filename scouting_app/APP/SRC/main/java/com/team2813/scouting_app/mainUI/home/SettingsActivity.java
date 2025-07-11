package com.team2813.scouting_app.mainUI.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.team2813.scouting_app.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class SettingsActivity extends AppCompatActivity {

    TextView settingsName;
    TextView settingsCompetition;
    Button scoutingListButton;

    final int PICKFILE_RESULT_CODE = 738;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mn_activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbarSettings);
        settingsName = findViewById(R.id.scouterName);
        settingsName.setText(getApplication().getSharedPreferences("prefs", MODE_PRIVATE).getString("scouterName", "Squiwardo"));
        settingsCompetition = findViewById(R.id.competitionName);
        settingsCompetition.setText(getApplication().getSharedPreferences("prefs", MODE_PRIVATE).getString("compName", "FTC"));
        scoutingListButton = findViewById(R.id.scoutingListButton);
        File file = new File(getExternalFilesDir(null) + "/importData.txt");
        if(file.exists()) scoutingListButton.setText("File loaded! Reload?");
        scoutingListButton.setOnClickListener(view -> {
            Intent fileChooser = new Intent(Intent.ACTION_GET_CONTENT);
            fileChooser.addCategory(Intent.CATEGORY_OPENABLE);
            fileChooser.setType("text/plain");

            startActivityForResult(Intent.createChooser(fileChooser, "Choose a file"), PICKFILE_RESULT_CODE);
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            SharedPreferences.Editor editor = getApplication().getSharedPreferences("prefs", MODE_PRIVATE).edit();
            editor.putString("scouterName", settingsName.getText().toString());
            editor.putString("compName", settingsCompetition.getText().toString());
            editor.commit();

            finish();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICKFILE_RESULT_CODE && resultCode == Activity.RESULT_OK){
                FileInputStream in = (FileInputStream) getContentResolver().openInputStream( data.getData());
                File copied = new File(getExternalFilesDir(null) + "/importData.txt");
                FileOutputStream out = new FileOutputStream(copied);
                FileChannel inChannel = in.getChannel();
                FileChannel outChannel = out.getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                in.close();
                out.close();
                scoutingListButton.setText("File loaded! Reload?");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
}
