/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TipoProducto;

/**
 *
 * @author German Bartoli
 */
public class CTipoProducto {

    private int idTipoProducto;
    private String tipoProducto;
    private Boolean trueFalse ;

    CTipoProducto() {
    }

    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }
    public Boolean getTrueFalse() {
        return trueFalse;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public void setNombre(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
    
        public void setTrueFalse(Boolean trueFalse) {
        this.trueFalse = trueFalse;
    }

    public CTipoProducto(int idTipoProducto, String tipoProducto, boolean trueFalse) {
        this.idTipoProducto = idTipoProducto;
        this.tipoProducto = tipoProducto;
        this.trueFalse = trueFalse;
    }

    @Override
    public String toString() {
        return tipoProducto;
    }
}
