/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Venta.DetalleFactura;

/**
 *
 * @author German Bartoli
 */
public class CDetalleFactura {

    private int ID_Detalle_Factura;
    private int ID_Factura;
    private int ID_Producto;
    private double Cantidad;
    private double Precio_Unitario;
    private double Sub_Total;
    private boolean Baja_Logica;

    public int getID_Detalle_Factura() {
        return ID_Detalle_Factura;
    }

    public int getID_Factura() {
        return ID_Factura;
    }

    public int getID_Producto() {
        return ID_Producto;
    }

    public double getCantidad() {
        return Cantidad;
    }

    public double getPrecio_Unitario() {
        return Precio_Unitario;
    }

    public double getSub_Total() {
        return Sub_Total;
    }

    public boolean isBaja_Logica() {
        return Baja_Logica;
    }

    public void setID_Detalle_Factura(int ID_Detalle_Factura) {
        this.ID_Detalle_Factura = ID_Detalle_Factura;
    }

    public void setID_Factura(int ID_Factura) {
        this.ID_Factura = ID_Factura;
    }

    public void setID_Producto(int ID_Producto) {
        this.ID_Producto = ID_Producto;
    }

    public void setCantidad(double Cantidad) {
        this.Cantidad = Cantidad;
    }

    public void setPrecio_Unitario(double Precio_Unitario) {
        this.Precio_Unitario = Precio_Unitario;
    }

    public void setSub_Total(double Sub_Total) {
        this.Sub_Total = Sub_Total;
    }

    public void setBaja_Logica(boolean Baja_Logica) {
        this.Baja_Logica = Baja_Logica;
    }

    public CDetalleFactura(int ID_Detalle_Factura, int ID_Factura, int ID_Producto, double Cantidad, double Precio_Unitario, double Sub_Total, boolean Baja_Logica) {
        this.ID_Detalle_Factura = ID_Detalle_Factura;
        this.ID_Factura = ID_Factura;
        this.ID_Producto = ID_Producto;
        this.Cantidad = Cantidad;
        this.Precio_Unitario = Precio_Unitario;
        this.Sub_Total = Sub_Total;
        this.Baja_Logica = Baja_Logica;
    }

    public CDetalleFactura(int ID_Factura, int ID_Producto, double Cantidad, double Precio_Unitario, double Sub_Total, boolean Baja_Logica) {
        this.ID_Factura = ID_Factura;
        this.ID_Producto = ID_Producto;
        this.Cantidad = Cantidad;
        this.Precio_Unitario = Precio_Unitario;
        this.Sub_Total = Sub_Total;
        this.Baja_Logica = Baja_Logica;
    }
    
        public CDetalleFactura(int ID_Producto, double Cantidad, double Precio_Unitario, double Sub_Total, boolean Baja_Logica) {
        this.ID_Producto = ID_Producto;
        this.Cantidad = Cantidad;
        this.Precio_Unitario = Precio_Unitario;
        this.Sub_Total = Sub_Total;
        this.Baja_Logica = Baja_Logica;
    }

    public CDetalleFactura() {

    }

}
