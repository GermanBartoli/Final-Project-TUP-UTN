/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venta;

import Venta.Factura.GBDFactura;
import Venta.DetalleFactura.GBDDetalleFactura;
import Venta.Factura.CFactura;
import Venta.DetalleFactura.CDetalleFactura;
import Modelos.TextPrompt;
import Modelos.ValidacionesCampos;
import Producto.GBDProducto;
import javax.swing.JOptionPane;
import Producto.CProducto;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import Cliente.CCliente;
import Cliente.GBDCliente;
import Empleado.CEmpleado;
import Empleado.GBDEmpleado;
import FormaPago.CFormaPago;
import FormaPago.GBDFormaPago;
import Persona.GBDPersona;
import Stock.CStock;
import Stock.GBDStock;

/**
 *
 * @author German Bartoli
 */
public class JPVerFacturasXCliente extends javax.swing.JPanel {

    private final GBDPersona gBDPersona;
    private final GBDCliente gBDCliente;
    private final GBDEmpleado gBDEmpleado;
    private final GBDFormaPago gBDFormaPago;
    private final GBDFactura gBDFactura;
    private final GBDDetalleFactura gBDDetalleFactura;
    private final GBDProducto gBDProducto;

    private DefaultListModel<CCliente> listModel;

    private ArrayList<CCliente> arrayListClientes;
    private ListSelectionListener lslClientes;

    private ArrayList<CFactura> arrayListFacturas;
    private ListSelectionListener lslFactura;

    private ArrayList<CDetalleFactura> arrayListDetallesFactura;
    private ListSelectionListener lslDetallesFactura;

    private ListSelectionListener lslTablaStocks;

    final String DMY_FORMAT;
    final String YMD_FORMAT;

    private SimpleDateFormat simpleDateFormat;

    private SimpleDateFormat simpleTimeFormat;

    private boolean banderaAgregarEditar;

    /**
     * Creates new form JPVerFacturasXCliente
     */
    public JPVerFacturasXCliente() {
        initComponents();

        DMY_FORMAT = "dd/MM/yyyy";
        YMD_FORMAT = "yyyy/MM/dd";

        simpleDateFormat = new SimpleDateFormat();

        simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");

        //1.º
        gBDPersona = new GBDPersona();
        gBDCliente = new GBDCliente();
        gBDEmpleado = new GBDEmpleado();
        gBDFormaPago = new GBDFormaPago();
        gBDFactura = new GBDFactura();
        gBDDetalleFactura = new GBDDetalleFactura();
        gBDProducto = new GBDProducto();

        //Iniciar con el 1ro seleccionado
        //Tira error si no hay un item seleccionado
        CrearListSelectionListenerListaClientes();
        CrearListSelectionListenerTablaFacturas();

        CargarLista();

//        TextPrompt placeHolderCantidad = new TextPrompt("0 - 9", jFTFCantidad, TextPrompt.Show.ALWAYS);
//        TextPrompt placeHolderFechaElaboracion = new TextPrompt("dd/mm/yyyy", jFTFFechaElaboracion, TextPrompt.Show.ALWAYS);
//        TextPrompt placeHolderFechaVencimiento = new TextPrompt("dd/mm/yyyy", jFTFFechaVencimiento, TextPrompt.Show.ALWAYS);
    }

    public void CargarLista() {
        arrayListClientes = gBDCliente.CargarListaTodosLosClientes();

        jLClientes.removeListSelectionListener(lslClientes);

        //Crear un objeto DefaultListModel
        listModel = new DefaultListModel<CCliente>();

        //Recorrer el contenido del ArrayList
        for (int i = 0; i < arrayListClientes.size(); i++) {
            //Añadir cada elemento del ArrayList en el modelo de la lista
            listModel.add(i, arrayListClientes.get(i));
        }
        //Asociar el modelo de lista al JList
        jLClientes.setModel(listModel);

        jLClientes.addListSelectionListener(lslClientes);

        if (jLClientes.getModel().getSize() > 0) {
            HabilitarDeshabilitarCampos(true);

//            HabilitarDeshabilitarBotones(true);
            jLClientes.setSelectedIndex(0);
        } else {
            HabilitarDeshabilitarCampos(false);

        }
    }

