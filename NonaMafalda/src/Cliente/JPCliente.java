/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Modelos.TextPrompt;
import Modelos.ValidacionesCampos;
import Persona.GBDPersona;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author German Bartoli
 */
public class JPCliente extends javax.swing.JPanel {

    private final GBDPersona gBDPersona;
    private final GBDCliente gBDCliente;

    private DefaultListModel<CCliente> defaultListModelCCliente;

    private ArrayList<CCliente> arrayListClientes;
    private ListSelectionListener lslClientes;

    boolean banderaAgregarEditar;

    /**
     * Creates new form JPCliente
     */
    public JPCliente() {
        initComponents();
        gBDPersona = new GBDPersona();
        gBDCliente = new GBDCliente();

        CrearListSelectionListenerListaClientes();

        CargarLista();

        ValidacionCampos();

        //No copy/paste
        jTFDni.setTransferHandler(null);
        jTFNombre.setTransferHandler(null);
        jTFApellido.setTransferHandler(null);
        jTFEdad.setTransferHandler(null);
        jTFCorreo.setTransferHandler(null);
        jTFTel.setTransferHandler(null);
        jTFCantidadVisitas.setTransferHandler(null);

        TextPrompt placeHolderDNI = new TextPrompt(
                "Solo Números",
                jTFDni,
                TextPrompt.Show.ALWAYS);

        TextPrompt placeHolderEdad = new TextPrompt(
                "Solo Números",
                jTFEdad,
                TextPrompt.Show.ALWAYS);

        TextPrompt placeHolderCantidadVisitas = new TextPrompt(
                "Solo Números",
                jTFCantidadVisitas,
                TextPrompt.Show.ALWAYS);

        TextPrompt placeHolderTel = new TextPrompt(
                "Solo Números",
                jTFTel,
                TextPrompt.Show.ALWAYS);

        //TextPrompt placeHolderTel = new TextPrompt("+99 9 999 999 9999", jFTFTel, TextPrompt.Show.ALWAYS);
        TextPrompt placeHolderCantVisitas = new TextPrompt("0000 - 9999", jTFCantidadVisitas, TextPrompt.Show.ALWAYS);
    }

    public void CargarLista() {
        arrayListClientes = gBDCliente.CargarListaClientes();

        jListClientes.removeListSelectionListener(lslClientes);

        //Crear un objeto DefaultListModel
        defaultListModelCCliente = new DefaultListModel<CCliente>();

        //Recorrer el contenido del ArrayList
        for (int i = 0; i < arrayListClientes.size(); i++) {
            //Añadir cada elemento del ArrayList en el modelo de la lista
            defaultListModelCCliente.add(i, arrayListClientes.get(i));
        }
        //Asociar el modelo de lista al JList
        jListClientes.setModel(defaultListModelCCliente);

        jListClientes.addListSelectionListener(lslClientes);

        if (jListClientes.getModel().getSize() > 0) {
            HabilitarDeshabilitarCampos(false);
            jListClientes.setEnabled(true);

            HabilitarDeshabilitarBotones(true);
            jBGuardar.setEnabled(false);
            jBCancelar.setEnabled(false);

            jListClientes.setSelectedIndex(0);
        } else {
            HabilitarDeshabilitarCampos(false);

            HabilitarDeshabilitarBotones(true);
            jBEditar.setEnabled(false);
            jBBorrar.setEnabled(false);
            jBGuardar.setEnabled(false);
            jBCancelar.setEnabled(false);

            LimpiarCampos();
        }
    }

