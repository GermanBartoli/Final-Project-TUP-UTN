/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Marca;

import Modelos.GestorBDConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author German Bartoli
 */
public class GBDMarca {

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    ArrayList<CMarca> arrayListComboMarca;
    ArrayList<CMarca> alMarcas;

    public boolean ExisteNombreMarcaAgregar(CMarca marca) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Marca "
                    + "WHERE Marca = ? "
                    + "and True_False = 1";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setString(1, marca.getNombre());

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
    
        public boolean ExisteNombreMarcaEditar(CMarca marca) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Marca "
                    + "WHERE Marca = ? "
                    + "and True_False = 1 "
                    + "and ID_Marca != ?";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setString(1, marca.getNombre());
                        stmt.setInt(2, marca.getId_Marca());

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

    public CMarca ObtenerMarca(int idMarca) {
        CMarca marca = null;
        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL = "SELECT * "
                    + "FROM Marca "
                    + "WHERE  Id_Marca = " + idMarca;

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("Marca");

                marca = new CMarca(idMarca, nombre, true);
            }
            rs.close();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return marca;
    }

    public ArrayList<CMarca> CargarListaMarcas() {
        alMarcas = new ArrayList<CMarca>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL = "select * from Marca Where True_False = 1 Order by 2";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idMarca = rs.getInt("ID_Marca");
                String nombre = rs.getString("Marca");

                CMarca marca = new CMarca(idMarca, nombre, true);

                alMarcas.add(marca);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return alMarcas;
    }

    public ArrayList<CMarca> CargarComboMarcas() {

        arrayListComboMarca = new ArrayList<CMarca>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL = "select * "
                    + "from Marca "
                    + "Where True_False = 1 "
                    + "Order by Marca";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idMarca = rs.getInt("ID_Marca");
                String nombreMarca = rs.getString("Marca");
                boolean trueFalse = rs.getBoolean("True_False");

                CMarca marca = new CMarca(idMarca, nombreMarca, trueFalse);
                arrayListComboMarca.add(marca);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return arrayListComboMarca;
    }

    public boolean AgregarMarca(CMarca marca) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "INSERT INTO Marca(Marca, True_False) VALUES (?,?)");
            stmt.setString(1, marca.getNombre());
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

    public boolean ModificarMarca(CMarca marca) throws SQLException {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "UPDATE Marca SET Marca = ? where ID_Marca = ?");
            stmt.setString(1, marca.getNombre());
            stmt.setInt(2, marca.getId_Marca());

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

    public boolean EliminarMarca(int idMarca) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement("Update "
                    + "Marca Set True_False = 0 "
                    + "WHERE ID_Marca = ?");
            stmt.setInt(1, idMarca);

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