    private void CrearListSelectionListenerListaClientes() {
        lslClientes = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                    if (jLClientes.getModel().getSize() != 0) {
                        LimpiarTablaFacturas();
                        LimpiarTablaDetallesFactura();
                        CargarTablaFacturas();
                    }
                }
            }
        };
    }

    private void CargarTablaFacturas() {
        arrayListFacturas = gBDFactura.CargarTablaFacturasXIDCliente(jLClientes.getSelectedValue().getIdCliente());

        jTFacturas.getSelectionModel().removeListSelectionListener(lslFactura);

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] nombreDeColumnas = {
            "Nº",
            "Empleado",
            "Forma de Pago",
            "Fecha",
            "Hora",
            "Precio_Final",
            "Pago_Cliente",
            "Vuelto",
            "Estado"
        };
        dtm.setColumnIdentifiers(nombreDeColumnas);

        simpleDateFormat.applyPattern(DMY_FORMAT);

        for (CFactura factura : arrayListFacturas) {

            CEmpleado empleado = gBDEmpleado.ObtenerEmpleadoXIDActivosYNo(factura.getID_Empleado());
            CFormaPago formaPago = gBDFormaPago.ObtenerFormaPagoXID(factura.getID_Forma_De_Pago());

            String estadoFactura = "Cancelada";
            if (factura.isBaja_Logica()) {
                estadoFactura = "Emitida";
            }

            Object[] row = {
                factura.getID_Factura(),
                empleado.getApellido() + " " + empleado.getNombre() + " DNI: " + empleado.getDni(),
                formaPago.getFormaPago(),
                simpleDateFormat.format(factura.getFecha()),
                simpleTimeFormat.format(factura.getHora()),
                factura.getPrecio_Final(),
                factura.getPago_Cliente(),
                factura.getVuelto(),
                estadoFactura};

            dtm.addRow(row);
        }
        jTFacturas.setModel(dtm);

        jTFacturas.getSelectionModel().addListSelectionListener(lslTablaStocks);

        if (jTFacturas.getModel().getRowCount() > 0) {
            jTFacturas.setRowSelectionInterval(0, 0);
        } else {

        }
    }

    private void CrearListSelectionListenerTablaFacturas() {
        lslTablaStocks = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting() && jTFacturas.getModel().getRowCount() > 0) {
                    CargarTablaDetallesFactura();
                }
            }
        };
    }

    private void CargarTablaDetallesFactura() {

        int fila = jTFacturas.getSelectedRow();
        int columna = 0;
        int idFacturaSeleccionada = (Integer) jTFacturas.getModel().getValueAt(fila, columna);

        arrayListDetallesFactura = gBDDetalleFactura.CargarTablaDetallesFacturaXIDFactura(idFacturaSeleccionada);

        DefaultTableModel dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] nombreDeColumnas
                = {
                    "Producto",
                    "Marca",
                    "Cantidad",
                    "Precio Unitario",
                    "Sub-Total"
                };
        dtm.setColumnIdentifiers(nombreDeColumnas);

        simpleDateFormat.applyPattern(DMY_FORMAT);

        for (CDetalleFactura detallesFactura : arrayListDetallesFactura) {
            CProducto producto = gBDProducto.ObtenerProductoXID(detallesFactura.getID_Producto());

            Object[] row = {
                producto.getNombre(),
                producto.getMarca().getNombre(),
                detallesFactura.getCantidad(),
                detallesFactura.getPrecio_Unitario(),
                detallesFactura.getSub_Total()};

            dtm.addRow(row);
        }
        jTDetallesFactura.setModel(dtm);

