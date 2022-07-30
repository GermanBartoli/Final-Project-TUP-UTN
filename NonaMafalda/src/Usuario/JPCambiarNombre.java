/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Usuario;

import Modelos.TextPrompt;
import Usuario.GBDUsuario;
import Usuario.CUsuario;
import Modelos.ValidacionesCampos;
import UI.JFLogin;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author German Bartoli
 */
public class JPCambiarNombre extends javax.swing.JPanel {

    static CUsuario usuario;
    private GBDUsuario gestorBDUsuario;

    /**
     * Creates new form JPCambiarNombre
     */
    public JPCambiarNombre(CUsuario usuario) {
        initComponents();

        this.usuario = usuario;

        gestorBDUsuario = new GBDUsuario();

        ValidacionCampos();

        jTFNombreActual.setTransferHandler(null);
        jTFNombreNuevo.setTransferHandler(null);
        jTFNombreConfirmar.setTransferHandler(null);

        TextPrompt placeHolderNombreActual = new TextPrompt("Nombre Actual", jTFNombreActual, TextPrompt.Show.ALWAYS);
        TextPrompt placeHolderNombreNuevo = new TextPrompt("Nombre Nuevo", jTFNombreNuevo, TextPrompt.Show.ALWAYS);
        TextPrompt placeHolderNombreConfirmar = new TextPrompt("Confirmar Nombre", jTFNombreConfirmar, TextPrompt.Show.ALWAYS);
    }

    private boolean validar() {
        if (jTFNombreActual.getText().equals("")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar su Nombre Actual",
                    "Advertencia - Nona Mafalda",
                    JOptionPane.WARNING_MESSAGE);
            jTFNombreActual.requestFocus();
            return false;
        } else {
            if (jTFNombreNuevo.getText().equals("")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar un Nuevo Nombre de Usuario.",
                        "Advertencia - Nona Mafalda",
                        JOptionPane.WARNING_MESSAGE);
                jTFNombreNuevo.requestFocus();
                return false;
            } else {
                if (jTFNombreConfirmar.getText().equals("")) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Debe Confirmar su Nuevo Nombre de Usuario.",
                            "Advertencia - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    jTFNombreConfirmar.requestFocus();
                    return false;
                } else {
                    if (!usuario.getNombre().equals(jTFNombreActual.getText())) {
                        JOptionPane.showMessageDialog(
                                this,
                                "¡El nombre de usuario actual es incorrecto!",
                                "Error - Nona Mafalda",
                                JOptionPane.ERROR_MESSAGE);
                        jTFNombreActual.setText("");
                        jTFNombreActual.requestFocus();
                        return false;
                    } else {
                        if (jTFNombreActual.equals(jTFNombreNuevo)) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "¡El nombre de usuario actual y el nuevo coinciden!",
                                    "Error - Nona Mafalda",
                                    JOptionPane.ERROR_MESSAGE);
                            limpiarCampos();
                            jTFNombreActual.requestFocus();
                            return false;
                        } else {
                            if (!jTFNombreNuevo.getText().equals(jTFNombreConfirmar.getText())) {
                                JOptionPane.showMessageDialog(
                                        this,
                                        "¡El nuevo nombre de usuario y su confirmación no coinciden!",
                                        "Error - Nona Mafalda",
                                        JOptionPane.WARNING_MESSAGE);
                                jTFNombreNuevo.setText("");
                                jTFNombreConfirmar.setText("");
                                jTFNombreNuevo.requestFocus();
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
    }

    private void limpiarCampos() {
        jTFNombreActual.setText("");
        jTFNombreNuevo.setText("");
        jTFNombreConfirmar.setText("");
    }

    private void ValidacionCampos() {
        ValidacionesCampos.NombreUsuario(jTFNombreActual);
        ValidacionesCampos.NombreUsuario(jTFNombreNuevo);
        ValidacionesCampos.NombreUsuario(jTFNombreConfirmar);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCambiarNombre = new javax.swing.JButton();
        jTFNombreActual = new javax.swing.JTextField();
        jTFNombreNuevo = new javax.swing.JTextField();
        jTFNombreConfirmar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLImagen = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1280, 695));
        setLayout(null);

