/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HorariosE;

import Modelos.GestorBDConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author German Bartoli
 */
public class GBDHorariosE {

    GestorBDConexion gestorBDConexion = new GestorBDConexion();

    ArrayList<CHorariosE> listadoCEmpleadoHorarios;

    public int ObtenerUltimoIDIngresoEgreso() {
        int ultimoIDPersona = 0;
        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL
                    = "SELECT "
                    + "MAX(ID_Ingreso_Egreso) "
                    + "FROM Ingreso_Egreso;";

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

    public boolean ExisteHorario(CHorariosE cHorariosE) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Ingreso_Egreso "
                    + "WHERE ID_Empleado = ? "
                    + "and Dia_Trabajado = CAST(? as DATE) "
                    + "and (Hora_Ingreso = CAST(? as TIME(0)) "
                    + "or Hora_Egreso = CAST(? as TIME(0))) "
                    + "and True_False = 1";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setInt(1, cHorariosE.getIdEmpleado());

            java.util.Date dateDiaTrabajado = cHorariosE.getDiaTrabajado();
            java.sql.Date sqlDateDiaTrabajado = new java.sql.Date(dateDiaTrabajado.getTime());
            stmt.setDate(2, sqlDateDiaTrabajado);

            java.sql.Time timeHoraIngreso = cHorariosE.getHoraIngreso();
            java.sql.Time sqlTimeHoraIngreso = new java.sql.Time(timeHoraIngreso.getTime());
            stmt.setTime(3, sqlTimeHoraIngreso);

            java.sql.Time timeHoraEgreso = cHorariosE.getHoraEgreso();
            java.sql.Time sqlTimeHoraEgreso = new java.sql.Time(timeHoraEgreso.getTime());
            stmt.setTime(4, sqlTimeHoraEgreso);

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

    public boolean HorarioYaAsignadoAgregar(CHorariosE cHorariosE) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Ingreso_Egreso "
                    + "WHERE ID_Empleado = ? "
                    + "and Dia_Trabajado = ? "
                    + "and ((CAST(? as TIME(0)) BETWEEN Hora_Ingreso AND Hora_Egreso) "
                    + "or (CAST(? as TIME(0)) BETWEEN Hora_Ingreso AND Hora_Egreso) "
                    + "or (Hora_Ingreso BETWEEN CAST(? as TIME(0)) AND CAST(? as TIME(0))) "
                    + "or (Hora_Egreso BETWEEN CAST(? as TIME(0)) AND CAST(? as TIME(0)))) "
                    + "and True_False = 1";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setInt(1, cHorariosE.getIdEmpleado());

            java.util.Date dateDiaTrabajado = cHorariosE.getDiaTrabajado();
            java.sql.Date sqlDateDiaTrabajado = new java.sql.Date(dateDiaTrabajado.getTime());
            stmt.setDate(2, sqlDateDiaTrabajado);

            java.sql.Time timeHoraIngreso = cHorariosE.getHoraIngreso();
            java.sql.Time sqlTimeHoraIngreso = new java.sql.Time(timeHoraIngreso.getTime());
            stmt.setTime(3, sqlTimeHoraIngreso);

            java.sql.Time timeHoraEgreso = cHorariosE.getHoraEgreso();
            java.sql.Time sqlTimeHoraEgreso = new java.sql.Time(timeHoraEgreso.getTime());
            stmt.setTime(4, sqlTimeHoraEgreso);

            stmt.setTime(5, sqlTimeHoraIngreso);
            stmt.setTime(6, sqlTimeHoraEgreso);

            stmt.setTime(7, sqlTimeHoraIngreso);
            stmt.setTime(8, sqlTimeHoraEgreso);

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

    public boolean HorarioYaAsignadoEditar(CHorariosE cHorariosE) {
        boolean existe = false;
        try {
            gestorBDConexion.AbrirConexion();
            String sql = "select * "
                    + "from Ingreso_Egreso "
                    + "WHERE ID_Empleado = ? "
                    + "and Dia_Trabajado = ? "
                    + "and ((CAST(? as TIME(0)) BETWEEN Hora_Ingreso AND Hora_Egreso) "
                    + "or (CAST(? as TIME(0)) BETWEEN Hora_Ingreso AND Hora_Egreso) "
                    + "or (Hora_Ingreso BETWEEN CAST(? as TIME(0)) AND CAST(? as TIME(0))) "
                    + "or (Hora_Egreso BETWEEN CAST(? as TIME(0)) AND CAST(? as TIME(0)))) "
                    + "and ID_Ingreso_Egreso != ? "
                    + "and True_False = 1";
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sql);
            stmt.setInt(1, cHorariosE.getIdEmpleado());

            java.util.Date dateDiaTrabajado = cHorariosE.getDiaTrabajado();
            java.sql.Date sqlDateDiaTrabajado = new java.sql.Date(dateDiaTrabajado.getTime());
            stmt.setDate(2, sqlDateDiaTrabajado);

            java.sql.Time timeHoraIngreso = cHorariosE.getHoraIngreso();
            java.sql.Time sqlTimeHoraIngreso = new java.sql.Time(timeHoraIngreso.getTime());
            stmt.setTime(3, sqlTimeHoraIngreso);

            java.sql.Time timeHoraEgreso = cHorariosE.getHoraEgreso();
            java.sql.Time sqlTimeHoraEgreso = new java.sql.Time(timeHoraEgreso.getTime());
            stmt.setTime(4, sqlTimeHoraEgreso);

            stmt.setTime(5, sqlTimeHoraIngreso);
            stmt.setTime(6, sqlTimeHoraEgreso);

            stmt.setTime(7, sqlTimeHoraIngreso);
            stmt.setTime(8, sqlTimeHoraEgreso);

            stmt.setInt(9, cHorariosE.getIdIngresoEgreso());

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

    public ArrayList<CHorariosE> CargarTablaHorarios(int idEmpleadoSeleccionado) {
        listadoCEmpleadoHorarios = new ArrayList<CHorariosE>();

        try {
            gestorBDConexion.AbrirConexion();

            String sentenciaSQL = "select "
                    + "ID_Ingreso_Egreso, "
                    + "ID_Empleado, "
                    + "Dia_Trabajado, "
                    + "Hora_Ingreso, "
                    + "Hora_Egreso, "
                    + "CONVERT(time(0),DATEADD(s, DATEDIFF(s,Hora_Ingreso,Hora_Egreso),'00:00:00')), "
                    + "True_False "
                    + "from Ingreso_Egreso "
                    + "where ID_Empleado = ? "
                    + "AND True_False = 1 "
                    + "Order by 3 desc,4 desc";

            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(sentenciaSQL);
            stmt.setInt(1, idEmpleadoSeleccionado);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // int idStock = rs.getInt(ID_Stock);
                int ID_Ingreso_Egreso = rs.getInt(1);
                int idEmpleado = rs.getInt(2);
                Date diaTrabajado = rs.getDate(3);
                Time horaIngreso = rs.getTime(4);
                Time horaEgreso = rs.getTime(5);
                Time tiempoTrabajado = rs.getTime(6);

                CHorariosE cEmpleadoHorario = new CHorariosE(ID_Ingreso_Egreso, idEmpleado, diaTrabajado, horaIngreso, horaEgreso, tiempoTrabajado, true);

                listadoCEmpleadoHorarios.add(cEmpleadoHorario);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        return listadoCEmpleadoHorarios;
    }

    public boolean AgregarHorario(CHorariosE cEmpleadoHorario) {
        boolean b = false;
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "INSERT INTO Ingreso_Egreso("
                    + "ID_Empleado, "
                    + "Dia_Trabajado, "
                    + "Hora_Ingreso, "
                    + "Hora_Egreso, "
                    + "True_False) "
                    + "VALUES (?,?,?,?,?)");
            stmt.setInt(1, cEmpleadoHorario.getIdEmpleado());

            java.util.Date dateDiaTrabajado = cEmpleadoHorario.getDiaTrabajado();
            java.sql.Date sqlDateDiaTrabajado = new java.sql.Date(dateDiaTrabajado.getTime());
            stmt.setDate(2, sqlDateDiaTrabajado);

            java.sql.Time timeHoraIngreso = cEmpleadoHorario.getHoraIngreso();
            java.sql.Time sqlTimeHoraIngreso = new java.sql.Time(timeHoraIngreso.getTime());
            stmt.setTime(3, sqlTimeHoraIngreso);

            java.sql.Time timeHoraEgreso = cEmpleadoHorario.getHoraEgreso();
            java.sql.Time sqlTimeHoraEgreso = new java.sql.Time(timeHoraEgreso.getTime());
            stmt.setTime(4, sqlTimeHoraEgreso);

            stmt.setBoolean(5, true);

            filasAfectadas = stmt.executeUpdate();
            stmt.close();

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            gestorBDConexion.CerrarConexion();
        }

        if (filasAfectadas > 0) {
            return b = true;
        } else {
            return b;
        }
    }

    public boolean ModificarHorario(CHorariosE cEmpleadoHorario) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement(
                    "UPDATE Ingreso_Egreso "
                    + "SET Dia_Trabajado = ?, "
                    + "Hora_Ingreso = ?, "
                    + "Hora_Egreso = ? "
                    + "where ID_Ingreso_Egreso = ?"
            );

            java.util.Date dateDiaTrabajado = cEmpleadoHorario.getDiaTrabajado();
            java.sql.Date sqlDateDiaTrabajado = new java.sql.Date(dateDiaTrabajado.getTime());
            stmt.setDate(1, sqlDateDiaTrabajado);

            java.sql.Time timeHoraIngreso = cEmpleadoHorario.getHoraIngreso();
            java.sql.Time sqlDateHoraIngreso = new java.sql.Time(timeHoraIngreso.getTime());
            stmt.setTime(2, sqlDateHoraIngreso);

            java.sql.Time timeHoraEgreso = cEmpleadoHorario.getHoraEgreso();
            java.sql.Time sqlDateHoraEgreso = new java.sql.Time(timeHoraEgreso.getTime());
            stmt.setTime(3, sqlDateHoraEgreso);

            stmt.setInt(4, cEmpleadoHorario.getIdIngresoEgreso());

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

    public boolean EliminarHorario(int idIngresoEgreso) {
        int filasAfectadas = 0;
        try {
            gestorBDConexion.AbrirConexion();
            PreparedStatement stmt = gestorBDConexion.getConexion().prepareStatement("UPDATE Ingreso_Egreso "
                    + "SET True_False = 0 "
                    + "where ID_Ingreso_Egreso = ?");
            stmt.setInt(1, idIngresoEgreso);

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
