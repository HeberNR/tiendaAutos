package org.progI.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.progI.dao.AutoImpl;
import org.progI.dao.ClienteDAO;
import org.progI.entities.Auto;
import org.progI.entities.Cliente;
import org.progI.entities.Marca;
import org.progI.entities.Seguro;

import java.io.IOException;

public class AutoServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    String patente = "";
    String color = "";
    int anio = 0;
    int kilometraje = 0;
    Marca marca = null;
    String modelo = "";
    String operacion = "nuevo";
    int idCliente = 0;
    int idSeguro = 0;
    int idAuto = -1;

    operacion = req.getParameter("operacion");

    AutoImpl autoImpl = new AutoImpl();

    switch (operacion) {
      case "nuevo":
        Cliente clienteNuevo;
        ClienteDAO a = new ClienteDAO();
        int idCli=Integer.valueOf(req.getParameter("selectCliente"));
        clienteNuevo = a.getById(idCli);

        Seguro seguroNuevo = new Seguro();
        seguroNuevo.setIdSeguro(idSeguro);

        Auto autoNuevo = new Auto(patente, color, anio, kilometraje, marca, modelo,
            clienteNuevo, seguroNuevo);
        autoImpl.insert(autoNuevo);
        break;

      case "editar":
        Auto autoEditar = autoImpl.getById(idAuto);
        if (autoEditar != null) {
          autoEditar.setPatente(patente);
          autoEditar.setColor(color);
          autoEditar.setAnio(anio);
          autoEditar.setKilometraje(kilometraje);
          autoEditar.setMarca(marca);
          autoEditar.setModelo(modelo);

          Cliente clienteEditar = new Cliente();
          clienteEditar.setIdCliente(idCliente);

          Seguro seguroEditar = new Seguro();
          seguroEditar.setIdSeguro(idSeguro);

          autoImpl.update(autoEditar);
        }
        break;

      case "eliminar":
        autoImpl.delete(idAuto);
        break;
    }
    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
    rd.forward(req, res);
  }
}
