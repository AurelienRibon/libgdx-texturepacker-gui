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
	private String path = "";
	private String input = "";
	private String output = "";
	private Settings settings = new Settings();

	public Project() {
	}

	public Project(String input, String output, Settings settings) {
		this.input = input != null ? norm(input) : "";
		this.output = output != null ? norm(output) : "";
		this.settings = settings;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setInput(String input) {
		this.input = input != null ? norm(input) : "";
	}

	public void setOutput(String output) {
		this.output = output != null ? norm(output) : "";
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public String getPath() {
		return path;
	}

	public String getInput() {
		return FilenameUtils.concat(path, input);
	}

	public String getOutput() {
		return FilenameUtils.concat(path, output);
	}

	public String getRelativeInput() {
		return FilenameHelper.getRelativePath(getInput(), path);
	}

	public String getRelativeOutput() {
		return FilenameHelper.getRelativePath(getOutput(), path);
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

	private String norm(String path) {
		while (path.startsWith("\"") && path.endsWith("\""))
			path = path.substring(1, path.length()-1);
		return path;
	}

	// -------------------------------------------------------------------------
	// Static
	// -------------------------------------------------------------------------

	public static Project load(String filepath) throws IOException {
		String fullpath = new File(filepath).getCanonicalPath();
		String content = FileUtils.readFileToString(new File(fullpath));
		Project prj = new Project();
		prj.setPath(FilenameUtils.getPrefix(fullpath) + FilenameUtils.getPath(fullpath));
		prj.setSettings(loadSettings(content));

		String lines[] = content.split("\n");
		for (String line : lines) {
			if (line.startsWith("input=")) prj.setInput(line.substring("input=".length()));
			if (line.startsWith("output=")) prj.setOutput(line.substring("output=".length()));
		}

		return prj;
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
