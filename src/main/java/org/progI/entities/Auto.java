package org.progI.entities;

import java.util.Objects;

public class Auto implements Comparable<Auto> {

  private int idAuto;
  private String patente;
  private String color;
  private int anio;
  private int kilometraje;
  private Marca marca;
  private String modelo;
  private Cliente cliente;
  private Seguro seguro;


  public Auto() {
    this.cliente = new Cliente();
    this.seguro = new Seguro();
  }


  public Auto(String patente) {
    idAuto = -1;
    this.patente = patente;
    this.cliente = new Cliente();
    this.seguro = new Seguro();
  }

  public Auto(String patente, String color, int anio, int kilometraje, Marca marca,
              String modelo, Cliente cliente, Seguro seguro) {
    super();
    this.patente = patente;
    this.color = color;
    this.anio = anio;
    this.kilometraje = kilometraje;
    this.marca = marca;
    this.modelo = modelo;
    this.cliente = cliente;
    this.seguro = seguro;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public Seguro getSeguro() {
    return seguro;
  }

  public void setSeguro(Seguro seguro) {
    this.seguro = seguro;
  }

  public int getIdAuto() {
    return idAuto;
  }


  public void setIdAuto(int idAuto) {
    this.idAuto = idAuto;
  }


  public String getPatente() {
    return patente;
  }


  public void setPatente(String patente) {
    this.patente = patente;
  }


  public String getColor() {
    return color;
  }


  public void setColor(String color) {
    this.color = color;
  }


  public int getAnio() {
    return anio;
  }


  public void setAnio(int anio) {
    this.anio = anio;
  }


  public int getKilometraje() {
    return kilometraje;
  }


  public void setKilometraje(int kilometraje) {
    this.kilometraje = kilometraje;
  }


  public Marca getMarca() {
    return marca;
  }


  public void setMarca(Marca marca) {
    this.marca = marca;
  }


  public String getModelo() {
    return modelo;
  }


  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  @Override
  public String toString() {
    return "Auto{" +
        "idAuto=" + idAuto +
        ", patente='" + patente + '\'' +
        ", color='" + color + '\'' +
        ", anio=" + anio +
        ", kilometraje=" + kilometraje +
        ", marca=" + marca +
        ", modelo='" + modelo + '\'' +
        ", cliente=" + cliente +
        ", seguro=" + seguro +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Auto auto = (Auto) o;
    return idAuto == auto.idAuto &&
        anio == auto.anio &&
        kilometraje == auto.kilometraje &&
        Objects.equals(patente, auto.patente) &&
        Objects.equals(color, auto.color) &&
        marca == auto.marca &&
        Objects.equals(modelo, auto.modelo) &&
        Objects.equals(cliente, auto.cliente) &&
        Objects.equals(seguro, auto.seguro);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idAuto, patente, color, anio, kilometraje,
        marca, modelo, cliente, seguro);
  }


  @Override
  public int compareTo(Auto otro) {

    int cmp = this.marca.compareTo(otro.marca);

    if (cmp == 0) {
      cmp = Integer.compare(this.kilometraje, otro.kilometraje);
    }

    return cmp;
  }
}

