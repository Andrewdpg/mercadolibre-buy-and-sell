package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;

public class Storage {

    private static final String FOLDER_PATH = "data/";

    public static <T> Boolean saveJsonTo(String path, T obj) throws IOException {
        File folder = new File(FOLDER_PATH);
        if (!folder.exists())
            folder.mkdirs();
        FileOutputStream fos = new FileOutputStream(new File(FOLDER_PATH + path));
        String data = new Gson().toJson(obj);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
        writer.write(data);
        writer.flush();
        fos.close();
        return true;
    }

    public static <T> T loadJsonFrom(String path, Class<T> type) throws IOException {
        File file = new File(FOLDER_PATH + path);
        if (!file.exists()) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String content = "";
        String line = "";
        while ((line = reader.readLine()) != null) {
            content += line + "\n";
        }
        T data = new Gson().fromJson(content, type);
        reader.close();
        return data;
    }

    public static <T> boolean fileExists(String path) {
        return new File(FOLDER_PATH + path).exists();
    }
}
