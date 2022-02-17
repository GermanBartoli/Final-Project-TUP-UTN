/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HorariosE;

import Empleado.CEmpleado;
import Empleado.GBDEmpleado;
import HorariosE.CHorariosE;
import HorariosE.GBDHorariosE;
import Modelos.TextPrompt;
import Modelos.ValidacionesCampos;
import java.sql.Time;
import javax.swing.JOptionPane;
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
public class JPHorariosE extends javax.swing.JPanel {

    private final GBDEmpleado gBDEmpleado;
    private final GBDHorariosE gBDHorariosE;

    private ArrayList<CEmpleado> listaEmpleados;
    private ArrayList<CHorariosE> listaCHorariosE;

    private DefaultListModel<CEmpleado> listModel;
    private ListSelectionListener lslListaEmpleados;

    DefaultTableModel dtm;
    private ListSelectionListener lslTablaHorariosE;

    final String DMY_FORMAT;
    final String YMD_FORMAT;

    private SimpleDateFormat simpleDateFormat;

    private SimpleDateFormat simpleTimeFormat;

    private boolean banderaAgregarEditar;

    /**
     * Creates new form JPEmpleadoHorarios
     */
    public JPHorariosE() {
        initComponents();

        TextPrompt placeHolderDiaTrabajado = new TextPrompt("dd/mm/yyyy", jFTFFecha, TextPrompt.Show.ALWAYS);
        TextPrompt placeHolderHoraIngreso = new TextPrompt("HH:mm:ss", jFTFHoraIngreso, TextPrompt.Show.ALWAYS);
        TextPrompt placeHolderHoraEgreso = new TextPrompt("HH:mm:ss", jFTFHoraEgreso, TextPrompt.Show.ALWAYS);

        DMY_FORMAT = "dd/MM/yyyy";
        YMD_FORMAT = "yyyy/MM/dd";

        simpleDateFormat = new SimpleDateFormat();

        simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");

        //1.º
        gBDEmpleado = new GBDEmpleado();
        gBDHorariosE = new GBDHorariosE();

        //Iniciar con el 1ro seleccionado
        //Tira error si no hay un item seleccionado
        CrearListSelectionListenerListaEmpleados();
        CrearListSelectionListenerTablaHorarios();

        CargarListaEmpleados();

        ValidacionCampos();

        //No copy/paste
        jFTFFecha.setTransferHandler(null);
        jFTFHoraIngreso.setTransferHandler(null);
        jFTFHoraEgreso.setTransferHandler(null);
    }

    public void CargarListaEmpleados() {
        //Crear un objeto DefaultListModel
        listaEmpleados = gBDEmpleado.CargarListaEmpleados();
        jLEmpleados.removeListSelectionListener(lslListaEmpleados);

        listModel = new DefaultListModel<CEmpleado>();
        //Recorrer el contenido del ArrayList
        for (int i = 0; i < listaEmpleados.size(); i++) {
            //Añadir cada elemento del ArrayList en el modelo de la lista
            listModel.add(i, listaEmpleados.get(i));
        }
        //Asociar el modelo de lista al JList
        jLEmpleados.setModel(listModel);

        jLEmpleados.addListSelectionListener(lslListaEmpleados);

        if (jLEmpleados.getModel().getSize() > 0) {
            HabilitarDeshabilitarCampos(false);
            jLEmpleados.setEnabled(true);
            JTHorarios.setEnabled(true);

            HabilitarDeshabilitarBotones(true);
            btnGuardar.setEnabled(false);
            btnCancelar.setEnabled(false);

            jLEmpleados.setSelectedIndex(0);
        } else {
            HabilitarDeshabilitarCampos(false);

            HabilitarDeshabilitarBotones(false);

            LimpiarCampos();
        }
    }

