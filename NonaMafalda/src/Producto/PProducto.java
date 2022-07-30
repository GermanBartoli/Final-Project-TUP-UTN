/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Producto;

import Marca.CMarca;
import Marca.GBDMarca;
import Modelos.ValidacionesCampos;
import TipoProducto.CTipoProducto;
import TipoProducto.GBDTipoProducto;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author German Bartoli
 */
public class PProducto extends javax.swing.JPanel {

    private final GBDMarca gestorBDMarca;
    private final GBDProducto gestorBDProducto;
    private final GBDTipoProducto gestorBDTipoProducto;

    private DefaultComboBoxModel modelMarca;
    private DefaultComboBoxModel modelTipoProducto;

    private ArrayList<CProducto> listaProductos;
    private DefaultListModel<CProducto> listModel;

    private ListSelectionListener lslListaProductos;

    private boolean banderaAgregarEditar;

    /**
     * Creates new form Inicio
     */
    public PProducto() {
        initComponents();

        //1.º
        gestorBDMarca = new GBDMarca();
        gestorBDProducto = new GBDProducto();
        gestorBDTipoProducto = new GBDTipoProducto();

        //2.º
        cargarComboMarca();
        cargarComboTipoProducto();

        //Iniciar con el 1ro seleccionado
        //Tira error si no hay un item seleccionado
        CrearListSelectionListenerListaProductos();

        CargarLista();

        //TA sin scrool horizontal
        jTAIngredientes.setLineWrap(true);
        jTACoccion.setLineWrap(true);

        ValidacionCampos();

        jTFNombre.setTransferHandler(null);
        jTFTipoProducto.setTransferHandler(null);
        jTFMarca.setTransferHandler(null);
        jTFPeso.setTransferHandler(null);
        jTFPrecio.setTransferHandler(null);
        jTFUnidad.setTransferHandler(null);
        jTFPlatos.setTransferHandler(null);
        jTFStockMinimo.setTransferHandler(null);
        jTAIngredientes.setTransferHandler(null);
        jTACoccion.setTransferHandler(null);

        this.setVisible(false);
    }

    public void CargarLista() {
        listaProductos = gestorBDProducto.CargarListaProductos();

        jLProductos.removeListSelectionListener(lslListaProductos);

        //Crear un objeto DefaultListModel
        listModel = new DefaultListModel<CProducto>();

        //Recorrer el contenido del ArrayList
        for (int i = 0; i < listaProductos.size(); i++) {
            //Añadir cada elemento del ArrayList en el modelo de la lista
            listModel.add(i, listaProductos.get(i));
        }
        //Asociar el modelo de lista al JList
        jLProductos.setModel(listModel);

        jLProductos.addListSelectionListener(lslListaProductos);

        VerTipoProductoMarca(true);

        HabilitarDeshabilitarCampos(false);
        jLProductos.setEnabled(true);

        HabilitarDeshabilitarBotones(true);
        jBGuardar.setEnabled(false);
        jBCancelar.setEnabled(false);

        if (jLProductos.getModel().getSize() > 0) {

            jLProductos.setSelectedIndex(0);
        } else {

            jLProductos.setEnabled(false);

            HabilitarDeshabilitarBotones(false);
            jBAgregar.setEnabled(true);

            LimpiarCampos();
        }
    }

