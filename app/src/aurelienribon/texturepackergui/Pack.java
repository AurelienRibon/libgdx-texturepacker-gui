package aurelienribon.texturepackergui;

import aurelienribon.utils.PathUtils;
import aurelienribon.utils.TextUtils;
import aurelienribon.utils.notifications.ChangeableObject;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class Pack extends ChangeableObject {
	private final Settings settings;
	private String name = "";
	private String filename = "";
	private String input = "";
	private String output = "";

	public Pack() {
		settings = new Settings();
		settings.maxWidth = 2048; // Default settings.maxWidth value (1024) is outdated and 2048 is recommended
		settings.maxHeight = 2048; // Default settings.maxHeight value (1024) is outdated and 2048 is recommended
	}

	public Pack(Pack pack) {
		settings = new Settings(pack.settings);
		this.name = pack.name;
		this.filename = pack.filename;
		this.input = pack.input;
		this.output = pack.output;
	}

	public void setName(String name) {this.name = name; firePropertyChanged("name");}
	public void setFilename(String filename) {this.filename = filename;}
	public void setInput(String input) {this.input = input;}
	public void setOutput(String output) {this.output = output;}

	public String getName() {return name.equals("") ? "unnamed" : name;}
	public String getRawFilename() {return filename;}
	public String getFilename() {return filename.equals("") ? getName() + ".atlas" : filename;}
	public String getInput() {return input;}
	public String getOutput() {return output;}
	public Settings getSettings() {return settings;}

	public void load(String str, File baseDir) {
		String lines[] = str.split("\n");
		for (String line : lines) {
			if (line.startsWith("name=")) name = PathUtils.trim(line.substring("name=".length())).trim();
			if (line.startsWith("filename=")) filename = PathUtils.trim(line.substring("filename=".length())).trim();
			if (line.startsWith("input=")) input = PathUtils.trim(line.substring("input=".length())).trim();
			if (line.startsWith("output=")) output = PathUtils.trim(line.substring("output=".length())).trim();
		}

		try {
			if (!input.equals("") && !new File(input).isAbsolute()) {
				input = new File(baseDir, input).getCanonicalPath();
			}
			if (!output.equals("") && !new File(output).isAbsolute()) {
				output = new File(baseDir, output).getCanonicalPath();
			}
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			input = output = null;
		}

		importSettings(str);
	}

	public String save(File baseDir) {
		String str = "";
		str += "name=" + getName() + "\n";
		str += "filename=" + filename + "\n";
		str += "input=" + PathUtils.relativize(input, baseDir.getPath()) + "\n";
		str += "output=" + PathUtils.relativize(output, baseDir.getPath()) + "\n\n";
		str += exportSettings();
		return str;
	}

	public String exportSettings() {
		StringBuilder sb = new StringBuilder();

		sb.append("alias=").append(settings.alias).append("\n");
		sb.append("alphaThreshold=").append(settings.alphaThreshold).append("\n");
		sb.append("debug=").append(settings.debug).append("\n");
		sb.append("duplicatePadding=").append(settings.duplicatePadding).append("\n");
		sb.append("edgePadding=").append(settings.edgePadding).append("\n");
		sb.append("fast=").append(settings.fast).append("\n");
		sb.append("filterMag=").append(settings.filterMag).append("\n");
		sb.append("filterMin=").append(settings.filterMin).append("\n");
		sb.append("format=").append(settings.format).append("\n");
		sb.append("ignoreBlankImages=").append(settings.ignoreBlankImages).append("\n");
		sb.append("jpegQuality=").append(settings.jpegQuality).append("\n");
		sb.append("maxHeight=").append(settings.maxHeight).append("\n");
		sb.append("maxWidth=").append(settings.maxWidth).append("\n");
		sb.append("minHeight=").append(settings.minHeight).append("\n");
		sb.append("minWidth=").append(settings.minWidth).append("\n");
		sb.append("outputFormat=").append(settings.outputFormat).append("\n");
		sb.append("paddingX=").append(settings.paddingX).append("\n");
		sb.append("paddingY=").append(settings.paddingY).append("\n");
		sb.append("pot=").append(settings.pot).append("\n");
		sb.append("rotation=").append(settings.rotation).append("\n");
		sb.append("stripWhitespaceX=").append(settings.stripWhitespaceX).append("\n");
		sb.append("stripWhitespaceY=").append(settings.stripWhitespaceY).append("\n");
		sb.append("wrapX=").append(settings.wrapX).append("\n");
		sb.append("wrapY=").append(settings.wrapY);

		return sb.toString();
	}

	public void importSettings(String str) {
		List<String> lines = TextUtils.splitAndTrim(str);
		Settings defaultSettings = new Settings();

		settings.alias = find(lines, "atlas=", defaultSettings.alias);
		settings.alphaThreshold = find(lines, "alphaThreshold=", defaultSettings.alphaThreshold);
		settings.debug = find(lines, "debug=", defaultSettings.debug);
		settings.duplicatePadding = find(lines, "duplicatePadding=", defaultSettings.duplicatePadding);
		settings.edgePadding = find(lines, "edgePadding=", defaultSettings.edgePadding);
		settings.fast = find(lines, "fast=", defaultSettings.fast);
		settings.filterMag = TextureFilter.valueOf(find(lines, "filterMag=", defaultSettings.filterMag.toString()));
		settings.filterMin = TextureFilter.valueOf(find(lines, "filterMin=", defaultSettings.filterMin.toString()));
		settings.format = Format.valueOf(find(lines, "format=", defaultSettings.format.toString()));
		settings.ignoreBlankImages = find(lines, "ignoreBlankImages=", defaultSettings.ignoreBlankImages);
		settings.jpegQuality = find(lines, "jpegQuality=", defaultSettings.jpegQuality);
		settings.maxHeight = find(lines, "maxHeight=", 2048); // defaultSettings.maxHeight value (1024) is outdated and 2048 is recommended
		settings.maxWidth = find(lines, "maxWidth=", 2048); // defaultSettings.maxWidth value (1024) is outdated and 2048 is recommended
		settings.minHeight = find(lines, "minHeight=", defaultSettings.minHeight);
		settings.minWidth = find(lines, "minWidth=", defaultSettings.minWidth);
		settings.outputFormat = find(lines, "outputFormat=", defaultSettings.outputFormat);
		settings.paddingX = find(lines, "paddingX=", defaultSettings.paddingX);
		settings.paddingY = find(lines, "paddingY=", defaultSettings.paddingY);
		settings.pot = find(lines, "pot=", defaultSettings.pot);
		settings.rotation = find(lines, "rotation=", defaultSettings.rotation);
		settings.stripWhitespaceX = find(lines, "stripWhitespaceX=", defaultSettings.stripWhitespaceX);
		settings.stripWhitespaceY = find(lines, "stripWhitespaceY=", defaultSettings.stripWhitespaceY);
		settings.wrapX = TextureWrap.valueOf(find(lines, "wrapX=", defaultSettings.wrapX.toString()));
		settings.wrapY = TextureWrap.valueOf(find(lines, "wrapY=", defaultSettings.wrapY.toString()));
	}

	private String find(List<String> lines, String start, String defaultValue) {
		for (String line : lines)
			if (line.startsWith(start))
				return line.substring(start.length());
		return defaultValue;
	}

	private boolean find(List<String> lines, String start, boolean defaultValue) {
		String str = find(lines, start, null);
		if (str != null) return Boolean.parseBoolean(str);
		return defaultValue;
	}

	private int find(List<String> lines, String start, int defaultValue) {
		String str = find(lines, start, null);
		if (str != null) return Integer.parseInt(str);
		return defaultValue;
	}

	private float find(List<String> lines, String start, float defaultValue) {
		String str = find(lines, start, null);
		if (str != null) return Float.parseFloat(str);
		return defaultValue;
	}

	// -------------------------------------------------------------------------
	// Static
	// -------------------------------------------------------------------------

	public static List<Pack> parse(File file) throws IOException {
		List<Pack> packs = new ArrayList<Pack>();
		String[] strs = FileUtils.readFileToString(file).split("---");

		for (String str : strs) {
			Pack pack = new Pack();
			pack.load(str, file.getParentFile());
			packs.add(pack);
		}

		return Collections.unmodifiableList(packs);
	}

	public static void export(File file, List<Pack> packs) throws IOException {
		String str = packs.get(0).save(file.getParentFile());

		for (int i=1; i<packs.size(); i++) {
			str += "\n\n---\n\n";
			str += packs.get(i).save(file.getParentFile());
		}

		FileUtils.writeStringToFile(file, str);
	}
}
