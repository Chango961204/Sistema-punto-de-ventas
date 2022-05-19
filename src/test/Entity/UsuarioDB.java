package test.Entity;

import java.sql.Date;

public class UsuarioDB {
    private Integer idUsuario;
    private Integer nid;
    private String nombre;
    private String apellido;
    private String direccion;
    private String email;
    private String telefono;
    private String usuario;
    private String password;
    private String role;
    private String isActive;
    private Date fecha;

    public UsuarioDB(Integer idUsuario, Integer nid, String nombre, String apellido, String direccion, String email,
            String telefono, String usuario, String password, String role, String isActive, Date fecha) {
        this.idUsuario = idUsuario;
        this.nid = nid;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.usuario = usuario;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.fecha = fecha;
    }

    public UsuarioDB() {
    }

    public Integer getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getNid() {
        return this.nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIsActive() {
        return this.isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "[" +
                getIdUsuario() +
                "," + getNid() +
                "," + getNombre() +
                "," + getApellido() +
                "," + getDireccion() +
                "," + getEmail() +
                "," + getTelefono() +
                "," + getUsuario() +
                "," + getPassword() +
                "," + getRole() +
                "," + getIsActive() +
                "," + getFecha() +
                "]";
    }

}