    private void CrearListSelectionListenerListaEmpleados() {
        lslListaEmpleados = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting() && jLEmpleados.getModel().getSize() > 0) {
                    LimpiarTabla();
                    LimpiarCampos();
                    CargarTablaStocks();
                }
            }
        };
    }

    private void CargarTablaStocks() {
        listaCHorariosE = gBDHorariosE.CargarTablaHorarios(jLEmpleados.getSelectedValue().getIdEmpleado());

        JTHorarios.getSelectionModel().removeListSelectionListener(lslTablaHorariosE);

        dtm = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;//This causes all cells to be not editable
            }
        };

        String[] nombreDeColumnas = {"Código", "Fecha", "Ingreso", "Egreso", "Tiempo Trabajado"};
        dtm.setColumnIdentifiers(nombreDeColumnas);

        simpleDateFormat.applyPattern(DMY_FORMAT);

        for (CHorariosE horario : listaCHorariosE) {
            Object[] row = {
                horario.getIdIngresoEgreso(),
                simpleDateFormat.format(horario.getDiaTrabajado()),
                horario.getHoraIngreso(),
                horario.getHoraEgreso(),
                horario.getTiempoTrabajado()};
            dtm.addRow(row);
        }
        JTHorarios.setModel(dtm);

        JTHorarios.getSelectionModel().addListSelectionListener(lslTablaHorariosE);

        //if (jTStocks.getRowSelectionAllowed() && jTStocks.getColumnSelectionAllowed()) {
        if (JTHorarios.getModel().getRowCount() > 0) {
            JTHorarios.setRowSelectionInterval(0, 0);
            btnEditar.setEnabled(true);
            btnBorrar.setEnabled(true);
        } else {
            btnEditar.setEnabled(false);
            btnBorrar.setEnabled(false);
        }
    }

    private void CrearListSelectionListenerTablaHorarios() {
        lslTablaHorariosE = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting() && JTHorarios.getModel().getRowCount() > 0) {
                    CargarHorariosEnCampos();
                }
            }
        };
    }

    private void CargarHorariosEnCampos() {
        String stringFecha = (String) JTHorarios.getValueAt(JTHorarios.getSelectedRow(), 1);
        jFTFFecha.setText(stringFecha);

        Time ingreso = (Time) JTHorarios.getValueAt(JTHorarios.getSelectedRow(), 2);
        String stringIngreso = simpleTimeFormat.format(ingreso);
        jFTFHoraIngreso.setText(stringIngreso);

        Date egreso = (Date) JTHorarios.getValueAt(JTHorarios.getSelectedRow(), 3);
        String stringEgreso = simpleTimeFormat.format(egreso);
        jFTFHoraEgreso.setText(stringEgreso);
    }

    private void LimpiarCampos() {
        jFTFFecha.setText("");
        jFTFHoraIngreso.setText("");
        jFTFHoraEgreso.setText("");
    }

    private void LimpiarTabla() {
        DefaultTableModel dtm = new DefaultTableModel();
        JTHorarios.setModel(dtm);
    }

    private void HabilitarDeshabilitarCampos(boolean x) {
        jFTFFecha.setEnabled(x);
        jFTFHoraIngreso.setEnabled(x);
        jFTFHoraEgreso.setEnabled(x);
        jLEmpleados.setEnabled(x);
        JTHorarios.setEnabled(x);
    }

    private void HabilitarDeshabilitarBotones(boolean x) {
        btnAgregar.setEnabled(x);
        btnEditar.setEnabled(x);
        btnBorrar.setEnabled(x);
        btnGuardar.setEnabled(x);
        btnCancelar.setEnabled(x);
    }
