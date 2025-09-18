package org.progI.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.progI.dao.ClienteDAO;
import org.progI.entities.Cliente;

import java.io.IOException;
import java.util.Date;

public class clienteServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    String nombre = "";
    String apellido = "";
    String telefono = "";
    String operacion = "nuevo";
    int id = -1;

    nombre = req.getParameter("txtNombre");
    apellido = req.getParameter("txtApellido");
    telefono = req.getParameter("txtTelefono");
    id = Integer.parseInt(req.getParameter("txtId"));

    Cliente clienteNuevo = new Cliente(nombre, apellido, telefono);
    ClienteDAO clienteDAO = new ClienteDAO();
    clienteDAO.insert(clienteNuevo);

    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");

    rd.forward(req, res);
  }
}

