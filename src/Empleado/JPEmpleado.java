/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Empleado;

import Empleado.CEmpleado;
import Modelos.TextPrompt;
import Modelos.ValidacionesCampos;
import Persona.GBDPersona;
import Usuario.CUsuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author German Bartoli
 */
public class JPEmpleado extends javax.swing.JPanel {

    CUsuario usuario;

    private final GBDPersona gBDPersona;
    private final GBDEmpleado gBDEmpleado;

    private DefaultListModel<CEmpleado> listModel;

    private ArrayList<CEmpleado> arrayListEmpleados;
    private ListSelectionListener lslEmpleados;

    boolean banderaAgregarEditar;

    /**
     * Creates new form JPEmpleado
     */
    public JPEmpleado(CUsuario usuario) {
        initComponents();

        this.usuario = usuario;

        gBDPersona = new GBDPersona();
        gBDEmpleado = new GBDEmpleado();

        CrearListSelectionListenerListaEmpleados();

        CargarLista();

                TextPrompt placeHolderDNI = new TextPrompt(
                "Solo Números",
                jTFDni,
                TextPrompt.Show.ALWAYS);

        TextPrompt placeHolderEdad = new TextPrompt(
                "Solo Números",
                jTFEdad,
                TextPrompt.Show.ALWAYS);

        TextPrompt placeHolderTel = new TextPrompt(
                "Solo Números",
                jTFTel,
                TextPrompt.Show.ALWAYS);
        
        //No copy/paste
        jTFDni.setTransferHandler(null);
        jTFNombre.setTransferHandler(null);
        jTFApellido.setTransferHandler(null);
        jTFEdad.setTransferHandler(null);
        jTFCorreo.setTransferHandler(null);
        jTFTel.setTransferHandler(null);

        ValidacionCampos();
    }

    public void CargarLista() {
        arrayListEmpleados = gBDEmpleado.CargarListaEmpleados();

        jLEmpleados.removeListSelectionListener(lslEmpleados);

        //Crear un objeto DefaultListModel
        listModel = new DefaultListModel<CEmpleado>();

        //Recorrer el contenido del ArrayList
        for (int i = 0; i < arrayListEmpleados.size(); i++) {
            //Añadir cada elemento del ArrayList en el modelo de la lista
            listModel.add(i, arrayListEmpleados.get(i));
        }
        //Asociar el modelo de lista al JList
        jLEmpleados.setModel(listModel);

        jLEmpleados.addListSelectionListener(lslEmpleados);

        if (jLEmpleados.getModel().getSize() > 0) {
            HabilitarDeshabilitarCampos(false);
            jLEmpleados.setEnabled(true);

            HabilitarDeshabilitarBotones(true);
            jBGuardar.setEnabled(false);
            jBCancelar.setEnabled(false);

            jLEmpleados.setSelectedIndex(0);
        } else {
            HabilitarDeshabilitarCampos(false);

            HabilitarDeshabilitarBotones(false);
            jBAgregar.setEnabled(true);

            LimpiarCampos();
        }
    }

