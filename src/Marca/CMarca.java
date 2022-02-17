/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Marca;

/**
 *
 * @author German Bartoli
 */
public class CMarca {

    private int idMarca;
    private String nombre;

    private boolean trueFalse;

    public int getId_Marca() {
        return idMarca;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isTrueFalse() {
        return trueFalse;
    }

    public void setId_Marca(int id_Marca) {
        this.idMarca = id_Marca;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTrueFalse(boolean trueFalse) {
        this.trueFalse = trueFalse;
    }

    public CMarca(int id_Marca, String nombre, boolean trueFalse) {
        this.idMarca = id_Marca;
        this.nombre = nombre;
        this.trueFalse = trueFalse;
    }

    public CMarca() {
    }

    @Override
    public String toString() {
        return nombre;
    }
}
