package com.example.pixelgame;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class AppData {

    public final static String APP_NAME="PixelGame";
    public final static UUID APP_UUID=UUID.fromString("b8841a90-d134-11e8-b568-0800200c9a55");

    private final static File saveFile= new File("appData.xml");

    private static PlayerData playerData;

    public static PlayerData getPlayerData(){
        return playerData;
    }

    public static void save(){
        Serializer serializer= new Persister();
        try {
            serializer.write(playerData,saveFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load(){
        Serializer serializer= new Persister();
        try {
            playerData =serializer.read(playerData,saveFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
