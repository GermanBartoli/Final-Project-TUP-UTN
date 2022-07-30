/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormaPago;

/**
 *
 * @author German Bartoli
 */
public class CFormaPago {

    private int idFormaPago;
    private String formaPago;
    private boolean trueFalse;

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public boolean isTrueFalse() {
        return trueFalse;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public void setTrueFalse(boolean trueFalse) {
        this.trueFalse = trueFalse;
    }

    public CFormaPago(int idFormaPago, String formaPago, boolean trueFalse) {
        this.idFormaPago = idFormaPago;
        this.formaPago = formaPago;
        this.trueFalse = trueFalse;
    }

    public CFormaPago() {
    }

//    @Override
//    public String toString() {
//        return "CFormaPago{" + "idFormaPago=" + idFormaPago + ", formaPago=" + formaPago + ", trueFalse=" + trueFalse + '}';
//    }
    @Override
    public String toString() {
        return formaPago;
    }
}