    private void CrearListSelectionListenerListaClientes() {
        lslClientes = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                    if (jListClientes.getModel().getSize() > 0) {
                        LimpiarCampos();
                        SeleccionarItemLista();
                    }
                }
            }
        };
    }

    protected void SeleccionarItemLista() {
        if (jListClientes.getModel().getSize() > 0) {
            jTFDni.setText(Integer.toString(jListClientes.getSelectedValue().getDni()));
            jTFNombre.setText(jListClientes.getSelectedValue().getNombre());
            jTFApellido.setText(jListClientes.getSelectedValue().getApellido());
            jTFEdad.setText(String.valueOf(jListClientes.getSelectedValue().getEdad()));
            jTFCorreo.setText(jListClientes.getSelectedValue().getCorreo());
            jTFTel.setText(jListClientes.getSelectedValue().getTel());
            jTFCantidadVisitas.setText(String.valueOf(jListClientes.getSelectedValue().getCantidadVisitas()));
        }
    }

    private void LimpiarCampos() {
        jTFDni.setText("");
        jTFNombre.setText("");
        jTFApellido.setText("");
        jTFEdad.setText("");
        jTFCorreo.setText("");
        jTFTel.setText("");
        jTFCantidadVisitas.setText("");
    }

    private void HabilitarDeshabilitarCampos(boolean x) {
        jTFDni.setEnabled(x);
        jTFNombre.setEnabled(x);
        jTFApellido.setEnabled(x);
        jTFEdad.setEnabled(x);
        jTFCorreo.setEnabled(x);
        jTFTel.setEnabled(x);
        jTFCantidadVisitas.setEnabled(x);
        jListClientes.setEnabled(x);
    }

    private void HabilitarDeshabilitarBotones(boolean x) {
        jBAgregar.setEnabled(x);
        jBEditar.setEnabled(x);
        jBBorrar.setEnabled(x);
        jBGuardar.setEnabled(x);
        jBCancelar.setEnabled(x);
    }

    //Validar numeros y letras, agregar mensaje si no quiere agregarlo
    private boolean validar() {
        if (jTFDni.getText().equals("")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el DNI del Cliente",
                    "Advertencia - Nona Mafalda",
                    JOptionPane.WARNING_MESSAGE);
            jTFDni.requestFocus();
            return false;
        } else {
            if (jTFNombre.getText().equals("")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar el Nombre del Cliente",
                        "Advertencia - Nona Mafalda",
                        JOptionPane.WARNING_MESSAGE);
                jTFNombre.requestFocus();
                return false;
            } else {
                if (jTFApellido.getText().equals("")) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Debe ingresar el Apellido del Cliente",
                            "Advertencia - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    jTFApellido.requestFocus();
                    return false;
                } else {
                    if (jTFEdad.getText().equals("")) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Debe ingresar la Edad del Cliente",
                                "Advertencia - Nona Mafalda",
                                JOptionPane.WARNING_MESSAGE);
                        jTFEdad.requestFocus();
                        return false;
                    } else {
                        if (jTFCantidadVisitas.getText().equals("")) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Debe ingresar la Cantidad de Visitas del Cliente",
                                    "Advertencia - Nona Mafalda",
                                    JOptionPane.WARNING_MESSAGE);
                            jTFCantidadVisitas.requestFocus();
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void ValidacionCampos() {
        ValidacionesCampos.DNI(jTFDni);
        ValidacionesCampos.NombreApellido(jTFNombre);
        ValidacionesCampos.NombreApellido(jTFApellido);
        ValidacionesCampos.Edad(jTFEdad);
        ValidacionesCampos.Correo(jTFCorreo);
        ValidacionesCampos.NumTel(jTFTel);
        ValidacionesCampos.UnidadesPlatosVisitas(jTFCantidadVisitas);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListClientes = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTFNombre = new javax.swing.JTextField();
        jTFApellido = new javax.swing.JTextField();
        jTFCorreo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jBEditar = new javax.swing.JButton();
        jBBorrar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jBGuardar = new javax.swing.JButton();
        jBAgregar = new javax.swing.JButton();
        jLabelListadoClientes = new javax.swing.JLabel();
        jLabelDNI = new javax.swing.JLabel();
        jTFDni = new javax.swing.JTextField();
        jTFTel = new javax.swing.JTextField();
        jTFCantidadVisitas = new javax.swing.JTextField();
        jTFEdad = new javax.swing.JTextField();

        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1280, 695));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Clientes");
        add(jLabel1);
        jLabel1.setBounds(330, 20, 173, 56);

        jListClientes.setBackground(new java.awt.Color(0, 0, 0));
        jListClientes.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jListClientes.setForeground(new java.awt.Color(255, 255, 255));
        jListClientes.setToolTipText("Listado de Clientes");
        jListClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jListClientes.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jListClientes.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jListClientes);

        add(jScrollPane1);
        jScrollPane1.setBounds(910, 10, 350, 610);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Edad:");
        add(jLabel3);
        jLabel3.setBounds(200, 340, 90, 50);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Apellido:");
        add(jLabel4);
        jLabel4.setBounds(200, 270, 200, 50);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("N.º de Teléfono (Opc)");
        add(jLabel5);
        jLabel5.setBounds(200, 470, 240, 50);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Correo (Opc)");
        add(jLabel6);
        jLabel6.setBounds(200, 400, 150, 50);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre:");
        add(jLabel2);
        jLabel2.setBounds(200, 210, 200, 40);

        jTFNombre.setBackground(new java.awt.Color(0, 0, 0));
        jTFNombre.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFNombre.setForeground(new java.awt.Color(255, 255, 255));
        jTFNombre.setToolTipText("Nombre del Cliente");
        jTFNombre.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFNombre.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFNombre.setNextFocusableComponent(jTFApellido);
        jTFNombre.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFNombre.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFNombre);
        jTFNombre.setBounds(460, 200, 260, 50);

        jTFApellido.setBackground(new java.awt.Color(0, 0, 0));
        jTFApellido.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFApellido.setForeground(new java.awt.Color(255, 255, 255));
        jTFApellido.setToolTipText("Apellido del Cliente");
        jTFApellido.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFApellido.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFApellido.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFApellido.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFApellido);
        jTFApellido.setBounds(460, 270, 260, 50);

        jTFCorreo.setBackground(new java.awt.Color(0, 0, 0));
        jTFCorreo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFCorreo.setForeground(new java.awt.Color(255, 255, 255));
        jTFCorreo.setToolTipText("Correo del Cliente");
        jTFCorreo.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFCorreo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFCorreo.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFCorreo.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFCorreo);
        jTFCorreo.setBounds(460, 410, 260, 50);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Cantidad de Visitas:");
        add(jLabel7);
        jLabel7.setBounds(200, 540, 220, 50);

        jBEditar.setBackground(new java.awt.Color(0, 0, 0));
        jBEditar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBEditar.setForeground(new java.awt.Color(255, 255, 255));
        jBEditar.setText("Editar");
        jBEditar.setToolTipText("Editar Cliente Seleccionado");
        jBEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBEditar.setMaximumSize(new java.awt.Dimension(172, 58));
        jBEditar.setMinimumSize(new java.awt.Dimension(172, 58));
        jBEditar.setNextFocusableComponent(jBBorrar);
        jBEditar.setPreferredSize(new java.awt.Dimension(172, 58));
        jBEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEditarActionPerformed(evt);
            }
        });
        add(jBEditar);
        jBEditar.setBounds(220, 630, 130, 50);

        jBBorrar.setBackground(new java.awt.Color(0, 0, 0));
        jBBorrar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBBorrar.setForeground(new java.awt.Color(255, 255, 255));
        jBBorrar.setText("Borrar");
        jBBorrar.setToolTipText("Borrar Cliente Seleccionado");
        jBBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBorrar.setMaximumSize(new java.awt.Dimension(172, 58));
        jBBorrar.setMinimumSize(new java.awt.Dimension(172, 58));
        jBBorrar.setNextFocusableComponent(jBGuardar);
        jBBorrar.setPreferredSize(new java.awt.Dimension(172, 58));
        jBBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBorrarActionPerformed(evt);
            }
        });
        add(jBBorrar);
        jBBorrar.setBounds(360, 630, 130, 50);

        jBCancelar.setBackground(new java.awt.Color(0, 0, 0));
        jBCancelar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBCancelar.setForeground(new java.awt.Color(255, 255, 255));
        jBCancelar.setText("Cancelar");
        jBCancelar.setToolTipText("Cancelar Operación");
        jBCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBCancelar.setNextFocusableComponent(jListClientes);
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });
        add(jBCancelar);
        jBCancelar.setBounds(680, 630, 190, 50);

        jBGuardar.setBackground(new java.awt.Color(0, 0, 0));
        jBGuardar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBGuardar.setForeground(new java.awt.Color(255, 255, 255));
        jBGuardar.setText("Guardar");
        jBGuardar.setToolTipText("Guardar Cliente");
        jBGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBGuardar.setMaximumSize(new java.awt.Dimension(172, 58));
        jBGuardar.setMinimumSize(new java.awt.Dimension(172, 58));
        jBGuardar.setNextFocusableComponent(jBCancelar);
        jBGuardar.setPreferredSize(new java.awt.Dimension(172, 58));
        jBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarActionPerformed(evt);
            }
        });
        add(jBGuardar);
        jBGuardar.setBounds(500, 630, 170, 50);

        jBAgregar.setBackground(new java.awt.Color(0, 0, 0));
        jBAgregar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBAgregar.setForeground(new java.awt.Color(255, 255, 255));
        jBAgregar.setText("Agregar");
        jBAgregar.setToolTipText("Agregar Cliente");
        jBAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBAgregar.setNextFocusableComponent(jBEditar);
        jBAgregar.setPreferredSize(new java.awt.Dimension(172, 58));
        jBAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarActionPerformed(evt);
            }
        });
        add(jBAgregar);
        jBAgregar.setBounds(50, 630, 160, 50);

        jLabelListadoClientes.setBackground(new java.awt.Color(0, 0, 0));
        jLabelListadoClientes.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabelListadoClientes.setForeground(new java.awt.Color(255, 255, 255));
        jLabelListadoClientes.setText("Listado Clientes");
        add(jLabelListadoClientes);
        jLabelListadoClientes.setBounds(960, 630, 260, 42);

        jLabelDNI.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabelDNI.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDNI.setText("DNI:");
        add(jLabelDNI);
        jLabelDNI.setBounds(200, 150, 220, 40);

        jTFDni.setBackground(new java.awt.Color(0, 0, 0));
        jTFDni.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFDni.setForeground(new java.awt.Color(255, 255, 255));
        jTFDni.setToolTipText("DNI del Empleado");
        jTFDni.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFDni.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTFDni.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFDni.setNextFocusableComponent(jTFNombre);
        jTFDni.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFDni.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFDni);
        jTFDni.setBounds(460, 140, 260, 50);

        jTFTel.setBackground(new java.awt.Color(0, 0, 0));
        jTFTel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFTel.setForeground(new java.awt.Color(255, 255, 255));
        jTFTel.setToolTipText("N.º de Telefono del Empleado");
        jTFTel.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFTel.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTFTel.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFTel.setNextFocusableComponent(jTFCantidadVisitas);
        jTFTel.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFTel.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFTel);
        jTFTel.setBounds(460, 470, 260, 50);

        jTFCantidadVisitas.setBackground(new java.awt.Color(0, 0, 0));
        jTFCantidadVisitas.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFCantidadVisitas.setForeground(new java.awt.Color(255, 255, 255));
        jTFCantidadVisitas.setToolTipText("Cantidad de Visitas del Cliente");
        jTFCantidadVisitas.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFCantidadVisitas.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFCantidadVisitas.setNextFocusableComponent(jBAgregar);
        jTFCantidadVisitas.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFCantidadVisitas.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFCantidadVisitas);
        jTFCantidadVisitas.setBounds(460, 540, 260, 50);

        jTFEdad.setBackground(new java.awt.Color(0, 0, 0));
        jTFEdad.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFEdad.setForeground(new java.awt.Color(255, 255, 255));
        jTFEdad.setToolTipText("Edad del Cliente");
        jTFEdad.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFEdad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFEdad.setNextFocusableComponent(jTFCorreo);
        jTFEdad.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFEdad.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFEdad);
        jTFEdad.setBounds(460, 340, 260, 50);
    }// </editor-fold>//GEN-END:initComponents

    private void jBEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEditarActionPerformed
        // TODO add your handling code here:
        if (jListClientes.getModel().getSize() > 0) {
            banderaAgregarEditar = false;

            HabilitarDeshabilitarCampos(true);
            jListClientes.setEnabled(false);

            HabilitarDeshabilitarBotones(false);
            jBGuardar.setEnabled(true);
            jBCancelar.setEnabled(true);

            jTFDni.requestFocus();
        }
    }//GEN-LAST:event_jBEditarActionPerformed

    private void jBBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBorrarActionPerformed
        // TODO add your handling code here:
        if (jListClientes.getModel().getSize() > 0) {

            int idPersona = jListClientes.getSelectedValue().getIdPersona();
            int idCliente = jListClientes.getSelectedValue().getIdCliente();

            int option;
            boolean salir = false;

            option = JOptionPane.showConfirmDialog(
                    this,
                    "Esta seguro?",
                    "Eliminando Empleado - Nona Mafalda",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                boolean exitoPersona = false;
                boolean exitoEmpleado = false;

                exitoPersona = gBDPersona.EliminarPersonaEmpleado(idPersona);
                exitoEmpleado = gBDCliente.EliminarCliente(idCliente);

                CargarLista();

                if (exitoPersona == true && exitoEmpleado == true) {
                    JOptionPane.showMessageDialog(
                            this,
                            "El Cliente se eliminó correctamente.",
                            "Exito - Nona Mafalda",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Intentelo Nuevamente.",
                            "Error al Eliminar el Empleado - Nona Mafalda",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jBBorrarActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        // TODO add your handling code here:
        HabilitarDeshabilitarCampos(false);
        jListClientes.setEnabled(true);

        HabilitarDeshabilitarBotones(true);
        jBGuardar.setEnabled(false);
        jBCancelar.setEnabled(false);

        if (jListClientes.getModel().getSize() != 0) {
            jListClientes.setSelectedIndex(jListClientes.getSelectedIndex());
            SeleccionarItemLista();
        } else {
            jBBorrar.setEnabled(false);
            jBEditar.setEnabled(false);

            LimpiarCampos();
        }
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarActionPerformed
        // TODO add your handling code here:
        if (validar()) {
            int idPersona;
            int idCliente;

            if (banderaAgregarEditar) {
                idPersona = -1;
                idCliente = -1;
            } else {
                idPersona = jListClientes.getSelectedValue().getIdPersona();
                idCliente = jListClientes.getSelectedValue().getIdCliente();
            }

            int dni = Integer.parseInt(jTFDni.getText());

            String nombre = jTFNombre.getText();

            String apellido = jTFApellido.getText();

            int edad = Integer.parseInt(jTFEdad.getText());

            String correo = "";
            if (jTFCorreo.getText().equals("")) {
                correo = "";
            } else {
                correo = jTFCorreo.getText();
            }

            String tel = "";
            if (jTFTel.getText().equals("")) {
                tel = "";
            } else {
                tel = jTFTel.getText();
            }

            int cantidadVisitas = Integer.parseInt(jTFCantidadVisitas.getText());

            CCliente cCliente = new CCliente(idPersona, dni, nombre, apellido, edad, correo,
                    tel, true, idCliente, cantidadVisitas, true);

            if (banderaAgregarEditar) {
                if (gBDPersona.ExisteDNIClienteAgregar(cCliente)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "El DNI ya existe",
                            "Advertencia - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    jTFDni.requestFocus();
                    return;

                }
            } else {
                if (gBDPersona.ExisteDNIClienteEditar(cCliente)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "El DNI ya existe",
                            "Advertencia - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    jTFDni.requestFocus();
                    return;
                }
            }

            boolean exitoPersona = false;
            boolean exitoCliente = false;

            if (idPersona == -1) {
                exitoPersona = gBDPersona.AgregarPersonaCliente(cCliente);
                int ultimoIDPersona = gBDPersona.ObtenerUltimoIDPersona();
                exitoCliente = gBDCliente.AgregarCliente(ultimoIDPersona, cCliente.getCantidadVisitas());
            } else {
                exitoPersona = gBDPersona.ModificarPersonaCliente(cCliente);
                exitoCliente = gBDCliente.ModificarCliente(cCliente);
            }
            if (exitoPersona == true && exitoCliente == true) {

                CargarLista();

                for (int i = 0; i < jListClientes.getModel().getSize(); i++) {
                    if (jListClientes.getModel().getElementAt(i).getNombre().equals(nombre)) {
                        jListClientes.setSelectedIndex(i);
                    }
                }
                JOptionPane.showMessageDialog(
                        this,
                        "El Cliente se guardó Correctamente",
                        "Exito - Nona Mafalda",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBGuardarActionPerformed

    private void jBAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarActionPerformed
        // TODO add your handling code here:
        banderaAgregarEditar = true;
        LimpiarCampos();

        HabilitarDeshabilitarCampos(true);
        jListClientes.setEnabled(false);

        HabilitarDeshabilitarBotones(false);
        jBGuardar.setEnabled(true);
        jBCancelar.setEnabled(true);

        jTFDni.requestFocus();
    }//GEN-LAST:event_jBAgregarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAgregar;
    private javax.swing.JButton jBBorrar;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBEditar;
    private javax.swing.JButton jBGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelDNI;
    private javax.swing.JLabel jLabelListadoClientes;
    private javax.swing.JList<CCliente> jListClientes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFApellido;
    private javax.swing.JTextField jTFCantidadVisitas;
    private javax.swing.JTextField jTFCorreo;
    private javax.swing.JTextField jTFDni;
    private javax.swing.JTextField jTFEdad;
    private javax.swing.JTextField jTFNombre;
    private javax.swing.JTextField jTFTel;
    // End of variables declaration//GEN-END:variables
}
