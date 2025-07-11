package com.team2813.scouting_app.mainUI.camera;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.team2813.scouting_app.FileEditor;
import com.team2813.scouting_app.R;
import com.team2813.scouting_app.formUI.formFragments.DataFragmentInterface;
import com.team2813.scouting_app.mainUI.MainActivity;
import com.team2813.scouting_app.mainUI.histoy.HistoryFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class CameraFragment extends Fragment{

    SurfaceView cameraView;
    CameraSource cameraSource;
    TextView barcodeInfo;
    ImageButton cameraButton;
    ViewPager viewPager;

    String data;

    public CameraFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mn_fragment_camera, container, false);

        cameraView = view.findViewById(R.id.cameraView);
        barcodeInfo = view.findViewById(R.id.txtContent);
        cameraButton = view.findViewById(R.id.cameraButton);
        viewPager = getActivity().findViewById(R.id.contentView);

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.QR_CODE)//QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(getActivity(), barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .setAutoFocusEnabled(true)
                .build();

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                SparseArray<Barcode> barcodes = detections.getDetectedItems();

                barcodeInfo.post(() -> {
                    barcodeInfo.setText(barcodes.size() != 0 ? "Detected!" : "Nothing...");
                    if(barcodes.size() != 0) data = barcodes.valueAt(0).displayValue;
                });
            }
        });

        cameraButton.setOnClickListener(v -> {
            try {
                Log.d("asdf", data);
                String[] dataSplit = data.split("/");
                String[] infoStrings = dataSplit[0].split(",");
                String[] formDataStrings = dataSplit[1].split(",");
                JSONObject getLayoutItems = FileEditor.getAssetFile(getActivity(),"INFINITE_RECHARGE_2020");

                JSONObject info = getLayoutItems.getJSONObject("info");
                String[] infostuff = {"date", "model", "team_number", "match_number", "alliance_color", "scouter_name", "competition_name"};

                for(int i = 0; i < infostuff.length; i++){
                    info.put(infostuff[i], infoStrings[i]);
                }

                JSONArray formItems = getLayoutItems.getJSONArray("formItems");
                for (String f : formDataStrings) {
                    String[] datas = f.split(":");
                    Log.d("asdf", datas[0] + datas[1]);
                    for (int i = 0; i < formItems.length(); i++) {
                        if (formItems.getJSONObject(i).getString("name").equals(datas[0])) {
                            formItems.getJSONObject(i).put("value", datas[1]);
                            break;
                        }
                    }
                }

                File f = FileEditor.createFile(getActivity(),
                        info.getString("date") + info.getString("team_number") + info.getString("model"),
                        info);
                FileEditor.addToFile(getActivity(), f, "", formItems, "formItems");
                cameraSource.stop();
                viewPager.setCurrentItem(1);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("asdf", e.getMessage());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            cameraSource.start(cameraView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraSource.stop();
        Log.d("asdf", "hello");
    }
}
