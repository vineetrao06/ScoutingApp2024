package com.team2813.scouting_app;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import android.util.Log;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class QRFragment extends Fragment {

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

    public QRFragment() {
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

        super.onStart();
        b = getArguments();

        String scouter_name = b.getString("name");
        String team_num = b.getString("team_num");
        String match_num = b.getString("match_num");
        String starting_pos = b.getString("starting_pos");
        String leave = b.getString("leave");

        String speaker_counter_auto = b.getString("speaker_counter_auto");
        String amp_counter_auto = b.getString("amp_counter_auto");
        String wing_counter = b.getString("wing_counter");
        String midline_counter = b.getString("midline_counter");
        String missed_counter_auto = b.getString("missed_counter_auto");
        String speaker_counter1 = b.getString("speaker_counter1");
        String speaker_counter3 = b.getString("speaker_counter3");
        String amp_counter1 = b.getString("amp_counter1");
        String amp_counter3 = b.getString("amp_counter3");
        String amp_button1 = b.getString("amp_button1");
        String herd_counter = b.getString("herd_counter");
        String climb = b.getString("climb");
        String buddy_climb_counter = b.getString("buddy_climb_counter");
        String spotlight_counter = b.getString("spotlight_counter");

        String coop = b.getString("coop");
        String intakeSource = b.getString("intakeSource");
        String intakeGround = b.getString("intakeGround");
        String defense = b.getString("defense");
        String trap = b.getString("trap");
        String climbStart = b.getString("climbStart");
        String climbEnd = b.getString("climbEnd");
        String notes = b.getString("notes");

        String preScouting = b.getString("prescouting");
        String quals = b.getString("quals");
        String  playoffs = b.getString("playoffs");
        String gameMode;

        if ("Yes".equalsIgnoreCase(preScouting)) {
            gameMode = "Pre-scouting";
        } else if ("Yes".equalsIgnoreCase(quals)) {
            gameMode = "Qualifications";
        } else if ("Yes".equalsIgnoreCase(playoffs)) {
            gameMode = "Playoffs";
        } else {
            gameMode = "Unknown";
        }

        if ("Select a starting position".equalsIgnoreCase(starting_pos))
            starting_pos = "Unknown";

        for (String key : b.keySet()) {
            Object value = b.get(key);
            Log.d("Bundle data", key + ": " + String.valueOf(value));
        }


        String url = "https://docs.google.com/forms/d/e/1FAIpQLSfK-soD6GvG4d5_TibdvoJ_gtesSLd1QKNA_b-Ck6ZvTjsYnw/formResponse?usp=pp_url"
                + "&entry.421257827=" + scouter_name
                + "&entry.819601047=" + match_num
                + "&entry.89824987=" + team_num
                + "&entry.898007511=" + starting_pos
                + "&entry.993959770=" + speaker_counter_auto
                + "&entry.795834658=" + amp_counter_auto
                + "&entry.306324787=" + wing_counter
                + "&entry.807735240=" + midline_counter
                + "&entry.529066387=" + missed_counter_auto
                + "&entry.1217616982=" + speaker_counter1
                + "&entry.682876735=" + speaker_counter3
                + "&entry.1967591774=" + amp_counter1
                + "&entry.917240807=" + amp_counter3
                + "&entry.1222433661=" + amp_button1
                + "&entry.791068702=" + coop
                + "&entry.1750537532=" + intakeSource
                + "&entry.669197639=" + intakeGround
                + "&entry.1196248779=" + herd_counter
                + "&entry.1407505867=" + defense
                + "&entry.1267025253=" + leave
                + "&entry.127149468=" + climb
                + "&entry.1856418020=" + buddy_climb_counter
                + "&entry.387710031=" + trap
                + "&entry.1806835938=" + spotlight_counter
                + "&entry.106229748=" + climbStart
                + "&entry.115906423=" + climbEnd
                + "&entry.1015372695=" + notes
                + "&entry.1345180468=" + gameMode;

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
