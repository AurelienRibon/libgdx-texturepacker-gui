package aurelienribon.texturepackergui;

import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;
import java.io.File;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
		boolean runSilent = false;

		for (int i=0; i<args.length; i++) {
			if (args[i].startsWith("--input="))
				AppContext.inputDir = args[i].substring("--input=".length());
			else if (args[i].startsWith("--output="))
				AppContext.outputDir = args[i].substring("--output=".length());
			else if (args[i].startsWith("--settings="))
				AppContext.importSettings(new File(args[i].substring("--settings=".length())));
			else if (args[i].equals("--silentPack"))
				runSilent = true;
		}

		if (runSilent) {
			AppContext.pack();
			return;
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE);

				LwjglCanvas lwjglCanvas = new LwjglCanvas(new App(), false);

				MainWindow mw = new MainWindow();
				mw.setVisible(true);
				mw.setCanvas(lwjglCanvas.getCanvas());
			}
		});
    }
}
