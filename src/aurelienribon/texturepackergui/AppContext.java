package aurelienribon.texturepackergui;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.imagepacker.TexturePacker;
import com.badlogic.gdx.imagepacker.TexturePacker.Settings;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class AppContext {
	public static String inputDir;
	public static String outputDir;
	public static Settings settings = new Settings();

	public static void pack() {
		try {
			if (inputDir != null && outputDir != null) {
				TexturePacker.process(settings, inputDir, outputDir);
				AppEvents.fireEventToListeners(AppEvents.PackDoneListener.class);
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	public static void exportSettings(File file) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("alphaThreshold=" + settings.alphaThreshold + "\n");
			writer.write("debug=" + settings.debug + "\n");
			writer.write("defaultFilterMag=" + settings.defaultFilterMag + "\n");
			writer.write("defaultFilterMin=" + settings.defaultFilterMin + "\n");
			writer.write("defaultFormat=" + settings.defaultFormat + "\n");
			writer.write("duplicatePadding=" + settings.duplicatePadding + "\n");
			writer.write("incremental=" + settings.incremental + "\n");
			writer.write("maxHeight=" + settings.maxHeight + "\n");
			writer.write("maxWidth=" + settings.maxWidth + "\n");
			writer.write("minHeight=" + settings.minHeight + "\n");
			writer.write("minWidth=" + settings.minWidth + "\n");
			writer.write("padding=" + settings.padding + "\n");
			writer.write("pot=" + settings.pot + "\n");
			writer.write("rotate=" + settings.rotate + "\n");
			writer.write("stripWhitespace=" + settings.stripWhitespace + "\n");
			writer.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	public static void importSettings(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			List<String> lines = new ArrayList<String>();
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}

			settings = new Settings();
			for (String ln : lines) {
				Matcher m;
				m = Pattern.compile("alphaThreshold=(\\d+)").matcher(ln.trim());
				if (m.find()) settings.alphaThreshold = Integer.valueOf(m.group(1));
				m = Pattern.compile("debug=(true|false)").matcher(ln.trim());
				if (m.find()) settings.debug = Boolean.valueOf(m.group(1));
				m = Pattern.compile("defaultFilterMag=(.+)").matcher(ln.trim());
				if (m.find()) settings.defaultFilterMag = TextureFilter.valueOf(m.group(1));
				m = Pattern.compile("defaultFilterMin=(.+)").matcher(ln.trim());
				if (m.find()) settings.defaultFilterMin = TextureFilter.valueOf(m.group(1));
				m = Pattern.compile("defaultFormat=(.+)").matcher(ln.trim());
				if (m.find()) settings.defaultFormat = Format.valueOf(m.group(1));
				m = Pattern.compile("duplicatePadding=(true|false)").matcher(ln.trim());
				if (m.find()) settings.duplicatePadding = Boolean.valueOf(m.group(1));
				m = Pattern.compile("incremental=(true|false)").matcher(ln.trim());
				if (m.find()) settings.incremental = Boolean.valueOf(m.group(1));
				m = Pattern.compile("maxHeight=(\\d+)").matcher(ln.trim());
				if (m.find()) settings.maxHeight = Integer.valueOf(m.group(1));
				m = Pattern.compile("maxWidth=(true|false)").matcher(ln.trim());
				if (m.find()) settings.maxWidth = Integer.valueOf(m.group(1));
				m = Pattern.compile("minHeight=(true|false)").matcher(ln.trim());
				if (m.find()) settings.minHeight = Integer.valueOf(m.group(1));
				m = Pattern.compile("minWidth=(true|false)").matcher(ln.trim());
				if (m.find()) settings.minWidth = Integer.valueOf(m.group(1));
				m = Pattern.compile("padding=(true|false)").matcher(ln.trim());
				if (m.find()) settings.padding = Integer.valueOf(m.group(1));
				m = Pattern.compile("pot=(true|false)").matcher(ln.trim());
				if (m.find()) settings.pot = Boolean.valueOf(m.group(1));
				m = Pattern.compile("rotate=(true|false)").matcher(ln.trim());
				if (m.find()) settings.rotate = Boolean.valueOf(m.group(1));
				m = Pattern.compile("stripWhitespace=(true|false)").matcher(ln.trim());
				if (m.find()) settings.stripWhitespace = Boolean.valueOf(m.group(1));
			}
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
}
