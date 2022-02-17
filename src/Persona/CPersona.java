/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persona;

/**
 *
 * @author German Bartoli
 */
public class CPersona {

    protected int idPersona;
    protected int dni;
    protected String nombre;
    protected String apellido;
    protected int edad;
    protected String correo;
    protected String tel;
    protected boolean trueFalsePersona;

    public int getIdPersona() {
        return idPersona;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTel() {
        return tel;
    }

    public boolean isTrueFalsePersona() {
        return trueFalsePersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setTrueFalsePersona(boolean trueFalsePersona) {
        this.trueFalsePersona = trueFalsePersona;
    }

    public CPersona(int idPersona, int dni, String nombre, String apellido, int edad, String correo, String tel, boolean trueFalsePersona) {
        this.idPersona = idPersona;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.correo = correo;
        this.tel = tel;
        this.trueFalsePersona = trueFalsePersona;
    }

    @Override
    public String toString() {
        return "CPersona{" + "idPersona=" + idPersona + ", dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + ", correo=" + correo + ", tel=" + tel + ", trueFalsePersona=" + trueFalsePersona + '}';
    }

}
