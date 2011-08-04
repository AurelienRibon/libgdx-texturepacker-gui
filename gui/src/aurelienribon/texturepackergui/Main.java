package aurelienribon.texturepackergui;

import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
	private static boolean runSilent = false;

    public static void main(String[] args) {
		parseArgs(args);

		if (runSilent) {
			try {
				AppContext.pack();
			} catch (GdxRuntimeException ex) {
				ErrorReport.reportOnStdErr("Trying to pack the images causes problems...", ex);
			}
		} else {
			PrintStream ps = new PrintStream(AppContext.outputStream);
			System.setOut(ps);
			System.setErr(ps);
			makeWindow();
			System.out.println("libgdx-texturepacker-gui | 2011");
			System.out.println("Welcome!\n");
		}
    }

	private static void parseArgs(String[] args) {
		for (int i=0; i<args.length; i++) {
			if (args[i].startsWith("--input=")) {
				AppContext.inputDir = args[i].substring("--input=".length());

			} else if (args[i].startsWith("--output=")){
				AppContext.outputDir = args[i].substring("--output=".length());

			} else if (args[i].startsWith("--settings=")){
				try {
					AppContext.importSettings(new File(args[i].substring("--settings=".length())));
				} catch (FileNotFoundException ex) {
					ErrorReport.reportOnStdErr("It seems that the settings file was not found.", ex);
				} catch (IOException ex) {
					ErrorReport.reportOnStdErr("Settings file caused some problems, trash it, now.", ex);
				}

			} else if (args[i].equals("--silentPack")){
				runSilent = true;
			}
		}
	}

	private static void makeWindow() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception ex) {}

				LwjglCanvas glCanvas = new LwjglCanvas(new App(), false);

				MainWindow mw = new MainWindow(glCanvas.getCanvas());
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				mw.setSize(
					Math.min(1100, screenSize.width - 100),
					Math.min(800, screenSize.height - 100)
				);
				mw.setLocationRelativeTo(null);
				mw.setVisible(true);
			}
		});
	}
}
