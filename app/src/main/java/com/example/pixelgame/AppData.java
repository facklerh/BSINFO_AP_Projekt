package com.example.pixelgame;

import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.UUID;

public class AppData {

    public final static String APP_NAME = "PixelGame";
    public final static UUID APP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String TAG = "APPDATA";

    private static File dataDirectory;

    private static PlayerData playerData;

    public static PlayerData getPlayerData() {
        return playerData;
    }

    public static void initSaveDirectory(File baseDirectory) {
        if (dataDirectory == null) {
            dataDirectory = new File(baseDirectory, "appData.xml");
        }
    }

    public static void save() {
        Serializer serializer = new Persister();
        try {
            serializer.write(playerData, dataDirectory);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "save failed");
        }
    }

    public static boolean load() {
        boolean loadPossible = dataDirectory.exists();
        if (loadPossible) {
            Serializer serializer = new Persister();
            try {
                playerData = new PlayerData();
                serializer.read(playerData, dataDirectory);
            } catch (Exception e) {
                Log.d(TAG, "Could not read Data");
                e.printStackTrace();
                loadPossible = false;
            }
        }
        return loadPossible;
    }

    public static void init(String name) {
        playerData = new PlayerData(name);
    }
}
