package aurelienribon.texturepackergui;

import aurelienribon.texturepackergui.utils.ErrorReport;
import aurelienribon.texturepackergui.utils.UiHelper;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import org.apache.commons.io.FileUtils;

public class MainWindow extends javax.swing.JFrame {
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private final Project project = new Project();

    public MainWindow(Component canvas) {
		PrintStream ps = new PrintStream(outputStream);
		System.setOut(ps);
		System.setErr(ps);
		System.out.println("libgdx-texturepacker-gui | 2011");
		System.out.println("Welcome!\n");

        initComponents();
		renderPanel.add(canvas, BorderLayout.CENTER);

		final Timer stdOutTimer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ByteArrayOutputStream stream = outputStream;
				if (stream.size() > 0) {
					String txt = stream.toString();
					consoleTextArea.append(txt);
					stream.reset();
				}
			}
		});
		stdOutTimer.start();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				AppEvents.fireEventToListeners(AppEvents.PackDoneListener.class, project.getOutput());
			}

			@Override
			public void windowClosing(WindowEvent e) {
				stdOutTimer.stop();
			}
		});


		BasicSplitPaneDivider dividerContainer = (BasicSplitPaneDivider) jSplitPane1.getComponent(0);
		dividerContainer.setBackground(Theme.MAIN_BACKGROUND);
		dividerContainer.setBorder(null);
    }

	public void setProject(Project prj) {
		project.setPath(prj.getPath());
		project.setInput(prj.getInput());
		project.setOutput(prj.getOutput());
		project.setSettings(prj.getSettings());

		io_inputDir_field.setText(prj.getInput());
		io_outputDir_field.setText(prj.getOutput());
		loadSettings();
		AppEvents.fireEventToListeners(AppEvents.PackDoneListener.class, prj.getOutput());
	}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        configPanel = new javax.swing.JPanel();
        titlePanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        generalPanel = new javax.swing.JPanel();
        titlePanel1 = new aurelienribon.utils.ui.TitlePanel();
        jLabel17 = new javax.swing.JLabel();
        generalInnerPanel = new javax.swing.JPanel();
        io_setInputDir_btn = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        io_inputDir_field = new javax.swing.JTextField();
        io_setOutputDir_btn = new javax.swing.JButton();
        io_outputDir_field = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        io_loadPrj_btn = new javax.swing.JButton();
        io_savePrj_btn = new javax.swing.JButton();
        packlPanel = new javax.swing.JPanel();
        titlePanel3 = new aurelienribon.utils.ui.TitlePanel();
        jLabel19 = new javax.swing.JLabel();
        packInnerPanel = new javax.swing.JPanel();
        io_pack_btn = new javax.swing.JButton();
        io_deletePack_btn = new javax.swing.JButton();
        settingsPanel = new javax.swing.JPanel();
        titlePanel2 = new aurelienribon.utils.ui.TitlePanel();
        jLabel18 = new javax.swing.JLabel();
        settingsInnerPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        opt_defaultImgFormat_cbox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        opt_defaultMinFilter_cbox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        opt_defaultMagFilter_cbox = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        opt_minPageWidth_nud = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        opt_minPageHeight_nud = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        opt_maxPageWidth_nud = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        opt_maxPageHeight_nud = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        opt_padding_nud = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        opt_alphaThreashold_nud = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        opt_duplicatePadding_chk = new javax.swing.JCheckBox();
        opt_stripWhitespace_chk = new javax.swing.JCheckBox();
        opt_allowRotations_chk = new javax.swing.JCheckBox();
        opt_edgePadding_chk = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        opt_outputPOT_chk = new javax.swing.JCheckBox();
        opt_incremental_chk = new javax.swing.JCheckBox();
        opt_debug_chk = new javax.swing.JCheckBox();
        opt_alias_chk = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        renderPanelWrapper = new javax.swing.JPanel();
        renderPanel = new javax.swing.JPanel();
        toolsPanel = new javax.swing.JPanel();
        tool_previousPage_btn = new javax.swing.JButton();
        tool_nextpage_btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleTextArea = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Texture Packer GUI");

        configPanel.setBackground(Theme.MAIN_BACKGROUND);

        titlePanel.setBackground(Theme.MAIN_BACKGROUND);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/lgdx-logo.png"))); // NOI18N

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/title.png"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/texture.png"))); // NOI18N

        jLabel16.setForeground(Theme.MAIN_FOREGROUND);
        jLabel16.setText("v2.3");

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(titlePanelLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(115, 115, 115)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addContainerGap())
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(titlePanelLayout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel15))
                        .addComponent(jLabel13))
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        generalPanel.setOpaque(false);
        generalPanel.setLayout(new java.awt.BorderLayout());

        titlePanel1.setBackground(Theme.MAIN_ALT_BACKGROUND);

        jLabel17.setBackground(Theme.HEADER_FOREGROUND);
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("General");
        jLabel17.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 0, 0));

        javax.swing.GroupLayout titlePanel1Layout = new javax.swing.GroupLayout(titlePanel1);
        titlePanel1.setLayout(titlePanel1Layout);
        titlePanel1Layout.setHorizontalGroup(
            titlePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addContainerGap(227, Short.MAX_VALUE))
        );
        titlePanel1Layout.setVerticalGroup(
            titlePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17)
        );

        generalPanel.add(titlePanel1, java.awt.BorderLayout.NORTH);

        generalInnerPanel.setBackground(Theme.MAIN_ALT_BACKGROUND);

        io_setInputDir_btn.setText("...");
        io_setInputDir_btn.setMargin(new java.awt.Insets(2, 3, 2, 2));
        io_setInputDir_btn.setOpaque(false);
        io_setInputDir_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                io_setInputDir_btnActionPerformed(evt);
            }
        });

        jLabel11.setForeground(Theme.MAIN_FOREGROUND);
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Output directory:");

        jLabel1.setForeground(Theme.MAIN_FOREGROUND);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Input directory:");

        io_inputDir_field.setBackground(Theme.TEXTAREA_BACKGROUND);
        io_inputDir_field.setForeground(Theme.TEXTAREA_FOREGROUND);

        io_setOutputDir_btn.setText("...");
        io_setOutputDir_btn.setMargin(new java.awt.Insets(2, 3, 2, 2));
        io_setOutputDir_btn.setOpaque(false);
        io_setOutputDir_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                io_setOutputDir_btnActionPerformed(evt);
            }
        });

        io_outputDir_field.setBackground(Theme.TEXTAREA_BACKGROUND);
        io_outputDir_field.setForeground(Theme.TEXTAREA_FOREGROUND);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        io_loadPrj_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/ic_load.png"))); // NOI18N
        io_loadPrj_btn.setText("Load project");
        io_loadPrj_btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        io_loadPrj_btn.setMargin(new java.awt.Insets(2, 3, 2, 3));
        io_loadPrj_btn.setOpaque(false);
        io_loadPrj_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                io_loadPrj_btnActionPerformed(evt);
            }
        });
        jPanel3.add(io_loadPrj_btn);

        io_savePrj_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/ic_save.png"))); // NOI18N
        io_savePrj_btn.setText("Save project");
        io_savePrj_btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        io_savePrj_btn.setMargin(new java.awt.Insets(2, 3, 2, 3));
        io_savePrj_btn.setOpaque(false);
        io_savePrj_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                io_savePrj_btnActionPerformed(evt);
            }
        });
        jPanel3.add(io_savePrj_btn);

        javax.swing.GroupLayout generalInnerPanelLayout = new javax.swing.GroupLayout(generalInnerPanel);
        generalInnerPanel.setLayout(generalInnerPanelLayout);
        generalInnerPanelLayout.setHorizontalGroup(
            generalInnerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalInnerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(generalInnerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                    .addGroup(generalInnerPanelLayout.createSequentialGroup()
                        .addGroup(generalInnerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(generalInnerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(io_outputDir_field, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(io_inputDir_field, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(generalInnerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(io_setInputDir_btn)
                            .addComponent(io_setOutputDir_btn))))
                .addContainerGap())
        );
        generalInnerPanelLayout.setVerticalGroup(
            generalInnerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalInnerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(generalInnerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(io_inputDir_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(io_setInputDir_btn)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(generalInnerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(io_outputDir_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(io_setOutputDir_btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        generalPanel.add(generalInnerPanel, java.awt.BorderLayout.CENTER);

        packlPanel.setOpaque(false);
        packlPanel.setLayout(new java.awt.BorderLayout());

        titlePanel3.setBackground(Theme.MAIN_ALT_BACKGROUND);

        jLabel19.setBackground(Theme.HEADER_FOREGROUND);
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Pack");
        jLabel19.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 0, 0));

        javax.swing.GroupLayout titlePanel3Layout = new javax.swing.GroupLayout(titlePanel3);
        titlePanel3.setLayout(titlePanel3Layout);
        titlePanel3Layout.setHorizontalGroup(
            titlePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addContainerGap(244, Short.MAX_VALUE))
        );
        titlePanel3Layout.setVerticalGroup(
            titlePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19)
        );

        packlPanel.add(titlePanel3, java.awt.BorderLayout.NORTH);

        packInnerPanel.setBackground(Theme.MAIN_ALT_BACKGROUND);

        io_pack_btn.setFont(new java.awt.Font("Tahoma", 0, 18));
        io_pack_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/ic_pack.png"))); // NOI18N
        io_pack_btn.setText("Pack !");
        io_pack_btn.setMargin(new java.awt.Insets(2, 3, 2, 3));
        io_pack_btn.setOpaque(false);
        io_pack_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                io_pack_btnActionPerformed(evt);
            }
        });

        io_deletePack_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/ic_delete.png"))); // NOI18N
        io_deletePack_btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        io_deletePack_btn.setMargin(new java.awt.Insets(2, 3, 2, 3));
        io_deletePack_btn.setOpaque(false);
        io_deletePack_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                io_deletePack_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout packInnerPanelLayout = new javax.swing.GroupLayout(packInnerPanel);
        packInnerPanel.setLayout(packInnerPanelLayout);
        packInnerPanelLayout.setHorizontalGroup(
            packInnerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(packInnerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(io_deletePack_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(io_pack_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addContainerGap())
        );
        packInnerPanelLayout.setVerticalGroup(
            packInnerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, packInnerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(packInnerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(io_pack_btn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(io_deletePack_btn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addContainerGap())
        );

        packlPanel.add(packInnerPanel, java.awt.BorderLayout.CENTER);

        settingsPanel.setOpaque(false);
        settingsPanel.setLayout(new java.awt.BorderLayout());

        titlePanel2.setBackground(Theme.MAIN_ALT_BACKGROUND);

        jLabel18.setBackground(Theme.HEADER_FOREGROUND);
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Settings");
        jLabel18.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 0, 0));

        javax.swing.GroupLayout titlePanel2Layout = new javax.swing.GroupLayout(titlePanel2);
        titlePanel2.setLayout(titlePanel2Layout);
        titlePanel2Layout.setHorizontalGroup(
            titlePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addContainerGap(220, Short.MAX_VALUE))
        );
        titlePanel2Layout.setVerticalGroup(
            titlePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18)
        );

        settingsPanel.add(titlePanel2, java.awt.BorderLayout.NORTH);

        settingsInnerPanel.setBackground(Theme.MAIN_ALT_BACKGROUND);
        settingsInnerPanel.setLayout(new javax.swing.BoxLayout(settingsInnerPanel, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel4.setOpaque(false);

        jLabel2.setForeground(Theme.MAIN_FOREGROUND);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Default image format:");

        opt_defaultImgFormat_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RGBA8888", "RGBA4444", "RGB888", "RGB565", "Alpha" }));
        opt_defaultImgFormat_cbox.setOpaque(false);

        jLabel3.setForeground(Theme.MAIN_FOREGROUND);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Default min. filter:");

        opt_defaultMinFilter_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nearest", "Linear", "MipMap", "MipMapNearestNearest", "MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear" }));
        opt_defaultMinFilter_cbox.setOpaque(false);

        jLabel4.setForeground(Theme.MAIN_FOREGROUND);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Default mag. filter:");

        opt_defaultMagFilter_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nearest", "Linear", "MipMap", "MipMapNearestNearest", "MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear" }));
        opt_defaultMagFilter_cbox.setOpaque(false);

        jLabel7.setForeground(Theme.MAIN_FOREGROUND);
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Min. page width:");

        opt_minPageWidth_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(16), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel8.setForeground(Theme.MAIN_FOREGROUND);
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Min. page height:");

        opt_minPageHeight_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(16), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel9.setForeground(Theme.MAIN_FOREGROUND);
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Max. page width:");

        opt_maxPageWidth_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(512), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel10.setForeground(Theme.MAIN_FOREGROUND);
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Max. page height:");

        opt_maxPageHeight_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(512), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel6.setForeground(Theme.MAIN_FOREGROUND);
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Padding:");

        opt_padding_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(2), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel5.setForeground(Theme.MAIN_FOREGROUND);
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Alpha threshold:");

        opt_alphaThreashold_nud.setModel(new javax.swing.SpinnerNumberModel(0, 0, 255, 1));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(opt_alphaThreashold_nud, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(opt_padding_nud, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(opt_maxPageHeight_nud, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(opt_maxPageWidth_nud, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(opt_minPageHeight_nud, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(opt_minPageWidth_nud, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(opt_defaultMagFilter_cbox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 153, Short.MAX_VALUE)
                    .addComponent(opt_defaultMinFilter_cbox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 153, Short.MAX_VALUE)
                    .addComponent(opt_defaultImgFormat_cbox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 153, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(opt_defaultImgFormat_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(opt_defaultMinFilter_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(opt_defaultMagFilter_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(opt_minPageWidth_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(opt_minPageHeight_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(opt_maxPageWidth_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(opt_maxPageHeight_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(opt_padding_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(opt_alphaThreashold_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        settingsInnerPanel.add(jPanel4);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.SEPARATOR));
        jPanel8.setOpaque(false);

        opt_duplicatePadding_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_duplicatePadding_chk.setText("Duplicate padding");
        opt_duplicatePadding_chk.setOpaque(false);

        opt_stripWhitespace_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_stripWhitespace_chk.setText("Strip whitespace");
        opt_stripWhitespace_chk.setOpaque(false);

        opt_allowRotations_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_allowRotations_chk.setText("Allow rotations");
        opt_allowRotations_chk.setOpaque(false);

        opt_edgePadding_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_edgePadding_chk.setText("Edge padding");
        opt_edgePadding_chk.setOpaque(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(opt_stripWhitespace_chk)
                    .addComponent(opt_allowRotations_chk)
                    .addComponent(opt_duplicatePadding_chk)
                    .addComponent(opt_edgePadding_chk))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(opt_stripWhitespace_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_allowRotations_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_duplicatePadding_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_edgePadding_chk)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel8, java.awt.BorderLayout.WEST);

        jPanel9.setOpaque(false);

        opt_outputPOT_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_outputPOT_chk.setText("Force PoT output");
        opt_outputPOT_chk.setOpaque(false);

        opt_incremental_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_incremental_chk.setText("Incremental");
        opt_incremental_chk.setOpaque(false);

        opt_debug_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_debug_chk.setText("Debug output");
        opt_debug_chk.setOpaque(false);

        opt_alias_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_alias_chk.setText("Use aliases for duplicates");
        opt_alias_chk.setOpaque(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(opt_outputPOT_chk)
                    .addComponent(opt_alias_chk)
                    .addComponent(opt_incremental_chk)
                    .addComponent(opt_debug_chk))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(opt_outputPOT_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_alias_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_incremental_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_debug_chk)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel9, java.awt.BorderLayout.CENTER);

        settingsInnerPanel.add(jPanel1);

        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(282, 999999999));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 384, Short.MAX_VALUE)
        );

        settingsInnerPanel.add(jPanel2);

        settingsPanel.add(settingsInnerPanel, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout configPanelLayout = new javax.swing.GroupLayout(configPanel);
        configPanel.setLayout(configPanelLayout);
        configPanelLayout.setHorizontalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(generalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(configPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(packlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(configPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addContainerGap())
        );
        configPanelLayout.setVerticalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(packlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(settingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(configPanel, java.awt.BorderLayout.WEST);

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerSize(3);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(1.0);
        jSplitPane1.setContinuousLayout(true);

        renderPanelWrapper.setBackground(Theme.MAIN_BACKGROUND);

        renderPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout renderPanelWrapperLayout = new javax.swing.GroupLayout(renderPanelWrapper);
        renderPanelWrapper.setLayout(renderPanelWrapperLayout);
        renderPanelWrapperLayout.setHorizontalGroup(
            renderPanelWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 331, Short.MAX_VALUE)
            .addGroup(renderPanelWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(renderPanelWrapperLayout.createSequentialGroup()
                    .addComponent(renderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        renderPanelWrapperLayout.setVerticalGroup(
            renderPanelWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 177, Short.MAX_VALUE)
            .addGroup(renderPanelWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(renderPanelWrapperLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(renderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
        );

        jSplitPane1.setLeftComponent(renderPanelWrapper);

        toolsPanel.setBackground(Theme.MAIN_BACKGROUND);

        tool_previousPage_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/ic_previous.png"))); // NOI18N
        tool_previousPage_btn.setText("Previous page");
        tool_previousPage_btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tool_previousPage_btn.setMargin(new java.awt.Insets(2, 3, 2, 3));
        tool_previousPage_btn.setOpaque(false);
        tool_previousPage_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tool_previousPage_btnActionPerformed(evt);
            }
        });

        tool_nextpage_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/ic_next.png"))); // NOI18N
        tool_nextpage_btn.setText("Next page");
        tool_nextpage_btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tool_nextpage_btn.setMargin(new java.awt.Insets(2, 3, 2, 3));
        tool_nextpage_btn.setOpaque(false);
        tool_nextpage_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tool_nextpage_btnActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.SEPARATOR));
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        consoleTextArea.setBackground(Theme.CONSOLE_BACKGROUND);
        consoleTextArea.setColumns(20);
        consoleTextArea.setEditable(false);
        consoleTextArea.setForeground(Theme.CONSOLE_FOREGROUND);
        consoleTextArea.setRows(5);
        jScrollPane1.setViewportView(consoleTextArea);

        jLabel12.setForeground(Theme.MAIN_FOREGROUND);
        jLabel12.setText("<html>\n<b>Tip:</b> drag to pan, scroll to zoom");

        javax.swing.GroupLayout toolsPanelLayout = new javax.swing.GroupLayout(toolsPanel);
        toolsPanel.setLayout(toolsPanelLayout);
        toolsPanelLayout.setHorizontalGroup(
            toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toolsPanelLayout.createSequentialGroup()
                .addGroup(toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                    .addGroup(toolsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 272, Short.MAX_VALUE)
                        .addComponent(tool_previousPage_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tool_nextpage_btn)))
                .addContainerGap())
        );

        toolsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tool_nextpage_btn, tool_previousPage_btn});

        toolsPanelLayout.setVerticalGroup(
            toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolsPanelLayout.createSequentialGroup()
                .addGroup(toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tool_nextpage_btn)
                        .addComponent(tool_previousPage_btn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(toolsPanel);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void io_setInputDir_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_io_setInputDir_btnActionPerformed
		File startupDir = new File(project.getInput());
		if (!startupDir.isDirectory()) startupDir = new File(".");

		File selectedDir = UiHelper.showOpenFolderChooser(this, startupDir);
		if (selectedDir != null) {
			project.setInput(selectedDir.getPath());
			io_inputDir_field.setText(project.getInput());
		}
	}//GEN-LAST:event_io_setInputDir_btnActionPerformed

	private void io_setOutputDir_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_io_setOutputDir_btnActionPerformed
		File startupDir = new File(project.getOutput());
		if (!startupDir.isDirectory()) startupDir = new File(".");

		File selectedDir = UiHelper.showOpenFolderChooser(this, startupDir);
		if (selectedDir != null) {
			project.setOutput(selectedDir.getPath());
			io_outputDir_field.setText(project.getOutput());
			AppEvents.fireEventToListeners(AppEvents.PackDoneListener.class, project.getOutput());
		}
	}//GEN-LAST:event_io_setOutputDir_btnActionPerformed

	private void io_pack_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_io_pack_btnActionPerformed
		saveSettings();
		try {
			project.pack();
			AppEvents.fireEventToListeners(AppEvents.PackDoneListener.class, project.getOutput());
		} catch (GdxRuntimeException ex) {
			ErrorReport.reportOnUi(this, "Packing unsuccessful...", ex);
		}
	}//GEN-LAST:event_io_pack_btnActionPerformed

	private void tool_previousPage_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tool_previousPage_btnActionPerformed
		AppEvents.fireEventToListeners(AppEvents.PreviousPageRequestedListener.class);
	}//GEN-LAST:event_tool_previousPage_btnActionPerformed

	private void tool_nextpage_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tool_nextpage_btnActionPerformed
		AppEvents.fireEventToListeners(AppEvents.NextPageRequestedListener.class);
	}//GEN-LAST:event_tool_nextpage_btnActionPerformed

	private void io_deletePack_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_io_deletePack_btnActionPerformed
		File outputDir = new File(project.getOutput());
		if (outputDir.exists()) {
			File packFile = new File(outputDir, "pack");
			if (packFile.exists()) packFile.delete();
			AppEvents.fireEventToListeners(AppEvents.PackDoneListener.class, project.getOutput());
		}
	}//GEN-LAST:event_io_deletePack_btnActionPerformed

	private void io_loadPrj_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_io_loadPrj_btnActionPerformed
		File selectedFile = UiHelper.showOpenFileChooser(this, ".prj", "Project files (.prj)");
		if (selectedFile != null) {
			try {
				setProject(Project.load(selectedFile.getPath()));
			} catch (IOException ex) {
				ErrorReport.reportOnUi(this, null, ex);
			}
		}
	}//GEN-LAST:event_io_loadPrj_btnActionPerformed

	private void io_savePrj_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_io_savePrj_btnActionPerformed
		File selectedFile = UiHelper.showSaveFileChooser(this, ".prj", "Project files (.prj)");
		if (selectedFile != null) {
			try {
				saveSettings();
				project.setPath(selectedFile.getPath());
				FileUtils.writeStringToFile(selectedFile, project.save());
			} catch (IOException ex) {
				ErrorReport.reportOnUi(this, null, ex);
			}
		}
	}//GEN-LAST:event_io_savePrj_btnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel configPanel;
    private javax.swing.JTextArea consoleTextArea;
    private javax.swing.JPanel generalInnerPanel;
    private javax.swing.JPanel generalPanel;
    private javax.swing.JButton io_deletePack_btn;
    private javax.swing.JTextField io_inputDir_field;
    private javax.swing.JButton io_loadPrj_btn;
    private javax.swing.JTextField io_outputDir_field;
    private javax.swing.JButton io_pack_btn;
    private javax.swing.JButton io_savePrj_btn;
    private javax.swing.JButton io_setInputDir_btn;
    private javax.swing.JButton io_setOutputDir_btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JCheckBox opt_alias_chk;
    private javax.swing.JCheckBox opt_allowRotations_chk;
    private javax.swing.JSpinner opt_alphaThreashold_nud;
    private javax.swing.JCheckBox opt_debug_chk;
    private javax.swing.JComboBox opt_defaultImgFormat_cbox;
    private javax.swing.JComboBox opt_defaultMagFilter_cbox;
    private javax.swing.JComboBox opt_defaultMinFilter_cbox;
    private javax.swing.JCheckBox opt_duplicatePadding_chk;
    private javax.swing.JCheckBox opt_edgePadding_chk;
    private javax.swing.JCheckBox opt_incremental_chk;
    private javax.swing.JSpinner opt_maxPageHeight_nud;
    private javax.swing.JSpinner opt_maxPageWidth_nud;
    private javax.swing.JSpinner opt_minPageHeight_nud;
    private javax.swing.JSpinner opt_minPageWidth_nud;
    private javax.swing.JCheckBox opt_outputPOT_chk;
    private javax.swing.JSpinner opt_padding_nud;
    private javax.swing.JCheckBox opt_stripWhitespace_chk;
    private javax.swing.JPanel packInnerPanel;
    private javax.swing.JPanel packlPanel;
    private javax.swing.JPanel renderPanel;
    private javax.swing.JPanel renderPanelWrapper;
    private javax.swing.JPanel settingsInnerPanel;
    private javax.swing.JPanel settingsPanel;
    private javax.swing.JPanel titlePanel;
    private aurelienribon.utils.ui.TitlePanel titlePanel1;
    private aurelienribon.utils.ui.TitlePanel titlePanel2;
    private aurelienribon.utils.ui.TitlePanel titlePanel3;
    private javax.swing.JButton tool_nextpage_btn;
    private javax.swing.JButton tool_previousPage_btn;
    private javax.swing.JPanel toolsPanel;
    // End of variables declaration//GEN-END:variables

	private void loadSettings() {
		opt_alias_chk.setSelected(project.getSettings().alias);
		opt_alphaThreashold_nud.setValue(project.getSettings().alphaThreshold);
		opt_debug_chk.setSelected(project.getSettings().debug);
		opt_defaultMagFilter_cbox.setSelectedItem(project.getSettings().defaultFilterMag.toString());
		opt_defaultMinFilter_cbox.setSelectedItem(project.getSettings().defaultFilterMin.toString());
		opt_defaultImgFormat_cbox.setSelectedItem(project.getSettings().defaultFormat.toString());
		opt_duplicatePadding_chk.setSelected(project.getSettings().duplicatePadding);
		opt_incremental_chk.setSelected(project.getSettings().incremental);
		opt_maxPageHeight_nud.setValue(project.getSettings().maxHeight);
		opt_maxPageWidth_nud.setValue(project.getSettings().maxWidth);
		opt_minPageHeight_nud.setValue(project.getSettings().minHeight);
		opt_minPageWidth_nud.setValue(project.getSettings().minWidth);
		opt_padding_nud.setValue(project.getSettings().padding);
		opt_outputPOT_chk.setSelected(project.getSettings().pot);
		opt_allowRotations_chk.setSelected(project.getSettings().rotate);
		opt_stripWhitespace_chk.setSelected(project.getSettings().stripWhitespace);
		opt_edgePadding_chk.setSelected(project.getSettings().edgePadding);
	}

	private void saveSettings() {
		project.getSettings().alias = opt_alias_chk.isSelected();
		project.getSettings().alphaThreshold = (Integer)opt_alphaThreashold_nud.getValue();
		project.getSettings().debug = opt_debug_chk.isSelected();
		project.getSettings().defaultFilterMag = TextureFilter.valueOf((String)opt_defaultMagFilter_cbox.getSelectedItem());
		project.getSettings().defaultFilterMin = TextureFilter.valueOf((String)opt_defaultMinFilter_cbox.getSelectedItem());
		project.getSettings().defaultFormat = Format.valueOf((String)opt_defaultImgFormat_cbox.getSelectedItem());
		project.getSettings().duplicatePadding = opt_duplicatePadding_chk.isSelected();
		project.getSettings().incremental = opt_incremental_chk.isSelected();
		project.getSettings().maxHeight = (Integer)opt_maxPageHeight_nud.getValue();
		project.getSettings().maxWidth = (Integer)opt_maxPageWidth_nud.getValue();
		project.getSettings().minHeight = (Integer)opt_minPageHeight_nud.getValue();
		project.getSettings().minWidth = (Integer)opt_minPageWidth_nud.getValue();
		project.getSettings().padding = (Integer)opt_padding_nud.getValue();
		project.getSettings().pot = opt_outputPOT_chk.isSelected();
		project.getSettings().rotate = opt_allowRotations_chk.isSelected();
		project.getSettings().stripWhitespace = opt_stripWhitespace_chk.isSelected();
		project.getSettings().edgePadding = opt_edgePadding_chk.isSelected();
	}
}
