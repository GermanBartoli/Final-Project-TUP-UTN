/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Modelos.GestorBDConexion;
import java.util.ArrayList;

/**
 *
 * @author German Bartoli
 */
public class GBDCliente {

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    ArrayList<CCliente> arrayListComboClientes;
    ArrayList<CCliente> alClientes;

    public ArrayList<CCliente> CargarListaClientes() {
        alClientes = new ArrayList<CCliente>();

        try {

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
                    + "Cliente.ID_Cliente, "
                    + "Cliente.Cantidad_Visitas, "
                    + "Cliente.Baja_Logica "
                    + "From "
                    + "Persona "
                    + "Inner Join "
                    + "Cliente on Persona.ID_Persona = Cliente.ID_Persona "
                    + "WHERE "
                    + "Cliente.Baja_Logica = 1 and "
                    + "Persona.True_False = 1"
                    + "Order By Persona.Apellido";

            gestorBDConexion.AbrirConexion();
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
                int idCliente = rs.getInt(9);
                int cantidadVisitas = rs.getInt(10);
                boolean trueFalseCliente = true;

                CCliente cCliente = new CCliente(idPersona, dni, nombre, apellido, edad, correo, tel,
                        trueFalsePersona, idCliente, cantidadVisitas, trueFalseCliente);

                alClientes.add(cCliente);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }
        return alClientes;
    }

    public ArrayList<CCliente> CargarListaTodosLosClientes() {
        alClientes = new ArrayList<CCliente>();

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
                    + "Cliente.ID_Cliente, "
                    + "Cliente.Cantidad_Visitas, "
                    + "Cliente.Baja_Logica "
                    + "From Persona "
                    + "Inner Join Cliente on Persona.ID_Persona = Cliente.ID_Persona "
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
                int idCliente = rs.getInt(9);
                int cantidadVisitas = rs.getInt(10);
                boolean trueFalseCliente = true;

                CCliente cCliente = new CCliente(idPersona, dni, nombre, apellido, edad, correo, tel,
                        trueFalsePersona, idCliente, cantidadVisitas, trueFalseCliente);

                alClientes.add(cCliente);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }
        return alClientes;
    }

    public ArrayList<CCliente> CargarComboClientes() {

        arrayListComboClientes = new ArrayList<CCliente>();

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
                    + "Cliente.ID_Cliente, "
                    + "Cliente.Cantidad_Visitas, "
                    + "Cliente.Baja_Logica "
                    + "From Persona "
                    + "Inner Join Cliente on Persona.ID_Persona = Cliente.ID_Persona "
                    + "WHERE Cliente.Baja_Logica = 1 and "
                    + "Persona.True_False = 1";

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
                int idCliente = rs.getInt(9);
                int cantidadVisitas = rs.getInt(10);
                boolean trueFalseCliente = rs.getBoolean(11);

                CCliente cCliente = new CCliente(idPersona, dni, nombre, apellido, edad, correo, tel,
                        trueFalsePersona, idCliente, cantidadVisitas, trueFalseCliente);

                arrayListComboClientes.add(cCliente);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }
        return arrayListComboClientes;
    }

    public boolean AgregarCliente(int ultimoIDPersona, int cantidadVisitas) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "INSERT INTO Cliente"
                    + "("
                    + "ID_Persona, "
                    + "Cantidad_Visitas, "
                    + "Baja_Logica"
                    + ") "
                    + "VALUES (?,?,?)";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);
            stmt.setInt(1, ultimoIDPersona);
            stmt.setInt(2, cantidadVisitas);
            stmt.setBoolean(3, true);

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

    public boolean ModificarCliente(CCliente cCliente) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "UPDATE Cliente "
                    + "SET Cantidad_Visitas = ? "
                    + "where ID_Cliente = ?";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);
            stmt.setInt(1, cCliente.getCantidadVisitas());
            stmt.setInt(2, cCliente.getIdCliente());

            filasAfectadas = stmt.executeUpdate();
            stmt.close();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }
        if (filasAfectadas > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EliminarCliente(int idEmpleado) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "Update Cliente "
                    + "Set Baja_Logica = 0 "
                    + "WHERE ID_Cliente = ?";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);
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
