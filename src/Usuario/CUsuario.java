package Usuario;

public class CUsuario {

    private int idUsuario;
    private int idEmpleado;
    private String nombre;
    private String contraseña;

    private boolean trueFalse;

    CUsuario() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public boolean isTrueFalse() {
        return trueFalse;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setTrueFalse(boolean trueFalse) {
        this.trueFalse = trueFalse;
    }

    public CUsuario(int idUsuario, int idEmpleado, String nombre, String contraseña, boolean trueFalse) {
        this.idUsuario = idUsuario;
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.trueFalse = trueFalse;
    }

//    public CUsuario(String nombre, String contraseña) {
//        this.idUsuario = -1;
//        this.nombre = nombre;
//        this.contraseña = contraseña;
//    }

    @Override
    public String toString() {
        return nombre;
    }
}
