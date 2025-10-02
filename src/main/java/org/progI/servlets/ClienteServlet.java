package org.progI.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.progI.dao.ClienteDAO;
import org.progI.entities.Cliente;

import java.io.IOException;

public class ClienteServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    String nombre = "";
    String apellido = "";
    String telefono = "";
    String operacion = "nuevo";
    int id = -1;
    operacion = req.getParameter("operacion");

    if (operacion.equals("editar") || operacion.equals("nuevo")) {
      nombre = req.getParameter("txtNombre");
      apellido = req.getParameter("txtApellido");
      telefono = req.getParameter("txtTelefono");
      id = Integer.parseInt(req.getParameter("txtId"));
    }
    else {
      id = Integer.parseInt(req.getParameter("txtId"));
    }

    ClienteDAO clienteDAO = new ClienteDAO();
    if (operacion.equals("nuevo")) {
      Cliente clienteNuevo = new Cliente(nombre, apellido, telefono);
      clienteDAO.insert(clienteNuevo);
    }
    if (operacion.equals("editar")) {
      Cliente clienteEditar = clienteDAO.getById(id);
      clienteEditar.setNombre(nombre);
      clienteEditar.setApellido(apellido);
      clienteEditar.setTelefono(telefono);
      clienteDAO.update(clienteEditar);
    }
    if (operacion.equals("eliminar")) {
      clienteDAO.delete(id);
    }
    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
    rd.forward(req, res);
  }
}

