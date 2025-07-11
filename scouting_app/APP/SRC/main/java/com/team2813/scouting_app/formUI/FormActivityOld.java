package com.team2813.scouting_app.formUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.team2813.scouting_app.FileEditor;
import com.team2813.scouting_app.QRPopupActivity;
import com.team2813.scouting_app.R;
import com.team2813.scouting_app.formUI.formFragments.DataFragmentInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class FormActivityOld extends AppCompatActivity {

    String formName;

    FileEditor fileEditor = new FileEditor();
    ArrayList<Fragment> items = new ArrayList<>();

    EditText inputName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_activity_form);

        inputName = findViewById(R.id.formName);

        Toolbar toolbar = findViewById(R.id.toolbarForm);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LinearLayout formItems = findViewById(R.id.formPager);
        ArrayList<LinearLayout> layoutRows = null;

//        try {
//            if(checkIsNew()){
//                layoutRows = createTableRows(this, fileEditor.getAssetFile(getApplicationContext(),"INFINITE_RECHARGE_2020"));
//            }
//            else {
//                formName = getIntent().getExtras().getString("fileName");
//                layoutRows = createTableRows(this, fileEditor.getAssetFile(getApplicationContext(), "INFINITE_RECHARGE_2020"), new JSONObject(getIntent().getExtras().getString("fileJSONObjectString")));
//                inputName.setText(getIntent().getExtras().getString("name"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        for(LinearLayout t : layoutRows){
            formItems.addView(t);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.qrCodeButton) {
            String data = inputName.getText().toString() + " " + Calendar.getInstance().getTime().toString() + " " + android.os.Build.MODEL + "/";
            for(Fragment t: items) {
                DataFragmentInterface c = (DataFragmentInterface) t;
                data += c.getValue() + " ";
            }

            Bundle b = new Bundle();
            b.putString("c_value", data);

            Intent i = new Intent(this, QRPopupActivity.class);
            i.putExtras(b);
            startActivity(i);
            return true;
        } else if(id == R.id.saveButton){
            String fileName;
            File saveFile;
            if(checkIsNew()){
                fileName = Calendar.getInstance().getTime().toString() + android.os.Build.MODEL;
                JSONObject info = new JSONObject();
                try {
                    info.put("name", inputName.getText().toString());
                    info.put("date", Calendar.getInstance().getTime().toString());
                    info.put("model", android.os.Build.MODEL);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                saveFile = fileEditor.createFile(FormActivityOld.this, fileName, info);
            }
            else{
                fileName = formName;
                saveFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/" + fileName);
            }

            JSONArray saveParts = new JSONArray();
            for(Fragment t: items) {
                JSONObject content = new JSONObject();
                DataFragmentInterface c = (DataFragmentInterface) t;
                try {
                    content.put("type", c.getType());
                    content.put("name", c.getName());
                    content.put("value", c.getValue());
                } catch (JSONException e) { e.printStackTrace(); }
                saveParts.put(content);
            }
            if(checkIsNew()) fileEditor.addToFile(FormActivityOld.this, saveFile, "", saveParts, "formItems");
            else fileEditor.replaceInFile(FormActivityOld.this, saveFile, "formItems", saveParts);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

//    private ArrayList<LinearLayout> createTableRows(Context context, JSONObject layoutFile) throws JSONException{
//        return createTableRows(context, layoutFile, null);
//    }
//
//    private ArrayList<LinearLayout> createTableRows(Context context, JSONObject layoutFile, JSONObject infoFile) throws JSONException {
//        FragmentManager fragMan = this.getSupportFragmentManager();
//        FragmentTransaction fragTrans = fragMan.beginTransaction();
//
//        ArrayList<LinearLayout> layoutRows = new ArrayList<LinearLayout>();
//
//        //Obtaining layout file
//        JSONArray getLayoutItems = layoutFile.getJSONArray("formItems");
//        JSONArray getInfoItems = null;
//        if(infoFile != null) {
//            getInfoItems = infoFile.getJSONArray("formItems");
//        }
//
//        //Creation of table layout
//        for(int i = 0; i < layoutFile.getInt("layoutRows"); i++){
//            LinearLayout r = new LinearLayout(context);
//            r.setOrientation(LinearLayout.HORIZONTAL);
//            layoutRows.add(r);
//        }
//
//        //Add Form Fragments
//        for (int i = 0; i < getLayoutItems.length(); i++) {
//            //Obtain Fragment data
//            JSONObject itemLayoutInfo = getLayoutItems.getJSONObject(i);
//
//            //Create Fragment Container
//            FrameLayout frame = new FrameLayout(context);
//            frame.setLayoutParams(new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    1));
//            frame.setId(View.generateViewId());
//
//            //Create Fragment and insert it into the correct row
//            layoutRows.get(itemLayoutInfo.getInt("row") - 1).addView(frame);
//            Fragment insert;
//            switch(itemLayoutInfo.getString("type")) {
//                case "counter":
//                    insert = new CounterFragment();
//                    break;
//                case "toggle":
//                    insert = new ToggleFragment();
//                    break;
//                case "text":
//                    insert = new TextFragment();
//                    break;
//                default:
//                    insert = null;
//            }
//
//            Bundle b = new Bundle();
//            b.putString("name", itemLayoutInfo.getString("name"));
//
//            if (getInfoItems != null) {
//                JSONObject itemInfo = getInfoItems.getJSONObject(i);
//                b.putString("value", itemInfo.getString("value"));
//            }
//            insert.setArguments(b);
//
//            items.add(insert);
//            fragTrans.replace(frame.getId(), items.get(items.size() - 1));
//        }
//        fragTrans.commit();
//
//        return layoutRows;
//    }


    private Boolean checkIsNew(){
        return getIntent().hasExtra("isNew") && getIntent().getExtras().getBoolean("isNew") == true;
    }
}

