

import aurelienribon.texturepackergui.RenderCanvas;
import aurelienribon.texturepackergui.utils.ErrorReport;
import aurelienribon.texturepackergui.MainWindow;
import aurelienribon.texturepackergui.Project;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.apache.commons.io.FileUtils;

public class Main {
    public static void main(String[] args) {
		Parameters params = new Parameters(args);
		Project project = params.project;

		if (project == null) {
			String str = "";
			if (params.input != null) str += "input=" + params.input + "\n";
			if (params.output != null) str += "output=" + params.output + "\n";
			if (params.settings != null) str += params.settings;
			project = Project.loadFromString(System.getProperty("user.dir"), str);
		}

		if (params.silent) {
			if (project.getInput().equals("") || project.getOutput().equals("")) {
				ErrorReport.reportOnStdErr("Input and output directories have to be set for silent pack");
			} else  {
				try {
					project.pack();
				} catch (GdxRuntimeException ex) {
					ErrorReport.reportOnStdErr("Packing unsuccessful...", ex);
				}
			}
			return;
		}

		final Project prj = project;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception ex) {}

				LwjglCanvas glCanvas = new LwjglCanvas(new RenderCanvas(), false);

				MainWindow mw = new MainWindow(glCanvas.getCanvas());
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				mw.setSize(
					Math.min(1100, screenSize.width - 100),
					Math.min(800, screenSize.height - 100)
				);
				mw.setLocationRelativeTo(null);
				mw.setVisible(true);
				mw.setProject(prj);
			}
		});
    }

	private static class Parameters {
		public boolean silent = false;
		public Project project = null;
		public String input = null;
		public String output = null;
		public String settings = null;

		public Parameters(String[] args) {
			for (int i=0; i<args.length; i++) {
				if (args[i].startsWith("--project=")) {
					try {
						project = Project.loadFromFile(args[i].substring("--project=".length()));
					} catch (IOException ex) {
						ErrorReport.reportOnStdErr("Can't read project file", ex);
					}

				} else if (args[i].startsWith("--input=")) {
					input = args[i].substring("--input=".length());

				} else if (args[i].startsWith("--output=")) {
					output = args[i].substring("--output=".length());

				} else if (args[i].startsWith("--silent")) {
					silent = true;

				} else if (args[i].startsWith("--settings=")){
					try {
						File file = new File(args[i].substring("--settings=".length()));
						settings = FileUtils.readFileToString(file);

					} catch (IOException ex) {
						ErrorReport.reportOnStdErr("Can't read settings file", ex);
					}
				}
			}
		}
	}
}
