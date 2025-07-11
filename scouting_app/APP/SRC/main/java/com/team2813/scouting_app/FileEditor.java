package com.team2813.scouting_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.team2813.lib.JSONFileObject;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class FileEditor {
    public static File createFile(Context context, String ID, JSONObject info){
        File f = null;
        try {
            f = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ID+".json");
            f.createNewFile();

            JSONObject input = new JSONObject();
            input.put("info", info);

            FileOutputStream fstream = new FileOutputStream(f);
            fstream.flush();
            fstream.write(input.toString().getBytes());
            fstream.close();

            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));

        } catch (Exception e) {
            Toast t = Toast.makeText(context.getApplicationContext(), "File Failed to create", Toast.LENGTH_SHORT);
            t.show();
        }
        return f;
    }

    public static void deleteFile(Context context, File f){
        try {
            if(f.delete()){

                Toast t = Toast.makeText(context.getApplicationContext(), "Done!", Toast.LENGTH_SHORT);
                t.show();
            }else{
                Toast t = Toast.makeText(context.getApplicationContext(), "File Failed to Delete", Toast.LENGTH_SHORT);
                t.show();
            }

            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));

        } catch (Exception e){
            Toast t = Toast.makeText(context.getApplicationContext(), "File Failed to Delete", Toast.LENGTH_SHORT);
            t.show();
        }
    }

    public static void replaceInFile(Context context, File f, String locationLabel, JSONObject j){
        try {
            InputStream in = new FileInputStream(f);
            String theString = IOUtils.toString(in, "UTF-8");

            JSONObject og = new JSONObject(theString);
            if(locationLabel.equals("")){
                og = j;
            }
            else{
                og.getJSONObject(locationLabel).remove(locationLabel);
                og.put(locationLabel, j);
            }

            FileOutputStream fstream = null;

            fstream = new FileOutputStream(f);
            fstream.flush();
            fstream.write(og.toString().getBytes());
            fstream.close();

            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void replaceInFile(Context context, File f, String locationLabel, JSONArray j){
        try {
            InputStream in = new FileInputStream(f);
            String theString = IOUtils.toString(in, "UTF-8");

            JSONObject og = new JSONObject(theString);
            if(locationLabel.equals("")){
                throw new Exception("Cannot pass no location with JSONArray");
            }
            else{
                og.remove(locationLabel);
                og.put(locationLabel, j);
            }

            FileOutputStream fstream = null;

            fstream = new FileOutputStream(f);
            fstream.flush();
            fstream.write(og.toString().getBytes());
            fstream.close();

            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addToFile(Context context, File f, String locationLabel, JSONObject j, String label){
        try {
            InputStream in = new FileInputStream(f);
            String theString = IOUtils.toString(in, "UTF-8");

            //TODO Rename
            JSONObject og = new JSONObject(theString);
            if(locationLabel.equals(""))og.put(label, j);
            else og.getJSONObject(locationLabel).put(label, j);

            FileOutputStream fStream = null;

            fStream = new FileOutputStream(f);
            fStream.flush();
            fStream.write(og.toString().getBytes());
            fStream.close();

            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addToFile(Context context, File f, String locationLabel, JSONArray j, String label){
        try {
            InputStream in = new FileInputStream(f);
            String theString = IOUtils.toString(in, "UTF-8");

            JSONObject og = new JSONObject(theString);
            if(locationLabel.equals(""))og.put(label, j);
            else og.getJSONObject(locationLabel).put(label, j);

            FileOutputStream fstream = null;

            fstream = new FileOutputStream(f);
            fstream.flush();
            fstream.write(og.toString().getBytes());
            fstream.close();

            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getAssetFile(Context context, String fileName){
        try {
            InputStream in = context.getAssets().open(fileName);
            String theString = IOUtils.toString(in, "UTF-8");
            return new JSONObject(theString);
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    public static ArrayList<JSONFileObject> getFiles(String fileName){
        return getFiles(new File(fileName));
    }

    public static ArrayList<JSONFileObject> getFiles(File dirFile){
        try {
            File dir = dirFile;
            File[] directoryListing = dir.listFiles();
            ArrayList<JSONFileObject> fileObjects = new ArrayList<>();
            for (File child : directoryListing) {
                InputStream in = new FileInputStream(child);
                String theString = IOUtils.toString(in, "UTF-8");
                fileObjects.add(new JSONFileObject(child.getName(), new JSONObject(theString), child));
            }
            return fileObjects;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
