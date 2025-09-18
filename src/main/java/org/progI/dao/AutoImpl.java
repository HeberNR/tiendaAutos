package org.progI.dao;

import org.progI.entities.Auto;
import org.progI.entities.Marca;
import org.progI.interfaces.AdmConexion;
import org.progI.interfaces.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutoImpl implements AdmConexion, DAO<Auto, Integer> {
  private Connection conn = null;

  private static final String SQL_INSERT =
      "INSERT INTO autos (patente, color, anio, kilometraje, marca, modelo, idCliente, idSeguro)" +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String SQL_UPDATE =
      "UPDATE autos SET " +
          "patente = ?, " +
          "color = ?, " +
          "anio = ?, " +
          "kilometraje = ?, " +
          "marca = ?, " +
          "modelo = ? " +
          "WHERE idAuto = ?";

  private static final String SQL_DELETE = "DELETE FROM autos WHERE idAuto = ?";
  private static final String SQL_GETALL = "SELECT * FROM autos ORDER BY patente";
  private static final String SQL_GETBYID = "SELECT * FROM autos WHERE idAuto = ?";
  private static final String SQL_EXISTSBYID = "SELECT * FROM autos WHERE idAuto = ?";


  @Override
  public List<Auto> getAll() {
    List<Auto> lista = new ArrayList<>();

    try {
      PreparedStatement pst = conn.prepareStatement(SQL_GETALL);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        Auto auto = new Auto();
        auto.setIdAuto(rs.getInt("idAuto"));
        auto.setPatente(rs.getString("patente"));
        auto.setColor(rs.getString("color"));
        auto.setAnio(rs.getInt("anio"));
        auto.setKilometraje(rs.getInt("kilometraje"));
        auto.setMarca(Marca.valueOf(rs.getString("marca")));
        auto.setModelo(rs.getString("modelo"));

        lista.add(auto);
      }

      rs.close();
      pst.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return lista;
  }

  @Override
  public void insert(Auto objeto) {
    // 1 establecer conexion a la base de datos
    conn = obtenerConexion();
    ClienteDAO clienteDAO = new ClienteDAO();
    SeguroDAO seguroDAO = new SeguroDAO();

    boolean existeCliente = clienteDAO.existsById(objeto.getCliente().getIdCliente());
    boolean existeSeguro = seguroDAO.existsById(objeto.getSeguro().getIdSeguro());

    //solo guardo si existe el cliente y el seguro en la base de datos
    if (existeSeguro && existeCliente) {

      // 2 Crear string de consulta SQL
      PreparedStatement pst = null;
      try {
        pst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

        pst.setString(1, objeto.getPatente());
        pst.setString(2, objeto.getColor());
        pst.setInt(3, objeto.getAnio());
        pst.setInt(4, objeto.getKilometraje());
        pst.setString(5, objeto.getMarca().toString());
        pst.setString(6, objeto.getModelo());
        pst.setInt(7, objeto.getCliente().getIdCliente());
        pst.setInt(8, objeto.getSeguro().getIdSeguro());

        // 3 Ejecutar la instruccion
        int resultado = pst.executeUpdate();
        if (resultado == 1) {
          System.out.println("Auto insertado correctamente");
        } else {
          System.out.println("No se pudo insertar el auto");
        }

        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
          objeto.setIdAuto(rs.getInt(1));
          System.out.println("El id asignado es el: " + objeto.getIdAuto());
        }

        // 4 Cerrar conexiones
        pst.close();
        conn.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void update(Auto objeto) {
    ClienteDAO clienteDAO = new ClienteDAO();
    SeguroDAO seguroDAO = new SeguroDAO();
    boolean existeCliente = clienteDAO.existsById(objeto.getCliente().getIdCliente());
    boolean existeSeguro = seguroDAO.existsById(objeto.getSeguro().getIdSeguro());
    if (existeCliente && existeSeguro) {

      if (this.existsById(objeto.getIdAuto())) {
        try {
          PreparedStatement pst = conn.prepareStatement(SQL_UPDATE); //establecer la conexion

          pst.setString(1, objeto.getPatente());
          pst.setString(2, objeto.getColor());
          pst.setInt(3, objeto.getAnio());
          pst.setInt(4, objeto.getKilometraje());
          pst.setString(5, objeto.getMarca().toString());
          pst.setString(6, objeto.getModelo());
          pst.setInt(7, objeto.getIdAuto());

          pst.executeUpdate(); //executo la consulta(update)

          pst.close(); // cierro conexion
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  @Override
  public void delete(Integer id) {
    Connection conn = this.obtenerConexion();

    try {
      PreparedStatement pst = conn.prepareStatement(SQL_DELETE);
      pst.setInt(1, id);
      int resultado = pst.executeUpdate();
      if (resultado == 1) {
        System.out.println("Auto eliminado correctamente");
      } else {
        System.out.println("No se pudo eliminar el auto");
      }
      pst.close();
      conn.close();
    } catch (SQLException e) {
      System.out.println("No se pudo eliminar el auto. Error: " + e.getMessage());
    }

  }

  @Override
  public Auto getById(Integer id) {
    Auto auto = new Auto();
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
      pst = conn.prepareStatement(SQL_GETBYID);
      pst.setInt(1, id);
      rs = pst.executeQuery();

      if (rs.next()) {
        auto.setIdAuto(rs.getInt("idAuto"));
        auto.setPatente(rs.getString("patente"));
        auto.setColor(rs.getString("color"));
        auto.setMarca(Marca.valueOf(rs.getString("marca")));
        auto.setAnio(rs.getInt("anio"));
        auto.setKilometraje(rs.getInt("kilometraje"));
        auto.setModelo(rs.getString("modelo"));
      }

      pst.close();
      rs.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return auto;
  }

  @Override
  public boolean existsById(Integer id) {
    PreparedStatement pst = null;
    ResultSet rs = null;
    boolean existe = false;

    try {
      pst = conn.prepareStatement(SQL_EXISTSBYID);
      pst.setInt(1, id);
      rs = pst.executeQuery();

      if (rs.next()) {
        existe = true;
      }

      pst.close();
      rs.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return existe;
  }
}
