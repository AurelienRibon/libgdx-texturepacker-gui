package aurelienribon.texturepackergui.utils;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class UiHelper {
    public static File showSaveFileChooser(Component parent, final String ext, final String desc) {
		JFileChooser chooser = new JFileChooser(".");
		chooser.setFileFilter(new FileFilter() {
			@Override public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				int idx = f.getName().lastIndexOf(".");
				if (idx < 0)
					return false;
				return f.getName().substring(idx).equals(ext);
			}

			@Override public String getDescription() {
				return desc;
			}
		});

		if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			int idx = selectedFile.getName().lastIndexOf(".");
			if (idx < 0 || !selectedFile.getName().substring(idx).equals(ext))
				selectedFile = new File(selectedFile.getPath() + ext);
			return selectedFile;
		}

		return null;
	}

	public static File showOpenFileChooser(Component parent, final String ext, final String desc) {
		JFileChooser chooser = new JFileChooser(".");
		chooser.setFileFilter(new FileFilter() {
			@Override public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				int idx = f.getName().lastIndexOf(".");
				if (idx < 0)
					return false;
				return f.getName().substring(idx).equalsIgnoreCase(ext);
			}

			@Override public String getDescription() {
				return desc;
			}
		});

		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			return selectedFile;
		}

		return null;
	}

	public static File showOpenFolderChooser(Component parent, File startFolder) {
		JFileChooser chooser = new JFileChooser(startFolder);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setFileFilter(new FileFilter() {
			@Override public boolean accept(File f) {
				return f.isDirectory();
			}

			@Override public String getDescription() {
				return "Directories";
			}
		});

		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			File selectedFolder = chooser.getSelectedFile();
			return selectedFolder;
		}

		return null;
	}
}
