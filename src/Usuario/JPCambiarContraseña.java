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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author German Bartoli
 */
public class JPCambiarContraseña extends javax.swing.JPanel {

    static CUsuario usuario;

    String contraseñaActual;
    String contraseñaNueva;
    String contraseñaConfirmar;

    GBDUsuario gestorBDUsuario;

    /**
     * Creates new form JPCambiarContraseña
     */
    public JPCambiarContraseña(CUsuario usuario) {
        initComponents();

        this.usuario = usuario;

        ValidacionCampos();

        //No copy/paste
        jpfContraseñaActual.setTransferHandler(null);
        jpfContraseñaNueva.setTransferHandler(null);
        jpfContraseñaConfirmar.setTransferHandler(null);

        TextPrompt placeHolderContraseñaActual = new TextPrompt("Contraseña Actual", jpfContraseñaActual, TextPrompt.Show.ALWAYS);
        TextPrompt placeHolderContraseñaNueva = new TextPrompt("Contraseña Nueva", jpfContraseñaNueva, TextPrompt.Show.ALWAYS);
        TextPrompt placeHolderContraseñaConfirmar = new TextPrompt("Confirmar Contraseña", jpfContraseñaConfirmar, TextPrompt.Show.ALWAYS);

    }

    private boolean validar() {
        contraseñaActual = String.valueOf(jpfContraseñaActual.getPassword());
        contraseñaNueva = String.valueOf(jpfContraseñaNueva.getPassword());
        contraseñaConfirmar = String.valueOf(jpfContraseñaConfirmar.getPassword());

        if (contraseñaActual.equals("")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar su Contraseña Actual",
                    "Advertencia - Nona Mafalda",
                    JOptionPane.WARNING_MESSAGE);
            jpfContraseñaActual.requestFocus();
            return false;
        } else {
            if (contraseñaNueva.equals("")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar una Contraseña Nueva.",
                        "Advertencia - Nona Mafalda",
                        JOptionPane.WARNING_MESSAGE);
                jpfContraseñaNueva.requestFocus();
                return false;
            } else {
                if (contraseñaConfirmar.equals("")) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Debe Confirmar su Contraseña Nueva.",
                            "Advertencia - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    jpfContraseñaConfirmar.requestFocus();
                    return false;
                } else {
                    if (!usuario.getContraseña().equals(contraseñaActual)) {
                        JOptionPane.showMessageDialog(
                                this,
                                "¡La Contraseña Actual es Incorrecta!",
                                "Error - Nona Mafalda",
                                JOptionPane.ERROR_MESSAGE);
                        jpfContraseñaActual.setText("");
                        jpfContraseñaActual.requestFocus();
                        return false;
                    } else {
                        if (contraseñaActual.equals(contraseñaNueva)) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "¡La Contraseña Actual y la Nueva Coinciden!",
                                    "Error - Nona Mafalda",
                                    JOptionPane.ERROR_MESSAGE);
                            limpiarCampos();
                            jpfContraseñaActual.requestFocus();
                            return false;
                        } else {
                            if (!contraseñaNueva.equals(contraseñaConfirmar)) {
                                JOptionPane.showMessageDialog(
                                        this,
                                        "¡La Contraseña Nueva y la Confirmación no Coinciden!",
                                        "Error - Nona Mafalda",
                                        JOptionPane.WARNING_MESSAGE);
                                jpfContraseñaNueva.setText("");
                                jpfContraseñaConfirmar.setText("");
                                jpfContraseñaNueva.requestFocus();
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
        jpfContraseñaActual.setText("");
        jpfContraseñaNueva.setText("");
        jpfContraseñaConfirmar.setText("");
    }

    private void ValidacionCampos() {
        ValidacionesCampos.ContraseñaUsuario(jpfContraseñaActual);
        ValidacionesCampos.ContraseñaUsuario(jpfContraseñaNueva);
        ValidacionesCampos.ContraseñaUsuario(jpfContraseñaConfirmar);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLImagenContraseña = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jpfContraseñaActual = new javax.swing.JPasswordField();
        jpfContraseñaNueva = new javax.swing.JPasswordField();
        jpfContraseñaConfirmar = new javax.swing.JPasswordField();
        btnCambiarContraseña = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1280, 695));
        setLayout(null);

