/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Producto;

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
public class GBDProducto {

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    ArrayList<CProducto> arrayListComboProductos;
    ArrayList<CProducto> arrayListProductos;

    public boolean ExisteNombreProductoAgregar(CProducto cProducto) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();

            String sql
                    = "select * "
                    + "from Producto "
                    + "WHERE Nombre = ? "
                    + "and True_False = 1";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setString(1, cProducto.getNombre());

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

    public boolean ExisteNombreProductoEditar(CProducto cProducto) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Producto "
                    + "WHERE Nombre = ? "
                    + "and True_False = 1 "
                    + "and ID_Producto != ?";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setString(1, cProducto.getNombre());
            stmt.setInt(2, cProducto.getIdProducto());

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

    public CProducto ObtenerProductoXID(int idProducto) {
        CProducto producto = null;
        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "SELECT "
                    + "producto.*, "
                    + "marca.Marca "
                    + "FROM Producto producto "
                    + "inner join Marca marca on producto.ID_Marca = marca.ID_Marca "
                    + "WHERE  ID_Producto = " + idProducto;

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idTipoPoroducto = rs.getInt("ID_Tipo_Producto");
                String nombre = rs.getString("Nombre");
                double peso = rs.getDouble("Peso");
                double precio = rs.getDouble("Precio");
                int unidades = rs.getInt("Unidades");
                int platos = rs.getInt("Platos");
                String ingredientes = rs.getString("Ingredientes");
                String coccion = rs.getString("Coccion");
                int stockMinimo = rs.getInt("Stock_Minimo");
                boolean trueFalse = rs.getBoolean("True_False");

                int idMarca = rs.getInt("ID_Marca");
                String nombreMarca = rs.getString("Marca");

                CMarca marca = new CMarca();
                marca.setId_Marca(idMarca);
                marca.setNombre(nombreMarca);

                producto = new CProducto(idProducto, marca, idTipoPoroducto, nombre,
                        peso, precio, unidades, platos, ingredientes, coccion, stockMinimo, trueFalse);

            }
            rs.close();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return producto;
    }

    public ArrayList<CProducto> CargarComboProductos() {

        arrayListComboProductos = new ArrayList<CProducto>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "select "
                    + "ID_Producto, "
                    + "ID_Tipo_Producto, "
                    + "producto.Nombre, "
                    + "Peso, "
                    + "Precio, "
                    + "Unidades, "
                    + "Platos, "
                    + "Ingredientes, "
                    + "Coccion, "
                    + "Stock_Minimo, "
                    + "producto.True_False, "
                    + "marca.*"
                    + "from Producto producto "
                    + "inner join Marca marca on producto.ID_Marca = marca.id_Marca "
                    + "Where producto.True_False = 1 "
                    + "Order By 4";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idProducto = rs.getInt("ID_Producto");
                int idTipoPoroducto = rs.getInt("ID_Tipo_Producto");
                String nombre = rs.getString("Nombre");
                double peso = rs.getDouble("Peso");
                double precio = rs.getDouble("Precio");
                int unidades = rs.getInt("Unidades");
                int platos = rs.getInt("Platos");
                String ingredientes = rs.getString("Ingredientes");
                String coccion = rs.getString("Coccion");
                int stockMinimo = rs.getInt("Stock_Minimo");
                boolean trueFalse = rs.getBoolean("True_False");

                int idMarca = rs.getInt("ID_Marca");
                String nombreMarca = rs.getString("Marca");

                CMarca marca = new CMarca();
                marca.setId_Marca(idMarca);
                marca.setNombre(nombreMarca);

                CProducto producto = new CProducto(idProducto, marca, idTipoPoroducto, nombre,
                        peso, precio, unidades, platos, ingredientes, coccion, stockMinimo, trueFalse);

                arrayListComboProductos.add(producto);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }
        return arrayListComboProductos;
    }

    public ArrayList<CProducto> CargarListaProductos() {
        arrayListProductos = new ArrayList<CProducto>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "select "
                    + "ID_Producto, "
                    + "ID_Tipo_Producto, "
                    + "Producto.Nombre, "
                    + "Peso, "
                    + "Precio, "
                    + "Unidades, "
                    + "Platos, "
                    + "Ingredientes, "
                    + "Coccion, "
                    + "Stock_Minimo, "
                    + "producto.True_False, "
                    + "Marca.* "
                    + "from Producto producto "
                    + "inner join Marca marca on producto.ID_Marca = marca.id_Marca "
                    + "Where producto.True_False = 1 "
                    + "Order by 3; ";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int idProducto = rs.getInt("ID_Producto");
                int idTipoPoroducto = rs.getInt("ID_Tipo_Producto");
                String nombre = rs.getString("Nombre");
                double peso = rs.getDouble("Peso");
                double precio = rs.getDouble("Precio");
                int unidades = rs.getInt("Unidades");
                int platos = rs.getInt("Platos");
                String ingredientes = rs.getString("Ingredientes");
                String coccion = rs.getString("Coccion");
                int stockMinimo = rs.getInt("Stock_Minimo");
                boolean trueFalse = rs.getBoolean("True_False");

                int idMarca = rs.getInt("ID_Marca");
                String nombreMarca = rs.getString("Marca");

                CMarca marca = new CMarca();
                marca.setId_Marca(idMarca);
                marca.setNombre(nombreMarca);

                CProducto producto = new CProducto(idProducto, marca, idTipoPoroducto, nombre,
                        peso, precio, unidades, platos, ingredientes, coccion, stockMinimo, trueFalse);
                arrayListProductos.add(producto);
            }

            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return arrayListProductos;
    }

    public boolean AgregarProducto(CProducto producto) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "INSERT INTO "
                    + "Producto("
                    + "id_Marca, "
                    + "ID_Tipo_Producto, "
                    + "Nombre, Peso, "
                    + "Precio, "
                    + "Unidades, "
                    + "Platos, "
                    + "Ingredientes, "
                    + "Coccion, "
                    + "Stock_Minimo, "
                    + "True_False) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, producto.getMarca().getId_Marca());
            stmt.setInt(2, producto.getIdTipoProducto());
            stmt.setString(3, producto.getNombre());
            stmt.setDouble(4, producto.getPeso());
            stmt.setDouble(5, producto.getPrecio());
            stmt.setInt(6, producto.getUnidades());
            stmt.setInt(7, producto.getPlatos());
            stmt.setString(8, producto.getIngredientes());
            stmt.setString(9, producto.getCoccion());
            stmt.setInt(10, producto.getStockMinimo());
            stmt.setBoolean(11, true);

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

    public boolean ModificarProducto(CProducto producto) throws SQLException {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "UPDATE Producto "
                    + "SET ID_Marca = ?, "
                    + "ID_Tipo_Producto = ?, "
                    + "Nombre = ?, "
                    + "Peso = ?, "
                    + "Precio= ?, "
                    + "Unidades= ?, "
                    + "Platos= ?, "
                    + "Ingredientes = ?, "
                    + "Coccion = ?, "
                    + "Stock_Minimo = ? "
                    + "where ID_Producto = ?");
            stmt.setInt(1, producto.getMarca().getId_Marca());
            stmt.setInt(2, producto.getIdTipoProducto());
            stmt.setString(3, producto.getNombre());
            stmt.setDouble(4, producto.getPeso());
            stmt.setDouble(5, producto.getPrecio());
            stmt.setInt(6, producto.getUnidades());
            stmt.setInt(7, producto.getPlatos());
            stmt.setString(8, producto.getIngredientes());
            stmt.setString(9, producto.getCoccion());
            stmt.setInt(10, producto.getStockMinimo());
            stmt.setInt(11, producto.getIdProducto());

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

    public boolean EliminarProducto(int idProducto) throws SQLException {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement("UPDATE Producto "
                    + "SET True_False = 0 "
                    + "where ID_Producto = ?");
            stmt.setInt(1, idProducto);

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
