package com.team2813.scouting_app.mainUI.histoy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.team2813.lib.JSONFileObject;
import com.team2813.scouting_app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>{
    private ArrayList<HistoryRecyclerViewRow> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemLongClickListener mLongClickListener;

    private boolean selectionMode = false;

    // data is passed into the constructor
    HistoryRecyclerViewAdapter(Context context, ArrayList<JSONFileObject> data) {
        this.mInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
        for(JSONFileObject j : data) {
            this.mData.add(0, new HistoryRecyclerViewRow(j));
        }
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.mn_row_saved_form, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String formName;
        String formDescription;
        try {
            JSONObject info = mData.get(position).getContent().getJSONObject("info");
            formName = info.getString("competition_name") + " Qualification: " + info.getString("match_number") + " | Team " + info.getString("team_number");
            formDescription = "Test";
            holder.name.setText(formName);
            holder.description.setText(formDescription);
            holder.itemView.findViewById(R.id.checkBox).setVisibility(selectionMode? View.VISIBLE : View.GONE);
            ((CheckBox) holder.itemView.findViewById(R.id.checkBox)).setChecked(mData.get(position).isSelected());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView name;
        TextView description;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.savedFormName);
            description = itemView.findViewById(R.id.savedFormDescription);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (mLongClickListener != null) mLongClickListener.onLongItemClick(view, getAdapterPosition());
            return true;
        }
    }

    // convenience method for getting data at click position
    HistoryRecyclerViewRow getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    void setLongClickListener(ItemLongClickListener itemLongClickListener){
        this.mLongClickListener = itemLongClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface ItemLongClickListener {
        boolean onLongItemClick(View view, int position);
    }

    public void swap(ArrayList<JSONFileObject> datas)
    {
        mData.clear();
        mData = new ArrayList<>();
        for(JSONFileObject j : datas) {
            this.mData.add(0, new HistoryRecyclerViewRow(j));
        }
        notifyDataSetChanged();
    }

    public void setSelectionMode(boolean selectionMode){
        this.selectionMode = selectionMode;
        notifyDataSetChanged();
    }

    public boolean getSelectionMode(){
        return selectionMode;
    }
}
