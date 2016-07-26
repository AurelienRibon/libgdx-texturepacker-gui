package aurelienribon.texturepackergui;

import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Prefs {
    public static final String KEY_LAST_DIR_PROJ = "last_dir_proj";
    public static final String KEY_LAST_DIR_OUTPUT = "last_dir_output";
    public static final String KEY_LAST_DIR_INPUT = "last_dir_input";
    private static final Preferences prefs = Preferences.userNodeForPackage(Prefs.class);

    public static File getFile(String key) {
        String filePath = prefs.get(key, null);
        if (filePath == null || filePath.trim().isEmpty()) {
            return new File(System.getProperty("user.dir", "."));
        }

        File file = new File(filePath);
        if (!file.exists() || !file.isDirectory()) {
            return new File(System.getProperty("user.dir", "."));
        }
        return file;
    }

    public static void storeFile(String key, File file) {
        try {
            prefs.put(key, file.getAbsolutePath());
            prefs.flush();
        } catch (BackingStoreException e) {
            e.printStackTrace();
        }
    }
}
