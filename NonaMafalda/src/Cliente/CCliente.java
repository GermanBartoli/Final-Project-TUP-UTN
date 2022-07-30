/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Persona.CPersona;

/**
 *
 * @author German Bartoli
 */
public class CCliente extends CPersona {

    private int idCliente;
    private int cantidadVisitas;
    private boolean trueFalseCliente;

    public int getIdCliente() {
        return idCliente;
    }

    public int getCantidadVisitas() {
        return cantidadVisitas;
    }

    public boolean isTrueFalseCliente() {
        return trueFalseCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setCantidadVisitas(int cantidadVisitas) {
        this.cantidadVisitas = cantidadVisitas;
    }

    public void setTrueFalseCliente(boolean trueFalseCliente) {
        this.trueFalseCliente = trueFalseCliente;
    }

    public CCliente(int idPersona, int dni, String nombre, String apellido, int edad, String correo,
            String tel, boolean trueFalsePersona, int idCliente, int cantidadVisitas, boolean trueFalseCliente) {
        super(idPersona, dni, nombre, apellido, edad, correo, tel, trueFalsePersona);
        this.idCliente = idCliente;
        this.cantidadVisitas = cantidadVisitas;
        this.trueFalseCliente = trueFalseCliente;
    }

//    @Override
//    public String toString() {
//        return "Cliente{" + "idCliente=" + idCliente + ", cantidadVisitas=" + cantidadVisitas + ", trueFalseCliente=" + trueFalseCliente + '}';
//    }
    
        @Override
    public String toString() {
        return nombre + " " + apellido + " " + dni;
    }
}
