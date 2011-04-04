package aurelienribon.texturepackergui;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.imagepacker.TexturePacker.Settings;
import java.awt.Canvas;
import java.io.File;

public class MainWindow extends javax.swing.JFrame {

    public MainWindow() {
        initComponents();
		loadSettings();
		if (AppContext.inputDir != null)
			io_inputDir_field.setText(AppContext.inputDir);
		if (AppContext.outputDir != null)
			io_outputDir_field.setText(AppContext.outputDir);
    }

	public void setCanvas(Canvas canvas) {
		canvas.setSize(canvasPanel.getSize());
		canvasPanel.add(canvas);
	}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        configPanel = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        io_inputDir_field = new javax.swing.JTextField();
        io_outputDir_field = new javax.swing.JTextField();
        io_setInputDir_btn = new javax.swing.JButton();
        io_setOutputDir_btn = new javax.swing.JButton();
        io_pack_btn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
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
        opt_debug_chk = new javax.swing.JCheckBox();
        opt_stripWhitespace_chk = new javax.swing.JCheckBox();
        opt_incremental_chk = new javax.swing.JCheckBox();
        opt_outputPOT_chk = new javax.swing.JCheckBox();
        opt_duplicatePadding_chk = new javax.swing.JCheckBox();
        opt_allowRotations_chk = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        canvasPanel = new javax.swing.JPanel();
        toolPanel = new javax.swing.JPanel();
        tool_previousPage_btn = new javax.swing.JButton();
        tool_nextpage_btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Texture Packer GUI");
        setResizable(false);

        configPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));
        configPanel.setLayout(new javax.swing.BoxLayout(configPanel, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel6.setOpaque(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel12.setText("Input & output");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(156, 156, 156))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        configPanel.add(jPanel6);

        jPanel3.setOpaque(false);

        jLabel1.setText("Input directory:");

        jLabel11.setText("Output directory:");

        io_inputDir_field.setEditable(false);

        io_outputDir_field.setEditable(false);

        io_setInputDir_btn.setText("...");
        io_setInputDir_btn.setMargin(new java.awt.Insets(2, 3, 2, 2));
        io_setInputDir_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                io_setInputDir_btnActionPerformed(evt);
            }
        });

        io_setOutputDir_btn.setText("...");
        io_setOutputDir_btn.setMargin(new java.awt.Insets(2, 3, 2, 2));
        io_setOutputDir_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                io_setOutputDir_btnActionPerformed(evt);
            }
        });

        io_pack_btn.setText("Pack !");
        io_pack_btn.setOpaque(false);
        io_pack_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                io_pack_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(io_pack_btn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(io_outputDir_field, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(io_setOutputDir_btn))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(io_inputDir_field, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
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
                .addComponent(io_pack_btn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        configPanel.add(jPanel3);

        jPanel7.setOpaque(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel13.setText("Options");

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        opt_import_btn.setText("Import");
        opt_import_btn.setMargin(new java.awt.Insets(2, 3, 2, 2));
        opt_import_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opt_import_btnActionPerformed(evt);
            }
        });

        opt_export_btn.setText("Export...");
        opt_export_btn.setMargin(new java.awt.Insets(2, 3, 2, 2));
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
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                        .addComponent(opt_import_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opt_export_btn)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(opt_export_btn)
                    .addComponent(opt_import_btn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        configPanel.add(jPanel7);

        jPanel4.setOpaque(false);

        jLabel2.setText("Default image format:");

        opt_defaultImgFormat_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RGBA8888", "RGBA4444", "RGB888", "RGB565", "LuminanceAlpha", "Alpha" }));
        opt_defaultImgFormat_cbox.setOpaque(false);

        jLabel3.setText("Default min. filter:");

        opt_defaultMinFilter_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nearest", "Linear", "MipMap", "MipMapNearestNearest", "MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear" }));
        opt_defaultMinFilter_cbox.setOpaque(false);

        jLabel4.setText("Default mag. filter:");

        opt_defaultMagFilter_cbox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nearest", "Linear", "MipMap", "MipMapNearestNearest", "MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear" }));
        opt_defaultMagFilter_cbox.setOpaque(false);

        jLabel7.setText("Min. page width:");

        opt_minPageWidth_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(16), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel8.setText("Min. page height:");

        opt_minPageHeight_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(16), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel9.setText("Max. page width:");

        opt_maxPageWidth_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(512), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel10.setText("Max. page height:");

        opt_maxPageHeight_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(512), Integer.valueOf(0), null, Integer.valueOf(1)));

        jLabel6.setText("Padding:");

        opt_padding_nud.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(2), Integer.valueOf(0), null, Integer.valueOf(1)));

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(opt_defaultImgFormat_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(opt_defaultMinFilter_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(opt_defaultMagFilter_cbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addComponent(opt_minPageWidth_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(opt_minPageHeight_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(opt_maxPageWidth_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addComponent(opt_maxPageHeight_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                        .addComponent(opt_padding_nud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
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

        configPanel.add(jPanel4);

        jPanel5.setOpaque(false);

        opt_debug_chk.setText("Debug output");

        opt_stripWhitespace_chk.setText("Strip whitespace");

        opt_incremental_chk.setText("Incremental");

        opt_outputPOT_chk.setText("Output image size is power of two");

        opt_duplicatePadding_chk.setText("Duplicate padding");

        opt_allowRotations_chk.setText("Allow rotations");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(opt_stripWhitespace_chk)
                    .addComponent(opt_debug_chk)
                    .addComponent(opt_incremental_chk)
                    .addComponent(opt_allowRotations_chk)
                    .addComponent(opt_outputPOT_chk)
                    .addComponent(opt_duplicatePadding_chk))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(opt_stripWhitespace_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_allowRotations_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_duplicatePadding_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_outputPOT_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_incremental_chk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(opt_debug_chk)
                .addContainerGap())
        );

        configPanel.add(jPanel5);

        getContentPane().add(configPanel, java.awt.BorderLayout.WEST);

        jPanel1.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout canvasPanelLayout = new javax.swing.GroupLayout(canvasPanel);
        canvasPanel.setLayout(canvasPanelLayout);
        canvasPanelLayout.setHorizontalGroup(
            canvasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
        );
        canvasPanelLayout.setVerticalGroup(
            canvasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
        );

        jPanel1.add(canvasPanel, java.awt.BorderLayout.CENTER);

        toolPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 0, 0)));

        tool_previousPage_btn.setText("<<   Previous page");
        tool_previousPage_btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        tool_previousPage_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tool_previousPage_btnActionPerformed(evt);
            }
        });

        tool_nextpage_btn.setText("Next page   >>");
        tool_nextpage_btn.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        tool_nextpage_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tool_nextpage_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout toolPanelLayout = new javax.swing.GroupLayout(toolPanel);
        toolPanel.setLayout(toolPanelLayout);
        toolPanelLayout.setHorizontalGroup(
            toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tool_previousPage_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
                .addComponent(tool_nextpage_btn)
                .addContainerGap())
        );

        toolPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tool_nextpage_btn, tool_previousPage_btn});

        toolPanelLayout.setVerticalGroup(
            toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tool_previousPage_btn)
                    .addComponent(tool_nextpage_btn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(toolPanel, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void io_setInputDir_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_io_setInputDir_btnActionPerformed
		File selectedDir = UiHelper.showOpenFolderChooser(this, new File("."));
		if (selectedDir != null) {
			io_inputDir_field.setText(selectedDir.getPath());
			AppContext.inputDir = selectedDir.getPath();
		}
	}//GEN-LAST:event_io_setInputDir_btnActionPerformed

	private void io_setOutputDir_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_io_setOutputDir_btnActionPerformed
		File selectedDir = UiHelper.showOpenFolderChooser(this, new File("."));
		if (selectedDir != null) {
			io_outputDir_field.setText(selectedDir.getPath());
			AppContext.outputDir = selectedDir.getPath();
		}
	}//GEN-LAST:event_io_setOutputDir_btnActionPerformed

	private void io_pack_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_io_pack_btnActionPerformed
		saveSettings();
		AppContext.pack();
	}//GEN-LAST:event_io_pack_btnActionPerformed

	private void opt_export_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opt_export_btnActionPerformed
		saveSettings();
		File selectedFile = UiHelper.showSaveFileChooser(this, ".txt", "Text files (.txt)");
		if (selectedFile != null)
			AppContext.exportSettings(selectedFile);
	}//GEN-LAST:event_opt_export_btnActionPerformed

	private void opt_import_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opt_import_btnActionPerformed
		File selectedFile = UiHelper.showOpenFileChooser(this, ".txt", "Text files (.txt)");
		if (selectedFile != null) {
			AppContext.importSettings(selectedFile);
			loadSettings();
		}
	}//GEN-LAST:event_opt_import_btnActionPerformed

	private void tool_previousPage_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tool_previousPage_btnActionPerformed
		AppEvents.fireEventToListeners(AppEvents.PreviousPageRequestedListener.class);
	}//GEN-LAST:event_tool_previousPage_btnActionPerformed

	private void tool_nextpage_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tool_nextpage_btnActionPerformed
		AppEvents.fireEventToListeners(AppEvents.NextPageRequestedListener.class);
	}//GEN-LAST:event_tool_nextpage_btnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel canvasPanel;
    private javax.swing.JPanel configPanel;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
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
    private javax.swing.JPanel toolPanel;
    private javax.swing.JButton tool_nextpage_btn;
    private javax.swing.JButton tool_previousPage_btn;
    // End of variables declaration//GEN-END:variables

	private void loadSettings() {
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
