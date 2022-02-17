/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Empleado;

import Persona.CPersona;

/**
 *
 * @author German Bartoli
 */
public class CEmpleado extends CPersona {

    private int idEmpleado;
    private boolean trueFalseEmpleado;

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public boolean isTrueFalseEmpleado() {
        return trueFalseEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setTrueFalseEmpleado(boolean trueFalseEmpleado) {
        this.trueFalseEmpleado = trueFalseEmpleado;
    }

    public CEmpleado(int idPersona, int dni, String nombre, String apellido, int edad,
            String correo, String tel, boolean trueFalsePersona, int idEmpleado, boolean trueFalseEmpleado) {
        super(idPersona, dni, nombre, apellido, edad, correo, tel, trueFalsePersona);
        this.idEmpleado = idEmpleado;
        this.trueFalseEmpleado = trueFalseEmpleado;
    }

//    @Override
//    public String toString() {
//        return "Empleado{" + "idEmpleado=" + idEmpleado + ", trueFalseEmpleado=" + trueFalseEmpleado + '}';
//    }

    @Override
    public String toString() {
        return apellido + " " + nombre + " " + dni;
    }
}
