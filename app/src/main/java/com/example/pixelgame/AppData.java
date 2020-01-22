package com.example.pixelgame;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.UUID;

public class AppData {

    public final static String APP_NAME="PixelGame";
    public final static UUID APP_UUID=UUID.fromString("b8841a90-d134-11e8-b568-0800200c9a55");

    private static File dataDirectory;

    private static PlayerData playerData;

    public static PlayerData getPlayerData(){
        return playerData;
    }

    public static void initSaveDirectory(File baseDirectory){
        if (dataDirectory == null) {
            dataDirectory = new File(baseDirectory,"appData.xml");
        }
    }

    public static void save(){
        Serializer serializer= new Persister();
        try {
            serializer.write(playerData, dataDirectory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean load(){
        boolean loadPossible= dataDirectory.exists();
        if (loadPossible) {
            Serializer serializer= new Persister();
            try {
                serializer.read(playerData, dataDirectory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loadPossible;
    }

    public static void init(String name){
        playerData=new PlayerData(name);
    }
}
