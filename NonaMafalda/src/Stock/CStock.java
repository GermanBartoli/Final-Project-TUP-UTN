/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stock;

import java.util.Date;

/**
 *
 * @author German Bartoli
 */
public class CStock {

    private int idStock;
    private int idProducto;
    private double cantidad;

    private Date fechaElaboracion;
    private Date fechaVencimiento;

    private boolean trueFalse;

    public int getIdStock() {
        return idStock;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public boolean isTrue_False() {
        return trueFalse;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setTrue_False(boolean trueFalse) {
        this.trueFalse = trueFalse;
    }

    public CStock(int idStock, int idProducto, double cantidad, Date fechaElaboracion, Date fechaVencimiento,
            boolean trueFalse) {
        this.idStock = idStock;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.fechaElaboracion = fechaElaboracion;
        this.fechaVencimiento = fechaVencimiento;
        this.trueFalse = trueFalse;
    }

    public CStock(int idProducto, double cantidad, Date fechaElaboracion, Date fechaVencimiento, boolean trueFalse) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.fechaElaboracion = fechaElaboracion;
        this.fechaVencimiento = fechaVencimiento;
        this.trueFalse = trueFalse;
    }

    public CStock() {
    }
}
