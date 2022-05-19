package test.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

import test.Entity.UsuarioDB;

public class UsuarioDBMapper implements Function<ResultSet,UsuarioDB> {
    private static final String DB_ID_USUARIO_COLUMN="idUsuario";
    private static final String DB_NID_COLUMN="Nid";
    private static final String DB_NOMBRE_COLUMN="Nombre";
    private static final String DB_APELLIDO_COLUMN="Apellido";
    private static final String DB_DIRECCION_COLUMN="Direccion";
    private static final String DB_EMAIL_COLUMN="Email";
    private static final String DB_TELEFONO_COLUMN="Telefono";
    private static final String DB_USUARIO_COLUMN="Usuario";
    private static final String DB_PASWORD_COLUMN="Password";
    private static final String DB_ROLE_COLUMN="Role";
    private static final String DB_IS_ACTIVE_COLUMN="Is_active";
    private static final String DB_FECHA_COLUMN="fecha";
    @Override
    public UsuarioDB apply(ResultSet rs) {
       UsuarioDB usuarioDB = new UsuarioDB();
       try {
        usuarioDB.setIdUsuario(rs.getInt(DB_ID_USUARIO_COLUMN));
        usuarioDB.setNid(rs.getInt(DB_NID_COLUMN));
        usuarioDB.setNombre(rs.getString(DB_NOMBRE_COLUMN));
        usuarioDB.setApellido(rs.getString(DB_APELLIDO_COLUMN));
        usuarioDB.setDireccion(rs.getString(DB_DIRECCION_COLUMN));
        usuarioDB.setEmail(rs.getString(DB_EMAIL_COLUMN));
        usuarioDB.setTelefono(rs.getString(DB_TELEFONO_COLUMN));
        usuarioDB.setUsuario(rs.getString(DB_USUARIO_COLUMN));
        usuarioDB.setPassword(rs.getString(DB_PASWORD_COLUMN));
        usuarioDB.setRole(rs.getString(DB_ROLE_COLUMN));
        usuarioDB.setIsActive(rs.getString(DB_IS_ACTIVE_COLUMN));
        usuarioDB.setFecha(rs.getDate(DB_FECHA_COLUMN));
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return usuarioDB;
    }
    
}
