/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Consultas;

import DTO.DTO;
import HorariosE.JPHorariosE;
import Modelos.TextPrompt;
import Modelos.ValidacionesCampos;
import Stock.PStock;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author German Bartoli
 */
public class JPConsultas extends javax.swing.JPanel {

    GBDConsultas gBDConsultas;
    ArrayList<DTO> dTOProducto;

    final String DMY_FORMAT;
    //final String DMY_HMS_FORMAT;
    final String YMD_FORMAT;

    private SimpleDateFormat simpleDateFormat;

    private Date ahora;

    /**
     * Creates new form JPConsultas
     */
    public JPConsultas() {
        initComponents();

        gBDConsultas = new GBDConsultas();

        DMY_FORMAT = "dd/MM/yyyy";
        YMD_FORMAT = "yyyy/MM/dd";

        simpleDateFormat = new SimpleDateFormat();

        simpleDateFormat.applyPattern(DMY_FORMAT);

        ahora = new Date();
        String strDate = simpleDateFormat.format(ahora);

        jFTFFechaInicio.setText(strDate);
        jFTFFechaFin.setText(strDate);

        ValidacionCampos();

        LimpiarLabels();
        CargarTablaDTOProductosSinStock();
        jLTitulo.setText("Productos Sin Stock");

        HabilitarDeshabilidarBotones(true);
        jBProductosSinStock.setEnabled(false);

        //No copy/paste
        jFTFFechaInicio.setTransferHandler(null);
        jFTFFechaFin.setTransferHandler(null);

        TextPrompt placeHolderFechaElaboracion = new TextPrompt("dd/mm/yyyy", jFTFFechaInicio, TextPrompt.Show.ALWAYS);
        TextPrompt placeHolderFechaVencimiento = new TextPrompt("dd/mm/yyyy", jFTFFechaFin, TextPrompt.Show.ALWAYS);
    }

    private void CargarTablaDTOProductosSinStock() {
        dTOProducto = gBDConsultas.CargarTablaDTOProductosSinStock();

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] nombreDeColumnas = {
            "Producto",
            "Marca",
            "Tipo Producto",
            "Stock Mínimo"
        };
        dtm.setColumnIdentifiers(nombreDeColumnas);

        for (DTO producto : dTOProducto) {
            Object[] row = {producto.getNombreProducto(),
                producto.getMarca(),
                producto.getTipoProducto(),
                producto.getStockMinimo()
            };
            dtm.addRow(row);
        }
        jTConsultas.setModel(dtm);

