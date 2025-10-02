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

  private static String SQL_INSERT =
      "INSERT INTO autos (patente, color, anio, kilometraje, marca, modelo, idCliente, idSeguro)" +
          "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

  private static String SQL_UPDATE =
      "UPDATE autos SET " +
          "patente = ?, " +
          "color = ?, " +
          "anio = ?, " +
          "kilometraje = ?, " +
          "marca = ?, " +
          "modelo = ? " +
          "WHERE idAuto = ?";

  private static String SQL_DELETE = "DELETE FROM autos WHERE idAuto = ?";
  private static String SQL_GETALL = "SELECT * FROM autos ORDER BY anio";
  private static String SQL_GETBYID = "SELECT * FROM autos WHERE idAuto = ?";
  private static String SQL_EXISTSBYID = "SELECT * FROM autos WHERE idAuto = ?";


  @Override
  public List<Auto> getAll() {
    List<Auto> lista = new ArrayList<>();
    conn = obtenerConexion();

    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
      pst = conn.prepareStatement(SQL_GETALL);
      rs = pst.executeQuery();

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
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return lista;
  }

  @Override
  public void insert(Auto objeto) {
    conn = obtenerConexion();
    PreparedStatement pst = null;
    ResultSet rs = null;
    Auto auto = objeto;

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

      int resultado = pst.executeUpdate();
      if (resultado == 1) {
        System.out.println("Auto agregado correctamente.");
      } else {
        System.out.println("No se pudo agregar el auto.");
      }

      rs = pst.getGeneratedKeys();

      if (rs.next()) {
        auto.setIdAuto(rs.getInt(1));
        System.out.println("El id asignado es: " + auto.getIdAuto());
      }

      pst.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(Auto objeto) {
    conn = obtenerConexion();
    if (this.existsById(objeto.getIdAuto())) {
      try {
        PreparedStatement pst = conn.prepareStatement(SQL_UPDATE);

        pst.setString(1, objeto.getPatente());
        pst.setString(2, objeto.getColor());
        pst.setInt(3, objeto.getAnio());
        pst.setInt(4, objeto.getKilometraje());
        pst.setString(5, objeto.getMarca().toString());
        pst.setString(6, objeto.getModelo());
        pst.setInt(7, objeto.getIdAuto());

        int resultado = pst.executeUpdate();
        if (resultado == 1) {
          System.out.println("Auto actualizado correctamente");
        } else {
          System.out.println("No se pudo actualizar el auto");
        }
        pst.close();
        conn.close();
      } catch (SQLException e) {
        System.out.println("Error al crear el statement");
      }
    }
  }

  @Override
  public void delete(Integer id) {
    conn = obtenerConexion();

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
    conn = obtenerConexion();
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
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return auto;
  }

  @Override
  public boolean existsById(Integer id) {
    conn = obtenerConexion();
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
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return existe;
  }
}