//Validar numeros y letras, agregar mensaje si no quiere agregarlo

    private boolean validar() {
        try {
            
        
        //validar que la hora que salio no sea despues de la hora que es el dia que se hace la transaccion
        simpleDateFormat.applyPattern(DMY_FORMAT);

        Date fechaAhora = new Date();
        String strDate = simpleDateFormat.format(fechaAhora);

        Time horaAhora = null;
        String strHour = simpleTimeFormat.format(fechaAhora);

        Date milNovecientos = new GregorianCalendar(1900, Calendar.JANUARY, 1).getTime();
        String strMilNovecientos = simpleDateFormat.format(milNovecientos);

        Date dosMilDocientos = new GregorianCalendar(2200, Calendar.JANUARY, 1).getTime();
        String strdosMilDocientos = simpleDateFormat.format(dosMilDocientos);

        Date diaTrabajado = null;
        Time horaIngreso = null;
        Time horaEgreso = null;

        try {
            fechaAhora = simpleDateFormat.parse(strDate);
            horaAhora = new java.sql.Time(simpleTimeFormat.parse(strHour).getTime());

            milNovecientos = simpleDateFormat.parse(strMilNovecientos);
            dosMilDocientos = simpleDateFormat.parse(strdosMilDocientos);

            if (!jFTFFecha.getText().equals("")) {
                diaTrabajado = simpleDateFormat.parse(jFTFFecha.getText());
            } else 
            {
                JOptionPane.showMessageDialog(
                        this,
                        "Fecha Vacía",
                        "Error - Nona Mafalda",
                        JOptionPane.WARNING_MESSAGE);
                jFTFFecha.requestFocus();
                return false;
            }

            if (!jFTFHoraIngreso.getText().equals("")) {
                horaIngreso = new java.sql.Time(simpleTimeFormat.parse(jFTFHoraIngreso.getText()).getTime());
            } else{
                JOptionPane.showMessageDialog(
                        this,
                        "Hora de Ingreso Vacía",
                        "Error - Nona Mafalda",
                        JOptionPane.WARNING_MESSAGE);
                jFTFHoraIngreso.requestFocus();
                return false;
            }

            if (!jFTFHoraEgreso.getText().equals("")) {
                horaEgreso = new java.sql.Time(simpleTimeFormat.parse(jFTFHoraEgreso.getText()).getTime());
            } else{
                JOptionPane.showMessageDialog(
                        this,
                        "Hora de Egreso Vacía",
                        "Error - Nona Mafalda",
                        JOptionPane.WARNING_MESSAGE);
                jFTFHoraEgreso.requestFocus();
                return false;
            }

        } catch (ParseException ex) {
            Logger.getLogger(JPHorariosE.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (jFTFFecha.equals("")
                || diaTrabajado.after(fechaAhora)
                || diaTrabajado.before(milNovecientos)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Fecha Incorrecta",
                    "Advertencia - Nona Mafalda",
                    JOptionPane.WARNING_MESSAGE);
            jFTFFecha.requestFocus();
            return false;
        } else {
            if (jFTFHoraIngreso.equals("")
                    || horaIngreso.after(horaEgreso)
                    || horaIngreso.equals(horaEgreso)
                    || (diaTrabajado.equals(fechaAhora) && horaIngreso.after(horaAhora))) {
                JOptionPane.showMessageDialog(
                        this,
                        "Hora de Ingreso Incorrecta",
                        "Advertencia - Nona Mafalda",
                        JOptionPane.WARNING_MESSAGE);
                jFTFHoraIngreso.requestFocus();
                return false;
            } else {
                if (jFTFHoraEgreso.equals("")
                        || horaEgreso.equals(horaIngreso)
                        || horaEgreso.before(horaIngreso)
                        || (diaTrabajado.equals(fechaAhora) && horaEgreso.before(horaAhora))) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Hora de Egreso Incorrecta",
                            "Advertencia - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    jFTFHoraEgreso.requestFocus();
                    return false;
                }
            }
        }
        return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                        this,
                        "Error",
                        "Advertencia - Nona Mafalda",
                        JOptionPane.WARNING_MESSAGE);
                jFTFHoraIngreso.requestFocus();
            return false;
        }
    }

    private void ValidacionCampos() {
        ValidacionesCampos.Fecha(jFTFFecha);
        ValidacionesCampos.Hora(jFTFHoraIngreso);
        ValidacionesCampos.Hora(jFTFHoraEgreso);
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
        jLEmpleados = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTHorarios = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jFTFFecha = new javax.swing.JFormattedTextField();
        jFTFHoraEgreso = new javax.swing.JFormattedTextField();
        jFTFHoraIngreso = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1280, 695));
        setLayout(null);

        jLEmpleados.setBackground(new java.awt.Color(0, 0, 0));
        jLEmpleados.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLEmpleados.setForeground(new java.awt.Color(255, 255, 255));
        jLEmpleados.setToolTipText("Listado de Empleados");
        jLEmpleados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLEmpleados.setNextFocusableComponent(JTHorarios);
        jLEmpleados.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jLEmpleados.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jLEmpleados);

        add(jScrollPane1);
        jScrollPane1.setBounds(970, 10, 300, 630);

        JTHorarios.setBackground(new java.awt.Color(0, 0, 0));
        JTHorarios.setForeground(new java.awt.Color(255, 255, 255));
        JTHorarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        JTHorarios.setToolTipText("Tabla de Horarios");
        JTHorarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JTHorarios.setGridColor(new java.awt.Color(102, 102, 102));
        JTHorarios.setNextFocusableComponent(jFTFFecha);
        JTHorarios.setSelectionBackground(new java.awt.Color(255, 255, 255));
        JTHorarios.setSelectionForeground(new java.awt.Color(0, 0, 0));
        JTHorarios.setShowGrid(true);
        jScrollPane2.setViewportView(JTHorarios);

        add(jScrollPane2);
        jScrollPane2.setBounds(14, 75, 940, 400);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Horarios");
        add(jLabel1);
        jLabel1.setBounds(390, 10, 182, 56);

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Listado Empleados");
        add(jLabel4);
        jLabel4.setBounds(970, 640, 305, 42);

        btnAgregar.setBackground(new java.awt.Color(0, 0, 0));
        btnAgregar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.setToolTipText("Agregar Horario");
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.setNextFocusableComponent(btnEditar);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        add(btnAgregar);
        btnAgregar.setBounds(60, 630, 160, 50);

        btnGuardar.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setToolTipText("Guardar Horario");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setNextFocusableComponent(btnCancelar);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        add(btnGuardar);
        btnGuardar.setBounds(530, 630, 170, 50);

        btnEditar.setBackground(new java.awt.Color(0, 0, 0));
        btnEditar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setText("Editar");
        btnEditar.setToolTipText("Editar Horario Seleccionado");
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
        btnBorrar.setToolTipText("Borrar Horario Seleccionado");
        btnBorrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrar.setNextFocusableComponent(btnGuardar);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        add(btnBorrar);
        btnBorrar.setBounds(390, 630, 130, 50);

        btnCancelar.setBackground(new java.awt.Color(0, 0, 0));
        btnCancelar.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancelar Operación");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setNextFocusableComponent(jLEmpleados);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        add(btnCancelar);
        btnCancelar.setBounds(710, 630, 180, 50);

        jFTFFecha.setBackground(new java.awt.Color(0, 0, 0));
        jFTFFecha.setForeground(new java.awt.Color(255, 255, 255));
        jFTFFecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        jFTFFecha.setToolTipText("Fecha del Horario");
        jFTFFecha.setCaretColor(new java.awt.Color(255, 255, 255));
        jFTFFecha.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jFTFFecha.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jFTFFecha.setNextFocusableComponent(jFTFHoraIngreso);
        jFTFFecha.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jFTFFecha.setSelectionColor(new java.awt.Color(255, 255, 255));
        jFTFFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFTFFechaActionPerformed(evt);
            }
        });
        add(jFTFFecha);
        jFTFFecha.setBounds(40, 550, 230, 60);

        jFTFHoraEgreso.setBackground(new java.awt.Color(0, 0, 0));
        jFTFHoraEgreso.setForeground(new java.awt.Color(255, 255, 255));
        jFTFHoraEgreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance())));
        jFTFHoraEgreso.setToolTipText("Hora de Salida del Horario");
        jFTFHoraEgreso.setCaretColor(new java.awt.Color(255, 255, 255));
        jFTFHoraEgreso.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jFTFHoraEgreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jFTFHoraEgreso.setNextFocusableComponent(btnAgregar);
        jFTFHoraEgreso.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jFTFHoraEgreso.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jFTFHoraEgreso);
        jFTFHoraEgreso.setBounds(700, 550, 230, 60);

        jFTFHoraIngreso.setBackground(new java.awt.Color(0, 0, 0));
        jFTFHoraIngreso.setForeground(new java.awt.Color(255, 255, 255));
        jFTFHoraIngreso.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance())));
        jFTFHoraIngreso.setToolTipText("Hora de Ingreso del Horario");
        jFTFHoraIngreso.setCaretColor(new java.awt.Color(255, 255, 255));
        jFTFHoraIngreso.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jFTFHoraIngreso.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jFTFHoraIngreso.setNextFocusableComponent(jFTFHoraEgreso);
        jFTFHoraIngreso.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jFTFHoraIngreso.setSelectionColor(new java.awt.Color(255, 255, 255));
        add(jFTFHoraIngreso);
        jFTFHoraIngreso.setBounds(380, 550, 230, 60);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Fecha");
        add(jLabel2);
        jLabel2.setBounds(120, 480, 80, 60);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Hora Salida");
        add(jLabel3);
        jLabel3.setBounds(740, 470, 140, 60);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Hora Ingreso");
        add(jLabel5);
        jLabel5.setBounds(420, 470, 150, 60);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        if (jLEmpleados.getModel().getSize() > 0) {

            banderaAgregarEditar = true;
            LimpiarCampos();

            HabilitarDeshabilitarCampos(true);
            jLEmpleados.setEnabled(false);
            JTHorarios.setEnabled(false);

            HabilitarDeshabilitarBotones(false);
            btnGuardar.setEnabled(true);
            btnCancelar.setEnabled(true);

            jFTFFecha.requestFocus();
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (validar()) {

            int idIngresoEgreso;

            if (banderaAgregarEditar) {
                idIngresoEgreso = -1;
            } else {
                idIngresoEgreso = (Integer) JTHorarios.getModel().getValueAt(JTHorarios.getSelectedRow(), 0);
            }

            int idEmpleado = jLEmpleados.getSelectedValue().getIdEmpleado();

            simpleDateFormat.applyPattern(DMY_FORMAT);

            Date diaTrabajado = null;
            Time horaIngreso = null;
            Time horaEgreso = null;

            try {
                diaTrabajado = simpleDateFormat.parse(jFTFFecha.getText());

                simpleDateFormat.applyPattern(YMD_FORMAT);

                String stringDiaTrabajado = simpleDateFormat.format(diaTrabajado);

                diaTrabajado = simpleDateFormat.parse(stringDiaTrabajado);

                horaIngreso = new java.sql.Time(simpleTimeFormat.parse(jFTFHoraIngreso.getText()).getTime());
                horaEgreso = new java.sql.Time(simpleTimeFormat.parse(jFTFHoraEgreso.getText()).getTime());

            } catch (ParseException ex) {
                Logger.getLogger(JPHorariosE.class.getName()).log(Level.SEVERE, null, ex);
            }

            CHorariosE cHorariosE = new CHorariosE(idIngresoEgreso, idEmpleado, diaTrabajado, horaIngreso, horaEgreso, true);

            if (banderaAgregarEditar) {
                if (gBDHorariosE.ExisteHorario(cHorariosE)) {
                    jFTFHoraIngreso.requestFocus();
                    JOptionPane.showMessageDialog(
                            this,
                            "El Empleado ya tiene ese Horario asignado",
                            "Error - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                } else {
                    if (gBDHorariosE.HorarioYaAsignadoAgregar(cHorariosE)) {
                        jFTFHoraIngreso.requestFocus();

                        JOptionPane.showMessageDialog(
                                this,
                                "El Empleado ya tiene ese Horario asignado",
                                "Error - Nona Mafalda",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            } else {
                if (gBDHorariosE.HorarioYaAsignadoEditar(cHorariosE)) {
                    jFTFHoraIngreso.requestFocus();

                    JOptionPane.showMessageDialog(
                            this,
                            "El Empleado ya tiene ese Horario asignado",
                            "Error - Nona Mafalda",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            boolean exito = false;

            if (idIngresoEgreso == -1) {
                exito = gBDHorariosE.AgregarHorario(cHorariosE);
            } else {
                exito = gBDHorariosE.ModificarHorario(cHorariosE);
            }
            if (exito) {
                int productSelected = jLEmpleados.getSelectedIndex();
                int tableRowSelected = JTHorarios.getSelectedRow();

                CargarListaEmpleados();

                jLEmpleados.setSelectedIndex(productSelected);

                if (idIngresoEgreso == -1) {
                    int ultimoIDIngresoEgreso = gBDHorariosE.ObtenerUltimoIDIngresoEgreso();

                    for (int i = 0; i < JTHorarios.getModel().getRowCount(); i++) {
                        if (ultimoIDIngresoEgreso == (Integer) JTHorarios.getModel().getValueAt(i, 0)) {
                            JTHorarios.setRowSelectionInterval(i, i);
                        }
                    }
                } else {
                    JTHorarios.getSelectionModel().removeListSelectionListener(lslTablaHorariosE);
                    JTHorarios.removeRowSelectionInterval(tableRowSelected, tableRowSelected);
                    JTHorarios.getSelectionModel().addListSelectionListener(lslTablaHorariosE);

                    JTHorarios.setRowSelectionInterval(tableRowSelected, tableRowSelected);
                }

                JOptionPane.showMessageDialog(
                        this,
                        "El Horario se guardó Correctamente",
                        "- Nona Mafalda",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        if (JTHorarios.getModel().getRowCount() > 0) {
            banderaAgregarEditar = false;

            HabilitarDeshabilitarCampos(true);
            jLEmpleados.setEnabled(false);
            JTHorarios.setEnabled(false);

            HabilitarDeshabilitarBotones(false);
            btnGuardar.setEnabled(true);
            btnCancelar.setEnabled(true);

            jFTFFecha.requestFocus();
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        if (JTHorarios.getModel().getRowCount() > 0) {
            if (JOptionPane.showConfirmDialog(
                    this,
                    "Esta seguro?",
                    "Eliminando Horario - Nona Mafalda",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                // yes option
                int idIngresoEgreso = (int) JTHorarios.getValueAt(JTHorarios.getSelectedRow(), 0);

                boolean exito = false;

                exito = gBDHorariosE.EliminarHorario(idIngresoEgreso);

                if (exito) {
                    int empleadoSeleccionado = jLEmpleados.getSelectedIndex();
                    CargarListaEmpleados();

                    if ((JTHorarios.getModel().getRowCount() > 0)) {
                        jLEmpleados.setSelectedIndex(empleadoSeleccionado);
                    }
                    JOptionPane.showMessageDialog(
                            this,
                            "El Horario se eliminó correctamente.",
                            "Exito - Nona Mafalda",
                            JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Intentelo Nuevamente.",
                            "Error al Eliminar el Horario - Nona Mafalda",
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
        jLEmpleados.setEnabled(true);
        JTHorarios.setEnabled(true);

        HabilitarDeshabilitarBotones(true);
        btnGuardar.setEnabled(false);
        btnCancelar.setEnabled(false);

        int listaFilaSeleccionada = jLEmpleados.getSelectedIndex();
        int tablaFilaSeleccionada = JTHorarios.getSelectedRow();

        if (jLEmpleados.getModel().getSize() > 0) {
            jLEmpleados.setSelectedIndex(listaFilaSeleccionada);
            if (JTHorarios.getModel().getRowCount() > 0) {

                JTHorarios.getSelectionModel().removeListSelectionListener(lslTablaHorariosE);
                JTHorarios.removeRowSelectionInterval(tablaFilaSeleccionada, tablaFilaSeleccionada);
                JTHorarios.getSelectionModel().addListSelectionListener(lslTablaHorariosE);
                JTHorarios.setRowSelectionInterval(tablaFilaSeleccionada, tablaFilaSeleccionada);

            } else {
                btnBorrar.setEnabled(false);
                btnEditar.setEnabled(false);

                LimpiarCampos();

            }
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jFTFFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFTFFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFFechaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTHorarios;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JFormattedTextField jFTFFecha;
    private javax.swing.JFormattedTextField jFTFHoraEgreso;
    private javax.swing.JFormattedTextField jFTFHoraIngreso;
    private javax.swing.JList<CEmpleado> jLEmpleados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