    private void CrearListSelectionListenerListaEmpleados() {
        lslEmpleados = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting()) {
                    if (jLEmpleados.getModel().getSize() > 0) {
                        LimpiarCampos();
                        SeleccionarItemLista();
                    }
                }
            }
        };
    }

    protected void SeleccionarItemLista() {
        if (jLEmpleados.getModel().getSize() > 0) {
            jTFDni.setText(Integer.toString(jLEmpleados.getSelectedValue().getDni()));
            jTFNombre.setText(jLEmpleados.getSelectedValue().getNombre());
            jTFApellido.setText(jLEmpleados.getSelectedValue().getApellido());
            jTFEdad.setText(String.valueOf(jLEmpleados.getSelectedValue().getEdad()));
            jTFCorreo.setText(jLEmpleados.getSelectedValue().getCorreo());
            jTFTel.setText(jLEmpleados.getSelectedValue().getTel());
        }
    }

    private void LimpiarCampos() {
        jTFDni.setText("");
        jTFNombre.setText("");
        jTFApellido.setText("");
        jTFEdad.setText("");
        jTFCorreo.setText("");
        jTFTel.setText("");
    }

    private void HabilitarDeshabilitarCampos(boolean x) {
        jTFDni.setEnabled(x);
        jTFNombre.setEnabled(x);
        jTFApellido.setEnabled(x);
        jTFEdad.setEnabled(x);
        jTFCorreo.setEnabled(x);
        jTFTel.setEnabled(x);
        jLEmpleados.setEnabled(x);
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
        jLEmpleados = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jBEditar = new javax.swing.JButton();
        jBBorrar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jBGuardar = new javax.swing.JButton();
        jBAgregar = new javax.swing.JButton();
        jTFTel = new javax.swing.JTextField();
        jTFNombre = new javax.swing.JTextField();
        jTFApellido = new javax.swing.JTextField();
        jTFEdad = new javax.swing.JTextField();
        jTFCorreo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTFDni = new javax.swing.JTextField();

        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1280, 695));
        setLayout(null);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Empleados");
        add(jLabel1);
        jLabel1.setBounds(330, 20, 250, 56);

        jLEmpleados.setBackground(new java.awt.Color(0, 0, 0));
        jLEmpleados.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        jLEmpleados.setToolTipText("Listado de Empleados");
        jLEmpleados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLEmpleados.setNextFocusableComponent(jTFDni);
        jLEmpleados.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jLEmpleados.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jLEmpleados);

        add(jScrollPane1);
        jScrollPane1.setBounds(950, 10, 320, 610);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre:");
        add(jLabel2);
        jLabel2.setBounds(190, 190, 260, 70);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Edad:");
        add(jLabel3);
        jLabel3.setBounds(190, 350, 150, 60);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Apellido:");
        add(jLabel4);
        jLabel4.setBounds(190, 270, 190, 60);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("N.º de Teléfono (Opc)");
        add(jLabel5);
        jLabel5.setBounds(190, 510, 250, 60);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Correo (Opc)");
        add(jLabel6);
        jLabel6.setBounds(190, 430, 150, 60);

        jBEditar.setBackground(new java.awt.Color(0, 0, 0));
        jBEditar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBEditar.setForeground(new java.awt.Color(255, 255, 255));
        jBEditar.setText("Editar");
        jBEditar.setToolTipText("Editar Empleado Seleccionado");
        jBEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBEditar.setMaximumSize(new java.awt.Dimension(172, 58));
        jBEditar.setMinimumSize(new java.awt.Dimension(172, 58));
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
        jBBorrar.setToolTipText("Borrar Empleado Seleccionado");
        jBBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBorrar.setMaximumSize(new java.awt.Dimension(172, 58));
        jBBorrar.setMinimumSize(new java.awt.Dimension(172, 58));
        jBBorrar.setPreferredSize(new java.awt.Dimension(172, 58));
        jBBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBorrarActionPerformed(evt);
            }
        });
        add(jBBorrar);
        jBBorrar.setBounds(360, 630, 140, 50);

        jBCancelar.setBackground(new java.awt.Color(0, 0, 0));
        jBCancelar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBCancelar.setForeground(new java.awt.Color(255, 255, 255));
        jBCancelar.setText("Cancelar");
        jBCancelar.setToolTipText("Cancelar Operacion");
        jBCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });
        add(jBCancelar);
        jBCancelar.setBounds(690, 630, 180, 50);

        jBGuardar.setBackground(new java.awt.Color(0, 0, 0));
        jBGuardar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBGuardar.setForeground(new java.awt.Color(255, 255, 255));
        jBGuardar.setText("Guardar");
        jBGuardar.setToolTipText("Guardar Empleado");
        jBGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBGuardar.setMaximumSize(new java.awt.Dimension(172, 58));
        jBGuardar.setMinimumSize(new java.awt.Dimension(172, 58));
        jBGuardar.setPreferredSize(new java.awt.Dimension(172, 58));
        jBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarActionPerformed(evt);
            }
        });
        add(jBGuardar);
        jBGuardar.setBounds(510, 630, 170, 50);

        jBAgregar.setBackground(new java.awt.Color(0, 0, 0));
        jBAgregar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jBAgregar.setForeground(new java.awt.Color(255, 255, 255));
        jBAgregar.setText("Agregar");
        jBAgregar.setToolTipText("Agregar Empleado");
        jBAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBAgregar.setMaximumSize(new java.awt.Dimension(172, 58));
        jBAgregar.setMinimumSize(new java.awt.Dimension(172, 58));
        jBAgregar.setPreferredSize(new java.awt.Dimension(172, 58));
        jBAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarActionPerformed(evt);
            }
        });
        add(jBAgregar);
        jBAgregar.setBounds(50, 630, 160, 50);

        jTFTel.setBackground(new java.awt.Color(0, 0, 0));
        jTFTel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFTel.setForeground(new java.awt.Color(255, 255, 255));
        jTFTel.setToolTipText("N.º de Telefono del Empleado");
        jTFTel.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFTel.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTFTel.setNextFocusableComponent(jBAgregar);
        jTFTel.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFTel.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFTel);
        jTFTel.setBounds(470, 510, 300, 60);

        jTFNombre.setBackground(new java.awt.Color(0, 0, 0));
        jTFNombre.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFNombre.setForeground(new java.awt.Color(255, 255, 255));
        jTFNombre.setToolTipText("Nombre del Empleado");
        jTFNombre.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTFNombre.setNextFocusableComponent(jTFApellido);
        jTFNombre.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFNombre.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFNombre);
        jTFNombre.setBounds(470, 190, 300, 60);

        jTFApellido.setBackground(new java.awt.Color(0, 0, 0));
        jTFApellido.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFApellido.setForeground(new java.awt.Color(255, 255, 255));
        jTFApellido.setToolTipText("Apellido del Empleado");
        jTFApellido.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFApellido.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTFApellido.setNextFocusableComponent(jTFEdad);
        jTFApellido.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFApellido.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFApellido);
        jTFApellido.setBounds(470, 270, 300, 60);

        jTFEdad.setBackground(new java.awt.Color(0, 0, 0));
        jTFEdad.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFEdad.setForeground(new java.awt.Color(255, 255, 255));
        jTFEdad.setToolTipText("Edad del Empleado");
        jTFEdad.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFEdad.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTFEdad.setNextFocusableComponent(jTFCorreo);
        jTFEdad.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFEdad.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFEdad);
        jTFEdad.setBounds(470, 350, 300, 60);

        jTFCorreo.setBackground(new java.awt.Color(0, 0, 0));
        jTFCorreo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFCorreo.setForeground(new java.awt.Color(255, 255, 255));
        jTFCorreo.setToolTipText("Correo del Empleado");
        jTFCorreo.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFCorreo.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTFCorreo.setNextFocusableComponent(jTFTel);
        jTFCorreo.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFCorreo.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFCorreo);
        jTFCorreo.setBounds(470, 430, 300, 60);

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Listado Empleados");
        add(jLabel7);
        jLabel7.setBounds(960, 630, 305, 42);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("DNI:");
        add(jLabel8);
        jLabel8.setBounds(190, 110, 180, 70);

        jTFDni.setBackground(new java.awt.Color(0, 0, 0));
        jTFDni.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTFDni.setForeground(new java.awt.Color(255, 255, 255));
        jTFDni.setToolTipText("DNI del Empleado");
        jTFDni.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFDni.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTFDni.setNextFocusableComponent(jTFNombre);
        jTFDni.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFDni.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFDni);
        jTFDni.setBounds(470, 110, 300, 60);
    }// </editor-fold>//GEN-END:initComponents

    private void jBEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEditarActionPerformed
        // TODO add your handling code here:
        if (jLEmpleados.getModel().getSize() > 0) {
            banderaAgregarEditar = false;

            HabilitarDeshabilitarCampos(true);
            jLEmpleados.setEnabled(false);

            HabilitarDeshabilitarBotones(false);
            jBGuardar.setEnabled(true);
            jBCancelar.setEnabled(true);

            jTFDni.requestFocus();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un Empleado",
                    "Advertencia - Nona Mafalda",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jBEditarActionPerformed

    private void jBBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBorrarActionPerformed
        // TODO add your handling code here:
        if (jLEmpleados.getModel().getSize() > 0) {
            int idPersona = jLEmpleados.getSelectedValue().getIdPersona();
            int idEmpleado = jLEmpleados.getSelectedValue().getIdEmpleado();

            int option;
            boolean salir = false;

            if (usuario.getIdEmpleado() != idEmpleado) {
                option = JOptionPane.showConfirmDialog(
                        this,
                        "Esta seguro?",
                        "Eliminando Empleado - Nona Mafalda",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

            } else {
                option = JOptionPane.showConfirmDialog(
                        this,
                        "Esta seguro? Requiere Reinicio!",
                        "Eliminando Empleado - Nona Mafalda",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                salir = true;
            }

            if (option == JOptionPane.YES_OPTION) {
                boolean exitoPersona = false;
                boolean exitoEmpleado = false;

                exitoPersona = gBDPersona.EliminarPersonaEmpleado(idPersona);
                exitoEmpleado = gBDEmpleado.EliminarEmpleado(idEmpleado);

                if (exitoPersona == true && exitoEmpleado == true) {
                    if (salir) {
                        JOptionPane.showMessageDialog(
                                this,
                                "El Empleado se eliminó correctamente. El programa se cerrará...",
                                "Exito - Nona Mafalda",
                                JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                        return;
                    } else {
                        CargarLista();
                        JOptionPane.showMessageDialog(
                                this,
                                "El Empleado se eliminó correctamente.",
                                "Exito - Nona Mafalda",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
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
        jLEmpleados.setEnabled(true);

        HabilitarDeshabilitarBotones(true);
        jBGuardar.setEnabled(false);
        jBCancelar.setEnabled(false);

        if (jLEmpleados.getModel().getSize() > 0) {
            SeleccionarItemLista();

            jLEmpleados.setSelectedIndex(jLEmpleados.getSelectedIndex());
        } else {
            LimpiarCampos();

            jBBorrar.setEnabled(false);
            jBEditar.setEnabled(false);
        }
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarActionPerformed
        // TODO add your handling code here:
        if (validar()) {
            int idPersona;
            int idEmpleado;

            if (banderaAgregarEditar) {
                idPersona = -1;
                idEmpleado = -1;
            } else {
                idPersona = jLEmpleados.getSelectedValue().getIdPersona();
                idEmpleado = jLEmpleados.getSelectedValue().getIdEmpleado();
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

            CEmpleado cEmpleado = new CEmpleado(idPersona, dni, nombre, apellido, edad, correo, tel, true, idEmpleado, true);

            if (banderaAgregarEditar) {
                if (gBDPersona.ExisteDNIEmpleadoAgregar(cEmpleado)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "El DNI ya existe",
                            "Advertencia - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    jTFDni.requestFocus();
                    return;

                }
            } else {
                if (gBDPersona.ExisteDNIEmpleadoEditar(cEmpleado)) {
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
            boolean exitoEmpleado = false;

            if (idPersona == -1) {
                exitoPersona = gBDPersona.AgregarPersonaEmpleado(cEmpleado);
                int ultimoIDPersona = gBDPersona.ObtenerUltimoIDPersona();
                exitoEmpleado = gBDEmpleado.AgregarEmpleado(ultimoIDPersona);
            } else {
                try {
                    exitoPersona = gBDPersona.ModificarPersonaEmpleado(cEmpleado);
                    exitoEmpleado = true;
                } catch (SQLException ex) {
                    Logger.getLogger(JPEmpleado.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (exitoPersona == true && exitoEmpleado == true) {

                CargarLista();

                for (int i = 0; i < jLEmpleados.getModel().getSize(); i++) {
                    if (jLEmpleados.getModel().getElementAt(i).getNombre().equals(nombre)) {
                        jLEmpleados.setSelectedIndex(i);
                    }
                }

                JOptionPane.showMessageDialog(
                        this,
                        "El Empleado se guardó Correctamente",
                        "Exito - Nona Mafalda",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Intentelo Nuevamente",
                        "Error al Guardar el Empleado - Nona Mafalda",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jBGuardarActionPerformed

    private void jBAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarActionPerformed
        // TODO add your handling code here:
        banderaAgregarEditar = true;
        LimpiarCampos();

        HabilitarDeshabilitarCampos(true);
        jLEmpleados.setEnabled(false);

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
    private javax.swing.JList<CEmpleado> jLEmpleados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFApellido;
    private javax.swing.JTextField jTFCorreo;
    private javax.swing.JTextField jTFDni;
    private javax.swing.JTextField jTFEdad;
    private javax.swing.JTextField jTFNombre;
    private javax.swing.JTextField jTFTel;
    // End of variables declaration//GEN-END:variables
}
