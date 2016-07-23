package aurelienribon.texturepackergui;

import aurelienribon.texturepackergui.canvas.Canvas;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
		final Parameters params = new Parameters(args);

		SwingUtilities.invokeLater(new Runnable() {@Override public void run() {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException ex) {
			} catch (InstantiationException ex) {
			} catch (IllegalAccessException ex) {
			} catch (UnsupportedLookAndFeelException ex) {
			}

			Canvas canvas = new Canvas();
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			LwjglCanvas glCanvas = new LwjglCanvas(canvas, config);

			MainWindow mw = new MainWindow(canvas, glCanvas.getCanvas());
			mw.pack();
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			mw.setSize(
				Math.min(1100, screenSize.width - 100),
				Math.min(mw.getHeight(), screenSize.height - 50)
			);
			mw.setLocationRelativeTo(null);
			mw.setVisible(true);

			if (params.project != null) {
				try {
					mw.load(params.project);
				} catch (IOException ex) {
					System.err.println("[error] Cannot read the given project file");
				}
			}
		}});
    }

	private static class Parameters {
		public File project = null;

		public Parameters(String[] args) {
			String path = get(args, "-project", null);
			if (path != null) project = new File(path);
		}

		private String get(String[] args, String arg, String defaultValue) {
			for (int i=0; i<args.length-1; i++) {
				if (args[i].equals(arg)) return args[i+1];
			}
			return defaultValue;
		}

		private boolean check(String[] args, String arg) {
			for (int i=0; i<args.length; i++) {
				if (args[i].equals(arg)) return true;
			}
			return false;
		}
	}
}
