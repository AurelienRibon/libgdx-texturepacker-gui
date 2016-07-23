package aurelienribon.texturepackergui;

import aurelienribon.ui.components.PaintedPanel;
import aurelienribon.ui.css.Style;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

/**
 * @author Aurelien Ribon | http://www.aurelienribon.com/
 */
public class PackDialog extends JDialog {

	private JButton closeBtn;
	private JLabel jLabel1;
	private JScrollPane jScrollPane1;
	private PaintedPanel paintedPanel1;
	private JTextArea textArea;

    public PackDialog(JFrame parent) {
        super(parent, true);

		setContentPane(new PaintedPanel());
        initComponents();

		closeBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {dispose();}});

		Style.registerCssClasses(getContentPane(), ".rootPanel", ".configPanel");
		Style.apply(getContentPane(), new Style(Gdx.files.internal("css/style.css").readString()));
    }

	public void launchPack(final Pack pack) {
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream, true));
		System.setErr(new PrintStream(stream, true));

		final Timer timer = new Timer(100, new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				textArea.append(stream.toString());
				stream.reset();
			}
		});

		timer.setRepeats(true);
		timer.start();
		closeBtn.setEnabled(false);

		new Thread(new Runnable() {@Override public void run() {
			System.out.println("-------------------------------------------------------");
			System.out.println("-- Starting TexturePacker for pack '" + pack.getName() + "'");
			System.out.println("-------------------------------------------------------");

			boolean isValid = true;

			if (!new File(pack.getInput()).isDirectory()) {
				System.err.println("[error] Input directory is not valid.");
				isValid = false;
			}

			if (isValid) {
				try {
					TexturePacker.process(pack.getSettings(), pack.getInput(), pack.getOutput(), pack.getFilename());
					System.out.println("Done!");
				} catch (RuntimeException ex) {
					System.err.println("[error] Exception occured: " + ex.getMessage());
				}
			}

			textArea.append(stream.toString());
			timer.stop();
			IOUtils.closeQuietly(stream);
			closeBtn.setEnabled(true);
			closeBtn.requestFocusInWindow();
			System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
			System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));

		}}).start();
	}

	public void launchPack(final List<Pack> packs) {
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream, true));
		System.setErr(new PrintStream(stream, true));

		final Timer timer = new Timer(100, new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				textArea.append(stream.toString());
				stream.reset();
			}
		});

		timer.setRepeats(true);
		timer.start();
		closeBtn.setEnabled(false);

		new Thread(new Runnable() {@Override public void run() {
			for (Pack pack : packs) {
				System.out.println("-------------------------------------------------------");
				System.out.println("-- Starting TexturePacker for pack '" + pack.getName() + "'");
				System.out.println("-------------------------------------------------------");

				boolean isValid = true;

				if (!new File(pack.getInput()).isDirectory()) {
					System.err.println("[error] Input directory is not valid.");
					isValid = false;
				}

				if (isValid) {
					try {
						TexturePacker.process(pack.getSettings(), pack.getInput(), pack.getOutput(), pack.getFilename());
						System.out.println("Done!");
					} catch (RuntimeException ex) {
						System.err.println("[error] Exception occured: " + ex.getMessage());
					}
				}

				System.out.println("\n");
			}

			textArea.append(stream.toString());
			timer.stop();
			IOUtils.closeQuietly(stream);
			closeBtn.setEnabled(true);
			closeBtn.requestFocusInWindow();
			System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
			System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));

		}}).start();
	}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paintedPanel1 = new PaintedPanel();
        closeBtn = new JButton();
        jScrollPane1 = new JScrollPane();
        textArea = new JTextArea();
        jLabel1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Processing...");

        closeBtn.setText("Close");

        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        textArea.setColumns(20);
        textArea.setEditable(false);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        jLabel1.setText("TexturePacker processing");

        GroupLayout paintedPanel1Layout = new GroupLayout(paintedPanel1);
        paintedPanel1.setLayout(paintedPanel1Layout);
        paintedPanel1Layout.setHorizontalGroup(
            paintedPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(paintedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paintedPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(paintedPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 360, Short.MAX_VALUE)
                        .addComponent(closeBtn, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        paintedPanel1Layout.setVerticalGroup(
            paintedPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, paintedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paintedPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(closeBtn)
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paintedPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paintedPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

}
