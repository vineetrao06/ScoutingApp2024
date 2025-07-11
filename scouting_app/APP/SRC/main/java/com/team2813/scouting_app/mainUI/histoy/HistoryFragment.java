package com.team2813.scouting_app.mainUI.histoy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.team2813.lib.JSONFileObject;
import com.team2813.scouting_app.FileEditor;
import com.team2813.scouting_app.R;
import com.team2813.scouting_app.formUI.FormActivity;

import java.io.IOException;
import java.util.ArrayList;

public class HistoryFragment extends Fragment implements HistoryRecyclerViewAdapter.ItemClickListener, HistoryRecyclerViewAdapter.ItemLongClickListener{

    RecyclerView savedFormItems;
    HistoryRecyclerViewAdapter savedFormItemsAdapter;
    SwipeRefreshLayout formRefresher;
    ImageButton deleteButton;
    ImageButton cancelButton;
    ImageView noForms;

    ViewPager viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mn_fragment_history, container, false);

        viewPager = getActivity().findViewById(R.id.contentView);

        savedFormItems = view.findViewById(R.id.savedFromsList);
        savedFormItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        savedFormItemsAdapter = new HistoryRecyclerViewAdapter(getActivity(), retrieveSavedForms());
        savedFormItems.setAdapter(savedFormItemsAdapter);
        savedFormItemsAdapter.setClickListener(this);
        savedFormItemsAdapter.setLongClickListener(this);
        
        formRefresher = view.findViewById(R.id.formRefresher);
        formRefresher.setOnRefreshListener(
                () -> {
                    savedFormItemsAdapter.swap(retrieveSavedForms());
                    formRefresher.setRefreshing(false);
                }
        );

        deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> {
            for(int i = 0; i < savedFormItemsAdapter.getItemCount(); i++){
                HistoryRecyclerViewRow row = savedFormItemsAdapter.getItem(i);
                if(row.isSelected()){
                    FileEditor.deleteFile(getActivity(), row.getFile());;
                }
            }
            savedFormItemsAdapter.swap(retrieveSavedForms());
            hideSelections();

            if(savedFormItemsAdapter.getItemCount() == 0)noForms.setVisibility(View.VISIBLE);
            else noForms.setVisibility(View.INVISIBLE);
        });

        cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> {
            hideSelections();
        });

        noForms = view.findViewById(R.id.noformImage);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        savedFormItemsAdapter.swap(retrieveSavedForms());
        if(savedFormItemsAdapter.getItemCount() == 0)noForms.setVisibility(View.VISIBLE);
        else noForms.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        hideSelections();
    }

    @Override
    public void onItemClick(View view, int position) {
        if(!savedFormItemsAdapter.getSelectionMode()) {
            Intent intent = new Intent(getActivity(), FormActivity.class);
            intent.putExtra("isNew", false);
            intent.putExtra("fileJSONObjectString", savedFormItemsAdapter.getItem(position).getContent().toString());
            intent.putExtra("fileName", savedFormItemsAdapter.getItem(position).getName());
            startActivity(intent);
        }else{
            savedFormItemsAdapter.getItem(position).setSelected(!savedFormItemsAdapter.getItem(position).isSelected());
            savedFormItemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onLongItemClick(View view, int position) {
        savedFormItemsAdapter.getItem(position).setSelected(true);
        showSelections();
        return true;
    }

    public ArrayList<JSONFileObject> retrieveSavedForms(){
        ArrayList<JSONFileObject> savedFilesJSON = new ArrayList<>();
        for (JSONFileObject js : FileEditor.getFiles(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS))) {
            try {
                savedFilesJSON.add(js);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return savedFilesJSON;
    }

    public void showSelections(){
        savedFormItemsAdapter.setSelectionMode(true);
        cancelButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
    }

    public void hideSelections(){
        for(int i = 0; i < savedFormItemsAdapter.getItemCount(); i++){
            savedFormItemsAdapter.getItem(i).setSelected(false);
        }
        savedFormItemsAdapter.setSelectionMode(false);
        cancelButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);
    }
}