    private void CrearListSelectionListenerListaProductos() {
        lslListaProductos = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                    if (jLProductos.getModel().getSize() > 0) {
                        LimpiarCampos();
                        SeleccionarItemLista();
                    }
                }
            }
        };
    }

    protected void SeleccionarItemLista() {
        if (jLProductos.getModel().getSize() > 0) {
            jTFNombre.setText(jLProductos.getSelectedValue().getNombre());

            CTipoProducto tipoProducto = gestorBDTipoProducto.ObtenerTipoProducto(jLProductos.getSelectedValue().getIdTipoProducto());
            jTFTipoProducto.setText(tipoProducto.getTipoProducto());

            //CMarca marca = gestorBDMarca.ObtenerMarca(jLProductos.getSelectedValue().getIdMarca());
            CMarca marca = jLProductos.getSelectedValue().getMarca();
            jTFMarca.setText(marca.getNombre());

            jTFPeso.setText(String.valueOf(jLProductos.getSelectedValue().getPeso()));
            jTFPrecio.setText(String.valueOf(jLProductos.getSelectedValue().getPrecio()));
            jTFUnidad.setText(String.valueOf(jLProductos.getSelectedValue().getUnidades()));
            jTFPlatos.setText(String.valueOf(jLProductos.getSelectedValue().getPlatos()));
            jTFStockMinimo.setText(String.valueOf(jLProductos.getSelectedValue().getStockMinimo()));
            jTAIngredientes.setText(jLProductos.getSelectedValue().getIngredientes());
            jTACoccion.setText(jLProductos.getSelectedValue().getCoccion());
        }
    }

    private void cargarComboMarca() {
        modelMarca = new DefaultComboBoxModel();

        ArrayList<CMarca> marcas = gestorBDMarca.CargarComboMarcas();

        for (CMarca marca : marcas) {
            modelMarca.addElement(marca);
        }
        jCBMarca.setModel(modelMarca);
    }

    private void cargarComboTipoProducto() {
        modelTipoProducto = new DefaultComboBoxModel();

        ArrayList<CTipoProducto> tiposProducto = gestorBDTipoProducto.CargarComboTiposProductos();
        for (CTipoProducto tipoProducto : tiposProducto) {
            modelTipoProducto.addElement(tipoProducto);
        }
        jCBTipoProducto.setModel(modelTipoProducto);
    }

    private void LimpiarCampos() {
        jTFNombre.setText("");
        jCBTipoProducto.setSelectedIndex(0);
        jCBMarca.setSelectedIndex(0);
        jTFPeso.setText("");
        jTFPrecio.setText("");
        jTFUnidad.setText("");
        jTFPlatos.setText("");
        jTFStockMinimo.setText("");
        jTAIngredientes.setText("");
        jTACoccion.setText("");
    }

    private void HabilitarDeshabilitarCampos(boolean x) {
        jTFNombre.setEnabled(x);
        jCBTipoProducto.setEnabled(x);
        jTFTipoProducto.setEnabled(x);
        jCBMarca.setEnabled(x);
        jTFMarca.setEnabled(x);
        jTFPeso.setEnabled(x);
        jTFPrecio.setEnabled(x);
        jTFUnidad.setEnabled(x);
        jTFPlatos.setEnabled(x);
        jTFStockMinimo.setEnabled(x);
        jTAIngredientes.setEnabled(x);
        jTACoccion.setEnabled(x);
        jLProductos.setEnabled(x);
    }

    private void HabilitarDeshabilitarBotones(boolean x) {
        jBAgregar.setEnabled(x);
        jBEditar.setEnabled(x);
        jBBorrar.setEnabled(x);
        jBGuardar.setEnabled(x);
        jBCancelar.setEnabled(x);
    }

    private void VerTipoProductoMarca(boolean x) {
        jTFTipoProducto.setVisible(x);
        jTFMarca.setVisible(x);
        jCBTipoProducto.setVisible(!x);
        jCBMarca.setVisible(!x);
    }

