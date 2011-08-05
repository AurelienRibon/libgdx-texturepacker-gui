package aurelienribon.texturepackergui;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.imagepacker.TexturePacker.Settings;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.Timer;

public class MainWindow extends javax.swing.JFrame {
    public MainWindow(Component canvas) {
        initComponents();

		// Launch a timer to periodically update the console
		final Timer consoleTimer = new Timer(100, new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				ByteArrayOutputStream stream = AppContext.outputStream;
				if (stream.size() > 0) {
					String txt = stream.toString();
					consoleTextArea.append(txt);
					stream.reset();
				}
			}
		});
		consoleTimer.start();

		// Stop the timer on exit
		addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				consoleTimer.stop();
			}
		});

		// Set the render panel to an opengl context
		renderPanel.add(canvas, BorderLayout.CENTER);

		// Init the ui
		loadSettings();
    }

	public void setInputDir(String dir) {
		io_inputDir_field.setText(dir);
	}

	public void setOutputDir(String dir) {
		io_outputDir_field.setText(dir);
		AppEvents.fireEventToListeners(AppEvents.PackDoneListener.class, dir);
	}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        configPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        io_inputDir_field = new javax.swing.JTextField();
        io_outputDir_field = new javax.swing.JTextField();
        io_setInputDir_btn = new javax.swing.JButton();
        io_setOutputDir_btn = new javax.swing.JButton();
        io_pack_btn = new javax.swing.JButton();
        io_deletePack_btn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        opt_import_btn = new javax.swing.JButton();
        opt_export_btn = new javax.swing.JButton();
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
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        opt_alias_chk = new javax.swing.JCheckBox();
        opt_duplicatePadding_chk = new javax.swing.JCheckBox();
        opt_stripWhitespace_chk = new javax.swing.JCheckBox();
        opt_allowRotations_chk = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        opt_outputPOT_chk = new javax.swing.JCheckBox();
        opt_incremental_chk = new javax.swing.JCheckBox();
        opt_debug_chk = new javax.swing.JCheckBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        renderPanel = new javax.swing.JPanel();
        toolsPanel = new javax.swing.JPanel();
        tool_previousPage_btn = new javax.swing.JButton();
        tool_nextpage_btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleTextArea = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Texture Packer GUI");

        jScrollPane2.setBorder(null);

        configPanel.setBackground(Theme.MAIN_BACKGROUND);
        configPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, Theme.SEPARATOR));

        jPanel2.setBackground(Theme.MAIN_BACKGROUND);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/lgdx-logo.png"))); // NOI18N

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/title.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel15)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(Theme.HEADER_BACKGROUND);
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, Theme.SEPARATOR));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel12.setForeground(Theme.HEADER_FOREGROUND);
        jLabel12.setText("Images & output");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(155, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(Theme.MAIN_BACKGROUND);

        jLabel1.setForeground(Theme.MAIN_FOREGROUND);
        jLabel1.setText("Input directory:");

        jLabel11.setForeground(Theme.MAIN_FOREGROUND);
        jLabel11.setText("Output directory:");

        io_setInputDir_btn.setText("...");
        io_setInputDir_btn.setMargin(new java.awt.Insets(2, 3, 2, 2));
        io_setInputDir_btn.setOpaque(false);
        io_setInputDir_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                io_setInputDir_btnActionPerformed(evt);
            }
        });

        io_setOutputDir_btn.setText("...");
        io_setOutputDir_btn.setMargin(new java.awt.Insets(2, 3, 2, 2));
        io_setOutputDir_btn.setOpaque(false);
        io_setOutputDir_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                io_setOutputDir_btnActionPerformed(evt);
            }
        });

        io_pack_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/ic_pack.png"))); // NOI18N
        io_pack_btn.setText("Pack !");
        io_pack_btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        io_pack_btn.setMargin(new java.awt.Insets(2, 3, 2, 3));
        io_pack_btn.setOpaque(false);
        io_pack_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                io_pack_btnActionPerformed(evt);
            }
        });

        io_deletePack_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/ic_delete.png"))); // NOI18N
        io_deletePack_btn.setText("Delete pack");
        io_deletePack_btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        io_deletePack_btn.setMargin(new java.awt.Insets(2, 3, 2, 3));
        io_deletePack_btn.setOpaque(false);
        io_deletePack_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                io_deletePack_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(io_pack_btn, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(io_deletePack_btn))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(io_outputDir_field, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(io_setOutputDir_btn))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(io_inputDir_field, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(io_setInputDir_btn)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(io_inputDir_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(io_setInputDir_btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(io_outputDir_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(io_setOutputDir_btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(io_pack_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(io_deletePack_btn))
                .addContainerGap())
        );

        jPanel7.setBackground(Theme.HEADER_BACKGROUND);
        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, Theme.SEPARATOR));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel13.setForeground(Theme.HEADER_FOREGROUND);
        jLabel13.setText("Options");

        opt_import_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/ic_import.png"))); // NOI18N
        opt_import_btn.setText("Import...");
        opt_import_btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        opt_import_btn.setMargin(new java.awt.Insets(2, 3, 2, 3));
        opt_import_btn.setOpaque(false);
        opt_import_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opt_import_btnActionPerformed(evt);
            }
        });

        opt_export_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aurelienribon/texturepackergui/gfx/ic_export.png"))); // NOI18N
        opt_export_btn.setText("Export...");
        opt_export_btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        opt_export_btn.setMargin(new java.awt.Insets(2, 3, 2, 3));
        opt_export_btn.setOpaque(false);
        opt_export_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opt_export_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(opt_import_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_export_btn)
                .addContainerGap())
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {opt_export_btn, opt_import_btn});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(opt_export_btn)
                    .addComponent(opt_import_btn))
                .addContainerGap())
        );

        jPanel4.setBackground(Theme.MAIN_BACKGROUND);

        jLabel2.setForeground(Theme.MAIN_FOREGROUND);
        jLabel2.setText("Default image format:");

        opt_defaultImgFormat_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RGBA8888", "RGBA4444", "RGB888", "RGB565", "Alpha" }));
        opt_defaultImgFormat_cbox.setOpaque(false);

        jLabel3.setForeground(Theme.MAIN_FOREGROUND);
        jLabel3.setText("Default min. filter:");

        opt_defaultMinFilter_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nearest", "Linear", "MipMap", "MipMapNearestNearest", "MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear" }));
        opt_defaultMinFilter_cbox.setOpaque(false);

        jLabel4.setForeground(Theme.MAIN_FOREGROUND);
        jLabel4.setText("Default mag. filter:");

        opt_defaultMagFilter_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nearest", "Linear", "MipMap", "MipMapNearestNearest", "MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear" }));
        opt_defaultMagFilter_cbox.setOpaque(false);

        jLabel7.setForeground(Theme.MAIN_FOREGROUND);
        jLabel7.setText("Min. page width:");

        opt_minPageWidth_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(16), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel8.setForeground(Theme.MAIN_FOREGROUND);
        jLabel8.setText("Min. page height:");

        opt_minPageHeight_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(16), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel9.setForeground(Theme.MAIN_FOREGROUND);
        jLabel9.setText("Max. page width:");

        opt_maxPageWidth_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(512), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel10.setForeground(Theme.MAIN_FOREGROUND);
        jLabel10.setText("Max. page height:");

        opt_maxPageHeight_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(512), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel6.setForeground(Theme.MAIN_FOREGROUND);
        jLabel6.setText("Padding:");

        opt_padding_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(2), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel5.setForeground(Theme.MAIN_FOREGROUND);
        jLabel5.setText("Alpha threshold:");

        opt_alphaThreashold_nud.setModel(new javax.swing.SpinnerNumberModel(0, 0, 255, 1));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(opt_defaultImgFormat_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(opt_defaultMinFilter_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(opt_defaultMagFilter_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(opt_minPageWidth_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(opt_minPageHeight_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(opt_maxPageWidth_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addComponent(opt_maxPageHeight_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                        .addComponent(opt_padding_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(opt_alphaThreashold_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {opt_alphaThreashold_nud, opt_defaultImgFormat_cbox, opt_defaultMagFilter_cbox, opt_defaultMinFilter_cbox, opt_maxPageHeight_nud, opt_maxPageWidth_nud, opt_minPageHeight_nud, opt_minPageWidth_nud, opt_padding_nud});

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

        jPanel5.setBackground(Theme.MAIN_BACKGROUND);

        jPanel8.setBackground(Theme.MAIN_BACKGROUND);

        opt_alias_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_alias_chk.setText("Use aliases for duplicates");
        opt_alias_chk.setOpaque(false);

        opt_duplicatePadding_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_duplicatePadding_chk.setText("Duplicate padding");
        opt_duplicatePadding_chk.setOpaque(false);

        opt_stripWhitespace_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_stripWhitespace_chk.setText("Strip whitespace");
        opt_stripWhitespace_chk.setOpaque(false);

        opt_allowRotations_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_allowRotations_chk.setText("Allow rotations");
        opt_allowRotations_chk.setOpaque(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(opt_alias_chk)
                    .addComponent(opt_stripWhitespace_chk)
                    .addComponent(opt_allowRotations_chk)
                    .addComponent(opt_duplicatePadding_chk))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(opt_alias_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_stripWhitespace_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_allowRotations_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_duplicatePadding_chk)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(Theme.MAIN_BACKGROUND);

        opt_outputPOT_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_outputPOT_chk.setText("Force PoT output");
        opt_outputPOT_chk.setOpaque(false);

        opt_incremental_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_incremental_chk.setText("Incremental");
        opt_incremental_chk.setOpaque(false);

        opt_debug_chk.setForeground(Theme.MAIN_FOREGROUND);
        opt_debug_chk.setText("Debug output");
        opt_debug_chk.setOpaque(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(opt_incremental_chk)
                    .addComponent(opt_outputPOT_chk)
                    .addComponent(opt_debug_chk))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(opt_outputPOT_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_incremental_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(opt_debug_chk)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout configPanelLayout = new javax.swing.GroupLayout(configPanel);
        configPanel.setLayout(configPanelLayout);
        configPanelLayout.setHorizontalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        configPanelLayout.setVerticalGroup(
            configPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configPanelLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane2.setViewportView(configPanel);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.WEST);

        jSplitPane1.setBorder(null);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(1.0);
        jSplitPane1.setContinuousLayout(true);

        renderPanel.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setLeftComponent(renderPanel);

        toolsPanel.setBackground(Theme.MAIN_BACKGROUND);
        toolsPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.SEPARATOR));

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

        jLabel16.setForeground(Theme.MAIN_FOREGROUND);
        jLabel16.setText("Console (standard output):");

        javax.swing.GroupLayout toolsPanelLayout = new javax.swing.GroupLayout(toolsPanel);
        toolsPanel.setLayout(toolsPanelLayout);
        toolsPanelLayout.setHorizontalGroup(
            toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toolsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                .addComponent(tool_previousPage_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tool_nextpage_btn)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
        );

        toolsPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tool_nextpage_btn, tool_previousPage_btn});

        toolsPanelLayout.setVerticalGroup(
            toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(toolsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tool_nextpage_btn)
                        .addComponent(tool_previousPage_btn))
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(toolsPanel);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void io_setInputDir_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_io_setInputDir_btnActionPerformed
		File startupDir = new File(io_inputDir_field.getText());
		if (!startupDir.isDirectory())
			startupDir = new File(".");

		File selectedDir = UiHelper.showOpenFolderChooser(this, startupDir);
		if (selectedDir != null)
			setInputDir(selectedDir.getPath());
	}//GEN-LAST:event_io_setInputDir_btnActionPerformed

	private void io_setOutputDir_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_io_setOutputDir_btnActionPerformed
		File startupDir = new File(io_outputDir_field.getText());
		if (!startupDir.isDirectory())
			startupDir = new File(".");

		File selectedDir = UiHelper.showOpenFolderChooser(this, startupDir);
		if (selectedDir != null)
			setOutputDir(selectedDir.getPath());
	}//GEN-LAST:event_io_setOutputDir_btnActionPerformed

	private void io_pack_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_io_pack_btnActionPerformed
		saveSettings();
		String inputDir = io_inputDir_field.getText();
		String outputDir = io_outputDir_field.getText();
		try {
			AppContext.pack(inputDir, outputDir);
		} catch (GdxRuntimeException ex) {
			ErrorReport.reportOnUi(this, "Trying to pack the images causes problems...", ex);
		}
	}//GEN-LAST:event_io_pack_btnActionPerformed

	private void opt_export_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opt_export_btnActionPerformed
		saveSettings();
		File selectedFile = UiHelper.showSaveFileChooser(this, ".txt", "Settings files (.txt)");
		if (selectedFile != null) {
			try {
				AppContext.exportSettings(selectedFile);
			} catch (IOException ex) {
				ErrorReport.reportOnUi(this, "", ex);
			}
		}
	}//GEN-LAST:event_opt_export_btnActionPerformed

	private void opt_import_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opt_import_btnActionPerformed
		File selectedFile = UiHelper.showOpenFileChooser(this, ".txt", "Settings files (.txt)");
		if (selectedFile != null) {
			try {
				AppContext.importSettings(selectedFile);
				loadSettings();
			} catch (FileNotFoundException ex) {
			} catch (IOException ex) {
			}
		}
	}//GEN-LAST:event_opt_import_btnActionPerformed

	private void tool_previousPage_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tool_previousPage_btnActionPerformed
		AppEvents.fireEventToListeners(AppEvents.PreviousPageRequestedListener.class);
	}//GEN-LAST:event_tool_previousPage_btnActionPerformed

	private void tool_nextpage_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tool_nextpage_btnActionPerformed
		AppEvents.fireEventToListeners(AppEvents.NextPageRequestedListener.class);
	}//GEN-LAST:event_tool_nextpage_btnActionPerformed

	private void io_deletePack_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_io_deletePack_btnActionPerformed
		File outputDir = new File(io_outputDir_field.getText());
		if (outputDir.exists()) {
			File packFile = new File(outputDir, "pack");
			if (packFile.exists())
				packFile.delete();
			AppEvents.fireEventToListeners(AppEvents.PackDoneListener.class, outputDir.getPath());
		}
	}//GEN-LAST:event_io_deletePack_btnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel configPanel;
    private javax.swing.JTextArea consoleTextArea;
    private javax.swing.JButton io_deletePack_btn;
    private javax.swing.JTextField io_inputDir_field;
    private javax.swing.JTextField io_outputDir_field;
    private javax.swing.JButton io_pack_btn;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JCheckBox opt_alias_chk;
    private javax.swing.JCheckBox opt_allowRotations_chk;
    private javax.swing.JSpinner opt_alphaThreashold_nud;
    private javax.swing.JCheckBox opt_debug_chk;
    private javax.swing.JComboBox opt_defaultImgFormat_cbox;
    private javax.swing.JComboBox opt_defaultMagFilter_cbox;
    private javax.swing.JComboBox opt_defaultMinFilter_cbox;
    private javax.swing.JCheckBox opt_duplicatePadding_chk;
    private javax.swing.JButton opt_export_btn;
    private javax.swing.JButton opt_import_btn;
    private javax.swing.JCheckBox opt_incremental_chk;
    private javax.swing.JSpinner opt_maxPageHeight_nud;
    private javax.swing.JSpinner opt_maxPageWidth_nud;
    private javax.swing.JSpinner opt_minPageHeight_nud;
    private javax.swing.JSpinner opt_minPageWidth_nud;
    private javax.swing.JCheckBox opt_outputPOT_chk;
    private javax.swing.JSpinner opt_padding_nud;
    private javax.swing.JCheckBox opt_stripWhitespace_chk;
    private javax.swing.JPanel renderPanel;
    private javax.swing.JButton tool_nextpage_btn;
    private javax.swing.JButton tool_previousPage_btn;
    private javax.swing.JPanel toolsPanel;
    // End of variables declaration//GEN-END:variables

	private void loadSettings() {
		opt_alias_chk.setSelected(AppContext.settings.alias);
		opt_alphaThreashold_nud.setValue(AppContext.settings.alphaThreshold);
		opt_debug_chk.setSelected(AppContext.settings.debug);
		opt_defaultMagFilter_cbox.setSelectedItem(AppContext.settings.defaultFilterMag.toString());
		opt_defaultMinFilter_cbox.setSelectedItem(AppContext.settings.defaultFilterMin.toString());
		opt_defaultImgFormat_cbox.setSelectedItem(AppContext.settings.defaultFormat.toString());
		opt_duplicatePadding_chk.setSelected(AppContext.settings.duplicatePadding);
		opt_incremental_chk.setSelected(AppContext.settings.incremental);
		opt_maxPageHeight_nud.setValue(AppContext.settings.maxHeight);
		opt_maxPageWidth_nud.setValue(AppContext.settings.maxWidth);
		opt_minPageHeight_nud.setValue(AppContext.settings.minHeight);
		opt_minPageWidth_nud.setValue(AppContext.settings.minWidth);
		opt_padding_nud.setValue(AppContext.settings.padding);
		opt_outputPOT_chk.setSelected(AppContext.settings.pot);
		opt_allowRotations_chk.setSelected(AppContext.settings.rotate);
		opt_stripWhitespace_chk.setSelected(AppContext.settings.stripWhitespace);
	}

	private void saveSettings() {
		AppContext.settings = new Settings();
		AppContext.settings.alias = opt_alias_chk.isSelected();
		AppContext.settings.alphaThreshold = (Integer)opt_alphaThreashold_nud.getValue();
		AppContext.settings.debug = opt_debug_chk.isSelected();
		AppContext.settings.defaultFilterMag = TextureFilter.valueOf((String)opt_defaultMagFilter_cbox.getSelectedItem());
		AppContext.settings.defaultFilterMin = TextureFilter.valueOf((String)opt_defaultMinFilter_cbox.getSelectedItem());
		AppContext.settings.defaultFormat = Format.valueOf((String)opt_defaultImgFormat_cbox.getSelectedItem());
		AppContext.settings.duplicatePadding = opt_duplicatePadding_chk.isSelected();
		AppContext.settings.incremental = opt_incremental_chk.isSelected();
		AppContext.settings.maxHeight = (Integer)opt_maxPageHeight_nud.getValue();
		AppContext.settings.maxWidth = (Integer)opt_maxPageWidth_nud.getValue();
		AppContext.settings.minHeight = (Integer)opt_minPageHeight_nud.getValue();
		AppContext.settings.minWidth = (Integer)opt_minPageWidth_nud.getValue();
		AppContext.settings.padding = (Integer)opt_padding_nud.getValue();
		AppContext.settings.pot = opt_outputPOT_chk.isSelected();
		AppContext.settings.rotate = opt_allowRotations_chk.isSelected();
		AppContext.settings.stripWhitespace = opt_stripWhitespace_chk.isSelected();
	}
}
