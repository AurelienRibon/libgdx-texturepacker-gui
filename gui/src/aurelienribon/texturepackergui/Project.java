package aurelienribon.texturepackergui;

import aurelienribon.utils.io.FilenameHelper;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.imagepacker.TexturePacker;
import com.badlogic.gdx.tools.imagepacker.TexturePacker.Settings;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class Project {
	private String basePath;
	private String input;
	private String output;
	private Settings settings;

	public Project(String basePath, String input, String output, Settings settings) {
		this.basePath = basePath;
		this.input = input;
		this.output = output;
		this.settings = settings;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public String getBasePath() {
		return basePath;
	}

	public String getInput() {
		return input;
	}

	public String getOutput() {
		return output;
	}

	public String getRelativeInput() {
		return FilenameHelper.getRelativePath(input, basePath);
	}

	public String getRelativeOutput() {
		return FilenameHelper.getRelativePath(output, basePath);
	}

	public Settings getSettings() {
		return settings;
	}

	public String save() {
		return "input=\"" + getRelativeInput() + "\"\n"
			+ "output=\"" + getRelativeOutput() + "\"\n\n"
			+ saveSettings(settings);
	}

	public void pack() throws GdxRuntimeException {
		TexturePacker.process(getSettings(), getInput(), getOutput());
	}

	// -------------------------------------------------------------------------
	// Static
	// -------------------------------------------------------------------------

	public static Project loadFromFile(String filepath) throws IOException {
		filepath = new File(filepath).getCanonicalPath();
		String content = FileUtils.readFileToString(new File(filepath));
		String path = new File(filepath).getParent();
		return loadFromString(path, content);
	}

	public static Project loadFromString(String dirpath, String content) {
		String basePath = dirpath;
		String input = null;
		String output = null;
		Settings settings = loadSettings(content);

		String lines[] = content.split("\n");
		for (String line : lines) {
			if (line.startsWith("input=")) input = FilenameHelper.trim(line.substring("input=".length()));
			if (line.startsWith("output=")) output = FilenameHelper.trim(line.substring("output=".length()));
		}

		if (input != null) {
			input = FilenameUtils.concat(basePath, input);
			input = FilenameUtils.normalizeNoEndSeparator(input);
		} else {
			input = "";
		}

		if (output != null) {
			output = FilenameUtils.concat(basePath, output);
			output = FilenameUtils.normalizeNoEndSeparator(output);
		} else {
			output = "";
		}

		return new Project(basePath, input, output, settings);
	}
	
	public static String saveSettings(Settings settings) {
		StringBuilder sb = new StringBuilder();
		sb.append("alias=").append(settings.alias).append("\n");
		sb.append("alphaThreshold=").append(settings.alphaThreshold).append("\n");
		sb.append("debug=").append(settings.debug).append("\n");
		sb.append("defaultFilterMag=").append(settings.defaultFilterMag).append("\n");
		sb.append("defaultFilterMin=").append(settings.defaultFilterMin).append("\n");
		sb.append("defaultFormat=").append(settings.defaultFormat).append("\n");
		sb.append("duplicatePadding=").append(settings.duplicatePadding).append("\n");
		sb.append("incremental=").append(settings.incremental).append("\n");
		sb.append("maxHeight=").append(settings.maxHeight).append("\n");
		sb.append("maxWidth=").append(settings.maxWidth).append("\n");
		sb.append("minHeight=").append(settings.minHeight).append("\n");
		sb.append("minWidth=").append(settings.minWidth).append("\n");
		sb.append("padding=").append(settings.padding).append("\n");
		sb.append("pot=").append(settings.pot).append("\n");
		sb.append("rotate=").append(settings.rotate).append("\n");
		sb.append("stripWhitespace=").append(settings.stripWhitespace).append("\n");
		sb.append("edgePadding=").append(settings.edgePadding).append("\n");
		return sb.toString();
	}

	public static Settings loadSettings(String str) {
		Settings settings = new Settings();
		String[] lines = str.split("\n");
		Matcher m = null;
		for (String ln : lines) {
			m = Pattern.compile("alias=(true|false)").matcher(ln.trim());
			if (m.matches()) settings.alias = Boolean.valueOf(m.group(1));
			m = Pattern.compile("alphaThreshold=(\\d+)").matcher(ln.trim());
			if (m.matches()) settings.alphaThreshold = Integer.valueOf(m.group(1));
			m = Pattern.compile("debug=(true|false)").matcher(ln.trim());
			if (m.matches()) settings.debug = Boolean.valueOf(m.group(1));
			m = Pattern.compile("defaultFilterMag=(.+)").matcher(ln.trim());
			if (m.matches()) settings.defaultFilterMag = TextureFilter.valueOf(m.group(1));
			m = Pattern.compile("defaultFilterMin=(.+)").matcher(ln.trim());
			if (m.matches()) settings.defaultFilterMin = TextureFilter.valueOf(m.group(1));
			m = Pattern.compile("defaultFormat=(.+)").matcher(ln.trim());
			if (m.matches()) settings.defaultFormat = Format.valueOf(m.group(1));
			m = Pattern.compile("duplicatePadding=(true|false)").matcher(ln.trim());
			if (m.matches()) settings.duplicatePadding = Boolean.valueOf(m.group(1));
			m = Pattern.compile("incremental=(true|false)").matcher(ln.trim());
			if (m.matches()) settings.incremental = Boolean.valueOf(m.group(1));
			m = Pattern.compile("maxHeight=(\\d+)").matcher(ln.trim());
			if (m.matches()) settings.maxHeight = Integer.valueOf(m.group(1));
			m = Pattern.compile("maxWidth=(\\d+)").matcher(ln.trim());
			if (m.matches()) settings.maxWidth = Integer.valueOf(m.group(1));
			m = Pattern.compile("minHeight=(\\d+)").matcher(ln.trim());
			if (m.matches()) settings.minHeight = Integer.valueOf(m.group(1));
			m = Pattern.compile("minWidth=(\\d+)").matcher(ln.trim());
			if (m.matches()) settings.minWidth = Integer.valueOf(m.group(1));
			m = Pattern.compile("padding=(\\d+)").matcher(ln.trim());
			if (m.matches()) settings.padding = Integer.valueOf(m.group(1));
			m = Pattern.compile("pot=(true|false)").matcher(ln.trim());
			if (m.matches()) settings.pot = Boolean.valueOf(m.group(1));
			m = Pattern.compile("rotate=(true|false)").matcher(ln.trim());
			if (m.matches()) settings.rotate = Boolean.valueOf(m.group(1));
			m = Pattern.compile("stripWhitespace=(true|false)").matcher(ln.trim());
			if (m.matches()) settings.stripWhitespace = Boolean.valueOf(m.group(1));
			m = Pattern.compile("edgePadding=(true|false)").matcher(ln.trim());
			if (m.matches()) settings.edgePadding = Boolean.valueOf(m.group(1));
		}
		return settings;
	}
}
