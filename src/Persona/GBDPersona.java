/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persona;

import Cliente.CCliente;
import Empleado.CEmpleado;
import Marca.CMarca;
import Modelos.GestorBDConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author German Bartoli
 */
public class GBDPersona {

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    ArrayList<CMarca> arrayListComboMarca;
    ArrayList<CMarca> alMarcas;

    public boolean ExisteDNIClienteAgregar(CCliente cCliente) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Persona "
                    + "WHERE DNI = ? "
                    + "and True_False = 1";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setInt(1, cCliente.getDni());

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

    public boolean ExisteDNIClienteEditar(CCliente cCliente) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Persona "
                    + "WHERE DNI = ? "
                    + "and True_False = 1 "
                    + "and ID_Persona != ?";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setInt(1, cCliente.getDni());
            stmt.setInt(2, cCliente.getIdPersona());

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

    public boolean ExisteDNIEmpleadoAgregar(CEmpleado cEmpleado) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Persona "
                    + "WHERE DNI = ? "
                    + "and True_False = 1";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setInt(1, cEmpleado.getDni());

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

    public boolean ExisteDNIEmpleadoEditar(CEmpleado cEmpleado) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Persona "
                    + "WHERE DNI = ? "
                    + "and True_False = 1 "
                    + "and ID_Persona != ?";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setInt(1, cEmpleado.getDni());
            stmt.setInt(2, cEmpleado.getIdPersona());

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

    public int ObtenerUltimoIDPersona() {
        int ultimoIDPersona = 0;
        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL = "SELECT MAX(ID_Persona) "
                    + "FROM Persona;";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ultimoIDPersona = rs.getInt(1);
            }
            rs.close();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return ultimoIDPersona;
    }

    public boolean AgregarPersonaCliente(CCliente cCliente) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "INSERT INTO Persona(DNI, Nombre, Apellido, Edad, Correo, Tel, True_False) "
                    + "VALUES (?,?,?,?,?,?,?)");
            stmt.setInt(1, cCliente.getDni());
            stmt.setString(2, cCliente.getNombre());
            stmt.setString(3, cCliente.getApellido());
            stmt.setInt(4, cCliente.getEdad());
            stmt.setString(5, cCliente.getCorreo());
            stmt.setString(6, cCliente.getTel());
            stmt.setBoolean(7, true);

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

    public boolean ModificarPersonaCliente(CCliente cCliente) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "UPDATE Persona "
                    + "SET DNI = ?, "
                    + "Nombre = ?, "
                    + "Apellido = ?, "
                    + "Edad = ?, "
                    + "Correo = ?, "
                    + "Tel = ? "
                    + "where ID_Persona = ?");
            stmt.setInt(1, cCliente.getDni());
            stmt.setString(2, cCliente.getNombre());
            stmt.setString(3, cCliente.getApellido());
            stmt.setInt(4, cCliente.getEdad());
            stmt.setString(5, cCliente.getCorreo());
            stmt.setString(6, cCliente.getTel());
            stmt.setInt(7, cCliente.getIdPersona());

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

    public boolean EliminarPersonaCliente(int idPersonaCliente) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "Update Persona "
                    + "set True_False = 0 "
                    + "where ID_Persona = ?");
            stmt.setInt(1, idPersonaCliente);

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

    public boolean AgregarPersonaEmpleado(CEmpleado cEmpleado) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "INSERT INTO Persona(dni, Nombre, Apellido, Edad, Correo, Tel, True_False) "
                    + "VALUES (?,?,?,?,?,?,?)");
            stmt.setInt(1, cEmpleado.getDni());
            stmt.setString(2, cEmpleado.getNombre());
            stmt.setString(3, cEmpleado.getApellido());
            stmt.setInt(4, cEmpleado.getEdad());
            stmt.setString(5, cEmpleado.getCorreo());
            stmt.setString(6, cEmpleado.getTel());
            stmt.setBoolean(7, true);

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

    public boolean ModificarPersonaEmpleado(CEmpleado cEmpleado) throws SQLException {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "UPDATE Persona "
                    + "SET DNI = ?, "
                    + "Nombre = ?, "
                    + "Apellido = ?, "
                    + "Edad = ?, "
                    + "Correo = ?, "
                    + "Tel = ? "
                    + "where ID_Persona = ?");
            stmt.setInt(1, cEmpleado.getDni());
            stmt.setString(2, cEmpleado.getNombre());
            stmt.setString(3, cEmpleado.getApellido());
            stmt.setInt(4, cEmpleado.getEdad());
            stmt.setString(5, cEmpleado.getCorreo());
            stmt.setString(6, cEmpleado.getTel());
            stmt.setInt(7, cEmpleado.getIdPersona());

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

    public boolean EliminarPersonaEmpleado(int idPersonaEmpleado) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "Update Persona "
                    + "set True_False = 0 "
                    + "where ID_Persona = ?");
            stmt.setInt(1, idPersonaEmpleado);

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
