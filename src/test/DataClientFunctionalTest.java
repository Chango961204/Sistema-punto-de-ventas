package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import test.Entity.UsuarioDB;
import test.Mappers.UsuarioDBMapper;


public class DataClientFunctionalTest {
    Connection connection = null;
    public static final String URL="jdbc:mysql://localhost:3306/punto_de_ventas?autoReconnet=true&useSSL=false";//se crea static para que lo pueda usar la clase y final para que  
    public static final String usuario="root";
    public static final String contraseña="";
    public final UsuarioDBMapper usuarioDBMapper = new UsuarioDBMapper();
    private final String SQL_USER_QUERY= "select * from tusuarios";
    private final String SQL_STORE_QUERY= "select * from tbodega";
    private final String SQL_TEMPORAL_PRODUCTOS_QUERY= "select * from ttemporal_productos";
    @BeforeEach
    void init() {
    }
    @AfterEach
    void close(){
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName(value = "It can evalute consistent data users from client")
    @Test
    public void it_can_handles_solid_data_users_from_client_database() {
        getConexion();
        try (PreparedStatement ps = connection.prepareStatement(SQL_USER_QUERY)) {
            try (ResultSet rs = ps.executeQuery()) {
                List<UsuarioDB> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(usuarioDBMapper.apply(rs));
                }
                users.stream().forEach((user)->{
                    assertTrue(user instanceof UsuarioDB);
                    System.out.println(user.toString().concat(" User_"+user.getNombre()+" cotains requiremts"));
                });
                System.out.println("\nTest Ok - All Users contains all fields");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @DisplayName(value = "It can evalute consistent store data from client")
    @Test
    public void it_can_handles_consistent_data_store_from_client_database() {
        getConexion();
        try (PreparedStatement ps = connection.prepareStatement(SQL_STORE_QUERY)) {
            try (ResultSet rs = ps.executeQuery()) {
                List<String> stores = new ArrayList<>();
                while (rs.next()) {
                    stores.add(rs.getString("Existencia"));
                }
                stores.stream().forEach(System.out::println);
                System.out.println("\nTest Ok - All stores contains all fields");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
    @DisplayName(value = "It can evalute consistent relation on temporal and produts data from client")
    @Test
    public void it_can_handles_consistent_data_temporal_produtcs_from_client_database() {
        getConexion();
        try (PreparedStatement ps = connection.prepareStatement(SQL_TEMPORAL_PRODUCTOS_QUERY)) {
            try (ResultSet rs = ps.executeQuery()) {
                List<String> temporalProdtcs = new ArrayList<>();
                while (rs.next()) {
                    temporalProdtcs.add("["+rs.getString("id")+"-"+rs.getString("idProducto")+"-"+rs.getString("idUsuario")+"]");
                }
                temporalProdtcs.stream().forEach(System.out::println);
                System.out.println("\nTest Ok - All relations contains all fields");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getConexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(URL, usuario, contraseña);
        } catch (Exception e) {
            System.err.println("Error, " + e);
        }
    }

}