        int sum = 0;
        for (int i = 0; i < jTConsultas.getRowCount(); i++) {
            sum = sum + 1;
        }
        jLabelProductosSinStock.setText("Productos Sin Stock: " + Integer.toString(sum));
    }

    private void CargarTablaDTOProductosStockVencidos() {
        dTOProducto = gBDConsultas.CargarTablaDTOProductosStockVencidos();

        DefaultTableModel dtm;
        dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] nombreDeColumnas = {
            "Producto",
            "Marca",
            "Tipo Producto",
            "Cantidad",
            "Fecha Elaboración",
            "Fecha Vencimiento",
            "Vencido hace 'x' días"
        };
        dtm.setColumnIdentifiers(nombreDeColumnas);

        simpleDateFormat.applyPattern(DMY_FORMAT);

        for (DTO producto : dTOProducto) {
            Object[] row = {
                producto.getNombreProducto(),
                producto.getMarca(),
                producto.getTipoProducto(),
                producto.getCantidadStock(),
                simpleDateFormat.format(producto.getFechaElaboracion()),
                simpleDateFormat.format(producto.getFechaVencimiento()),
                producto.getDiasVencido()
            };
            dtm.addRow(row);
        }
        jTConsultas.setModel(dtm);

        double sum1 = 0;
        double sum2 = 0;
        for (int i = 0; i < jTConsultas.getRowCount(); i++) {
            sum1 = sum1 + 1;
            sum2 = sum2 + Double.parseDouble(jTConsultas.getValueAt(i, 3).toString());
        }
        jLabelProductosStockVencido.setText("Stocks Vencidos: " + Double.toString(sum1));
    }

    private void CargarTablaCantidadTotalVendidaXProducto() {
        simpleDateFormat.applyPattern(DMY_FORMAT);

        Date fechaInicio = null;
        Date fechaFin = null;
        try {

            fechaInicio = simpleDateFormat.parse(jFTFFechaInicio.getText());
            fechaFin = simpleDateFormat.parse(jFTFFechaFin.getText());

            simpleDateFormat.applyPattern(YMD_FORMAT);

            String stringfechaInicio = simpleDateFormat.format(fechaInicio);
            String stringfechaFin = simpleDateFormat.format(fechaFin);

            fechaInicio = simpleDateFormat.parse(stringfechaInicio);
            fechaFin = simpleDateFormat.parse(stringfechaFin);

        } catch (ParseException ex) {
            Logger.getLogger(PStock.class.getName()).log(Level.SEVERE, null, ex);
        }

        dTOProducto = gBDConsultas.CantidadTotalVendidaXProducto(fechaInicio, fechaFin);

        DefaultTableModel dtm;
        dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] nombreDeColumnas = {
            "Producto",
            "Cantidad Vendida del Producto",
            "Total Recaudado del Producto"
        };
        dtm.setColumnIdentifiers(nombreDeColumnas);

        simpleDateFormat.applyPattern(DMY_FORMAT);

        for (DTO dto : dTOProducto) {
            Object[] row = {
                dto.getNombreProducto(),
                dto.getSUMCantidad_Detalle_Factura(),
                dto.getSUMSubtotal()};
            dtm.addRow(row);
        }
        jTConsultas.setModel(dtm);
    }

    private void CargarTablaTotalRecaudadoXDias() {
        simpleDateFormat.applyPattern(DMY_FORMAT);

        Date fechaInicio = null;
        Date fechaFin = null;
        try {

            fechaInicio = simpleDateFormat.parse(jFTFFechaInicio.getText());
            fechaFin = simpleDateFormat.parse(jFTFFechaFin.getText());

            simpleDateFormat.applyPattern(YMD_FORMAT);

            String stringfechaInicio = simpleDateFormat.format(fechaInicio);
            String stringfechaFin = simpleDateFormat.format(fechaFin);

            fechaInicio = simpleDateFormat.parse(stringfechaInicio);
            fechaFin = simpleDateFormat.parse(stringfechaFin);

        } catch (ParseException ex) {
            Logger.getLogger(PStock.class.getName()).log(Level.SEVERE, null, ex);
        }

        dTOProducto = gBDConsultas.TotalRecaudadoXDias(fechaInicio, fechaFin);

        DefaultTableModel dtm;
        dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] nombreDeColumnas = {
            "Día",
            "Total Recaudado en el Día"
        };
        dtm.setColumnIdentifiers(nombreDeColumnas);

        simpleDateFormat.applyPattern(DMY_FORMAT);

        for (DTO dto : dTOProducto) {
            Object[] row = {
                dto.getFechaFactura(),
                dto.getSUMprecioFinalFactura()};

            dtm.addRow(row);
        }
        jTConsultas.setModel(dtm);

        double sum = 0;
        for (int i = 0; i < jTConsultas.getRowCount(); i++) {
            sum = sum + Double.parseDouble(jTConsultas.getValueAt(i, 1).toString());
        }
        jLabelTotalRecaudado.setText("Total Recaudado: " + Double.toString(sum));
    }

    private boolean validar() {
        simpleDateFormat.applyPattern(DMY_FORMAT);

        Date fechaAhora = new Date();
        String strFechaAhora = simpleDateFormat.format(fechaAhora);

        Date milNovecientos = new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime();
        String strMilNovecientos = simpleDateFormat.format(milNovecientos);

        Date dosMilDocientos = new GregorianCalendar(2200, Calendar.JANUARY, 1).getTime();
        String strdosMilDocientos = simpleDateFormat.format(dosMilDocientos);

        Date fechaInicio = null;
        Date fechaFin = null;

        try {
            fechaAhora = simpleDateFormat.parse(strFechaAhora);

            milNovecientos = simpleDateFormat.parse(strMilNovecientos);
            dosMilDocientos = simpleDateFormat.parse(strdosMilDocientos);

            if (!jFTFFechaInicio.getText().equals("")) {
                fechaInicio = simpleDateFormat.parse(jFTFFechaInicio.getText());
            }

            if (!jFTFFechaFin.getText().equals("")) {
                fechaFin = simpleDateFormat.parse(jFTFFechaFin.getText());
            }

        } catch (ParseException ex) {
            Logger.getLogger(JPHorariosE.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (jFTFFechaInicio.getText().equals("")
                || fechaInicio.after(fechaFin)
                || fechaInicio.after(fechaAhora)
                || fechaInicio.before(milNovecientos)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error en la Fecha de Inicio",
                    "Error - Nona Mafalda",
                    JOptionPane.WARNING_MESSAGE);
            jFTFFechaInicio.requestFocus();
            return false;
        } else {
            if (jFTFFechaFin.getText().equals("")
                    || fechaFin.before(fechaInicio)
                    || fechaFin.before(milNovecientos)
                    || fechaFin.after(dosMilDocientos)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error en la Fecha de Fin",
                        "Error - Nona Mafalda",
                        JOptionPane.WARNING_MESSAGE);
                jFTFFechaFin.requestFocus();
                return false;
            }
        }

        return true;
    }

    private void HabilitarDeshabilidarBotones(boolean x) {
        jBProductosSinStock.setEnabled(x);
        jBStockVencidos.setEnabled(x);
        jBCantidadVendidaYTotalRecaudadoXProducto.setEnabled(x);
        jBTotalRecaudadoDias.setEnabled(x);
    }

    private void LimpiarLabels() {
        jLabelProductosSinStock.setText("Productos Sin Stock");
        jLabelProductosStockVencido.setText("Stocks Vencidos");
        jLabelCantidadTotalVendidaXProducto.setText("Cantidad Vendida y Total Recaudado por Producto");
        jLabelTotalRecaudado.setText("Total Recaudado x Día");
    }

    private void ValidacionCampos() {
        ValidacionesCampos.Fecha(jFTFFechaInicio);
        ValidacionesCampos.Fecha(jFTFFechaFin);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTConsultas = new javax.swing.JTable();
        jLabelProductosStockVencido = new javax.swing.JLabel();
        jLabelTotalRecaudado = new javax.swing.JLabel();
        jBCantidadVendidaYTotalRecaudadoXProducto = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jBProductosSinStock = new javax.swing.JButton();
        jLabelProductosSinStock = new javax.swing.JLabel();
        jBStockVencidos = new javax.swing.JButton();
        jLabelCantidadTotalVendidaXProducto = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jFTFFechaFin = new javax.swing.JFormattedTextField();
        jFTFFechaInicio = new javax.swing.JFormattedTextField();
        jBTotalRecaudadoDias = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 0, 0));
        setLayout(null);

        jLTitulo.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLTitulo.setForeground(new java.awt.Color(255, 255, 255));
        jLTitulo.setText("Consultas");
        add(jLTitulo);
        jLTitulo.setBounds(10, 10, 1230, 28);

        jTConsultas.setBackground(new java.awt.Color(0, 0, 0));
        jTConsultas.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTConsultas.setForeground(new java.awt.Color(255, 255, 255));
        jTConsultas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTConsultas.setToolTipText("Tabla de Consultas");
        jTConsultas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTConsultas.setGridColor(new java.awt.Color(102, 102, 102));
        jTConsultas.setNextFocusableComponent(jBProductosSinStock);
        jTConsultas.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTConsultas.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTConsultas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTConsultas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTConsultas.setShowGrid(true);
        jScrollPane1.setViewportView(jTConsultas);

        add(jScrollPane1);
        jScrollPane1.setBounds(10, 40, 1260, 450);

        jLabelProductosStockVencido.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabelProductosStockVencido.setForeground(new java.awt.Color(255, 255, 255));
        jLabelProductosStockVencido.setText("Stocks Vencidos");
        add(jLabelProductosStockVencido);
        jLabelProductosStockVencido.setBounds(70, 550, 960, 40);

        jLabelTotalRecaudado.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabelTotalRecaudado.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalRecaudado.setText("Total Recaudado por Dia/s");
        add(jLabelTotalRecaudado);
        jLabelTotalRecaudado.setBounds(70, 650, 960, 40);

        jBCantidadVendidaYTotalRecaudadoXProducto.setBackground(new java.awt.Color(0, 0, 0));
        jBCantidadVendidaYTotalRecaudadoXProducto.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBCantidadVendidaYTotalRecaudadoXProducto.setForeground(new java.awt.Color(255, 255, 255));
        jBCantidadVendidaYTotalRecaudadoXProducto.setText("X");
        jBCantidadVendidaYTotalRecaudadoXProducto.setToolTipText("Productos sin Stock");
        jBCantidadVendidaYTotalRecaudadoXProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBCantidadVendidaYTotalRecaudadoXProducto.setNextFocusableComponent(jBTotalRecaudadoDias);
        jBCantidadVendidaYTotalRecaudadoXProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCantidadTotalVendidaPorProductoActionPerformed(evt);
            }
        });
        add(jBCantidadVendidaYTotalRecaudadoXProducto);
        jBCantidadVendidaYTotalRecaudadoXProducto.setBounds(10, 600, 53, 40);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Fecha Fin:");
        add(jLabel6);
        jLabel6.setBounds(1050, 640, 110, 50);

        jBProductosSinStock.setBackground(new java.awt.Color(0, 0, 0));
        jBProductosSinStock.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBProductosSinStock.setForeground(new java.awt.Color(255, 255, 255));
        jBProductosSinStock.setText("X");
        jBProductosSinStock.setToolTipText("Productos sin Stock");
        jBProductosSinStock.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBProductosSinStock.setNextFocusableComponent(jBStockVencidos);
        jBProductosSinStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProductosSinStockActionPerformed(evt);
            }
        });
        add(jBProductosSinStock);
        jBProductosSinStock.setBounds(10, 500, 53, 40);

        jLabelProductosSinStock.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabelProductosSinStock.setForeground(new java.awt.Color(255, 255, 255));
        jLabelProductosSinStock.setText("Productos Sin Stock");
        add(jLabelProductosSinStock);
        jLabelProductosSinStock.setBounds(70, 500, 960, 40);

        jBStockVencidos.setBackground(new java.awt.Color(0, 0, 0));
        jBStockVencidos.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBStockVencidos.setForeground(new java.awt.Color(255, 255, 255));
        jBStockVencidos.setText("X");
        jBStockVencidos.setToolTipText("Productos sin Stock");
        jBStockVencidos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBStockVencidos.setNextFocusableComponent(jBCantidadVendidaYTotalRecaudadoXProducto);
        jBStockVencidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBProductosStockVencidoActionPerformed(evt);
            }
        });
        add(jBStockVencidos);
        jBStockVencidos.setBounds(10, 550, 53, 40);

        jLabelCantidadTotalVendidaXProducto.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabelCantidadTotalVendidaXProducto.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCantidadTotalVendidaXProducto.setText("Cantidad Vendida y Total Recaudado por Producto");
        add(jLabelCantidadTotalVendidaXProducto);
        jLabelCantidadTotalVendidaXProducto.setBounds(70, 600, 960, 40);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Fecha Inicio:");
        add(jLabel9);
        jLabel9.setBounds(1050, 500, 110, 50);

        jFTFFechaFin.setBackground(new java.awt.Color(0, 0, 0));
        jFTFFechaFin.setForeground(new java.awt.Color(255, 255, 255));
        jFTFFechaFin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        jFTFFechaFin.setToolTipText("Fecha de Elaboración del Stock");
        jFTFFechaFin.setCaretColor(new java.awt.Color(255, 255, 255));
        jFTFFechaFin.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jFTFFechaFin.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jFTFFechaFin.setNextFocusableComponent(jTConsultas);
        jFTFFechaFin.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jFTFFechaFin.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jFTFFechaFin);
        jFTFFechaFin.setBounds(1160, 640, 110, 50);

        jFTFFechaInicio.setBackground(new java.awt.Color(0, 0, 0));
        jFTFFechaInicio.setForeground(new java.awt.Color(255, 255, 255));
        jFTFFechaInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        jFTFFechaInicio.setToolTipText("Fecha de Elaboración del Stock");
        jFTFFechaInicio.setCaretColor(new java.awt.Color(255, 255, 255));
        jFTFFechaInicio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jFTFFechaInicio.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jFTFFechaInicio.setNextFocusableComponent(jFTFFechaFin);
        jFTFFechaInicio.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jFTFFechaInicio.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jFTFFechaInicio);
        jFTFFechaInicio.setBounds(1160, 500, 110, 50);

        jBTotalRecaudadoDias.setBackground(new java.awt.Color(0, 0, 0));
        jBTotalRecaudadoDias.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBTotalRecaudadoDias.setForeground(new java.awt.Color(255, 255, 255));
        jBTotalRecaudadoDias.setText("X");
        jBTotalRecaudadoDias.setToolTipText("Productos sin Stock");
        jBTotalRecaudadoDias.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBTotalRecaudadoDias.setNextFocusableComponent(jFTFFechaInicio);
        jBTotalRecaudadoDias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTotalRecaudadoDiasActionPerformed(evt);
            }
        });
        add(jBTotalRecaudadoDias);
        jBTotalRecaudadoDias.setBounds(10, 650, 53, 40);
    }// </editor-fold>//GEN-END:initComponents

    private void jBProductosSinStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProductosSinStockActionPerformed
        // TODO add your handling code here:
        LimpiarLabels();
        CargarTablaDTOProductosSinStock();
        jLTitulo.setText("Productos Sin Stock");

        HabilitarDeshabilidarBotones(true);
        jBProductosSinStock.setEnabled(false);
    }//GEN-LAST:event_jBProductosSinStockActionPerformed

    private void jBCantidadTotalVendidaPorProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCantidadTotalVendidaPorProductoActionPerformed
        // TODO add your handling code here:
        if (validar()) {
            LimpiarLabels();
            CargarTablaCantidadTotalVendidaXProducto();
            jLTitulo.setText("Cantidad Vendida y Total Recaudado por Producto entre el " + jFTFFechaInicio.getText() + " y " + jFTFFechaFin.getText());

            HabilitarDeshabilidarBotones(true);
            jBCantidadVendidaYTotalRecaudadoXProducto.setEnabled(false);
        }
    }//GEN-LAST:event_jBCantidadTotalVendidaPorProductoActionPerformed

    private void jBProductosStockVencidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBProductosStockVencidoActionPerformed
        // TODO add your handling code here:
        LimpiarLabels();
        CargarTablaDTOProductosStockVencidos();
        jLTitulo.setText("Stocks Vencidos");

        HabilitarDeshabilidarBotones(true);
        jBStockVencidos.setEnabled(false);
    }//GEN-LAST:event_jBProductosStockVencidoActionPerformed

    private void jBTotalRecaudadoDiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTotalRecaudadoDiasActionPerformed
        // TODO add your handling code here:
        if (validar()) {
            LimpiarLabels();
            CargarTablaTotalRecaudadoXDias();
            jLTitulo.setText("Total Recaudado por Dia/s entre el " + jFTFFechaInicio.getText() + " y " + jFTFFechaFin.getText());

            HabilitarDeshabilidarBotones(true);
            jBTotalRecaudadoDias.setEnabled(false);
        }
    }//GEN-LAST:event_jBTotalRecaudadoDiasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCantidadVendidaYTotalRecaudadoXProducto;
    private javax.swing.JButton jBProductosSinStock;
    private javax.swing.JButton jBStockVencidos;
    private javax.swing.JButton jBTotalRecaudadoDias;
    private javax.swing.JFormattedTextField jFTFFechaFin;
    private javax.swing.JFormattedTextField jFTFFechaInicio;
    private javax.swing.JLabel jLTitulo;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCantidadTotalVendidaXProducto;
    private javax.swing.JLabel jLabelProductosSinStock;
    private javax.swing.JLabel jLabelProductosStockVencido;
    private javax.swing.JLabel jLabelTotalRecaudado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTConsultas;
    // End of variables declaration//GEN-END:variables
}
