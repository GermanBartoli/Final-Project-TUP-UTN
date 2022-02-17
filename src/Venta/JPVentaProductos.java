/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venta;

import Venta.Factura.GBDFactura;
import Venta.DetalleFactura.GBDDetalleFactura;
import Venta.DetalleFactura.CDetalleFactura;
import Venta.Factura.CFactura;
import Cliente.CCliente;
import Cliente.GBDCliente;
import Empleado.CEmpleado;
import FormaPago.CFormaPago;
import FormaPago.GBDFormaPago;
import Modelos.ValidacionesCampos;
import java.sql.Time;
import Producto.CProducto;
import Producto.GBDProducto;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author German Bartoli
 */
public class JPVentaProductos extends javax.swing.JPanel {

    private GBDFormaPago gBDFormaPago;
    private GBDCliente gBDCliente;
    private GBDProducto gBDProducto;
    private GBDFactura gBDFactura;
    private GBDDetalleFactura gBDDetalleFactura;

    private final CEmpleado cEmpleado;
    private CDetalleFactura cDetalleFactura;

    private DefaultComboBoxModel modelFormaPago;
    private DefaultComboBoxModel modelCliente;
    private DefaultComboBoxModel modelProducto;

    private ArrayList<Object[]> filasTabla;

    private ListSelectionListener lslTablaDetalleFactura;

    final String DMY_FORMAT;
    final String YMD_FORMAT;

    private SimpleDateFormat simpleDateFormat;

    final String HMS_FORMAT;
    private SimpleDateFormat simpleTimeFormat;

    private Date ahora;

    private boolean banderaAgregarEditar;

    /**
     * Creates new form JPVentaProductos
     */
    public JPVentaProductos(CEmpleado cEmpleado) {
        initComponents();

//        TextPrompt placeHolderDiaTrabajado = new TextPrompt("dd/mm/yyyy", jFTFFecha, TextPrompt.Show.ALWAYS);
//        TextPrompt placeHolderHoraIngreso = new TextPrompt("HH:mm:ss", jFTFHoraIngreso, TextPrompt.Show.ALWAYS);
//        TextPrompt placeHolderHoraEgreso = new TextPrompt("HH:mm:ss", jFTFHoraEgreso, TextPrompt.Show.ALWAYS);
        DMY_FORMAT = "dd/MM/yyyy HH:mm:ss";
        YMD_FORMAT = "yyyy/MM/dd HH:mm:ss";

        simpleDateFormat = new SimpleDateFormat();

        HMS_FORMAT = "HH:mm:ss";
        simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");

        gBDFormaPago = new GBDFormaPago();
        gBDCliente = new GBDCliente();
        gBDProducto = new GBDProducto();
        gBDFactura = new GBDFactura();
        gBDDetalleFactura = new GBDDetalleFactura();

        CrearListSelectionListenerTablaDetalleFactura();

        this.cEmpleado = cEmpleado;

        CargarComboFormaPago();
        CargarComboClientes();
        CargarComboProductos();

        filasTabla = new ArrayList<Object[]>();

        simpleDateFormat.applyPattern(DMY_FORMAT);

        ahora = new Date();
        String strDateHour = simpleDateFormat.format(ahora);

        jTFFechaHoraFactura.setText(strDateHour);

        jTFNombreApellidoVendedor.setText(cEmpleado.getApellido() + ", " + cEmpleado.getNombre());

        int ultimoIDFactura = gBDFactura.ObtenerUltimoIDFactura();

        if (ultimoIDFactura != 0) {
            jTFNumeroFactura.setText(Integer.toString(ultimoIDFactura));
        }

        if (jCBClientes.getModel().getSize() > 0) {
            MostrarOcultarCamposCliente(false);

            MostrarOcultarBotonesCliente(false);
            jBAgregarCliente.setVisible(true);
        } else {
            MostrarOcultarCamposCliente(false);

            MostrarOcultarBotonesCliente(false);
        }

        if (jCBProductos.getModel().getSize() > 0) {
            HabilitarDeshabilitarCamposProducto(false);
            jTDetalleFactura.setEnabled(true);

            HabilitarDeshabilitarBotonesProducto(false);
            jBAgregarProducto.setEnabled(true);

            jCBProductos.setSelectedIndex(0);
        } else {
            HabilitarDeshabilitarCamposProducto(false);

            HabilitarDeshabilitarBotonesProducto(false);

            LimpiarCamposProducto();
        }

        ValidacionCampos();

        //No copy/paste
        jTFCantidad.setTransferHandler(null);
        jTFPagoCliente.setTransferHandler(null);

    }

    private void CargarComboProductos() {
        modelProducto = new DefaultComboBoxModel();

        ArrayList<CProducto> alProductos = gBDProducto.CargarComboProductos();

        for (CProducto producto : alProductos) {
            modelProducto.addElement(producto);
        }
        jCBProductos.setModel(modelProducto);

        if (jCBProductos.getModel().getSize() > 0) {
            jCBProductos.setEnabled(true);
        } else {
            jCBProductos.setEnabled(false);
        }
    }

    private void CargarComboClientes() {
        modelCliente = new DefaultComboBoxModel();

        ArrayList<CCliente> alClientes = gBDCliente.CargarComboClientes();

        for (CCliente cliente : alClientes) {
            modelCliente.addElement(cliente);
        }
        jCBClientes.setModel(modelCliente);

        if (jCBClientes.getModel().getSize() > 0) {
            jCBClientes.setEnabled(true);
        } else {
            jCBClientes.setEnabled(false);
        }
    }

