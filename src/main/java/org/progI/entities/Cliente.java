package org.progI.entities;

import java.util.Objects;

public class Cliente implements Comparable<Cliente> {
  private int idCliente;
  private String nombre;
  private String apellido;
  private String telefono;

  public Cliente() {
    idCliente = -1;
  }

  public Cliente(String nombre, String apellido, String telefono) {
    super();
    this.nombre = nombre;
    this.apellido = apellido;
    this.telefono = telefono;
  }

  public int getIdCliente() {
    return idCliente;
  }

  public void setIdCliente(int idCliente) {
    this.idCliente = idCliente;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }


  @Override
  public String toString() {
    return "Cliente{" +
        "idCliente=" + idCliente +
        ", nombre='" + nombre + '\'' +
        ", apellido='" + apellido + '\'' +
        ", telefono='" + telefono + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Cliente cliente = (Cliente) o;
    return idCliente == cliente.idCliente &&
        Objects.equals(nombre, cliente.nombre) &&
        Objects.equals(apellido, cliente.apellido) &&
        Objects.equals(telefono, cliente.telefono);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idCliente, nombre, apellido, telefono);
  }


  @Override
  public int compareTo(Cliente otroCliente) {
    int resultado = this.nombre.compareTo(otroCliente.nombre);

    if (resultado == 0) {
      resultado = this.apellido.compareTo(otroCliente.apellido);

      if (resultado == 0) {
        resultado = this.telefono.compareTo(otroCliente.apellido);
      }
    }
    return resultado;
  }
}
