package aurelienribon.texturepackergui;

import aurelienribon.ui.components.ArStyle;
import aurelienribon.ui.components.PaintedPanel;
import aurelienribon.ui.css.Style;
import aurelienribon.ui.css.swing.SwingStyle;
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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static aurelienribon.utils.ImageUtil.loadImage;

public class MainWindow extends javax.swing.JFrame {
	private final Canvas canvas;
	private final ObservableList<Pack> packs = new ObservableList<Pack>();
	private File lastDir = new File(".");

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

		setContentPane(new PaintedPanel());
		getContentPane().setLayout(new BorderLayout());
        initComponents();
		renderPanel.add(canvasCmp, BorderLayout.CENTER);

		newPackBtn.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {newPack();}});
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
		if (packs.isEmpty()) packsList.clearSelection();
		else packsList.setSelectedIndex(0);
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
		JFileChooser chooser = new JFileChooser(lastDir);
		chooser.setDialogTitle("Select your project file");

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				File file = chooser.getSelectedFile();
				lastDir = file.getParentFile();
				packs.replaceBy(Pack.parse(file));
				if (packs.isEmpty()) packsList.clearSelection();
				else packsList.setSelectedIndex(0);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Project file cannot be read.");
			}
		}
	}

	private void saveProject() {
		Pack pack = (Pack) packsList.getSelectedValue();
		if (pack != null) savePack(pack);

		JFileChooser chooser = new JFileChooser(lastDir);
		chooser.setDialogTitle("Select your project file");

		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				File file = chooser.getSelectedFile();
				lastDir = file.getParentFile();
				Pack.export(file, packs);
				JOptionPane.showMessageDialog(this, "Save done.");
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Project file cannot be written to.");
			}
		}
	}

	private void browseInput() {
		JFileChooser chooser = new JFileChooser(lastDir);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setFileFilter(new FileFilter() {
			@Override public boolean accept(File f) {return f.isDirectory();}
			@Override public String getDescription() {return "Directories";}
		});

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			lastDir = file;
			inputField.setText(file.getPath());
		}
	}

	private void browseOutput() {
		JFileChooser chooser = new JFileChooser(lastDir);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setFileFilter(new FileFilter() {
			@Override public boolean accept(File f) {return f.isDirectory();}
			@Override public String getDescription() {return "Directories";}
		});

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			lastDir = file;
			outputField.setText(file.getPath());
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

        configPanel = new javax.swing.JPanel();
        projectPanel = new javax.swing.JPanel();
        headerPanel = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        newPackBtn = new javax.swing.JButton();
        renamePackBtn = new javax.swing.JButton();
        deletePackBtn = new javax.swing.JButton();
        moveUpPackBtn = new javax.swing.JButton();
        moveDownPackBtn = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        openProjectBtn = new javax.swing.JButton();
        saveProjectBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        packAllBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        inputField = new javax.swing.JTextField();
        outputField = new javax.swing.JTextField();
        browseOutputBtn = new javax.swing.JButton();
        filenameField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        browseInputBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        packSelectedBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        packsList = new javax.swing.JList();
        commentLabel = new javax.swing.JLabel();
        settingsPanel = new javax.swing.JPanel();
        headerPanel1 = new javax.swing.JPanel();
        jToolBar4 = new javax.swing.JToolBar();
        copySettingsBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        opt_minPageHeight_nud = new javax.swing.JSpinner();
        opt_pot_chk = new javax.swing.JCheckBox();
        opt_duplicatePadding_chk = new javax.swing.JCheckBox();
        opt_alias_chk = new javax.swing.JCheckBox();
        opt_minPageWidth_nud = new javax.swing.JSpinner();
        opt_maxPageWidth_nud = new javax.swing.JSpinner();
        opt_ignoreBlankImages_chk = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        opt_alphaThreashold_nud = new javax.swing.JSpinner();
        opt_debug_chk = new javax.swing.JCheckBox();
        opt_fast_chk = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        opt_stripWhitespaceY_chk = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        opt_rotation_chk = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        opt_edgePadding_chk = new javax.swing.JCheckBox();
        opt_paddingX_nud = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        opt_maxPageHeight_nud = new javax.swing.JSpinner();
        opt_jpegQuality_nud = new javax.swing.JSpinner();
        opt_format_cbox = new javax.swing.JComboBox();
        opt_filterMin_cbox = new javax.swing.JComboBox();
        opt_outputFormat_cbox = new javax.swing.JComboBox();
        opt_filterMag_cbox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        opt_paddingY_nud = new javax.swing.JSpinner();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        opt_wrapX_cbox = new javax.swing.JComboBox();
        opt_wrapY_cbox = new javax.swing.JComboBox();
        opt_stripWhitespaceX_chk = new javax.swing.JCheckBox();
        versionLabel = new aurelienribon.utils.VersionLabel();
        centerPanel = new javax.swing.JPanel();
        renderPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GDX Texture Packer");

        configPanel.setOpaque(false);

        projectPanel.setLayout(new java.awt.BorderLayout());

        headerPanel.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

		//TODO double check
//        newPackBtn.setIcon(new javax.swing.ImageIcon(("/res/gfx/ic_add.png"))); // NOI18N
        newPackBtn.setIcon(new ImageIcon(loadImage("gfx/ic_add.png"))); // NOI18N

        newPackBtn.setText("New pack");
        newPackBtn.setToolTipText("Add a new pack to the list");
        newPackBtn.setFocusable(false);
        newPackBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(newPackBtn);

        renamePackBtn.setIcon(new ImageIcon(loadImage("gfx/ic_edit.png"))); // NOI18N
        renamePackBtn.setToolTipText("Rename the selected pack");
        renamePackBtn.setFocusable(false);
        jToolBar1.add(renamePackBtn);

        deletePackBtn.setIcon(new ImageIcon(loadImage("gfx/ic_delete.png"))); // NOI18N
        deletePackBtn.setToolTipText("Delete the selected pack");
        deletePackBtn.setFocusable(false);
        deletePackBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(deletePackBtn);

        moveUpPackBtn.setIcon(new ImageIcon(loadImage("gfx/ic_up.png"))); // NOI18N
        moveUpPackBtn.setToolTipText("Move the selected pack up in the list");
        moveUpPackBtn.setFocusable(false);
        moveUpPackBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        moveUpPackBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(moveUpPackBtn);

        moveDownPackBtn.setIcon(new ImageIcon(loadImage("gfx/ic_down.png"))); // NOI18N
        moveDownPackBtn.setToolTipText("Move the selected pack down in the list");
        moveDownPackBtn.setFocusable(false);
        moveDownPackBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        moveDownPackBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(moveDownPackBtn);

        headerPanel.add(jToolBar1, java.awt.BorderLayout.CENTER);

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        openProjectBtn.setIcon(new ImageIcon(loadImage("gfx/ic_open.png"))); // NOI18N
        openProjectBtn.setText("Open project");
        openProjectBtn.setToolTipText("");
        openProjectBtn.setFocusable(false);
        openProjectBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        openProjectBtn.setMargin(new java.awt.Insets(2, 3, 2, 3));
        jToolBar2.add(openProjectBtn);

        saveProjectBtn.setIcon(new ImageIcon(loadImage("gfx/ic_save.png"))); // NOI18N
        saveProjectBtn.setText("Save project");
        saveProjectBtn.setFocusable(false);
        saveProjectBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        saveProjectBtn.setMargin(new java.awt.Insets(2, 3, 2, 3));
        jToolBar2.add(saveProjectBtn);

        headerPanel.add(jToolBar2, java.awt.BorderLayout.EAST);

        projectPanel.add(headerPanel, java.awt.BorderLayout.NORTH);

        jPanel2.setOpaque(false);

        packAllBtn.setIcon(new ImageIcon(loadImage("gfx/ic_pack.png"))); // NOI18N
        packAllBtn.setText("Pack'em all");
        packAllBtn.setMargin(new java.awt.Insets(2, 3, 2, 3));

        jPanel1.setOpaque(false);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Output directory:");

        inputField.setColumns(10);

        outputField.setColumns(10);

        browseOutputBtn.setText("...");
        browseOutputBtn.setMargin(new java.awt.Insets(2, 3, 2, 2));
        browseOutputBtn.setOpaque(false);

        filenameField.setColumns(10);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("File name:");

        browseInputBtn.setText("...");
        browseInputBtn.setMargin(new java.awt.Insets(2, 3, 2, 2));
        browseInputBtn.setOpaque(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Input directory:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(outputField, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(inputField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(browseInputBtn)
                            .addComponent(browseOutputBtn)))
                    .addComponent(filenameField)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseInputBtn)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(outputField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseOutputBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(filenameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        packSelectedBtn.setIcon(new ImageIcon(loadImage("gfx/ic_pack.png"))); // NOI18N
        packSelectedBtn.setText("Pack selected");
        packSelectedBtn.setMargin(new java.awt.Insets(2, 3, 2, 3));

        packsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        packsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(packsList);

        commentLabel.setText("Leave blank for \"<packname>.pack\"");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(packAllBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(packSelectedBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(commentLabel)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(commentLabel))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(packSelectedBtn)
                    .addComponent(packAllBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        projectPanel.add(jPanel2, java.awt.BorderLayout.CENTER);

        settingsPanel.setLayout(new java.awt.BorderLayout());

        headerPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar4.setFloatable(false);
        jToolBar4.setRollover(true);

        copySettingsBtn.setIcon(new ImageIcon(loadImage("gfx/ic_copy.png"))); // NOI18N
        copySettingsBtn.setText("Copy settings to all packs");
        copySettingsBtn.setFocusable(false);
        copySettingsBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        copySettingsBtn.setMargin(new java.awt.Insets(2, 3, 2, 3));
        jToolBar4.add(copySettingsBtn);

        headerPanel1.add(jToolBar4, java.awt.BorderLayout.WEST);

        settingsPanel.add(headerPanel1, java.awt.BorderLayout.NORTH);

        jPanel3.setOpaque(false);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Min page height");

        opt_minPageHeight_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(16), Integer.valueOf(0), null, Integer.valueOf(1)));

        opt_pot_chk.setText("Force PoT");
        opt_pot_chk.setOpaque(false);

        opt_duplicatePadding_chk.setText("Duplicate padding");
        opt_duplicatePadding_chk.setOpaque(false);

        opt_alias_chk.setText("Use aliases");
        opt_alias_chk.setOpaque(false);

        opt_minPageWidth_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(16), Integer.valueOf(0), null, Integer.valueOf(1)));

        opt_maxPageWidth_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(512), Integer.valueOf(0), null, Integer.valueOf(1)));

        opt_ignoreBlankImages_chk.setText("Ignore blank imgs");
        opt_ignoreBlankImages_chk.setOpaque(false);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Max page width");

        opt_alphaThreashold_nud.setModel(new javax.swing.SpinnerNumberModel(0, 0, 255, 1));

        opt_debug_chk.setText("Debug");
        opt_debug_chk.setOpaque(false);

        opt_fast_chk.setText("Use fast algorithm");
        opt_fast_chk.setOpaque(false);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Jpeg quality");

        opt_stripWhitespaceY_chk.setText("Strip whitespace Y");
        opt_stripWhitespaceY_chk.setOpaque(false);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("PaddingX");

        opt_rotation_chk.setText("Allow rotations");
        opt_rotation_chk.setOpaque(false);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Min page width");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Alpha threshold");

        opt_edgePadding_chk.setText("Edge padding");
        opt_edgePadding_chk.setOpaque(false);

        opt_paddingX_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(2), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Max page height");

        opt_maxPageHeight_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(512), Integer.valueOf(0), null, Integer.valueOf(1)));

        opt_jpegQuality_nud.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(0.05f)));

        opt_format_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RGBA8888", "RGBA4444", "RGB888", "RGB565", "Alpha", "LuminanceAlpha", "Intensity" }));
        opt_format_cbox.setOpaque(false);

        opt_filterMin_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nearest", "Linear", "MipMap", "MipMapNearestNearest", "MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear" }));
        opt_filterMin_cbox.setOpaque(false);

        opt_outputFormat_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "png", "jpg" }));
        opt_outputFormat_cbox.setOpaque(false);

        opt_filterMag_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nearest", "Linear", "MipMap", "MipMapNearestNearest", "MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear" }));
        opt_filterMag_cbox.setOpaque(false);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Mag filter");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Min filter");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Output format");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Encoding format");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("PaddingY");

        opt_paddingY_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(2), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("WrapX");

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("WrapY");

        opt_wrapX_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ClampToEdge", "Repeat" }));
        opt_wrapX_cbox.setOpaque(false);

        opt_wrapY_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ClampToEdge", "Repeat" }));
        opt_wrapY_cbox.setOpaque(false);

        opt_stripWhitespaceX_chk.setText("Strip whitespace X");
        opt_stripWhitespaceX_chk.setOpaque(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(opt_format_cbox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(opt_outputFormat_cbox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_filterMag_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_filterMin_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(opt_duplicatePadding_chk)
                            .addComponent(opt_edgePadding_chk)
                            .addComponent(opt_fast_chk)
                            .addComponent(opt_rotation_chk)
                            .addComponent(opt_stripWhitespaceY_chk)
                            .addComponent(opt_stripWhitespaceX_chk))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(opt_jpegQuality_nud))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(opt_alphaThreashold_nud, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(opt_ignoreBlankImages_chk)
                                .addComponent(opt_debug_chk)
                                .addComponent(opt_pot_chk)
                                .addComponent(opt_alias_chk))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_minPageHeight_nud, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_maxPageWidth_nud, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_maxPageHeight_nud, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_minPageWidth_nud, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(opt_paddingY_nud, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(opt_paddingX_nud)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel19))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(opt_wrapX_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(opt_wrapY_cbox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {opt_format_cbox, opt_maxPageHeight_nud, opt_maxPageWidth_nud, opt_minPageHeight_nud, opt_minPageWidth_nud, opt_outputFormat_cbox});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {opt_duplicatePadding_chk, opt_edgePadding_chk, opt_fast_chk, opt_rotation_chk, opt_stripWhitespaceY_chk});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {opt_alias_chk, opt_debug_chk, opt_ignoreBlankImages_chk, opt_pot_chk});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel16, jLabel17, jLabel18, jLabel19, jLabel2, jLabel20, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {opt_alphaThreashold_nud, opt_filterMag_cbox, opt_filterMin_cbox, opt_jpegQuality_nud, opt_paddingX_nud, opt_paddingY_nud, opt_wrapX_cbox, opt_wrapY_cbox});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(opt_filterMin_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(opt_filterMag_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(opt_format_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(opt_outputFormat_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(opt_minPageWidth_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(opt_minPageHeight_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(opt_paddingX_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(opt_paddingY_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(opt_maxPageWidth_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(opt_maxPageHeight_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(opt_wrapX_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(opt_wrapY_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(opt_fast_chk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opt_duplicatePadding_chk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opt_edgePadding_chk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opt_stripWhitespaceX_chk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opt_stripWhitespaceY_chk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opt_rotation_chk))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(opt_jpegQuality_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(opt_alphaThreashold_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(opt_ignoreBlankImages_chk, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_debug_chk))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(opt_pot_chk)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(opt_alias_chk)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        settingsPanel.add(jPanel3, java.awt.BorderLayout.CENTER);

        versionLabel.setText("versionLabel1");

        javax.swing.GroupLayout configPanelLayout = new javax.swing.GroupLayout(configPanel);
        configPanel.setLayout(configPanelLayout);
        configPanelLayout.setHorizontalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(projectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(settingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(versionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        configPanelLayout.setVerticalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(projectPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(settingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(versionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(configPanel, java.awt.BorderLayout.WEST);

        centerPanel.setOpaque(false);

        renderPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout centerPanelLayout = new javax.swing.GroupLayout(centerPanel);
        centerPanel.setLayout(centerPanelLayout);
        centerPanelLayout.setHorizontalGroup(
            centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, centerPanelLayout.createSequentialGroup()
                .addComponent(renderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                .addContainerGap())
        );
        centerPanelLayout.setVerticalGroup(
            centerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(renderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseInputBtn;
    private javax.swing.JButton browseOutputBtn;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JLabel commentLabel;
    private javax.swing.JPanel configPanel;
    private javax.swing.JButton copySettingsBtn;
    private javax.swing.JButton deletePackBtn;
    private javax.swing.JTextField filenameField;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel headerPanel1;
    private javax.swing.JTextField inputField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JButton moveDownPackBtn;
    private javax.swing.JButton moveUpPackBtn;
    private javax.swing.JButton newPackBtn;
    private javax.swing.JButton openProjectBtn;
    private javax.swing.JCheckBox opt_alias_chk;
    private javax.swing.JSpinner opt_alphaThreashold_nud;
    private javax.swing.JCheckBox opt_debug_chk;
    private javax.swing.JCheckBox opt_duplicatePadding_chk;
    private javax.swing.JCheckBox opt_edgePadding_chk;
    private javax.swing.JCheckBox opt_fast_chk;
    private javax.swing.JComboBox opt_filterMag_cbox;
    private javax.swing.JComboBox opt_filterMin_cbox;
    private javax.swing.JComboBox opt_format_cbox;
    private javax.swing.JCheckBox opt_ignoreBlankImages_chk;
    private javax.swing.JSpinner opt_jpegQuality_nud;
    private javax.swing.JSpinner opt_maxPageHeight_nud;
    private javax.swing.JSpinner opt_maxPageWidth_nud;
    private javax.swing.JSpinner opt_minPageHeight_nud;
    private javax.swing.JSpinner opt_minPageWidth_nud;
    private javax.swing.JComboBox opt_outputFormat_cbox;
    private javax.swing.JSpinner opt_paddingX_nud;
    private javax.swing.JSpinner opt_paddingY_nud;
    private javax.swing.JCheckBox opt_pot_chk;
    private javax.swing.JCheckBox opt_rotation_chk;
    private javax.swing.JCheckBox opt_stripWhitespaceX_chk;
    private javax.swing.JCheckBox opt_stripWhitespaceY_chk;
    private javax.swing.JComboBox opt_wrapX_cbox;
    private javax.swing.JComboBox opt_wrapY_cbox;
    private javax.swing.JTextField outputField;
    private javax.swing.JButton packAllBtn;
    private javax.swing.JButton packSelectedBtn;
    private javax.swing.JList packsList;
    private javax.swing.JPanel projectPanel;
    private javax.swing.JButton renamePackBtn;
    private javax.swing.JPanel renderPanel;
    private javax.swing.JButton saveProjectBtn;
    private javax.swing.JPanel settingsPanel;
    private aurelienribon.utils.VersionLabel versionLabel;
    // End of variables declaration//GEN-END:variables
}
