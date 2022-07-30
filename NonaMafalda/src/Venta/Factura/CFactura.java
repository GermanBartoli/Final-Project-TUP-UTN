/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venta.Factura;

import java.util.Date;
import java.sql.Time;

/**
 *
 * @author German Bartoli
 */
public class CFactura {

    private int ID_Factura;
    private int ID_Empleado;
    private int ID_Cliente;
    private int ID_Forma_De_Pago;
    private Date fecha;
    private Time hora;
    private double Precio_Final;
    private double Pago_Cliente;
    private double Vuelto;
//    private String Observaciones;
    private boolean Baja_Logica;

    public int getID_Factura() {
        return ID_Factura;
    }

    public int getID_Empleado() {
        return ID_Empleado;
    }

    public int getID_Cliente() {
        return ID_Cliente;
    }

    public int getID_Forma_De_Pago() {
        return ID_Forma_De_Pago;
    }

    public Date getFecha() {
        return fecha;
    }

    public Time getHora() {
        return hora;
    }

    public double getPrecio_Final() {
        return Precio_Final;
    }

    public double getPago_Cliente() {
        return Pago_Cliente;
    }

    public double getVuelto() {
        return Vuelto;
    }

//    public String getObservaciones() {
//        return Observaciones;
//    }

    public boolean isBaja_Logica() {
        return Baja_Logica;
    }

    public void setID_Factura(int ID_Factura) {
        this.ID_Factura = ID_Factura;
    }

    public void setID_Empleado(int ID_Empleado) {
        this.ID_Empleado = ID_Empleado;
    }

    public void setID_Cliente(int ID_Cliente) {
        this.ID_Cliente = ID_Cliente;
    }

    public void setID_Forma_De_Pago(int ID_Forma_De_Pago) {
        this.ID_Forma_De_Pago = ID_Forma_De_Pago;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public void setPrecio_Final(double Precio_Final) {
        this.Precio_Final = Precio_Final;
    }

    public void setPago_Cliente(double Pago_Cliente) {
        this.Pago_Cliente = Pago_Cliente;
    }

    public void setVuelto(double Vuelto) {
        this.Vuelto = Vuelto;
    }

//    public void setObservaciones(String Observaciones) {
//        this.Observaciones = Observaciones;
//    }

    public void setBaja_Logica(boolean Baja_Logica) {
        this.Baja_Logica = Baja_Logica;
    }

    public CFactura(int ID_Factura, int ID_Empleado, int ID_Cliente, int ID_Forma_De_Pago, Date fecha, Time hora, double Precio_Final, 
            double Pago_Cliente, double Vuelto, boolean Baja_Logica) {
        this.ID_Factura = ID_Factura;
        this.ID_Empleado = ID_Empleado;
        this.ID_Cliente = ID_Cliente;
        this.ID_Forma_De_Pago = ID_Forma_De_Pago;
        this.fecha = fecha;
        this.hora = hora;
        this.Precio_Final = Precio_Final;
        this.Pago_Cliente = Pago_Cliente;
        this.Vuelto = Vuelto;
//        this.Observaciones = Observaciones;
        this.Baja_Logica = Baja_Logica;
    }
    
        public CFactura(int ID_Empleado, int ID_Cliente, int ID_Forma_De_Pago, Date fecha, Time hora, double Precio_Final, 
                double Pago_Cliente, double Vuelto, boolean Baja_Logica) {
        this.ID_Empleado = ID_Empleado;
        this.ID_Cliente = ID_Cliente;
        this.ID_Forma_De_Pago = ID_Forma_De_Pago;
        this.fecha = fecha;
        this.hora = hora;
        this.Precio_Final = Precio_Final;
        this.Pago_Cliente = Pago_Cliente;
        this.Vuelto = Vuelto;
//        this.Observaciones = Observaciones;
        this.Baja_Logica = Baja_Logica;
    }
}
