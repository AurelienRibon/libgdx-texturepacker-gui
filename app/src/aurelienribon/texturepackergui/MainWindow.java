package aurelienribon.texturepackergui;

import aurelienribon.texturepackergui.canvas.Canvas;
import aurelienribon.ui.components.ArStyle;
import aurelienribon.ui.components.PaintedPanel;
import aurelienribon.ui.css.Style;
import aurelienribon.ui.css.swing.SwingStyle;
import aurelienribon.utils.VersionLabel;
import aurelienribon.utils.notifications.AutoListModel;
import aurelienribon.utils.notifications.ObservableList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import static aurelienribon.texturepackergui.Prefs.*;
import static aurelienribon.utils.ImageUtil.loadImage;

public class MainWindow extends JFrame {
	private final Canvas canvas;
	private final ObservableList<Pack> packs = new ObservableList<Pack>();

	public MainWindow(final Canvas canvas, Component canvasCmp) {
		try {
			Font font1 = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/SquareFont.ttf"));
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font1);
		} catch (FontFormatException | IOException ex) {
			throw new RuntimeException("Cannot initialize font", ex);
		}

		this.canvas = canvas;

		canvas.setCallback(new Canvas.Callback() {
			@Override public void atlasError() {
				JOptionPane.showMessageDialog(MainWindow.this, "Impossible to create the atlas in LibGDX canvas, sorry.");
			}
		});