//Validar numeros y letras, agregar mensaje si no quiere agregarlo, siin coccion, sin vacio algunos
    private boolean validar() {
        if (jTFNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el Nombre del Producto",
                    "Advertencia - Nona Mafalda",
                    JOptionPane.WARNING_MESSAGE);
            jTFNombre.requestFocus();
            return false;
        } else {
            if (!jTFPeso.getText().equals("")) {
                if (Double.parseDouble(jTFPeso.getText()) > 999) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Peso del Producto Incorrecto",
                            "Advertencia - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    jTFPeso.requestFocus();
                    return false;
                }
            } else {
                if (jTFPrecio.getText().equals("")
                        || Double.parseDouble(jTFPrecio.getText()) > 999999) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Precio del Producto Incorrecto",
                            "Advertencia - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    jTFPrecio.requestFocus();
                    return false;
                } else {
                    if (!jTFUnidad.getText().equals("")) {
                        if (Integer.parseInt(jTFUnidad.getText()) > 999) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Unidad del Producto Incorrecta",
                                    "Advertencia - Nona Mafalda",
                                    JOptionPane.WARNING_MESSAGE);
                            jTFUnidad.requestFocus();
                            return false;
                        }
                    } else {
                        if (!jTFPlatos.getText().equals("")) {
                            if (Integer.parseInt(jTFPlatos.getText()) > 999) {
                                JOptionPane.showMessageDialog(
                                        this,
                                        "Cantidad de Platos Ingresados Incorrecto",
                                        "Advertencia - Nona Mafalda",
                                        JOptionPane.WARNING_MESSAGE);
                                jTFPlatos.requestFocus();
                                return false;
                            }
                        } else {
                            if (jTFStockMinimo.getText().equals("")
                                    || Integer.parseInt(jTFStockMinimo.getText()) > 999) {
                                JOptionPane.showMessageDialog(
                                        this,
                                        "Stock Mínimo Icorrecto",
                                        "Advertencia - Nona Mafalda",
                                        JOptionPane.WARNING_MESSAGE);
                                jTFStockMinimo.requestFocus();
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private void ValidacionCampos() {
        ValidacionesCampos.MayusculasMinusculasNumerosEspaciosBorrar40(jTFNombre);
        ValidacionesCampos.PesoCantidad(jTFPeso);
        ValidacionesCampos.Precio(jTFPrecio);
        ValidacionesCampos.UnidadesPlatosVisitas(jTFUnidad);
        ValidacionesCampos.UnidadesPlatosVisitas(jTFPlatos);
        ValidacionesCampos.PesoCantidad(jTFStockMinimo);
        ValidacionesCampos.ValidacionTextArea(jTAIngredientes);
        ValidacionesCampos.ValidacionTextArea(jTACoccion);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTFNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jBAgregar = new javax.swing.JButton();
        jBGuardar = new javax.swing.JButton();
        jBEditar = new javax.swing.JButton();
        jBBorrar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jCBTipoProducto = new javax.swing.JComboBox<>();
        jTFUnidad = new javax.swing.JTextField();
        jTFStockMinimo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jCBMarca = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTAIngredientes = new javax.swing.JTextArea();
        jTFPlatos = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTACoccion = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLProductos = new javax.swing.JList<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTFMarca = new javax.swing.JTextField();
        jTFTipoProducto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTFPrecio = new javax.swing.JTextField();
        jTFPeso = new javax.swing.JTextField();

        setBackground(new java.awt.Color(0, 0, 0));
        setForeground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        setPreferredSize(new java.awt.Dimension(1280, 695));
        setLayout(null);

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre:");
        add(jLabel2);
        jLabel2.setBounds(50, 110, 100, 60);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("$");
        add(jLabel3);
        jLabel3.setBounds(190, 360, 40, 50);

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Platos (Opc)");
        add(jLabel4);
        jLabel4.setBounds(50, 480, 140, 60);

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Unidad/es (Opc)");
        add(jLabel6);
        jLabel6.setBounds(50, 420, 140, 60);

        jTFNombre.setBackground(new java.awt.Color(0, 0, 0));
        jTFNombre.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFNombre.setForeground(new java.awt.Color(255, 255, 255));
        jTFNombre.setToolTipText("Nombre del Producto");
        jTFNombre.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFNombre.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFNombre.setNextFocusableComponent(jCBTipoProducto);
        jTFNombre.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFNombre.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFNombre);
        jTFNombre.setBounds(230, 120, 240, 50);

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Precio:");
        add(jLabel7);
        jLabel7.setBounds(50, 360, 100, 60);

        jBAgregar.setBackground(new java.awt.Color(0, 0, 0));
        jBAgregar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBAgregar.setForeground(new java.awt.Color(255, 255, 255));
        jBAgregar.setText("Agregar");
        jBAgregar.setToolTipText("Agregar Producto");
        jBAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBAgregar.setNextFocusableComponent(jBEditar);
        jBAgregar.setPreferredSize(new java.awt.Dimension(172, 58));
        jBAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarActionPerformed(evt);
            }
        });
        add(jBAgregar);
        jBAgregar.setBounds(50, 630, 170, 50);

        jBGuardar.setBackground(new java.awt.Color(0, 0, 0));
        jBGuardar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBGuardar.setForeground(new java.awt.Color(255, 255, 255));
        jBGuardar.setText("Guardar");
        jBGuardar.setToolTipText("Guardar Producto");
        jBGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBGuardar.setNextFocusableComponent(jBCancelar);
        jBGuardar.setPreferredSize(new java.awt.Dimension(172, 58));
        jBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarActionPerformed(evt);
            }
        });
        add(jBGuardar);
        jBGuardar.setBounds(520, 630, 170, 50);

        jBEditar.setBackground(new java.awt.Color(0, 0, 0));
        jBEditar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBEditar.setForeground(new java.awt.Color(255, 255, 255));
        jBEditar.setText("Editar");
        jBEditar.setToolTipText("Editar Producto Seleccionado");
        jBEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBEditar.setNextFocusableComponent(jBBorrar);
        jBEditar.setPreferredSize(new java.awt.Dimension(172, 58));
        jBEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEditarActionPerformed(evt);
            }
        });
        add(jBEditar);
        jBEditar.setBounds(230, 630, 130, 50);

        jBBorrar.setBackground(new java.awt.Color(0, 0, 0));
        jBBorrar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBBorrar.setForeground(new java.awt.Color(255, 255, 255));
        jBBorrar.setText("Borrar");
        jBBorrar.setToolTipText("Borrar Producto Seleccionado");
        jBBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBorrar.setNextFocusableComponent(jBGuardar);
        jBBorrar.setPreferredSize(new java.awt.Dimension(172, 58));
        jBBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBorrarActionPerformed(evt);
            }
        });
        add(jBBorrar);
        jBBorrar.setBounds(370, 630, 140, 50);

        jBCancelar.setBackground(new java.awt.Color(0, 0, 0));
        jBCancelar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBCancelar.setForeground(new java.awt.Color(255, 255, 255));
        jBCancelar.setText("Cancelar");
        jBCancelar.setToolTipText("Cancelar Operacion");
        jBCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBCancelar.setNextFocusableComponent(jLProductos);
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });
        add(jBCancelar);
        jBCancelar.setBounds(700, 630, 180, 50);

        jCBTipoProducto.setBackground(new java.awt.Color(0, 0, 0));
        jCBTipoProducto.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCBTipoProducto.setForeground(new java.awt.Color(255, 255, 255));
        jCBTipoProducto.setToolTipText("Seleccione el Tipo de Producto");
        jCBTipoProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jCBTipoProducto.setName(""); // NOI18N
        jCBTipoProducto.setNextFocusableComponent(jCBMarca);
        add(jCBTipoProducto);
        jCBTipoProducto.setBounds(230, 180, 240, 50);

        jTFUnidad.setBackground(new java.awt.Color(0, 0, 0));
        jTFUnidad.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFUnidad.setForeground(new java.awt.Color(255, 255, 255));
        jTFUnidad.setToolTipText("Ingrese las Unidades que contiene el Producto");
        jTFUnidad.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFUnidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFUnidad.setNextFocusableComponent(jTFPlatos);
        jTFUnidad.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFUnidad.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFUnidad);
        jTFUnidad.setBounds(230, 420, 240, 50);

        jTFStockMinimo.setBackground(new java.awt.Color(0, 0, 0));
        jTFStockMinimo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFStockMinimo.setForeground(new java.awt.Color(255, 255, 255));
        jTFStockMinimo.setToolTipText("Ingrese el Stock Mínimo del Producto");
        jTFStockMinimo.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFStockMinimo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFStockMinimo.setNextFocusableComponent(jTAIngredientes);
        jTFStockMinimo.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFStockMinimo.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFStockMinimo);
        jTFStockMinimo.setBounds(230, 540, 240, 50);

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Stock Mínimo U/KG:");
        add(jLabel8);
        jLabel8.setBounds(50, 540, 170, 60);

        jCBMarca.setBackground(new java.awt.Color(0, 0, 0));
        jCBMarca.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jCBMarca.setForeground(new java.awt.Color(255, 255, 255));
        jCBMarca.setToolTipText("Seleccione la Marca");
        jCBMarca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jCBMarca.setNextFocusableComponent(jTFPeso);
        add(jCBMarca);
        jCBMarca.setBounds(230, 240, 240, 50);

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Peso en Kg (Opc)");
        add(jLabel9);
        jLabel9.setBounds(50, 300, 150, 50);

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Ingredientes (Opc)");
        add(jLabel10);
        jLabel10.setBounds(630, 110, 160, 50);

        jTAIngredientes.setBackground(new java.awt.Color(0, 0, 0));
        jTAIngredientes.setColumns(20);
        jTAIngredientes.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTAIngredientes.setForeground(new java.awt.Color(255, 255, 255));
        jTAIngredientes.setRows(5);
        jTAIngredientes.setToolTipText("Ingredientes del Producto");
        jTAIngredientes.setCaretColor(new java.awt.Color(255, 255, 255));
        jTAIngredientes.setNextFocusableComponent(jTACoccion);
        jTAIngredientes.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTAIngredientes.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane3.setViewportView(jTAIngredientes);

        add(jScrollPane3);
        jScrollPane3.setBounds(560, 160, 310, 170);

        jTFPlatos.setBackground(new java.awt.Color(0, 0, 0));
        jTFPlatos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFPlatos.setForeground(new java.awt.Color(255, 255, 255));
        jTFPlatos.setToolTipText("Ingrese la cantidad de platos que el producto puede satisfacer");
        jTFPlatos.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFPlatos.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFPlatos.setNextFocusableComponent(jTFStockMinimo);
        jTFPlatos.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFPlatos.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFPlatos);
        jTFPlatos.setBounds(230, 480, 240, 50);

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Cocción (Opc)");
        add(jLabel11);
        jLabel11.setBounds(650, 350, 130, 40);

        jTACoccion.setBackground(new java.awt.Color(0, 0, 0));
        jTACoccion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTACoccion.setForeground(new java.awt.Color(255, 255, 255));
        jTACoccion.setToolTipText("Cocción del Producto");
        jTACoccion.setCaretColor(new java.awt.Color(255, 255, 255));
        jTACoccion.setNextFocusableComponent(jBAgregar);
        jTACoccion.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTACoccion.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane5.setViewportView(jTACoccion);

        add(jScrollPane5);
        jScrollPane5.setBounds(560, 400, 310, 170);

        jLProductos.setBackground(new java.awt.Color(0, 0, 0));
        jLProductos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLProductos.setForeground(new java.awt.Color(255, 255, 255));
        jLProductos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jLProductos.setToolTipText("Listado de Productos");
        jLProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLProductos.setNextFocusableComponent(jTFNombre);
        jLProductos.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jLProductos.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jLProductos);

        add(jScrollPane1);
        jScrollPane1.setBounds(970, 10, 300, 640);

        jLabel13.setBackground(new java.awt.Color(0, 0, 0));
        jLabel13.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Marca:");
        add(jLabel13);
        jLabel13.setBounds(50, 240, 100, 60);

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Tipo de Producto:");
        add(jLabel16);
        jLabel16.setBounds(50, 180, 160, 60);

        jTFMarca.setBackground(new java.awt.Color(0, 0, 0));
        jTFMarca.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFMarca.setForeground(new java.awt.Color(255, 255, 255));
        jTFMarca.setToolTipText("Marca");
        jTFMarca.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFMarca.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFMarca.setNextFocusableComponent(jTFPeso);
        jTFMarca.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFMarca.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFMarca);
        jTFMarca.setBounds(230, 240, 240, 50);

        jTFTipoProducto.setBackground(new java.awt.Color(0, 0, 0));
        jTFTipoProducto.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFTipoProducto.setForeground(new java.awt.Color(255, 255, 255));
        jTFTipoProducto.setToolTipText("Tipo de Producto");
        jTFTipoProducto.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFTipoProducto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFTipoProducto.setNextFocusableComponent(jCBMarca);
        jTFTipoProducto.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFTipoProducto.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFTipoProducto);
        jTFTipoProducto.setBounds(230, 180, 240, 50);

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Listado Productos");
        add(jLabel5);
        jLabel5.setBounds(980, 650, 290, 50);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Productos");
        add(jLabel1);
        jLabel1.setBounds(380, 30, 230, 56);

        jTFPrecio.setBackground(new java.awt.Color(0, 0, 0));
        jTFPrecio.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFPrecio.setForeground(new java.awt.Color(255, 255, 255));
        jTFPrecio.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFPrecio.setNextFocusableComponent(jTFUnidad);
        jTFPrecio.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFPrecio.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFPrecio);
        jTFPrecio.setBounds(230, 360, 240, 50);

        jTFPeso.setBackground(new java.awt.Color(0, 0, 0));
        jTFPeso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFPeso.setForeground(new java.awt.Color(255, 255, 255));
        jTFPeso.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFPeso.setNextFocusableComponent(jTFPrecio);
        jTFPeso.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFPeso.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFPeso);
        jTFPeso.setBounds(230, 300, 240, 50);
    }// </editor-fold>//GEN-END:initComponents

    private void jBAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarActionPerformed
        // TODO add your handling code here:
        if (jCBMarca.getModel().getSize() > 0 && jCBTipoProducto.getModel().getSize() > 0) {
            banderaAgregarEditar = true;
            LimpiarCampos();

            VerTipoProductoMarca(false);

            HabilitarDeshabilitarCampos(true);
            jLProductos.setEnabled(false);

            HabilitarDeshabilitarBotones(false);
            jBGuardar.setEnabled(true);
            jBCancelar.setEnabled(true);

            jTFNombre.requestFocus();

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe cargar primero Tipos de Productos y Marcas",
                    "Advertencia - Nona Mafalda",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jBAgregarActionPerformed

    private void jBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarActionPerformed
        // TODO add your handling code here:
        if (validar()) {

            int idProducto;

            if (banderaAgregarEditar) {
                idProducto = -1;
            } else {
                idProducto = jLProductos.getSelectedValue().getIdProducto();
            }

            String nombre = jTFNombre.getText();

            CTipoProducto tipoProducto = (CTipoProducto) jCBTipoProducto.getSelectedItem();
            int idTipoProducto = tipoProducto.getIdTipoProducto();

            CMarca marca = (CMarca) jCBMarca.getSelectedItem();
            //int idMarca = marca.getId_Marca();

            Double peso = 0.0;
            if (jTFPeso.getText().equals("")) {
                peso = 0.0;
            } else {
                peso = Double.parseDouble(jTFPeso.getText());
            }

            double precio = Double.parseDouble(jTFPrecio.getText());

            Integer unidades = 0;
            if (jTFUnidad.getText().equals("")) {
                unidades = 0;
            } else {
                unidades = Integer.valueOf(jTFUnidad.getText());
            }

            Integer platos = 0;
            if (jTFPlatos.getText().equals("")) {
                platos = 0;
            } else {
                platos = Integer.valueOf(jTFPlatos.getText());

            }

            String ingredientes = null;
            if (jTAIngredientes.getText().equals("")) {
                ingredientes = null;
            } else {
                ingredientes = jTAIngredientes.getText();
            }

            String coccion = null;

            if (jTACoccion.getText().equals("")) {
                coccion = null;
            } else {
                coccion = jTACoccion.getText();
            }

            int stockMinimo = Integer.valueOf(jTFStockMinimo.getText());

            CProducto producto = new CProducto(idProducto, marca, idTipoProducto, nombre, peso,
                    precio, unidades, platos, ingredientes, coccion, stockMinimo, true);

            if (banderaAgregarEditar) {
                if (gestorBDProducto.ExisteNombreProductoAgregar(producto)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "¡Nombre de Producto Repetido!",
                            "Advertencia - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    jTFNombre.requestFocus();
                    return;
                }
            } else {
                if (gestorBDProducto.ExisteNombreProductoEditar(producto)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "¡Nombre de Producto Repetido!",
                            "Advertencia - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    jTFNombre.requestFocus();
                    return;
                }
            }

            boolean exito = false;

            if (idProducto == -1) {
                exito = gestorBDProducto.AgregarProducto(producto);
            } else {
                try {
                    exito = gestorBDProducto.ModificarProducto(producto);
                } catch (SQLException ex) {
                    Logger.getLogger(PProducto.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (exito) {

                CargarLista();

                for (int i = 0; i < jLProductos.getModel().getSize(); i++) {
                    if (jLProductos.getModel().getElementAt(i).getNombre().equals(nombre)) {
                        jLProductos.setSelectedIndex(i);
                    }
                }

                JOptionPane.showMessageDialog(
                        this,
                        "El Producto se guardó Correctamente",
                        "Exito - Nona Mafalda",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBGuardarActionPerformed

    private void jBEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEditarActionPerformed
        // TODO add your handling code here:
        if (jCBMarca.getModel().getSize() > 0 && jCBTipoProducto.getModel().getSize() > 0) {
            banderaAgregarEditar = false;

            VerTipoProductoMarca(false);

            HabilitarDeshabilitarCampos(true);
            jLProductos.setEnabled(false);

            HabilitarDeshabilitarBotones(false);
            jBGuardar.setEnabled(true);
            jBCancelar.setEnabled(true);

            jTFNombre.requestFocus();

            boolean banderaTipoProducto = false;
            CTipoProducto tipoProducto = gestorBDTipoProducto.ObtenerTipoProducto(jLProductos.getSelectedValue().getIdTipoProducto());
            for (int i = 0; i < jCBTipoProducto.getItemCount(); i++) {
                if (jCBTipoProducto.getItemAt(i).getIdTipoProducto() == tipoProducto.getIdTipoProducto()) {
                    jCBTipoProducto.setSelectedIndex(i);
                    banderaTipoProducto = true;
                }
            }

            if (!banderaTipoProducto) {
                jCBTipoProducto.setSelectedIndex(0);
            }

            boolean banderaMarca = false;
            CMarca marca = gestorBDMarca.ObtenerMarca(jLProductos.getSelectedValue().getMarca().getId_Marca());
            for (int i = 0; i < jCBMarca.getItemCount(); i++) {
                if (jCBMarca.getItemAt(i).getId_Marca() == marca.getId_Marca()) {
                    jCBMarca.setSelectedIndex(i);
                    banderaMarca = true;
                }
            }

            if (!banderaMarca) {
                jCBMarca.setSelectedIndex(0);
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe cargar primero Tipos de Productos y Marcas",
                    "Advertencia - Nona Mafalda",
                    JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_jBEditarActionPerformed

    private void jBBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBorrarActionPerformed
        // TODO add your handling code here:
        if (jLProductos.getModel().getSize() > 0) {

            if (JOptionPane.showConfirmDialog(
                    this,
                    "Esta seguro?",
                    "Eliminando Producto - Nona Mafalda",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                // yes option
                int idProducto = jLProductos.getSelectedValue().getIdProducto();
                boolean exito = false;

                try {
                    exito = gestorBDProducto.EliminarProducto(idProducto);
                } catch (SQLException ex) {
                    Logger.getLogger(PProducto.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                if (exito) {
                    JOptionPane.showMessageDialog(
                            this,
                            "El Producto se eliminó correctamente.",
                            "Exito - Nona Mafalda",
                            JOptionPane.INFORMATION_MESSAGE);

                    CargarLista();

                } else {
                    JOptionPane.showConfirmDialog(
                            this,
                            "Intentelo Nuevamente.",
                            "Error al Eliminar el Producto - Nona Mafalda",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jBBorrarActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        // TODO add your handling code here:

        VerTipoProductoMarca(true);

        HabilitarDeshabilitarCampos(false);
        jLProductos.setEnabled(true);

        HabilitarDeshabilitarBotones(true);
        jBGuardar.setEnabled(false);
        jBCancelar.setEnabled(false);

        if (jLProductos.getModel().getSize() > 0) {
            jLProductos.setSelectedIndex(jLProductos.getSelectedIndex());
            SeleccionarItemLista();
        } else {
            jBBorrar.setEnabled(false);
            jBEditar.setEnabled(false);

            LimpiarCampos();
        }

    }//GEN-LAST:event_jBCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAgregar;
    private javax.swing.JButton jBBorrar;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBEditar;
    private javax.swing.JButton jBGuardar;
    private javax.swing.JComboBox<CMarca> jCBMarca;
    private javax.swing.JComboBox<TipoProducto.CTipoProducto> jCBTipoProducto;
    private javax.swing.JList<Producto.CProducto> jLProductos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea jTACoccion;
    private javax.swing.JTextArea jTAIngredientes;
    private javax.swing.JTextField jTFMarca;
    private javax.swing.JTextField jTFNombre;
    private javax.swing.JTextField jTFPeso;
    private javax.swing.JTextField jTFPlatos;
    private javax.swing.JTextField jTFPrecio;
    private javax.swing.JTextField jTFStockMinimo;
    private javax.swing.JTextField jTFTipoProducto;
    private javax.swing.JTextField jTFUnidad;
    // End of variables declaration//GEN-END:variables
}