        btnCambiarNombre.setBackground(new java.awt.Color(0, 0, 0));
        btnCambiarNombre.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        btnCambiarNombre.setForeground(new java.awt.Color(255, 255, 255));
        btnCambiarNombre.setText("Aplicar");
        btnCambiarNombre.setToolTipText("Aplicar Cambios");
        btnCambiarNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCambiarNombre.setNextFocusableComponent(jTFNombreActual);
        btnCambiarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarNombreActionPerformed(evt);
            }
        });
        add(btnCambiarNombre);
        btnCambiarNombre.setBounds(547, 455, 136, 75);

        jTFNombreActual.setBackground(new java.awt.Color(0, 0, 0));
        jTFNombreActual.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTFNombreActual.setForeground(new java.awt.Color(255, 255, 255));
        jTFNombreActual.setToolTipText("Nombre Actual");
        jTFNombreActual.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFNombreActual.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFNombreActual.setNextFocusableComponent(jTFNombreNuevo);
        jTFNombreActual.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFNombreActual.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFNombreActual);
        jTFNombreActual.setBounds(470, 190, 310, 60);

        jTFNombreNuevo.setBackground(new java.awt.Color(0, 0, 0));
        jTFNombreNuevo.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTFNombreNuevo.setForeground(new java.awt.Color(255, 255, 255));
        jTFNombreNuevo.setToolTipText("Nombre Nuevo");
        jTFNombreNuevo.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFNombreNuevo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFNombreNuevo.setNextFocusableComponent(jTFNombreConfirmar);
        jTFNombreNuevo.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFNombreNuevo.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFNombreNuevo);
        jTFNombreNuevo.setBounds(470, 270, 310, 60);

        jTFNombreConfirmar.setBackground(new java.awt.Color(0, 0, 0));
        jTFNombreConfirmar.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jTFNombreConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        jTFNombreConfirmar.setToolTipText("Confirmar Nombre Nuevo");
        jTFNombreConfirmar.setCaretColor(new java.awt.Color(255, 255, 255));
        jTFNombreConfirmar.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTFNombreConfirmar.setNextFocusableComponent(btnCambiarNombre);
        jTFNombreConfirmar.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jTFNombreConfirmar.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jTFNombreConfirmar);
        jTFNombreConfirmar.setBounds(470, 360, 310, 60);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Arial", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cambiar Nombre");
        add(jLabel1);
        jLabel1.setBounds(440, 40, 390, 56);

        jLImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/User.png"))); // NOI18N
        add(jLImagen);
        jLImagen.setBounds(595, 108, 50, 50);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCambiarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarNombreActionPerformed
        // TODO add your handling code here:
        if (validar()) {
            int option = JOptionPane.showConfirmDialog(
                    null,
                    "¿Está Seguro? Se reiniciara el Software",
                    "Cambiando Nombre",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {
                usuario.setNombre(jTFNombreNuevo.getText());

                if (gestorBDUsuario.ExisteUsuarioNombre(usuario)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "¡El nombre de Usuario esta en uso!",
                            "Error - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    jTFNombreNuevo.setText("");
                    jTFNombreConfirmar.setText("");
                    jTFNombreNuevo.requestFocus();
                    return;
                }

                Boolean exito = false;

                try {
                    exito = gestorBDUsuario.ModificarNombre(usuario);
                } catch (SQLException ex) {
                    Logger.getLogger(JPCambiarNombre.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (exito) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Operación realizada con Éxito!",
                            "Cambio Nombre de Usuario - Nona Mafalda",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);

                    JFLogin jFLogin = new JFLogin();
                    return;
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Se produjo un Error. \n"
                            + "Intentelo Nuevamente!",
                            "Cambio Nombre de Usuario - Nona Mafalda",
                            JOptionPane.ERROR_MESSAGE);
                    limpiarCampos();
                    jTFNombreActual.requestFocus();
                    return;
                }
            }

        }
    }//GEN-LAST:event_btnCambiarNombreActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarNombre;
    private javax.swing.JLabel jLImagen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTFNombreActual;
    private javax.swing.JTextField jTFNombreConfirmar;
    private javax.swing.JTextField jTFNombreNuevo;
    // End of variables declaration//GEN-END:variables
}
