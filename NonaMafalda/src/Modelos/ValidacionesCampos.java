/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author German Bartoli
 */
public class ValidacionesCampos {

    //Usuario Nombre de Usuario
    public static void NombreUsuario(final JTextField jTextField) {
        jTextField.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 48 || 57 < k) && (k < 97 || 122 < k) && k != 8 && k != 127) || (jTextField.getText().length() >= 10)) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }

    //Contraseña de Usuario
    public static void ContraseñaUsuario(final JPasswordField jPasswordField) {
        jPasswordField.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 48 || 57 < k) && (k < 97 || 122 < k) && (k < 65 || 90 < k) && k != 8 && k != 127) || (jPasswordField.getPassword().length >= 20)) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }

    //Forma de Pago, Marca, Tipo de Producto
    //Producto -> Nombre
    public static void MayusculasMinusculasNumerosEspaciosBorrar40(final JTextField jTextField) {
        jTextField.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 48 || 57 < k) && (k < 97 || 122 < k) && (k < 65 || 90 < k) && k != 8 && k != 127 && k != 32)
                        || (jTextField.getText().length() >= 40)) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }

    public static void Fecha(final JFormattedTextField jFormattedTextField) {
        jFormattedTextField.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 47 || 57 < k) && k != 8 && k != 127)
                        || jFormattedTextField.getText().length() >= 10) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }

    public static void Hora(final JFormattedTextField jFormattedTextField) {
        jFormattedTextField.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 48 || 58 < k) && k != 8 && k != 127)
                        || jFormattedTextField.getText().length() >= 8) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }

    //Stock -> Cantidad, 
    //Producto -> Peso, stockminimo
	//Venta factura cantidad
    public static void PesoCantidad(final JTextField jTextField) {
        jTextField.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 48 || 57 < k) && k != 8 && k != 127 && (k != 46 || jTextField.getText().contains(".")))
                        || jTextField.getText().length() >= 7) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }

    //Producto Precio
	//factura pago cliente
    public static void Precio(final JTextField jTextField) {
        jTextField.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 48 || 57 < k) && k != 8 && k != 127 && (k != 46 || jTextField.getText().contains(".")))
                        || jTextField.getText().length() >= 9) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }

    //Producto Unidades, Platos
    //Cliente cantidad visitas
    public static void UnidadesPlatosVisitas(final JTextField jTextField) {
        jTextField.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 48 || 57 < k) && k != 8 && k != 127)
                        || jTextField.getText().length() >= 6) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }

    public static void ValidacionTextArea(final JTextArea jTextArea) {
        jTextArea.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 48 || 57 < k) && (k < 97 || 122 < k) && (k < 65 || 90 < k) && k != 8 && k != 44 && k != 46 && k != 127 && k != 32)
                        || (jTextArea.getText().length() >= 200)) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }

//Persona -> DNI
    public static void DNI(final JTextField jTextField) {
        jTextField.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 48 || 57 < k) && k != 8 && k != 127)
                        || jTextField.getText().length() >= 10) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }

    //Persona -> Nombre, Apelido
    public static void NombreApellido(final JTextField jTextField) {
        jTextField.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 65 || 90 < k) && (k < 97 || 122 < k) && k != 8 && k != 127)
                        || (jTextField.getText().length() >= 40)) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }

    public static void Edad(final JTextField jTextField) {
        jTextField.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 48 || 57 < k) && k != 8 && k != 127)
                        || (jTextField.getText().length() >= 3)) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }

    public static void Correo(final JTextField jTextField) {
        jTextField.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 48 || 57 < k) && (k < 97 || 122 < k) && (k < 65 || 90 < k) && k != 8 && k != 127 && (k != 64 || jTextField.getText().contains("@")))
                        || jTextField.getText().length() >= 60) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }

    public static void NumTel(final JTextField jTextField) {
        jTextField.addKeyListener(
                new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int k = (int) evt.getKeyChar();
                if (((k < 48 || 57 < k) && k != 8 && k != 127)
                        || jTextField.getText().length() >= 35) {
                    //evt.setKeyChar((char) KeyEvent.VK_CLEAR);
                    evt.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
        );
    }
}