    private void CargarComboFormaPago() {
        modelFormaPago = new DefaultComboBoxModel();

        ArrayList<CFormaPago> alFormasPago = gBDFormaPago.CargarComboFormasPago();

        for (CFormaPago formaPago : alFormasPago) {
            modelFormaPago.addElement(formaPago);
        }
        jCBFormaPago.setModel(modelFormaPago);

        if (jCBFormaPago.getModel().getSize() > 0) {
            jCBFormaPago.setEnabled(true);
        } else {
            jCBFormaPago.setEnabled(false);
        }
    }

    private void CargarTablaDetalleFactura(ArrayList<Object[]> filasTabla) {
        jTDetalleFactura.getSelectionModel().removeListSelectionListener(lslTablaDetalleFactura);

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] nombreDeColumnas
                = {
                    "ID Producto",
                    "Producto",
                    "Marca",
                    "Cantidad",
                    "Precio Unitario",
                    "Sub-Total"
                };
        dtm.setColumnIdentifiers(nombreDeColumnas);

        for (Object[] filaTabla : filasTabla) {
            dtm.addRow(filaTabla);
        }

        jTDetalleFactura.setModel(dtm);

        jTDetalleFactura.getSelectionModel().addListSelectionListener(lslTablaDetalleFactura);

        if (jTDetalleFactura.getModel().getRowCount() > 0) {
            jTDetalleFactura.setRowSelectionInterval(0, 0);
        } else {

        }
    }

    private void CrearListSelectionListenerTablaDetalleFactura() {
        lslTablaDetalleFactura = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting() && jTDetalleFactura.getModel().getRowCount() > 0) {
                    LimpiarCamposProducto();
                    CargarDetalleFacturaEnCampos();
                }
            }
        };
    }

    private void CargarDetalleFacturaEnCampos() {

        CProducto producto = gBDProducto.ObtenerProductoXID((Integer) jTDetalleFactura.getValueAt(jTDetalleFactura.getSelectedRow(), 0));
        for (int i = 0; i < jCBProductos.getItemCount(); i++) {
            if (jCBProductos.getItemAt(i).getIdProducto() == producto.getIdProducto()) {
                jCBProductos.setSelectedIndex(i);
            }
        }

        Double cantidad = (Double) jTDetalleFactura.getValueAt(jTDetalleFactura.getSelectedRow(), 3);
        jTFCantidad.setText(Double.toString(cantidad));

        Double precioUnitario = (Double) jTDetalleFactura.getValueAt(jTDetalleFactura.getSelectedRow(), 4);
        jTFPrecioUnitario.setText(Double.toString(precioUnitario));

        Double subTotal = (Double) jTDetalleFactura.getValueAt(jTDetalleFactura.getSelectedRow(), 5);
        jTFSubTotal.setText(Double.toString(subTotal));

        double sum = 0;
        for (int i = 0; i < jTDetalleFactura.getRowCount(); i++) {
            sum = sum + Double.parseDouble(jTDetalleFactura.getValueAt(i, 5).toString());
        }
        jTFTotal.setText(Double.toString(sum));

        jBGuardarVenta.setEnabled(true);
    }

    private void LimpiarTabla() {
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] nombreDeColumnas
                = {
                    "ID Producto",
                    "Producto",
                    "Marca",
                    "Cantidad",
                    "Precio Unitario",
                    "Sub-Total"
                };
        dtm.setColumnIdentifiers(nombreDeColumnas);

        jTDetalleFactura.setModel(dtm);
    }

    private void LimpiarCamposProducto() {
        jCBProductos.setSelectedIndex(0);
        jTFCantidad.setText("");
        jTFPrecioUnitario.setText("");
        jTFSubTotal.setText("");
        jTFTotal.setText("");
    }

    private void LimpiarCamposCliente() {
        jTFDni.setText("");
        jTFNombreApellidoCliente.setText("");
        jTFCorreoCliente.setText("");
        jTFTelefonoCliente.setText("");
        jTFCantidadVisitas.setText("");
    }

    private void HabilitarDeshabilitarCamposProducto(boolean x) {
        jTFCantidad.setEnabled(x);
        jCBProductos.setEnabled(x);
        jTDetalleFactura.setEnabled(x);
    }

    private void HabilitarDeshabilitarBotonesProducto(boolean x) {
        jBAgregarProducto.setEnabled(x);
        jBEditarProducto.setEnabled(x);
        jBQuitarProducto.setEnabled(x);
        jBGuardarProducto.setEnabled(x);
        jBCancelarOperacionProducto.setEnabled(x);
    }

    private void MostrarOcultarCamposCliente(boolean x) {
        jLDni.setVisible(x);
        jTFDni.setVisible(x);
        jLCliente.setVisible(x);
        jTFNombreApellidoCliente.setVisible(x);
        jLCorreo.setVisible(x);
        jTFCorreoCliente.setVisible(x);
        jLTelefono.setVisible(x);
        jTFTelefonoCliente.setVisible(x);
        jLCantidadVisitas.setVisible(x);
        jTFCantidadVisitas.setVisible(x);
    }

    private void MostrarOcultarBotonesCliente(boolean x) {
        jBAgregarCliente.setVisible(x);
        jBSeleccionarCliente.setVisible(x);
        jCBClientes.setVisible(x);
        jBQuitarCliente.setVisible(x);
    }

    private boolean ValidarCantidad() {
        if (jTFCantidad.getText().equals("")
                || Double.parseDouble(jTFCantidad.getText()) > 999) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error en la Cantidad del Producto",
                    "Error - Nona Mafalda",
                    JOptionPane.ERROR_MESSAGE);
            jTFCantidad.requestFocus();
            return false;
        }
        return true;
    }

    private boolean ValidarPagoCliente() {
        if (jTFPagoCliente.getText().equals("")
                || Double.parseDouble(jTFPagoCliente.getText()) > 999999
                || Double.parseDouble(jTFPagoCliente.getText()) < Double.parseDouble(jTFTotal.getText())) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error en el Pago del Cliente",
                    "Advertencia - Nona Mafalda",
                    JOptionPane.WARNING_MESSAGE);
            jTFPagoCliente.requestFocus();
            return false;
        }
        return true;
    }

    private void ValidacionCampos() {
        ValidacionesCampos.PesoCantidad(jTFCantidad);
        ValidacionesCampos.Precio(jTFPagoCliente);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTFFechaHoraFactura = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLCliente = new javax.swing.JLabel();
        jTFNombreApellidoCliente = new javax.swing.JTextField();
        jTFNombreApellidoVendedor = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTDetalleFactura = new javax.swing.JTable();
        jTFTotal = new javax.swing.JTextField();
        jBQuitarCliente = new javax.swing.JButton();
        jBAgregarProducto = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jCBFormaPago = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jTFVuelto = new javax.swing.JTextField();
        jTFPagoCliente = new javax.swing.JTextField();
        jTFCorreoCliente = new javax.swing.JTextField();
        jTFTelefonoCliente = new javax.swing.JTextField();
        jLCorreo = new javax.swing.JLabel();
        jLTelefono = new javax.swing.JLabel();
        jBEditarProducto = new javax.swing.JButton();
        jBCancelarOperacionProducto = new javax.swing.JButton();
        jBGuardarVenta = new javax.swing.JButton();
        jBQuitarProducto = new javax.swing.JButton();
        jCBProductos = new javax.swing.JComboBox<>();
        jCBClientes = new javax.swing.JComboBox<>();
        jTFCantidadVisitas = new javax.swing.JTextField();
        jLCantidadVisitas = new javax.swing.JLabel();
        jLCantidad = new javax.swing.JLabel();
        jLCantidad1 = new javax.swing.JLabel();
        jLCantidad2 = new javax.swing.JLabel();
        jBAgregarCliente = new javax.swing.JButton();
        jBSeleccionarCliente = new javax.swing.JButton();
        jLDni = new javax.swing.JLabel();
        jTFDni = new javax.swing.JTextField();
        jBGuardarProducto = new javax.swing.JButton();
        jTFCantidad = new javax.swing.JTextField();
        jTFPrecioUnitario = new javax.swing.JTextField();
        jTFSubTotal = new javax.swing.JTextField();
        jBNuevaVenta = new javax.swing.JButton();
        jTFNumeroFactura = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1280, 695));
        setLayout(null);

        jTFFechaHoraFactura.setBackground(new java.awt.Color(0, 0, 0));
        jTFFechaHoraFactura.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTFFechaHoraFactura.setForeground(new java.awt.Color(255, 255, 255));
        jTFFechaHoraFactura.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFFechaHoraFactura.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFFechaHoraFactura.setEnabled(false);
        jTFFechaHoraFactura.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFFechaHoraFactura.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFFechaHoraFactura);
        jTFFechaHoraFactura.setBounds(340, 10, 180, 80);

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Vendedor:");
        add(jLabel4);
        jLabel4.setBounds(520, 40, 111, 28);

        jLCliente.setBackground(new java.awt.Color(0, 0, 0));
        jLCliente.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLCliente.setForeground(new java.awt.Color(255, 255, 255));
        jLCliente.setText("Cliente:");
        add(jLCliente);
        jLCliente.setBounds(0, 120, 82, 28);

        jTFNombreApellidoCliente.setBackground(new java.awt.Color(0, 0, 0));
        jTFNombreApellidoCliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTFNombreApellidoCliente.setForeground(new java.awt.Color(255, 255, 255));
        jTFNombreApellidoCliente.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFNombreApellidoCliente.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFNombreApellidoCliente.setEnabled(false);
        jTFNombreApellidoCliente.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFNombreApellidoCliente.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFNombreApellidoCliente);
        jTFNombreApellidoCliente.setBounds(90, 100, 140, 70);

        jTFNombreApellidoVendedor.setBackground(new java.awt.Color(0, 0, 0));
        jTFNombreApellidoVendedor.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTFNombreApellidoVendedor.setForeground(new java.awt.Color(255, 255, 255));
        jTFNombreApellidoVendedor.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFNombreApellidoVendedor.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFNombreApellidoVendedor.setEnabled(false);
        jTFNombreApellidoVendedor.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFNombreApellidoVendedor.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFNombreApellidoVendedor);
        jTFNombreApellidoVendedor.setBounds(630, 10, 190, 80);

        jTDetalleFactura.setBackground(new java.awt.Color(0, 0, 0));
        jTDetalleFactura.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTDetalleFactura.setForeground(new java.awt.Color(255, 255, 255));
        jTDetalleFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Producto", "Producto", "Marca", "Cantidad", "Precio Unitario", "Sub-Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTDetalleFactura.setToolTipText("Detalles de la Factura");
        jTDetalleFactura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTDetalleFactura.setEnabled(false);
        jTDetalleFactura.setGridColor(new java.awt.Color(102, 102, 102));
        jTDetalleFactura.setNextFocusableComponent(jBAgregarProducto);
        jTDetalleFactura.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTDetalleFactura.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTDetalleFactura.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTDetalleFactura.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTDetalleFactura);

        add(jScrollPane1);
        jScrollPane1.setBounds(0, 180, 1280, 216);

        jTFTotal.setBackground(new java.awt.Color(0, 0, 0));
        jTFTotal.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTFTotal.setForeground(new java.awt.Color(255, 255, 255));
        jTFTotal.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFTotal.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFTotal.setEnabled(false);
        jTFTotal.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFTotal.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFTotal);
        jTFTotal.setBounds(1070, 580, 200, 110);

        jBQuitarCliente.setBackground(new java.awt.Color(0, 0, 0));
        jBQuitarCliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jBQuitarCliente.setForeground(new java.awt.Color(255, 255, 255));
        jBQuitarCliente.setText("Quitar Cliente");
        jBQuitarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBQuitarCliente.setNextFocusableComponent(jBAgregarProducto);
        jBQuitarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBQuitarClienteActionPerformed(evt);
            }
        });
        add(jBQuitarCliente);
        jBQuitarCliente.setBounds(830, 10, 200, 80);

        jBAgregarProducto.setBackground(new java.awt.Color(0, 0, 0));
        jBAgregarProducto.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jBAgregarProducto.setForeground(new java.awt.Color(255, 255, 255));
        jBAgregarProducto.setText("Agregar");
        jBAgregarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarProductoActionPerformed(evt);
            }
        });
        add(jBAgregarProducto);
        jBAgregarProducto.setBounds(90, 500, 130, 50);

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Pago Cliente");
        add(jLabel7);
        jLabel7.setBounds(380, 580, 140, 110);

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Forma de Pago");
        add(jLabel8);
        jLabel8.setBounds(10, 620, 170, 30);

        jCBFormaPago.setBackground(new java.awt.Color(0, 0, 0));
        jCBFormaPago.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jCBFormaPago.setForeground(new java.awt.Color(255, 255, 255));
        jCBFormaPago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jCBFormaPago.setNextFocusableComponent(jTFPagoCliente);
        add(jCBFormaPago);
        jCBFormaPago.setBounds(180, 580, 200, 110);

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Vuelto");
        add(jLabel9);
        jLabel9.setBounds(700, 580, 100, 110);

        jTFVuelto.setBackground(new java.awt.Color(0, 0, 0));
        jTFVuelto.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTFVuelto.setForeground(new java.awt.Color(255, 255, 255));
        jTFVuelto.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFVuelto.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFVuelto.setEnabled(false);
        jTFVuelto.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFVuelto.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFVuelto);
        jTFVuelto.setBounds(810, 580, 170, 110);

        jTFPagoCliente.setBackground(new java.awt.Color(0, 0, 0));
        jTFPagoCliente.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTFPagoCliente.setForeground(new java.awt.Color(255, 255, 255));
        jTFPagoCliente.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFPagoCliente.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFPagoCliente.setEnabled(false);
        jTFPagoCliente.setNextFocusableComponent(jBGuardarVenta);
        jTFPagoCliente.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFPagoCliente.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFPagoCliente);
        jTFPagoCliente.setBounds(520, 580, 170, 110);

        jTFCorreoCliente.setBackground(new java.awt.Color(0, 0, 0));
        jTFCorreoCliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTFCorreoCliente.setForeground(new java.awt.Color(255, 255, 255));
        jTFCorreoCliente.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFCorreoCliente.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFCorreoCliente.setEnabled(false);
        jTFCorreoCliente.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFCorreoCliente.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFCorreoCliente);
        jTFCorreoCliente.setBounds(500, 100, 250, 70);

        jTFTelefonoCliente.setBackground(new java.awt.Color(0, 0, 0));
        jTFTelefonoCliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTFTelefonoCliente.setForeground(new java.awt.Color(255, 255, 255));
        jTFTelefonoCliente.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFTelefonoCliente.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFTelefonoCliente.setEnabled(false);
        jTFTelefonoCliente.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFTelefonoCliente.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFTelefonoCliente);
        jTFTelefonoCliente.setBounds(860, 100, 210, 70);

        jLCorreo.setBackground(new java.awt.Color(0, 0, 0));
        jLCorreo.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLCorreo.setForeground(new java.awt.Color(255, 255, 255));
        jLCorreo.setText("Correo:");
        add(jLCorreo);
        jLCorreo.setBounds(420, 120, 79, 28);

        jLTelefono.setBackground(new java.awt.Color(0, 0, 0));
        jLTelefono.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLTelefono.setForeground(new java.awt.Color(255, 255, 255));
        jLTelefono.setText("Telefono:");
        add(jLTelefono);
        jLTelefono.setBounds(760, 120, 100, 28);

        jBEditarProducto.setBackground(new java.awt.Color(0, 0, 0));
        jBEditarProducto.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jBEditarProducto.setForeground(new java.awt.Color(255, 255, 255));
        jBEditarProducto.setText("Editar");
        jBEditarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBEditarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEditarProductoActionPerformed(evt);
            }
        });
        add(jBEditarProducto);
        jBEditarProducto.setBounds(230, 500, 100, 50);

        jBCancelarOperacionProducto.setBackground(new java.awt.Color(0, 0, 0));
        jBCancelarOperacionProducto.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jBCancelarOperacionProducto.setForeground(new java.awt.Color(255, 255, 255));
        jBCancelarOperacionProducto.setText("Cancelar");
        jBCancelarOperacionProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBCancelarOperacionProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarOperacionProductoActionPerformed(evt);
            }
        });
        add(jBCancelarOperacionProducto);
        jBCancelarOperacionProducto.setBounds(590, 500, 126, 50);

        jBGuardarVenta.setBackground(new java.awt.Color(0, 0, 0));
        jBGuardarVenta.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jBGuardarVenta.setForeground(new java.awt.Color(255, 255, 255));
        jBGuardarVenta.setText("Guardar Venta");
        jBGuardarVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBGuardarVenta.setEnabled(false);
        jBGuardarVenta.setNextFocusableComponent(jBNuevaVenta);
        jBGuardarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarVentaActionPerformed(evt);
            }
        });
        add(jBGuardarVenta);
        jBGuardarVenta.setBounds(730, 500, 190, 50);

        jBQuitarProducto.setBackground(new java.awt.Color(0, 0, 0));
        jBQuitarProducto.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jBQuitarProducto.setForeground(new java.awt.Color(255, 255, 255));
        jBQuitarProducto.setText("Quitar");
        jBQuitarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBQuitarProducto.setNextFocusableComponent(jCBFormaPago);
        jBQuitarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBQuitarProductoActionPerformed(evt);
            }
        });
        add(jBQuitarProducto);
        jBQuitarProducto.setBounds(340, 500, 100, 50);

        jCBProductos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCBProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jCBProductos.setNextFocusableComponent(jTFCantidad);
        add(jCBProductos);
        jCBProductos.setBounds(10, 410, 340, 60);

        jCBClientes.setBackground(new java.awt.Color(0, 0, 0));
        jCBClientes.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCBClientes.setForeground(new java.awt.Color(255, 255, 255));
        jCBClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jCBClientes.setNextFocusableComponent(jBSeleccionarCliente);
        add(jCBClientes);
        jCBClientes.setBounds(1040, 10, 240, 80);

        jTFCantidadVisitas.setBackground(new java.awt.Color(0, 0, 0));
        jTFCantidadVisitas.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTFCantidadVisitas.setForeground(new java.awt.Color(255, 255, 255));
        jTFCantidadVisitas.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFCantidadVisitas.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFCantidadVisitas.setEnabled(false);
        jTFCantidadVisitas.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFCantidadVisitas.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFCantidadVisitas);
        jTFCantidadVisitas.setBounds(1150, 100, 120, 70);

        jLCantidadVisitas.setBackground(new java.awt.Color(0, 0, 0));
        jLCantidadVisitas.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLCantidadVisitas.setForeground(new java.awt.Color(255, 255, 255));
        jLCantidadVisitas.setText("Visitas:");
        add(jLCantidadVisitas);
        jLCantidadVisitas.setBounds(1070, 120, 80, 28);

        jLCantidad.setBackground(new java.awt.Color(0, 0, 0));
        jLCantidad.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLCantidad.setForeground(new java.awt.Color(255, 255, 255));
        jLCantidad.setText("Cantidad:");
        add(jLCantidad);
        jLCantidad.setBounds(370, 410, 110, 60);

        jLCantidad1.setBackground(new java.awt.Color(0, 0, 0));
        jLCantidad1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLCantidad1.setForeground(new java.awt.Color(255, 255, 255));
        jLCantidad1.setText("Precio Unitario/Kg:");
        add(jLCantidad1);
        jLCantidad1.setBounds(640, 410, 210, 60);

        jLCantidad2.setBackground(new java.awt.Color(0, 0, 0));
        jLCantidad2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLCantidad2.setForeground(new java.awt.Color(255, 255, 255));
        jLCantidad2.setText("Sub Total:");
        add(jLCantidad2);
        jLCantidad2.setBounds(1020, 410, 111, 60);

        jBAgregarCliente.setBackground(new java.awt.Color(0, 0, 0));
        jBAgregarCliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jBAgregarCliente.setForeground(new java.awt.Color(255, 255, 255));
        jBAgregarCliente.setText("Agregar Cliente (Opcional)");
        jBAgregarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBAgregarCliente.setNextFocusableComponent(jBAgregarProducto);
        jBAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarClienteActionPerformed(evt);
            }
        });
        add(jBAgregarCliente);
        jBAgregarCliente.setBounds(830, 10, 280, 80);

        jBSeleccionarCliente.setBackground(new java.awt.Color(0, 0, 0));
        jBSeleccionarCliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jBSeleccionarCliente.setForeground(new java.awt.Color(255, 255, 255));
        jBSeleccionarCliente.setText("Seleccionar Cliente");
        jBSeleccionarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBSeleccionarCliente.setNextFocusableComponent(jBAgregarProducto);
        jBSeleccionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSeleccionarClienteActionPerformed(evt);
            }
        });
        add(jBSeleccionarCliente);
        jBSeleccionarCliente.setBounds(830, 10, 200, 80);

        jLDni.setBackground(new java.awt.Color(0, 0, 0));
        jLDni.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLDni.setForeground(new java.awt.Color(255, 255, 255));
        jLDni.setText("DNI:");
        add(jLDni);
        jLDni.setBounds(230, 120, 50, 28);

        jTFDni.setBackground(new java.awt.Color(0, 0, 0));
        jTFDni.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTFDni.setForeground(new java.awt.Color(255, 255, 255));
        jTFDni.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFDni.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFDni.setEnabled(false);
        jTFDni.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFDni.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFDni);
        jTFDni.setBounds(280, 100, 140, 70);

        jBGuardarProducto.setBackground(new java.awt.Color(0, 0, 0));
        jBGuardarProducto.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jBGuardarProducto.setForeground(new java.awt.Color(255, 255, 255));
        jBGuardarProducto.setText("Guardar");
        jBGuardarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBGuardarProducto.setNextFocusableComponent(jBCancelarOperacionProducto);
        jBGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarProductoActionPerformed(evt);
            }
        });
        add(jBGuardarProducto);
        jBGuardarProducto.setBounds(450, 500, 130, 50);

        jTFCantidad.setBackground(new java.awt.Color(0, 0, 0));
        jTFCantidad.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTFCantidad.setForeground(new java.awt.Color(255, 255, 255));
        jTFCantidad.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFCantidad.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFCantidad.setNextFocusableComponent(jBGuardarProducto);
        jTFCantidad.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFCantidad.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFCantidad);
        jTFCantidad.setBounds(480, 410, 140, 60);

        jTFPrecioUnitario.setBackground(new java.awt.Color(0, 0, 0));
        jTFPrecioUnitario.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTFPrecioUnitario.setForeground(new java.awt.Color(255, 255, 255));
        jTFPrecioUnitario.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFPrecioUnitario.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFPrecioUnitario.setEnabled(false);
        jTFPrecioUnitario.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFPrecioUnitario.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFPrecioUnitario);
        jTFPrecioUnitario.setBounds(850, 410, 160, 60);

        jTFSubTotal.setBackground(new java.awt.Color(0, 0, 0));
        jTFSubTotal.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTFSubTotal.setForeground(new java.awt.Color(255, 255, 255));
        jTFSubTotal.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFSubTotal.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFSubTotal.setEnabled(false);
        jTFSubTotal.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFSubTotal.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFSubTotal);
        jTFSubTotal.setBounds(1140, 410, 120, 60);

        jBNuevaVenta.setBackground(new java.awt.Color(0, 0, 0));
        jBNuevaVenta.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jBNuevaVenta.setForeground(new java.awt.Color(255, 255, 255));
        jBNuevaVenta.setText("Nueva  Venta");
        jBNuevaVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBNuevaVenta.setNextFocusableComponent(jBAgregarProducto);
        jBNuevaVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNuevaVentaActionPerformed(evt);
            }
        });
        add(jBNuevaVenta);
        jBNuevaVenta.setBounds(940, 500, 180, 50);

        jTFNumeroFactura.setBackground(new java.awt.Color(0, 0, 0));
        jTFNumeroFactura.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTFNumeroFactura.setForeground(new java.awt.Color(255, 255, 255));
        jTFNumeroFactura.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFNumeroFactura.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTFNumeroFactura.setEnabled(false);
        jTFNumeroFactura.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFNumeroFactura.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFNumeroFactura);
        jTFNumeroFactura.setBounds(160, 10, 170, 80);

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nº de Factura:");
        add(jLabel5);
        jLabel5.setBounds(0, 10, 170, 70);

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Total");
        add(jLabel11);
        jLabel11.setBounds(990, 580, 80, 110);
    }// </editor-fold>//GEN-END:initComponents

    private void jBQuitarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBQuitarClienteActionPerformed
        // TODO add your handling code here:
        MostrarOcultarCamposCliente(false);

        LimpiarCamposCliente();

        MostrarOcultarBotonesCliente(false);
        jBAgregarCliente.setVisible(true);

        jBAgregarCliente.requestFocus();
    }//GEN-LAST:event_jBQuitarClienteActionPerformed

    private void jBSeleccionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSeleccionarClienteActionPerformed
        // TODO add your handling code here:
        jBSeleccionarCliente.setVisible(false);
        jCBClientes.setVisible(false);

        CCliente cCliente = (CCliente) jCBClientes.getSelectedItem();
        jTFDni.setText(Integer.toString(cCliente.getDni()));
        jTFNombreApellidoCliente.setText(cCliente.getNombre() + ", " + cCliente.getApellido());
        jTFCorreoCliente.setText(cCliente.getCorreo());
        jTFTelefonoCliente.setText(cCliente.getTel());
        jTFCantidadVisitas.setText(Integer.toString(cCliente.getCantidadVisitas()));

        MostrarOcultarCamposCliente(true);
        jBQuitarCliente.setVisible(true);

        jBAgregarProducto.requestFocus();
    }//GEN-LAST:event_jBSeleccionarClienteActionPerformed

    private void jBAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarClienteActionPerformed
        // TODO add your handling code here:
        jBAgregarCliente.setVisible(false);
        jBSeleccionarCliente.setVisible(true);
        jCBClientes.setVisible(true);
        jCBClientes.requestFocus();
    }//GEN-LAST:event_jBAgregarClienteActionPerformed

    private void jBAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarProductoActionPerformed
        // TODO add your handling code here:
        if (jCBProductos.getModel().getSize() > 0
                && jCBFormaPago.getModel().getSize() > 0) {

            banderaAgregarEditar = true;
            LimpiarCamposProducto();

            HabilitarDeshabilitarCamposProducto(true);
            jTDetalleFactura.setEnabled(false);

            HabilitarDeshabilitarBotonesProducto(false);
            jBGuardarProducto.setEnabled(true);
            jBCancelarOperacionProducto.setEnabled(true);

            jBGuardarVenta.setEnabled(false);

            jCBProductos.requestFocus();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe cargar primero Formas de Pago y Productos",
                    "Advertencia - Nona Mafalda",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jBAgregarProductoActionPerformed

    private void jBGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarProductoActionPerformed
        // TODO add your handling code here:
        if (ValidarCantidad()) {

            CProducto cProducto = (CProducto) jCBProductos.getSelectedItem();

            double cantidad = Double.parseDouble(jTFCantidad.getText());
            double precioUnitario = cProducto.getPrecio();
            double subTotal = cantidad * precioUnitario;

            Object[] row = {
                cProducto.getIdProducto(),
                cProducto.getNombre(),
                cProducto.getMarca(),
                cantidad,
                precioUnitario,
                subTotal
            };

            if (banderaAgregarEditar) {
                filasTabla.add(row);
            } else {
                int tablaFilaSeleccionada = jTDetalleFactura.getSelectedRow();
                filasTabla.set(tablaFilaSeleccionada, row);
            }

            int tableRowSelected = jTDetalleFactura.getSelectedRow();

            CargarTablaDetalleFactura(filasTabla);

            int tableLastRow = jTDetalleFactura.getRowCount() - 1;

            if (banderaAgregarEditar) {
                jTDetalleFactura.setRowSelectionInterval(tableLastRow, tableLastRow);
            } else {
                jTDetalleFactura.getSelectionModel().removeListSelectionListener(lslTablaDetalleFactura);
                jTDetalleFactura.removeRowSelectionInterval(tableRowSelected, tableRowSelected);
                jTDetalleFactura.getSelectionModel().addListSelectionListener(lslTablaDetalleFactura);
                jTDetalleFactura.setRowSelectionInterval(tableRowSelected, tableRowSelected);
            }

            HabilitarDeshabilitarCamposProducto(false);
            jTDetalleFactura.setEnabled(true);

            HabilitarDeshabilitarBotonesProducto(true);
            jBGuardarProducto.setEnabled(false);
            jBCancelarOperacionProducto.setEnabled(false);

            jBGuardarVenta.setEnabled(true);
            jTFPagoCliente.setEnabled(true);

            jTDetalleFactura.requestFocus();
        }
    }//GEN-LAST:event_jBGuardarProductoActionPerformed

    private void jBEditarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEditarProductoActionPerformed
        // TODO add your handling code here:

        if (jCBProductos.getModel().getSize() > 0
                && jCBFormaPago.getModel().getSize() > 0
                && jTDetalleFactura.getModel().getRowCount() > 0) {

            banderaAgregarEditar = false;

            HabilitarDeshabilitarCamposProducto(true);
            jTDetalleFactura.setEnabled(false);

            HabilitarDeshabilitarBotonesProducto(false);
            jBGuardarProducto.setEnabled(true);
            jBCancelarOperacionProducto.setEnabled(true);

            jBGuardarVenta.setEnabled(false);

            jCBProductos.requestFocus();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe cargar primero Formas de Pago y Productos",
                    "Advertencia - Nona Mafalda",
                    JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_jBEditarProductoActionPerformed

    private void jBQuitarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBQuitarProductoActionPerformed
        // TODO add your handling code here:
        if (jTDetalleFactura.getModel().getRowCount() > 0) {

            if (JOptionPane.showConfirmDialog(
                    this,
                    "Esta seguro?",
                    "Quitando Producto Seleccionado - Nona Mafalda",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                // yes option
                int tablaFilaSeleccionada = jTDetalleFactura.getSelectedRow();

                filasTabla.remove(tablaFilaSeleccionada);

                jTDetalleFactura.getSelectionModel().removeListSelectionListener(lslTablaDetalleFactura);
                jTDetalleFactura.removeRowSelectionInterval(tablaFilaSeleccionada, tablaFilaSeleccionada);

                DefaultTableModel modelo = (DefaultTableModel) jTDetalleFactura.getModel();
                modelo.removeRow(tablaFilaSeleccionada);

                jTDetalleFactura.getSelectionModel().addListSelectionListener(lslTablaDetalleFactura);

                if (jTDetalleFactura.getModel().getRowCount() > 0) {

                    jBEditarProducto.setEnabled(true);
                    jBQuitarProducto.setEnabled(true);

                    jBGuardarVenta.setEnabled(true);
                    jTFPagoCliente.setEnabled(true);

                    jTDetalleFactura.setRowSelectionInterval(0, 0);

                    jTDetalleFactura.requestFocus();

                } else {
                    jBEditarProducto.setEnabled(false);
                    jBQuitarProducto.setEnabled(false);

                    jBGuardarVenta.setEnabled(false);
                    jTFPagoCliente.setEnabled(false);
                    LimpiarCamposProducto();
                    LimpiarTabla();

                    jBAgregarProducto.requestFocus();
                }

            } else {
                // no option
            }
        }
    }//GEN-LAST:event_jBQuitarProductoActionPerformed

    private void jBCancelarOperacionProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarOperacionProductoActionPerformed
        // TODO add your handling code here:
        HabilitarDeshabilitarCamposProducto(false);
        jTDetalleFactura.setEnabled(true);

        HabilitarDeshabilitarBotonesProducto(true);
        jBGuardarProducto.setEnabled(false);
        jBCancelarOperacionProducto.setEnabled(false);

        int tablaFilaSeleccionada = jTDetalleFactura.getSelectedRow();

        if (jTDetalleFactura.getModel().getRowCount() > 0) {
            jTDetalleFactura.getSelectionModel().removeListSelectionListener(lslTablaDetalleFactura);
            jTDetalleFactura.removeRowSelectionInterval(tablaFilaSeleccionada, tablaFilaSeleccionada);
            jTDetalleFactura.getSelectionModel().addListSelectionListener(lslTablaDetalleFactura);
            jTDetalleFactura.setRowSelectionInterval(tablaFilaSeleccionada, tablaFilaSeleccionada);

            jTDetalleFactura.requestFocus();
        } else {
            jBQuitarProducto.setEnabled(false);
            jBEditarProducto.setEnabled(false);

            LimpiarCamposProducto();

            jBAgregarProducto.requestFocus();
        }


    }//GEN-LAST:event_jBCancelarOperacionProductoActionPerformed

    private void jBNuevaVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNuevaVentaActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(
                this,
                "Esta seguro?",
                "Limpiar Campos - Nona Mafalda",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            LimpiarTabla();
            LimpiarCamposProducto();
            LimpiarCamposCliente();

            jTFPagoCliente.setText("");
            jTFVuelto.setText("");
            jTFTotal.setText("");

            CargarComboFormaPago();
            CargarComboClientes();
            CargarComboProductos();

            filasTabla = new ArrayList<Object[]>();

            simpleDateFormat.applyPattern(DMY_FORMAT);

            ahora = new Date();
            String strDateHour = simpleDateFormat.format(ahora);

            jTFFechaHoraFactura.setText(strDateHour);

            jTFNombreApellidoVendedor.setText(cEmpleado.getApellido() + ", " + cEmpleado.getNombre());

            int ultimoIDFactura = gBDFactura.ObtenerUltimoIDFactura();

            if (ultimoIDFactura > 0) {
                jTFNumeroFactura.setText(Integer.toString(ultimoIDFactura));
            }

            if (jCBProductos.getModel().getSize() > 0) {
                HabilitarDeshabilitarCamposProducto(false);

                HabilitarDeshabilitarBotonesProducto(false);
                jBAgregarProducto.setEnabled(true);

                LimpiarCamposProducto();
            } else {
                HabilitarDeshabilitarCamposProducto(false);

                HabilitarDeshabilitarBotonesProducto(false);

                jCBProductos.setSelectedIndex(0);
            }

            jBGuardarVenta.setEnabled(false);
            jTFPagoCliente.setEnabled(false);

            //Botones y Campos Cliente
            jBAgregarCliente.setVisible(true);

            jBSeleccionarCliente.setVisible(false);
            jCBClientes.setVisible(false);
            jBQuitarCliente.setVisible(false);

            MostrarOcultarCamposCliente(false);

            jBAgregarCliente.requestFocus();
        }
    }//GEN-LAST:event_jBNuevaVentaActionPerformed

    private void jBGuardarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarVentaActionPerformed
        // TODO add your handling code here:
        if (ValidarPagoCliente()) {

            int idEmpleado = cEmpleado.getIdEmpleado();

            int idCliente;

            if (!jBQuitarCliente.isVisible()) {
                idCliente = 8;
            } else {
                CCliente cliente = (CCliente) jCBClientes.getModel().getSelectedItem();
                idCliente = cliente.getIdCliente();
            }

            int idFormaPago;
            CFormaPago formaPago = (CFormaPago) jCBFormaPago.getModel().getSelectedItem();
            idFormaPago = formaPago.getIdFormaPago();

            Date ahoraFecha = new Date();
            String fecha;

            Time ahoraHoraTime = null;

            try {
                simpleDateFormat.applyPattern(YMD_FORMAT);
                fecha = simpleDateFormat.format(ahoraFecha);
                ahoraFecha = simpleDateFormat.parse(fecha);

                ahoraHoraTime = new java.sql.Time(ahoraFecha.getTime());

            } catch (ParseException ex) {
                Logger.getLogger(JPVentaProductos.class.getName()).log(Level.SEVERE, null, ex);
            }

            double total = Double.parseDouble(jTFTotal.getText());
            double pagoCliente = Double.parseDouble(jTFPagoCliente.getText());
            double vuelto = pagoCliente - total;

            CFactura cFactura = new CFactura(idEmpleado, idCliente, idFormaPago,
                    ahoraFecha, ahoraHoraTime, total, pagoCliente, vuelto, true);

            boolean exitoAgregarFactura = false;

            exitoAgregarFactura = gBDFactura.AgregarFactura(cFactura);

            int ultimoIDFactura = gBDFactura.ObtenerUltimoIDFactura();

            boolean exitoAgregarDetallesFactura = false;

            for (Object[] object : filasTabla) {
                exitoAgregarDetallesFactura = gBDDetalleFactura.AgregarDetalleFactura(ultimoIDFactura, object);
            }

            if (exitoAgregarFactura && exitoAgregarDetallesFactura) {

                if (jBQuitarCliente.isVisible()) {
                    CCliente cliente = (CCliente) jCBClientes.getModel().getSelectedItem();
                    cliente.setCantidadVisitas(cliente.getCantidadVisitas() + 1);
                    gBDCliente.ModificarCliente(cliente);
                }

                jBGuardarVenta.setEnabled(false);
                jTFPagoCliente.setEnabled(false);
                jCBFormaPago.setEnabled(false);
                jTFVuelto.setText(String.valueOf(vuelto));

                HabilitarDeshabilitarCamposProducto(false);

                HabilitarDeshabilitarBotonesProducto(false);

                int tablaFilaSeleccionada = jTDetalleFactura.getSelectedRow();
                jTDetalleFactura.getSelectionModel().removeListSelectionListener(lslTablaDetalleFactura);
                jTDetalleFactura.removeRowSelectionInterval(tablaFilaSeleccionada, tablaFilaSeleccionada);

                if (jBQuitarCliente.isVisible()) {
                    MostrarOcultarCamposCliente(true);
                    MostrarOcultarBotonesCliente(false);
                } else {
                    MostrarOcultarCamposCliente(false);
                    MostrarOcultarBotonesCliente(false);
                }

                JOptionPane.showMessageDialog(
                        this,
                        "La Venta se guardó Correctamente",
                        "- Nona Mafalda",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBGuardarVentaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAgregarCliente;
    private javax.swing.JButton jBAgregarProducto;
    private javax.swing.JButton jBCancelarOperacionProducto;
    private javax.swing.JButton jBEditarProducto;
    private javax.swing.JButton jBGuardarProducto;
    private javax.swing.JButton jBGuardarVenta;
    private javax.swing.JButton jBNuevaVenta;
    private javax.swing.JButton jBQuitarCliente;
    private javax.swing.JButton jBQuitarProducto;
    private javax.swing.JButton jBSeleccionarCliente;
    private javax.swing.JComboBox<CCliente> jCBClientes;
    private javax.swing.JComboBox<String> jCBFormaPago;
    private javax.swing.JComboBox<CProducto> jCBProductos;
    private javax.swing.JLabel jLCantidad;
    private javax.swing.JLabel jLCantidad1;
    private javax.swing.JLabel jLCantidad2;
    private javax.swing.JLabel jLCantidadVisitas;
    private javax.swing.JLabel jLCliente;
    private javax.swing.JLabel jLCorreo;
    private javax.swing.JLabel jLDni;
    private javax.swing.JLabel jLTelefono;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTDetalleFactura;
    private javax.swing.JTextField jTFCantidad;
    private javax.swing.JTextField jTFCantidadVisitas;
    private javax.swing.JTextField jTFCorreoCliente;
    private javax.swing.JTextField jTFDni;
    private javax.swing.JTextField jTFFechaHoraFactura;
    private javax.swing.JTextField jTFNombreApellidoCliente;
    private javax.swing.JTextField jTFNombreApellidoVendedor;
    private javax.swing.JTextField jTFNumeroFactura;
    private javax.swing.JTextField jTFPagoCliente;
    private javax.swing.JTextField jTFPrecioUnitario;
    private javax.swing.JTextField jTFSubTotal;
    private javax.swing.JTextField jTFTelefonoCliente;
    private javax.swing.JTextField jTFTotal;
    private javax.swing.JTextField jTFVuelto;
    // End of variables declaration//GEN-END:variables
}
