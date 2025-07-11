package com.team2813.scouting_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

public class QRPopupActivity extends AppCompatActivity {

    public Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_activity_popup);

        Toolbar toolbar = findViewById(R.id.toolbarQR);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        Intent in = getIntent();
        b = in.getExtras();

        QRFragment cf = new QRFragment();
        cf.setArguments(b);

        getSupportFragmentManager().beginTransaction().replace(R.id.qrCode, cf).commit();
    }
}
