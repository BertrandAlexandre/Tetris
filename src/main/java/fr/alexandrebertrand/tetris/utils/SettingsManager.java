package fr.alexandrebertrand.tetris.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Settings manager to read and update settings
 * 
 * @author Alexandre Bertrand
 */
public class SettingsManager {

    private Gson gson;

    public SettingsManager() {
        gson = new Gson();
        readSettings();
    }

    private void readSettings() {
        try {
            InputStream is = this.getClass().getClassLoader()
                    .getResourceAsStream("resources/datas/custom-settings.json");
            if (is == null) {
                this.getClass().getClassLoader()
                        .getResourceAsStream("resources/datas/settings.json");
            }
            int ch;
            StringBuilder sb = new StringBuilder();
            while((ch = is.read()) != -1)
                sb.append((char) ch);
            is.close();
            gson.fromJson(sb.toString(), Settings.class);
        } catch (JsonSyntaxException | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveSettings() {
        try {
            File f = new File("resources/datas/custom-settings.json");
            OutputStream os = new FileOutputStream(f);
            os.write(gson.toJson(Settings.class).getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public void initializeSettings() {
        // todo : delete custom file
        // readSettings
    }

}
