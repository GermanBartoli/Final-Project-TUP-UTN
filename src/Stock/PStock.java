/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stock;

import HorariosE.JPHorariosE;
import Modelos.TextPrompt;
import Modelos.ValidacionesCampos;
import Producto.GBDProducto;
import javax.swing.JOptionPane;
import Producto.CProducto;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author German Bartoli
 */
public class PStock extends javax.swing.JPanel {

    private final GBDProducto gestorBDProducto;
    private final GBDStock gestorBDStock;

    private ArrayList<CProducto> listaProductos;
    private ArrayList<CStock> listaStocks;

    private ListSelectionListener lslListaProductos;
    private ListSelectionListener lslTablaStocks;

    final String DMY_FORMAT;
    final String YMD_FORMAT;

    private SimpleDateFormat simpleDateFormat;

    private SimpleDateFormat simpleTimeFormat;

    private boolean banderaAgregarEditar;

    /**
     * Creates new form NewJPanel
     */
    public PStock() {
        initComponents();

        DMY_FORMAT = "dd/MM/yyyy";
        YMD_FORMAT = "yyyy/MM/dd";

        simpleDateFormat = new SimpleDateFormat();

        simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");

        //1.º
        gestorBDProducto = new GBDProducto();
        gestorBDStock = new GBDStock();

        //Iniciar con el 1ro seleccionado
        //Tira error si no hay un item seleccionado
        CrearListSelectionListenerListaProductos();
        CrearListSelectionListenerTablaStock();

        CargarListaProductos();

        ValidacionCampos();

        //No copy/paste
        jTFCantidad.setTransferHandler(null);
        jFTFFechaElaboracion.setTransferHandler(null);
        jFTFFechaVencimiento.setTransferHandler(null);

        TextPrompt placeHolderCantidad = new TextPrompt("0 - 9", jTFCantidad, TextPrompt.Show.ALWAYS);
        TextPrompt placeHolderFechaElaboracion = new TextPrompt("dd/mm/yyyy", jFTFFechaElaboracion, TextPrompt.Show.ALWAYS);
        TextPrompt placeHolderFechaVencimiento = new TextPrompt("dd/mm/yyyy", jFTFFechaVencimiento, TextPrompt.Show.ALWAYS);
    }