        jLImagenContraseña.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Key.png"))); // NOI18N
        add(jLImagenContraseña);
        jLImagenContraseña.setBounds(600, 90, 50, 50);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Arial", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cambiar Contraseña");
        add(jLabel1);
        jLabel1.setBounds(420, 20, 450, 70);

        jpfContraseñaActual.setBackground(new java.awt.Color(0, 0, 0));
        jpfContraseñaActual.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jpfContraseñaActual.setForeground(new java.awt.Color(255, 255, 255));
        jpfContraseñaActual.setToolTipText("Contraseña Actual del Usuario Logeado");
        jpfContraseñaActual.setCaretColor(new java.awt.Color(255, 255, 255));
        jpfContraseñaActual.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jpfContraseñaActual.setNextFocusableComponent(jpfContraseñaNueva);
        jpfContraseñaActual.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jpfContraseñaActual.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jpfContraseñaActual);
        jpfContraseñaActual.setBounds(480, 170, 310, 60);

        jpfContraseñaNueva.setBackground(new java.awt.Color(0, 0, 0));
        jpfContraseñaNueva.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jpfContraseñaNueva.setForeground(new java.awt.Color(255, 255, 255));
        jpfContraseñaNueva.setToolTipText("Contraseña Nueva");
        jpfContraseñaNueva.setCaretColor(new java.awt.Color(255, 255, 255));
        jpfContraseñaNueva.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jpfContraseñaNueva.setNextFocusableComponent(jpfContraseñaConfirmar);
        jpfContraseñaNueva.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jpfContraseñaNueva.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jpfContraseñaNueva);
        jpfContraseñaNueva.setBounds(480, 250, 310, 60);

        jpfContraseñaConfirmar.setBackground(new java.awt.Color(0, 0, 0));
        jpfContraseñaConfirmar.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jpfContraseñaConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        jpfContraseñaConfirmar.setToolTipText("Debe Confirmar la Contraseña Nueva");
        jpfContraseñaConfirmar.setCaretColor(new java.awt.Color(255, 255, 255));
        jpfContraseñaConfirmar.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jpfContraseñaConfirmar.setNextFocusableComponent(btnCambiarContraseña);
        jpfContraseñaConfirmar.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jpfContraseñaConfirmar.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jpfContraseñaConfirmar);
        jpfContraseñaConfirmar.setBounds(480, 330, 310, 60);

        btnCambiarContraseña.setBackground(new java.awt.Color(0, 0, 0));
        btnCambiarContraseña.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        btnCambiarContraseña.setForeground(new java.awt.Color(255, 255, 255));
        btnCambiarContraseña.setText("Aplicar");
        btnCambiarContraseña.setToolTipText("Aplicar los Cambios");
        btnCambiarContraseña.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCambiarContraseña.setNextFocusableComponent(jpfContraseñaActual);
        btnCambiarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarContraseñaActionPerformed(evt);
            }
        });
        add(btnCambiarContraseña);
        btnCambiarContraseña.setBounds(560, 420, 140, 80);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCambiarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarContraseñaActionPerformed
        // TODO add your handling code here:
        if (validar()) {
            int option = JOptionPane.showConfirmDialog(
                    null,
                    "¿Está Seguro? Requiere reiniciar el software",
                    "Cambiando Contraseña",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (option == JOptionPane.YES_OPTION) {

                usuario.setContraseña(contraseñaNueva);

                gestorBDUsuario = new GBDUsuario();

                Boolean exito = false;

                try {
                    exito = gestorBDUsuario.ModificarConreseña(usuario);
                } catch (SQLException ex) {
                    Logger.getLogger(JPCambiarContraseña.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (exito) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Operación realizada con Éxito!",
                            "Cambio de Contraseña - Nona Mafalda",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);

                    return;
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Se produjo un Error. \n"
                            + "Intentelo Nuevamente!",
                            "Cambio de Contraseña - Nona Mafalda",
                            JOptionPane.ERROR_MESSAGE);
                    limpiarCampos();
                    jpfContraseñaActual.requestFocus();
                    return;
                }
            }
        }
    }//GEN-LAST:event_btnCambiarContraseñaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarContraseña;
    private javax.swing.JLabel jLImagenContraseña;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPasswordField jpfContraseñaActual;
    private javax.swing.JPasswordField jpfContraseñaConfirmar;
    private javax.swing.JPasswordField jpfContraseñaNueva;
    // End of variables declaration//GEN-END:variables
}
