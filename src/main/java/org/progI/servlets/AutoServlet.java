package org.progI.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.progI.dao.AutoImpl;
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
    Cliente cliente = null;
    Seguro seguro = null;
    int id = -1;

    operacion = req.getParameter("operacion");

      if ("editar".equals(operacion) || "nuevo".equals(operacion)) {
        patente = req.getParameter("txtPatente");
        color = req.getParameter("txtColor");
        anio = Integer.parseInt(req.getParameter("txtAnio"));
        kilometraje = Integer.parseInt(req.getParameter("txtKilometraje"));
        marca = Marca.valueOf(req.getParameter("txtMarca"));
        modelo = req.getParameter("txtModelo");
      } else {
        id = Integer.parseInt(req.getParameter("id"));
      }

      AutoImpl autoImpl = new AutoImpl();
      if ("nuevo".equals(operacion)) {
        Auto autoNuevo = new Auto(patente, color, anio, kilometraje, marca, modelo);
        autoImpl.insert(autoNuevo);
      }

      if ("editar".equals(operacion)) {
        Auto autoEditar = autoImpl.getById(id);
        autoEditar.setPatente(patente);
        autoEditar.setColor(color);
        autoEditar.setAnio(anio);
        autoEditar.setKilometraje(kilometraje);
        autoEditar.setMarca(marca);
        autoEditar.setModelo(modelo);
        autoEditar.setCliente(cliente);
        autoEditar.setSeguro(seguro);
        autoImpl.update(autoEditar);
      }

      if ("eliminar".equals(operacion)) {
        autoImpl.delete(id);
      }

      RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
      rd.forward(req, res);
    }
}