    public void CargarListaProductos() {
        //Crear un objeto DefaultListModel
        listaProductos = gestorBDProducto.CargarListaProductos();
        jLProductos.removeListSelectionListener(lslListaProductos);

        DefaultListModel<CProducto> listModel = new DefaultListModel<CProducto>();
        //Recorrer el contenido del ArrayList
        for (int i = 0; i < listaProductos.size(); i++) {
            //Añadir cada elemento del ArrayList en el modelo de la lista
            listModel.add(i, listaProductos.get(i));
        }
        //Asociar el modelo de lista al JList
        jLProductos.setModel(listModel);

        jLProductos.addListSelectionListener(lslListaProductos);

        if (jLProductos.getModel().getSize() > 0) {
            HabilitarDeshabilitarCampos(false);
            jLProductos.setEnabled(true);
            jTStocks.setEnabled(true);

            HabilitarDeshabilitarBotones(true);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);

            jLProductos.setSelectedIndex(0);
        } else {
            HabilitarDeshabilitarCampos(false);

            HabilitarDeshabilitarBotones(false);

            LimpiarCampos();
        }
    }

    private void CrearListSelectionListenerListaProductos() {
        lslListaProductos = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting() && jLProductos.getModel().getSize() > 0) {
                    LimpiarTabla();
                    LimpiarCampos();
                    CargarTablaStocks();
                }
            }
        };
    }

    private void CargarTablaStocks() {

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] nombreDeColumnas = {
            "Lote",
            "Cantidad",
            "Fecha Elaboración",
            "Fecha Vencimiento"
        };
        dtm.setColumnIdentifiers(nombreDeColumnas);

        simpleDateFormat.applyPattern(DMY_FORMAT);

        listaStocks = gestorBDStock.CargarTablaStocks(jLProductos.getSelectedValue().getIdProducto());

        jTStocks.getSelectionModel().removeListSelectionListener(lslTablaStocks);

        for (CStock stock : listaStocks) {
            //dm.setColumnIdentifiers(new String [] {})
            Object[] row = {
                stock.getIdStock(),
                stock.getCantidad(),
                simpleDateFormat.format(stock.getFechaElaboracion()),
                simpleDateFormat.format(stock.getFechaVencimiento())};
            //String.valueOf()
            dtm.addRow(row);
        }
        jTStocks.setModel(dtm);

        jTStocks.getSelectionModel().addListSelectionListener(lslTablaStocks);

        if (jTStocks.getModel().getRowCount() > 0) {
            jTStocks.setRowSelectionInterval(0, 0);
            btnEditar.setEnabled(true);
            btnBorrar.setEnabled(true);
        } else {
            btnEditar.setEnabled(false);
            btnBorrar.setEnabled(false);
        }
    }

    private void CrearListSelectionListenerTablaStock() {
        lslTablaStocks = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting() && jTStocks.getModel().getRowCount() > 0) {
                    CargarStockEnCampos();
                }
            }
        };
    }

    private void CargarStockEnCampos() {
        Double cantidad = (Double) jTStocks.getValueAt(jTStocks.getSelectedRow(), 1);
        jTFCantidad.setText(Double.toString(cantidad));

        String stringFechaElaboracion = (String) jTStocks.getValueAt(jTStocks.getSelectedRow(), 2);
        jFTFFechaElaboracion.setText(stringFechaElaboracion);

        String stringFechaVencimiento = (String) jTStocks.getValueAt(jTStocks.getSelectedRow(), 3);
        jFTFFechaVencimiento.setText(stringFechaVencimiento);
    }

    private void LimpiarCampos() {
        jTFCantidad.setText("");
        jFTFFechaElaboracion.setText("");
        jFTFFechaVencimiento.setText("");
    }

    private void LimpiarTabla() {
        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] nombreDeColumnas = {
            "Lote",
            "Cantidad",
            "Fecha Elaboración",
            "Fecha Vencimiento"
        };
        dtm.setColumnIdentifiers(nombreDeColumnas);

        jTStocks.setModel(dtm);
    }

    private void HabilitarDeshabilitarCampos(boolean x) {
        jTFCantidad.setEnabled(x);
        jFTFFechaElaboracion.setEnabled(x);
        jFTFFechaVencimiento.setEnabled(x);
        jLProductos.setEnabled(x);
        jTStocks.setEnabled(x);
    }

    private void HabilitarDeshabilitarBotones(boolean x) {
        jBAgregar.setEnabled(x);
        btnEditar.setEnabled(x);
        btnBorrar.setEnabled(x);
        btnGuardar.setEnabled(x);
        btnCancelar.setEnabled(x);
    }

    private boolean validar() {
        simpleDateFormat.applyPattern(DMY_FORMAT);

        Date fechaAhora = new Date();
        String strDate = simpleDateFormat.format(fechaAhora);

        Date milNovecientos = new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime();
        String strMilNovecientos = simpleDateFormat.format(milNovecientos);

        Date dosMilDocientos = new GregorianCalendar(2200, Calendar.JANUARY, 1).getTime();
        String strdosMilDocientos = simpleDateFormat.format(dosMilDocientos);

        Date fechaElaboracion = null;
        Date fechaVencimiento = null;

        try {
            fechaAhora = simpleDateFormat.parse(strDate);

            milNovecientos = simpleDateFormat.parse(strMilNovecientos);
            dosMilDocientos = simpleDateFormat.parse(strdosMilDocientos);

            if (jTFCantidad.getText().equals("")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Cantidad Vacía",
                        "Error - Nona Mafalda",
                        JOptionPane.WARNING_MESSAGE);
                jTFCantidad.requestFocus();
                return false;
            }
            
            if (!jFTFFechaElaboracion.getText().equals("")) {
                fechaElaboracion = simpleDateFormat.parse(jFTFFechaElaboracion.getText());
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Fecha de Elaboracion del Stock Vacía",
                        "Error - Nona Mafalda",
                        JOptionPane.WARNING_MESSAGE);
                jFTFFechaElaboracion.requestFocus();
                return false;
            }

            if (!jFTFFechaVencimiento.getText().equals("")) {
                fechaVencimiento = simpleDateFormat.parse(jFTFFechaVencimiento.getText());
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Fecha de Vencimiento del Stock Vacía",
                        "Error - Nona Mafalda",
                        JOptionPane.WARNING_MESSAGE);
                jFTFFechaElaboracion.requestFocus();
                return false;
            }

        } catch (ParseException ex) {
            Logger.getLogger(JPHorariosE.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (jTFCantidad.getText().equals("")
                || Double.parseDouble(jTFCantidad.getText()) > 999) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error en la Cantidad del Stock",
                    "Error - Nona Mafalda",
                    JOptionPane.ERROR_MESSAGE);
            jTFCantidad.requestFocus();
            return false;
        } else {
            if (jFTFFechaElaboracion.getText().equals("")
                    || fechaElaboracion.after(fechaVencimiento)
                    || fechaElaboracion.after(fechaAhora)
                    || fechaElaboracion.before(milNovecientos)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error en la Fecha de Elaboracion del Stock",
                        "Error - Nona Mafalda",
                        JOptionPane.WARNING_MESSAGE);
                jFTFFechaElaboracion.requestFocus();
                return false;
            } else {
                if (jFTFFechaVencimiento.getText().equals("")
                        || fechaVencimiento.before(fechaElaboracion)
                        || fechaVencimiento.before(milNovecientos)
                        || fechaVencimiento.after(dosMilDocientos)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Error en la Fecha de Vencimiento del Stock",
                            "Error - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    jFTFFechaVencimiento.requestFocus();
                    return false;
                }
            }
        }
        return true;
    }

    private void ValidacionCampos() {
        ValidacionesCampos.PesoCantidad(jTFCantidad);
        ValidacionesCampos.Fecha(jFTFFechaElaboracion);
        ValidacionesCampos.Fecha(jFTFFechaVencimiento);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jLProductos = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTStocks = new javax.swing.JTable();
        jBAgregar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jFTFFechaVencimiento = new javax.swing.JFormattedTextField();
        jFTFFechaElaboracion = new javax.swing.JFormattedTextField();
        jLCantidad = new javax.swing.JLabel();
        jLFechaVencimiento = new javax.swing.JLabel();
        jLFechaElaboración = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTFCantidad = new javax.swing.JTextField();

        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1280, 695));
        setLayout(null);

        jLProductos.setBackground(new java.awt.Color(0, 0, 0));
        jLProductos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLProductos.setForeground(new java.awt.Color(255, 255, 255));
        jLProductos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jLProductos.setToolTipText("Listado de Productos");
        jLProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLProductos.setNextFocusableComponent(jTStocks);
        jLProductos.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jLProductos.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jLProductos);

        add(jScrollPane1);
        jScrollPane1.setBounds(950, 10, 320, 620);

        jTStocks.setBackground(new java.awt.Color(0, 0, 0));
        jTStocks.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTStocks.setForeground(new java.awt.Color(255, 255, 255));
        jTStocks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTStocks.setToolTipText("Stocks del Producto Seleccionado");
        jTStocks.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTStocks.setGridColor(new java.awt.Color(102, 102, 102));
        jTStocks.setNextFocusableComponent(jTFCantidad);
        jTStocks.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTStocks.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTStocks.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTStocks.setShowGrid(true);
        jScrollPane2.setViewportView(jTStocks);

        add(jScrollPane2);
        jScrollPane2.setBounds(10, 80, 930, 400);

        jBAgregar.setBackground(new java.awt.Color(0, 0, 0));
        jBAgregar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBAgregar.setForeground(new java.awt.Color(255, 255, 255));
        jBAgregar.setText("Agregar");
        jBAgregar.setToolTipText("Agregar Stock");
        jBAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBAgregar.setNextFocusableComponent(btnEditar);
        jBAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarActionPerformed(evt);
            }
        });
        add(jBAgregar);
        jBAgregar.setBounds(70, 630, 160, 50);

        btnGuardar.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("Guardar Stock");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setNextFocusableComponent(btnCancelar);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        add(btnGuardar);
        btnGuardar.setBounds(520, 630, 170, 50);

        btnEditar.setBackground(new java.awt.Color(0, 0, 0));
        btnEditar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.setToolTipText("Editar Stock Seleccionado");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setNextFocusableComponent(btnBorrar);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        add(btnEditar);
        btnEditar.setBounds(240, 630, 130, 50);

        btnBorrar.setBackground(new java.awt.Color(0, 0, 0));
        btnBorrar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        btnBorrar.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrar.setText("Borrar");
        btnBorrar.setToolTipText("Borrar Stock");
        btnBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrar.setNextFocusableComponent(btnGuardar);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        add(btnBorrar);
        btnBorrar.setBounds(380, 630, 130, 50);

        btnCancelar.setBackground(new java.awt.Color(0, 0, 0));
        btnCancelar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancelar Operación");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setNextFocusableComponent(jLProductos);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        add(btnCancelar);
        btnCancelar.setBounds(700, 630, 180, 50);

        jFTFFechaVencimiento.setBackground(new java.awt.Color(0, 0, 0));
        jFTFFechaVencimiento.setForeground(new java.awt.Color(255, 255, 255));
        jFTFFechaVencimiento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        jFTFFechaVencimiento.setToolTipText("Fecha de Vencimiento del Stock");
        jFTFFechaVencimiento.setCaretColor(new java.awt.Color(255, 255, 255));
        jFTFFechaVencimiento.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jFTFFechaVencimiento.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jFTFFechaVencimiento.setNextFocusableComponent(jBAgregar);
        jFTFFechaVencimiento.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jFTFFechaVencimiento.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jFTFFechaVencimiento);
        jFTFFechaVencimiento.setBounds(680, 550, 230, 60);

        jFTFFechaElaboracion.setBackground(new java.awt.Color(0, 0, 0));
        jFTFFechaElaboracion.setForeground(new java.awt.Color(255, 255, 255));
        jFTFFechaElaboracion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        jFTFFechaElaboracion.setToolTipText("Fecha de Elaboración del Stock");
        jFTFFechaElaboracion.setCaretColor(new java.awt.Color(255, 255, 255));
        jFTFFechaElaboracion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jFTFFechaElaboracion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jFTFFechaElaboracion.setNextFocusableComponent(jFTFFechaVencimiento);
        jFTFFechaElaboracion.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jFTFFechaElaboracion.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jFTFFechaElaboracion);
        jFTFFechaElaboracion.setBounds(360, 550, 230, 60);

        jLCantidad.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLCantidad.setForeground(new java.awt.Color(255, 255, 255));
        jLCantidad.setText("Cantidad");
        add(jLCantidad);
        jLCantidad.setBounds(90, 480, 110, 60);

        jLFechaVencimiento.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLFechaVencimiento.setForeground(new java.awt.Color(255, 255, 255));
        jLFechaVencimiento.setText("Fecha Vencimiento");
        add(jLFechaVencimiento);
        jLFechaVencimiento.setBounds(690, 490, 220, 50);

        jLFechaElaboración.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLFechaElaboración.setForeground(new java.awt.Color(255, 255, 255));
        jLFechaElaboración.setText("Fecha Elaboración");
        add(jLFechaElaboración);
        jLFechaElaboración.setBounds(370, 490, 210, 50);

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Listado Productos");
        add(jLabel4);
        jLabel4.setBounds(960, 640, 300, 42);

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Arial", 0, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Stocks");
        add(jLabel5);
        jLabel5.setBounds(330, 20, 160, 56);

        jTFCantidad.setBackground(new java.awt.Color(0, 0, 0));
        jTFCantidad.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFCantidad.setForeground(new java.awt.Color(255, 255, 255));
        jTFCantidad.setToolTipText("Cantidad del Stock");
        jTFCantidad.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFCantidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFCantidad.setNextFocusableComponent(jFTFFechaElaboracion);
        jTFCantidad.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFCantidad.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFCantidad);
        jTFCantidad.setBounds(30, 550, 230, 60);
    }// </editor-fold>//GEN-END:initComponents

    private void jBAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarActionPerformed
        // TODO add your handling code here:
        if (jLProductos.getModel().getSize() > 0) {
            banderaAgregarEditar = true;
            LimpiarCampos();

            HabilitarDeshabilitarCampos(true);
            jLProductos.setEnabled(false);
            jTStocks.setEnabled(false);

            HabilitarDeshabilitarBotones(false);
            btnGuardar.setEnabled(true);
            btnCancelar.setEnabled(true);

            jTFCantidad.requestFocus();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe cargar primero Productos",
                    "Advertencia - Nona Mafalda",
                    JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_jBAgregarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (validar()) {

            Integer idStock;

            if (banderaAgregarEditar) {
                idStock = -1;
            } else {
                idStock = (Integer) jTStocks.getModel().getValueAt(jTStocks.getSelectedRow(), 0);
            }

            int idProducto = jLProductos.getSelectedValue().getIdProducto();
            double cantidad = Double.parseDouble(jTFCantidad.getText());

            simpleDateFormat.applyPattern(DMY_FORMAT);

            Date fechaElaboracion = null;
            Date fechaVencimiento = null;
            try {

                fechaElaboracion = simpleDateFormat.parse(jFTFFechaElaboracion.getText());
                fechaVencimiento = simpleDateFormat.parse(jFTFFechaVencimiento.getText());

                simpleDateFormat.applyPattern(YMD_FORMAT);

                String stringFechaElaboracion = simpleDateFormat.format(fechaElaboracion);
                String stringFechaVencimiento = simpleDateFormat.format(fechaVencimiento);

                fechaElaboracion = simpleDateFormat.parse(stringFechaElaboracion);
                fechaVencimiento = simpleDateFormat.parse(stringFechaVencimiento);

            } catch (ParseException ex) {
                Logger.getLogger(PStock.class.getName()).log(Level.SEVERE, null, ex);
            }

            CStock stock = new CStock(idStock, idProducto, cantidad, fechaElaboracion, fechaVencimiento, true);

            if (banderaAgregarEditar) {
                if (gestorBDStock.ExisteStockAgregar(stock)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "El Stock ya Existe",
                            "Advertencia - Nona Mafalda",
                            JOptionPane.ERROR_MESSAGE);
                    jTFCantidad.requestFocus();
                    jTFCantidad.setText("");
                    return;
                }
            } else {
                if (gestorBDStock.ExisteStockEditar(stock)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "El Stock ya Existe",
                            "Advertencia - Nona Mafalda",
                            JOptionPane.ERROR_MESSAGE);
                    jTFCantidad.requestFocus();
                    jTFCantidad.setText("");
                    return;
                }
            }

            boolean exito = false;

            if (idStock == -1) {
                exito = gestorBDStock.AgregarStock(stock);
            } else {
                try {
                    exito = gestorBDStock.ModificarStock(stock);
                } catch (SQLException ex) {
                    Logger.getLogger(PStock.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (exito) {
                int productSelected = jLProductos.getSelectedIndex();
                int tableRowSelected = jTStocks.getSelectedRow();

                CargarListaProductos();

                jLProductos.setSelectedIndex(productSelected);

                int tableLastRow = jTStocks.getRowCount() - 1;

                if (idStock == -1) {
                    int ultimoIDStock = gestorBDStock.ObtenerUltimoIDStock();

                    for (int i = 0; i < jTStocks.getModel().getRowCount(); i++) {
                        if (ultimoIDStock == (Integer) jTStocks.getModel().getValueAt(i, 0)) {
                            jTStocks.setRowSelectionInterval(i, i);
                        }
                    }
                } else {
                    jTStocks.getSelectionModel().removeListSelectionListener(lslTablaStocks);
                    jTStocks.removeRowSelectionInterval(tableRowSelected, tableRowSelected);
                    jTStocks.getSelectionModel().addListSelectionListener(lslTablaStocks);

                    jTStocks.setRowSelectionInterval(tableRowSelected, tableRowSelected);
                }

                JOptionPane.showMessageDialog(
                        this,
                        "El Stock se guardó Correctamente",
                        "- Nona Mafalda",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        if (jTStocks.getModel().getRowCount() > 0) {
            banderaAgregarEditar = false;

            HabilitarDeshabilitarCampos(true);
            jLProductos.setEnabled(false);
            jTStocks.setEnabled(false);

            HabilitarDeshabilitarBotones(false);
            btnGuardar.setEnabled(true);
            btnCancelar.setEnabled(true);

            jTFCantidad.requestFocus();
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        if (jTStocks.getModel().getRowCount() > 0) {
            if (JOptionPane.showConfirmDialog(
                    this,
                    "Esta seguro?",
                    "Eliminando Stock - Nona Mafalda",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                // yes option
                int idStock = (int) jTStocks.getValueAt(jTStocks.getSelectedRow(), 0);

                boolean exito = false;
                try {
                    exito = gestorBDStock.EliminarStock(idStock);
                } catch (SQLException ex) {
                    Logger.getLogger(PStock.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (exito) {
                    int productSelected = jLProductos.getSelectedIndex();
                    CargarListaProductos();

                    if ((jTStocks.getModel().getRowCount() > 0)) {
                        jLProductos.setSelectedIndex(productSelected);
                    }
                    JOptionPane.showMessageDialog(
                            this,
                            "El Producto se eliminó correctamente.",
                            "Exito - Nona Mafalda",
                            JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Intentelo Nuevamente.",
                            "Error al Eliminar el Producto - Nona Mafalda",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // no option
            }
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        HabilitarDeshabilitarCampos(false);
        jLProductos.setEnabled(true);
        jTStocks.setEnabled(true);

        HabilitarDeshabilitarBotones(true);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);

        int listaFilaSeleccionada = jLProductos.getSelectedIndex();
        int tablaFilaSeleccionada = jTStocks.getSelectedRow();

        if (jLProductos.getModel().getSize() > 0) {
            jLProductos.setSelectedIndex(listaFilaSeleccionada);
            if (jTStocks.getModel().getRowCount() > 0) {

                jTStocks.getSelectionModel().removeListSelectionListener(lslTablaStocks);
                jTStocks.removeRowSelectionInterval(tablaFilaSeleccionada, tablaFilaSeleccionada);
                jTStocks.getSelectionModel().addListSelectionListener(lslTablaStocks);
                jTStocks.setRowSelectionInterval(tablaFilaSeleccionada, tablaFilaSeleccionada);

            } else {
                btnBorrar.setEnabled(false);
                btnEditar.setEnabled(false);

                LimpiarCampos();
            }
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton jBAgregar;
    private javax.swing.JFormattedTextField jFTFFechaElaboracion;
    private javax.swing.JFormattedTextField jFTFFechaVencimiento;
    private javax.swing.JLabel jLCantidad;
    private javax.swing.JLabel jLFechaElaboración;
    private javax.swing.JLabel jLFechaVencimiento;
    private javax.swing.JList<Producto.CProducto> jLProductos;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTFCantidad;
    private javax.swing.JTable jTStocks;
    // End of variables declaration//GEN-END:variables
}
