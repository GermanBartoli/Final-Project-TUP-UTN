/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HorariosE;

import java.util.Date;
import java.sql.Time;

/**
 *
 * @author German Bartoli
 */
public class CHorariosE {

    private int idIngresoEgreso;
    private int idEmpleado;
    private Date diaTrabajado;
    private Time horaIngreso;
    private Time horaEgreso;
    private Time tiempoTrabajado;
    private boolean trueFalse;

    public int getIdIngresoEgreso() {
        return idIngresoEgreso;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public Date getDiaTrabajado() {
        return diaTrabajado;
    }

    public Time getHoraIngreso() {
        return horaIngreso;
    }

    public Time getHoraEgreso() {
        return horaEgreso;
    }

    public Time getTiempoTrabajado() {
        return tiempoTrabajado;
    }

    public boolean isTrueFalse() {
        return trueFalse;
    }

    public void setIdIngresoEgreso(int idIngresoEgreso) {
        this.idIngresoEgreso = idIngresoEgreso;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setDiaTrabajado(Date diaTrabajado) {
        this.diaTrabajado = diaTrabajado;
    }

    public void setHoraIngreso(Time horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public void setHoraEgreso(Time horaEgreso) {
        this.horaEgreso = horaEgreso;
    }

    public void setTiempoTrabajado(Time tiempoTrabajado) {
        this.tiempoTrabajado = tiempoTrabajado;
    }

    public void setTrueFalse(boolean trueFalse) {
        this.trueFalse = trueFalse;
    }

    public CHorariosE(int idIngresoEgreso, int idEmpleado, Date diaTrabajado, Time horaIngreso, Time horaEgreso, Time tiempoTrabajado, boolean trueFalse) {
        this.idIngresoEgreso = idIngresoEgreso;
        this.idEmpleado = idEmpleado;
        this.diaTrabajado = diaTrabajado;
        this.horaIngreso = horaIngreso;
        this.horaEgreso = horaEgreso;
        this.tiempoTrabajado = tiempoTrabajado;
        this.trueFalse = trueFalse;
    }

    public CHorariosE(int idIngresoEgreso, int idEmpleado, Date diaTrabajado, Time horaIngreso, Time horaEgreso, boolean trueFalse) {
        this.idIngresoEgreso = idIngresoEgreso;
        this.idEmpleado = idEmpleado;
        this.diaTrabajado = diaTrabajado;
        this.horaIngreso = horaIngreso;
        this.horaEgreso = horaEgreso;
        this.tiempoTrabajado = tiempoTrabajado;
        this.trueFalse = trueFalse;
    }

    @Override
    public String toString() {
        return "CEmpleadoHorario{" + "idIngresoEgreso=" + idIngresoEgreso + ", idEmpleado=" + idEmpleado + ", diaTrabajado=" + diaTrabajado + ", horaIngreso=" + horaIngreso + ", horaEgreso=" + horaEgreso + ", tiempoTrabajado=" + tiempoTrabajado + ", trueFalse=" + trueFalse + '}';
    }

}
