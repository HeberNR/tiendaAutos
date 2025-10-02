package org.progI.dao;

import org.progI.entities.Cliente;
import org.progI.interfaces.AdmConexion;
import org.progI.interfaces.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO implements AdmConexion, DAO<Cliente, Integer> {
  private Connection conn = null;

  private static String SQL_INSERT =
      "INSERT INTO clientes (nombre, apellido, telefono) " +
          "VALUES (?, ?, ?)";
  private static String SQL_UPDATE =
      "UPDATE clientes SET " +
          "nombre = ?, " +
          "apellido = ?, " +
          "telefono = ? " +
          "WHERE idCliente = ?";
  private static String SQL_DELETE = "DELETE FROM clientes WHERE idCliente = ?";
  private static String SQL_GETALL = "SELECT * FROM clientes ORDER BY nombre";
  private static String SQL_GETBYID = "SELECT * FROM clientes WHERE idCliente = ?";
  private static String SQL_EXISTSBYID = "SELECT * FROM clientes WHERE idCliente = ?";

  @Override
  public List<Cliente> getAll() {
    conn = obtenerConexion();

    PreparedStatement pst = null;
    ResultSet rs = null;

    List<Cliente> lista = new ArrayList<>();

    try {
      pst = conn.prepareStatement(SQL_GETALL);
      rs = pst.executeQuery();

      while (rs.next()) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("idCliente"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellido(rs.getString("apellido"));
        cliente.setTelefono(rs.getString("telefono"));

        lista.add(cliente);
      }

      pst.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      System.out.println("Error al crear el statement.");
      throw new RuntimeException(e);
    }
    return lista;
  }

  @Override
  public void insert(Cliente objeto) {
    conn = obtenerConexion();
    PreparedStatement pst = null;
    ResultSet rs = null;
    Cliente cliente = objeto;

    try {
      pst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

      pst.setString(1, cliente.getNombre());
      pst.setString(2, cliente.getApellido());
      pst.setString(3, cliente.getTelefono());

      int resultado = pst.executeUpdate();
      if (resultado == 1) {
        System.out.println("Cliente agregado correctamente.");
      } else {
        System.out.println("No se pudo agregar el cliente.");
      }

      rs = pst.getGeneratedKeys();

      if (rs.next()) {
        cliente.setIdCliente(rs.getInt(1));
        System.out.println("El id asignado es: " + cliente.getIdCliente());
      }

      pst.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void update(Cliente objeto) {
    conn = obtenerConexion();
    if (this.existsById(objeto.getIdCliente())) {
      try {
        PreparedStatement pst = conn.prepareStatement(SQL_UPDATE);

        pst.setString(1, objeto.getNombre());
        pst.setString(2, objeto.getApellido());
        pst.setString(3, objeto.getTelefono());
        pst.setInt(4, objeto.getIdCliente());

        int resultado = pst.executeUpdate();
        if (resultado == 1) {
          System.out.println("Cliente actualizado correctamente");
        } else {
          System.out.println("No se pudo actualizar el cliente");
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
        System.out.println("Cliente eliminado correctamente");
      } else {
        System.out.println("No se pudo eliminar el cliente");
      }

      pst.close();
      conn.close();
    } catch (SQLException e) {
      System.out.println("No se pudo eliminar el cliente. Error: " + e.getMessage());
    }
  }

  @Override
  public Cliente getById(Integer id) {
    conn = obtenerConexion();
    PreparedStatement pst = null;
    ResultSet rs = null;
    Cliente cliente = null;

    try {
      pst = conn.prepareStatement(SQL_EXISTSBYID);
      pst.setInt(1, id);
      rs = pst.executeQuery(); //ejecuto la consulta
      //Si la consulta devuelve al menos 1 regristo, existe
      if (rs.next()) {
        cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("idCliente"));
        cliente.setNombre((rs.getString("nombre")));
        cliente.setApellido(rs.getString("apellido"));
        cliente.setTelefono(rs.getString("telefono"));
      }

      pst.close();
      rs.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return cliente;
  }

  @Override
  public boolean existsById(Integer id) {
    conn = obtenerConexion();
    // Se crea un statement
    PreparedStatement pst = null;
    ResultSet rs = null;
    boolean existe = false;

    try {
      pst = conn.prepareStatement(SQL_GETBYID);
      pst.setInt(1, id);
      rs = pst.executeQuery(); // Ejecuto la consulta
      //Si la consulta devuelve al menos 1 regristo, existe
      if (rs.next()) {
        existe = true;
      }

      rs.close();
      pst.close();
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return existe;
  }

  @Override
  public Connection obtenerConexion() {
    return AdmConexion.super.obtenerConexion();
  }
}
