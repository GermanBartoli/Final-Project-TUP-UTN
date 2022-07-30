/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Empleado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Modelos.GestorBDConexion;
import java.util.ArrayList;

/**
 *
 * @author German Bartoli
 */
public class GBDEmpleado {

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    ArrayList<CEmpleado> arrayListComboEmpleados;
    ArrayList<CEmpleado> alEmpleados;

    public boolean ExisteEmpleado(int idEmpleado) throws SQLException {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();

            String sql = 
                    "select * "
                    + "from Empleado "
                    + "WHERE ID_Empleado = ? "
                    + "and Baja_Logica = 1";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);

            stmt.setInt(1, idEmpleado);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                existe = true;
            }
            rs.close();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return existe;
    }
    
    public CEmpleado ObtenerEmpleadoXIDActivos(int idEmpleado1) {
        CEmpleado empleado = null;

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "SELECT "
                    + "Persona.ID_Persona, "
                    + "Persona.DNI, "
                    + "Persona.Nombre, "
                    + "Persona.Apellido, "
                    + "Persona.Edad, "
                    + "Persona.Correo, "
                    + "Persona.Tel, "
                    + "Persona.True_False, "
                    + "Empleado.ID_Empleado, Empleado.Baja_Logica "
                    + "From Persona "
                    + "Inner Join Empleado on Persona.ID_Persona = Empleado.ID_Persona "
                    + "WHERE ID_Empleado = ? and "
                    + "Empleado.Baja_Logica = 1 "
                    + "and Persona.True_False = 1";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);
            stmt.setInt(1, idEmpleado1);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idPersona = rs.getInt(1);
                int dni = rs.getInt(2);
                String nombre = rs.getString(3);
                String apellido = rs.getString(4);
                int edad = rs.getInt(5);
                String correo = rs.getString(6);
                String tel = rs.getString(7);
                boolean trueFalsePersona = true;
                int idEmpleado2 = rs.getInt(9);
                boolean trueFalseEmpleado = true;

                empleado = new CEmpleado(idPersona, dni, nombre, apellido, edad, correo, tel, trueFalsePersona, idEmpleado2, trueFalseEmpleado);
            }
            rs.close();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return empleado;
    }

    public CEmpleado ObtenerEmpleadoXIDActivosYNo(int idEmpleado1) {
        CEmpleado empleado = null;

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "SELECT "
                    + "Persona.ID_Persona, "
                    + "Persona.DNI, "
                    + "Persona.Nombre, "
                    + "Persona.Apellido, "
                    + "Persona.Edad, "
                    + "Persona.Correo, "
                    + "Persona.Tel, "
                    + "Persona.True_False, "
                    + "Empleado.ID_Empleado, Empleado.Baja_Logica "
                    + "From Persona "
                    + "Inner Join Empleado on Persona.ID_Persona = Empleado.ID_Persona "
                    + "WHERE ID_Empleado = ?";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);
            stmt.setInt(1, idEmpleado1);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idPersona = rs.getInt(1);
                int dni = rs.getInt(2);
                String nombre = rs.getString(3);
                String apellido = rs.getString(4);
                int edad = rs.getInt(5);
                String correo = rs.getString(6);
                String tel = rs.getString(7);
                boolean trueFalsePersona = true;
                int idEmpleado2 = rs.getInt(9);
                boolean trueFalseEmpleado = true;

                empleado = new CEmpleado(idPersona, dni, nombre, apellido, edad, correo, tel, trueFalsePersona, idEmpleado2, trueFalseEmpleado);
            }
            rs.close();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return empleado;
    }

    
    
    public ArrayList<CEmpleado> CargarListaEmpleados() {
        alEmpleados = new ArrayList<CEmpleado>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "SELECT "
                    + "Persona.ID_Persona, "
                    + "Persona.DNI, "
                    + "Persona.Nombre, "
                    + "Persona.Apellido, "
                    + "Persona.Edad, "
                    + "Persona.Correo, "
                    + "Persona.Tel, "
                    + "Persona.True_False, "
                    + "Empleado.ID_Empleado, "
                    + "Empleado.Baja_Logica "
                    + "From Persona "
                    + "Inner Join Empleado on Persona.ID_Persona = Empleado.ID_Persona "
                    + "WHERE Empleado.Baja_Logica = 1 and Persona.True_False = 1"
                    + "Order By Persona.Apellido";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idPersona = rs.getInt(1);
                int dni = rs.getInt(2);
                String nombre = rs.getString(3);
                String apellido = rs.getString(4);
                int edad = rs.getInt(5);
                String correo = rs.getString(6);
                String tel = rs.getString(7);
                boolean trueFalsePersona = rs.getBoolean(8);
                int idEmpleado = rs.getInt(9);
                boolean trueFalseEmpleado = rs.getBoolean(10);

                CEmpleado cEmpleado = new CEmpleado(idPersona, dni, nombre, apellido, edad, correo, tel,
                        trueFalsePersona, idEmpleado, trueFalseEmpleado);

                alEmpleados.add(cEmpleado);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }
        return alEmpleados;
    }

    public ArrayList<CEmpleado> CargarComboEmpleados() {

        arrayListComboEmpleados = new ArrayList<CEmpleado>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "SELECT Persona.ID_Persona, "
                    + "Persona.DNI, "
                    + "Persona.Nombre, "
                    + "Persona.Apellido, "
                    + "Persona.Edad, "
                    + "Persona.Correo, "
                    + "Persona.Tel, "
                    + "Persona.True_False, "
                    + "Empleado.ID_Empleado, "
                    + "Empleado.Baja_Logica "
                    + "From Persona "
                    + "Inner Join Empleado on Persona.ID_Persona = Empleado.ID_Persona "
                    + "WHERE Empleado.Baja_Logica = 1 and Persona.True_False = 1";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idPersona = rs.getInt(1);
                int dni = rs.getInt(2);
                String nombre = rs.getString(3);
                String apellido = rs.getString(4);
                int edad = rs.getInt(5);
                String correo = rs.getString(6);
                String tel = rs.getString(7);
                boolean trueFalsePersona = rs.getBoolean(8);
                int idEmpleado = rs.getInt(9);
                boolean trueFalseEmpleado = rs.getBoolean(10);

                CEmpleado cEmpleado = new CEmpleado(idPersona, dni, nombre, apellido, edad, correo, tel, trueFalsePersona, idEmpleado, trueFalseEmpleado);
                arrayListComboEmpleados.add(cEmpleado);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }
        return arrayListComboEmpleados;
    }

    public boolean AgregarEmpleado(int ultimoIDPersona) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "INSERT INTO Empleado(ID_Persona, Baja_Logica) VALUES (?,?)");
            stmt.setInt(1, ultimoIDPersona);
            stmt.setBoolean(2, true);

            filasAfectadas = stmt.executeUpdate();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        if (filasAfectadas > 0) {
            return true;
        } else {
            return false;
        }
    }
//
//    public boolean ModificarEmpleado(CEmpleado cEmpleado) throws SQLException {
//        int filasAfectadas = 0;
//        try {
//            gestorBDConexion.AbrirConexion();
//            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
//                    "UPDATE Marca SET Marca = ? where ID_Marca = ?");
//            stmt.setString(1, marca.getNombre());
//            stmt.setInt(2, marca.getId_Marca());
//
//            filasAfectadas = stmt.executeUpdate();
//            stmt.close();
//
//        } catch (Exception exc) {
//            exc.printStackTrace();
//        } finally {
//            gestorBDConexion.CerrarConexion();
//        }
//        if (filasAfectadas > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public boolean EliminarEmpleado(int idEmpleado) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "Update Empleado "
                    + "Set Baja_Logica = 0 "
                    + "WHERE ID_Empleado = ?");
            stmt.setInt(1, idEmpleado);

            filasAfectadas = stmt.executeUpdate();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }
        if (filasAfectadas > 0) {
            return true;
        } else {
            return false;
        }
    }
}
