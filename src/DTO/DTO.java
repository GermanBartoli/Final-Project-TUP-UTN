/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.util.Date;

/**
 *
 * @author German Bartoli
 */
public class DTO {

    //TotalRecaudadoXDias
    private String fechaFactura;
    private double SUMprecioFinalFactura;

    public String getFechaFactura() {
        return fechaFactura;
    }

    public double getSUMprecioFinalFactura() {
        return SUMprecioFinalFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public void setSUMprecioFinalFactura(double SUMprecioFinalFactura) {
        this.SUMprecioFinalFactura = SUMprecioFinalFactura;
    }

    //CantidadTotalVendidaXProducto
    private double SUMcantidad_Detalle_Factura;
    private double SUMsubtotal;

    public double getSUMCantidad_Detalle_Factura() {
        return SUMcantidad_Detalle_Factura;
    }

    public double getSUMSubtotal() {
        return SUMsubtotal;
    }

    public void setSUMCantidad_Detalle_Factura(double SUMcantidad_Detalle_Factura) {
        this.SUMcantidad_Detalle_Factura = SUMcantidad_Detalle_Factura;
    }

    public void setSUMSubtotal(double SUMsubtotal) {
        this.SUMsubtotal = SUMsubtotal;
    }

    private int idProducto;
    private String nombreProducto;
    private int stockMinimo;
    private int idMarca;
    private String marca;
    private int idTipoProducto;
    private String tipoProducto;
    private int idStock;
    private double cantidadStock;
    private Date fechaElaboracion;
    private Date fechaVencimiento;
    private int diasParaVencer;
    private int diasVencido;
    private boolean bajaLogicaStock; //1 baja logica por cada uno

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public String getMarca() {
        return marca;
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public int getIdStock() {
        return idStock;
    }

    public double getCantidadStock() {
        return cantidadStock;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public int getDiasParaVencer() {
        return diasParaVencer;
    }

    public int getDiasVencido() {
        return diasVencido;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public void setCantidadStock(double cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setDiasParaVencer(int diasParaVencer) {
        this.diasParaVencer = diasParaVencer;
    }

    public void setDiasVencido(int diasVencido) {
        this.diasVencido = diasVencido;
    }

    public DTO() {

    }

}
