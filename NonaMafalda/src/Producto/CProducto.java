/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Producto;

import Marca.CMarca;

/**
 *
 * @author German Bartoli
 */
public class CProducto {

    private Integer idProducto;
    private CMarca marca;
    //private int idMarca;
    private Integer idTipoProducto;
    private String nombre;
    private Double peso;
    private Double precio;
    private Integer unidades;
    private Integer platos;
    private String ingredientes;
    private String coccion;
    private Integer stockMinimo;
    private Boolean trueFalse;

    public Integer getIdProducto() {
        return idProducto;
    }

//    public int getIdMarca() {
//        return idMarca;
//    }

    public CMarca getMarca() {
        return marca;
    }
    
    public Integer getIdTipoProducto() {
        return idTipoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPeso() {
        return peso;
    }

    public Double getPrecio() {
        return precio;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public Integer getPlatos() {
        return platos;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getCoccion() {
        return coccion;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public Boolean isTrueFalse() {
        return trueFalse;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

//    public void setIdMarca(int idMarca) {
//        this.idMarca = idMarca;
//    }

    public void setMarca(CMarca marca) {
        this.marca = marca;
    }
    
    public void setIdTipoProducto(Integer idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public void setPlatos(Integer platos) {
        this.platos = platos;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setCoccion(String coccion) {
        this.coccion = coccion;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public void setTrueFalse(Boolean trueFalse) {
        this.trueFalse = trueFalse;
    }

    public CProducto(Integer idProducto, CMarca marca, Integer idTipoProducto, String nombre, Double peso,
            Double precio, Integer unidades, Integer platos, String ingredientes, String coccion, Integer stockMinimo, Boolean trueFalse) {
        this.idProducto = idProducto;
        this.marca = marca;
        this.idTipoProducto = idTipoProducto;
        this.nombre = nombre;
        this.peso = peso;
        this.precio = precio;
        this.unidades = unidades;
        this.platos = platos;
        this.ingredientes = ingredientes;
        this.coccion = coccion;
        this.stockMinimo = stockMinimo;
        this.trueFalse = trueFalse;
    }

    public CProducto(CMarca marca, int idTipoProducto, String nombre, double peso,
            double precio, int unidades, int platos, String ingredientes, String coccion, int stockMinimo, boolean trueFalse) {
         this.marca = marca;
        this.idTipoProducto = idTipoProducto;
        this.nombre = nombre;
        this.peso = peso;
        this.precio = precio;
        this.unidades = unidades;
        this.platos = platos;
        this.ingredientes = ingredientes;
        this.coccion = coccion;
        this.stockMinimo = stockMinimo;
        this.trueFalse = trueFalse;
    }

//    @Override
//    public String toString() {
//        return "CProducto{" + "idProducto=" + idProducto + ", idMarca=" + idMarca + ", idTipoProducto=" + idTipoProducto + ", nombre=" + nombre + ", peso=" + peso + ", precio=" + precio + ", unidades=" + unidades + ", platos=" + platos + ", ingredientes=" + ingredientes + ", coccion=" + coccion + '}';
//    }
    @Override
    public String toString() {
        return nombre + " $" + precio;
    }
}