//        jTStocks.getSelectionModel().addListSelectionListener(lslTablaStocks);
//        if (jTStocks.getModel().getRowCount() > 0) {
//            jTStocks.setRowSelectionInterval(0, 0);
//            btnEditar.setEnabled(true);
//            btnBorrar.setEnabled(true);
//        } else {
//            btnEditar.setEnabled(false);
//            btnBorrar.setEnabled(false);
//        }
        if (jTDetallesFactura.getModel().getRowCount() > 0) {
            jTDetallesFactura.setRowSelectionInterval(0, 0);
        } else {

        }
    }

    private void LimpiarTablaFacturas() {
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] nombreDeColumnas = {
            "Nº",
            "Empleado",
            "Forma de Pago",
            "Fecha",
            "Hora",
            "Precio_Final",
            "Pago_Cliente",
            "Vuelto",
            "Estado"
        };
        dtm.setColumnIdentifiers(nombreDeColumnas);

        jTFacturas.setModel(dtm);
    }

    private void LimpiarTablaDetallesFactura() {
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] nombreDeColumnas
                = {
                    "Producto",
                    "Cantidad",
                    "Precio Unitario",
                    "Sub-Total"
                };
        dtm.setColumnIdentifiers(nombreDeColumnas);

        jTDetallesFactura.setModel(dtm);
    }

    private void HabilitarDeshabilitarCampos(boolean x) {
        jLClientes.setEnabled(x);
        jTFacturas.setEnabled(x);
        jTDetallesFactura.setEnabled(x);
    }

//    private void HabilitarDeshabilitarBotones(boolean x) {
//        jBAgregar.setEnabled(x);
//        jBEditar.setEnabled(x);
//        jBBorrar.setEnabled(x);
//        jBGuardar.setEnabled(x);
//        jBCancelar.setEnabled(x);
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jLClientes = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTDetallesFactura = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTFacturas = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(new java.awt.Color(255, 255, 255));
        setLayout(null);

        jLClientes.setBackground(new java.awt.Color(0, 0, 0));
        jLClientes.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLClientes.setForeground(new java.awt.Color(255, 255, 255));
        jLClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jLClientes.setToolTipText("Listado de Clientes");
        jLClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLClientes.setNextFocusableComponent(jTFacturas);
        jLClientes.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jLClientes.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jLClientes);

        add(jScrollPane1);
        jScrollPane1.setBounds(920, 10, 350, 640);

        jTDetallesFactura.setBackground(new java.awt.Color(0, 0, 0));
        jTDetallesFactura.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTDetallesFactura.setForeground(new java.awt.Color(255, 255, 255));
        jTDetallesFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Cantidad", "Precio Unitario", "Sub-Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTDetallesFactura.setToolTipText("Detalles de la Factura");
        jTDetallesFactura.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTDetallesFactura.setGridColor(new java.awt.Color(102, 102, 102));
        jTDetallesFactura.setNextFocusableComponent(jLClientes);
        jTDetallesFactura.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTDetallesFactura.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTDetallesFactura.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTDetallesFactura.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(jTDetallesFactura);

        add(jScrollPane5);
        jScrollPane5.setBounds(10, 380, 900, 300);

        jTFacturas.setBackground(new java.awt.Color(0, 0, 0));
        jTFacturas.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTFacturas.setForeground(new java.awt.Color(255, 255, 255));
        jTFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nº", "Empleado", "Forma de Pago", "Fecha", "Hora", "Total", "Pago Cliente", "Vuelto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTFacturas.setToolTipText("Facturas del Cliente");
        jTFacturas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTFacturas.setGridColor(new java.awt.Color(102, 102, 102));
        jTFacturas.setNextFocusableComponent(jTDetallesFactura);
        jTFacturas.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTFacturas.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTFacturas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTFacturas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(jTFacturas);

        add(jScrollPane6);
        jScrollPane6.setBounds(10, 40, 900, 300);

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Facturas");
        add(jLabel8);
        jLabel8.setBounds(370, 0, 150, 42);

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Listado Clientes");
        add(jLabel9);
        jLabel9.setBounds(960, 650, 260, 42);

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Detalles de Factura");
        add(jLabel10);
        jLabel10.setBounds(300, 340, 330, 42);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<CCliente> jLClientes;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTDetallesFactura;
    private javax.swing.JTable jTFacturas;
    // End of variables declaration//GEN-END:variables
}