		// Will finish up application properly on close
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});

		setContentPane(new PaintedPanel());
		getContentPane().setLayout(new BorderLayout());
        initComponents();
		renderPanel.add(canvasCmp, BorderLayout.CENTER);

		newPackBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {newPack();}});
		copyPackBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {copyPack();}});
		renamePackBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {renamePack();}});
		deletePackBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {deletePack();}});
		moveUpPackBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {moveUp();}});
		moveDownPackBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {moveDown();}});
		openProjectBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {loadProject();}});
		saveProjectBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {saveProject();}});
		browseInputBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {browseInput();}});
		browseOutputBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {browseOutput();}});
		packSelectedBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {packSelected();}});
		packAllBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {packAll();}});
		copySettingsBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {copySettingsToAll();}});

		packsList.setModel(new AutoListModel<Pack>(packs));
		packsList.setCellRenderer(packsListCellRenderer);
		packsList.addListSelectionListener(packsListSelectionListener);
		packsListSelectionListener.valueChanged(null);

		SwingStyle.init();
		ArStyle.init();
		Style.registerCssClasses(getContentPane(), ".rootPanel");
		Style.registerCssClasses(projectPanel, ".titledPanel", "#projectPanel");
		Style.registerCssClasses(settingsPanel, ".titledPanel", "#settingsPanel");
		Style.registerCssClasses(renderPanel, ".titledPanel", "#renderPanel");
		Style.registerCssClasses(headerPanel, ".headerPanel");
		Style.registerCssClasses(headerPanel1, ".headerPanel");
		Style.registerCssClasses(jScrollPane1, ".listPanel");
		Style.registerCssClasses(commentLabel, ".comment");
		Style.registerCssClasses(versionLabel, ".versionLabel");
		Style.apply(getContentPane(), new Style(Gdx.files.internal("css/style.css").readString()));

		//TODO rework version checking logic
		versionLabel.initAndCheck("3.2.0", null,
			"http://www.aurelienribon.com/projects/libgdx-texturepacker-gui/versions.txt",
			"http://code.google.com/p/libgdx-texturepacker-gui/");
    }

	public void load(File file) throws IOException {
		packs.replaceBy(Pack.parse(file));
		if (packs.isEmpty()) {
			packsList.clearSelection();
		} else {
			packsList.setSelectedIndex(0);
		}

		// Update dialog dirs
		File dialogDir = file.getParentFile();
		storeFile(KEY_LAST_DIR_PROJ, dialogDir);
		storeFile(KEY_LAST_DIR_OUTPUT, dialogDir);
	}

	private final ListCellRenderer packsListCellRenderer = new DefaultListCellRenderer() {
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Pack pack = (Pack) value;
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setText(pack.getName());
			return label;
		}
	};

	private final ListSelectionListener packsListSelectionListener = new ListSelectionListener() {
		private Pack selectedPack;

		@Override
		public void valueChanged(ListSelectionEvent e) {
			Pack pack = (Pack) packsList.getSelectedValue();
			if (selectedPack != null) savePack(selectedPack);
			selectedPack = pack;

			copyPackBtn.setEnabled(pack != null);
			renamePackBtn.setEnabled(pack != null);
			deletePackBtn.setEnabled(pack != null);
			moveUpPackBtn.setEnabled(pack != null);
			moveDownPackBtn.setEnabled(pack != null);
			inputField.setEnabled(pack != null);
			outputField.setEnabled(pack != null);
			filenameField.setEnabled(pack != null);
			browseInputBtn.setEnabled(pack != null);
			browseOutputBtn.setEnabled(pack != null);
			packSelectedBtn.setEnabled(pack != null);
			packAllBtn.setEnabled(pack != null);
			enable(settingsPanel, pack != null);

			if (pack != null) {
				loadPack(pack);
				canvas.requestPackReload(pack.getOutput() + "/" + pack.getFilename());
			} else {
				inputField.setText("");
				outputField.setText("");
				filenameField.setText("");
				canvas.requestPackReload(null);
			}
		}
	};

	private void newPack() {
		Pack pack = new Pack();
		String name = JOptionPane.showInputDialog(this, "Name of the pack?", "");
		if (name != null) {
			pack.setName(name);
			packs.add(pack);
			packsList.setSelectedValue(pack, true);
		}
	}

	private void copyPack() {
		Pack selectedPack = (Pack) packsList.getSelectedValue();
		Pack pack = new Pack(selectedPack);
		String name = JOptionPane.showInputDialog(this, "Name of the pack?", selectedPack.getName());
		if (name != null) {
			pack.setName(name);
			packs.add(pack);
			packsList.setSelectedValue(pack, true);
		}
	}

	private void renamePack() {
		Pack pack = (Pack) packsList.getSelectedValue();
		String name = JOptionPane.showInputDialog(this, "New name of the pack?", pack.getName());
		if (name != null) pack.setName(name);
	}

	private void deletePack() {
		Pack pack = (Pack) packsList.getSelectedValue();
		packs.remove(pack);
		packsList.clearSelection();
	}

	private void moveUp() {
		Pack pack = (Pack) packsList.getSelectedValue();
		int idx = packs.indexOf(pack);
		if (idx > 0) {
			Pack pack2 = packs.get(idx-1);
			packs.set(idx-1, pack);
			packs.set(idx, pack2);
			packsList.setSelectedValue(pack, true);
		}
	}

	private void moveDown() {
		Pack pack = (Pack) packsList.getSelectedValue();
		int idx = packs.indexOf(pack);
		if (idx < packs.size()-1) {
			Pack pack2 = packs.get(idx+1);
			packs.set(idx+1, pack);
			packs.set(idx, pack2);
			packsList.setSelectedValue(pack, true);
		}
	}

	private void loadProject() {
		JFileChooser chooser = new JFileChooser(getFile(KEY_LAST_DIR_PROJ));
		chooser.setDialogTitle("Select your project file");
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("GDX Texture Packer project", "packprj"));

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				File file = chooser.getSelectedFile();
				storeFile(KEY_LAST_DIR_PROJ, file.getParentFile());
				packs.replaceBy(Pack.parse(file));
				if (packs.isEmpty()) {
                    packsList.clearSelection();
                } else {
				    packsList.setSelectedIndex(0);
                }
            } catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Project file cannot be read.");
			}
		}
	}

	private void saveProject() {
		Pack pack = (Pack) packsList.getSelectedValue();
		if (pack != null) savePack(pack);

		JFileChooser chooser = new JFileChooser(getFile(KEY_LAST_DIR_PROJ));
		chooser.setDialogTitle("Select your project file");

		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				File file = chooser.getSelectedFile();
				storeFile(KEY_LAST_DIR_PROJ, file.getParentFile());
				Pack.export(file, packs);
				JOptionPane.showMessageDialog(this, "Save done.");
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Project file cannot be written to.");
			}
		}
	}

	private void browseInput() {
		// Resolving dialog directory
		File openDir = getFile(KEY_LAST_DIR_INPUT);
		Pack pack = (Pack) packsList.getSelectedValue();
		String inputSt = pack.getInput();
		if (inputSt != null && !inputSt.trim().isEmpty()) {
			File inputDir = new File(inputSt);
			if (inputDir.exists() && inputDir.isDirectory()) {
				openDir = inputDir;
			}
		}

		JFileChooser chooser = new JFileChooser(openDir);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setFileFilter(new FileFilter() {
			@Override public boolean accept(File f) {return f.isDirectory();}
			@Override public String getDescription() {return "Directories";}
		});

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			inputField.setText(file.getPath());

			storeFile(KEY_LAST_DIR_INPUT, file);
		}
	}

	private void browseOutput() {
		// Resolving dialog directory
		File openDir = getFile(KEY_LAST_DIR_OUTPUT);
		Pack pack = (Pack) packsList.getSelectedValue();
		String outputSt = pack.getOutput();
		if (outputSt != null && !outputSt.trim().isEmpty()) {
			File outputDir = new File(outputSt);
			if (outputDir.exists() && outputDir.isDirectory()) {
				openDir = outputDir;
			}
		}

		JFileChooser chooser = new JFileChooser(openDir);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setFileFilter(new FileFilter() {
			@Override public boolean accept(File f) {return f.isDirectory();}
			@Override public String getDescription() {return "Directories";}
		});

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			outputField.setText(file.getPath());

			storeFile(KEY_LAST_DIR_OUTPUT, file);
		}
	}

	private void packSelected() {
		Pack pack = (Pack) packsList.getSelectedValue();
		savePack(pack);

		PackDialog dialog = new PackDialog(this);
		dialog.launchPack(pack);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);

		canvas.requestPackReload(pack.getOutput() + "/" + pack.getFilename());
	}

	private void packAll() {
		Pack pack = (Pack) packsList.getSelectedValue();
		savePack(pack);

		PackDialog dialog = new PackDialog(this);
		dialog.launchPack(packs);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);

		canvas.requestPackReload(pack.getOutput() + "/" + pack.getFilename());
	}

	private void copySettingsToAll() {
		Pack pack = (Pack) packsList.getSelectedValue();
		savePack(pack);

		String settings = pack.exportSettings();
		for (Pack pack2 : packs) pack2.importSettings(settings);

		JOptionPane.showMessageDialog(this, "Successful! All packs now use the current settings.");
	}

	private void loadPack(Pack pack) {
		inputField.setText(pack.getInput());
		outputField.setText(pack.getOutput());
		filenameField.setText(pack.getRawFilename());

		Settings stgs = pack.getSettings();

		opt_alias_chk.setSelected(stgs.alias);
		opt_alphaThreashold_nud.setValue(stgs.alphaThreshold);
		opt_debug_chk.setSelected(stgs.debug);
		opt_duplicatePadding_chk.setSelected(stgs.duplicatePadding);
		opt_edgePadding_chk.setSelected(stgs.edgePadding);
		opt_fast_chk.setSelected(stgs.fast);
		opt_filterMag_cbox.setSelectedItem(stgs.filterMag.toString());
		opt_filterMin_cbox.setSelectedItem(stgs.filterMin.toString());
		opt_format_cbox.setSelectedItem(stgs.format.toString());
		opt_ignoreBlankImages_chk.setSelected(stgs.ignoreBlankImages);
		opt_jpegQuality_nud.setValue(stgs.jpegQuality);
		opt_maxPageHeight_nud.setValue(stgs.maxHeight);
		opt_maxPageWidth_nud.setValue(stgs.maxWidth);
		opt_minPageHeight_nud.setValue(stgs.minHeight);
		opt_minPageWidth_nud.setValue(stgs.minWidth);
		opt_outputFormat_cbox.setSelectedItem(stgs.outputFormat.toString());
		opt_paddingX_nud.setValue(stgs.paddingX);
		opt_paddingX_nud.setValue(stgs.paddingY);
		opt_pot_chk.setSelected(stgs.pot);
		opt_rotation_chk.setSelected(stgs.rotation);
		opt_use_indices.setSelected(stgs.useIndexes);
		opt_subdirs.setSelected(stgs.combineSubdirectories);
		opt_stripWhitespaceX_chk.setSelected(stgs.stripWhitespaceX);
		opt_stripWhitespaceY_chk.setSelected(stgs.stripWhitespaceY);
		opt_wrapX_cbox.setSelectedItem(stgs.wrapX);
		opt_wrapY_cbox.setSelectedItem(stgs.wrapY);
	}

	private void savePack(Pack pack) {
		pack.setInput(inputField.getText().trim());
		pack.setOutput(outputField.getText().trim());
		pack.setFilename(filenameField.getText().trim());

		Settings stgs = pack.getSettings();

		stgs.alias = opt_alias_chk.isSelected();
		stgs.alphaThreshold = (Integer)opt_alphaThreashold_nud.getValue();
		stgs.debug = opt_debug_chk.isSelected();
		stgs.duplicatePadding = opt_duplicatePadding_chk.isSelected();
		stgs.edgePadding = opt_edgePadding_chk.isSelected();
		stgs.fast = opt_fast_chk.isSelected();
		stgs.filterMag = TextureFilter.valueOf((String)opt_filterMag_cbox.getSelectedItem());
		stgs.filterMin = TextureFilter.valueOf((String)opt_filterMin_cbox.getSelectedItem());
		stgs.format = Format.valueOf((String)opt_format_cbox.getSelectedItem());
		stgs.ignoreBlankImages = opt_ignoreBlankImages_chk.isSelected();
		stgs.jpegQuality = (Float)opt_jpegQuality_nud.getValue();
		stgs.maxHeight = (Integer)opt_maxPageHeight_nud.getValue();
		stgs.maxWidth = (Integer)opt_maxPageWidth_nud.getValue();
		stgs.minHeight = (Integer)opt_minPageHeight_nud.getValue();
		stgs.minWidth = (Integer)opt_minPageWidth_nud.getValue();
		stgs.outputFormat = (String)opt_outputFormat_cbox.getSelectedItem();
		stgs.paddingX = (Integer)opt_paddingX_nud.getValue();
		stgs.paddingY = (Integer)opt_paddingY_nud.getValue();
		stgs.pot = opt_pot_chk.isSelected();
		stgs.rotation = opt_rotation_chk.isSelected();
		stgs.combineSubdirectories = opt_subdirs.isSelected();
		stgs.useIndexes = opt_use_indices.isSelected();
		stgs.stripWhitespaceX = opt_stripWhitespaceX_chk.isSelected();
		stgs.stripWhitespaceY = opt_stripWhitespaceY_chk.isSelected();
		stgs.wrapX = TextureWrap.valueOf((String)opt_wrapX_cbox.getSelectedItem());
		stgs.wrapY = TextureWrap.valueOf((String)opt_wrapY_cbox.getSelectedItem());
	}

	private void enable(Component cmp, boolean value) {
		cmp.setEnabled(value);
		if (cmp instanceof Container) {
			Container c = (Container) cmp;
			for (Component child : c.getComponents()) enable(child, value);
		}
	}

	// -------------------------------------------------------------------------
	// Generated stuff
	// -------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        configPanel = new JPanel();
        projectPanel = new JPanel();
        headerPanel = new JPanel();
        jToolBar1 = new JToolBar();
        newPackBtn = new JButton();
        renamePackBtn = new JButton();
		copyPackBtn = new JButton();
        deletePackBtn = new JButton();
        moveUpPackBtn = new JButton();
        moveDownPackBtn = new JButton();
        jToolBar2 = new JToolBar();
        openProjectBtn = new JButton();
        saveProjectBtn = new JButton();
        jPanel2 = new JPanel();
        packAllBtn = new JButton();
        jPanel1 = new JPanel();
        jLabel11 = new JLabel();
        inputField = new JTextField();
        outputField = new JTextField();
        browseOutputBtn = new JButton();
        filenameField = new JTextField();
        jLabel12 = new JLabel();
        browseInputBtn = new JButton();
        jLabel1 = new JLabel();
        packSelectedBtn = new JButton();
        jScrollPane1 = new JScrollPane();
        packsList = new JList();
        commentLabel = new JLabel();
        settingsPanel = new JPanel();
        headerPanel1 = new JPanel();
        jToolBar4 = new JToolBar();
        copySettingsBtn = new JButton();
        jPanel3 = new JPanel();
        jLabel8 = new JLabel();
        opt_minPageHeight_nud = new JSpinner();
		opt_use_indices = new JCheckBox();
        opt_pot_chk = new JCheckBox();
        opt_duplicatePadding_chk = new JCheckBox();
        opt_alias_chk = new JCheckBox();
        opt_minPageWidth_nud = new JSpinner();
        opt_maxPageWidth_nud = new JSpinner();
        opt_ignoreBlankImages_chk = new JCheckBox();
        jLabel9 = new JLabel();
        opt_alphaThreashold_nud = new JSpinner();
        opt_debug_chk = new JCheckBox();
        opt_fast_chk = new JCheckBox();
        opt_subdirs = new JCheckBox();
        jLabel18 = new JLabel();
        opt_stripWhitespaceY_chk = new JCheckBox();
        jLabel6 = new JLabel();
        opt_rotation_chk = new JCheckBox();
        jLabel7 = new JLabel();
        jLabel5 = new JLabel();
        opt_edgePadding_chk = new JCheckBox();
        opt_paddingX_nud = new JSpinner();
        jLabel10 = new JLabel();
        opt_maxPageHeight_nud = new JSpinner();
        opt_jpegQuality_nud = new JSpinner();
        opt_format_cbox = new JComboBox();
        opt_filterMin_cbox = new JComboBox();
        opt_outputFormat_cbox = new JComboBox();
        opt_filterMag_cbox = new JComboBox();
        jLabel4 = new JLabel();
        jLabel3 = new JLabel();
        jLabel17 = new JLabel();
        jLabel2 = new JLabel();
        jLabel16 = new JLabel();
        opt_paddingY_nud = new JSpinner();
        jLabel19 = new JLabel();
        jLabel20 = new JLabel();
        opt_wrapX_cbox = new JComboBox();
        opt_wrapY_cbox = new JComboBox();
        opt_stripWhitespaceX_chk = new JCheckBox();
        versionLabel = new VersionLabel();
        centerPanel = new JPanel();
        renderPanel = new JPanel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GDX Texture Packer");

        configPanel.setOpaque(false);

        projectPanel.setLayout(new BorderLayout());

        headerPanel.setLayout(new BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

        newPackBtn.setIcon(new ImageIcon(loadImage("gfx/ic_add.png"))); // NOI18N

        newPackBtn.setText("New pack");
        newPackBtn.setToolTipText("Add a new pack to the list");
        newPackBtn.setFocusable(false);
        newPackBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(newPackBtn);

        renamePackBtn.setIcon(new ImageIcon(loadImage("gfx/ic_edit.png"))); // NOI18N
        renamePackBtn.setToolTipText("Rename the selected pack");
        renamePackBtn.setFocusable(false);
        jToolBar1.add(renamePackBtn);

        copyPackBtn.setIcon(new ImageIcon(loadImage("gfx/ic_copy.png"))); // NOI18N
        copyPackBtn.setToolTipText("Create copy of selected pack");
        copyPackBtn.setFocusable(false);
        jToolBar1.add(copyPackBtn);

        deletePackBtn.setIcon(new ImageIcon(loadImage("gfx/ic_delete.png"))); // NOI18N
        deletePackBtn.setToolTipText("Delete the selected pack");
        deletePackBtn.setFocusable(false);
        deletePackBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(deletePackBtn);

        moveUpPackBtn.setIcon(new ImageIcon(loadImage("gfx/ic_up.png"))); // NOI18N
        moveUpPackBtn.setToolTipText("Move the selected pack up in the list");
        moveUpPackBtn.setFocusable(false);
        moveUpPackBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        moveUpPackBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(moveUpPackBtn);

        moveDownPackBtn.setIcon(new ImageIcon(loadImage("gfx/ic_down.png"))); // NOI18N
        moveDownPackBtn.setToolTipText("Move the selected pack down in the list");
        moveDownPackBtn.setFocusable(false);
        moveDownPackBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        moveDownPackBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(moveDownPackBtn);

        headerPanel.add(jToolBar1, BorderLayout.CENTER);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        openProjectBtn.setIcon(new ImageIcon(loadImage("gfx/ic_open.png"))); // NOI18N
        openProjectBtn.setText("Open project");
        openProjectBtn.setToolTipText("");
        openProjectBtn.setFocusable(false);
        openProjectBtn.setHorizontalAlignment(SwingConstants.LEFT);
        openProjectBtn.setMargin(new Insets(2, 3, 2, 3));
        jToolBar2.add(openProjectBtn);

        saveProjectBtn.setIcon(new ImageIcon(loadImage("gfx/ic_save.png"))); // NOI18N
        saveProjectBtn.setText("Save project");
        saveProjectBtn.setFocusable(false);
        saveProjectBtn.setHorizontalAlignment(SwingConstants.LEFT);
        saveProjectBtn.setMargin(new Insets(2, 3, 2, 3));
        jToolBar2.add(saveProjectBtn);

        headerPanel.add(jToolBar2, BorderLayout.EAST);

        projectPanel.add(headerPanel, BorderLayout.NORTH);

        jPanel2.setOpaque(false);

        packAllBtn.setIcon(new ImageIcon(loadImage("gfx/ic_pack.png"))); // NOI18N
        packAllBtn.setText("Pack'em all");
        packAllBtn.setMargin(new Insets(2, 3, 2, 3));

        jPanel1.setOpaque(false);

        jLabel11.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel11.setText("Output dir:");

        inputField.setColumns(10);

        outputField.setColumns(10);

        browseOutputBtn.setText("...");
        browseOutputBtn.setMargin(new Insets(2, 3, 2, 2));
        browseOutputBtn.setOpaque(false);

        filenameField.setColumns(10);

        jLabel12.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel12.setText("File name:");

        browseInputBtn.setText("...");
        browseInputBtn.setMargin(new Insets(2, 3, 2, 2));
        browseInputBtn.setOpaque(false);

        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText("Input dir:");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel12, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, GroupLayout.Alignment.LEADING))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(outputField, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(inputField))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(browseInputBtn)
                            .addComponent(browseOutputBtn)))
                    .addComponent(filenameField)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(inputField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseInputBtn)
                    .addComponent(jLabel1))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(outputField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseOutputBtn))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(filenameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        );

        packSelectedBtn.setIcon(new ImageIcon(loadImage("gfx/ic_pack.png"))); // NOI18N
        packSelectedBtn.setText("Pack selected");
        packSelectedBtn.setMargin(new Insets(2, 3, 2, 3));

        packsList.setModel(new AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        packsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(packsList);

        commentLabel.setText("Leave blank for \"<packname>.atlas\"");

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(packAllBtn)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(packSelectedBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(commentLabel)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(commentLabel))
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(packSelectedBtn)
                    .addComponent(packAllBtn))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        projectPanel.add(jPanel2, BorderLayout.CENTER);

        settingsPanel.setLayout(new BorderLayout());

        headerPanel1.setLayout(new BorderLayout());

        jToolBar4.setFloatable(false);
        jToolBar4.setRollover(true);

        copySettingsBtn.setIcon(new ImageIcon(loadImage("gfx/ic_copy.png"))); // NOI18N
        copySettingsBtn.setText("Copy settings to all packs");
        copySettingsBtn.setFocusable(false);
        copySettingsBtn.setHorizontalAlignment(SwingConstants.LEFT);
        copySettingsBtn.setMargin(new Insets(2, 3, 2, 3));
        jToolBar4.add(copySettingsBtn);

        headerPanel1.add(jToolBar4, BorderLayout.WEST);

        settingsPanel.add(headerPanel1, BorderLayout.NORTH);

        jPanel3.setOpaque(false);

        jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel8.setText("Min page height");

        opt_minPageHeight_nud.setModel(new SpinnerNumberModel(Integer.valueOf(16), Integer.valueOf(0), null, Integer.valueOf(1)));

        opt_pot_chk.setText("Force PoT");
        opt_pot_chk.setOpaque(false);

		opt_use_indices.setText("Use indices");
		opt_use_indices.setOpaque(false);

        opt_duplicatePadding_chk.setText("Duplicate padding");
        opt_duplicatePadding_chk.setOpaque(false);

        opt_alias_chk.setText("Use aliases");
        opt_alias_chk.setOpaque(false);

        opt_minPageWidth_nud.setModel(new SpinnerNumberModel(Integer.valueOf(16), Integer.valueOf(0), null, Integer.valueOf(1)));

        opt_maxPageWidth_nud.setModel(new SpinnerNumberModel(Integer.valueOf(512), Integer.valueOf(0), null, Integer.valueOf(1)));

        opt_ignoreBlankImages_chk.setText("Ignore blank imgs");
        opt_ignoreBlankImages_chk.setOpaque(false);

        jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel9.setText("Max page width");

        opt_alphaThreashold_nud.setModel(new SpinnerNumberModel(0, 0, 255, 1));

        opt_debug_chk.setText("Debug");
        opt_debug_chk.setOpaque(false);

        opt_fast_chk.setText("Use fast algorithm");
        opt_fast_chk.setOpaque(false);

        jLabel18.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel18.setText("Jpeg quality");

        opt_stripWhitespaceY_chk.setText("Strip whitespace Y");
        opt_stripWhitespaceY_chk.setOpaque(false);

        jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel6.setText("PaddingX");

        opt_rotation_chk.setText("Allow rotations");
        opt_rotation_chk.setOpaque(false);

		opt_subdirs.setText("Include subdirs");
		opt_subdirs.setOpaque(false);

        jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel7.setText("Min page width");

        jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel5.setText("Alpha threshold");

        opt_edgePadding_chk.setText("Edge padding");
        opt_edgePadding_chk.setOpaque(false);

        opt_paddingX_nud.setModel(new SpinnerNumberModel(Integer.valueOf(2), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel10.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel10.setText("Max page height");

        opt_maxPageHeight_nud.setModel(new SpinnerNumberModel(Integer.valueOf(512), Integer.valueOf(0), null, Integer.valueOf(1)));

        opt_jpegQuality_nud.setModel(new SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.05f)));

        opt_format_cbox.setModel(new DefaultComboBoxModel(new String[] { "RGBA8888", "RGBA4444", "RGB888", "RGB565", "Alpha", "LuminanceAlpha", "Intensity" }));
        opt_format_cbox.setOpaque(false);

        opt_filterMin_cbox.setModel(new DefaultComboBoxModel(new String[] { "Nearest", "Linear", "MipMap", "MipMapNearestNearest", "MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear" }));
        opt_filterMin_cbox.setOpaque(false);

        opt_outputFormat_cbox.setModel(new DefaultComboBoxModel(new String[] { "png", "jpg" }));
        opt_outputFormat_cbox.setOpaque(false);

        opt_filterMag_cbox.setModel(new DefaultComboBoxModel(new String[] { "Nearest", "Linear", "MipMap", "MipMapNearestNearest", "MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear" }));
        opt_filterMag_cbox.setOpaque(false);

        jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel4.setText("Mag filter");

        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setText("Min filter");

        jLabel17.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel17.setText("Output format");

        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText("Encoding format");

        jLabel16.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel16.setText("PaddingY");

        opt_paddingY_nud.setModel(new SpinnerNumberModel(Integer.valueOf(2), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel19.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel19.setText("WrapX");

        jLabel20.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel20.setText("WrapY");

        opt_wrapX_cbox.setModel(new DefaultComboBoxModel(new String[] { "ClampToEdge", "Repeat" }));
        opt_wrapX_cbox.setOpaque(false);

        opt_wrapY_cbox.setModel(new DefaultComboBoxModel(new String[] { "ClampToEdge", "Repeat" }));
        opt_wrapY_cbox.setOpaque(false);

        opt_stripWhitespaceX_chk.setText("Strip whitespace X");
        opt_stripWhitespaceX_chk.setOpaque(false);

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel2))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(opt_format_cbox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(opt_outputFormat_cbox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_filterMag_cbox, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
                            .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_filterMin_cbox, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(opt_duplicatePadding_chk)
                            .addComponent(opt_edgePadding_chk)
                            .addComponent(opt_fast_chk)
                            .addComponent(opt_rotation_chk)
                            .addComponent(opt_subdirs)
                            .addComponent(opt_stripWhitespaceY_chk)
                            .addComponent(opt_stripWhitespaceX_chk))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(opt_jpegQuality_nud))
                                .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(opt_alphaThreashold_nud, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)))
                            .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(opt_ignoreBlankImages_chk)
                                .addComponent(opt_debug_chk)
                                .addComponent(opt_use_indices)
                                .addComponent(opt_pot_chk)
                                .addComponent(opt_alias_chk))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_minPageHeight_nud, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_maxPageWidth_nud, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_maxPageHeight_nud, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_minPageWidth_nud, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel16)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(opt_paddingY_nud, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(opt_paddingX_nud)))
                            .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel19))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(opt_wrapX_cbox, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(opt_wrapY_cbox, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(SwingConstants.HORIZONTAL, opt_format_cbox, opt_maxPageHeight_nud, opt_maxPageWidth_nud, opt_minPageHeight_nud, opt_minPageWidth_nud, opt_outputFormat_cbox);

        jPanel3Layout.linkSize(SwingConstants.HORIZONTAL, opt_duplicatePadding_chk, opt_edgePadding_chk, opt_fast_chk, opt_rotation_chk, opt_stripWhitespaceY_chk, opt_subdirs);

        jPanel3Layout.linkSize(SwingConstants.HORIZONTAL, opt_alias_chk, opt_debug_chk, opt_use_indices, opt_ignoreBlankImages_chk, opt_pot_chk);

        jPanel3Layout.linkSize(SwingConstants.HORIZONTAL, jLabel10, jLabel16, jLabel17, jLabel18, jLabel19, jLabel2, jLabel20, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9);

        jPanel3Layout.linkSize(SwingConstants.HORIZONTAL, opt_alphaThreashold_nud, opt_filterMag_cbox, opt_filterMin_cbox, opt_jpegQuality_nud, opt_paddingX_nud, opt_paddingY_nud, opt_wrapX_cbox, opt_wrapY_cbox);

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(opt_filterMin_cbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(opt_filterMag_cbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(opt_format_cbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(opt_outputFormat_cbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(opt_minPageWidth_nud, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(opt_minPageHeight_nud, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(opt_paddingX_nud, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(opt_paddingY_nud, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(opt_maxPageWidth_nud, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(opt_maxPageHeight_nud, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(opt_wrapX_cbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(opt_wrapY_cbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(opt_fast_chk)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opt_duplicatePadding_chk)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opt_edgePadding_chk)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opt_stripWhitespaceX_chk)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opt_stripWhitespaceY_chk)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opt_rotation_chk)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opt_subdirs))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(opt_jpegQuality_nud, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(opt_alphaThreashold_nud, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(opt_ignoreBlankImages_chk, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_debug_chk)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_use_indices))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(opt_pot_chk)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_alias_chk)))))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        settingsPanel.add(jPanel3, BorderLayout.CENTER);

        versionLabel.setText("versionLabel1");

        GroupLayout configPanelLayout = new GroupLayout(configPanel);
        configPanel.setLayout(configPanelLayout);
        configPanelLayout.setHorizontalGroup(
            configPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(configPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(projectPanel, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(settingsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(versionLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        configPanelLayout.setVerticalGroup(
            configPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(projectPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(settingsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(versionLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(configPanel, BorderLayout.WEST);

        centerPanel.setOpaque(false);

        renderPanel.setLayout(new BorderLayout());

        GroupLayout centerPanelLayout = new GroupLayout(centerPanel);
        centerPanel.setLayout(centerPanelLayout);
        centerPanelLayout.setHorizontalGroup(
            centerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, centerPanelLayout.createSequentialGroup()
                .addComponent(renderPanel, GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                .addContainerGap())
        );
        centerPanelLayout.setVerticalGroup(
            centerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(centerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(renderPanel, GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(centerPanel, BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton browseInputBtn;
    private JButton browseOutputBtn;
    private JPanel centerPanel;
    private JLabel commentLabel;
    private JPanel configPanel;
	private JButton copyPackBtn;
    private JButton copySettingsBtn;
    private JButton deletePackBtn;
    private JTextField filenameField;
    private JPanel headerPanel;
    private JPanel headerPanel1;
    private JTextField inputField;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel18;
    private JLabel jLabel19;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private JToolBar jToolBar1;
    private JToolBar jToolBar2;
    private JToolBar jToolBar4;
    private JButton moveDownPackBtn;
    private JButton moveUpPackBtn;
    private JButton newPackBtn;
    private JButton openProjectBtn;
    private JCheckBox opt_alias_chk;
    private JSpinner opt_alphaThreashold_nud;
    private JCheckBox opt_debug_chk;
    private JCheckBox opt_duplicatePadding_chk;
    private JCheckBox opt_edgePadding_chk;
    private JCheckBox opt_fast_chk;
    private JComboBox opt_filterMag_cbox;
	private JComboBox opt_filterMin_cbox;
	private JComboBox opt_format_cbox;
	private JCheckBox opt_ignoreBlankImages_chk;
	private JSpinner opt_jpegQuality_nud;
	private JSpinner opt_maxPageHeight_nud;
	private JSpinner opt_maxPageWidth_nud;
	private JSpinner opt_minPageHeight_nud;
	private JSpinner opt_minPageWidth_nud;
	private JComboBox opt_outputFormat_cbox;
	private JSpinner opt_paddingX_nud;
	private JSpinner opt_paddingY_nud;
	private JCheckBox opt_pot_chk;
	private JCheckBox opt_rotation_chk;
	private JCheckBox opt_use_indices;
	private JCheckBox opt_stripWhitespaceX_chk;
	private JCheckBox opt_stripWhitespaceY_chk;
	private JCheckBox opt_subdirs;
	private JComboBox opt_wrapX_cbox;
    private JComboBox opt_wrapY_cbox;
    private JTextField outputField;
    private JButton packAllBtn;
    private JButton packSelectedBtn;
    private JList packsList;
    private JPanel projectPanel;
    private JButton renamePackBtn;
    private JPanel renderPanel;
    private JButton saveProjectBtn;
    private JPanel settingsPanel;
    private VersionLabel versionLabel;
    // End of variables declaration//GEN-END:variables
}
