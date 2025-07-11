package com.team2813.scouting_app.mainUI.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.team2813.scouting_app.R;
import com.team2813.scouting_app.formUI.FormActivity;
import com.team2813.scouting_app.formUI.FormActivityQualitative;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HomeFragment extends Fragment {

    Button newFormButton;
    Button qualitativeFormButton;
    ViewPager viewPager;
    TextView centerText;
    ImageView centerImage;

    List<List<String>> scoutingList;


    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mn_fragment_home, container, false);

        viewPager = getActivity().findViewById(R.id.contentView);
        newFormButton = view.findViewById(R.id.newFormButton);
        newFormButton.setOnClickListener((v) -> {
            Intent i = new Intent(getActivity(), FormActivity.class);
            i.putExtra("isNew", true);
            startActivity(i);
        });

        qualitativeFormButton = view.findViewById(R.id.settingsButton);
        qualitativeFormButton.setOnClickListener((v) -> {
            Intent i = new Intent(getActivity(), FormActivityQualitative.class);
            i.putExtra("isNew", true);
            startActivity(i);
        });

        centerText = view.findViewById(R.id.centerText);
        centerImage = view.findViewById(R.id.centerImage);

        refreshScoutingList(view.getContext());

        qualitativeFormButton = view.findViewById(R.id.centerButton);
        qualitativeFormButton.setOnClickListener((v) -> {
            try {
                for(List<String> s : scoutingList){
                    if(s.get(3).equals("0")) {
                        Intent i = new Intent(getActivity(), FormActivity.class);

                        i.putExtra("isMadeNew", true);

                        JSONObject info = new JSONObject();
                        info.put("team_number", s.get(1));
                        info.put("match_number", s.get(0));
                        info.put("alliance_color", s.get(2));
                        i.putExtra("info", info.toString());

                        startActivity(i);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshScoutingList(getActivity());
    }

    public void refreshScoutingList(Context context){
        scoutingList = new ArrayList<>();
        File file = new File(getActivity().getExternalFilesDir(null) + "/importData.txt");
        try {
            Scanner scan = new Scanner(file);
            while(scan.hasNext()){
                List<String> scoutingListEntry = new ArrayList();
                scoutingListEntry.add(scan.next());
                scoutingListEntry.add(scan.next());
                scoutingListEntry.add(scan.next());
                scoutingListEntry.add(scan.next());
                scoutingList.add(scoutingListEntry);
            }
            for(List<String> s : scoutingList){
                if(s.get(3).equals("0")) {
                    centerText.setText("Next Match: " + s.get(1) + " of Qual " + s.get(0));

                    Resources resources = getResources();
                    int resourceId = resources.getIdentifier("img_2020_" + s.get(1), "drawable", context.getPackageName());
                    if(resourceId != 0) centerImage.setImageResource(resourceId);
                    else centerImage.setImageResource(R.drawable.missingphoto);

                    break;
                }
                centerText.setText("Enjoy the competition!");
                centerImage.setImageResource(R.drawable.infinite_recharge);
            }
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
            centerText.setText("Enjoy the competition!");
        }
    }
}
