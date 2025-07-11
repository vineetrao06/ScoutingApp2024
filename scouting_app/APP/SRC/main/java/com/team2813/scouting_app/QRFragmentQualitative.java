package com.team2813.scouting_app;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRFragmentQualitative extends Fragment {

    Bundle b;

    public Bitmap createBitmap(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public QRFragmentQualitative() {
    }

    ImageView qrCode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.qr_fragment_qr, container, false);
        qrCode=(ImageView)view.findViewById(R.id.qrImageView);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        b = getArguments();

        String scouter_name = b.getString("name");
        String team_num = b.getString("team_num");
        String match_num = b.getString("match_num");
        String offense = b.getString("offense");
        String defense = b.getString("defense");


        String url = "https://docs.google.com/forms/d/e/1FAIpQLSdkwN_O4fNXbqHHYDu0KqA4W_u_l6JErndPJGf3UpzREiY73A/formResponse?usp=pp_url&"
                + "entry.68063732=" + scouter_name
                + "&entry.901289294=" + team_num
                + "&entry.216482256=" + match_num
                + "&entry.33088452=" + offense
                + "&entry.1216149316=" + defense;



        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE,500,500);
            Bitmap bitmap = createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
