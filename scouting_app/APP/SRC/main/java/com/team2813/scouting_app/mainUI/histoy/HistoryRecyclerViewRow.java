package com.team2813.scouting_app.mainUI.histoy;

import com.team2813.lib.JSONFileObject;

public class HistoryRecyclerViewRow extends JSONFileObject {

    private boolean selected = false;

    public HistoryRecyclerViewRow(JSONFileObject jsonFileObject){
        super(jsonFileObject.getName(), jsonFileObject.getContent(), jsonFileObject.getFile());
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
