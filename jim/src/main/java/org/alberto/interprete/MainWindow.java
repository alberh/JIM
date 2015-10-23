/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.alberto.interprete;

import org.alberto.interprete.gui.Consola;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author alber_000
 */
public class MainWindow extends javax.swing.JFrame {

    private final JFileChooser _fc = new JFileChooser();
    private File _ficheroAbierto = null;
    private final File _ficheroTemporal = new File("jim.tmp");
    private boolean _hayCambios = false;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();

        Consola.inicializar(taSalida, taSalida);
        System.setOut(Consola.estandar());
        System.setErr(Consola.errores());

        Configuracion.cargar();
        MainWindow.bienvenida();
    }

    public static void bienvenida() {
        System.out.println("--------------------------------------------------------");
        System.out.println("JIM " + Configuracion.version());
        System.out.println("Intérprete de modelos de computación L, LOOP y WHILE");
        System.out.println("--------------------------------------------------------");
        System.out.println("Directorio actual: " + System.getProperty("user.dir"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barraHerramientas = new javax.swing.JToolBar();
        btnNuevoFichero = new javax.swing.JButton();
        btnAbrirFichero = new javax.swing.JButton();
        btnGuardarFichero = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        comboModelos = new javax.swing.JComboBox();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 0), new java.awt.Dimension(12, 32767));
        jLabel2 = new javax.swing.JLabel();
        tfEntradaPrograma = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnIniciarEjecucion = new javax.swing.JButton();
        btnPararEjecucion = new javax.swing.JButton();
        btnIniciarDebug = new javax.swing.JButton();
        btnSiguienteInstruccion = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        btnExpandirMacros = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        taEditor = new javax.swing.JTextArea();
        tabPanelSalida = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        taSalida = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        taTraza = new javax.swing.JTextArea();
        barraMenu = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuArchivoNuevo = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuArchivoAbrir = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuArchivoGuardar = new javax.swing.JMenuItem();
        menuArchivoGuardarComo = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuArchivoSalir = new javax.swing.JMenuItem();
        menuEditar = new javax.swing.JMenu();
        menuPrograma = new javax.swing.JMenu();
        menuProgramaIniciar = new javax.swing.JMenuItem();
        menuProgramaDetener = new javax.swing.JMenuItem();
        menuProgramaIniciarDebug = new javax.swing.JMenuItem();
        menuProgramaSiguienteInstruccion = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        menuProgramaExpandirMacros = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        menuProgramaPermitirMacros = new javax.swing.JCheckBoxMenuItem();
        menuProgramaPermitirInstruccionesExtra = new javax.swing.JCheckBoxMenuItem();
        menuAyuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("JIM 0.1");
        setMinimumSize(new java.awt.Dimension(640, 480));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        barraHerramientas.setFloatable(false);
        barraHerramientas.setRollover(true);

        btnNuevoFichero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/new_file.png"))); // NOI18N
        btnNuevoFichero.setToolTipText("Nuevo fichero");
        btnNuevoFichero.setFocusable(false);
        btnNuevoFichero.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevoFichero.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevoFichero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoFicheroActionPerformed(evt);
            }
        });
        barraHerramientas.add(btnNuevoFichero);

        btnAbrirFichero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/open_file.png"))); // NOI18N
        btnAbrirFichero.setToolTipText("Abrir fichero");
        btnAbrirFichero.setFocusable(false);
        btnAbrirFichero.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAbrirFichero.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAbrirFichero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirFicheroActionPerformed(evt);
            }
        });
        barraHerramientas.add(btnAbrirFichero);

        btnGuardarFichero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/save_file.png"))); // NOI18N
        btnGuardarFichero.setToolTipText("Guardar fichero");
        btnGuardarFichero.setEnabled(false);
        btnGuardarFichero.setFocusable(false);
        btnGuardarFichero.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardarFichero.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardarFichero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarFicheroActionPerformed(evt);
            }
        });
        barraHerramientas.add(btnGuardarFichero);
        barraHerramientas.add(jSeparator1);

        jLabel1.setText("Modelo:  ");
        barraHerramientas.add(jLabel1);

        comboModelos.setMaximumRowCount(3);
        comboModelos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "L", "Loop", "While" }));
        comboModelos.setToolTipText("");
        comboModelos.setMaximumSize(new java.awt.Dimension(100, 20));
        comboModelos.setMinimumSize(new java.awt.Dimension(80, 20));
        comboModelos.setName(""); // NOI18N
        comboModelos.setPreferredSize(new java.awt.Dimension(20, 20));
        barraHerramientas.add(comboModelos);
        barraHerramientas.add(filler1);

        jLabel2.setText("Entrada:  ");
        barraHerramientas.add(jLabel2);

        tfEntradaPrograma.setMaximumSize(new java.awt.Dimension(100, 2147483647));
        tfEntradaPrograma.setMinimumSize(new java.awt.Dimension(80, 20));
        barraHerramientas.add(tfEntradaPrograma);
        barraHerramientas.add(jSeparator5);

        btnIniciarEjecucion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/play.png"))); // NOI18N
        btnIniciarEjecucion.setToolTipText("Iniciar programa");
        btnIniciarEjecucion.setFocusable(false);
        btnIniciarEjecucion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIniciarEjecucion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnIniciarEjecucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarEjecucionActionPerformed(evt);
            }
        });
        barraHerramientas.add(btnIniciarEjecucion);

        btnPararEjecucion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/stop.png"))); // NOI18N
        btnPararEjecucion.setToolTipText("Detener programa");
        btnPararEjecucion.setEnabled(false);
        btnPararEjecucion.setFocusable(false);
        btnPararEjecucion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPararEjecucion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramientas.add(btnPararEjecucion);

        btnIniciarDebug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/debug.png"))); // NOI18N
        btnIniciarDebug.setToolTipText("Iniciar paso a paso");
        btnIniciarDebug.setFocusable(false);
        btnIniciarDebug.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIniciarDebug.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramientas.add(btnIniciarDebug);

        btnSiguienteInstruccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/next.png"))); // NOI18N
        btnSiguienteInstruccion.setToolTipText("Siguiente instrucción");
        btnSiguienteInstruccion.setEnabled(false);
        btnSiguienteInstruccion.setFocusable(false);
        btnSiguienteInstruccion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSiguienteInstruccion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramientas.add(btnSiguienteInstruccion);
        barraHerramientas.add(jSeparator8);

        btnExpandirMacros.setText("Expandir macros");
        btnExpandirMacros.setFocusable(false);
        btnExpandirMacros.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExpandirMacros.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExpandirMacros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpandirMacrosActionPerformed(evt);
            }
        });
        barraHerramientas.add(btnExpandirMacros);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.7);

        taEditor.setColumns(20);
        taEditor.setRows(5);
        taEditor.setText("\tZ <- producto(x1, x2)\n\tIF Z != 0 GOTO A\n\tY <- suma(x1, x2)\n\tGOTO E\n[A]\tY <- Z\n\t");
        taEditor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                taEditorKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(taEditor);

        jSplitPane1.setLeftComponent(jScrollPane1);

        taSalida.setEditable(false);
        taSalida.setColumns(20);
        taSalida.setRows(5);
        taSalida.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        taSalida.setMaximumSize(new java.awt.Dimension(2147483647, 96));
        jScrollPane2.setViewportView(taSalida);

        tabPanelSalida.addTab("Salida", jScrollPane2);

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        taTraza.setEditable(false);
        taTraza.setColumns(20);
        taTraza.setLineWrap(true);
        taTraza.setRows(5);
        jScrollPane4.setViewportView(taTraza);

        tabPanelSalida.addTab("Traza", jScrollPane4);

        jSplitPane1.setRightComponent(tabPanelSalida);

        menuArchivo.setText("Archivo");

        menuArchivoNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/new_file.png"))); // NOI18N
        menuArchivoNuevo.setText("Nuevo");
        menuArchivoNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuArchivoNuevoActionPerformed(evt);
            }
        });
        menuArchivo.add(menuArchivoNuevo);
        menuArchivo.add(jSeparator3);

        menuArchivoAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/open_file.png"))); // NOI18N
        menuArchivoAbrir.setText("Abrir");
        menuArchivoAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuArchivoAbrirActionPerformed(evt);
            }
        });
        menuArchivo.add(menuArchivoAbrir);
        menuArchivo.add(jSeparator4);

        menuArchivoGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/save_file.png"))); // NOI18N
        menuArchivoGuardar.setText("Guardar");
        menuArchivoGuardar.setEnabled(false);
        menuArchivoGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuArchivoGuardarActionPerformed(evt);
            }
        });
        menuArchivo.add(menuArchivoGuardar);

        menuArchivoGuardarComo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/save_file.png"))); // NOI18N
        menuArchivoGuardarComo.setText("Guardar como");
        menuArchivoGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuArchivoGuardarComoActionPerformed(evt);
            }
        });
        menuArchivo.add(menuArchivoGuardarComo);
        menuArchivo.add(jSeparator2);

        menuArchivoSalir.setText("Salir");
        menuArchivoSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuArchivoSalirActionPerformed(evt);
            }
        });
        menuArchivo.add(menuArchivoSalir);

        barraMenu.add(menuArchivo);

        menuEditar.setText("Editar");
        barraMenu.add(menuEditar);

        menuPrograma.setText("Programa");

        menuProgramaIniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/play.png"))); // NOI18N
        menuProgramaIniciar.setText("Iniciar");
        menuProgramaIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProgramaIniciarActionPerformed(evt);
            }
        });
        menuPrograma.add(menuProgramaIniciar);

        menuProgramaDetener.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/stop.png"))); // NOI18N
        menuProgramaDetener.setText("Detener");
        menuPrograma.add(menuProgramaDetener);

        menuProgramaIniciarDebug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/debug.png"))); // NOI18N
        menuProgramaIniciarDebug.setText("Iniciar paso a paso");
        menuPrograma.add(menuProgramaIniciarDebug);

        menuProgramaSiguienteInstruccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/next.png"))); // NOI18N
        menuProgramaSiguienteInstruccion.setText("Siguiente instrucción");
        menuPrograma.add(menuProgramaSiguienteInstruccion);
        menuPrograma.add(jSeparator6);

        menuProgramaExpandirMacros.setText("Expandir macros");
        menuProgramaExpandirMacros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProgramaExpandirMacrosActionPerformed(evt);
            }
        });
        menuPrograma.add(menuProgramaExpandirMacros);
        menuPrograma.add(jSeparator7);

        menuProgramaPermitirMacros.setSelected(true);
        menuProgramaPermitirMacros.setText("Permitir macros");
        menuPrograma.add(menuProgramaPermitirMacros);

        menuProgramaPermitirInstruccionesExtra.setSelected(true);
        menuProgramaPermitirInstruccionesExtra.setText("Permitir instrucciones extra");
        menuPrograma.add(menuProgramaPermitirInstruccionesExtra);

        barraMenu.add(menuPrograma);

        menuAyuda.setText("Ayuda");
        barraMenu.add(menuAyuda);

        setJMenuBar(barraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(barraHerramientas, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(barraHerramientas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuArchivoNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuArchivoNuevoActionPerformed
        nuevoFichero();
    }//GEN-LAST:event_menuArchivoNuevoActionPerformed

    private void menuArchivoGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuArchivoGuardarActionPerformed
        guardarFichero();
    }//GEN-LAST:event_menuArchivoGuardarActionPerformed

    private void btnAbrirFicheroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirFicheroActionPerformed
        abrirFichero();
    }//GEN-LAST:event_btnAbrirFicheroActionPerformed

    private void menuArchivoAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuArchivoAbrirActionPerformed
        abrirFichero();
    }//GEN-LAST:event_menuArchivoAbrirActionPerformed

    private void btnGuardarFicheroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarFicheroActionPerformed
        guardarFichero();
    }//GEN-LAST:event_btnGuardarFicheroActionPerformed

    private void menuArchivoSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuArchivoSalirActionPerformed
        salir();
    }//GEN-LAST:event_menuArchivoSalirActionPerformed

    private void btnNuevoFicheroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoFicheroActionPerformed
        nuevoFichero();
    }//GEN-LAST:event_btnNuevoFicheroActionPerformed

    private void taEditorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taEditorKeyPressed
        hayCambios();
    }//GEN-LAST:event_taEditorKeyPressed

    private void menuArchivoGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuArchivoGuardarComoActionPerformed
        guardarFicheroComo();
    }//GEN-LAST:event_menuArchivoGuardarComoActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        salir();
    }//GEN-LAST:event_formWindowClosing

    private void btnIniciarEjecucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarEjecucionActionPerformed
        iniciarPrograma();
    }//GEN-LAST:event_btnIniciarEjecucionActionPerformed

    private void menuProgramaIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProgramaIniciarActionPerformed
        iniciarPrograma();
    }//GEN-LAST:event_menuProgramaIniciarActionPerformed

    private void btnExpandirMacrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpandirMacrosActionPerformed
        iniciarExpansionMacros();
    }//GEN-LAST:event_btnExpandirMacrosActionPerformed

    private void menuProgramaExpandirMacrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProgramaExpandirMacrosActionPerformed
        iniciarExpansionMacros();
    }//GEN-LAST:event_menuProgramaExpandirMacrosActionPerformed

    private void comprobacionesPreviasAEjecucion() {
        taSalida.setText("");
        taTraza.setText("");

        // comprobar que el programa esté guardado en un fichero
        // si no, crear un fichero temporal "jim.tmp"
        if (_ficheroAbierto == null) {
            _ficheroAbierto = _ficheroTemporal;
            guardarFichero();
        }

        // si hay alguna modificación en el programa, se guarda
        if (_hayCambios) {
            guardarFichero();
        }
    }

    private void moverCursorAlFinal() {
        int posicionFinal = taSalida.getDocument().getLength();
        taSalida.setCaretPosition(posicionFinal);
    }

    private void iniciarPrograma() {
        comprobacionesPreviasAEjecucion();

        if (Programa.cargar(_ficheroAbierto.getAbsolutePath(), obtenerModelo())) {
            int[] parametros = null;
            if (!tfEntradaPrograma.getText().isEmpty()) {
                String[] parametrosComoCadenas = tfEntradaPrograma.getText().split(" ");
                int tam = parametrosComoCadenas.length;
                parametros = new int[tam];

                for (int i = 0; i < tam; ++i) {
                    try {
                        parametros[i] = Integer.parseInt(parametrosComoCadenas[i]);
                    } catch (NumberFormatException ex) {
                        parametros[i] = 0;
                    }
                }
            }

            Programa.iniciar(parametros);

            if (Programa.estadoOk()) {
                System.out.println();
                System.out.println("Resultado: " + Programa.resultado());
                taTraza.setText(Programa.traza());
            } else {
                tabPanelSalida.setSelectedIndex(0);
            }

            moverCursorAlFinal();
        }
    }

    private void iniciarExpansionMacros() {
        comprobacionesPreviasAEjecucion();

        if (Programa.cargar(_ficheroAbierto.getAbsolutePath(), obtenerModelo())) {
            Programa.iniciarExpansionMacros();

            taEditor.setText(Programa.obtenerPrograma());
            hayCambios();

            moverCursorAlFinal();
        }
        
        if (!Programa.estadoOk()) {
            tabPanelSalida.setSelectedIndex(0);
        }
    }

    private void hayCambios() {
        hayCambios(true);
    }

    private void hayCambios(boolean c) {
        _hayCambios = c;

        btnGuardarFichero.setEnabled(c);
        menuArchivoGuardar.setEnabled(c);
    }

    private Programa.Modelos obtenerModelo() {
        switch (comboModelos.getSelectedIndex()) {
            case 0: // L
                return Programa.Modelos.L;

            case 1: // Loop
                return Programa.Modelos.LOOP;

            default: // While
                return Programa.Modelos.WHILE;
        }
    }

    private String titulo() {
        return "JIM " + Configuracion.version();
    }

    private String tituloYFichero() {
        if (_ficheroAbierto != null) {
            return titulo() + " - " + _ficheroAbierto.getName();
        } else {
            return titulo();
        }
    }

    private void salir() {
        if (_hayCambios) {
            int n = JOptionPane.showConfirmDialog(this,
                    "¿Desea guardar el fichero actual antes de salir?",
                    "Advertencia",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (n == JOptionPane.YES_OPTION) {
                guardarFichero();
            } else if (n == JOptionPane.NO_OPTION) {
                // 
            } else {
                return;
            }
        }

        if (_ficheroTemporal.exists()) {
            _ficheroTemporal.delete();
        }

        System.exit(0);
    }

    private void abrirFichero() {
        if (_ficheroAbierto != null && _hayCambios) {
            int n = JOptionPane.showConfirmDialog(this,
                    "¿Desea guardar el fichero actual antes de cerrarlo?",
                    "Advertencia",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (n == JOptionPane.YES_OPTION) {
                guardarFichero();
            } else if (n == JOptionPane.NO_OPTION) {
                hayCambios(false);
            } else {
                return;
            }
        }

        int returnVal = _fc.showOpenDialog(MainWindow.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            _ficheroAbierto = _fc.getSelectedFile();

            taEditor.setText("");

            this.setTitle(titulo() + " - " + _ficheroAbierto.getName());

            try (BufferedReader in = new BufferedReader(new FileReader(_ficheroAbierto))) {
                String line = in.readLine();
                while (line != null) {
                    taEditor.append(line + "\n");
                    line = in.readLine();
                }

                taEditor.setCaretPosition(0);
                taEditor.requestFocus();

            } catch (Exception ex) {
                Error.alCargarProgramaGUI(_ficheroAbierto.getAbsolutePath());
            }
        }
    }

    private void guardarFichero() {
        if (_ficheroAbierto != null) {
            try (BufferedWriter out = new BufferedWriter(new FileWriter(_ficheroAbierto))) {
                out.write(taEditor.getText());
                hayCambios(false);
            } catch (Exception ex) {
                if (_ficheroAbierto != _ficheroTemporal) {
                    Error.alGuardarFichero(_ficheroAbierto.getAbsolutePath());
                } else {
                    Error.alGuardarFicheroTemporal();
                }
            }
        } else {
            guardarFicheroComo();
        }
    }

    private void guardarFicheroComo() {
        int returnVal = _fc.showSaveDialog(MainWindow.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            _ficheroAbierto = _fc.getSelectedFile();
            guardarFichero();

            this.setTitle(tituloYFichero());
        }
    }

    private void nuevoFichero() {
        if (_ficheroAbierto != null) {
            if (_hayCambios) {
                int n = JOptionPane.showConfirmDialog(this,
                        "¿Desea guardar el fichero actual antes de cerrarlo?",
                        "Advertencia",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (n == JOptionPane.YES_OPTION) {
                    guardarFichero();
                } else if (n == JOptionPane.NO_OPTION) {
                    hayCambios(false);
                } else {
                    return;
                }
            }
            _ficheroAbierto = null;
        }

        this.setTitle(titulo());
        taEditor.setText("");
        taEditor.requestFocus();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        /*
         try {
         for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
         if ("Nimbus".equals(info.getName())) {
         javax.swing.UIManager.setLookAndFeel(info.getClassName());
         break;
         }
         }
         } catch (ClassNotFoundException ex) {
         java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
         }
         //</editor-fold>
         */

        /*
         // Para cargar el Look & Feel del sistema
         try {
         javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
         } catch (Exception ex) {}
         */
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barraHerramientas;
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JButton btnAbrirFichero;
    private javax.swing.JButton btnExpandirMacros;
    private javax.swing.JButton btnGuardarFichero;
    private javax.swing.JButton btnIniciarDebug;
    private javax.swing.JButton btnIniciarEjecucion;
    private javax.swing.JButton btnNuevoFichero;
    private javax.swing.JButton btnPararEjecucion;
    private javax.swing.JButton btnSiguienteInstruccion;
    private javax.swing.JComboBox comboModelos;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuItem menuArchivoAbrir;
    private javax.swing.JMenuItem menuArchivoGuardar;
    private javax.swing.JMenuItem menuArchivoGuardarComo;
    private javax.swing.JMenuItem menuArchivoNuevo;
    private javax.swing.JMenuItem menuArchivoSalir;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuEditar;
    private javax.swing.JMenu menuPrograma;
    private javax.swing.JMenuItem menuProgramaDetener;
    private javax.swing.JMenuItem menuProgramaExpandirMacros;
    private javax.swing.JMenuItem menuProgramaIniciar;
    private javax.swing.JMenuItem menuProgramaIniciarDebug;
    private javax.swing.JCheckBoxMenuItem menuProgramaPermitirInstruccionesExtra;
    private javax.swing.JCheckBoxMenuItem menuProgramaPermitirMacros;
    private javax.swing.JMenuItem menuProgramaSiguienteInstruccion;
    private javax.swing.JTextArea taEditor;
    private javax.swing.JTextArea taSalida;
    private javax.swing.JTextArea taTraza;
    private javax.swing.JTabbedPane tabPanelSalida;
    private javax.swing.JTextField tfEntradaPrograma;
    // End of variables declaration//GEN-END:variables
}
